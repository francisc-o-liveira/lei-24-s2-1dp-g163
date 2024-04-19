package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.JobCategory;
import pt.ipp.isep.dei.esoft.project.repository.JobCategoryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.List;

public class RegisterJobCategoryController {

    public JobCategoryRepository jobCategoryRepository;

    public RegisterJobCategoryController(){
        getJobCategoryRepository();
    }

    /**Returns the repository of Job Categories
     *
     * @return repository of Job Categories
     */
    public JobCategoryRepository getJobCategoryRepository(){
        if (jobCategoryRepository == null) {
            Repositories repositories = Repositories.getInstance();
            jobCategoryRepository = repositories.getJobCategoryRepository();
        }
        return jobCategoryRepository;
    }

    /** The method registers a Job Category after verifications
     *
     * @param jobName
     */

    public void registerJobCategory(String jobName){
        verifyJobName(jobName);
        if(jobCategoryRepository.verifyJobCategoryExistByName(jobName)){
            throw new IllegalArgumentException("The Job Category already exists");
        }
        JobCategory jobCategory=new JobCategory(jobName);
        jobCategoryRepository.add(jobCategory);
    }

    /** The method verifies if the Job Name doesn't contain any special characters or digits
     *
     * @param jobName
     */

    private void verifyJobName(String jobName){
        if(jobName.trim().isEmpty()){
            throw new NullPointerException("The Job Name is empty. Please introduce a valid Job Name.");
        }
        char[] verification= jobName.trim().toCharArray();
        for(char character : verification){
            if(!Character.isLetter(character)){
                throw new IllegalArgumentException("The Job Name can't contain any special characters or digits. Please introduce a valid Job Name.");
            }
        }
    }

    /**Method to get the list of Job Categories
     *
     * @return List of Job Categories
     */
    public List<JobCategory> getJobCategoriesList(){
        return jobCategoryRepository.getJobCategoryList();
    }

}
