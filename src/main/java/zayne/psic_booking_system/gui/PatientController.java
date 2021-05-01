package zayne.psic_booking_system.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import zayne.psic_booking_system.models.Patient;
import zayne.psic_booking_system.models.Physician;

import java.time.DayOfWeek;
import java.time.LocalDate;

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
        datePickerPatient.valueProperty().addListener((observable, oldValue, newValue) -> {

        });

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
                            labelPatientID.setText(String.valueOf(patient._id));
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
        if (searchByName) {
            Physician.findPhysiciansByName(textFieldKeyword.getText().trim());
        }

        var keyword = textFieldKeyword.getText().trim();
        var expertise = choiceBoxExpertise.getValue();
        var date = datePickerPatient.getValue();

        if (searchByName && !keyword.equals("") && date != null) {
            System.out.printf("Search by name %s - %s%n", keyword, date);

        } else if (!searchByName && expertise != null && date != null) {
            System.out.printf("Search by expertise %s - %s%n", expertise, date);

        } else {
            labelErrMsg.setText("Fill in all fields!!");
        }
        // System.out.println(choiceBoxExpertise.getValue());
        // if (searchBy)

        /* TODO: Display result.
         *  Physician name
         *  Date & Time
         *  Room
         *  Treatment
         * */
    }
}
