package pt.ipp.isep.dei.esoft.project.domain.collaborator;

/**
 * Represent the Domain Class of JobCategory Objects
 */
//Verified By Francisco
public class JobCategory {
    /**
     * This variable represent the name of the job
     */
    private String name;

    /**
     * The constructor of JobCategory Object
     * @param name
     */
    public JobCategory(String name){
        this.name=name;
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
        return String.format("Job Category: %s\n", name);
    }

    public void setName(String name) {
        this.name = name;
    }
}
