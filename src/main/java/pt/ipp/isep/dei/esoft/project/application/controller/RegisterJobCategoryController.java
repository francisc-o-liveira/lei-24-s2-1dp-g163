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
        JobCategory jobCategory=new JobCategory(job);
        jobCategoryRepository.add(jobCategory);
    }

    public List<JobCategory> getJobCategoriesList(){
        return jobCategoryRepository.getJobCategoryList();
    }
}
