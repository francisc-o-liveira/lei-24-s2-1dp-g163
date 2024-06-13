package pt.ipp.isep.dei.esoft.project.core.application.domain.task;

import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.core.application.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.core.application.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.core.application.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;
import pt.ipp.isep.dei.esoft.project.utilities.TimePeriod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an entry in the task management system.
 * Extends the Task class and implements Serializable for object serialization.
 */
public class Entry extends Task implements Serializable {

    private static int REFERENCE_VALUE;

    private Date startDate;
    private Tempo startHour;
    private EntryState status;
    private List<Vehicle> vehicleList;
    private Team teamAssigned;
    private final String reference;
    private Collaborator collaboratorThatCompleted;
    private Date finishDate;

    /**
     * Constructs an Entry instance with the specified details.
     *
     * @param title           the title of the entry
     * @param description     the description of the entry
     * @param expectedDuration the expected duration of the entry
     * @param greenSpace      the green space associated with the entry
     * @param degreeUrgency   the degree of urgency of the entry
     * @param status          the initial status of the entry
     */
    public Entry(String title, String description, Tempo expectedDuration, GreenSpace greenSpace, DegreeUrgency degreeUrgency, EntryState status) {
        super(title, description, expectedDuration, greenSpace, degreeUrgency);
        validateReference(Integer.toString(REFERENCE_VALUE++));
        this.reference = Integer.toString(REFERENCE_VALUE);
        this.status = status;
        this.startDate = null;
        this.vehicleList = new ArrayList<>();
        this.teamAssigned = null;
        this.finishDate = null;
    }

    /**
     * Constructs an Entry instance with the specified details for comparison purposes.
     *
     * @param title           the title of the entry
     * @param description     the description of the entry
     * @param expectedDuration the expected duration of the entry
     * @param greenSpace      the green space associated with the entry
     * @param degreeUrgency   the degree of urgency of the entry
     * @param status          the initial status of the entry
     * @param reference       the reference number of the entry
     * @param vehicleList     the list of vehicles assigned to the entry
     * @param teamAssigned    the team assigned to the entry
     */
    public Entry(String title, String description, Tempo expectedDuration, GreenSpace greenSpace, DegreeUrgency degreeUrgency, EntryState status, int reference, List<Vehicle> vehicleList, Team teamAssigned) {
        super(title, description, expectedDuration, greenSpace, degreeUrgency);
        validateReference(Integer.toString(reference));
        this.reference = Integer.toString(reference);
        this.status = status;
        this.startDate = null;
        this.vehicleList = vehicleList;
        this.teamAssigned = teamAssigned;
        this.finishDate = null;
    }

    /**
     * Constructs an Entry instance for postponing purposes.
     *
     * @param startDate       the start date of the entry
     * @param title           the title of the entry
     * @param description     the description of the entry
     * @param expectedDuration the expected duration of the entry
     * @param greenSpace      the green space associated with the entry
     * @param degreeUrgency   the degree of urgency of the entry
     * @param status          the initial status of the entry
     * @param reference       the reference number of the entry
     */
    public Entry(Date startDate, String title, String description, Tempo expectedDuration, GreenSpace greenSpace, DegreeUrgency degreeUrgency, EntryState status, int reference, Tempo startHour) {
        super(title, description, expectedDuration, greenSpace, degreeUrgency);
        validateReference(Integer.toString(reference));
        this.reference = Integer.toString(reference);
        this.status = status;
        this.startDate = startDate;
        this.startHour=startHour;
        this.vehicleList = new ArrayList<>();
        this.teamAssigned = null;
        this.finishDate = null;
    }

    /**
     * Gets the finish date of the entry.
     *
     * @return the finish date
     */
    public Date getFinishDate() {
        return finishDate;
    }

    /**
     * Completes the task with the specified finish date and collaborator.
     *
     * @param finishDate   the finish date
     * @param collaborator the collaborator who completed the task
     * @throws IllegalArgumentException if the finish date is before the start date
     */
    public void completeTask(Date finishDate, Collaborator collaborator) {
        //if (finishDate.after(getTimePeriod().getStartDate())) {
            this.collaboratorThatCompleted = collaborator;
            setFinishDate(finishDate);
            this.status.doneEntry();
        /*} else {
            throw new IllegalArgumentException("This finish date is not right");
        }*/
    }

