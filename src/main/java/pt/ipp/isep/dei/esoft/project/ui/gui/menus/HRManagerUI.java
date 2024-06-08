package pt.ipp.isep.dei.esoft.project.ui.gui.menus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.collaborator.ManageCollaboratorsUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.collaborator.ManageJobsUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.collaborator.ManageSkillsUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.team.ManageTeamsUI;

import java.io.IOException;

/**
 * This class represents the UI for HR managers, allowing them to manage various aspects of the application,
 * such as jobs, skills, collaborators, and teams.
 */
public class HRManagerUI {

    /**
     * The main stage of the application.
     */
    public Stage stage = LoginUI.getMainStage();

    /**
     * The authentication controller instance.
     */
    public AuthenticationController ctrlAuth;

    /**
     * Constructor for the HRManagerUI class. Initializes the authentication controller.
     */
    public HRManagerUI(){
        ctrlAuth = AuthenticationController.getInstance();
    }

    /**
     * Reloads the HR manager menu scene.
     *
     * @param event the action event
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    public void reloadPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_HRM.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
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

    /**
     * Loads the scene for managing jobs.
     *
     * @param event the action event
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    public void manageJobs(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/collaborator/SceneTableViewJobs.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
        ManageJobsUI ctrlUI = fxmlLoader.getController();
        ctrlUI.setJobCategoryTable();
    }

    /**
     * Loads the scene for managing skills.
     *
     * @param event the action event
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    public void manageSkills(ActionEvent event)throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/collaborator/SceneTableViewSkill.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
        ManageSkillsUI ctrlUI = fxmlLoader.getController();
        ctrlUI.setSkillTable();
    }

    /**
     * Loads the scene for managing collaborators.
     *
     * @param event the action event
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    public void manageCollaborators(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/collaborator/SceneTableViewRegisterCollaborator.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
        ManageCollaboratorsUI ctrlUI = fxmlLoader.getController();
        ctrlUI.setTableCollaborators();
    }

    /**
     * Loads the scene for managing teams.
     *
     * @param event the action event
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    public void manageTeams(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/teams/SceneTableViewManageTeams.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
        ManageTeamsUI ctrlUI = fxmlLoader.getController();
        ctrlUI.setTableTeams();
    }
}
