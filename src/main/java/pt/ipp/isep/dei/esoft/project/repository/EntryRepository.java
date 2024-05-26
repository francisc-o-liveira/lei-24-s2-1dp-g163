package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.domain.ComparatorDates;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntryRepository {
    private List<Entry> agenda;
    private List<Entry> toDo;
    private static Tempo timeOfWorkByCollaborators;
    private static EntryMapper mapper;
    private static final int HOURS_WORK_PER_OMISSION=8;
    private static int REFERENCE_VALUE;


    public EntryRepository() {
        toDo = new ArrayList<Entry>();
        agenda = new ArrayList<Entry>();
        mapper = new EntryMapper();
        REFERENCE_VALUE = 0;
        try {
            timeOfWorkByCollaborators = ApplicationSession.getTimeOfWork();
        }catch (IOException e){
            System.out.println("Error in File Config Please Verify");
            timeOfWorkByCollaborators = new Tempo(HOURS_WORK_PER_OMISSION);
        }
    }

    public List<Entry> getAgenda() {
        return agenda;
    }



    public List<Entry> getToDo() {
        return toDo;
    }


    public static Task.DegreeUrgency[] getDegreeOfUrgency() {
        return Entry.getDegreeOfUrgency();
    }

    public Optional<Entry> registerNewTask(EntryDto entryDto) {
        Optional<Entry> newEntry = Optional.empty();
        Entry entry = mapper.entryDtoToEntryCreate(entryDto,REFERENCE_VALUE++);
            if (saveNewEntry(entry)){
                newEntry = Optional.of(entry);
            }
        return newEntry;
    }

    private boolean saveNewEntry(Entry entry) {
        return toDo.add(entry);
    }

    public Optional<Entry> assignEntryOnAgenda(EntryDto entryDto) {
        Optional<Entry> agendaEntry = Optional.empty();
        Entry entry = searchForEntryToDo(entryDto);
        mapper.entryDtoToEntry(entryDto,entry);
        if (toDo.remove(entry)){
            if(agenda.add(entry)){
                agendaEntry = Optional.of(entry);
            }
        }
        return agendaEntry;
    }

    private Entry searchForEntryToDo(EntryDto entryDto) {
        for (Entry entry : toDo) {
            if (entryDto.equals(entry)) {
                return entry;
            }
        }
        throw new RuntimeException("Entry not found");
    }

    public Optional<Entry> cancelEntry(EntryDto entryDto) {
        Optional<Entry> agendaEntry = Optional.empty();
        Entry entry = searchForEntryAgenda(entryDto);
        mapper.entryDtoToEntry(entryDto,entry);
        if (entry.isCanceled()){
            agendaEntry = Optional.of(entry);
        }
        return agendaEntry;
    }

    // Not Complete need to verify data of others entrys in same time of the postpone date
    public Optional<Entry> postponeEntry(EntryDto entryDto) {
        Optional<Entry> agendaEntry = Optional.empty();
        Entry entry = searchForEntryAgenda(entryDto);
        mapper.entryDtoToEntry(entryDto,entry);
        if (entry.isPostpone()){
            agendaEntry = Optional.of(entry);
        }
        return agendaEntry;
    }

    private Entry searchForEntryAgenda(EntryDto entryDto) {
        for (Entry entry : agenda) {
            if (entryDto.equals(entry)) {
                return entry;
            }
        }
        throw new RuntimeException("Entry not found");
    }

    public List<Vehicle> filterVehicleNotUseInTime(List<Vehicle> vehicleList,EntryDto entryDto) {
        Entry entry = searchForEntryAgenda(entryDto);
        ComparatorDates comparatorDates = new ComparatorDates();
        for(Entry entryAgenda : agenda){
            if(comparatorDates.compare(entry,entryAgenda)==0){ // == 0 significa que ha sobreposiçao das entrys nas datas
                for (Vehicle vehicle : entryAgenda.getVehicleList()){
                    if (vehicleList.contains(vehicle)){
                        vehicleList.remove(vehicle);
                    }
                }
            }
        }
        return vehicleList;
    }

    public Tempo getHoursOfWork() {
        return timeOfWorkByCollaborators;
    }

    public List<Entry> getEntrysByCollaboratorInAgenda(Collaborator collaboratorByEmail) {
        List<Entry> entryCollaboratorList = new ArrayList<>();
        for (Entry entry: agenda){
            if (entry.getTeamAssigned()!=null && entryHaveCollaborator(entry,collaboratorByEmail)){
                entryCollaboratorList.add(entry);
            }
        }
        return entryCollaboratorList;
    }

    private boolean entryHaveCollaborator(Entry entry, Collaborator collaboratorByEmail) {
        for (Collaborator collaborator : entry.getTeamAssigned().getTeamList()){
            if (collaborator.getEmail().equals(collaboratorByEmail.getEmail())){
                return true;
            }
        }
        return false;
    }
}
