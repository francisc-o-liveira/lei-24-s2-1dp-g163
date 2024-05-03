package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.util.Callback;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterCollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;


import javax.print.Doc;
import javax.swing.text.View;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ManageCollaboratorsUI {

    public Stage stage = LoginUI.getMainStage();
    public Stage stageToViewDetails = new Stage();
    public RegisterCollaboratorController ctrl;
    public ViewDetailsCollaboratorUI viewDetailsCollaboratorUI;
    public AuthenticationController ctrlAuth;


    /**
     * All the parameters needed to register a collaborator
     */
    private Date birthday;
    private Date admissionDate;
    private DocType.Type typeOfDocument;
    private JobCategory jobCategory;

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
    private ComboBox docType;
    @FXML
    private ComboBox selectedjobCategory;

    @FXML
    public TableView<Collaborator> tableCollaborators;

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

    public ManageCollaboratorsUI() {
        ctrl = new RegisterCollaboratorController();
        ctrlAuth= new AuthenticationController();
    }

    public TableView<Collaborator> getTableCollaborators(){
        setTableCollaborators();
        return tableCollaborators;
    }

    public Stage getStageToViewDetails(){
        return stageToViewDetails;
    }

    @FXML
    public void btnAddCollaborator(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_ViewDetails.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ViewDetailsCollaboratorUI uiToAdd=fxmlLoader.getController();
        uiToAdd.setTableAssignSkills();
        uiToAdd.setComboBoxes();
    }

    @FXML
    public void btnRemoveCollaborator() {
        Collaborator selectedCollaborator = tableCollaborators.getSelectionModel().getSelectedItem();
        if (selectedCollaborator != null) {
            Alert popUp = new Alert(Alert.AlertType.CONFIRMATION);

            popUp.setHeaderText("Removing Collaborator");
            popUp.setContentText("Do you want to remove this collaborator?");
            ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
            ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

            if (popUp.showAndWait().get() == ButtonType.OK) {
                tableCollaborators.getItems().remove(selectedCollaborator);
                ctrl.removeFromList(selectedCollaborator);
            }
        }
    }

    @FXML
    public void btnEditCollaborator() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_ViewDetails.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        ViewDetailsCollaboratorUI uiToAdd=fxmlLoader.getController();
        Collaborator selectedCollaborator = tableCollaborators.getSelectionModel().getSelectedItem();
        uiToAdd.setTableAssignSkills();
        uiToAdd.putInTextFields(selectedCollaborator);
    }

    public Collaborator getSelectedCollaborator(){
        return tableCollaborators.getSelectionModel().getSelectedItem();
    }

    private void putInTextFields() {
        Collaborator selectedCollaborator = tableCollaborators.getSelectionModel().getSelectedItem();

        String editName = selectedCollaborator.getName();
        name.setText(editName);

        String editDocIDNumber = String.valueOf(selectedCollaborator.getDocIDNumber());
        docIDNumber.setText(editDocIDNumber);

        String editEmail = selectedCollaborator.getEmail();
        email.setText(editEmail);

        String editPhoneNumber = String.valueOf(selectedCollaborator.getPhoneNumber());
        phoneNumber.setText(editPhoneNumber);

        String editCity = selectedCollaborator.getAddressCity();
        addressCity.setText(editCity);

        String editStreet = selectedCollaborator.getAddress();
        addressStreet.setText(editStreet);

        String editZipCode = selectedCollaborator.getAddressZipCode();
        addressZipCode.setText(editZipCode);

        LocalDate birthday = selectedCollaborator.getBirthdayLocal();
        dateBirthday.setValue(birthday);

        LocalDate admissionDate = selectedCollaborator.getAdmissionDateLocal();
        dateAdmission.setValue(admissionDate);

        DocType.Type typeOfDocument = selectedCollaborator.getDocType();
        docType.setValue(typeOfDocument);

        JobCategory jobCategory = selectedCollaborator.getJobCategory();
        selectedjobCategory.setValue(jobCategory);
    }


    public void setTableCollaborators() {

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnIDNumber.setCellValueFactory(new PropertyValueFactory<>("docIDNumber"));
        columnDocType.setCellValueFactory(new PropertyValueFactory<>("docType"));
        columnJobCategory.setCellValueFactory(new PropertyValueFactory<>("jobCategory"));

        columnButtonsDetails.setCellFactory(new Callback<
                TableColumn<Collaborator, Void>, TableCell<Collaborator, Void>>() {
            @Override
            public TableCell<Collaborator, Void> call(TableColumn<Collaborator, Void> param) {
                return new TableCell<Collaborator, Void>() {
                    private final javafx.scene.control.Button btn = new Button("View Details");

                    {

                        btn.setOnAction((ActionEvent event) -> {
                            int collaboratorID = Integer.parseInt(docIDNumber.getText());
                            Collaborator collaborator = ctrl.getCollaboratorRepository().searchForCollaborator(collaboratorID); //this thing cannot be accessed like this
                            try {
                                showMore(collaborator);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        });
    }

    //see this again later
    public void showMore(Collaborator collab) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_ViewDetails.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stageToViewDetails.setScene(scene);
        stageToViewDetails.show();
        ViewDetailsCollaboratorUI ui=fxmlLoader.getController();
        ui.putInTextFields(getSelectedCollaborator());
    }

    @FXML
    public void doLogout(ActionEvent event) throws IOException {
        Alert popUp = new Alert(Alert.AlertType.CONFIRMATION);

        popUp.setHeaderText("Logging Out");
        popUp.setContentText("Do you wish to log out?");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

        if (popUp.showAndWait().get() == ButtonType.OK) {
            ctrlAuth.doLogout();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneLogin.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader ;
        try {
            UserRoleDTO role = ctrlAuth.getAtualUserRole();
            if (role.getDescription().equals(AuthenticationController.ROLE_HRM)){
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_HRM.fxml"));
            } else if (role.getDescription().equals(AuthenticationController.ROLE_HRM)) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_VFM.fxml"));
            }else {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_GSM.fxml"));
            }
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (ArrayIndexOutOfBoundsException e){
            popUpOfVerifications(Alert.AlertType.WARNING,"PLEASE RESTART THIS APPLICATION").show();
        }
    }

    private Alert popUpOfVerifications(Alert.AlertType alertType, String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(message);

        return alerta;
    }
}