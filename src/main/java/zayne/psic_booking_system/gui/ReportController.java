package zayne.psic_booking_system.gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import zayne.psic_booking_system.models.Appointment;
import zayne.psic_booking_system.models.Patient;
import zayne.psic_booking_system.models.Visitor;

public class ReportController {
  public TitledPane appointmentPane;
  public VBox appointmentList;
  // public Button generateReport;
  public ChoiceBox<String> choiceBoxPatient;
  public VBox patientAppointmentList;
  public TitledPane patientAppointmentPane;

  public static ReportController controller;
  public TitledPane visitorAppointmentPane;
  public VBox visitorAppointmentList;

  public ReportController() {
    controller = this;
  }

  @FXML
  public void initialize() {
    loadPatients();
    choiceBoxPatient
        .valueProperty()
        .addListener((observable, oldValue, newValue) -> loadAppointmentsForPatient(newValue));
    // loadAppointments();
  }

  public void loadAppointmentsForPatient(String patientName) {
    if (patientName == null) return;
    patientAppointmentList.getChildren().clear();

    var patient = Patient.getPatient(patientName.split("_")[0]);

    patientAppointmentPane.setText(
        "Appointments for %s (%d appointments)"
            .formatted(patient.name, patient.getBookedAppointments().size()));

    patient
        .getBookedAppointments()
        .forEach(
            appointment -> {
              var label = new Label();
              // label.setPrefWidth(Control.USE_COMPUTED_SIZE);
              label.setTextAlignment(TextAlignment.LEFT);
              label.setAlignment(Pos.CENTER_LEFT);
              label.setText(appointment.getStat());
              patientAppointmentList.getChildren().add(label);
              patientAppointmentList.getChildren().add(new Separator());
            });
  }

  public void loadAppointments() {
    appointmentPane.setText(
        "All Appointments (%d appointments)".formatted(Appointment.getAppointments().size()));
    Appointment.getAppointments()
        .forEach(
            appointment -> {
              var label = new Label();
              // label.setPrefWidth(Control.USE_COMPUTED_SIZE);
              label.setTextAlignment(TextAlignment.LEFT);
              label.setAlignment(Pos.CENTER_LEFT);
              label.setText(appointment.getStat());
              appointmentList.getChildren().add(label);
              appointmentList.getChildren().add(new Separator());
            });
  }

  public void refreshData() {
    this.patientAppointmentList.getChildren().clear();
    this.patientAppointmentPane.setText("Patient Appointments");
    this.choiceBoxPatient.getItems().clear();
    loadPatients();

    this.appointmentList.getChildren().clear();
    loadAppointments();

    this.visitorAppointmentList.getChildren().clear();
    loadVisitorAppointments();
  }

  private void loadVisitorAppointments() {
    var visitorAppointments = Visitor.getVisitorAppointments();
    visitorAppointmentPane.setText(
        "Visitor Appointments (%d appointments)".formatted(visitorAppointments.size()));
    visitorAppointments.forEach(
        appointment -> {
          var label = new Label();
          // label.setPrefWidth(Control.USE_COMPUTED_SIZE);
          label.setTextAlignment(TextAlignment.LEFT);
          label.setAlignment(Pos.CENTER_LEFT);
          label.setText(appointment.getStat());
          visitorAppointmentList.getChildren().add(label);
          visitorAppointmentList.getChildren().add(new Separator());
        });
  }

  private void loadPatients() {
    Patient.getPatients()
        .forEach(
            patient -> {
              choiceBoxPatient.getItems().add(patient.name + "_" + patient._id);
            });
  }
}
