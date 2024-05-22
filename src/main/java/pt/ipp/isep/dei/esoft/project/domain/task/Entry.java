package pt.ipp.isep.dei.esoft.project.domain.task;

import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.ArrayList;
import java.util.List;

public class Entry extends Task {

    private Date startDate;

    private enum Status {Planned, Postponed, Canceled,Done};

    private Status status;

    private List<Vehicle> vehicleList;

    private Team teamAssigned;


    public Entry(String reference, String description, String informalDescription, String technicalDescription, int duration, double cost) {
        super(reference, description, informalDescription, technicalDescription, duration, cost);
        this.status = Status.Planned;
        this.startDate = null;
        this.vehicleList = new ArrayList<Vehicle>();
        this.teamAssigned = null;
    }

    public void assignTeam(Team teamToAssign){
        this.teamAssigned = teamToAssign;
    }

    public void cancelEntry(){
        if (this.status == Status.Postponed || this.status == Status.Planned){
            this.status = Status.Canceled;
        }else if (this.status == Status.Canceled) {
            throw new IllegalArgumentException("This entry is already cancelled");
        }else {
            throw new RuntimeException("Access Impossible");
        }
    }

    public void postponeEntry(Date newStartDate){
        if (this.status == Status.Canceled || this.status == Status.Planned){
            this.status = Status.Postponed;
            setStartDate(newStartDate);
        }else if (this.status == Status.Postponed) {
            setStartDate(newStartDate);
        }else {
            throw new RuntimeException("Access Impossible");
        }
    }

    private void setStartDate(Date newStartDate) {
        this.startDate = newStartDate;
    }


}
