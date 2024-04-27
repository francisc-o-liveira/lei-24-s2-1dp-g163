package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.util.Callback;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterCollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageCollaboratorsUI {

    public Stage stage = LoginUI.getMainStage();
    public RegisterCollaboratorController ctrl;

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
    private ChoiceBox docType;

    @FXML
    public TableView<Collaborator> tableCollaborators;

    @FXML
    public TableColumn<Collaborator, Boolean> columnSelect;
    @FXML
    public TableColumn<Collaborator, Integer> columnIDNumber;
    @FXML
    public TableColumn<Collaborator, DocType> columnDocType;
    @FXML
    public TableColumn<Collaborator, String> columnName;
    @FXML
    public TableColumn<Collaborator, JobCategory> columnJobCategory;
    @FXML
    public TableColumn<Collaborator, Void> columnButtonsDetails;

    public ManageCollaboratorsUI(){
        ctrl= new RegisterCollaboratorController();
    }

    @FXML
    public void btnAddCollaborator(ActionEvent event) {

    }

    @FXML
    public void btnEditCollaborator(ActionEvent event) {

    }

    @FXML
    void btnRemoveCollaborator(ActionEvent event) {

    }

    @FXML
    void btnUpdateCollaborator(ActionEvent event) {

    }

    public void setTableCollaborators(){
        List<DocType.Type> valuesDocType=new ArrayList<>();
        for(int i=0; i<DocType.Type.values().length; i++){
            valuesDocType.add(DocType.Type.values()[i]);
        }
        docType=new ChoiceBox<>(FXCollections.observableList(valuesDocType));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnIDNumber.setCellValueFactory(new PropertyValueFactory<>("docIDNumber"));
        columnDocType.setCellValueFactory(new PropertyValueFactory<>("docType"));
        columnJobCategory.setCellValueFactory(new PropertyValueFactory<>("jobCategory"));

    }


    private Alert popUp(Alert.AlertType alertType, String header, String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText(header);
        alerta.setContentText(message);

        return alerta;
    }

    private Alert popUpOfVerifications(Alert.AlertType alertType, List<String> messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        StringBuilder contentText = new StringBuilder();
        for (String message : messages) {
            contentText.append(message).append("\n");
        }
        alerta.setContentText(contentText.toString());

        return alerta;
    }

    @FXML
    public void doLogout(ActionEvent event) throws IOException {
        Alert popUp= new Alert(Alert.AlertType.CONFIRMATION);

        popUp.setHeaderText("Logging Out");
        popUp.setContentText("Do you wish to log out?");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

        if (popUp.showAndWait().get() == ButtonType.OK) {
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/SceneLogin.fxml"));
            Parent root= fxmlLoader.load();
            Scene scene= new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_HRM.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
