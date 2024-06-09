package pt.ipp.isep.dei.esoft.project.core.application.repository;

import pt.ipp.isep.dei.esoft.project.core.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.core.application.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.core.application.mapper.VehicleMapper;
import pt.ipp.isep.dei.esoft.project.core.application.repository.lists.AgendaList;
import pt.ipp.isep.dei.esoft.project.core.application.repository.lists.ToDoList;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.io.*;
import java.util.Objects;
import java.util.Optional;

/**
 * Repository class for entries.
 */
public class EntryRepository {
    private AgendaList agenda;
    private ToDoList toDo;
    private static Tempo timeOfWorkByCollaborators;
    private static EntryMapper mapper;
    private static final int HOURS_WORK_PER_OMISSION = 8;
    private static VehicleMapper vehicleMapper;

    /**
     * Constructor to initialize the EntryRepository.
     */
    public EntryRepository() throws IOException {
        toDo = new ToDoList();
        agenda = new AgendaList();
        mapper = new EntryMapper();
        vehicleMapper = new VehicleMapper();
        try {
            timeOfWorkByCollaborators = ApplicationSession.getTimeOfWork();
        } catch (IllegalArgumentException e) {
            System.out.println("Error in File Config Please Verify : Getting Time Of Work By Collaborators");
            timeOfWorkByCollaborators = new Tempo(HOURS_WORK_PER_OMISSION);
        }

        /* try {
            agenda.loadFromDataBase();
            toDo.loadFromDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

         */
    }

    /**
     * Retrieves the agenda list.
     *
     * @return The agenda list.
     */
    public AgendaList getAgenda() {
        return agenda;
    }

    /**
     * Retrieves the to-do list.
     *
     * @return The to-do list.
     */
    public ToDoList getToDo() {
        return toDo;
    }

    /**
     * Retrieves the degree of urgency for tasks.
     *
     * @return An array of task degree of urgency.
     */
    public static Task.DegreeUrgency[] getDegreeOfUrgency() {
        return Entry.getDegreeOfUrgency();
    }

    /**
     * Registers a new task.
     *
     * @param entryDto The entry DTO representing the task.
     * @return An optional containing the new task if registered successfully, empty otherwise.
     */
    public Optional<Entry> registerNewTask(EntryDto entryDto) {
        Optional<Entry> newEntry = Optional.empty();
        Entry entry = mapper.entryDtoToEntryCreate(entryDto);
        if (toDo.saveNewEntry(entry)) {
            newEntry = Optional.of(entry);
        }
        return newEntry;
    }

    /**
     * Assigns a task on the agenda.
     *
     * @param entryDto The entry DTO representing the task to be assigned.
     * @return An optional containing the task assigned on the agenda if successful, empty otherwise.
     */
    public Optional<Entry> assignEntryOnAgenda(EntryDto entryDto) {
        Optional<Entry> agendaEntry = Optional.empty();
        Entry entry = toDo.searchForEntryToDo(entryDto);
        mapper.entryDtoToEntry(entryDto, entry);
        if (toDo.remove(entry)) {
            agenda.add(entry);
          //  agenda.saveToDB();
            agendaEntry = Optional.of(agenda.getLast());
        }
        return agendaEntry;
    }

    /**
     * Cancels a task on the agenda.
     *
     * @param entryDto The entry DTO representing the task to be canceled.
     * @return An optional containing the canceled task if successful, empty otherwise.
     */
    public Optional<Entry> cancelEntry(EntryDto entryDto) {
        Optional<Entry> agendaEntry = Optional.empty();
        Entry entry = agenda.searchForEntryAgenda(entryDto);
        mapper.entryDtoToEntry(entryDto, entry);
        if (entry.isCanceled()) {
            // agenda.saveToDB();
            agendaEntry = Optional.of(entry);
        }
        return agendaEntry;
    }

    /**
     * Postpones a task on the agenda.
     *
     * @param entryDto The entry DTO representing the task to be postponed.
     * @return An optional containing the postponed task if successful, empty otherwise.
     */
    public Optional<Entry> postponeEntry(EntryDto entryDto) {
        Optional<Entry> agendaEntry = Optional.empty();
        Entry entry = agenda.searchForEntryAgenda(entryDto);
        mapper.entryDtoToEntry(entryDto, entry);
        if (entry.isPostpone()) {
            // agenda.saveToDB();
            agendaEntry = Optional.of(entry);
        }
        return agendaEntry;
    }

    /**
     * Retrieves the hours of work by collaborators.
     *
     * @return The hours of work by collaborators.
     */
    public Tempo getHoursOfWork() {
        return timeOfWorkByCollaborators;
    }

    /**
     * Assigns a vehicle to a task on the agenda.
     *
     * @param entryDto The entry DTO representing the task to which the vehicle is assigned.
     * @return An optional containing the task with the assigned vehicle if successful, empty otherwise.
     */
    public Optional<Entry> assignVehicleOnEntry(EntryDto entryDto) {
        Optional<Entry> agendaEntry = Optional.empty();
        Entry entry = agenda.searchForEntryAgenda(entryDto);
        mapper.entryDtoToEntry(entryDto, entry);
        if (entry.getVehicleList().equals(vehicleMapper.vehicleListDtoToVehicleList(entryDto.getVehicleList()))) {
            // agenda.saveToDB();
            agendaEntry = Optional.of(entry);
        }
        return agendaEntry;
    }

    /**
     * Assigns a team to a task on the agenda.
     *
     * @param entryDto The entry DTO representing the task to which the team is assigned.
     * @return An optional containing the task with the assigned team if successful, empty otherwise.
     */
    public Optional<Entry> assignTeamOnEntry(EntryDto entryDto) {
        Optional<Entry> agendaEntry = Optional.empty();
        Entry entry = agenda.searchForEntryAgenda(entryDto);
        mapper.entryDtoToEntry(entryDto, entry);
        if (haveSameTeam(entryDto, entry)) {
           // agenda.saveToDB();
            agendaEntry = Optional.of(entry);
        }
        return agendaEntry;
    }

    /**
     * Checks if the provided entry DTO and entry have the same team assigned.
     *
     * @param entryDto The entry DTO.
     * @param entry    The entry.
     * @return True if both have the same team assigned, false otherwise.
     */
    public boolean haveSameTeam(EntryDto entryDto, Entry entry) {
        return Objects.equals(entryDto.getTeamAssigned().getSkillsSelected(), entry.getTeamAssigned().getSkills())
                && Objects.equals(entryDto.getTeamAssigned().getTeamName(), entry.getTeamAssigned().getTeamName())
                && Objects.equals(entryDto.getTeamAssigned().getTeamList(), entry.getTeamAssigned().getTeamList());
    }

    /**
     * Completes a task for a collaborator.
     *
     * @param entryDto The entry DTO representing the task to be completed.
     * @return An optional containing the completed task if successful, empty otherwise.
     */
    public Optional<Entry> completeTaskCollaborator(EntryDto entryDto) {
        Optional<Entry> entryCompleted = Optional.empty();
        Entry entry = agenda.searchForEntryAgenda(entryDto);
        mapper.entryDtoToEntry(entryDto, entry);
        if (entry.getStatus().isCompleted() && entry.getCollaboratorFinish().equals(entryDto.getCollaboratorFinish())) {
          //  agenda.saveToDB();
            entryCompleted = Optional.of(entry);
        }
        return entryCompleted;
    }
}