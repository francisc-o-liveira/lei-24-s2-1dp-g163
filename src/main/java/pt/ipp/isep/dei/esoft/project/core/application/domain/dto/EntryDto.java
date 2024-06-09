package pt.ipp.isep.dei.esoft.project.core.application.domain.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.core.application.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.core.application.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.core.application.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Data Transfer Object for an Entry in the system.
 * Extends the TaskDto class to inherit common task properties.
 */
public class EntryDto extends TaskDto {

    private Date startDate;
    private EntryState status;
    private List<VehicleDto> vehicleList;
    private TeamDto teamAssigned;
    private String reference;
    private Date finishDate;
    private Tempo startHour;

    /** To complete the task */
    private Collaborator collaboratorThatCompleted;

    /**
     * Constructor with required parameters for initializing an EntryDto without a start date or reference.
     *
     * @param title           the title of the task
     * @param description     the description of the task
     * @param degreeOfUrgency the urgency degree of the task
     * @param timeExpec       the expected time duration of the task
     * @param greenSpaceDto   the green space associated with the task
     */
    public EntryDto(String title, String description, Task.DegreeUrgency degreeOfUrgency, Tempo timeExpec, GreenSpaceDto greenSpaceDto) {
        super(title, description, degreeOfUrgency, timeExpec, greenSpaceDto);
        this.status = new EntryState();
        this.vehicleList = new ArrayList<>();
        this.teamAssigned = null;
        this.reference = null;
        this.startDate = null;
        this.finishDate = null;
        this.collaboratorThatCompleted = null;
    }

    /**
     * Constructor with required parameters for initializing an EntryDto with an EntryState and reference.
     *
     * @param entryState      the state of the entry
     * @param title           the title of the task
     * @param description     the description of the task
     * @param degreeUrgency   the urgency degree of the task
     * @param expectedDuration the expected duration of the task
     * @param greenSpaceDto   the green space associated with the task
     * @param reference       the reference identifier for the entry
     */
    public EntryDto(EntryState entryState, String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration, GreenSpaceDto greenSpaceDto, String reference) {
        super(title, description, degreeUrgency, expectedDuration, greenSpaceDto);
        setStatus(entryState);
        this.vehicleList = new ArrayList<>();
        this.teamAssigned = null;
        this.reference = reference;
        this.startDate = null;
        this.startHour = null;
        this.finishDate = null;
        this.collaboratorThatCompleted = null;
    }

    /**
     * Constructor with additional parameters including team assignment and vehicle list.
     *
     * @param startDate        the start date of the entry
     * @param status           the status of the entry
     * @param title            the title of the task
     * @param description      the description of the task
     * @param degreeUrgency    the urgency degree of the task
     * @param expectedDuration the expected duration of the task
     * @param greenSpaceDto    the green space associated with the task
     * @param reference        the reference identifier for the entry
     * @param teamAssigned     the team assigned to the task
     * @param vehicleList      the list of vehicles assigned to the task
     */
    public EntryDto(Date startDate, Tempo startHour,  EntryState status, String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration, GreenSpaceDto greenSpaceDto, String reference, TeamDto teamAssigned, List<VehicleDto> vehicleList) {
        super(title, description, degreeUrgency, expectedDuration, greenSpaceDto);
        this.startDate = startDate;
        setStatus(status);
        this.vehicleList = vehicleList;
        this.teamAssigned = teamAssigned;
        this.reference = reference;
        this.startHour = startHour;
    }

    /**
     * Constructor with all parameters including start and finish dates, start hour, and collaborator that completed the task.
     *
     * @param startDate          the start date of the entry
     * @param startHour          the start hour of the entry
     * @param finishDate         the finish date of the entry
     * @param entryState         the state of the entry
     * @param title              the title of the task
     * @param description        the description of the task
     * @param degreeUrgency      the urgency degree of the task
     * @param expectedDuration   the expected duration of the task
     * @param greenSpaceDto      the green space associated with the task
     * @param reference          the reference identifier for the entry
     * @param teamDto            the team assigned to the task
     * @param vehicleDtos        the list of vehicles assigned to the task
     * @param collaboratorFinish the collaborator that completed the task
     */
    public EntryDto(Date startDate, Tempo startHour, Date finishDate, EntryState entryState, String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration, GreenSpaceDto greenSpaceDto, String reference, TeamDto teamDto, List<VehicleDto> vehicleDtos, Collaborator collaboratorFinish) {
        super(title, description, degreeUrgency, expectedDuration, greenSpaceDto);
        this.startDate = startDate;
        this.startHour = startHour;
        this.finishDate = finishDate;
        this.collaboratorThatCompleted = collaboratorFinish;
        setStatus(entryState);
        this.vehicleList = vehicleDtos;
        this.teamAssigned = teamDto;
        this.reference = reference;
    }

