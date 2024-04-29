package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterSkillController;

import java.util.Scanner;

public class RegisterSkillUI implements Runnable {
    private String skillName;

    private RegisterSkillController ctrl;

    public RegisterSkillUI(){
        ctrl=new RegisterSkillController();
    }

    private RegisterSkillController getController(){
        return ctrl;
    }

    public void run(){
        System.out.println("----- Register a Skill -----");
        skillName=requestData();
        submitsData();
    }

    private void submitsData() {
        boolean value = getController().RegisterSkill(skillName);
        if (value) {
            System.out.println("\nSkill successfully registed!");
        } else {
            System.out.println("\nSkill not registed!");
        }
    }

    private String requestData() {
        Scanner scan = new Scanner(System.in);
        String skillName = null;
        do{
            System.out.print("Please input the Job Category Name:");
            skillName=scan.nextLine();
        }while(!nameVerify(skillName));
        return skillName;
    }

    private boolean nameVerify(String skillName) {
        char[] characters = skillName.toCharArray();
        if(characters==null){
            return false;
        }
        for(char c : characters){
            if(!Character.isLetter(c)){
                return false;
            }
        }
        return true;
    }
}
