package pt.ipp.isep.dei.esoft.project.ui.gui;

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
        Skill skill1=new Skill("skill1");
        Skill skill2= new Skill("skill2");
        skills.add(skill1);
        skills.add(skill2);

        colSkills.setCellValueFactory(new PropertyValueFactory<>("skillName"));
        colSelect.setCellValueFactory(new PropertyValueFactory<>("selectedForTheTeam"));
        colSelect.setCellFactory(CheckBoxTableCell.forTableColumn(colSelect));

        for(Skill s : skills){
            tableViewTeam.getItems().add(s);
        }
    }

    public void getSkillsAndCollabs(){
        for(Skill s : skills){
            if(colSelect.getCellObservableValue(s).getValue()){
                skillsSelectedForTeam.add(s);
                numberCollabsPerSkill.add(colNumberCollabs.getCellObservableValue(s).getValue());
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
            popUpOfVerifications(Alert.AlertType.ERROR, "");
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
