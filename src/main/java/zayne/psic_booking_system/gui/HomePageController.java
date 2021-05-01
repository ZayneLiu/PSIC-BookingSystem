package zayne.psic_booking_system.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HomePageController {
    public static HomePageController controller;

    public VBox vBoxMain;
    public VBox vBoxPhysician;
    public VBox vBoxPatient;
    public VBox vBoxReport;

    public HBox hBoxData;

    @FXML
    public void initialize() throws IOException {
        // Retrieve and load fxml
        var resPatient = PatientController.class.getResource("patient-subsystem.fxml");
        assert resPatient != null;

        VBox fxmlPatient = FXMLLoader.load(resPatient);
        vBoxPatient.getChildren().addAll(fxmlPatient.getChildren());

        // Retrieve and load fxml
        var resData = PatientController.class.getResource("data.fxml");
        assert resData != null;

        HBox fxmlData = FXMLLoader.load(resData);
        hBoxData.getChildren().addAll(fxmlData.getChildren());

        controller = this;
    }

    public static void switchContent(Parent to) {}
}
