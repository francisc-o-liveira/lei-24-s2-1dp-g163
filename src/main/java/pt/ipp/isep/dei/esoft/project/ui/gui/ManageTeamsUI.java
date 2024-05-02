package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;

import java.io.IOException;

public class ManageTeamsUI {

    public Stage stage= LoginUI.getMainStage();
    public GenerateTeamController ctrl;

    @FXML
    public TableView<Team> tableViewTeams;

    @FXML
    public TableColumn<Team, Collaborator> colTeams;

    @FXML
    public void btnAdd() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_GenerateTeams.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        GenerateTeamsUI ctrlForGenerate= fxmlLoader.getController();
        ctrlForGenerate.setTableViewTeam();
    }

    @FXML
    public void btnRemove(){
        Team selectedTeam= tableViewTeams.getSelectionModel().getSelectedItem();

        if(selectedTeam != null){
            tableViewTeams.getItems().remove(selectedTeam);
            //ctrl.removeFromList(selectedTeam); -- method to be added in controller
        }
    }

    public void setTableTeams(){
        colTeams.setCellValueFactory(new PropertyValueFactory<>("TeamList"));
    }

    @FXML
    public void doLogout(ActionEvent event) throws IOException {
        Alert popUp = new Alert(Alert.AlertType.CONFIRMATION);

        popUp.setHeaderText("Logging Out");
        popUp.setContentText("Do you wish to log out?");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

        if (popUp.showAndWait().get() == ButtonType.OK) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneLogin.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_HRM.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
