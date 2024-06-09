package pt.ipp.isep.dei.esoft.project.ui.gui.team;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipp.isep.dei.esoft.project.core.application.controller.teamSystem.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.core.application.domain.team.Team;

/**
 * UI Controller for viewing details of a team.
 */
public class ViewDetailsTeamUI {

    /** Variable for the selected team to view details*/
    public Team selectedTeam;

    /** Controller */
    public GenerateTeamController ctrl;


    @FXML
    private TableColumn<Collaborator, String> colCollabs;
    @FXML
    private TableColumn<Skill, String> colSkills;
    @FXML
    private TableView<Collaborator> tableCollabs;
    @FXML
    private TableView<Skill> tableSkills;

    /**
     * Constructor for ViewDetailsTeamUI.
     * Initializes the controller.
     */
    public ViewDetailsTeamUI(){
        ctrl= GenerateTeamController.getInstance();
    }

    /**
     * Sets up the table view for displaying collaborators.
     */
    public void setTableCollabs(){
        colCollabs.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableCollabs.getItems().addAll(selectedTeam.getObservableTeamList());
    }

    /**
     * Sets up the table view for displaying skills.
     */
    public void setTableSkills(){
        colSkills.setCellValueFactory(new PropertyValueFactory<>("skillName"));
        tableSkills.getItems().addAll(selectedTeam.getObservableSkillList());
    }

    /**
     * Sets the selected team to view its details.
     *
     * @param team the team to view details
     */
    public void showTeamSelected(Team team){
        selectedTeam=team;
    }
}
