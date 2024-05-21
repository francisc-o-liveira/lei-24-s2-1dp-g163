package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.task.Entry;

import java.util.ArrayList;
import java.util.List;

public class EntryRepository {
    private List<Entry> agenda;
    private List<Entry> toDo;

    public EntryRepository() {
        toDo = new ArrayList<Entry>();
        agenda = new ArrayList<Entry>();
    }

    public List<Entry> getAgenda() {
        return agenda;
    }

    public List<Entry> getToDo() {
        return toDo;
    }


}
