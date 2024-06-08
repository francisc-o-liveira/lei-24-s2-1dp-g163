package pt.ipp.isep.dei.esoft.project.ui.gui.menus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.ViewTaskListAssignedCollabController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.ui.gui.collaborator.ViewTaskListCollaboratorUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the UI for collaborators, allowing them to see their tasks and log out.
 */
public class CollaboratorUI {
    /**
     * The main stage of the application.
     */
    public Stage stage = LoginUI.getMainStage();

    /**
     * The authentication controller instance.
     */
    public AuthenticationController ctrlAuth;

    /**
     * The controller for viewing the task list assigned to the collaborator.
     */
    public ViewTaskListAssignedCollabController ctrl;

    /**
     * List of tasks within the selected dates.
     */
    private List<EntryDto> tasksInThoseDates;

    /**
     * The first date picker for selecting the start date.
     */
    @FXML
    private DatePicker firstDate;

    /**
     * The second date picker for selecting the end date.
     */
    @FXML
    private DatePicker secondDate;

    /**
     * Constructor for the CollaboratorUI class. Initializes the authentication controller,
     * the task list controller, and the list of tasks.
     */
    public CollaboratorUI(){
        ctrlAuth=AuthenticationController.getInstance();
        ctrl=new ViewTaskListAssignedCollabController();
        tasksInThoseDates=new ArrayList<>();
    }

    /**
     * Retrieves the tasks assigned to the collaborator between the selected dates.
     */
    public void getTasksForCollaborator(){
        Date first=new Date(firstDate.getValue().getYear(),firstDate.getValue().getMonthValue(),firstDate.getValue().getDayOfMonth());
        Date second=new Date(secondDate.getValue().getYear(),secondDate.getValue().getMonthValue(),secondDate.getValue().getDayOfMonth());
        tasksInThoseDates=ctrl.getEntrysAssignedToMe(first,second);
    }

    /**
     * Handles the action of seeing tasks. Loads the task list scene and sets the tasks in the table.
     *
     * @param event the action event
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    public void seeTasks(ActionEvent event) throws IOException {
        getTasksForCollaborator();
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/collaborator/Scene_TaskListCollaborator.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
        ViewTaskListCollaboratorUI ui=fxmlLoader.getController();
        ui.setTableTasks(tasksInThoseDates);
    }

    /**
     * Handles the action of logging out. Displays a confirmation dialog and, if confirmed,
     * logs out the user and loads the login scene.
     *
     * @param event the action event
     * @throws IOException if the FXML file cannot be loaded
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
}
