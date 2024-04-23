package pt.ipp.isep.dei.esoft.project.domain.vehicle;

import pt.ipp.isep.dei.esoft.project.utilities.Date;

public class CheckUp {
    private int kmOfCheck;
    private Date dateOfCheck;

    public CheckUp(int kmOfCheck, Date dateOfCheck){
        this.dateOfCheck=dateOfCheck;
        this.kmOfCheck=kmOfCheck;
    }

    public Date getDateOfCheck() {
        return dateOfCheck;
    }

    public int getKmOfCheck() {
        return kmOfCheck;
    }
}
