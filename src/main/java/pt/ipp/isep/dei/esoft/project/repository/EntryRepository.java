package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.task.Entry;

import java.util.ArrayList;
import java.util.List;

public class EntryRepository {
    private List<Entry> entries;

    public EntryRepository() {
        entries = new ArrayList<Entry>();
    }

    public List<Entry> getEntries() {
        return entries;
    }

}
