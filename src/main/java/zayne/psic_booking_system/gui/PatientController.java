package zayne.psic_booking_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import zayne.psic_booking_system.models.Appointment;
import zayne.psic_booking_system.models.Patient;
import zayne.psic_booking_system.models.Physician;

import java.util.ArrayList;
import java.util.Calendar;

public class PatientController {

  public Label labelErrMsg;
  public Label labelPatientID;
  public Label labelPatientRegErrMsg;
  public Label labelAppointmentStatus;
  public Label labelAppointmentPatientID;
  public ListView<String> listViewResult;
  public TextField textPatientRegisterTel;
  public TextField textPatientRegisterName;
  public TextField textPatientRegisterAddr;
  public TextField textFieldKeyword;
  public ChoiceBox<String> choiceBoxSearchBy;
  public ChoiceBox<String> choiceBoxExpertise;
  public ChoiceBox<String> choiceBoxPatient;
  public ChoiceBox<String> choiceBoxAppointment;
  public ChoiceBox<String> choiceBoxPatientAppointment;
  public RadioButton radioBtnIsVisitor;
  public RadioButton radioBtnIsPatient;
  public ToggleGroup userIdentity = new ToggleGroup();
  public Button btnSearch;
  public Button btnBookAppointment;
  public Button btnPatientRegistration;
  public Button btnAttendAppointment;
  public Button btnMissAppointment;
  public Button btnCancelAppointment;
  // public DatePicker datePickerPatient;

  public ArrayList<Appointment> appointmentsForSpecificPatient = new ArrayList<>();

  /* method `initialize()` is called after the constructor. */
  @FXML
  public void initialize() {
    choiceBoxSearchBy.getItems().add("Name");
    choiceBoxSearchBy.getItems().add("Expertise");
    choiceBoxSearchBy.setValue("Name");

    choiceBoxExpertise.setDisable(true);
    radioBtnIsPatient.setSelected(true);
    btnMissAppointment.setDisable(true);
    btnAttendAppointment.setDisable(true);
    btnCancelAppointment.setDisable(true);

    setupPropertyBindings();
    setupEventListeners();

    loadExpertise();

    refreshPatientData();
  }

  private void loadExpertise() {
    for (var v : Physician.Expertise.values())
      choiceBoxExpertise.getItems().add(v.name().toLowerCase());
  }

