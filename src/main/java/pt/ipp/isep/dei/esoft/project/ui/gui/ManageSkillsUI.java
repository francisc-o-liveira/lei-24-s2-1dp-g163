package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterSkillController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;

import java.io.IOException;


public class ManageSkillsUI {
    public Stage stage = LoginUI.getMainStage();
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
