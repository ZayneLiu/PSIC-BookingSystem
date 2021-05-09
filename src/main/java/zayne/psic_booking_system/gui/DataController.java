package zayne.psic_booking_system.gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import zayne.psic_booking_system.models.Appointment;
import zayne.psic_booking_system.models.Patient;
import zayne.psic_booking_system.models.Physician;

public class DataController {
  public VBox appointmentList;
  public VBox patientList;
  public VBox physicianList;
  public TitledPane patientPane;
  public TitledPane physicianPane;
  public TitledPane appointmentPane;

  @FXML
  public void initialize() {
    // TODO: Observable Pattern

    physicianPane.setText("Physicians (%d physicians)".formatted(Patient.getPatients().size()));

    Physician.getPhysicians()
        .forEach(
            physician -> {
              var label = new Label();
              label.setPrefWidth(Control.USE_COMPUTED_SIZE);
              label.setTextAlignment(TextAlignment.LEFT);
              label.setAlignment(Pos.CENTER_LEFT);
              label.setText(physician.getStat());
              physicianList.getChildren().add(label);
            });

    patientPane.setText("Patients (%d patients)".formatted(Patient.getPatients().size()));

    Patient.getPatients()
        .forEach(
            patient -> {
              var label = new Label();
              // label.setPrefWidth(Control.USE_COMPUTED_SIZE);
              label.setTextAlignment(TextAlignment.LEFT);
              label.setAlignment(Pos.CENTER_LEFT);
              label.setText(patient.getStat());
              patientList.getChildren().add(label);
            });

    appointmentPane.setText(
        "Appointments (%d appointments)".formatted(Appointment.getAppointments().size()));

    Appointment.getAppointments()
        .forEach(
            appointment -> {
              var label = new Label();
              // label.setPrefWidth(Control.USE_COMPUTED_SIZE);
              label.setTextAlignment(TextAlignment.LEFT);
              label.setAlignment(Pos.CENTER_LEFT);
              label.setText(appointment.getStat());
            });
  }
}
