package pt.ipp.isep.dei.esoft.project.ui.console.team;

import pt.ipp.isep.dei.esoft.project.application.controller.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GenerateTeamUI implements Runnable {
    private static final int MAX_COLLABS_FOR_SKILL = 20;

    public GenerateTeamController ctrl;

    private int minSize;
    private int maxSize;

    private List<Skill> skillsSelected;
    private List<Integer> numberCollabForSkill;
    /**
     * This instance represent the team created by the Generate Team Serv.
     */
    private Team teamCreated;

    /**
     * This method constructor, start the UI, creating a new GenerateTeamController.
     */
    public GenerateTeamUI(){
        ctrl= new GenerateTeamController();
    }

    /**
     * This method getController return the existing instance for the controller
     * @return ctrl instance
     */

    public GenerateTeamController getController(){
        return ctrl;
    }


    // If the system don't have sufficient collaborators to generate a Team, the system need to notify the situation. --- AC2

    /**
     * This method correspond to Runnable Method for the UI
     * This is the main method
     */
    public void run(){
        try {
            System.out.println("Generate a Team Automatically");
            requestData();
            skillsSelected = showSelectData();
            numberCollabForSkill = showAndRequestData();
            teamCreated=submitsDataAndShow();
            requestDataOrConfirmation();
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    private void requestDataOrConfirmation() {
        int option=-1;
        Scanner scan = new Scanner(System.in);
        do {
            if(option==2){
                System.out.println("Feature in Developer Mode");
            }
            System.out.println("1 - Confirm Team Operation");
            System.out.println("2 - Edit Team Operation");
            option= Utils.readIntegerFromConsole("Introduce option:");
        }while (option<=0 || option>1);
        if(option==1){
            submitsData();
        }
    }

    /**
     * This method submits the data introduced by user
     * @return
     */

    private boolean submitsData() {
        return ctrl.saveTeam(teamCreated);
    }

    /**
     * This method show the skill select by the user and ask to introduce the number of collabs fot this skill
     * @return List
     */

    private List<Integer> showAndRequestData() {
        List<Integer> numbCollabPerSkill=new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        int number;
        System.out.println("Introduce the number of Collaborator per Skill");
        for(Skill s : skillsSelected){
            do {
                System.out.printf("%s -- Number Collaborators:", s);
                number = scan.nextInt();
            }while (number<0 || number>MAX_COLLABS_FOR_SKILL);
            numbCollabPerSkill.add(number);
        }
        return numbCollabPerSkill;
    }

    /**
     * This method submits the data introduced by the user and generate the team, after this show the team if it is generated
     * @return Team Object
     */

    private Team submitsDataAndShow() {
        Optional<Team> team = getController().generateTeam(minSize,maxSize,skillsSelected,numberCollabForSkill);
        if (team.isPresent()) {
            System.out.println("\nTeam successfully created!");
            System.out.println(team.get());
        } else {
            System.out.println("\nTeam not created!");
        }
        return team.get();
    }

    /**
     * This method show the skill list and ask the user select the Skills that want to generate the team!
     * @return List of skills selected by the user
     */

    private List<Skill> showSelectData() {
        Scanner scan = new Scanner(System.in);
        List<Skill> skillsToShow = ctrl.getSkillList();
        List<Skill> collectSkill = new ArrayList<>();
        int i = 1;
        int  next;
        int choice;
        do{
            System.out.println("Select one skill for the team:");
            i = 1;
            for(Skill s : skillsToShow){
                System.out.printf("%d --- %s",i,s);
                i++;
            }
            do {
                System.out.print("Choice:");
                choice = scan.nextInt();
            }while(choice>i-1 || choice < 1);
            collectSkill.add(skillsToShow.get(choice-1));
            do{
                System.out.print("You want:");
                System.out.println("1 ---- Continue");
                System.out.println("2 ---- Add more");
                next = scan.nextInt();
            }while (next<=0 || next>2);
        }while (next != 1);
        return collectSkill;
    }

    /**
     * This method represent the request data method when the user introduce the minimum size team and the maximum size team
     */

    private void requestData() {
        Scanner scan = new Scanner(System.in);
        while(minSize<1 || maxSize>minSize){
            System.out.print("Introduce the Minimum Size for the Team: ");
            minSize = scan.nextInt();
        }
        do{
            System.out.print("Introduce the Maximum Size for the Team: ");
            maxSize = scan.nextInt();
        }while(maxSize<minSize);
    }
}
