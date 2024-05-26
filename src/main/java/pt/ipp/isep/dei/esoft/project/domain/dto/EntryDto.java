package pt.ipp.isep.dei.esoft.project.domain.dto;

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


    public EntryDto(Date startDate, EntryState status, String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration, GreenSpaceDto greenSpaceDto, String reference) {
        super(title, description,degreeUrgency, expectedDuration, greenSpaceDto);
        this.startDate = startDate;
        setStatus(status);
        this.vehicleList = new ArrayList<Vehicle>();
        this.teamAssigned = null;
        this.reference = reference;
    }


    public EntryDto(Date startDate, EntryState status, List<Vehicle> vehicleList, Team teamAssigned,String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration,GreenSpaceDto greenSpaceDto, String reference) {
        super(title, description,degreeUrgency, expectedDuration,greenSpaceDto);
        this.startDate = startDate;
        setStatus(status);
        this.vehicleList = vehicleList;
        this.teamAssigned = teamAssigned;
        this.reference = reference;
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

}
