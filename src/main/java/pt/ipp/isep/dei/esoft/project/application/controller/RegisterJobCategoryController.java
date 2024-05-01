package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.repository.JobCategoryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.List;
import java.util.Optional;


//Verified By Francisco
public class RegisterJobCategoryController {
    /**
     * Represent the job category repository instance(unique)
     */
    public JobCategoryRepository jobCategoryRepository;

    /**
     * When the controller is created, the jobCategory instance is taken from repositories
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
     * Method to register a Job Category
     *
     * @param jobName represents the Job Category name
     * @return true if jobCategory is created
     */
    public boolean registerJobCategory(String jobName) throws CloneNotSupportedException {
        Optional<JobCategory> jobCategory= jobCategoryRepository.registerJobCategory(jobName);
        if(jobCategory.isPresent()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * Get from Job Category Repository all the Job Categories in a List
     * @return a List of JobCategories
     */
    public List<JobCategory> getJobCategoriesList(){
        return jobCategoryRepository.getJobCategoryList();
    }

    /** Removes a Job Category from the List of Job Categories
     *
     * @param jobCategory to be removed
     */
    public void removeJobCategory(JobCategory jobCategory){
        getJobCategoriesList().remove(jobCategory);
    }
}
