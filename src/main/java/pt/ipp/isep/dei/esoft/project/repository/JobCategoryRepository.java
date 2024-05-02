package pt.ipp.isep.dei.esoft.project.repository;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represent the JobCategoryRepository
 */
public class JobCategoryRepository {
    private final List<JobCategory> jobCategories;

    public JobCategoryRepository() {
        jobCategories = new ArrayList<>();
    }

    /**
     * This method create the JobCategory with the name typed be the user
     * @param jobCategoryName represent the name introduced by the user
     * @return the new job category if there are saved on the jobCategories List and successful been created.
     */
    public Optional<JobCategory> registerJobCategory(String jobCategoryName) throws CloneNotSupportedException {
        Optional<JobCategory> newJobCategory =Optional.empty();
        JobCategory jobCategory = new JobCategory(jobCategoryName);
        newJobCategory=verifyJobCategoryExistAndSave(jobCategory);
        return newJobCategory;
    }

    /**
     * This method verify after creating the jobCategory, if dont exist one equals to them
     * If exist, the method dont save the JobCategory created on the list of JobCategories
     * @param jobCategory created in RegisterJobCategory method by the user
     * @return the JobCategory if save them on the List
     */

    private Optional<JobCategory> verifyJobCategoryExistAndSave(JobCategory jobCategory) throws CloneNotSupportedException {
       Optional<JobCategory> newJobCategory = Optional.empty();
       boolean operationSucess = false;
        if (!jobCategories.contains(jobCategory)){
            operationSucess=jobCategories.add(jobCategory);
            newJobCategory=Optional.of(jobCategory);
        }
        if (!operationSucess){
            newJobCategory=Optional.empty();
            throw new CloneNotSupportedException("This Job Category already Exist");
        }
        return newJobCategory;
    }

    /**
     * This method returns a defensive (immutable) copy of the list of job categories.
     *
     * @return The list of job categories.
     */
    public List<JobCategory> getJobCategoryList() {
        //This is a defensive copy, so that the repository cannot be modified from the outside.
        return List.copyOf(jobCategories);
    }

    public void removeJobCategory(JobCategory jobCategory) {
        if (jobCategories.contains(jobCategory)){
            jobCategories.remove(jobCategory);
        }else{
            throw new RuntimeException("This Job Category does not exist in the Repository");
        }
    }
}
