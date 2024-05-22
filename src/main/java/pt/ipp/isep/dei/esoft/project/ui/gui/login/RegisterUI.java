package pt.ipp.isep.dei.esoft.project.ui.gui.login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.RegisterController;

import java.io.IOException;

public class RegisterUI {

    @FXML
    private TextField emailLogin;

    @FXML
    private PasswordField passwordLogin;


    @FXML
    private PasswordField repeatPasswordLogin;

    @FXML
    private ComboBox<String> roleComboBox;

    public static Stage mainStage;

    private final RegisterController ctrl = new RegisterController();
    private final AuthenticationController ctrlAuth=new AuthenticationController();

    public void setMainStageAndBox(Stage mainStage) {
        ObservableList<String> roles= FXCollections.observableArrayList(ctrlAuth.getRolesToSelect());
        roleComboBox.setItems(roles);
        this.mainStage = mainStage;
    }

    @FXML
    public void btnLogin(ActionEvent event) throws IOException {
        returnToLogin();
    }

    @FXML
    public void btnRegister(ActionEvent event) {
        try{
            regAccount();
            if(ctrl.userExists(emailLogin.getText())){
                popUp().showAndWait();

                if(popUp().showAndWait().get()==ButtonType.OK){
                    returnToLogin();
                }
            }
        }catch (Exception e){
            popUpExceptions(e.getMessage()).show();
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
                        ctrl.registerManager(roleComboBox.getSelectionModel().getSelectedItem(),emailLogin.getText(),passwordLogin.getText());
                    case "HRM":
                        ctrl.registerManager(roleComboBox.getSelectionModel().getSelectedItem(),emailLogin.getText(),passwordLogin.getText());
                    case "VFM":
                        ctrl.registerManager(roleComboBox.getSelectionModel().getSelectedItem(),emailLogin.getText(),passwordLogin.getText());
                    case "Collaborator":
                        ctrl.registerCollaborator(emailLogin.getText(),passwordLogin.getText());
                }
            }else {
                throw new IllegalArgumentException("Passwords do not match");
            }
        }
    }

    private Alert popUp(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Register completed!");

        return alert;
    }

    private Alert popUpExceptions(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText(message);

        return alert;
    }

    private void returnToLogin()throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/SceneLogin.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }
}
