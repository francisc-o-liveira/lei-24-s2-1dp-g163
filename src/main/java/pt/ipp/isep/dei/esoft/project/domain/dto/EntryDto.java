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
    private int reference;


    public EntryDto(Date startDate, EntryState status, String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration, GreenSpaceDto greenSpaceDto) {
        super(title, description,degreeUrgency, expectedDuration, greenSpaceDto);
        this.startDate = startDate;
        this.status = status;
        this.vehicleList = new ArrayList<Vehicle>();
        this.teamAssigned = null;
    }




    public EntryDto(Date startDate, EntryState status, List<Vehicle> vehicleList, Team teamAssigned,String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration,GreenSpaceDto greenSpaceDto, int reference) {
        super(title, description,degreeUrgency, expectedDuration,greenSpaceDto);
        this.startDate = startDate;
        this.status = status;
        this.vehicleList = vehicleList;
        this.teamAssigned = teamAssigned;
        this.reference = reference;
    }

    public Date getStartDate() {
        return startDate;
    }

    public EntryState getStatus() {
        return status;
    }
}
