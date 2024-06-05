package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;

import java.util.List;
import java.util.Objects;

public class ToDoList {
    private List<Entry> toDo;

    public List<Entry> getToDo() {
        return toDo;
    }

    public boolean saveNewEntry(Entry entry) {
        for (Entry entry1 : toDo){
            if (entry.getReference().equals(entry1.getReference())){
                throw new RuntimeException("Duplicate entry reference");
            }else {
                return toDo.add(entry);
            }
        }
        return toDo.add(entry);
    }


    public Entry searchForEntryToDo(EntryDto entryDto) {
        for (Entry entry : toDo) {
            if (Objects.equals(entry.getTitle(), entryDto.getTitle()) &&Objects.equals(entry.getDegreeUrgency(), entryDto.getDegreeUrgency()) && Objects.equals(entry.getDescription(), entryDto.getDescription()) && Objects.equals(entry.getExpectedDuration(),entryDto.getExpectedDuration())){
                return entry;
            }
        }
        throw new RuntimeException("Entry not found");
    }


    public boolean remove(Entry entry) {
        return toDo.remove(entry);
    }
}
