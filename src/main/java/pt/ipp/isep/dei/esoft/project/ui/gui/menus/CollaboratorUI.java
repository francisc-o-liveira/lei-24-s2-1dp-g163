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
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.ui.gui.collaborator.ViewTaskListCollaboratorUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CollaboratorUI {
    public Stage stage = LoginUI.getMainStage();

    public AuthenticationController ctrlAuth;
    private List<EntryDto> tasksInThoseDates;

    @FXML
    private DatePicker firstDate;
    @FXML
    private DatePicker secondDate;

    public CollaboratorUI(){
        ctrlAuth=new AuthenticationController();
        tasksInThoseDates=new ArrayList<>();
    }

    public void getTasksForCollaborator(){
        //tasksInThoseDates=ctrl.getTasks(firstDate.getValue(),secondDate.getValue());
    }


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

    @FXML
    public void btnCompleteTasks(ActionEvent event){
        popUp().show();
        if(popUp().showAndWait().get() == ButtonType.OK){
            //mark as complete and also register the finish time!
        }
    }

    private Alert popUp() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);

        alerta.setTitle("Confirmation");
        alerta.setContentText("Do you wish to mark these tasks as complete?");

        return alerta;
    }

    @FXML
    public void reload(ActionEvent event) throws IOException {
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
}
