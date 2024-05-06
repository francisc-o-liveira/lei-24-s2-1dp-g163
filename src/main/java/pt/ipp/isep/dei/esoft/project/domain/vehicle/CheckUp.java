package pt.ipp.isep.dei.esoft.project.domain.vehicle;

import pt.ipp.isep.dei.esoft.project.utilities.Date;

public class CheckUp {
    private double kmOfCheck;
    private Date dateOfCheck;

    public CheckUp(double kmOfCheck, Date dateOfCheck){
        this.dateOfCheck=dateOfCheck;
        this.kmOfCheck=kmOfCheck;
    }

    public Date getDateOfCheck() {
        return dateOfCheck;
    }

    public double getKmOfCheck() {
        return kmOfCheck;
    }

    @Override
    public String toString(){
        return String.format("Km of Check-Up: %d\n" +
                "Date of Check-Up: %s\n", kmOfCheck, dateOfCheck);
    }

    @Override
    public boolean equals(Object other){
        if(this==other){
            return true;
        }
        if(other==null || this.getClass() != other.getClass()){
            return false;
        }
        CheckUp otherCheck= (CheckUp) other;
        return this.getDateOfCheck()==otherCheck.getDateOfCheck() && this.getKmOfCheck()== otherCheck.getKmOfCheck();
    }
}
