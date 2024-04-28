package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterSkillController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;


public class ManageSkillsUI {

    @FXML
    public TextField introducingSkill;

    @FXML
    public TableView<Skill> tableSkills;

    @FXML
    public TableColumn<Skill, String> colSkills;

    public RegisterSkillController ctrl;

    public ManageSkillsUI(){
        ctrl=new RegisterSkillController();
    }

    public void setSkillTable(){
        colSkills.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getSkillName());
        });
        tableSkills.getItems().clear();
        for(Skill s : ctrl.getSkillList()){
            tableSkills.getItems().add(s);
        }
        tableSkills.setOnMouseClicked(mouseEvent -> putInTextField());
    }

    @FXML
    public void btnAdd(){
        String skillName = introducingSkill.getText();
        if (skillName.isEmpty()){
            popUpOfVerifications(Alert.AlertType.ERROR, "The Skill Name is Empty").show();
            return;
        } else if(!verifySkillName(skillName)){
            popUpOfVerifications(Alert.AlertType.ERROR, "The Skill Name is invalid").show();
            return;
        }
        ctrl.RegisterSkill(skillName);
        introducingSkill.clear();
        ObservableList<Skill> listForTable= FXCollections.observableArrayList(ctrl.getSkillList());
        tableSkills.getItems().clear();
        tableSkills.setItems(listForTable);
    }

    @FXML
    public void btnRemove(){
        Skill selectedSkill = tableSkills.getSelectionModel().getSelectedItem();
        if(selectedSkill != null){
            tableSkills.getItems().remove(selectedSkill);
            ctrl.removeFromList(selectedSkill);
        }
    }

    @FXML
    public void btnEdit(){
        Skill editedSkill = tableSkills.getSelectionModel().getSelectedItem();
        if (editedSkill != null) {
            String newSkillName = introducingSkill.getText();
            editedSkill.setSkillName(newSkillName);
            introducingSkill.clear();
            tableSkills.refresh();
        }
    }

    private void putInTextField(){
        Skill selectedSkill=tableSkills.getSelectionModel().getSelectedItem();
        String editSkill= selectedSkill.getSkillName();
        introducingSkill.setText(editSkill);
    }

    private boolean verifySkillName(String skillName) {
        char[] testSkill = skillName.trim().toCharArray();
        for(char x : testSkill){
            if(!Character.isLetter(x)){
                return false;
            }
        }
        return true;
    }

    private Alert popUpOfVerifications(Alert.AlertType alertType, String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(message);

        return alerta;
    }
}
