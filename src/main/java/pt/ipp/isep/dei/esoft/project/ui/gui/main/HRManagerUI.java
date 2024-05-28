package pt.ipp.isep.dei.esoft.project.ui.gui.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.manage.ManageCollaboratorsUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.manage.ManageJobsUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.manage.ManageSkillsUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.manage.ManageTeamsUI;


import java.io.IOException;


public class HRManagerUI {

    public Stage stage = LoginUI.getMainStage();

    public AuthenticationController ctrlAuth;

    public HRManagerUI(){
        ctrlAuth = new AuthenticationController();
    }

    @FXML
    public void reloadPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_HRM.fxml"));
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
