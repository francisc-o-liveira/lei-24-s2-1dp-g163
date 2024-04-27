package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.awt.*;

public class ManageCollaboratorsUI {

    public Stage stage = LoginUI.getMainStage();
    @FXML
    private TextField addressCity;

    @FXML
    private TextField addressStreet;

    @FXML
    private TextField addressZipCode;

    @FXML
    private DatePicker dateAdmission;

    @FXML
    private DatePicker dateBirthday;

    @FXML
    private TextField docIDNumber;

    @FXML
    private TextField email;


    @FXML
    private TextField name;

    @FXML
    private TextField phoneNumber;

    @FXML
    public void btnAddCollaborator(ActionEvent event) {

    }

    @FXML
    void btnEditCollaborator(ActionEvent event) {

    }

    @FXML
    void btnRemoveCollaborator(ActionEvent event) {

    }

    @FXML
    void btnUpdateCollaborator(ActionEvent event) {

    }

    @FXML
    void selectDocType(ActionEvent event) {

    }

    private Alert popUp(Alert.AlertType alertType, String header, String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText(header);
        alerta.setContentText(message);

        return alerta;
    }
}
