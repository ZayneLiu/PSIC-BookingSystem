package zayne.psic_booking_system.gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
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

  public static DataController controller;

  @FXML
  public void initialize() {
    // TODO: Observable Pattern
    controller = this;

    loadPhysicians();

    loadPatients();

    loadAppointments();
  }

  public void loadPhysicians() {
    physicianPane.setText("Physicians (%d physicians)".formatted(Physician.getPhysicians().size()));
    Physician.getPhysicians()
        .forEach(
            physician -> {
              var label = new Label();
              label.setPrefWidth(Control.USE_COMPUTED_SIZE);
              label.setTextAlignment(TextAlignment.LEFT);
              label.setAlignment(Pos.CENTER_LEFT);
              label.setText(physician.getStat());
              physicianList.getChildren().add(label);
              physicianList.getChildren().add(new Separator());
            });
  }

  public void loadAppointments() {
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
              appointmentList.getChildren().add(label);
              appointmentList.getChildren().add(new Separator());
            });
  }

  public void loadPatients() {
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
              patientList.getChildren().add(new Separator());
            });
  }

  public void refreshData() {
    this.physicianList.getChildren().clear();
    loadPhysicians();
    this.patientList.getChildren().clear();
    loadPatients();
    this.appointmentList.getChildren().clear();
    loadAppointments();
  }
}
