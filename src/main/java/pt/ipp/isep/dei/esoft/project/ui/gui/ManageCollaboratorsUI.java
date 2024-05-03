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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.util.Callback;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterCollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageCollaboratorsUI {

    public Stage stage = LoginUI.getMainStage();
    public RegisterCollaboratorController ctrl;
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

    @FXML
    public void btnAddCollaborator(ActionEvent event) {
        admissionDate = new Date(dateAdmission.getValue().getYear(), dateAdmission.getValue().getMonthValue(), dateAdmission.getValue().getDayOfMonth());
        birthday = new Date(dateBirthday.getValue().getYear(), dateBirthday.getValue().getMonthValue(), dateBirthday.getValue().getDayOfMonth());
        typeOfDocument = (DocType.Type) docType.getValue();
        jobCategory=(JobCategory) selectedjobCategory.getValue();
        String nameCollab= name.getText();
        String address= addressStreet.getText();
        String addresszipcode=addressZipCode.getText();
        String addresscity=addressCity.getText();
        String e_mail=email.getText();
        String phone=phoneNumber.getText();

        if(nameCollab.isEmpty() || address.isEmpty() || addresszipcode.isEmpty() || addresscity.isEmpty() || e_mail.isEmpty() || phone.isEmpty()){
            popUpOfVerifications(Alert.AlertType.ERROR, "The Collaborator is empty").show();
        } else {
            try {
                ctrl.registerCollaborator(name.getText(), birthday, admissionDate, addressStreet.getText(), addressZipCode.getText(), addressCity.getText(), phoneNumber.getText(), email.getText(), typeOfDocument, Integer.parseInt(docIDNumber.getText()), jobCategory);
                popUp();
            } catch (CloneNotSupportedException e){
                popUpOfVerifications(Alert.AlertType.ERROR, "This Collaborator already exists.").show();
            }catch (IllegalArgumentException e){
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
        name.clear();
        addressZipCode.clear();
        addressStreet.clear();
        addressCity.clear();
        email.clear();
        phoneNumber.clear();
        docIDNumber.clear();
        dateBirthday.setValue(null);
        dateAdmission.setValue(null);
        docType.getSelectionModel().clearSelection();
        selectedjobCategory.getSelectionModel().clearSelection();
        ObservableList<Collaborator> listForTable= FXCollections.observableArrayList(ctrl.getCollaboratorList());
        tableCollaborators.getItems().clear();
        tableCollaborators.setItems(listForTable);
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
    public void btnEditCollaborator() {
        Collaborator editedCollaborator = tableCollaborators.getSelectionModel().getSelectedItem();
        if (editedCollaborator != null) {
            String newName = name.getText();
            editedCollaborator.setName(newName);
            name.clear();

            String newZipCode = addressZipCode.getText();
            editedCollaborator.setAddressZipCode(newZipCode);
            addressZipCode.clear();

            String newDocIDNumber = docIDNumber.getText();
            int idNew = Integer.parseInt(newDocIDNumber);
            typeOfDocument = (DocType.Type) docType.getValue();
            editedCollaborator.setDocType(typeOfDocument,idNew);
            docIDNumber.clear();

            String newEmail = email.getText();
            editedCollaborator.setEmail(newEmail);
            email.clear();

            String newPhoneNumber = phoneNumber.getText();
            editedCollaborator.setPhoneNumber(newPhoneNumber);
            phoneNumber.clear();

            String newCity = addressCity.getText();
            editedCollaborator.setAddressCity(newCity);
            addressCity.clear();

            String newStreet = addressStreet.getText();
            editedCollaborator.setAddress(newStreet);
            addressStreet.clear();

            tableCollaborators.refresh();
        }
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
    }


    public void setTableCollaborators() {
        ComboBox<DocType> cbxStatus = new ComboBox<>();
        docType.setItems(FXCollections.observableArrayList(DocType.Type.values()));
        selectedjobCategory.setItems(FXCollections.observableArrayList(ctrl.getJobCategoryList()));

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
        tableCollaborators.setOnMouseClicked(mouseEvent -> putInTextFields());
    }

    //see this again later
    public void showMore(Collaborator collab) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hi.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private Alert popUp() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);

        alerta.setHeaderText("Information");
        alerta.setContentText("Collaborator added!");

        return alerta;
    }

    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
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
            if (role.equals(ctrlAuth.ROLE_HRM)){
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_HRM.fxml"));
            } else if (role.equals(ctrlAuth.ROLE_HRM)) {
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
}