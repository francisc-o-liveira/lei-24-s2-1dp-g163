package pt.ipp.isep.dei.esoft.project.ui.gui.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.RegisterController;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.List;

/** UI Controller for the login */
public class LoginUI {
    /** Textfield for the email */
    @FXML
    private TextField emailLogin;
    /** Password field for the password */
    @FXML
    private PasswordField passwordLogin;
    /** Attempts the user has */
    private static int attemps=4;
    /** Stage */
    public static Stage mainStage;
    /** Authentication controller */
    private final AuthenticationController ctrl = AuthenticationController.getInstance();

    /**
     * Sets the main stage of the application.
     *
     * @param mainStage The main stage of the application.
     */
    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    /**
     * Gets the main stage of the application.
     *
     * @return The main stage of the application.
     */
    public static Stage getMainStage(){
        return mainStage;
    }

    /**
     * Initializes the UI components.
     */
    public void initialize(){
        emailLogin.setOnKeyPressed(event ->{
            if (event.getCode().equals(KeyCode.ENTER)) {
                passwordLogin.requestFocus();
            }
        });
        passwordLogin.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                uiToShow(new ActionEvent());
            }
        });
    }

    /**
     * Handles the action when the login button is clicked.
     *
     * @param event The action event.
     */
    @FXML
    public void uiToShow(ActionEvent event) {
        if (attemps==0){
            blockUser();
        }
        try {
            attemps--;
            authenticateCredentials();
            List<UserRoleDTO> roles = this.ctrl.getUserRoles();
            UserRoleDTO role = selectsRole(roles);
            if (role.getDescription().equals(RegisterController.ROLE_VFM)) {
                showVFManagerUI();
            }else if (role.getDescription().equals(RegisterController.ROLE_HRM)) {
                showHRManagerUI();
            }else if (role.getDescription().equals(RegisterController.ROLE_GSM)) {
                showGSManagerUI();
            } else if (role.getDescription().equals(AuthenticationController.ROLE_ADMIN)) {
                showAdminManagerUI();
            } else {
                showCollaboratorUI();
            }
        } catch (LoginException e) {
            popUp(Alert.AlertType.WARNING, "Invalid Credentials of Login", "Try Again Please more: " + attemps + " times.").show();
        } catch (ArrayIndexOutOfBoundsException e) {
            popUp(Alert.AlertType.ERROR, "Login Error Program", "Try Again Please").show();
        } catch (IOException e) {
            popUp(Alert.AlertType.ERROR, "Redirect Page By Role Error", "Try Again Please").show();
        }
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

    /**
     * Handles the action when the register button is clicked.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
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

    /**
     * Handles the action when the forgot password button is clicked.
     *
     * @param event The action event.
     */
    @FXML
    public void btnForgotPassword(ActionEvent event) {
        popUp(Alert.AlertType.WARNING, "Please Contact the Administrator", "He can unblock your account and trade your password").show();
    }

    /**
     * Shows the HR Manager UI.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void showHRManagerUI() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_HRM.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

    /**
     * Shows the VF Manager UI.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void showVFManagerUI() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_VFM.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

/**
 * Shows the Admin Manager UI.
 *
 * @throws IOException If an I/O error occurs.
 */
public void showAdminManagerUI() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_Admin.fxml"));
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root);
    mainStage.setScene(scene);
    mainStage.show();
}

    /**
     * Shows the GS Manager UI.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void showGSManagerUI() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_GSM.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

    /**
     * Shows the Collaborator UI.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void showCollaboratorUI() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/collaborator/SceneMenu_Collaborator.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

    /**
     * Creates and returns a pop-up alert.
     *
     * @param alertType The type of the alert.
     * @param header    The header text of the alert.
     * @param message   The content text of the alert.
     * @return The pop-up alert.
     */
    private Alert popUp(Alert.AlertType alertType, String header, String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText(header);
        alerta.setContentText(message);

        return alerta;
    }
}
