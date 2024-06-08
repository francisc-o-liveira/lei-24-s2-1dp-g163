package pt.ipp.isep.dei.esoft.project.ui.gui.menus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.vehicles.ManageVehiclesUI;

import java.io.IOException;

/**
 * This class represents the UI for Vehicle Fleet managers, allowing them to manage various aspects of the application,
 * such as vehicles and equipment.
 */
public class VFManagerUI {

    /**
     * The main stage of the application.
     */
    public Stage stage = LoginUI.getMainStage();

    /**
     * The authentication controller instance.
     */
    public AuthenticationController ctrlAuth;

    /**
     * Constructor for the VFManagerUI class. Initializes the authentication controller.
     */
    public VFManagerUI(){
        ctrlAuth = AuthenticationController.getInstance();
    }

    /**
     * Reloads the Vehicle Fleet manager menu scene.
     *
     * @param event the action event
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    public void reload(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_VFM.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates and returns an alert with the specified type and message.
     *
     * @param alertType the type of alert
     * @param messages the message to be displayed in the alert
     * @return the created alert
     */
    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }

    /**
     * Handles the action of logging out. Displays a confirmation dialog and, if confirmed,
     * logs out the user and loads the login scene.
     *
     * @param event the action event
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void doLogout(ActionEvent event) throws IOException{
        Alert popUp= new Alert(Alert.AlertType.CONFIRMATION);

        popUp.setHeaderText("Logging Out");
        popUp.setContentText("Do you wish to log out?");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

        if (popUp.showAndWait().get() == ButtonType.OK) {
            ctrlAuth.doLogout();
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/SceneLogin.fxml"));
            Parent root= fxmlLoader.load();
            Scene scene= new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Loads the scene for managing vehicles.
     *
     * @param event the action event
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    public void manageVehicles(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/vehicles/SceneTableViewRegisterVehicle.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
        ManageVehiclesUI ctrlUI=fxmlLoader.getController();
        ctrlUI.setTableVehicles();
    }
}
