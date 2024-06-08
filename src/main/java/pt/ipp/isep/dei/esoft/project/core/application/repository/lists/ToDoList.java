/**
 * The ToDoList class represents a list of tasks to be done.
 * It provides functionality to manage tasks such as adding, searching, and removing tasks,
 * as well as saving and loading tasks to and from a database.
 */
package pt.ipp.isep.dei.esoft.project.core.application.repository.lists;

import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.core.application.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.ui.Bootstrap;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToDoList {
    /**
     * List of tasks to be done.
     */
    private static List<Entry> toDo = new ArrayList<>();

    /**
     * Retrieves the list of tasks to be done.
     *
     * @return The list of tasks to be done.
     */
    public List<Entry> getToDo() {
        return toDo;
    }

    /**
     * Saves a new task to the list of tasks to be done.
     *
     * @param entry The task to be saved.
     * @return True if the task was successfully saved, false otherwise.
     * @throws RuntimeException if a task with the same reference already exists in the list.
     */
    public boolean saveNewEntry(Entry entry) {
        for (Entry entry1 : toDo) {
            if (entry.getReference().equals(entry1.getReference())) {
                throw new RuntimeException("Duplicate entry reference");
            } else {
                saveToDB();
                return toDo.add(entry);
            }
        }
        return toDo.add(entry);
    }

    /**
     * Searches for a task in the list of tasks to be done based on its attributes.
     *
     * @param entryDto The data transfer object representing the task to search for.
     * @return The task if found.
     * @throws RuntimeException if the task is not found in the list.
     */
    public Entry searchForEntryToDo(EntryDto entryDto) {
        for (Entry entry : toDo) {
            if (Objects.equals(entry.getTitle(), entryDto.getTitle()) && Objects.equals(entry.getDegreeUrgency(), entryDto.getDegreeUrgency()) && Objects.equals(entry.getDescription(), entryDto.getDescription()) && Objects.equals(entry.getExpectedDuration(), entryDto.getExpectedDuration())) {
                return entry;
            }
        }
        throw new RuntimeException("Entry not found");
    }

    /**
     * Removes a task from the list of tasks to be done.
     *
     * @param entry The task to be removed.
     * @return True if the task was successfully removed, false otherwise.
     */
    public boolean remove(Entry entry) {
        return toDo.remove(entry);
    }

    /**
     * Saves the list of tasks to be done to the database.
     */
    public void saveToDB() {
        try {
            File file1 = new File(Bootstrap.getTaskDataBaseFile());
            PrintWriter writer = new PrintWriter(new FileWriter(file1));
            writer.print("");
            writer.close();

            FileOutputStream file = new FileOutputStream(file1, true);
            ObjectOutputStream out;
            out = new ObjectOutputStream(file);
            out.writeObject(getToDo());

            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the list of tasks to be done from the database.
     *
     * @throws IOException if an I/O error occurs while reading from the database file.
     */
    @SuppressWarnings("unchecked")
    public void loadFromDataBase() throws IOException {
        List<Entry> loadEntry;
        File file = new File(Bootstrap.getTaskDataBaseFile());
        if (!file.exists()) {
            throw new IOException("Task database file does not exist. Starting with an empty list.");
        }
        if (file.length() == 0) {
            loadEntry = new ArrayList<>();
        } else {
            try (FileInputStream fileIn = new FileInputStream(file)) {
                if (fileIn.getChannel().size() > 0) {
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    loadEntry = (List<Entry>) in.readObject();
                    loadInSystem(loadEntry);
                }
            } catch (ClassNotFoundException | CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Loads tasks into the system from a list.
     *
     * @param entries The list of tasks to be loaded.
     * @throws CloneNotSupportedException if the cloning of tasks is not supported.
     */
    private void loadInSystem(List<Entry> entries) throws CloneNotSupportedException {
        for (Entry entry : entries) {
            toDo.add(entry);
        }
    }
}