    public EntryDto(Date startDate, Tempo startHour, EntryState entryState, String title, String description, Task.DegreeUrgency degreeUrgency, Tempo expectedDuration, GreenSpaceDto greenSpaceDto, String reference) {
        super(title, description, degreeUrgency, expectedDuration, greenSpaceDto);
        this.startDate = startDate;
        this.startHour = startHour;
        this.finishDate = null;
        this.collaboratorThatCompleted = null;
        setStatus(entryState);
        this.vehicleList = new ArrayList<>();
        this.teamAssigned = null;
        this.reference = reference;
    }

    /**
     * Gets the collaborator that completed the task.
     *
     * @return the collaborator that completed the task
     */
    public Collaborator getCollaboratorFinish() {
        return collaboratorThatCompleted;
    }

    /**
     * Gets the start date of the entry.
     *
     * @return the start date of the entry
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Gets the status of the entry.
     *
     * @return the status of the entry
     */
    public EntryState getStatus() {
        return status;
    }

    /**
     * Gets the reference identifier of the entry.
     *
     * @return the reference identifier of the entry
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the agenda for the entry.
     *
     * @param newDate   the new date for the entry
     * @param startHour the new start hour for the entry
     */
    public void setEntryAgenda(Date newDate, Tempo startHour) {
        if (newDate != null) {
            this.status.assignState();
            this.startDate = newDate;
            this.startHour = startHour;
        } else {
            throw new NullPointerException("Start date cannot be null");
        }
    }

    /**
     * Sets the status of the entry.
     *
     * @param newStatus the new status of the entry
     */
    public void setStatus(EntryState newStatus) {
        if (newStatus == null) {
            throw new NullPointerException("Status cannot be null");
        }
        this.status = newStatus;
    }

    /**
     * Cancels the entry.
     */
    public void cancel() {
        this.status.cancelEntry();
    }

    public void setCollaboratorThatCompleted(Collaborator collaboratorThatCompleted){this.collaboratorThatCompleted = collaboratorThatCompleted;}

    /**
     * Postpones the entry to a new date and start time.
     *
     * @param newDate  the new date for the entry
     * @param startTime the new start time for the entry
     */
    public void postpone(Date newDate, Tempo startTime) {
        this.status.postponeState();
        this.startDate = newDate;
        this.startHour = startTime;
    }

/**
 * Assigns a vehicle to the entry.

 *
 * @param vehicle the vehicle to be assigned to the entry
 * @return true if the vehicle is successfully assigned
 * @throws NullPointerException if the vehicle is null
 */
public boolean assignVehicle(VehicleDto vehicle) {
    if (vehicle != null) {
        this.vehicleList.add(vehicle);
        return true;
    } else {
        throw new NullPointerException("You must select one or more vehicles to assign");
    }
}

    /**
     * Gets the list of vehicles assigned to the entry.
     *
     * @return the list of vehicles assigned to the entry
     */
    public List<VehicleDto> getVehicleList() {
        return this.vehicleList;
    }

    /**
     * Gets the team assigned to the entry.
     *
     * @return the team assigned to the entry
     */
    public TeamDto getTeamAssigned() {
        return teamAssigned;
    }

    /**
     * Assigns a team to the entry.
     *
     * @param team the team to be assigned to the entry
     * @throws NullPointerException if the team is null
     */
    public void assignTeam(TeamDto team) {
        if (team != null) {
            this.teamAssigned = team;
        } else {
            throw new NullPointerException("You must select a team to assign");
        }
    }

    /**
     * Gets the finish date of the entry.
     *
     * @return the finish date of the entry
     */
    public Date getFinishDate() {
        return finishDate;
    }

    /**
     * Marks the task as completed.
     *
     * @param finishDate the date the task was completed
     * @param collaborator the collaborator who completed the task
     * @throws IllegalArgumentException if the finish date or collaborator is invalid, or if the task is already completed or canceled
     */
    public void completeTask(Date finishDate, Collaborator collaborator) {
        if (!status.isCompleted() && !status.isCanceled()) {
            if (finishDate != null && collaborator != null) {
                this.finishDate = finishDate;
                this.collaboratorThatCompleted = collaborator;
                status.doneEntry();
                status.isCompleted();
            } else {
                throw new IllegalArgumentException("The finish date or collaborator is invalid");
            }
        } else {
            throw new IllegalArgumentException("This Task is already completed");
        }
    }

    /**
     * Gets the start hour of the entry.
     *
     * @return the start hour of the entry
     */
    public Tempo getStartHour() {
        return startHour;
    }
}