  private void setupEventListeners() {
    choiceBoxSearchBy
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              choiceBoxExpertise.setDisable(newValue.equals("Name"));
              // choiceBoxExpertise.setValue(DEFAULT_EXPERTISE);
              textFieldKeyword.setDisable(newValue.equals("Expertise"));
              // textFieldKeyword.setText(DEFAULT_KEYWORD);
            });

    choiceBoxPatientAppointment
        .valueProperty()
        .addListener(
            (observable, oldV, newV) -> {
              var patient = Patient.getPatient(newV);
              labelAppointmentPatientID.setText(String.valueOf(patient._id));
              loadAppointmentsForPatient(patient);
            });

    choiceBoxAppointment
        .valueProperty()
        .addListener(
            ((observable, oldValue, newValue) -> {
              var appointment = Appointment.getAppointmentById(newValue.split("#")[1]);
              labelAppointmentStatus.setText(appointment.state.toString());
              if (!appointment.isCancelled()
                  || !appointment.isMissed()
                  || !appointment.isAttended()) {
                btnMissAppointment.setDisable(false);
                btnAttendAppointment.setDisable(false);
                btnCancelAppointment.setDisable(false);
              }
            }));

    choiceBoxPatient
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              var patient = Patient.getPatient(newValue);
              labelPatientID.setText("Patient ID: " + patient._id);
              search();
            });

    radioBtnIsVisitor
        .onMouseClickedProperty()
        .addListener(
            (event) -> {
              // TODO: Change slots showing to consultation.
              search();
            });

    btnSearch.setOnMouseClicked(mouseEvent -> search());
    btnBookAppointment.setOnMouseClicked(mouseEvent -> book());
    btnPatientRegistration.setOnMouseClicked(event -> register());
    btnAttendAppointment.setOnMouseClicked(event -> attendAnAppointment());
    btnMissAppointment.setOnMouseClicked(event -> missAnAppointment());
    btnCancelAppointment.setOnMouseClicked(event -> cancelAnAppointment());
  }

  private void loadAppointmentsForPatient(Patient patient) {
    appointmentsForSpecificPatient.clear();
    patient
        .getBookedAppointment()
        .forEach(
            appointment -> {
              appointmentsForSpecificPatient.add(appointment);
              this.choiceBoxAppointment.getItems().add(appointment.toString());
            });
  }

  private void setupPropertyBindings() {
    choiceBoxAppointment
        .disableProperty()
        .bind(choiceBoxPatientAppointment.valueProperty().isEqualTo(""));

    choiceBoxPatient.disableProperty().bind(radioBtnIsVisitor.selectedProperty());
    labelPatientID.disableProperty().bind(radioBtnIsVisitor.selectedProperty());
  }

  private void refreshPatientData() {
    choiceBoxPatient.getItems().clear();
    choiceBoxPatientAppointment.getItems().clear();

    for (var patient : Patient.getPatients()) {
      choiceBoxPatient.getItems().add("%s".formatted(patient.name));
      choiceBoxPatientAppointment.getItems().add("%s".formatted(patient.name));
    }
  }

  private void loadSearchResult(ArrayList<Appointment> appointments) {

    appointments.forEach(
        appointment -> {
          String[] days = new String[] {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

          var resMonth = appointment.startTime.get(Calendar.MONTH) + 1;
          var resDay = appointment.startTime.get(Calendar.DAY_OF_MONTH);
          var resDayOfWeek = days[appointment.startTime.get(Calendar.DAY_OF_WEEK) - 1];
          var resHour = appointment.startTime.get(Calendar.HOUR_OF_DAY);
          var resPhysicianName = String.join("_", appointment.physician.name.split(" "));
          var resRoomName =
              appointment.room.roomName.length() == 1
                  ? "Room-" + appointment.room.roomName
                  : appointment.room.roomName;
          var resTreatment = appointment.treatment.toString();
          // Assemble resulting string.
          // "05-12 Wed 16:00 Some_Name Room-A Message"
          var res =
              "%02d-%02d %s %s:00  %s\t%s\t%s"
                  .formatted(
                      resMonth,
                      resDay,
                      resDayOfWeek,
                      resHour,
                      resPhysicianName,
                      resRoomName,
                      resTreatment);

          listViewResult.getItems().add(res);
        });
  }

  private void attendAnAppointment() {
    var appointment =
        appointmentsForSpecificPatient.get(
            choiceBoxAppointment.getSelectionModel().getSelectedIndex());
    if (!appointment.isCancelled() || !appointment.isMissed() || !appointment.isAttended()) {
      appointment.attend();
      labelAppointmentStatus.setText(appointment.state.toString());
      btnMissAppointment.setDisable(true);
      btnAttendAppointment.setDisable(true);
      btnCancelAppointment.setDisable(true);
    }
    DataController.controller.refreshData();
  }

  private void missAnAppointment() {
    var appointment =
        appointmentsForSpecificPatient.get(
            choiceBoxAppointment.getSelectionModel().getSelectedIndex());
    if (!appointment.isCancelled() || !appointment.isMissed() || !appointment.isAttended()) {
      appointment.miss();
      labelAppointmentStatus.setText(appointment.state.toString());
      btnMissAppointment.setDisable(true);
      btnAttendAppointment.setDisable(true);
      btnCancelAppointment.setDisable(true);
    }
    DataController.controller.refreshData();
  }

  private void cancelAnAppointment() {
    var appointment =
        appointmentsForSpecificPatient.get(
            choiceBoxAppointment.getSelectionModel().getSelectedIndex());
    if (!appointment.isCancelled() || !appointment.isMissed() || !appointment.isAttended()) {
      appointment.cancel();
      labelAppointmentStatus.setText(appointment.state.toString());
      btnMissAppointment.setDisable(true);
      btnAttendAppointment.setDisable(true);
      btnCancelAppointment.setDisable(true);
    }
    DataController.controller.refreshData();
  }

  public void search() {
    labelErrMsg.setText("");
    // Clear the result listview for new search.
    listViewResult.getItems().clear();
    var searchByName = choiceBoxSearchBy.getValue().equals("Name");

    if (choiceBoxPatient.getValue() == null) {
      labelErrMsg.setText("Please Select Patient!");
      return;
    }

    var patient = Patient.getPatient(choiceBoxPatient.getValue());
    var keyword = textFieldKeyword.getText().strip();
    var expertise =
        choiceBoxExpertise.getValue() == null ? null : choiceBoxExpertise.getValue().strip();

    if (searchByName) {
      if (keyword.equals("")) {
        labelErrMsg.setText("Enter Search Keyword!");
        return;
      }
      // Done： get target physician's all slot and availability
      var physicians = Physician.getPhysiciansByName(keyword.strip());

      for (Physician physician : physicians) {
        var availableAppointments = availabilityPipeline(physician, patient);

        // Add available appointment to search result view.
        loadSearchResult(availableAppointments);
      }
      var searchResult =
          physicians.size() == 0
              ? "No results found."
              : "%d physicians %d slots."
                  .formatted(physicians.size(), listViewResult.getItems().size());

      labelErrMsg.setText(searchResult);

    } else if (expertise != null) {
      // System.out.printf("Search by expertise %s - %s%n", expertise);

      var physiciansWithTargetExpertise = Physician.getPhysiciansByExpertise(expertise);

      physiciansWithTargetExpertise.forEach(
          physician -> {
            var availableAppointments = availabilityPipeline(physician, patient);

            // Add available appointment to search result view.
            loadSearchResult(availableAppointments);
          });

      var searchResult =
          physiciansWithTargetExpertise.size() == 0
              ? "No results found."
              : "%d physicians %d slots."
                  .formatted(
                      physiciansWithTargetExpertise.size(), listViewResult.getItems().size());
      labelErrMsg.setText(searchResult);

    } else {
      labelErrMsg.setText("Fill in all fields!!");
    }
    // System.out.println(choiceBoxExpertise.getValue());
    // if (searchBy)
  }

  private ArrayList<Appointment> availabilityPipeline(Physician physician, Patient patient) {
    var availableAppointments = physician.getAvailableAppointments();
    // Availability Pipeline:::: Patient's side: no parallel appointments are allowed.
    var parallelAppointments = new ArrayList<Appointment>();
    availableAppointments.forEach(
        appointment -> {
          patient
              .getBookedAppointment()
              .forEach(
                  patientAppointment -> {
                    var isSameMonth =
                        appointment.startTime.get(Calendar.MONTH)
                            == patientAppointment.startTime.get(Calendar.MONTH);
                    var isSameDay =
                        appointment.startTime.get(Calendar.DAY_OF_MONTH)
                            == patientAppointment.startTime.get(Calendar.DAY_OF_MONTH);
                    var isSameTime =
                        appointment.startTime.get(Calendar.HOUR_OF_DAY)
                            == patientAppointment.startTime.get(Calendar.HOUR_OF_DAY);

                    var isSameDate = isSameMonth && isSameDay && isSameTime;

                    if (isSameDate && !patientAppointment.isCancelled()) {

                      parallelAppointments.add(appointment);
                    }
                  });
        });
    availableAppointments.removeAll(parallelAppointments);

    return availableAppointments;
  }

  public void book() {
    var selected = listViewResult.getSelectionModel().getSelectedItem();
    if (selected == null) {
      labelErrMsg.setText("Select a slot to book!");
      return;
    }
    // `05-05 Wed 14:00  Betsy_Hopper	Room-A	Neural_Mobilisation`
    // regex to split a string by multiple consecutive spaces.
    var data = selected.split("\\s+");
    var date = Calendar.getInstance();
    date.set(
        2021,
        Integer.parseInt(data[0].split("-")[0]) - 1,
        Integer.parseInt(data[0].split("-")[1]),
        Integer.parseInt(data[2].split(":")[0]),
        0);
    var physician =
        Physician.getPhysiciansByName(String.join(" ", data[3].split("_")).strip()).get(0);
    var patient = Patient.getPatient(choiceBoxPatient.getValue().strip());
    var treatment = Physician.Treatment.valueOf(data[5].toUpperCase());
    // var room = Room.getRoom(data[4].split("-")[data[4].split("-").length - 1]);

    var appointment = new Appointment(date, physician, patient, treatment).book();
    if (appointment == null) labelErrMsg.setText("NOT booked (duplicated).");
    else {
      labelErrMsg.setText("Appointment booked!");
      DataController.controller.refreshData();
      System.out.println(appointment.getStat());
      search();
    }
  }

  public void register() {
    btnPatientRegistration.setDisable(true);
    labelPatientRegErrMsg.setText("");
    var name = textPatientRegisterName.getText().trim();
    var tel = textPatientRegisterTel.getText().trim();
    var addr = textPatientRegisterAddr.getText().trim();

    if (name.equals("") || tel.equals("") || addr.equals("")) {
      labelPatientRegErrMsg.setText("Please fill in all fields.");
      return;
    }

    var patient = new Patient(name, tel, addr);
    if (Patient.registerPatient(patient)) {
      textPatientRegisterName.setText("");
      textPatientRegisterTel.setText("");
      textPatientRegisterAddr.setText("");

      labelPatientRegErrMsg.setText("Successfully registered.");
      refreshPatientData();
      DataController.controller.refreshData();
    }
    btnPatientRegistration.setDisable(false);
  }
}
