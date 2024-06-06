package pt.ipp.isep.dei.esoft.project.domain.task;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;
import pt.ipp.isep.dei.esoft.project.utilities.TimePeriod;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Entry extends Task implements Serializable {

    private static int REFERENCE_VALUE;

    private Date startDate;

    private Tempo startHour;

    private EntryState status;

    private List<Vehicle> vehicleList;

    private Team teamAssigned;

    private final String reference;

    /**To complete the task*/
    private Collaborator collaboratorThatCompleted;
    private Date finishDate;



    public Entry(String title, String description, Tempo expectedDuration, GreenSpace greenSpace, DegreeUrgency degreeUrgency, EntryState status) {
        super(title, description, expectedDuration, greenSpace, degreeUrgency);
        validateReference(Integer.toString(REFERENCE_VALUE++));
        this.reference = Integer.toString(REFERENCE_VALUE);
        this.status = status;
        this.startDate = null;
        this.vehicleList = null;
        this.teamAssigned = null;
        this.finishDate = null;
    }

    // To Create one instance to compare for Postpone
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

    //to do the postpone, you would need this constructor:
    public Entry(Date startDate,String title, String description, Tempo expectedDuration, GreenSpace greenSpace, DegreeUrgency degreeUrgency, EntryState status, int reference) {
        super(title, description, expectedDuration, greenSpace, degreeUrgency);
        validateReference(Integer.toString(reference));
        this.reference = Integer.toString(reference);
        this.status = status;
        this.startDate = startDate;
        this.vehicleList = null;
        this.teamAssigned = null;
        this.finishDate = null;
    }
    // Compare constructor





    public Date getFinishDate() {
        return finishDate;
    }

    public void completeTask(Date finishDate, Collaborator collaborator){
        if (finishDate.after(getTimePeriod().getStartDate())){
            this.collaboratorThatCompleted=collaborator;
            setFinishDate(finishDate);
            this.status.doneEntry();
        }else {
            throw new IllegalArgumentException("This finish date is not right");
        }
    }

    public String getReference(){
        return reference;
    }

    private void validateReference(String reference) {
        if (reference == null || reference.isEmpty()) {
            throw new IllegalArgumentException("Reference cannot be null or empty.");
        }
    }

    public void assignVehicle(Vehicle vehicle) {
        vehicleList.add(vehicle);
    }

    public void cancelEntry(){
        status.cancelEntry();
        //cancelData();
    }

    private void cancelData() {
        this.startDate = null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference);
    }

    public void postponeEntry(Date newStartDate){
        status.postponeState();
        setStartDate(newStartDate);
    }
    private void setStartDate(Date newStartDate) {
        this.startDate = newStartDate;
    }

    public Collaborator getCollaboratorFinish() {
        return collaboratorThatCompleted;
    }
    public Date getStartDate() {
        return this.startDate;
    }
    public List<Vehicle> getVehicleList() {
        return this.vehicleList;
    }
    public Team getTeamAssigned() {
        return this.teamAssigned;
    }
    public EntryState getStatus() {
        return this.status;
    }
    public static DegreeUrgency[] getDegreeOfUrgency(){
        return Task.getDegreeUrgencyValues();
    }


    public void setEntryAgenda(Date startDate, Tempo startHour) {
        setStartDate(startDate);
        setStartHour(startHour);
        status.assignState();}

    private void setStartHour(Tempo startHour) {
        if (startHour == null) {
            if (startHour.getHoras()>=8 && startHour.getHoras()<=20){
                this.startHour = startHour;
            }else {
                throw new IllegalArgumentException("Start hour must be between 8 and 20 horas.");
            }
        }
    }

    public Tempo getStartHour() {
        return startHour;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }
    public void setTeamAssigned(Team teamAssigned) {
        this.teamAssigned = teamAssigned;
    }
    public boolean isCanceled() {
        return this.status.isCanceled();
    }
    public boolean isPostpone() {
        return this.status.isPostpone();
    }
    public TimePeriod getTimePeriod(){
        return new TimePeriod(getStartHour(),getStartDate(),getExpectedDuration(), Repositories.getInstance().getEntryRepository().getHoursOfWork());
    }


    private void setFinishDate(Date date) {
        if (date == null && date.after(startDate)) {
            throw new IllegalArgumentException("Date cannot be null.");
        }else {
            this.finishDate = date;
        }
    }
}
