package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntryRepository {
    private List<Entry> agenda;
    private List<Entry> toDo;
    private static Tempo timeOfWorkByCollaborators;
    private static EntryMapper mapper;
    private  static final int HOURS_WORK_PER_OMISSION=8;

    public EntryRepository() {
        toDo = new ArrayList<Entry>();
        agenda = new ArrayList<Entry>();
        mapper = new EntryMapper();
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


}
