package pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator;

import java.io.Serializable;

/**
 * Represent the Domain Class of JobCategory Objects
 */
//Verified By Francisco
public class JobCategory implements Serializable {
    /**
     * This variable represent the name of the job
     */
    private String name;

    /**
     * The constructor of JobCategory Object
     * @param name of job category
     */
    public JobCategory(String name){
       setName(name);
    }

    /**
     * This method return the JobCategoryName
     * @return the name of the JobCategory
     */
    public String getName() {
        return name;
    }

    /**
     * This method it is for trade the jobCategory name
     * @param name variable for change
     */
    public void setTradeName(String name){
        this.name=name;
    }

    /**
     * To String method return a String with the JobCategory Name
     * @return String with jobName
     */
    @Override
    public String toString() {
        return String.format("%s\n", name);
    }

    /** Sets the name for Job Category after verifying it
     *
     * @param name for job category
     */
    public void setName(String name) {
        if (verifyJobName(name)){
            this.name = name;
        }else{
            throw new IllegalArgumentException("Invalid Job Category name");
        }
    }

    /** Method to verify the name of Job Category
     *
     * @param name for the job category
     * @return true if the name given is not empty and if it only contains characters
     */
    private boolean verifyJobName(String name) {
        if (name.isEmpty()){
            return false;
        }
        for(char c : name.toCharArray()){
            if(!Character.isLetter(c) && !Character.isSpace(c)){
                return false;
            }
        }
        return true;
    }

    /** Method to compare if two Job Categories are the same
     *
     * Two Job Categories are the same if both have the same name
     *
     * @param other - Job Category to be compared along with other
     * @return true if they are the same
     */
    @Override
    public boolean equals(Object other) {
        if(this==other){
            return true;
        }
        if(other == null || this.getClass() != other.getClass()){
            return false;
        }
        if (other instanceof JobCategory){
            return this.getName().equals(((JobCategory) other).getName());
        }
        return false;
    }
}
