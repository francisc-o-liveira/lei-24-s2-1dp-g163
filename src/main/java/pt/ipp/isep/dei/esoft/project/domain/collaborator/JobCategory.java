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
        return String.format("Job Category: %s\n", name);
    }

    public void setName(String name) {
        if (verifyJobName(name)){
            this.name = name;
        }else{
            throw new IllegalArgumentException("Invalid Job Category name");
        }
    }

    private boolean verifyJobName(String name) {
        if (name.isEmpty()){
            return false;
        }
        for(char c : name.toCharArray()){
            if(!Character.isLetter(c)){
                return false;
            }
        }
        return true;
    }

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
