package pt.ipp.isep.dei.esoft.project.domain.vehicle;

import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.io.Serializable;

/** Domain class for Check-Up*/
public class CheckUp implements Serializable {

    /** The kilometers when check-up was made */
    private double kmOfCheck;

    /** Date of the Check-up*/
    private Date dateOfCheck;

    /** Constructor method for Check-Up
     *
     * @param kmOfCheck - kilometers when check-up was made
     * @param dateOfCheck - date when check-up was made
     */
    public CheckUp(double kmOfCheck, Date dateOfCheck){
        this.dateOfCheck=dateOfCheck;
        this.kmOfCheck=kmOfCheck;
    }

    /** Gets the date when Check-Up was made
     *
     * @return date of Check-Up
     *
     */

    public Date getDateOfCheck() {
        return dateOfCheck;
    }

    /** Gets the kilometers of the vehicle when Check-Up was made
     *
     * @return the kilometers when check-up was made
     */

    public double getKmOfCheck() {
        return kmOfCheck;
    }

    /** Method to get the data of Check-up in a String
     *
     * @return String with the check-up data
     */

    @Override
    public String toString(){
        return String.format("Km of Check-Up: %d\n" +
                "Date of Check-Up: %s\n", kmOfCheck, dateOfCheck);
    }

    /** Method to compare if two check-ups are the same
     *
     * Two Check-ups are the same if both have the same date and the same kilometers
     * @param other - the check-up to be compared
     * @return true if both are the same
     */

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
