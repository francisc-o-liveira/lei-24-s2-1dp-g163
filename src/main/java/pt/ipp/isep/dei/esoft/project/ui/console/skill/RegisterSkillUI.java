package pt.ipp.isep.dei.esoft.project.ui.console.skill;

import pt.ipp.isep.dei.esoft.project.application.controller.collaboratorSystem.RegisterSkillController;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RegisterSkillUI implements Runnable {
    private String skillName;
    private String fileName;
    /**
     * This variable correspond to the controller
     */
    private RegisterSkillController ctrl;

    /**
     * This method is the method constructor
     */
    public RegisterSkillUI(){
        ctrl=new RegisterSkillController();
    }

    /**
     * This method get the Controller instance
     * @return Register Skill controller
     */

    private RegisterSkillController getController(){
        return ctrl;
    }

    /**
     * This method is the run() method of the User Interface
     */

    public void run(){
        System.out.println("----- Register a Skill -----");
        int option = selectMethodToAddSkill();
        if (option==1){
            try {
                skillName=requestData();
                submitsData();
            }catch (CloneNotSupportedException e){
                System.out.println("Skill Already Exist / Not Supported");
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
        else {
            fileName=requestDataFile();
            try {
                submitsDataFile();
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found");
            }catch (CloneNotSupportedException e){
                System.out.println("Skill Already Exist / Not Supported");
            }
        }
    }

    /**
     * This method submits the data file with skills to loaded to the program
     * @throws FileNotFoundException don't find the File
     * @throws CloneNotSupportedException if the skill name already exist
     */

    private void submitsDataFile() throws FileNotFoundException, CloneNotSupportedException {
        boolean value = getController().loadSkillsByFile(fileName);
        if (value) {
            System.out.println("\nSkill's sucessfully loaded!");
        } else {
            System.out.println("\nSkill's not sucessfully loaded!");
        }
    }

    /**
     * This method request the data file with the skills to load to the program
     * @return fileName if it can execute (open)
     */

    private String requestDataFile() {
        Scanner scan = new Scanner(System.in);
        String fileName = null;
        File file;
        do{
            fileName=Utils.readLineFromConsole("Please input the Skill Name:");
            file = new File(fileName);
        }while(!file.canExecute());
        return fileName;

    }

    /**
     * This method it is for select a method to register a new skill
     * @return option selected
     */

    private int selectMethodToAddSkill() {
        Scanner scan=new Scanner(System.in);
       int option=0;
        do {
            System.out.println("1 - Add Skill by skill name");
            System.out.println("2 - Add Skill's by file");
            option = Utils.readIntegerFromConsole("Select an option:");
        }while(option<1 || option>2);
        return option;
    }

    /**
     * This method submits the skillName
     */
    private void submitsData() throws CloneNotSupportedException {
            boolean value = getController().RegisterSkill(skillName);
            if (value) {
                System.out.println("\nSkill successfully registed!");
            } else {
                System.out.println("\nSkill not registed!");
            }
    }

    /**
     * This method request the data needed to register one skill by her name
     * @return the skill name if it is verified
     */
    private String requestData() {
        Scanner scan = new Scanner(System.in);
        String skillName;
            do{
                skillName= Utils.readLineFromConsole("\"Please input the Skill Name:\"");
            }while(skillName.isEmpty());
        return skillName;
    }


}
