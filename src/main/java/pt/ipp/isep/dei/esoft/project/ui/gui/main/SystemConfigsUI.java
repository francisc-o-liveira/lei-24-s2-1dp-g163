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
    private TextField passwordTxt;

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

    private OrganizationController org=new OrganizationController();
    private ObservableList<Manager> managers=FXCollections.observableSet(org.getList());


    public void setComboBoxAndStage(Stage stage){
        ObservableList<String> rolesForBox= FXCollections.observableArrayList(ctrl.getRolesToSelect());
        roles.setItems(rolesForBox);
        this.stage=stage;
    }

    public void setTableSystemConfigs(){
        colManagers.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colType.setCellValueFactory(new PropertyValueFactory<>("position"));
        tableSystemConfigs.setItems(managers);
    }


    @FXML
    void btnRemove(ActionEvent event) {

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
