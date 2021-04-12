package zayne.psic_booking_system.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class HomePageController {
    public static HomePageController controller;

    public VBox vBoxMain;
    public VBox vBoxPhysician;
    public VBox vBoxPatient;
    public VBox vBoxReport;

    @FXML
    public void initialize() throws IOException {
        var res =
                Objects.requireNonNull(
                        PatientController.class.getResource("patient-subsystem.fxml"));
        VBox fxml = FXMLLoader.load(res);

        vBoxPatient.getChildren().addAll(fxml.getChildren());
        controller = this;
    }

    public static void switchContent(Parent to) {}
}
