package pt.ipp.isep.dei.esoft.project.domain.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.ArrayList;
import java.util.List;

public class EntryDto extends TaskDto{

    private Date startDate;
    private EntryState status;
    private List<VehicleDto> vehicleList;
    private TeamDto teamAssigned;
    private String reference;
    private Date finishDate;
    private Tempo startHour;


    /**To complete the task*/
    private Collaborator collaboratorThatCompleted;


    public EntryDto(Date startDate, EntryState status, String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration, GreenSpaceDto greenSpaceDto, String reference) {
        super(title, description,degreeUrgency, expectedDuration, greenSpaceDto);
        this.startDate = startDate;
        setStatus(status);
        this.vehicleList = new ArrayList<VehicleDto>();
        this.teamAssigned = null;
        this.reference = reference;
        this.finishDate = null;
        this.collaboratorThatCompleted = null;
    }

    public EntryDto(String title, String description, Task.DegreeUrgency degreeOfUrgency, Tempo timeExpec, GreenSpaceDto greenSpaceDto) {
        super(title, description,degreeOfUrgency, timeExpec, greenSpaceDto);
        this.status = new EntryState();
        this.vehicleList = null;
        this.teamAssigned = null;
        this.reference = null;
        this.startDate = null;
        this.finishDate = null;
        this.collaboratorThatCompleted = null;
    }


    public EntryDto(EntryState entryState, String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration, GreenSpaceDto greenSpaceDto, String reference) {
        super(title, description,degreeUrgency, expectedDuration, greenSpaceDto);
        setStatus(entryState);
        this.vehicleList = null;
        this.teamAssigned = null;
        this.reference = reference;
        this.startDate = null;
        this.startHour = null;
        this.finishDate = null;
        this.collaboratorThatCompleted = null;
    }

    public EntryDto(Date startDate, EntryState status, String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration, GreenSpaceDto greenSpaceDto, String reference, TeamDto teamAssigned, List<VehicleDto> vehicleList) {
        super(title, description,degreeUrgency, expectedDuration,greenSpaceDto);
        this.startDate = startDate;
        setStatus(status);
        this.vehicleList = vehicleList;
        this.teamAssigned = teamAssigned;
        this.reference = reference;
    }

    public EntryDto(Date startDate, Tempo startHour, Date finishDate, EntryState entryState, String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration, GreenSpaceDto greenSpaceDto, String reference, TeamDto teamDto, List<VehicleDto> vehicleDtos, Collaborator collaboratorFinish) {
        super(title, description,degreeUrgency, expectedDuration,greenSpaceDto);
        this.startDate = startDate;
        this.startHour = startHour;
        this.finishDate = finishDate;
        this.collaboratorThatCompleted = collaboratorFinish;
        setStatus(entryState);
        this.vehicleList = vehicleDtos;
        this.teamAssigned = teamDto;
        this.reference = reference;
    }



    // new
    public EntryDto(String reference, String title, String description, Tempo expectedDuration, Task.DegreeUrgency degreeUrgency, GreenSpaceDto greenSpace) {
        super(title, description,degreeUrgency, expectedDuration,greenSpace);
        this.reference=reference;
        this.vehicleList = null;
        this.teamAssigned = null;
        this.startDate = null;
        this.startHour = null;
        this.finishDate = null;
        this.collaboratorThatCompleted = null;
    }


    public Collaborator getCollaboratorFinish() {
        return collaboratorThatCompleted;
    }

    public Date getStartDate() {
        return startDate;
    }

    public EntryState getStatus() {
        return status;
    }

    public String getReference() {
        return reference;
    }

    public void setEntryAgenda(Date newDate, Tempo startHour){
        if(newDate != null){
            this.status.assignState();
            this.startDate = newDate;
            this.startHour = startHour;
        }else {
            throw new NullPointerException("Start date cannot be null");
        }
    }

    public void setStatus(EntryState newStatus) {
        if(newStatus == null){
            throw new NullPointerException("Status cannot be null");
        }
        this.status = newStatus;
    }

    public void cancel(){
        this.status.cancelEntry();
    }

    public void postpone(Date newDate, Tempo startTime) {
        this.status.postponeState();
        this.startDate = newDate;
        this.startHour=startTime;
    }

    public boolean assignVehicle(VehicleDto vehicle) {
        if(vehicle != null){
            this.vehicleList.add(vehicle);
            return true;
        }else {
            throw new NullPointerException("You must select one or more vehicles to assign");
        }
    }

    public List<VehicleDto> getVehicleList() {
        return this.vehicleList;
    }

    public TeamDto getTeamAssigned() {
        return teamAssigned;
    }

    public void assignTeam(TeamDto team) {
        if (team!=null){
            this.teamAssigned = team;
        }else{
            throw new NullPointerException("You must select a team to assign");
        }
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void completeTask(Date finishDate, Collaborator collaborator){
        if (!status.isCompleted() && !status.isCanceled()){
            if (finishDate!=null && collaborator!=null){
                this.finishDate = finishDate;
                this.collaboratorThatCompleted = collaborator;
                status.isCompleted();
            }else {
                throw new IllegalArgumentException("This finish date is not right");
            }
        }else {
            throw new IllegalArgumentException("This Task is already completed");
        }

    }

    public Tempo getStartHour() {
        return startHour;
    }
}
