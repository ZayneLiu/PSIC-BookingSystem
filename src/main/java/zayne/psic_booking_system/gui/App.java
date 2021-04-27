package zayne.psic_booking_system.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    public static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent fxml = FXMLLoader.load(getClass().getResource("homepage.fxml"));

        scene = new Scene(fxml, 640, 640);
        // Load `app.css` to the scene
        var css = getClass().getResource("app.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
