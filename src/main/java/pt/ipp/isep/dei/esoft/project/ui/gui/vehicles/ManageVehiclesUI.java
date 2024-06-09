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
import pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.core.application.controller.vehicleSystem.RegisterVehicleController;
import pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.core.application.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;
import java.util.List;
/**
 * UI Controller for managing vehicles.
 */
public class ManageVehiclesUI {

    /** Stage */
    public Stage stage= LoginUI.getMainStage();
    /** Stage to view the details of the vehicle */
    public Stage stageToViewDetails = new Stage();
    /** Controller */
    public RegisterVehicleController ctrl;
    /** Authentication controller */
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
    @FXML
    public CheckBox vehiclesCheckUp;
    @FXML
    public Label labelRole;

    /** Observale list for the TableView*/
    private ObservableList<Vehicle> vehiclesObservableList= FXCollections.observableArrayList();
    /** Variable for the selected vehicle */
    public Vehicle selectedVehicle;

    /**
     * Constructor for ManageVehiclesUI.
     * Initializes the controllers.
     */
    public ManageVehiclesUI(){
        ctrl= RegisterVehicleController.getInstance();
        ctrlAuth=AuthenticationController.getInstance();
    }

    /**
     * Sets the label to display the user role.
     */
    public void setLabel(){
        UserRoleDTO role = ctrlAuth.getAtualUserRole();
        if (role.getDescription().equals(RegisterController.ROLE_VFM)){
            labelRole.setText("VehicleFleetManager");
        } else if (role.getDescription().equals(RegisterController.ROLE_GSM)) {
            labelRole.setText("GreenSpaceManager");
            labelRole.setLayoutX(28.0);
            labelRole.setLayoutY(130.0);
        } else {
            labelRole.setText("Admin");
            labelRole.setLayoutX(69);
            labelRole.setLayoutY(130.0);
        }
    }

    /**
     * Sets up the table view for displaying vehicles.
     */
    public void setTableVehicles(){
        setLabel();
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

    /**
     * Displays more details of a selected vehicle.
     *
     * @param vehicle the selected vehicle
     * @throws IOException if an error occurs during loading the UI
     */
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

    /**
     * Sets the selected vehicle.
     */
    public void setSelectedVehicle(){
        this.selectedVehicle=tableViewVehicles.getSelectionModel().getSelectedItem();
    }

    /**
     * Gets the selected vehicle.
     *
     * @return the selected vehicle
     */
    public Vehicle getSelectedVehicle(){
        return selectedVehicle;
    }


    /**
     * Handles the button click event to remove a vehicle.
     * Removes the selected vehicle from the table view and the vehicle list.
     * Displays a confirmation message upon successful removal.
     */
    @FXML
    public void btnRemove(){
        setSelectedVehicle();
        Vehicle selectedVehicle=getSelectedVehicle();
        boolean operationSuccess = false;
        if(selectedVehicle != null){
            tableViewVehicles.getItems().remove(selectedVehicle);
            try{
                ctrl.removeVehicleFromList(selectedVehicle);
            }catch (RuntimeException e){
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage());
            }
            finally {
                popUpOfVerifications(Alert.AlertType.CONFIRMATION,"Vehicle Removed Successfully");
            }
        }
    }

    /**
     * Handles the button click event to add a new vehicle.
     * Loads the scene for adding a new vehicle and displays it in a new stage.
     *
     * @throws IOException if an error occurs during loading the UI
     */
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

    /**
     * Handles the button click event to edit a vehicle.
     * Loads the scene for viewing and editing vehicle details and displays it in a new stage.
     *
     * @throws IOException if an error occurs during loading the UI
     */
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

    /**
     * Handles the button click event to reload the table view with vehicles.
     *
     * @param event the ActionEvent triggering the method
     */
    @FXML
    public void btnReload(ActionEvent event) {
        tableViewVehicles.getItems().clear();
        setTableVehicles();
    }

    /**
     * Handles the button click event to log out the user.
     * Displays a confirmation dialog before logging out.
     * Redirects to the login scene upon successful logout.
     *
     * @param event the ActionEvent triggering the method
     * @throws IOException if an error occurs during loading the UI
     */
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

    /**
     * Handles the button click event to go back to the main menu.
     * Redirects the user to the appropriate main menu based on their role.
     * Displays a warning message if an error occurs.
     *
     * @param event the ActionEvent triggering the method
     * @throws IOException if an error occurs during loading the UI
     */
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader;
        try {
            UserRoleDTO role = ctrlAuth.getAtualUserRole();
            if (role.getDescription().equals(RegisterController.ROLE_HRM)){
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_HRM.fxml"));
            } else if (role.getDescription().equals(RegisterController.ROLE_VFM)) {
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


    /**
     * Opens the scene to update the kilometers for the selected vehicle.
     *
     * @throws IOException if an error occurs during loading the UI
     */
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

    /**
     * Filters the vehicles based on check-up requirement.
     *
     * @param event the ActionEvent triggering the method
     */
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

    /**
     * Displays a pop-up alert for verifications.
     *
     * @param alertType the type of alert
     * @param message   the message to display
     * @return the created Alert object
     */
    private Alert popUpOfVerifications(Alert.AlertType alertType, String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(message);

        return alerta;
    }
}
