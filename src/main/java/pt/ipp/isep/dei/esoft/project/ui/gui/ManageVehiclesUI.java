package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterVehicleController;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;

import java.io.IOException;

public class ManageVehiclesUI {

    public Stage stage=LoginUI.getMainStage();
    public RegisterVehicleController ctrl;
    @FXML
    public TableView<Vehicle> tableViewVehicles;


    public ManageVehiclesUI(){
        ctrl=new RegisterVehicleController();
    }

    public void setTableVehicles(){
        //set the table for the vehicles
    }

    @FXML
    public void btnRemove(){
        Vehicle selectedVehicle=tableViewVehicles.getSelectionModel().getSelectedItem();
        if(selectedVehicle != null){
            //should have a pop-up to confirm removal
            tableViewVehicles.getItems().remove(selectedVehicle);
            //ctrl.removeVehicleFromList(selectedVehicle); -- method to add in the ctrl
        }

    }

    @FXML
    public void btnAdd() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_ViewDetailsRegisterVehicle.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage otherStage= new Stage();
        otherStage.setScene(scene);
        otherStage.show();
    }

    //this scene needs to have the details already in the text fields
    @FXML
    public void btnEdit() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_ViewDetailsRegisterVehicle.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage otherStage= new Stage();
        otherStage.setScene(scene);
        otherStage.show();
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
