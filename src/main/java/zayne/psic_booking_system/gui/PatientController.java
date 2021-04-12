package zayne.psic_booking_system.gui;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;

import java.util.Properties;

public class PatientController {

    private static final String DEFAULT_EXPERTISE = "Enter Keyword";
    private static final String DEFAULT_KEYWORD = "Select Expertise";
    public ChoiceBox<String> choiceBoxSearchBy;
    public ChoiceBox<String> choiceBoxExpertise;
    public TextField textFieldKeyword;
    public Button btnSearch;

    /* method `initialize()` is called after the constructor. */
    @FXML
    public void initialize() {
        choiceBoxSearchBy.getItems().add("Name");
        choiceBoxSearchBy.getItems().add("Expertise");
        choiceBoxSearchBy.setValue("Name");

        choiceBoxSearchBy
                .valueProperty()
                .addListener(
                        new ChangeListener<String>() {
                            @Override
                            public void changed(
                                    ObservableValue<? extends String> observable,
                                    String oldValue,
                                    String newValue) {
                                choiceBoxExpertise.setDisable(newValue.equals("Name"));
                                // choiceBoxExpertise.setValue(DEFAULT_EXPERTISE);
                                textFieldKeyword.setDisable(newValue.equals("Expertise"));
                                // textFieldKeyword.setText(DEFAULT_KEYWORD);
                            }
                        });

        // .visibleProperty().bindBidirectional(choiceBoxSearchBy.valueProperty()equals());
        btnSearch.setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        // TODO: Search by name.

                        // TODO: Search by expertise.
                        System.out.println();

                        /* TODO: Display result.
                         *  Physician name
                         *  Date & Time
                         *  Room
                         *  Treatment
                         * */
                    }
                });
    }
}
