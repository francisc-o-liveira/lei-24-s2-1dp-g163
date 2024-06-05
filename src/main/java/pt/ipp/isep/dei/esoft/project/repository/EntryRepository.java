package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.domain.ComparatorDates;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.mapper.VehicleMapper;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;
import pt.ipp.isep.dei.esoft.project.utilities.AppendableObjectOutputStream;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EntryRepository {
    private AgendaList agenda;
    private ToDoList toDo;
    private static Tempo timeOfWorkByCollaborators;
    private static EntryMapper mapper;
    private static final int HOURS_WORK_PER_OMISSION=8;
    private static VehicleMapper vehicleMapper;

    public EntryRepository() {
        toDo = new ToDoList();
        agenda = new AgendaList();
        mapper = new EntryMapper();
        vehicleMapper = new VehicleMapper();
        try {
            timeOfWorkByCollaborators = ApplicationSession.getTimeOfWork();
        }catch (IOException e){
            System.out.println("Error in File Config Please Verify");
            timeOfWorkByCollaborators = new Tempo(HOURS_WORK_PER_OMISSION);
        }
    }



    public AgendaList getAgenda() {
        return agenda;
    }

    public ToDoList getToDo() {
        return toDo;
    }

    public static Task.DegreeUrgency[] getDegreeOfUrgency() {
        return Entry.getDegreeOfUrgency();
    }

    public Optional<Entry> registerNewTask(EntryDto entryDto) {
        Optional<Entry> newEntry = Optional.empty();
        Entry entry = mapper.entryDtoToEntryCreate(entryDto);
            if (toDo.saveNewEntry(entry)){
                newEntry = Optional.of(entry);
            }
        return newEntry;
    }

    public Optional<Entry> assignEntryOnAgenda(EntryDto entryDto) {
        Optional<Entry> agendaEntry = Optional.empty();
        Entry entry = toDo.searchForEntryToDo(entryDto);
        mapper.entryDtoToEntry(entryDto,entry);
        if (toDo.remove(entry)){
            agenda.add(entry);
            agendaEntry = Optional.of(agenda.getLast());
        }
        return agendaEntry;
    }

    public Optional<Entry> cancelEntry(EntryDto entryDto) {
        Optional<Entry> agendaEntry = Optional.empty();
        Entry entry = agenda.searchForEntryAgenda(entryDto);
        mapper.entryDtoToEntry(entryDto,entry);
        if (entry.isCanceled()){
            agendaEntry = Optional.of(entry);
        }
        return agendaEntry;
    }
    // Not Complete need to verify data of others entrys in same time of the postpone date
    public Optional<Entry> postponeEntry(EntryDto entryDto) {
        Optional<Entry> agendaEntry = Optional.empty();
        Entry entry = agenda.searchForEntryAgenda(entryDto);
        mapper.entryDtoToEntry(entryDto,entry);
        if (entry.isPostpone()){
            agendaEntry = Optional.of(entry);
        }
        return agendaEntry;
    }

    public Tempo getHoursOfWork() {
        return timeOfWorkByCollaborators;
    }




    public Optional<Entry> assignVehicleOnEntry(EntryDto entryDto) {
        Optional<Entry> agendaEntry = Optional.empty();
        Entry entry = agenda.searchForEntryAgenda(entryDto);
        mapper.entryDtoToEntry(entryDto,entry);
        if (entry.getVehicleList().equals(vehicleMapper.vehicleListDtoToVehicleList(entryDto.getVehicleList()))){
            agendaEntry = Optional.of(entry);
        }
        return agendaEntry;
    }


    public Optional<Entry> assignTeamOnEntry(EntryDto entryDto) {
        Optional<Entry> agendaEntry = Optional.empty();
        Entry entry = agenda.searchForEntryAgenda(entryDto);
        mapper.entryDtoToEntry(entryDto,entry);
        if (entry.getTeamAssigned().equals(entryDto.getTeamAssigned())){
            agendaEntry = Optional.of(entry);
        }
        return agendaEntry;
    }


    public Optional<Entry> completeTaskCollaborator(EntryDto entryDto) {
        Optional<Entry> entryCompleted = Optional.empty();
        Entry entry = agenda.searchForEntryAgenda(entryDto);
        mapper.entryDtoToEntry(entryDto,entry);
        if(entry.getStatus().isCompleted() && entry.getCollaboratorFinish().equals(entryDto.getCollaboratorFinish())){
            entryCompleted = Optional.of(entry);
        }
        return entryCompleted;
    }









}
