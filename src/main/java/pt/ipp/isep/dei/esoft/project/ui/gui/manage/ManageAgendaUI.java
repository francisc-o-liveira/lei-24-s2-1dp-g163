package pt.ipp.isep.dei.esoft.project.ui.gui.manage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.DetailsEntryAgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class ManageAgendaUI  implements Initializable{
    public Stage stage = LoginUI.getMainStage();
    public Stage stageToViewDetails = new Stage();

    public AuthenticationController ctrlAuth;
    public DetailsEntryAgendaController ctrlEntry;
    private VBox view;
    private YearMonth currentYearMonth;
    private List<Entry> entries;
    @FXML
    private AnchorPane calendarAnchorPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctrlAuth = new AuthenticationController();
        ctrlEntry=new DetailsEntryAgendaController();
        view = new VBox();
        view.setAlignment(Pos.CENTER);
        view.setSpacing(20);
        view.getStyleClass().add("outline");
        calendarAnchorPane.getChildren().add(getView());
        ctrlAuth = new AuthenticationController();
        ctrlEntry=new DetailsEntryAgendaController();
        this.entries=ctrlEntry.getList();
        currentYearMonth = YearMonth.now();
        drawCalendar(currentYearMonth);
    }

    public VBox getView() {
        return view;
    }

    private void drawCalendar(YearMonth yearMonth) {
        view.getChildren().clear();
        Label header = new Label(yearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + yearMonth.getYear());
        header.getStyleClass().add("header");

        Button previousMonthButton = new Button("<");
        previousMonthButton.setOnAction(e -> changeMonth(-1));
        previousMonthButton.getStyleClass().add("prev-button");
        Button nextMonthButton = new Button(">");
        nextMonthButton.setOnAction(e -> changeMonth(1));
        nextMonthButton.getStyleClass().add("next-button");

        HBox headerBox = new HBox(previousMonthButton, header, nextMonthButton);
        headerBox.setAlignment(Pos.CENTER);

        GridPane calendarGrid = new GridPane();
        calendarGrid.setAlignment(Pos.CENTER);
        calendarGrid.setGridLinesVisible(false);
        calendarGrid.getStyleClass().add("calendar");

        String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        for (int i = 0; i < dayNames.length; i++) {
            Label dayLabel = new Label(dayNames[i]);
            dayLabel.getStyleClass().add("day-names");
            calendarGrid.add(dayLabel, i, 0);
        }

        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue() % 7;
        int daysInMonth = yearMonth.lengthOfMonth();

        int row = 1;
        int col = dayOfWeek;

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = yearMonth.atDay(day);
            VBox dayBox = new VBox();
            dayBox.setAlignment(Pos.TOP_LEFT);
            dayBox.getStyleClass().add("day-cell");
            dayBox.setPrefWidth(130);
            dayBox.setPrefHeight(70);
            dayBox.getChildren().add(new Label(String.valueOf(day)));

            List<Entry> dayEntries = entries.stream()
                    .filter(entry -> entry.getStartDate() != null && LocalDate.of(entry.getStartDate().getYear(), entry.getStartDate().getMonth(), entry.getStartDate().getDay()).equals(date))
                    .collect(Collectors.toList());

            for (Entry entry : dayEntries) {
                Label entryLabel = new Label(entry.getDescription()); //needs to be ctrlEntry.getDescription(entry)
                entryLabel.getStyleClass().add("event-label");

                switch (entry.getStatus()) { //i know it is with the controller
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
                dayBox.getChildren().add(entryLabel);
            }

            calendarGrid.add(dayBox, col, row);
            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }

        view.getChildren().addAll(headerBox, calendarGrid);
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

    private void setLabels(Entry entry){
        //TODO: to view details and also edit entries
    }


    private void changeMonth(int months) {
        currentYearMonth = currentYearMonth.plusMonths(months);
        drawCalendar(currentYearMonth);
    }

    @FXML
    private void btnAddEntry(ActionEvent event)throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/Scene_AddEntry.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void reload(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/SceneMenu_GSM.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void doLogout(ActionEvent event) throws IOException{
        Alert popUp= new Alert(Alert.AlertType.CONFIRMATION);

        popUp.setHeaderText("Logging Out");
        popUp.setContentText("Do you wish to log out?");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

        if (popUp.showAndWait().get() == ButtonType.OK) {
            ctrlAuth.doLogout();
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/SceneLogin.fxml"));
            Parent root= fxmlLoader.load();
            Scene scene= new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
