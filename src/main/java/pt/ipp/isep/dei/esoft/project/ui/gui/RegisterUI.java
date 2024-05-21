package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.RegisterController;

public class RegisterUI {

    @FXML
    private TextField emailLogin;

    @FXML
    private PasswordField passwordLogin;


    @FXML
    private PasswordField repeatPasswordLogin;

    @FXML
    private ComboBox<String> roleComboBox;

    private static int attemps=4;

    public static Stage mainStage;

    private final RegisterController ctrl = new RegisterController();

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public static Stage getMainStage(){
        return mainStage;
    }

    @FXML
    public void btnRegister(ActionEvent event) {
        if (attemps==0){
            blockUser();
        }
        try{
            attemps--;
            regAccount();
            if(ctrl.userExists(emailLogin.getText())){
                //Success
            }else {
                // Not Success
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void regAccount() {
        if (emailLogin.getText().isEmpty() || passwordLogin.getText().isEmpty() || repeatPasswordLogin.getText().isEmpty()) {
            throw new IllegalArgumentException("Email or password are required");
        }
        if (ctrl.userExists(emailLogin.getText())){
            throw new IllegalArgumentException("User already exists on the System, please login");
        }else {
            if (passwordLogin.getText().equals(repeatPasswordLogin.getText()) && ctrl.verifyPassword(passwordLogin.getText())) {
                switch (roleComboBox.getSelectionModel().getSelectedItem()) {
                    case "GSM":
                        ctrl.registerManager(roleComboBox.getSelectionModel().getSelectedItem());
                    case "HRM":
                        ctrl.registerManager(roleComboBox.getSelectionModel().getSelectedItem());
                    case "VFM":
                        ctrl.registerManager(roleComboBox.getSelectionModel().getSelectedItem());
                    case "Collaborator":
                        ctrl.registerCollaborator();
                }
            }else {
                throw new IllegalArgumentException("Passwords do not match");
            }
        }
    }




    private void blockUser() {
    }


}
