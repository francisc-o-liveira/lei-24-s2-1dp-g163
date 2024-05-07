package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipp.isep.dei.esoft.project.application.controller.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;

public class ViewDetailsTeamUI {

    public Team selectedTeam;
    public GenerateTeamController ctrl;

    public ObservableList<Collaborator> collabsOfTeam = FXCollections.observableArrayList();
    public ObservableList<Skill> skillsOfTeam= FXCollections.observableArrayList();

    @FXML
    private TableColumn<Team, String> colCollabs;

    @FXML
    private TableColumn<Skill, String> colSkills;

    @FXML
    private TableView<Collaborator> tableCollabs;

    @FXML
    private TableView<Skill> tableSkills;

    public ViewDetailsTeamUI(){
        ctrl=new GenerateTeamController();
    }

    public void setTableCollabs(){
        colCollabs.setCellValueFactory(new PropertyValueFactory<>("TeamList")); // the retrieval cannot be made with this property!!
        for(Collaborator c : selectedTeam.getTeamList()){
            collabsOfTeam.add(c);
        }

        tableCollabs.getItems().addAll(collabsOfTeam);
    }

    public void setTableSkills(){
        colSkills.setCellValueFactory(new PropertyValueFactory<>("TeamList")); // the retrieval cannot be made with this property!!
        for(Skill s : selectedTeam.getSkills()){
            skillsOfTeam.add(s);
        }

        tableSkills.getItems().addAll(skillsOfTeam);
    }
    public void showTeamSelected(Team team){
        selectedTeam=team;
    }
    @FXML
    void btnEdit(ActionEvent event) {
        //TODO: edit team implementation
    }
}
