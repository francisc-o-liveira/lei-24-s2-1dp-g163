package pt.ipp.isep.dei.esoft.project.domain.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private boolean selectedByCollab;
    private BooleanProperty selectingCollab;


    public EntryDto(Date startDate, EntryState status, String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration, GreenSpaceDto greenSpaceDto, String reference) {
        super(title, description,degreeUrgency, expectedDuration, greenSpaceDto);
        this.startDate = startDate;
        setStatus(status);
        this.vehicleList = new ArrayList<VehicleDto>();
        this.teamAssigned = null;
        this.reference = reference;
        this.selectingCollab = new SimpleBooleanProperty(false);
        this.selectingCollab.addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                selectedByCollab=true;
            }
        });
    }

    public EntryDto(Date startDate, EntryState status, List<VehicleDto> vehicleList, TeamDto teamAssigned,String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration,GreenSpaceDto greenSpaceDto, String reference) {
        super(title, description,degreeUrgency, expectedDuration,greenSpaceDto);
        this.startDate = startDate;
        setStatus(status);
        this.vehicleList = vehicleList;
        this.teamAssigned = teamAssigned;
        this.reference = reference;
        this.selectingCollab = new SimpleBooleanProperty(false);

        this.selectingCollab.addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                selectedByCollab=true;
            }
        });
    }

    public EntryDto(String title, String description, Task.DegreeUrgency degreeOfUrgency, Tempo timeExpec, GreenSpaceDto greenSpaceDto) {
        super(title, description,degreeOfUrgency, timeExpec, greenSpaceDto);
        this.status = new EntryState();
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
        if(newStatus == null){
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

    public boolean assignVehicle(VehicleDto vehicle) {
        if(vehicle != null){
            this.vehicleList.add(vehicle);
            return true;
        }else {
            throw new NullPointerException("Vehicle cannot be null");
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
            throw new NullPointerException("Team cannot be null");
        }
    }

    public BooleanProperty selectedCollab(){
        return selectingCollab;
    }

    public void setSelectingCollab(boolean value){
        this.selectingCollab.set(value);
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void completeTask(Date finishDate){
        if (!status.isCompleted() && !status.isCanceled()){
            if (finishDate!=null){
                this.finishDate = finishDate;
                status.isCompleted();
            }else {
                throw new IllegalArgumentException("This finish date is not right");
            }
        }else {
            throw new IllegalArgumentException("This Task is already completed");
        }

    }
}
