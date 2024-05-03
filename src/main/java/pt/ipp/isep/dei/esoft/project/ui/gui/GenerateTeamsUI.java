package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GenerateTeamsUI {

    public Stage stage= new Stage();
    public GenerateTeamController ctrl;
    ObservableList<Skill> skillsToChoose= FXCollections.observableArrayList();

    List<Skill> skills= new ArrayList<>();
    @FXML
    public TableView<Skill> tableViewTeam;
    @FXML
    public TableColumn<Skill, Boolean> colSelect;
    @FXML
    public TableColumn<Skill, Skill> colSkills;
    @FXML
    public TableColumn<Skill, Integer> colNumberCollabs;
    @FXML
    public TextField maximumTeamSize;
    @FXML
    public TextField minimumTeamSize;

    private List<Skill> skillsSelectedForTeam;
    private List<Integer> numberCollabsPerSkill;

    public GenerateTeamsUI(){
        ctrl = new GenerateTeamController();
        skillsSelectedForTeam= new ArrayList<>();
        numberCollabsPerSkill= new ArrayList<>();
    }
    public void setTableViewTeam(){
        Skill skill1=new Skill("skill");
        Skill skill2= new Skill("skil");

        colSkills.setCellValueFactory(new PropertyValueFactory<>("skillName"));
        colSelect.setCellValueFactory(cellData -> cellData.getValue().selectedSkill());
        colSelect.setCellFactory(CheckBoxTableCell.forTableColumn(colSelect));


        skillsToChoose.add(skill1);
        skillsToChoose.add(skill2);

        tableViewTeam.setItems(skillsToChoose);
    }

    public void getSkillsAndCollabs(){
        skillsSelectedForTeam.clear();
        for (Skill s : skillsToChoose) {
            if (s.selectedSkill().get()==true) {
                skillsSelectedForTeam.add(s);
            }
        }
    }

    @FXML
    public void btnGenerateTeam(){
        int maxTeamSize=Integer.parseInt(maximumTeamSize.getText());
        int minTeamSize=Integer.parseInt(minimumTeamSize.getText());
        getSkillsAndCollabs();
        try{
            ctrl.generateTeam(maxTeamSize, minTeamSize, skillsSelectedForTeam, numberCollabsPerSkill);
        } catch (RuntimeException e){
            popUpOfVerifications(Alert.AlertType.ERROR, "").show();
        }
    }

    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }



}
