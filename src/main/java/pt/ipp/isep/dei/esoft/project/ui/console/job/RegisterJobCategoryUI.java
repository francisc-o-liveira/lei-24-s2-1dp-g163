package pt.ipp.isep.dei.esoft.project.ui.console.job;

import pt.ipp.isep.dei.esoft.project.application.controller.collaboratorSystem.RegisterJobCategoryController;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.Scanner;

public class RegisterJobCategoryUI implements Runnable {
    private String jobCategoryName;

    private RegisterJobCategoryController ctrl;

    public RegisterJobCategoryUI(){
        ctrl = RegisterJobCategoryController.getInstance();
    }

    private RegisterJobCategoryController getController(){
        return ctrl;
    }

    public void run(){
        try {
            System.out.println("----- Register a Job Category -----");
            jobCategoryName=requestData();
            submitsData();
        }catch (IllegalArgumentException | CloneNotSupportedException e){
            System.out.println(e.getMessage());
        }

    }

    private void submitsData() throws CloneNotSupportedException {
        boolean value = getController().registerJobCategory(jobCategoryName);
        if (value) {
            System.out.println("\nJobCategory successfully registed!");
        } else {
            System.out.println("\nJobCategory not registed!");
        }
    }

    private String requestData() {
        Scanner scan = new Scanner(System.in);
        String jobName;
        do{
            jobName= Utils.readLineFromConsole("Please input the Job Category Name:");
        }while(jobName.isEmpty());
        return jobName;
    }

}
