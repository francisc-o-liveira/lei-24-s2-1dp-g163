package pt.ipp.isep.dei.esoft.project.ui.gui.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.OrganizationController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.employee.Manager;
import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.domain.model.Email;
import pt.isep.lei.esoft.auth.domain.model.User;
import pt.isep.lei.esoft.auth.domain.model.UserRole;
import pt.isep.lei.esoft.auth.domain.store.UserStore;

import java.util.Collections;


public class SystemConfigsUI {

    AuthenticationController ctrl=new AuthenticationController();
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
    public OrganizationController org;
    public ObservableList<Manager> managers=FXCollections.observableArrayList();

    public SystemConfigsUI(){
        org=new OrganizationController();
    }

    public void setComboBoxAndStage(Stage stage){
        ObservableList<String> rolesForBox= FXCollections.observableArrayList(ctrl.getRolesToSelect());
        roles.setItems(rolesForBox);
        this.stage=stage;
    }

    public void setTableSystemConfigs(){
        colManagers.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colType.setCellValueFactory(new PropertyValueFactory<>("position"));

        for(Manager m : org.getManagersList()){
            managers.add(m);
        }
        tableSystemConfigs.setItems(managers);
    }


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
    @FXML
    void addManager(ActionEvent event) {
        String name=nameTxt.getText();
        String email=emailTxt.getText();
        String role=roles.getValue();
        String phone=phoneTxt.getText();
        try{
            org.addEmployee(name,role,phone,email); //add exceptions on adding an employee
            popUp().show();
        } catch (Exception e){
            popUpWithMessage(e.getMessage()).show();
        }
    }

    private Alert popUp(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Manager added!");

        return alert;
    }

    private Alert popUpWithMessage(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR");
        alert.setContentText(message);

        return alert;
    }

    @FXML
    public void btnReload(ActionEvent event){
        tableSystemConfigs.getItems().clear();
        setTableSystemConfigs();
    }
}
