# US002 - Register a Job Category 

## 4. Tests 

**Test 1:** Check that it is not possible to create an instance of the job class with special digits.- AC1 

	@Test
    void verifyIfNameCanHaveSpecialCharacters(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new JobCategory("2313@SkillTest");
        });
    }
	

**Test 2:** Check that it is not possible to create an instance of the job class with a null job name.- AC3

	@Test
    void verifyIfNameCanNullPointer(){
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new JobCategory("");
        });
    }

## 5. Construction (Implementation)

### Class RegisterJobCategoryController

```java
package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.core.application.repository.JobCategoryRepository;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;

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
    public RegisterJobCategoryController() {
        getJobCategoryRepository();
    }

    /**Returns the repository of Job Categories
     *
     * @return repository of Job Categories
     */
    public JobCategoryRepository getJobCategoryRepository() {
        if (jobCategoryRepository == null) {
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
        Optional<JobCategory> jobCategory = jobCategoryRepository.registerJobCategory(jobName);
        if (jobCategory.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get from Job Category Repository all the Job Categories in a List
     * @return a List of JobCategories
     */
    public List<JobCategory> getJobCategoriesList() {
        return jobCategoryRepository.getJobCategoryList();
    }

    /** Removes a Job Category from the List of Job Categories
     *
     * @param jobCategory to be removed
     */
    public void removeJobCategory(JobCategory jobCategory) {
        getJobCategoriesList().remove(jobCategory);
    }
}
```

### Class JobCategoryRepository

```java
/** Represent the JobCategoryRepository
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
}

```

## 6. Integration and Demo
* For demo purposes some job categories are bootstrapped while system starts.


## 7. Observations
* With the implementation, as the team was ahead of schedule, it was decided that due to JavaFX, exceptions were handled in the UI, so "try{}catch(){}" started to be carried out in the UI at console level as well, as it was not possible to have two ways of catch the exceptions for the two different UI types.

* Some verifications simples are maded in UI, because are simple verifications.