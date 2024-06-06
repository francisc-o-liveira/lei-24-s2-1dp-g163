package pt.ipp.isep.dei.esoft.project.repository.lists;

import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToDoList {
    private static List<Entry> toDo = new ArrayList<>();

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

    public void saveToDB(){
        try {
            File file1=new File(MainApp.getTaskDataBaseFile());
            PrintWriter writer = new PrintWriter(new FileWriter(file1));
            writer.print("");
            writer.close();

            FileOutputStream file = new FileOutputStream(file1, true);
            ObjectOutputStream out;
            out=new ObjectOutputStream(file);
            out.writeObject(getToDo());

            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    public void loadFromDataBase() throws IOException {
        List<Entry> loadEntry;
        File file= new File(MainApp.getTaskDataBaseFile());
        if (!file.exists()) {
            throw new IOException("Task database file does not exist. Starting with an empty list.");
        }
        if(file.length()==0){
            loadEntry=new ArrayList<>();
        } else {
            try (FileInputStream fileIn = new FileInputStream(file)){
                if (fileIn.getChannel().size()>0){
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    loadEntry = (List<Entry>) in.readObject();
                    loadInSystem(loadEntry);
                }
            } catch (ClassNotFoundException | IOException | CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private  void loadInSystem(List<Entry> entries) throws CloneNotSupportedException {
        for (Entry entry : entries) {
            toDo.add(entry);
        }
    }
}
