package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.JobCategory;
import pt.ipp.isep.dei.esoft.project.repository.JobCategoryRepository;

import java.util.List;

public class RegisterJobCategoryController {

    public JobCategoryRepository jobCategoryRepository;

    public JobCategoryRepository getJobCategoryRepository(){
        return jobCategoryRepository;
    }

    public void registerJobCategory(String job){
        verifyJobName(job);
        JobCategory jobCategory=new JobCategory(job);
        jobCategoryRepository.add(jobCategory);
    }

    private void verifyJobName(String job){
        if(job.trim().isEmpty()){
            throw new NullPointerException("The Job Name is empty. Please introduce a valid Job Name.");
        }
        char[] verification= job.trim().toCharArray();
        for(char character : verification){
            if(!Character.isLetter(character)){
                throw new IllegalArgumentException("The Job Name can't contain any special characters or digits. Please introduce a valid Job Name.");
            }
        }
    }

    public List<JobCategory> getJobCategoriesList(){
        return jobCategoryRepository.getJobCategoryList();
    }
}
