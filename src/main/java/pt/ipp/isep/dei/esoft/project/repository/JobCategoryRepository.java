package pt.ipp.isep.dei.esoft.project.repository;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
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
    private final List<JobCategory> jobCategories;

    public JobCategoryRepository() {
        jobCategories = new ArrayList<>();
        loadFromJobCategoryDataBase();
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
        List<JobCategory> jobCategoryList = new ArrayList<>();
        JobCategory jobCategoryLoaded;
        try {
            FileInputStream file = new FileInputStream(MainApp.getJobCategoryDataBaseFile());
            ObjectInputStream in = new ObjectInputStream(file);
            while (true) {
                try {
                    jobCategoryLoaded = (JobCategory) in.readObject();
                    if (!jobCategoryLoaded.equals(jobCategory)) {
                        jobCategoryList.add(jobCategoryLoaded);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
            in.close();
            file.close();

            FileOutputStream fileOut = new FileOutputStream(MainApp.getJobCategoryDataBaseFile());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (JobCategory jobCategorySave : jobCategoryList) {
                out.writeObject(jobCategorySave);
            }
            out.close();
            fileOut.close();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFromJobCategoryInDataBase(JobCategory jobCategory){
        try {
            FileOutputStream file = new FileOutputStream(MainApp.getJobCategoryDataBaseFile());
            ObjectOutputStream out;
            // If the file already has content, we need to use the AppendableObjectOutputStream
            if (file.getChannel().size() > 0) {
                out = new AppendableObjectOutputStream(file);
            } else {
                out = new ObjectOutputStream(file);
            }
            out.writeObject(jobCategory);
            out.close();
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFromJobCategoryDataBase(){
        JobCategory jobCategoryLoaded;
        try {
            FileInputStream file = new FileInputStream(MainApp.getJobCategoryDataBaseFile());
            if (file.getChannel().size() > 0){
                ObjectInputStream in = new ObjectInputStream(file);
                while (true) {
                    try {
                        jobCategoryLoaded = (JobCategory) in.readObject();
                        loadInSystem(jobCategoryLoaded);
                    } catch (EOFException e) {
                        break;
                    }
                }
                in.close();
                file.close();
            }
            file.close();
        } catch (ClassNotFoundException | IOException | CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadInSystem(JobCategory jobCategory) throws CloneNotSupportedException {
        verifyJobCategoryExistAndSave(jobCategory);
    }

}
