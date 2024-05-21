package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;

import java.util.ArrayList;

public class SystemConfigsUI {

    AuthenticationController ctrl=new AuthenticationController();
    public Stage stage;
    @FXML
    private TextField emailTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField passwordTxt;

    @FXML
    private ComboBox<String> roles;
    public void setComboBoxAndStage(Stage stage){
        ObservableList<String> rolesForBox= FXCollections.observableArrayList(ctrl.getRolesToSelect());
        roles.setItems(rolesForBox);
        this.stage=stage;
    }
    @FXML
    void addManager(ActionEvent event) {
        String name=nameTxt.getText();
        String email=emailTxt.getText();
        String password=passwordTxt.getText();
        String role=roles.getValue();

        ctrl.addUserWithRole(name,email,password,role);
        if(popUp().showAndWait().get()== ButtonType.OK){
            stage.close();
        }
    }

    private Alert popUp(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Manager added!");

        return alert;
    }


}
