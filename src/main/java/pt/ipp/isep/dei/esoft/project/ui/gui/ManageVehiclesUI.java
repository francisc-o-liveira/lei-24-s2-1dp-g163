package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterVehicleController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;

public class ManageVehiclesUI {

    public Stage stage=LoginUI.getMainStage();
    public Stage stageToViewDetails = new Stage();

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

    @FXML
    public TableColumn<Vehicle, Void> colButtonsDetails;

    ObservableList<Vehicle> vehiclesObservableList= FXCollections.observableArrayList();

    public ManageVehiclesUI(){
        ctrl=new RegisterVehicleController();
        ctrlAuth=new AuthenticationController();
    }

    public void setTableVehicles(){
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCurrentKm.setCellValueFactory(new PropertyValueFactory<>("currentKm"));
        colCheckUpFreq.setCellValueFactory(new PropertyValueFactory<>("checkUpFreq"));
        colMaintenance.setCellValueFactory(new PropertyValueFactory<>("maintenance"));
        colButtonsDetails.setCellFactory(new Callback<
                TableColumn<Vehicle, Void>, TableCell<Vehicle, Void>>() {
            @Override
            public TableCell<Vehicle, Void> call(TableColumn<Vehicle, Void> param) {
                return new TableCell<Vehicle, Void>() {
                    private final Button btn = new Button("View Details");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Vehicle vehicle = getTableView().getItems().get(getIndex());
                            try {
                                showMore(vehicle);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        });

        for(Vehicle v : ctrl.getVehicleList()){
            vehiclesObservableList.add(v);
        }

        tableViewVehicles.setOnMouseClicked(mouseEvent -> putInTextField());
    }

    public void showMore(Vehicle vehicle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_ViewDetailsRegisterVehicle.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stageToViewDetails.setScene(scene);
        stageToViewDetails.show();
        ViewDetailsVehicleUI ui= fxmlLoader.getController();
        ui.putInTextFields(getSelectedCollaborator());
    }

    public Vehicle getSelectedCollaborator(){
        return tableViewVehicles.getSelectionModel().getSelectedItem();
    }


    private void putInTextField(){
        Vehicle selectVehicle = tableViewVehicles.getSelectionModel().getSelectedItem();
        String editVehicle = selectVehicle.getBrand();
        introducingVehicleNameField.setText(editVehicle);
    }

    @FXML
    public void btnUpdateKm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_UpdateKm.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage otherStage= new Stage();
        otherStage.setScene(scene);
        otherStage.show();
    }

    @FXML
    public void btnRegisterCheck() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_RegisterCheckVehicle.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage otherStage= new Stage();
        otherStage.setScene(scene);
        otherStage.show();
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
            ctrlAuth.doLogout();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneLogin.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader;
        try {
            UserRoleDTO role = ctrlAuth.getAtualUserRole();
            if (role.getDescription().equals(AuthenticationController.ROLE_HRM)){
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_HRM.fxml"));
            } else if (role.getDescription().equals(AuthenticationController.ROLE_HRM)) {
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
