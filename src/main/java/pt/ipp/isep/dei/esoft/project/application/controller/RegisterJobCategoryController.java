package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.repository.JobCategoryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.List;


//Verified By Francisco
public class RegisterJobCategoryController {
    /**
     * Represent the job category repository instance(unique)
     */
    public JobCategoryRepository jobCategoryRepository;

    /**
     * When create the Controller, the jobCategory instance is taken from repositories
     */
    public RegisterJobCategoryController(){
        getJobCategoryRepository();
    }

    /**Returns the repository of Job Categories
     *
     * @return repository of Job Categories
     */
    public JobCategoryRepository getJobCategoryRepository(){
        if (jobCategoryRepository == null){
            Repositories repositories = Repositories.getInstance();
            jobCategoryRepository = repositories.getJobCategoryRepository();
        }
        return jobCategoryRepository;
    }

    /**
     * Register Job Category Method
     * @param jobname represent the Job Category name
     */
    public void registerJobCategory(String jobname){
        jobCategoryRepository.registerJobCategory(jobname);
    }

    /**
     * Get from Job Category Repository all the Job Categories in a List
     * @return a List of JobCategory's
     */
    public List<JobCategory> getJobCategoriesList(){
        return jobCategoryRepository.getJobCategoryList();
    }
}
