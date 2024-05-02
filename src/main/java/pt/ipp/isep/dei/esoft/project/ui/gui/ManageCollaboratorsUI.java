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
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageCollaboratorsUI {

    public Stage stage = LoginUI.getMainStage();
    public RegisterCollaboratorController ctrl;


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
    //@FXML
    //private ComboBox selectedjobCategory;

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

    public ManageCollaboratorsUI() {
        ctrl = new RegisterCollaboratorController();
    }

    @FXML
    public void btnAddCollaborator(ActionEvent event) {
        admissionDate = new Date(dateAdmission.getValue().getYear(), dateAdmission.getValue().getMonthValue(), dateAdmission.getValue().getDayOfMonth());
        birthday = new Date(dateBirthday.getValue().getYear(), dateBirthday.getValue().getMonthValue(), dateBirthday.getValue().getDayOfMonth());
        typeOfDocument = (DocType.Type) docType.getValue();
        String nameCollab= name.getText();
        String address= addressStreet.getText();
        String addresszipcode=addressZipCode.getText();
        String addresscity=addressCity.getText();
        String e_mail=email.getText();
        String phone=phoneNumber.getText();

        if(nameCollab.isEmpty() || address.isEmpty() || addresszipcode.isEmpty() || addresscity.isEmpty() || e_mail.isEmpty() || phone.isEmpty()){
            popUpOfVerifications(Alert.AlertType.ERROR, "The Collaborator is empty");
        } else {
            try {
                ctrl.registerCollaborator(name.getText(), birthday, admissionDate, addressStreet.getText(), addressCity.getText(), addressZipCode.getText(), phoneNumber.getText(), email.getText(), typeOfDocument, Integer.parseInt(docIDNumber.getText()), jobCategory);
            } catch (CloneNotSupportedException e){
                popUpOfVerifications(Alert.AlertType.ERROR, "This Collaborator already exists.");
            }catch (IllegalArgumentException e){
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage());
            }
        }
    }

    public void registerCollaborator() {
        //jobCategory=(JobCategory) selectedjobCategory.getValue();
        admissionDate = new Date(dateAdmission.getValue().getYear(), dateAdmission.getValue().getMonthValue(), dateAdmission.getValue().getDayOfMonth());
        birthday = new Date(dateBirthday.getValue().getYear(), dateBirthday.getValue().getMonthValue(), dateBirthday.getValue().getDayOfMonth());
        typeOfDocument = (DocType.Type)docType.getValue();
        //ctrl.registerCollaborator(name.getText(), birthday, admissionDate, addressStreet.getText(), addressCity.getText(), addressZipCode.getText(), phoneNumber.getText(), email.getText(), typeOfDocument, Integer.parseInt(docIDNumber.getText()), jobCategory);*/
    }

    /*List<String> messageError = new ArrayList<>();


        if (!verifyName(name.getText())) {
            messageError.add("The introduced name is invalid");
        }
        if (!verifyAddress(addressStreet.getText(), addressZipCode.getText(), addressCity.getText())) {
            messageError.add("The introduced address is invalid");
        }
        if (!verifyEmail(email.getText())) {
            messageError.add("The introduced email is invalid");
        }
        if (!verifyPhoneNumber(Integer.parseInt(phoneNumber.getText()))) {
            messageError.add("The introduced phone number is incorrect.");
        } else if (!phoneNumber.getText().contains("+351") && !verifyInternationalPhoneNumber(phoneNumber.getText())) {
            messageError.add("The introduced phone number is incorrect.");
        }

        DocType selectedType = (DocType) docType.getSelectionModel().getSelectedItem();
        /*if (!validDocType(selectedType, Integer.parseInt(docIDNumber.getText()))) {
            messageError.add("The introduced ID Number is incorrect");
        }

        if (messageError.isEmpty()) {
        popUpOfVerifications(Alert.AlertType.ERROR, messageError).show();
    } else {
        registerCollaborator();
        popUp();
    }*/

    @FXML
    public void btnRemoveCollaborator() {
        Collaborator selectedCollaborator = tableCollaborators.getSelectionModel().getSelectedItem();
        if (selectedCollaborator != null) {
            tableCollaborators.getItems().remove(selectedCollaborator);
            ctrl.removeFromList(selectedCollaborator);
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
        //selectedjobCategory.setItems(FXCollections.observableArrayList(ctrl.getJobCategoryList()));

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnIDNumber.setCellValueFactory(new PropertyValueFactory<>("docIDNumber"));
        columnDocType.setCellValueFactory(new PropertyValueFactory<>("docType"));
        columnJobCategory.setCellValueFactory(new PropertyValueFactory<>("jobCategory"));

        columnSelect.setCellValueFactory(new PropertyValueFactory<>("selected"));
        columnSelect.setCellFactory(CheckBoxTableCell.forTableColumn(columnSelect));
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

    /*private boolean validDocType(DocType type, int docIDNumber) {
        return ctrl.validateDocType(type, docIDNumber);
    }*/


    /**
     * Verifies if e-mail is correctly inserted
     *
     * @param email of collaborator
     * @return true if e-mail is correct
     */

    private boolean verifyEmail(String email) {
        boolean value = false;
        String[] check = email.split("");
        for (String letter : check) {
            if (letter.equals("@")) {
                value = true;
            }
        }
        String[] domainPrefix;
        String[] domain;
        if (value) {
            domainPrefix = email.split("@");
            value = domainPrefix.length == 2;
            if (value) {
                domain = domainPrefix[1].split(".");
                value = domain.length == 2;
                if (value) {
                    value = domain[1].equals("com") || domain[1].equals("pt");
                }
            }
        }
        return value;
    }

    /**
     * Verifies if phone number is correctly inserted
     *
     * @param phoneNumber of collaborator
     * @return true if phoneNumber is correct
     */

    private boolean verifyPhoneNumber(int phoneNumber) {
        return (phoneNumber % 1000000000) > 0.9 && (phoneNumber % 1000000000) < 1 && ((phoneNumber / 10000000) == 91 || (phoneNumber / 10000000) == 92 || (phoneNumber / 10000000) == 93 || (phoneNumber / 10000000) == 96);
    }

    /**
     * Using Java Regex, this method verifies an international phone number
     *
     * @param phone of collaborator to validate
     * @return true if phoneNumber starts with a plus sign followed by 6 to 14 digits with optional spaces between them
     */

    private boolean verifyInternationalPhoneNumber(String phone) {
        String verify = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        Pattern pattern = Pattern.compile(verify);
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * Verifies if address is correctly inserted
     *
     * @param address        of collaborator
     * @param addressZipCode of collaborator
     * @param addressCity    of collaborator
     * @return true if address is correct
     */
    private boolean verifyAddress(String address, String addressZipCode, String addressCity) {
        return (addressZipCode.split("-").length == 2 && addressCity.split(" ").length < 5);
    }

    /**
     * Verifies if the name is valid
     *
     * @param name of collaborator
     * @return true if name contains six words or fewer
     */
    private boolean verifyName(String name) {
        String[] arrayNeedSize = name.split(" ");
        return arrayNeedSize.length <= 6;
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneLogin.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_HRM.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}