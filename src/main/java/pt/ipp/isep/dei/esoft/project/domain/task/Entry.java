package pt.ipp.isep.dei.esoft.project.domain.task;

import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.TaskDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Entry extends Task {

    private Date startDate;

    private EntryState status;

    private List<Vehicle> vehicleList;

    private Team teamAssigned;

    private final String reference;


    public Entry(String title, String reference, String description, Tempo expectedDuration, GreenSpace greenSpace, TaskDto.DegreeUrgency degreeUrgency, EntryState status) {
        super(title, description, expectedDuration, greenSpace, degreeUrgency);
        validateReference(reference);
        this.reference = reference;
        this.status = status;
        this.startDate = null;
        this.vehicleList = new ArrayList<Vehicle>();
        this.teamAssigned = null;
    }

    public String getReference() {
        return reference;
    }

    private void validateReference(String reference) {
        //TODO: missing from the diagrams
        if (reference == null || reference.isEmpty()) {
            throw new IllegalArgumentException("Reference cannot be null or empty.");
        }
    }

    public void assignTeam(Team teamToAssign){
        this.teamAssigned = teamToAssign;
    }

    public void cancelEntry(){
        status.cancelEntry();
        cancelData();
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

    public Date getStartDate() {
        return this.startDate;
    }
    public List<Vehicle> getVehicleList() {
        return this.vehicleList;
    }
    public Team getTeamAssigned() {
        return this.teamAssigned;
    }

    public EntryState.State getStatus() {
        return this.status.getState();
    }


}
