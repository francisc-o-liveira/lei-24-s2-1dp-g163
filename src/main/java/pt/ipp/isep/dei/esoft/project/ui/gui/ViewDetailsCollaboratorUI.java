package pt.ipp.isep.dei.esoft.project.ui.gui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

public class ViewDetailsCollaboratorUI {

    public RegisterCollaboratorController ctrl;
    public AssignSkillsController ctrlSkills;

    public ManageCollaboratorsUI manageTable;
    public Stage stage;
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

    public List<Skill> skillsToAssign=new ArrayList<>();

    ObservableList<Skill> skillsToChoose= FXCollections.observableArrayList();

    Collaborator editedCollaborator;

    public ViewDetailsCollaboratorUI(){
        ctrl=new RegisterCollaboratorController();
        ctrlSkills= new AssignSkillsController();
        manageTable= new ManageCollaboratorsUI();
        stage=manageTable.getStageToViewDetails();
    }


    public void setComboBoxes(){
        docType.setItems(FXCollections.observableArrayList(DocType.Type.values()));
        selectedjobCategory.setItems(FXCollections.observableArrayList(ctrl.getJobCategoryList()));
    }

    public void setBtnEditCollaboratorValue(boolean value){
        btnEditCollaborator.setVisible(value);
    }

    public void setBtnAddCollaboratorValue(boolean value){
        btnAddCollaborator.setVisible(value);
    }

    @FXML
    public void btnAdd(ActionEvent event) {
        admissionDate = new Date(dateAdmission.getValue().getYear(), dateAdmission.getValue().getMonthValue(), dateAdmission.getValue().getDayOfMonth());
        birthday = new Date(dateBirthday.getValue().getYear(), dateBirthday.getValue().getMonthValue(), dateBirthday.getValue().getDayOfMonth());
        typeOfDocument = docType.getValue();
        jobCategory=selectedjobCategory.getValue();
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
                popUp().show();
            } catch (CloneNotSupportedException e){
                popUpOfVerifications(Alert.AlertType.ERROR, "This Collaborator already exists.").show();
            }catch (IllegalArgumentException e){
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    public void btnAssign(ActionEvent event) {
        getSkillsToAssign();
            Collaborator collaboratorAssigning=ctrl.getCollaboratorRepository().searchForCollaboratorByIDNumber(Integer.parseInt(docIDNumber.getText()));
            for(Skill skill : skillsToAssign){
                try{
                    ctrlSkills.assignSkills(collaboratorAssigning, skill);
                    popUpSkills().show();
                    tableAssignSkills.getItems().clear();
                    setTableAssignSkills();
                } catch (CloneNotSupportedException e) {
                    popUpOfVerifications(Alert.AlertType.ERROR, "s").show();
                }
            }
    }

    public void getSkillsToAssign(){
        for (Skill s : skillsToChoose) {
            if (s.selectedSkill().get()==true) {
                skillsToAssign.add(s);
            }
        }
    }

    public void setTableAssignSkills(){
        colSkills.setCellValueFactory(new PropertyValueFactory<>("skillName"));
        colSelect.setCellValueFactory(cellData -> cellData.getValue().selectedSkill());
        colSelect.setCellFactory(CheckBoxTableCell.forTableColumn(colSelect));

        for(Skill skill : ctrlSkills.getAllSkills()){
            if(editedCollaborator==null){
               skillsToChoose.add(skill);
            } else {
                if(!editedCollaborator.verifyIfHaveSkill(skill)){
                    skillsToChoose.add(skill);
                    skill.setSelecting(false);
                }
            }
        }
        tableAssignSkills.setItems(skillsToChoose);
    }

    @FXML
    public void btnEdit(ActionEvent event) {
        try{
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

            editedCollaborator.setBirthday(new Date(dateBirthday.getValue().getYear(), dateBirthday.getValue().getMonthValue(), dateBirthday.getValue().getDayOfMonth()));
            dateBirthday.setValue(null);
            editedCollaborator.setAdmissionDate(new Date(dateAdmission.getValue().getYear(), dateAdmission.getValue().getMonthValue(), dateAdmission.getValue().getDayOfMonth()));
            dateAdmission.setValue(null);

            editedCollaborator.setJobCategory(selectedjobCategory.getValue());
            selectedjobCategory.getSelectionModel().clearSelection();
        }
        } catch (IllegalArgumentException e){
            popUpOfVerifications(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void showCollaboratorSelected(Collaborator selectedCollaborator){
        editedCollaborator=selectedCollaborator;
    }
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

    private Alert popUp() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Collaborator added!");

        return alert;
    }

    private Alert popUpSkills() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Information");
        alert.setContentText("Skills added!");

        return alert;
    }

    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }
}
