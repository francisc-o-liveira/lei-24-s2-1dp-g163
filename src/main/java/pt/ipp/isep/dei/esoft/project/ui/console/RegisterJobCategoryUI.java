package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterJobCategoryController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;

import java.util.Optional;
import java.util.Scanner;

public class RegisterJobCategoryUI {
    private String jobCategoryName;

    private RegisterJobCategoryController ctrl;

    public RegisterJobCategoryUI(){
        ctrl = new RegisterJobCategoryController();
    }

    private RegisterJobCategoryController getController(){
        return ctrl;
    }

    public void run(){
        System.out.println("----- Register a Job Category -----");
        jobCategoryName=requestData();
        submitsData();
    }

    private void submitsData() {
        boolean value = getController().registerJobCategory(jobCategoryName);
        if (value) {
            System.out.println("\nJobCategory successfully registed!");
        } else {
            System.out.println("\nJobCategory not registed!");
        }
    }

    private String requestData() {
        Scanner scan = new Scanner(System.in);
        String jobName = null;
        do{
            System.out.print("Please input the Job Category Name:");
            jobName=scan.nextLine();
        }while(!nameVerify(jobName));
        return jobName;
    }

    private boolean nameVerify(String jobName) {
        char[] characters = jobName.toCharArray();
        if (characters==null){
            return false;
        }
        for(char c : characters){
            if (!Character.isLetter(c)){
                return false;
            }
        }
        return true;
    }

}
