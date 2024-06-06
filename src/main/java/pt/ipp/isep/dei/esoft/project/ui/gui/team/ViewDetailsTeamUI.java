package pt.ipp.isep.dei.esoft.project.ui.gui.team;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipp.isep.dei.esoft.project.application.controller.teamSystem.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;

public class ViewDetailsTeamUI {

    public Team selectedTeam;
    public GenerateTeamController ctrl;

    @FXML
    private TableColumn<Collaborator, String> colCollabs;

    @FXML
    private TableColumn<Skill, String> colSkills;

    @FXML
    private TableView<Collaborator> tableCollabs;

    @FXML
    private TableView<Skill> tableSkills;

    public ViewDetailsTeamUI(){
        ctrl= GenerateTeamController.getInstance();
    }

    public void setTableCollabs(){
        colCollabs.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableCollabs.getItems().addAll(selectedTeam.getObservableTeamList());
    }

    public void setTableSkills(){
        colSkills.setCellValueFactory(new PropertyValueFactory<>("skillName"));
        tableSkills.getItems().addAll(selectedTeam.getObservableSkillList());
    }
    public void showTeamSelected(Team team){
        selectedTeam=team;
    }
    @FXML
    void btnEdit(ActionEvent event) {
        //TODO: edit team implementation
    }
}
