package zayne.psic_booking_system.gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import zayne.psic_booking_system.models.Physician;

public class DataController {
    public VBox appointmentList;
    public VBox patientList;
    public VBox physicianList;

    @FXML
    public void initialize() {
        Physician.getPhysicians()
                .forEach(
                        physician -> {
                            var label = new Label();
                            label.setPrefWidth(Control.USE_COMPUTED_SIZE);
                            label.setTextAlignment(TextAlignment.LEFT);
                            label.setAlignment(Pos.CENTER_LEFT);
                            label.setText(physician.toString());
                            physicianList.getChildren().add(label);
                        });
    }
}
