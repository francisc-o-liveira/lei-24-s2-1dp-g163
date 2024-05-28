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
import pt.ipp.isep.dei.esoft.project.ui.gui.tasks.ManageTasksUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.team.ManageTeamsUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.vehicles.ManageVehiclesUI;

import java.io.IOException;

public class GSManagerUI {
    public Stage stage = LoginUI.getMainStage();
    public AuthenticationController ctrlAuth;

    public GSManagerUI(){
        ctrlAuth = new AuthenticationController();
    }

    @FXML
    public void reload(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_GSM.fxml"));
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
    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }

    /*@FXML
    public void manageEquipment(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/SceneTableViewEquipment.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
        ManageEquipment ctrlUI=fxmlLoader.getController();
        ctrlUI.setTableEquipment();
        popUpOfVerifications(Alert.AlertType.INFORMATION,"Implementing ...");
    }*/

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

    @FXML
    public void manageToDoList(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/tasks/Scene_ToDoList.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void manageAgenda(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/entry/Scene_Agenda.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
        //ManageAgendaUI ui=fxmlLoader.getController();
    }

    @FXML
    public void manageGreenSpaces(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/green_spaces/Scene_ListGreenSpaces.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
