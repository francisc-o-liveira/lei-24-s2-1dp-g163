package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.JobCategory;
import pt.ipp.isep.dei.esoft.project.domain.TaskCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobCategoryRepository {
    private final List<JobCategory> jobCategories;
    public JobCategoryRepository() {
        jobCategories = new ArrayList<>();
    }

    /**
     * This method returns an exsiting Task Category by its description.
     *
     * @param jobCategoryName The description of the task category to be created.
     * @return The task category.
     * @throws IllegalArgumentException if the task category does not exist, which should never happen.
     */
    public boolean verifyJobCategoryExistByName(String jobCategoryName) {
        JobCategory newJobCategory = new JobCategory(jobCategoryName);
        JobCategory jobCategory = null;
        if (jobCategories.contains(newJobCategory)) {
            jobCategory = jobCategories.get(jobCategories.indexOf(newJobCategory));
        }
        if (jobCategory == null) {
            return false;
        }
        return true;
    }

    public boolean add(JobCategory jobCategory) {
        boolean operationSuccess = false;
        if (!verifyJobCategoryExist(jobCategory) && !verifyJobCategoryExistByName(jobCategory.getName())) {
           operationSuccess=true;
           jobCategories.add(jobCategory);
        }
        return true;
    }

    private boolean verifyJobCategoryExist(JobCategory jobCategory) {
        boolean isValid = !jobCategories.contains(jobCategory);
        return isValid;
    }

    /**
     * This method returns a defensive (immutable) copy of the list of task categories.
     *
     * @return The list of task categories.
     */
    public List<JobCategory> getJobCategoryList() {
        //This is a defensive copy, so that the repository cannot be modified from the outside.
        return List.copyOf(jobCategories);
    }
}
