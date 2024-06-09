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
import pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.RegisterController;

import java.io.IOException;

import static pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.RegisterController.ROLE_COLAB;

/**
 * UI Controller class for the register.
 */
public class RegisterUI {

    /** Text field for entering the user's email. */
    @FXML
    private TextField emailLogin;

    /** Password field for entering the user's password. */
    @FXML
    private PasswordField passwordLogin;

    /** Password field for repeating the user's password. */
    @FXML
    private PasswordField repeatPasswordLogin;

    /** Combo box for selecting the user's role. */
    @FXML
    private ComboBox<String> roleComboBox;

    /** The main stage of the application. */
    public static Stage mainStage;

    /** Instance of the RegisterController for handling registration. */
    private final RegisterController ctrl =  RegisterController.getInstance();

    /** Instance of the AuthenticationController for authentication-related operations. */
    private final AuthenticationController ctrlAuth= AuthenticationController.getInstance();

    /**
     * Sets the main stage of the application and populates the role combo box.
     *
     * @param mainStage The main stage of the application.
     */
    public void setMainStageAndBox(Stage mainStage) {
        ObservableList<String> roles= FXCollections.observableArrayList(ctrl.getRoles());
        roleComboBox.setItems(roles);
        this.mainStage = mainStage;
    }

    /**
     * Handles the action when the login button is clicked.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    public void btnLogin(ActionEvent event) throws IOException {
        returnToLogin();
    }

    /**
     * Handles the action when the register button is clicked.
     *
     * @param event The action event.
     */
    @FXML
    public void btnRegister(ActionEvent event) {
        try{
            regAccount();
            if(ctrl.userExists(emailLogin.getText())){
                popUp().showAndWait();

                if(popUp().showAndWait().get()==ButtonType.OK){
                    returnToLogin();
                }
            } else {
                String message=String.format("This" + roleComboBox.getValue() + "does not exist. Please contact GSM to add him first.");
                popUpExceptions(message).show();
                if(popUpExceptions(message).showAndWait().get()==ButtonType.OK){
                    emailLogin.clear();
                    passwordLogin.clear();
                    repeatPasswordLogin.clear();
                }
            }
        }catch (Exception e){
            popUpExceptions(e.getMessage()).show();
        }
    }

    /**
     * Registers a new user account.
     */
    private void regAccount() {
        if (emailLogin.getText().isEmpty() || passwordLogin.getText().isEmpty() || repeatPasswordLogin.getText().isEmpty()) {
            throw new IllegalArgumentException("Email or password are required");
        }
        if (ctrl.userExists(emailLogin.getText())){
            throw new IllegalArgumentException("User already exists on the System, please login");
        }else {
            if (passwordLogin.getText().equals(repeatPasswordLogin.getText()) && ctrl.verifyPassword(passwordLogin.getText())) {
                if(roleComboBox.getValue().equals(ROLE_COLAB)){
                    ctrl.registerCollaborator(emailLogin.getText(),passwordLogin.getText());
                }
                ctrl.registerManager(roleComboBox.getSelectionModel().getSelectedItem(),emailLogin.getText(),passwordLogin.getText());
            }else {
                throw new IllegalArgumentException("Passwords do not match");
            }
        }
    }

    /**
     * Creates and returns a pop-up alert.
     *
     * @return The pop-up alert.
     */
    private Alert popUp(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Register completed!");

        return alert;
    }

    /**
     * Creates and returns a pop-up alert for exceptions.
     *
     * @param message The message to display in the alert.
     * @return The pop-up alert.
     */
    private Alert popUpExceptions(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText(message);

        return alert;
    }

    /**
     * Returns to the login screen.
     *
     * @throws IOException If an I/O error occurs.
     */
    private void returnToLogin()throws IOException{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/SceneLogin.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }
}
