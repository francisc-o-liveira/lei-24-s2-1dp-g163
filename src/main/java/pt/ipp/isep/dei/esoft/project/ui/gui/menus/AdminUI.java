package pt.ipp.isep.dei.esoft.project.ui.gui.menus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.ui.gui.collaborator.ManageCollaboratorsUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.collaborator.ManageJobsUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.collaborator.ManageSkillsUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.team.ManageTeamsUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.vehicles.ManageVehiclesUI;

import java.io.IOException;

/**
 * This class represents the UI for administrators, allowing them to manage various aspects of the application,
 * such as vehicles, jobs, skills, collaborators, teams, and system configurations.
 */
public class AdminUI {
    /**
     * The main stage of the application.
     */
    public Stage stage = LoginUI.getMainStage();

    /**
     * The authentication controller instance.
     */
    public AuthenticationController ctrlAuth;

    /**
     * Constructor for the AdminUI class. Initializes the authentication controller.
     */
    public AdminUI(){
        ctrlAuth = AuthenticationController.getInstance();
    }

    /**
     * Reloads the admin menu scene.
     *
     * @param event the action event
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    public void reload(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_Admin.fxml"));
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

    /**
     * Loads the scene for managing vehicles.
     *
     * @param event the action event
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    public void manageVehicles(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/vehicles/SceneTableViewRegisterVehicle.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
        ManageVehiclesUI ctrlUI=fxmlLoader.getController();
        ctrlUI.setTableVehicles();
        popUpOfVerifications(Alert.AlertType.INFORMATION,"Implementing ...");
    }

    /**
     * Displays a pop-up alert with the specified message.
     *
     * @param alertType the type of alert
     * @param messages the message to display
     * @return the created alert
     */
    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
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

    /**
     * Loads the scene for system configurations.
     *
     * @param event the action event
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void btnSystemConfigs(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/Scene_SystemConfigs.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        Stage stageToAdd=new Stage();
        stageToAdd.setScene(scene);
        stageToAdd.show();
        SystemConfigsUI ui =fxmlLoader.getController();
        ui.setComboBoxAndStage(stageToAdd);
        ui.setTableSystemConfigs();
    }

    /**
     * Loads the scene for managing the to-do list.
     *
     * @param event the action event
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    public void manageToDoList(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/tasks/Scene_ToDoList.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the scene for managing the agenda.
     *
     * @param event the action event
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    public void manageAgenda(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/entry/Scene_Agenda.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the scene for managing green spaces.
     *
     * @param event the action event
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    public void manageGreenSpaces(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/green_spaces/Scene_ListGreenSpaces.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
