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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.core.application.controller.ViewTaskListAssignedCollabController;
import pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * UI controller class for managing the task list of a collaborator.
 * It handles the display and interaction with the tasks assigned to the collaborator.
 */
public class ViewTaskListCollaboratorUI {

    /**
     * Stage for displaying the main window.
     */
    public Stage stage = LoginUI.getMainStage();

    /**
     * Controller for handling authentication.
     */
    public AuthenticationController ctrlAuth;

    /**
     * Controller for handling the viewing of tasks assigned to a collaborator.
     */
    public ViewTaskListAssignedCollabController ctrl;

    /**
     * Observable list of tasks assigned to the collaborator.
     */
    private ObservableList<EntryDto> tasksOfCollab= FXCollections.observableArrayList();

    /**
     * Filtered list of tasks for applying filters.
     */
    private FilteredList<EntryDto> filteredData;

    /**
     * Selected entry from the table.
     */
    private EntryDto entrySelected;

    /**
     * List of tasks assigned to the collaborator.
     */
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

    /**
     * Constructor for initializing controllers and task list.
     */
    public ViewTaskListCollaboratorUI(){
        ctrlAuth= AuthenticationController.getInstance();
        tasksForCollab=new ArrayList<>();
        ctrl= new ViewTaskListAssignedCollabController();
    }

    /**
     * Sets the tasks in the table view for the collaborator.
     *
     * @param tasksForCollaborator List of tasks assigned to the collaborator.
     */
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

        for(EntryDto entry : tasksForCollab){
            tasksOfCollab.add(entry);
        }

        tableTasks.setItems(tasksOfCollab);
        filteredData = new FilteredList<>(tasksOfCollab, p -> true);
        tableTasks.setItems(filteredData);
    }

    /**
     * Handles the action of marking a task as complete.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    private void btnComplete(ActionEvent event) {
        entrySelected=tableTasks.getSelectionModel().getSelectedItem();
        try{
            Date completedDate=Date.atualDate();
            ctrl.assignEntryCompleted(entrySelected,completedDate);
            popUp().show();
        } catch (Exception e) {
            popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    /**
     * Filters the tasks based on the selected checkboxes.
     *
     * @param event The action event triggered by the checkbox change.
     */
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

    /**
     * Reloads the task list UI.
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    public void reload(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/collaborator/Scene_TaskListCollaborator.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the logout action.
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If the FXML file cannot be loaded.
     */
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

    /**
     * Creates a pop-up alert for verification errors.
     *
     * @param alertType The type of alert.
     * @param messages  The error message.
     * @return The alert.
     */
    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }

    /**
     * Creates a pop-up alert for successful task completion.
     *
     * @return The alert.
     */
    private Alert popUp(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Tasks done!");

        return alert;
    }
}
