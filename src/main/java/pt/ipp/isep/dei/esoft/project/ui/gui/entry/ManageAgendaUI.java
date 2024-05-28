package pt.ipp.isep.dei.esoft.project.ui.gui.entry;

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
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
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
    private VBox viewWeek;
    private YearMonth currentYearMonth;
    private LocalDate currentStartDate;
    private List<Entry> entries;
    @FXML
    private AnchorPane calendarAnchorPane;
    @FXML
    private AnchorPane weeklyViewAnchorPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctrlAuth = new AuthenticationController();
        ctrlEntry=new DetailsEntryAgendaController();
        view = new VBox();
        viewWeek=new VBox();
        view.setAlignment(Pos.CENTER);
        view.setSpacing(20);
        view.getStyleClass().add("outline");
        viewWeek.setAlignment(Pos.CENTER);
        viewWeek.setSpacing(20);
        viewWeek.getStyleClass().add("outline");
        calendarAnchorPane.getChildren().add(getView());
        weeklyViewAnchorPane.getChildren().add(getViewWeekly());
        ctrlAuth = new AuthenticationController();
        ctrlEntry=new DetailsEntryAgendaController();
        //this.entries=ctrlEntry.getList();
        currentYearMonth = YearMonth.now();
        drawCalendar(currentYearMonth);
        currentStartDate = LocalDate.now().with(DayOfWeek.MONDAY);
        drawWeeklyCalendar(currentStartDate);
    }

    public VBox getView() {
        return view;
    }

    public VBox getViewWeekly(){
        return viewWeek;
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

            if(dayEntries != null){
                introduceDates(dayEntries,dayBox.getPrefHeight(), dayBox.getPrefWidth(), dayBox);
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

    private void drawWeeklyCalendar(LocalDate startDate) {
        viewWeek.getChildren().clear();
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
            dayBox.setPrefHeight(200);
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

        viewWeek.getChildren().addAll(headerBox, calendarGrid);
    }

    public void introduceDates(List<Entry> dayEntries, double rectangleHeight, double rectangleWidth, VBox dayBox) {
        VBox calendarActivityBox = new VBox();
        calendarActivityBox.setSpacing(5);
        for (int k = 0; k < dayEntries.size(); k++) {
            if (k >= 2) {
                Label moreActivities = new Label("show more...");
                calendarActivityBox.getChildren().add(moreActivities);

                ContextMenu contextMenu = new ContextMenu();
                contextMenu.getStyleClass().add("context-menu");
                for(int i=2; i< dayEntries.size(); i++){
                    Entry entryDisplay=dayEntries.get(i);
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
            String eventBlock=String.format(entry.getStartDate() + "\n" + entry.getTitle());
            Label entryLabel = new Label(eventBlock);
            entryLabel.setPrefHeight(50);
            entryLabel.getStyleClass().add("event-label");

            /*switch (entry.getStatus()) {
                case EntryState.State.Postponed:
                    entryLabel.getStyleClass().add("status-postponed");
                    break;
                case EntryState.State.Planned:
                    entryLabel.getStyleClass().add("status-planned");
                    break;
                case EntryState.State.Canceled:
                    entryLabel.getStyleClass().add("status-canceled");
                    break;
                case EntryState.State.Done:
                    entryLabel.getStyleClass().add("status-done");
                    break;
            }*/

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

    private void setLabels(Entry entry){
        //TODO: to view details and also edit entries
    }


    private void changeMonth(int months) {
        currentYearMonth = currentYearMonth.plusMonths(months);
        drawCalendar(currentYearMonth);
    }

    private void changeWeek(int weeks) {
        currentStartDate = currentStartDate.plusWeeks(weeks);
        drawWeeklyCalendar(currentStartDate);
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
