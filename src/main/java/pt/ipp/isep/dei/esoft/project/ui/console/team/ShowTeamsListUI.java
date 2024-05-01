package pt.ipp.isep.dei.esoft.project.ui.console.team;

import pt.ipp.isep.dei.esoft.project.application.controller.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;

import java.util.List;

public class ShowTeamsListUI implements Runnable{

    /** Variable to represent the controller */
    private GenerateTeamController ctrl;

    /** Initializes the controller */
    public ShowTeamsListUI(){
        ctrl=new GenerateTeamController();
    }

    /** Gets the controller */
    public GenerateTeamController getController(){
        return ctrl;
    }
    @Override
    public void run(){
        System.out.println("---- Teams List ----\n");
        displayTeamsList();
    }

    /** Method to display the List of Teams */
    public void displayTeamsList(){
        List<Team> teamList=getController().getTeams();
        if(teamList==null){
            System.out.print("No collaborators are registered on the system.");
        } else {
            for(Team t : teamList){
                System.out.println(t+"\n");
            }
        }
    }
}
