package pt.ipp.isep.dei.esoft.project.ui.gui.vehicles;

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
import pt.ipp.isep.dei.esoft.project.application.controller.vehicleSystem.RegisterVehicleController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;
import java.util.List;

public class ManageVehiclesUI {

    public Stage stage= LoginUI.getMainStage();
    public Stage stageToViewDetails = new Stage();

    public RegisterVehicleController ctrl;
    public AuthenticationController ctrlAuth;
    @FXML
    public TableView<Vehicle> tableViewVehicles;

    @FXML
    public TableColumn<Vehicle, String> colBrand;

    @FXML
    public TableColumn<Vehicle, String> colModel;
    @FXML
    public TableColumn<Vehicle, Vehicle.Type> colType;
    @FXML
    public TableColumn<Vehicle, Double> colCurrentKm;
    @FXML
    public TableColumn<Vehicle, Double> colCheckUpFreq;

    @FXML
    public TableColumn<Vehicle, Void> colButtonsDetails;

    @FXML public CheckBox vehiclesCheckUp;

    ObservableList<Vehicle> vehiclesObservableList= FXCollections.observableArrayList();

    Vehicle selectedVehicle;

    public ManageVehiclesUI(){
        ctrl=new RegisterVehicleController();
        ctrlAuth=new AuthenticationController();
    }

    public void setTableVehicles(){
        colBrand.setCellValueFactory(new PropertyValueFactory<>("Brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("Model"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colCurrentKm.setCellValueFactory(new PropertyValueFactory<>("CurrentKm"));
        colCheckUpFreq.setCellValueFactory(new PropertyValueFactory<>("FrequencyCheckKm"));
        colButtonsDetails.setCellFactory(new Callback<
                TableColumn<Vehicle, Void>, TableCell<Vehicle, Void>>() {
            @Override
            public TableCell<Vehicle, Void> call(TableColumn<Vehicle, Void> param) {
                return new TableCell<Vehicle, Void>() {
                    private final Button btn = new Button("View Details");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            selectedVehicle = tableViewVehicles.getItems().get(((TableCell) ((Button)event.getSource()).getParent()).getIndex());
                            try {
                                showMore(selectedVehicle);
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
        vehiclesObservableList.addAll(ctrl.getVehicleList());
        tableViewVehicles.setItems(vehiclesObservableList);
    }

    public void showMore(Vehicle vehicle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/vehicles/Scene_ViewDetailsRegisterVehicle.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stageToViewDetails.setScene(scene);
        stageToViewDetails.show();
        ViewDetailsVehicleUI ui= fxmlLoader.getController();
        ui.setSelectedVehicle(vehicle);
        ui.putInTextFields(vehicle);
        ui.setTable(vehicle);
        ui.setComboBox();
    }

    public void setSelectedVehicle(){
        this.selectedVehicle=tableViewVehicles.getSelectionModel().getSelectedItem();
    }

    public Vehicle getSelectedVehicle(){
        return selectedVehicle;
    }

    @FXML
    public void btnUpdateKm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/vehicles/Scene_UpdateKm.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage otherStage= new Stage();
        otherStage.setScene(scene);
        otherStage.show();
        ViewDetailsVehicleUI ui= fxmlLoader.getController();
        setSelectedVehicle();
        ui.setSelectedVehicle(getSelectedVehicle());
    }


    @FXML
    public void btnRemove(){
        setSelectedVehicle();
        Vehicle selectedVehicle=getSelectedVehicle();
        boolean operationSuccess = false;
        if(selectedVehicle != null){
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/vehicles/Scene_AddVehicle.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage otherStage= new Stage();
        otherStage.setScene(scene);
        otherStage.show();
        ViewDetailsVehicleUI ui=fxmlLoader.getController();
        ui.setComboBox();
    }


    @FXML
    public void btnEdit() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/vehicles/Scene_ViewDetailsRegisterVehicle.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage otherStage= new Stage();
        otherStage.setScene(scene);
        otherStage.show();
        ViewDetailsVehicleUI ui=fxmlLoader.getController();
        setSelectedVehicle();
        ui.setSelectedVehicle(getSelectedVehicle());
        ui.showSelectedVehicle(getSelectedVehicle());
        ui.putInTextFields(getSelectedVehicle());
        ui.setComboBox();
        ui.setTable(getSelectedVehicle());
        ui.setBtnAddVehicleToVisibleOrNot(false);
        ui.setBtnEditVehicleToVisileOrNot(true);
    }

    @FXML
    public void btnReload(ActionEvent event){
        tableViewVehicles.getItems().clear();
        setTableVehicles();
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
        FXMLLoader fxmlLoader ;
        try {
            UserRoleDTO role = ctrlAuth.getAtualUserRole();
            if (role.getDescription().equals(AuthenticationController.ROLE_HRM)){
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_HRM.fxml"));
            } else if (role.getDescription().equals(AuthenticationController.ROLE_VFM)) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_VFM.fxml"));
            }else {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_Admin.fxml"));
            }
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (ArrayIndexOutOfBoundsException e){
            popUpOfVerifications(Alert.AlertType.WARNING,"PLEASE RESTART THIS APPLICATION").show();
        }
    }

    @FXML
    public void btnUpdateKm(ActionEvent event)throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/vehicles/Scene_UpdateKm.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
        ViewDetailsVehicleUI ui=fxmlLoader.getController();
        setSelectedVehicle();
        ui.setSelectedVehicle(getSelectedVehicle());
    }

    @FXML
    public void filterVehicles(ActionEvent event){
        if(vehiclesCheckUp.isSelected()){
            List<Vehicle> vehiclesNeedingCheckUp=ctrl.getVehiclesNeedingCheckUp();
            tableViewVehicles.getItems().clear();
            ObservableList<Vehicle> vehiclesCheckUp=FXCollections.observableArrayList();
            vehiclesCheckUp.addAll(vehiclesNeedingCheckUp);
            tableViewVehicles.setItems(vehiclesCheckUp);
        } else {
            tableViewVehicles.getItems().clear();
            setTableVehicles();
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
