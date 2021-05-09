package zayne.psic_booking_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import zayne.psic_booking_system.models.Appointment;
import zayne.psic_booking_system.models.Patient;
import zayne.psic_booking_system.models.Physician;
import zayne.psic_booking_system.utils.Helper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

public class PatientController {

  private static final String DEFAULT_EXPERTISE = "Enter Keyword";
  private static final String DEFAULT_KEYWORD = "Select Expertise";
  public Label labelErrMsg;
  public Label labelPatientID;
  public Label labelPatientRegisterErrMsg;
  public Label labelAppointment;
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
  public DatePicker datePickerPatient;

  /* method `initialize()` is called after the constructor. */
  @FXML
  public void initialize() {
    choiceBoxSearchBy.getItems().add("Name");
    choiceBoxSearchBy.getItems().add("Expertise");
    choiceBoxSearchBy.setValue("Name");

    choiceBoxExpertise.setDisable(true);
    radioBtnIsPatient.setSelected(true);
    datePickerPatient.setShowWeekNumbers(false);
    datePickerPatient.setDisable(true);
    datePickerPatient.setVisible(false);

    var minDate = LocalDate.of(2021, 5, 3);
    var maxDate = LocalDate.of(2021, 5, 30);
    datePickerPatient.setDayCellFactory(
        d ->
            new DateCell() {
              @Override
              public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(
                    item.isBefore(minDate)
                        || item.isAfter(maxDate)
                        || item.getDayOfWeek() == DayOfWeek.SATURDAY
                        || item.getDayOfWeek() == DayOfWeek.SUNDAY);
              }
            });
    datePickerPatient.valueProperty().addListener((observable, oldValue, newValue) -> {});

    choiceBoxSearchBy
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              choiceBoxExpertise.setDisable(newValue.equals("Name"));
              // choiceBoxExpertise.setValue(DEFAULT_EXPERTISE);
              textFieldKeyword.setDisable(newValue.equals("Expertise"));
              // textFieldKeyword.setText(DEFAULT_KEYWORD);
            });

    choiceBoxPatient
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              var patient = Patient.findPatient(newValue);
              labelPatientID.setText("Patient ID: " + patient._id);
            });
    choiceBoxPatientAppointment
        .valueProperty()
        .addListener(
            (observable, oldV, newV) -> {
              var patient = Patient.findPatient(newV);
              labelAppointmentPatientID.setText(String.valueOf(patient._id));
            });
    choiceBoxAppointment
        .disableProperty()
        .bind(choiceBoxPatientAppointment.valueProperty().isEqualTo(""));

    radioBtnIsVisitor
        .onMouseClickedProperty()
        .addListener(
            (event) -> {
              // TODO: Change slots showing to consultation.
            });

    loadExpertise();
    refreshPatientData();

    choiceBoxPatient.disableProperty().bind(radioBtnIsVisitor.selectedProperty());
    labelPatientID.disableProperty().bind(radioBtnIsVisitor.selectedProperty());

    // .visibleProperty().bindBidirectional(choiceBoxSearchBy.valueProperty()equals());
    btnSearch.setOnMouseClicked(mouseEvent -> search());
    btnPatientRegistration.setOnMouseClicked(event -> register());
  }

  private void loadExpertise() {
    for (var v : Physician.Expertise.values())
      choiceBoxExpertise.getItems().add(v.name().toLowerCase());
  }

  public void register() {
    labelPatientRegisterErrMsg.setText("");
    var name = textPatientRegisterName.getText().trim();
    var tel = textPatientRegisterTel.getText().trim();
    var addr = textPatientRegisterAddr.getText().trim();

    if (name.equals("") || tel.equals("") || addr.equals("")) {
      labelPatientRegisterErrMsg.setText("Please fill in all fields.");
      return;
    }

    var patient = new Patient(name, tel, addr);
    if (Patient.registerPatient(patient)) {
      textPatientRegisterName.setText("");
      textPatientRegisterTel.setText("");
      textPatientRegisterAddr.setText("");

      labelPatientRegisterErrMsg.setText("Successfully registered.");
      refreshPatientData();
    }
  }

  public void refreshPatientData() {
    choiceBoxPatient.getItems().clear();
    choiceBoxPatientAppointment.getItems().clear();

    for (var patient : Patient.getPatients()) {
      choiceBoxPatient.getItems().add("%s".formatted(patient.name));
      choiceBoxPatientAppointment.getItems().add("%s".formatted(patient.name));
    }
  }

  public void search() {
    labelErrMsg.setText("");
    var searchByName = choiceBoxSearchBy.getValue().equals("Name");

    if (choiceBoxPatient.getValue() == null) {
      labelErrMsg.setText("Please Select Patient!");
      return;
    }

    var patient = Patient.findPatient(choiceBoxPatient.getValue());
    var keyword = textFieldKeyword.getText().trim();
    var expertise = choiceBoxExpertise.getValue();



    if (searchByName) {
        if (keyword.equals("")) {
            labelErrMsg.setText("Please Enter Search Keyword!");
            return;
        }
      System.out.printf("Search by name %s - %s\n", keyword, patient);

      var physicians = Physician.getPhysiciansByName(keyword.strip());
      System.out.printf("Physicians: %s\n", physicians);

      // Done： get target physician's all slot and availability
      physicians.forEach(
          physician -> {
            // Get all dates which the physician is working,
            // based on the `workingDays` of the physician.
            // e.g. `[Tue, Fri]` -> `[May-4, May-7, May-11, May-14, May-18, May-21, May-25, May-28]`
            var workingDays = physician.getWorkingDates();
            System.out.printf("Workdays: %s\n", workingDays.size());
            workingDays.forEach(
                day -> {
                  // System.out.println(day.get(Calendar.DAY_OF_MONTH));
                });

            workingDays.forEach(
                calendar -> {
                  // var appointments = new ArrayList<Appointment>();
                  // Get 4 slots on given day.
                  // e.g. `May-4` -> `[May-4-10:00, May-4-12:00, May-4-14:00, May-4-16:00]`
                  var slots = Helper.getSlots(calendar);
                  System.out.println(slots.size());

                  slots.forEach(
                      slot -> {
                        var resAppointment = Appointment.getAppointment(slot, physician);
                        System.out.println(resAppointment);
                        // if (physician.consultHours[0] == slot.get(Calendar.DAY_OF_WEEK)
                        //     && physician.consultHours[1] == slot.get(Calendar.HOUR_OF_DAY))
                        if (resAppointment == null) {

                          physician.treatment.forEach(
                              treatment -> {
                                var res = new StringBuilder();
                                res.append("%02d".formatted(calendar.get(Calendar.MONTH) + 1))
                                    .append("-")
                                    .append("%02d".formatted(calendar.get(Calendar.DAY_OF_MONTH)))
                                    .append("\t")
                                    .append(slot.get(Calendar.HOUR_OF_DAY))
                                    .append(":00\t")
                                    .append(physician.name)
                                    .append("\tRoom-")
                                    .append(physician.room.roomName)
                                    .append("\t")
                                    .append(treatment.toString());

                                System.out.println(res);
                                this.listViewResult.getItems().add(res.toString());
                              });
                        }
                      });
                });
          });

    } else if (expertise != null) {
      System.out.printf("Search by expertise %s - %s%n", expertise);
      // TODO： get target expertise's all slot and availability.
      // TODO: expertise -> treatments mapping

    } else {
      labelErrMsg.setText("Fill in all fields!!");
    }
    // System.out.println(choiceBoxExpertise.getValue());
    // if (searchBy)
  }
}
