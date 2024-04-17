package pt.ipp.isep.dei.esoft.project.ui.console.utils;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;

import java.util.Scanner;

public class GenerateTeamUI {

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
