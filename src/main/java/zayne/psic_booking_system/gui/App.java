package zayne.psic_booking_system.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import zayne.psic_booking_system.utils.DataLoader;

import java.util.Objects;

public class App extends Application {

  public static Scene scene;

  public App() {
    DataLoader.load();
  }

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    var res = getClass().getResource("homepage.fxml");
    Parent fxml = FXMLLoader.load(Objects.requireNonNull(res));

    scene = new Scene(fxml, 640, 700);
    // Load `app.css` to the scene
    var resCss = getClass().getResource("app.css");
    var css = Objects.requireNonNull(resCss).toExternalForm();
    scene.getStylesheets().add(css);

    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
