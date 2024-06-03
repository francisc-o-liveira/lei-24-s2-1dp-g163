package pt.ipp.isep.dei.esoft.project.ui.gui.collaborator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.ViewTaskListAssignedCollabController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewTaskListCollaboratorUI {

    public Stage stage = LoginUI.getMainStage();
    public AuthenticationController ctrlAuth;
    public ViewTaskListAssignedCollabController ctrl;
    private ObservableList<EntryDto> tasksOfCollab= FXCollections.observableArrayList();
    private FilteredList<EntryDto> filteredData;
    private List<EntryDto> entriesSelected;
    private List<EntryDto> tasksForCollab;
    @FXML
    private TableView<EntryDto> tableTasks;

    @FXML
    private TableColumn<EntryDto, Date> taskDate;

    @FXML
    private TableColumn<EntryDto, GreenSpaceDto> taskGreenSpace;

    @FXML
    private TableColumn<EntryDto, String> taskName;

    @FXML
    private TableColumn<EntryDto, Boolean> taskSelect;

    @FXML
    private TableColumn<EntryDto, EntryState> taskStatus;

    @FXML
    private CheckBox assignedCheck;

    @FXML
    private CheckBox canceledCheck;

    @FXML
    private CheckBox doneCheck;

    @FXML
    private CheckBox plannedCheck;

    @FXML
    private CheckBox postponedCheck;

    public ViewTaskListCollaboratorUI(){
        ctrlAuth=new AuthenticationController();
        entriesSelected =new ArrayList<>();
        tasksForCollab=new ArrayList<>();
        ctrl=new ViewTaskListAssignedCollabController();
    }

    public void setTableTasks(List<EntryDto> tasksForCollaborator){
        tasksForCollab=tasksForCollaborator;
        Comparator<EntryDto> sortingTasks=new Comparator<EntryDto>() {
            @Override
            public int compare(EntryDto o1, EntryDto o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        };

        Collections.sort(tasksForCollab,sortingTasks);
        taskDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        taskGreenSpace.setCellValueFactory(new PropertyValueFactory<>("greenSpace"));
        taskName.setCellValueFactory(new PropertyValueFactory<>("title"));
        taskStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        taskSelect.setCellFactory(CheckBoxTableCell.forTableColumn(taskSelect));

        for(EntryDto entry : tasksForCollab){
            tasksOfCollab.add(entry);
            entry.setSelectingCollab(false);
        }

        tableTasks.setItems(tasksOfCollab);
        filteredData = new FilteredList<>(tasksOfCollab, p -> true);
        tableTasks.setItems(filteredData);
    }

    @FXML
    private void btnComplete(ActionEvent event) {
            try{
                getTasksDone();
                ZonedDateTime timeAndDate = ZonedDateTime.now();
                Date completedDate=new Date(timeAndDate.getYear(),timeAndDate.getMonthValue(),timeAndDate.getDayOfMonth());
                for(EntryDto entry : entriesSelected){
                    ctrl.assignEntryCompleted(entry,completedDate);
                }
                popUp().show();
            } catch (Exception e) {
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
            }
    }

    private void getTasksDone(){
        for (EntryDto entry : tasksForCollab) {
            if (entry.selectedCollab().get()) {
                entriesSelected.add(entry);
            }
        }
    }

    @FXML
    public void filterTasks(ActionEvent event){
        filteredData.setPredicate(task -> {
            boolean showAssigned = assignedCheck.isSelected();
            boolean showCanceled = canceledCheck.isSelected();
            boolean showDone = doneCheck.isSelected();
            boolean showPlanned = plannedCheck.isSelected();
            boolean showPostponed = postponedCheck.isSelected();

            if (!showAssigned && !showCanceled && !showDone && !showPlanned && !showPostponed) {
                return true;
            }

            boolean matchesAssigned = showAssigned && task.getStatus().getState() == EntryState.State.Assigned;
            boolean matchesCanceled = showCanceled && task.getStatus().getState() == EntryState.State.Canceled;
            boolean matchesDone = showDone && task.getStatus().getState() == EntryState.State.Done;
            boolean matchesPlanned = showPlanned && task.getStatus().getState() == EntryState.State.Planned;
            boolean matchesPostponed = showPostponed && task.getStatus().getState() == EntryState.State.Postponed;

            return matchesAssigned || matchesCanceled || matchesDone || matchesPlanned || matchesPostponed;
        });
    }


    @FXML
    public void reload(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/collaborator/SceneMenu_Collaborator.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void doLogout(ActionEvent event) throws IOException {
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

    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }

    private Alert popUp(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Tasks done!");

        return alert;
    }
}
