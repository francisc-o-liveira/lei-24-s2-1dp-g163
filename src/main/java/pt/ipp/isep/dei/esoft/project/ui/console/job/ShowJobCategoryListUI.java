package pt.ipp.isep.dei.esoft.project.ui.console.job;

import pt.ipp.isep.dei.esoft.project.application.controller.collaboratorSystem.RegisterJobCategoryController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;

import java.util.List;

public class ShowJobCategoryListUI implements Runnable{

    private RegisterJobCategoryController ctrl;

    public ShowJobCategoryListUI(){
        ctrl = RegisterJobCategoryController.getInstance();
    }

    private RegisterJobCategoryController getController(){
        return ctrl;
    }

    @Override
    public void run() {
        System.out.println("----- Job Category List -----\n");
        showDataAsked();
    }

    private void showDataAsked() {
        List<JobCategory> jobCategoryList = getController().getJobCategoriesList();
        if (jobCategoryList == null) {
            System.out.println("No job categories are registered on the system.");
        }else{
            for (JobCategory j : jobCategoryList){
                System.out.println(j);
            }
        }
    }
}
