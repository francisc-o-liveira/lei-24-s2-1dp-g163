package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.org.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
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
    private  static final int HOURS_WORK_PER_OMISSION=8;
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
}
