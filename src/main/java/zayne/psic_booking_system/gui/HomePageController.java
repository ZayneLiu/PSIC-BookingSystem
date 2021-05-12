package zayne.psic_booking_system.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
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

  public static void switchContent(Parent to) {}

  @FXML
  public void initialize() throws IOException {
    // Retrieve fxml
    var resPatient = PatientController.class.getResource("patient-subsystem.fxml");
    assert resPatient != null;
    // Load fxml
    VBox fxmlPatient = FXMLLoader.load(resPatient);
    vBoxPatient.getChildren().addAll(fxmlPatient.getChildren());

    // Retrieve fxml
    var resData = PatientController.class.getResource("data.fxml");
    assert resData != null;
    // load fxml
    HBox fxmlData = FXMLLoader.load(resData);
    hBoxData.getChildren().addAll(fxmlData.getChildren());

    var resReport = ReportController.class.getResource("report-subsystem.fxml");
    assert resReport != null;
    VBox fxmlReport = FXMLLoader.load(resReport);
    vBoxReport.getChildren().addAll(fxmlReport.getChildren());

    controller = this;
  }
}
