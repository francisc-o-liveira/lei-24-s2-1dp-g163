package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.JobCategory;
import pt.ipp.isep.dei.esoft.project.repository.JobCategoryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.List;

public class RegisterJobCategoryController {
    /**
     * Represent the job category repository instance(unique)
     */
    public JobCategoryRepository jobCategoryRepository;

    /**
     * When create the Controller, the jobCategory instance is taken from repositories
     */
    public RegisterJobCategoryController() {
        jobCategoryRepository=Repositories.getInstance().getJobCategoryRepository();
    }


    public void registerJobCategory(String jobname){
        jobCategoryRepository.registerJobCategory(jobname);
    }

    public List<JobCategory> getJobCategoriesList(){
        return jobCategoryRepository.getJobCategoryList();
    }
}
