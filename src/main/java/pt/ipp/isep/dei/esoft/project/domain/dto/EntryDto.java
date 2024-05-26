package pt.ipp.isep.dei.esoft.project.domain.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private List<Vehicle> vehicleList;
    private Team teamAssigned;
    private String reference;
    private boolean selectedByCollab;
    private BooleanProperty selecting;


    public EntryDto(Date startDate, EntryState status, String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration, GreenSpaceDto greenSpaceDto, String reference) {
        super(title, description,degreeUrgency, expectedDuration, greenSpaceDto);
        this.startDate = startDate;
        setStatus(status);
        this.vehicleList = new ArrayList<Vehicle>();
        this.teamAssigned = null;
        this.reference = reference;
        this.selecting = new SimpleBooleanProperty(false);

        this.selecting.addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                selectedByCollab=true;
            }
        });
    }


    public EntryDto(Date startDate, EntryState status, List<Vehicle> vehicleList, Team teamAssigned,String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration,GreenSpaceDto greenSpaceDto, String reference) {
        super(title, description,degreeUrgency, expectedDuration,greenSpaceDto);
        this.startDate = startDate;
        setStatus(status);
        this.vehicleList = vehicleList;
        this.teamAssigned = teamAssigned;
        this.reference = reference;
        this.selecting = new SimpleBooleanProperty(false);

        this.selecting.addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                selectedByCollab=true;
            }
        });
    }

    public EntryDto(EntryState entryState, String title, String description, Task.DegreeUrgency degreeOfUrgency, Tempo timeExpec, GreenSpaceDto greenSpaceDto) {
        super(title, description,degreeOfUrgency, timeExpec, greenSpaceDto);
        this.status = entryState;
        this.vehicleList = null;
        this.teamAssigned = null;
        this.reference = null;
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

    public void setEntryAgenda(Date newDate){
        if(newDate != null){
            this.status.assignState();
            this.startDate = newDate;
        }else {
            throw new NullPointerException("Start date cannot be null");
        }
    }

    public void setStatus(EntryState newStatus) {
        if(status == null){
            throw new NullPointerException("Status cannot be null");
        }
        this.status = newStatus;
    }

    public void cancel(){
        this.status.cancelEntry();
    }

    public void postpone(Date newDate) {
        this.status.postponeState();
        this.startDate = newDate;
    }

    public BooleanProperty selectedEntry(){
        return selecting;
    }

    public void setSelecting(boolean value){
        this.selecting.set(value);
    }


    public boolean assignVehicle(Vehicle vehicle) {
        if(vehicle != null){
            this.vehicleList.add(vehicle);
            return true;
        }else {
            throw new NullPointerException("Vehicle cannot be null");
        }
    }

    public List<Vehicle> getVehicleList() {
        return this.vehicleList;
    }

    public Team getTeamAssigned() {
        return teamAssigned;
    }

    public void assignTeam(Team team) {
        if (team!=null){
            this.teamAssigned = team;
        }else{
            throw new NullPointerException("Team cannot be null");
        }
    }
}
