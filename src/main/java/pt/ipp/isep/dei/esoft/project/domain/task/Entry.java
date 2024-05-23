package pt.ipp.isep.dei.esoft.project.domain.task;

import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.ArrayList;
import java.util.List;

public class Entry extends Task {

    private Date startDate;

    private EntryDto.Status status;

    private List<Vehicle> vehicleList;

    private Team teamAssigned;


    public Entry(String title, String reference, String description, String informalDescription, String technicalDescription, int duration, GreenSpace greenSpace) {
        super(title, reference, description, informalDescription, technicalDescription, duration, greenSpace);
        this.status = EntryDto.Status.Planned;
        this.startDate = null;
        this.vehicleList = new ArrayList<Vehicle>();
        this.teamAssigned = null;
    }

    public void assignTeam(Team teamToAssign){
        this.teamAssigned = teamToAssign;
    }

    public void cancelEntry(){
        if (this.status == EntryDto.Status.Postponed || this.status == EntryDto.Status.Planned){
            this.status = EntryDto.Status.Canceled;
        }else if (this.status == EntryDto.Status.Canceled) {
            throw new IllegalArgumentException("This entry is already cancelled");
        }else {
            throw new RuntimeException("Access Impossible");
        }
    }

    public void postponeEntry(Date newStartDate){
        if (this.status == EntryDto.Status.Canceled || this.status == EntryDto.Status.Planned){
            this.status = EntryDto.Status.Postponed;
            setStartDate(newStartDate);
        }else if (this.status == EntryDto.Status.Postponed) {
            setStartDate(newStartDate);
        }else {
            throw new RuntimeException("Access Impossible");
        }
    }

    private void setStartDate(Date newStartDate) {
        this.startDate = newStartDate;
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
    public EntryDto.Status getStatus() {
        return this.status;
    }


}
