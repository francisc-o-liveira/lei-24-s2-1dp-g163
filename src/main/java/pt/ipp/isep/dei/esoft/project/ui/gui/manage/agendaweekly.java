package pt.ipp.isep.dei.esoft.project.ui.gui.manage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.DetailsEntryAgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class agendaweekly implements Initializable {
    public Stage stage = LoginUI.getMainStage();
    public Stage stageToViewDetails = new Stage();

    public AuthenticationController ctrlAuth;
    public DetailsEntryAgendaController ctrlEntry;
    private VBox view;
    private LocalDate currentStartDate;
    private List<Entry> entries;

    @FXML
    private AnchorPane calendarAnchorPane1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctrlAuth = new AuthenticationController();
        ctrlEntry = new DetailsEntryAgendaController();
        view = new VBox();
        view.setAlignment(Pos.CENTER);
        view.setSpacing(20);
        view.getStyleClass().add("outline");
        calendarAnchorPane1.getChildren().add(getView());
        ctrlAuth = new AuthenticationController();
        ctrlEntry = new DetailsEntryAgendaController();
        this.entries = ctrlEntry.getList();
        currentStartDate = LocalDate.now().with(DayOfWeek.MONDAY);
        drawWeeklyCalendar(currentStartDate);
    }

    public VBox getView() {
        return view;
    }

    private void drawWeeklyCalendar(LocalDate startDate) {
        view.getChildren().clear();
        Label header = new Label("Week of " + startDate);
        header.getStyleClass().add("header");

        Button previousWeekButton = new Button("<");
        previousWeekButton.setOnAction(e -> changeWeek(-1));
        previousWeekButton.getStyleClass().add("prev-button");
        Button nextWeekButton = new Button(">");
        nextWeekButton.setOnAction(e -> changeWeek(1));
        nextWeekButton.getStyleClass().add("next-button");

        HBox headerBox = new HBox(previousWeekButton, header, nextWeekButton);
        headerBox.setAlignment(Pos.CENTER);

        GridPane calendarGrid = new GridPane();
        calendarGrid.setAlignment(Pos.BOTTOM_CENTER);
        calendarGrid.setGridLinesVisible(false);
        calendarGrid.getStyleClass().add("calendar");

        String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < dayNames.length; i++) {
            Label dayLabel = new Label(dayNames[i]);
            dayLabel.getStyleClass().add("day-names");
            calendarGrid.add(dayLabel, i, 0);
        }

        LocalDate date = startDate;
        for (int col = 0; col < 7; col++) {
            VBox dayBox = new VBox();
            dayBox.setAlignment(Pos.TOP_LEFT);
            dayBox.getStyleClass().add("day-cell");
            dayBox.setPrefWidth(130);
            dayBox.setPrefHeight(70);
            dayBox.getChildren().add(new Label(String.valueOf(date.getDayOfMonth())));

            LocalDate finalDate = date;
            List<Entry> dayEntries = entries.stream()
                    .filter(entry -> entry.getStartDate() != null && LocalDate.of(entry.getStartDate().getYear(), entry.getStartDate().getMonth(), entry.getStartDate().getDay()).equals(finalDate))
                    .collect(Collectors.toList());

            if (dayEntries != null) {
                introduceDates(dayEntries, dayBox.getPrefHeight(), dayBox.getPrefWidth(), dayBox);
            }
            calendarGrid.add(dayBox, col, 1);
            date = date.plusDays(1);
        }

        view.getChildren().addAll(headerBox, calendarGrid);
    }

    public void introduceDates(List<Entry> dayEntries, double rectangleHeight, double rectangleWidth, VBox dayBox) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < dayEntries.size(); k++) {
            if (k >= 2) {
                Label moreActivities = new Label("show more");
                calendarActivityBox.getChildren().add(moreActivities);

                ContextMenu contextMenu = new ContextMenu();
                contextMenu.getStyleClass().add("context-menu");
                for (int i = 2; i < dayEntries.size(); i++) {
                    Entry entryDisplay = dayEntries.get(i);
                    MenuItem menuItem = new MenuItem(entryDisplay.getTitle());
                    menuItem.getStyleClass().add("menu-item");
                    menuItem.setOnAction(event -> {
                        try {
                            showEntryDetails(entryDisplay);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    contextMenu.getItems().add(menuItem);
                }

                moreActivities.setOnMouseClicked(mouseEvent -> {
                    contextMenu.show(moreActivities, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                });
                break;
            }
            Entry entry = dayEntries.get(k);
            Label entryLabel = new Label(entry.getTitle());
            entryLabel.getStyleClass().add("event-label");

            switch (entry.getStatus()) {
                case Postponed:
                    entryLabel.getStyleClass().add("status-postponed");
                    break;
                case Planned:
                    entryLabel.getStyleClass().add("status-planned");
                    break;
                case Canceled:
                    entryLabel.getStyleClass().add("status-canceled");
                    break;
                case Done:
                    entryLabel.getStyleClass().add("status-done");
                    break;
            }

            entryLabel.setOnMouseClicked(event -> {
                try {
                    showEntryDetails(entry);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            calendarActivityBox.getChildren().add(entryLabel);
        }
        calendarActivityBox.getStyleClass().add("show-more");
        dayBox.getChildren().add(calendarActivityBox);
    }

    private void showEntryDetails(Entry entry) throws IOException {
        System.out.print("to be implemented");
        /*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_ViewDetailsEntry.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stageToViewDetails.setScene(scene);
        stageToViewDetails.show();
        setLabels(entry); //should be done another ui for this*/
    }

    private void setLabels(Entry entry) {
        // TODO: to view details and also edit entries
    }

    private void changeWeek(int weeks) {
        currentStartDate = currentStartDate.plusWeeks(weeks);
        drawWeeklyCalendar(currentStartDate);
    }

    @FXML
    private void btnAddEntry(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_AddEntry.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void reload(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_GSM.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void doLogout(ActionEvent event) throws IOException {
        Alert popUp = new Alert(Alert.AlertType.CONFIRMATION);

        popUp.setHeaderText("Logging Out");
        popUp.setContentText("Do you wish to log out?");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

        if (popUp.showAndWait().get() == ButtonType.OK) {
            ctrlAuth.doLogout();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneLogin.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}