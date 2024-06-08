package pt.ipp.isep.dei.esoft.project.ui.gui.menus;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.OrganizationController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.domain.employee.Manager;

/**
 * This class represents the UI for system configurations, allowing the management of managers
 * within the system. It provides functionalities to add, remove, and display managers.
 */
public class SystemConfigsUI {

    /**
     * The register controller instance.
     */
    private RegisterController ctrl=RegisterController.getInstance();

    /**
     * The main stage of the application.
     */
    public Stage stage;

    @FXML
    private TextField emailTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private ComboBox<String> roles;

    @FXML
    private TableColumn<Manager, String> colEmail;

    @FXML
    private TableColumn<Manager, String> colManagers;

    @FXML
    private TableColumn<Manager, String> colType;

    @FXML
    private TableView<Manager> tableSystemConfigs;

    /**
     * Observable list of managers for displaying in the table view.
     */
    ObservableList<Manager> managers=FXCollections.observableArrayList();

    /**
     * The organization controller instance.
     */
    OrganizationController org;

    /**
     * Constructor for the SystemConfigsUI class. Initializes the organization controller.
     */
    public SystemConfigsUI(){
        org= OrganizationController.getInstance();
    }

    /**
     * Sets up the combo box with roles and the stage.
     *
     * @param stage the main stage
     */
    public void setComboBoxAndStage(Stage stage){
        ObservableList<String> rolesForBox= FXCollections.observableArrayList(ctrl.getRolesToSelect());
        roles.setItems(rolesForBox);
        this.stage=stage;
    }

    /**
     * Sets up the table view for system configurations with managers' data.
     */
    public void setTableSystemConfigs(){
        colManagers.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colType.setCellValueFactory(new PropertyValueFactory<>("position"));

        for(Manager m : org.getManagersList()){
            managers.add(m);
        }
        tableSystemConfigs.setItems(managers);
    }

    /**
     * Handles the action of removing a selected manager from the table and organization.
     *
     * @param event the action event
     */
    @FXML
    void btnRemove(ActionEvent event) {
        Manager selectedManager=tableSystemConfigs.getSelectionModel().getSelectedItem();
        if(selectedManager != null){

        Alert popUp = new Alert(Alert.AlertType.CONFIRMATION);

        popUp.setHeaderText("Removing Manager");
        popUp.setContentText("Do you want to remove this manager?");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

            if (popUp.showAndWait().get() == ButtonType.OK) {
                tableSystemConfigs.getItems().remove(selectedManager);
                org.removeManager(selectedManager);
            }
        } else {
            popUpWithMessage("Select a manager to remove").showAndWait();
        }
    }

    /**
     * Handles the action of adding a new manager to the organization.
     *
     * @param event the action event
     */
    @FXML
    void addManager(ActionEvent event) {
        String name=nameTxt.getText();
        String email=emailTxt.getText();
        String role=roles.getValue();
        String phone=phoneTxt.getText();
        try{
            org.addEmployee(name,role,phone,email);
            popUp().show();
        } catch (Exception e){
            popUpWithMessage(e.getMessage()).show();
        }
    }

    /**
     * Creates and returns a confirmation alert indicating that a manager was added.
     *
     * @return the created alert
     */
    private Alert popUp(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Manager added!");

        return alert;
    }

    /**
     * Creates and returns an error alert with a specified message.
     *
     * @param message the message to be displayed in the alert
     * @return the created alert
     */
    private Alert popUpWithMessage(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR");
        alert.setContentText(message);

        return alert;
    }

    /**
     * Handles the action of reloading the table view with updated managers' data.
     *
     * @param event the action event
     */
    @FXML
    public void btnReload(ActionEvent event){
        tableSystemConfigs.getItems().clear();
        setTableSystemConfigs();
    }
}