    /**
     * Gets the reference of the entry.
     *
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * Validates the reference string.
     *
     * @param reference the reference to validate
     * @throws IllegalArgumentException if the reference is null or empty
     */
    private void validateReference(String reference) {
        if (reference == null || reference.isEmpty()) {
            throw new IllegalArgumentException("Reference cannot be null or empty.");
        }
    }

    /**
     * Assigns a vehicle to the entry.
     *
     * @param vehicle the vehicle to assign
     */
    public void assignVehicle(Vehicle vehicle) {
        vehicleList.add(vehicle);
    }

    /**
     * Cancels the entry.
     */
    public void cancelEntry() {
        status.cancelEntry();
    }

    /**
     * Cancels the data of the entry.
     */
    private void cancelData() {
        this.startDate = null;
    }

    /**
     * Calculates the hash code for the entry based on its reference.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(reference);
    }

    /**
     * Postpones the entry to a new start date.
     *
     * @param newStartDate the new start date
     */
    public void postponeEntry(Date newStartDate,  Tempo startTime) {
        setStart(newStartDate,startTime);
        status.postponeState();
    }

    /** Sets the start date and start hour
     *
     * */
    public void setStart(Date newStartDate,  Tempo startTime){
        if(newStartDate.compareTo(Date.atualDate())>=0 && startTime.getHoras() >= 8 && startTime.getHoras() <= 20 ){
            this.startDate = newStartDate;
            this.startHour = startTime;
        }else {
            throw new IllegalArgumentException("The start date or the start hour is not correct.");
        }
    }

    public void setCollaboratorThatCompleted(Collaborator c){this.collaboratorThatCompleted=c;}

    /**
     * Gets the collaborator who completed the entry.
     *
     * @return the collaborator
     */
    public Collaborator getCollaboratorFinish() {
        return collaboratorThatCompleted;
    }

    /**
     * Gets the start date of the entry.
     *
     * @return the start date
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Gets the list of vehicles assigned to the entry.
     *
     * @return the list of vehicles
     */
    public List<Vehicle> getVehicleList() {
        return this.vehicleList;
    }

    /**
     * Gets the team assigned to the entry.
     *
     * @return the team
     */
    public Team getTeamAssigned() {
        return this.teamAssigned;
    }

    /**
     * Gets the status of the entry.
     *
     * @return the status
     */
    public EntryState getStatus() {
        return this.status;
    }

    /**
     * Gets all possible degrees of urgency.
     *
     * @return an array of DegreeUrgency enums
     */
    public static DegreeUrgency[] getDegreeOfUrgency() {
        return getDegreeUrgencyValues();
    }

    /**
     * Sets the agenda for the entry with the specified start date and start hour.
     *
     * @param startDate the start date
     * @param startHour the start hour
     */
    public void setEntryAgenda(Date startDate, Tempo startHour) {
        setStart(startDate,startHour);
        status.assignState();
    }


    /**
     * Gets the start hour of the entry.
     *
     * @return the start hour
     */
    public Tempo getStartHour() {
        return startHour;
    }

    /**
     * Sets the list of vehicles assigned to the entry.
     *
     * @param vehicleList the list of vehicles
     */
    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    /**
     * Sets the team assigned to the entry.
     *
     * @param teamAssigned the team to assign
     */
    public void setTeamAssigned(Team teamAssigned) {
        this.teamAssigned = teamAssigned;
    }

    /**
     * Checks if the entry is canceled.
     *
     * @return true if the entry is canceled, false otherwise
     */
    public boolean isCanceled() {
        return this.status.isCanceled();
    }

    public boolean isCompleted(){return this.status.isCompleted();}

    /**
     * Checks if the entry is postponed.
     *
     * @return true if the entry is postponed, false otherwise
     */
    public boolean isPostpone() {
        return this.status.isPostpone();
    }

    /**
     * Gets the time period of the entry.
     *
     * @return the time period
     */
    public TimePeriod getTimePeriod() {
        return new TimePeriod(getStartHour(), getStartDate(), getExpectedDuration(), Repositories.getInstance().getEntryRepository().getHoursOfWork());
    }

    /**
     * Sets the finish date of the entry.
     *
     * @param date the finish date
     * @throws IllegalArgumentException if the date is null or before the start date
     */
    private void setFinishDate(Date date) {
        if (date == null || date.compareTo(startDate)<0) {
            throw new IllegalArgumentException("Date cannot be null or before the start date.");
        } else {
            this.finishDate = date;
        }
    }
}
