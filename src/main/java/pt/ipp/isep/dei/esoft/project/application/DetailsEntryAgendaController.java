package pt.ipp.isep.dei.esoft.project.application;

import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.ArrayList;
import java.util.List;

public class DetailsEntryAgendaController {
    public List<Entry> getList(){
        List<Entry> entries = new ArrayList<>();
        // Example entries
        entries.add(new Entry("Title 1", "Ref 1", "Desc 1", "Informal Desc 1", "Tech Desc 1", 60));
        entries.get(0).postponeEntry(new Date(2024, 5, 1));
        entries.add(new Entry("Title 2", "Ref 2", "Desc 2", "Informal Desc 2", "Tech Desc 2", 120));
        entries.get(1).postponeEntry(new Date(2024, 5, 15));
        entries.add(new Entry("Title 3", "Ref 3", "Desc 3", "Informal Desc 3", "Tech Desc 3", 90));
        entries.get(2).postponeEntry(new Date(2024, 5, 15));
        return entries;
    }
}
