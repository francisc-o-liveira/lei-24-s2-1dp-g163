package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterSkillController;
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
            skillName=requestData();
            submitsData();
        }
        else {
            fileName=requestDataFile();
            try {
                submitsDataFile();
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found");
            }
        }
    }

    /**
     * This method submits the data file with skills to loaded to the program
     * @throws FileNotFoundException if don't find the File
     */

    private void submitsDataFile() throws FileNotFoundException {
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
    private void submitsData() {
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
        String skillName = null;
        try {
            do{
                skillName= Utils.readLineFromConsole("\"Please input the Skill Name:\"");
            }while(!nameVerify(skillName));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return skillName;
    }

    /**
     * This method verify the name for the skill
     * @param skillName receive the skillName introduced by user on console
     * @return true if the skill name it is valid to register a new Skill
     */

    private boolean nameVerify(String skillName) {
        char[] characters = skillName.toCharArray();
        if(characters==null){
            throw new NullPointerException("The Skill Name is empty please introduce name");
        }
        for(char c : characters){
            if(!Character.isLetter(c)){
                throw new IllegalArgumentException("The Skill Name dont accept Special Characters or Numbers");
            }
        }
        return true;
    }
}
