package pt.ipp.isep.dei.esoft.project.ui.gui.collaborator;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.util.Callback;
import pt.ipp.isep.dei.esoft.project.application.controller.collaboratorSystem.AssignSkillsController;
import pt.ipp.isep.dei.esoft.project.application.controller.collaboratorSystem.RegisterCollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the View Details Collaborator UI.
 * It handles the functionality related to viewing and managing collaborator details.
 */
public class ViewDetailsCollaboratorUI {

    /**
     * Controller for registering a collaborator.
     */
    public RegisterCollaboratorController ctrl;

    /**
     * Controller for assigning skills to a collaborator.
     */
    public AssignSkillsController ctrlSkills;

    /**
     * UI manager for managing collaborators.
     */
    public ManageCollaboratorsUI manageTable;

    /**
     * Stage for viewing details.
     */
    public Stage stage;

    /**
     * Stage for adding a collaborator.
     */
    public Stage stageToAdd;

    /**
     * Date of birth of the collaborator.
     */
    private Date birthday;

    /**
     * Date of admission of the collaborator.
     */
    private Date admissionDate;

    /**
     * Type of document of the collaborator.
     */
    private DocType.Type typeOfDocument;

    /**
     * Job category of the collaborator.
     */
    private JobCategory jobCategory;

    /**
     * FXML elements for the scene
     */
    @FXML
    private TextField addressCity;

    @FXML
    private TextField addressStreet;

    @FXML
    private TextField addressZipCode;

    @FXML
    private TableColumn<Skill, Boolean> colSelect;

    @FXML
    private TableColumn<Skill, Skill> colSkills;

    @FXML
    private DatePicker dateAdmission;

    @FXML
    private DatePicker dateBirthday;

    @FXML
    private TextField docIDNumber;

    @FXML
    private ComboBox<DocType.Type> docType;

    @FXML
    private TextField email;

    @FXML
    private TextField name;

    @FXML
    private TextField phoneNumber;

    @FXML
    private ComboBox<JobCategory> selectedjobCategory;

    @FXML
    private TableView<Skill> tableAssignSkills;

    @FXML
    private Button btnEditCollaborator;

    @FXML
    private Button btnAddCollaborator;

    @FXML
    private TableView<Skill> tableSkillsAssigned;

    @FXML
    private TableColumn<Skill, String> colSkillsAssigned;

    /**
     * List of skills to assign to a collaborator.
     */
    public List<Skill> skillsToAssign = new ArrayList<>();

    /**
     * Observable list of skills to choose from.
     */
    ObservableList<Skill> skillsToChoose = FXCollections.observableArrayList();

    /**
     * Observable list of assigned skills.
     */
    ObservableList<Skill> assignedSkills = FXCollections.observableArrayList();

    /**
     * Collaborator being edited.
     */
    Collaborator editedCollaborator;

    /**
     * Constructor for ViewDetailsCollaboratorUI.
     */
    public ViewDetailsCollaboratorUI() {
        ctrl = RegisterCollaboratorController.getInstance();
        ctrlSkills = AssignSkillsController.getInstance();
        manageTable = new ManageCollaboratorsUI();
        stage = manageTable.getStageToViewDetails();
    }

    /**
     * Sets the stage for adding a collaborator.
     *
     * @param stage the stage to set
     */
    public void setStageToAdd(Stage stage) {
        this.stageToAdd = stage;
    }

    /**
     * Sets the combo boxes with available values.
     */
    public void setComboBoxes() {
        docType.setItems(FXCollections.observableArrayList(DocType.Type.values()));
        selectedjobCategory.setItems(FXCollections.observableArrayList(ctrl.getJobCategoryList()));
    }

    /**
     * Sets the visibility of the edit collaborator button.
     *
     * @param value visibility value
     */
    public void setBtnEditCollaboratorValue(boolean value) {
        btnEditCollaborator.setVisible(value);
    }

    /**
     * Sets the visibility of the add collaborator button.
     *
     * @param value visibility value
     */
    public void setBtnAddCollaboratorValue(boolean value) {
        btnAddCollaborator.setVisible(value);
    }

