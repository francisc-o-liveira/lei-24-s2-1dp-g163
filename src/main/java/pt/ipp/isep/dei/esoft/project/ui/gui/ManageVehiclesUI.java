package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterVehicleController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;

public class ManageVehiclesUI {

    public Stage stage=LoginUI.getMainStage();

    @FXML
    public TextField introducingVehicleNameField;

    public RegisterVehicleController ctrl;
    public AuthenticationController ctrlAuth;
    @FXML
    public TableView<Vehicle> tableViewVehicles;

    @FXML
    public TableColumn<Vehicle, String> colBrand;

    public TableColumn<Vehicle, String> colModel;

    public TableColumn<Vehicle, String> colType;

    public TableColumn<Vehicle, String> colCurrentKm;

    public TableColumn<Vehicle, String> colCheckUpFreq;

    public TableColumn<Vehicle, String> colMaintenance;


    public ManageVehiclesUI(){
        ctrl=new RegisterVehicleController();
        ctrlAuth=new AuthenticationController();
    }

    public void setTableVehicles(){
        colBrand.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getBrand());
        });
        tableViewVehicles.getItems().clear();
        for(Vehicle v : ctrl.getVehicleList()){
            tableViewVehicles.getItems().add(v);
        }
        tableViewVehicles.setOnMouseClicked(mouseEvent -> putInTextField());
    }


    private void putInTextField(){
        Vehicle selectVehicle = tableViewVehicles.getSelectionModel().getSelectedItem();
        String editVehicle = selectVehicle.getBrand();
        introducingVehicleNameField.setText(editVehicle);
    }

    @FXML
    public void btnRemove(){
        Vehicle selectedVehicle=tableViewVehicles.getSelectionModel().getSelectedItem();
        boolean operationSuccess = false;
        if(selectedVehicle != null){
            //should have a pop-up to confirm removal
            tableViewVehicles.getItems().remove(selectedVehicle);
            try{
                operationSuccess=ctrl.removeVehicleFromList(selectedVehicle);
            }catch (RuntimeException e){
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage());
            }
            if (operationSuccess){
                popUpOfVerifications(Alert.AlertType.CONFIRMATION,"Vehicle Removed Successfully");
            }
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
        FXMLLoader fxmlLoader ;
        try {
            UserRoleDTO role = ctrlAuth.getAtualUserRole();
            if (role.equals(ctrlAuth.ROLE_HRM)){
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_HRM.fxml"));
            } else if (role.equals(ctrlAuth.ROLE_HRM)) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_VFM.fxml"));
            }else {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_GSM.fxml"));
            }
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (ArrayIndexOutOfBoundsException e){
            popUpOfVerifications(Alert.AlertType.WARNING,"PLEASE RESTART THIS APPLICATION").show();
        }
    }

    private Alert popUpOfVerifications(Alert.AlertType alertType, String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(message);

        return alerta;
    }
}
