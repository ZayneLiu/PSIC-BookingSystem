package zayne.psic_booking_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import zayne.psic_booking_system.models.Appointment;
import zayne.psic_booking_system.models.Patient;
import zayne.psic_booking_system.models.Physician;

import java.util.Calendar;

public class PatientController {

  private static final String DEFAULT_EXPERTISE = "Enter Keyword";
  private static final String DEFAULT_KEYWORD = "Select Expertise";
  public Label labelErrMsg;
  public Label labelPatientID;
  public Label labelPatientRegErrMsg;
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
  // public DatePicker datePickerPatient;

  /* method `initialize()` is called after the constructor. */
  @FXML
  public void initialize() {
    choiceBoxSearchBy.getItems().add("Name");
    choiceBoxSearchBy.getItems().add("Expertise");
    choiceBoxSearchBy.setValue("Name");

    choiceBoxExpertise.setDisable(true);
    radioBtnIsPatient.setSelected(true);

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
            });

    choiceBoxPatient
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              var patient = Patient.getPatient(newValue);
              labelPatientID.setText("Patient ID: " + patient._id);
            });

    radioBtnIsVisitor
        .onMouseClickedProperty()
        .addListener(
            (event) -> {
              // TODO: Change slots showing to consultation.
            });

    btnSearch.setOnMouseClicked(mouseEvent -> search());
    btnBookAppointment.setOnMouseClicked(mouseEvent -> book());
    btnPatientRegistration.setOnMouseClicked(event -> register());
  }

  private void setupPropertyBindings() {
    choiceBoxAppointment
        .disableProperty()
        .bind(choiceBoxPatientAppointment.valueProperty().isEqualTo(""));

    choiceBoxPatient.disableProperty().bind(radioBtnIsVisitor.selectedProperty());
    labelPatientID.disableProperty().bind(radioBtnIsVisitor.selectedProperty());
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
    listViewResult.getItems().clear();
    var searchByName = choiceBoxSearchBy.getValue().equals("Name");

    if (choiceBoxPatient.getValue() == null) {
      labelErrMsg.setText("Please Select Patient!");
      return;
    }

    var patient = Patient.getPatient(choiceBoxPatient.getValue());
    var keyword = textFieldKeyword.getText().trim();
    var expertise = choiceBoxExpertise.getValue();

    if (searchByName) {
      if (keyword.equals("")) {
        labelErrMsg.setText("Please Enter Search Keyword!");
        return;
      }

      var physicians = Physician.getPhysiciansByName(keyword.strip());

      // Done： get target physician's all slot and availability
      for (Physician physician : physicians) {

        var availableAppointments = physician.getAvailableAppointments();

        // Add available appointment to search result view.
        for (String appointment : availableAppointments) {
          this.listViewResult.getItems().add(appointment);
        }
      }
      var searchResult =
          physicians.size() == 0
              ? "No results found."
              : "%d physicians %d slots."
                  .formatted(physicians.size(), listViewResult.getItems().size());

      labelErrMsg.setText(searchResult);

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
