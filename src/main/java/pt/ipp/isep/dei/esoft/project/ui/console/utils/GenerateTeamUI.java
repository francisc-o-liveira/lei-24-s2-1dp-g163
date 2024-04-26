package pt.ipp.isep.dei.esoft.project.ui.console.utils;

import pt.ipp.isep.dei.esoft.project.application.controller.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GenerateTeamUI {
    private static final int MAX_COLLABS_FOR_SKILL = 20;

    public GenerateTeamController ctrl;

    private int minSize;
    private int maxSize;

    private List<Skill> skillsSelected;
    private List<Integer> numberCollabForSkill;

    public GenerateTeamUI(){
        ctrl= new GenerateTeamController();
    }

    public GenerateTeamController getController(){
        return ctrl;
    }

    public void run(){
        System.out.println("Generate a Team Automatically");
        requestData();
        skillsSelected = showSelectData();
        numberCollabForSkill = showAndRequestData();
        submitsData();
    }

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

    private void submitsData() {
        Optional<Team> team = getController().generateTeam(minSize,maxSize,skillsSelected,numberCollabForSkill);
        if (team.isPresent()) {
            System.out.println("\nTeam successfully created!");
        } else {
            System.out.println("\nTeam not created!");
        }
    }

    private List<Skill> showSelectData() {
        Scanner scan = new Scanner(System.in);
        List<Skill> skillsToShow = ctrl.getSkillList();
        List<Skill> collectSkill = new ArrayList<>();
        int i = 1;
        int  next;
        int choice;
        int numberCollaborators;
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
            }while (next<0 || next>2);
        }while (next != 1);
        return collectSkill;
    }

    private void requestData() {
        Scanner scan = new Scanner(System.in);
        int maxSize = 0;
        int minSize = 0;
        while(minSize<1 && maxSize>minSize){
            System.out.print("Introduce the Minimum Size for the Team: ");
            minSize = scan.nextInt();
        }
        while(maxSize>minSize){
            System.out.print("Introduce the Maximum Size for the Team: ");
            maxSize = scan.nextInt();
        }
    }

    public static boolean getSelected(Collaborator c){
        Scanner scan=new Scanner(System.in);
        boolean collaboratorSelected=false;
        System.out.print("Select?");
        int option=scan.nextInt();
        if(option==1){
            collaboratorSelected=true;
        }
        return collaboratorSelected;

    }
}
