package pt.ipp.isep.dei.esoft.project.repository;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;
import pt.ipp.isep.dei.esoft.project.utilities.AppendableObjectOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represent the JobCategoryRepository
 */
public class JobCategoryRepository {
    private List<JobCategory> jobCategories;

    public JobCategoryRepository() {
        try {
            //jobCategories = new ArrayList<>();
            loadFromJobCategoryDataBase();
        } catch (IOException | CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
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
            saveFromJobCategoryInDataBase(jobCategory);
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

    /** Removes the job category from list of job categories
     *
     * @param jobCategory to be removed
     *
     */
    public void removeJobCategory(JobCategory jobCategory) {
        if (jobCategories.contains(jobCategory)){
            jobCategories.remove(jobCategory);
            removeFromJobCategoryDataBase(jobCategory);
        }else{
            throw new RuntimeException("This Job Category does not exist in the Repository");
        }
    }


    public void removeFromJobCategoryDataBase(JobCategory jobCategory) {
        jobCategories.remove(jobCategory);
        saveJobCategorys();
    }

    public void saveFromJobCategoryInDataBase(JobCategory jobCategory) {
        if (!jobCategories.contains(jobCategory)) {
            jobCategories.add(jobCategory);
            saveJobCategorys();
        }
    }

    private void saveJobCategorys() {
        cleanFile(MainApp.getJobCategoryDataBaseFile());
        try (FileOutputStream fileOut = new FileOutputStream(MainApp.getJobCategoryDataBaseFile());
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(jobCategories);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cleanFile(String jobCategorysDataBaseFile) {
        File file = new File(MainApp.getJobCategoryDataBaseFile());
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print("");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + file, e);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromJobCategoryDataBase() throws CloneNotSupportedException, IOException {
        File file = new File(MainApp.getJobCategoryDataBaseFile());
        if (!file.exists()) {
            throw new IOException("JobCategory database file does not exist. Starting with an empty list.");
        }
        List<JobCategory> jobCategorysList;
        if(file.length()==0){
            jobCategories=new ArrayList<>();
        } else {
        try (FileInputStream fileIn = new FileInputStream(file)
             ) {
            if (fileIn.getChannel().size()>0){
                ObjectInputStream in = new ObjectInputStream(fileIn);
                jobCategories = (List<JobCategory>) in.readObject();
                loadInSystem(jobCategories);
            }
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        }
    }

    private void loadInSystem(List<JobCategory> jobCategoryss) throws CloneNotSupportedException {
        for (JobCategory jobCategorys : jobCategoryss) {
            verifyJobCategoryExistAndSave(jobCategorys);
        }
    }

}
