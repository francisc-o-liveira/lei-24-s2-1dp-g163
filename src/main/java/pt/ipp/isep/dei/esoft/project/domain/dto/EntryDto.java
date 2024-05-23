package pt.ipp.isep.dei.esoft.project.domain.dto;

import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.ArrayList;
import java.util.List;

public class EntryDto extends TaskDto{

    private Date startDate;
    private Status status;
    private List<Vehicle> vehicleList;
    private Team teamAssigned;

    public static enum Status {Planned, Assigned, Postponed, Canceled,Done};



    public EntryDto(Date startDate, Status status,String title, String description) {
        super(title, description);
        this.startDate = startDate;
        this.status = status;
        this.vehicleList = new ArrayList<Vehicle>();
        this.teamAssigned = null;
    }

    public EntryDto(Date startDate, Status status, List<Vehicle> vehicleList, Team teamAssigned,String title, String description) {
        super(title, description);
        this.startDate = startDate;
        this.status = status;
        this.vehicleList = vehicleList;
        this.teamAssigned = teamAssigned;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Status getStatus() {
        return status;
    }
}
