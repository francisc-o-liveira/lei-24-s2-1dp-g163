package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterJobCategoryController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;

import java.util.List;

public class ShowJobCategoryListUI implements Runnable{

    private RegisterJobCategoryController ctrl;

    public ShowJobCategoryListUI(){
        ctrl = new RegisterJobCategoryController();
    }

    private RegisterJobCategoryController getController(){
        return ctrl;
    }

    @Override
    public void run() {
        System.out.println("----- Job Category List -----");
        showDataAsked();
    }

    private void showDataAsked() {
        List<JobCategory> jobCategoryList = getController().getJobCategoriesList();
        if (jobCategoryList == null) {
            System.out.println("Dont exist any Job Category register on the System.");
        }else{
            for (JobCategory j : jobCategoryList){
                System.out.println(j);
            }
        }
    }
}
