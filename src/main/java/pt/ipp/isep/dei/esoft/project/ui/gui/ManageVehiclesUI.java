package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import pt.ipp.isep.dei.esoft.project.domain.vehicle.CheckUp;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ManageVehiclesUI {

    public Stage stage=LoginUI.getMainStage();
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

    @FXML public CheckBox closeToCheck;

    ObservableList<Vehicle> vehiclesObservableList= FXCollections.observableArrayList();

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
                            Vehicle vehicle = tableViewVehicles.getSelectionModel().getSelectedItem();
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

        // Creating example vehicles
        Vehicle vehicle1 = new Vehicle(
                "Toyota",
                "Camry",
                Vehicle.Type.LightPassenger,
                1500,
                2000,
                50000,
                new Date(2022,5,11), // Register Date
                new Date(2023,5,12),// Acquisition Date
                10000,"AB-12-AA",
                new Date(2024,1,20),
                10000
        );

        Vehicle vehicle2 = new Vehicle(
                "Honda",
                "Civic",
                Vehicle.Type.LightPassenger,
                1400,  // Tare
                1800,  // Gross Weight
                60000, // Current Km
                new Date(2021, 8, 15), // Register Date
                new Date(2022, 9, 20), // Acquisition Date
                12000, // Frequency Check Km
                "XY-34-SS", // Plate
                new Date(2023, 3, 10), // Last check-up date
                8000 // Frequency Check Km for next check-up
        );


        // Adding vehicles to a list
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(vehicle1);
        vehicleList.add(vehicle2);



        vehiclesObservableList.addAll(ctrl.getVehicleList());
        tableViewVehicles.setItems(vehiclesObservableList);
    }

    public void showMore(Vehicle vehicle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_ViewDetailsRegisterVehicle.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stageToViewDetails.setScene(scene);
        stageToViewDetails.show();
        ViewDetailsVehicleUI ui= fxmlLoader.getController();
        ui.putInTextFields(getSelectedVehicle());
    }

    public Vehicle getSelectedVehicle(){
        return tableViewVehicles.getSelectionModel().getSelectedItem();
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
        ViewDetailsVehicleUI ui=fxmlLoader.getController();
        ui.setComboBox();
    }


    @FXML
    public void btnEdit() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_ViewDetailsRegisterVehicle.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage otherStage= new Stage();
        otherStage.setScene(scene);
        otherStage.show();
        ViewDetailsVehicleUI ui=fxmlLoader.getController();
        ui.setTable();
        Vehicle selectedVehicle=tableViewVehicles.getSelectionModel().getSelectedItem();
        ui.showSelectedVehicle(selectedVehicle);
        ui.putInTextFields(getSelectedVehicle());
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

    @FXML
    public void btnRegisterCheck(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_RegisterCheckVehicle.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void btnUpdateKm(ActionEvent event)throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_UpdateKm.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void filterVehicles(ActionEvent event){
        FilteredList<Vehicle> filteredList = new FilteredList<>(vehiclesObservableList);

        Predicate<Vehicle> closeVehicles = Vehicle::isCloseToCheck;

        filteredList.predicateProperty().bind(
                Bindings.createObjectBinding(
                        () -> closeToCheck.isSelected() ? closeVehicles : null,
                        closeToCheck.selectedProperty()
                )
        );

        tableViewVehicles.setItems(filteredList);

    }

    private Alert popUpOfVerifications(Alert.AlertType alertType, String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(message);

        return alerta;
    }
}
