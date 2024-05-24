package pt.ipp.isep.dei.esoft.project.ui.gui.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.List;


public class LoginUI {
    @FXML
    private TextField emailLogin;

    @FXML
    private PasswordField passwordLogin;

    private static int attemps=4;

    public static Stage mainStage;

    private final AuthenticationController ctrl = new AuthenticationController();

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public static Stage getMainStage(){
        return mainStage;
    }

    @FXML
    public void uiToShow(ActionEvent event) throws IOException{
        /*if (attemps==0){
            blockUser();
        }
        try {
            attemps--;
            authenticateCredentials();
            List<UserRoleDTO> roles = this.ctrl.getUserRoles();
            UserRoleDTO role = selectsRole(roles);
            if (role.getDescription().equals(AuthenticationController.ROLE_VFM)) {
                showVFManagerUI();
            }
            if (role.getDescription().equals(AuthenticationController.ROLE_HRM)) {
                showHRManagerUI();
            }
            if (role.getDescription().equals(AuthenticationController.ROLE_GSM)) {*/
                showGSManagerUI();
            /*}
        } catch (LoginException e) {
            popUp(Alert.AlertType.WARNING, "Invalid Credentials of Login", "Try Again Please more: " + attemps + " times.").show();
        } catch (ArrayIndexOutOfBoundsException e) {
            popUp(Alert.AlertType.ERROR, "Login Error Program", "Try Again Please").show();
        } catch (IOException e) {
            popUp(Alert.AlertType.ERROR, "Redirect Page By Role Error", "Try Again Please").show();
        }*/
    }

    private void blockUser() {
    }

    private UserRoleDTO selectsRole(List<UserRoleDTO> roles) {
        if (roles.size() == 1) {
            return roles.get(0);
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private boolean authenticateCredentials() throws LoginException {
        boolean sucess;
        sucess = ctrl.doLogin(emailLogin.getText(), passwordLogin.getText());
        if (!sucess) {
            throw new LoginException(emailLogin.getText());
        }
        return true;
    }

    @FXML
    public void btnRegister(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/SceneRegister.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
        RegisterUI ui =fxmlLoader.getController();
        ui.setMainStageAndBox(mainStage);
    }

    @FXML
    public void btnForgotPassword(ActionEvent event) {
        popUp(Alert.AlertType.WARNING, "Please Contact the Administrator", "He can unblock your account and trade your password").show();
    }

    public void showHRManagerUI() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_HRM.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void showVFManagerUI() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_VFM.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void showGSManagerUI() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_GSM.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

    private Alert popUp(Alert.AlertType alertType, String header, String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText(header);
        alerta.setContentText(message);

        return alerta;
    }
}