    /**
     * Handles the action of adding a collaborator.
     *
     * @param event the action event
     */
    @FXML
    public void btnAdd(ActionEvent event) {
        admissionDate = new Date(dateAdmission.getValue().getYear(), dateAdmission.getValue().getMonthValue(), dateAdmission.getValue().getDayOfMonth());
        birthday = new Date(dateBirthday.getValue().getYear(), dateBirthday.getValue().getMonthValue(), dateBirthday.getValue().getDayOfMonth());
        typeOfDocument = docType.getValue();
        jobCategory = selectedjobCategory.getValue();
        String nameCollab = name.getText();
        String address = addressStreet.getText();
        String addresszipcode = addressZipCode.getText();
        String addresscity = addressCity.getText();
        String e_mail = email.getText();
        String phone = phoneNumber.getText();

        if (nameCollab.isEmpty() || address.isEmpty() || addresszipcode.isEmpty() || addresscity.isEmpty() || e_mail.isEmpty() || phone.isEmpty()) {
            popUpOfVerifications(Alert.AlertType.ERROR, "The Collaborator is empty").show();
        } else {
            try {
                ctrl.registerCollaborator(name.getText(), birthday, admissionDate, addressStreet.getText(), addressZipCode.getText(), addressCity.getText(), phoneNumber.getText(), email.getText(), typeOfDocument, Integer.parseInt(docIDNumber.getText()), jobCategory);
                if (popUp().showAndWait().get() == ButtonType.OK) {
                    stageToAdd.close();
                }
            } catch (CloneNotSupportedException e) {
                popUpOfVerifications(Alert.AlertType.ERROR, "This Collaborator already exists.").show();
            } catch (IllegalArgumentException e) {
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    /**
     * Handles the action of assigning skills to a collaborator.
     *
     * @param event the action event
     */
    @FXML
    public void btnAssign(ActionEvent event) {
        Collaborator collaboratorAssigning = ctrl.getCollaboratorRepository().searchForCollaboratorByIDNumber(Integer.parseInt(docIDNumber.getText()));
        for (Skill skill : skillsToAssign) {
            try {
                ctrlSkills.assignSkills(collaboratorAssigning, skill);
            } catch (CloneNotSupportedException e) {
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
        popUpSkills().show();
        tableAssignSkills.getItems().clear();
        setTableAssignSkills();
        tableSkillsAssigned.getItems().clear();
        setTableSkillsAssigned();
    }

    /**
     * Sets up the table for assigning skills.
     */
    public void setTableAssignSkills() {
        colSkills.setCellValueFactory(new PropertyValueFactory<>("skillName"));
        colSelect.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(false));
        colSelect.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Skill, Boolean> call(TableColumn<Skill, Boolean> param) {
                return new TableCell<>() {
                    private final CheckBox checkBox = new CheckBox();

                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                            setGraphic(null);
                            return;
                        }
                        setGraphic(checkBox);
                        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                            Skill skill = (Skill) getTableRow().getItem();
                            if (isNowSelected) {
                                skillsToAssign.add(skill);
                            }
                        });
                    }
                };
            }
        });
        for (Skill skill : ctrlSkills.getAllSkills()) {
            if (editedCollaborator == null) {
                skillsToChoose.add(skill);
            } else {
                if (!editedCollaborator.verifyIfHaveSkill(skill)) {
                    skillsToChoose.add(skill);
                }
            }
        }
        tableAssignSkills.setItems(skillsToChoose);
    }

    /**
     * Sets up the table for displaying assigned skills.
     */
    public void setTableSkillsAssigned() {
        colSkillsAssigned.setCellValueFactory(new PropertyValueFactory<>("skillName"));
        for (Skill skill : ctrlSkills.getAllSkills()) {
            if (editedCollaborator.verifyIfHaveSkill(skill)) {
                assignedSkills.add(skill);
            }
        }
        tableSkillsAssigned.setItems(assignedSkills);
    }

    /**
     * Handles the action of editing a collaborator's details.
     *
     * @param event the action event
     */
    @FXML
    public void btnEdit(ActionEvent event) {
        try {
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
                editedCollaborator.setDocType(typeOfDocument, idNew);
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

                editedCollaborator.setBirthday(new Date(dateBirthday.getValue().getYear(), dateBirthday.getValue().getMonthValue(), dateBirthday.getValue().getDayOfMonth()));
                dateBirthday.setValue(null);
                editedCollaborator.setAdmissionDate(new Date(dateAdmission.getValue().getYear(), dateAdmission.getValue().getMonthValue(), dateAdmission.getValue().getDayOfMonth()));
                dateAdmission.setValue(null);

                editedCollaborator.setJobCategory(selectedjobCategory.getValue());
                selectedjobCategory.getSelectionModel().clearSelection();
            }
        } catch (IllegalArgumentException e) {
            popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    /**
     * Shows the selected collaborator details.
     *
     * @param selectedCollaborator the selected collaborator
     */
    public void showCollaboratorSelected(Collaborator selectedCollaborator) {
        editedCollaborator = selectedCollaborator;
    }

    /**
     * Puts the details of the selected collaborator into the text fields.
     *
     * @param selectedCollaborator the selected collaborator
     */
    public void putInTextFields(Collaborator selectedCollaborator) {
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

    /**
     * Creates a pop-up alert for successful collaborator addition.
     *
     * @return the alert
     */
    private Alert popUp() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Collaborator added!");

        return alert;
    }

    /**
     * Creates a pop-up alert for successful skill assignment.
     *
     * @return the alert
     */
    private Alert popUpSkills() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Skills added!");

        return alert;
    }

    /**
     * Creates a pop-up alert for validation errors.
     *
     * @param alertType the type of alert
     * @param messages  the error message
     * @return the alert
     */
    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }
}
