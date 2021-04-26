package zayne.psic_booking_system.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import zayne.psic_booking_system.models.Physician;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class PatientController {

    private static final String DEFAULT_EXPERTISE = "Enter Keyword";
    private static final String DEFAULT_KEYWORD = "Select Expertise";
    public ChoiceBox<String> choiceBoxSearchBy;
    public ChoiceBox<String> choiceBoxExpertise;
    public TextField textFieldKeyword;
    public Button btnSearch;
    public DatePicker datePickerPatient;
    public Button btnBookAppointment;
    public Label labelErrMsg;
    public ListView<String> listViewResult;

    /* method `initialize()` is called after the constructor. */
    @FXML
    public void initialize() {
        choiceBoxSearchBy.getItems().add("Name");
        choiceBoxSearchBy.getItems().add("Expertise");
        choiceBoxSearchBy.setValue("Name");

        choiceBoxExpertise.setDisable(true);

        var minDate = LocalDate.of(2021, 5, 3);
        var maxDate = LocalDate.of(2021, 5, 30);
        datePickerPatient.setDayCellFactory(
                d ->
                        new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                setDisable(
                                        item.isBefore(minDate)
                                                || item.isAfter(maxDate)
                                                || item.getDayOfWeek() == DayOfWeek.SATURDAY
                                                || item.getDayOfWeek() == DayOfWeek.SUNDAY);
                            }
                        });

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

        for (var v : Physician.Expertise.values()) {

            choiceBoxExpertise.getItems().add(v.name().toLowerCase());
        }

        // .visibleProperty().bindBidirectional(choiceBoxSearchBy.valueProperty()equals());
        btnSearch.setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        labelErrMsg.setText("");

                        // TODO: Search by name.
                        var searchByName = choiceBoxSearchBy.getValue().equals("Name");
                        if (searchByName) {
                            Physician.FindPhysiciansByName(textFieldKeyword.getText().trim());
                        }

                        var keyword = textFieldKeyword.getText().trim();
                        var expertise = choiceBoxExpertise.getValue();
                        var date = datePickerPatient.getValue();

                        if (searchByName && !keyword.equals("") && date != null) {
                            System.out.printf("Search by name %s - %s%n", keyword, date);

                        } else if (!searchByName && expertise != null && date != null) {
                            System.out.printf("Search by expertise %s - %s%n", expertise, date);

                        } else {
                            labelErrMsg.setText("Fill in all fields!!");
                        }
                        // System.out.println(choiceBoxExpertise.getValue());
                        // if (searchBy)

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
