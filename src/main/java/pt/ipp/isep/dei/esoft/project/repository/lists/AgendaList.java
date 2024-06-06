package pt.ipp.isep.dei.esoft.project.repository.lists;

import pt.ipp.isep.dei.esoft.project.domain.ComparatorDates;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;
import pt.ipp.isep.dei.esoft.project.ui.gui.entry.ManageAgendaUI;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.io.*;
import java.util.*;

public class AgendaList implements Serializable , List<Entry> {
    private static List<Entry> agenda = new ArrayList<>();

    public static List<Entry> getList() {
        return agenda;
    }
    public static Task.DegreeUrgency[] getDegreeOfUrgency() {
        return Entry.getDegreeOfUrgency();
    }
    public Entry searchForEntryAgenda(EntryDto entryDto) {
        for (Entry entry : agenda) {
            if (Objects.equals(entry.getTitle(), entryDto.getTitle())
                    &&Objects.equals(entry.getDegreeUrgency(), entryDto.getDegreeUrgency())
                    && Objects.equals(entry.getDescription(), entryDto.getDescription())
                    && Objects.equals(entry.getExpectedDuration(),entryDto.getExpectedDuration())
            ){
                return entry;
            }
        }
        throw new RuntimeException("Entry not found");
    }
    public List<Vehicle> filterVehicleNotUseInTime(List<Vehicle> vehicleList, EntryDto entryDto) {
        Entry entry = searchForEntryAgenda(entryDto);
        ComparatorDates comparatorDates = new ComparatorDates();
        for(Entry entryAgenda : agenda){
            if(comparatorDates.compare(entry,entryAgenda)==0 && !entryAgenda.getStatus().isCanceled()){ // == 0 significa que ha sobreposiçao das entrys nas datas
                for (Vehicle vehicle : entryAgenda.getVehicleList()){
                    if (vehicleList.contains(vehicle)){
                        vehicleList.remove(vehicle);
                    }
                }
            }
        }
        return vehicleList;
    }
    public List<Entry> getEntrysByCollaboratorInAgenda(Collaborator collaboratorByEmail, Date first, Date second) {
        List<Entry> entryCollaboratorList = new ArrayList<>();
        for (Entry entry: agenda){
            if (entry.getTeamAssigned()!=null && entryHaveCollaborator(entry,collaboratorByEmail)){
                if(entry.getStartDate().compareTo(first) > 0 && entry.getStartDate().compareTo(second) < 0){
                    entryCollaboratorList.add(entry);
                }
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
    public List<Team> filterTeamNotActivateInTime(List<Team> teams, EntryDto entryDto) {
        Entry entry = searchForEntryAgenda(entryDto);
        ComparatorDates comparatorDates = new ComparatorDates();
        for(Entry entryAgenda : agenda){
            if(comparatorDates.compare(entry,entryAgenda)==0 && !entryAgenda.getStatus().isCanceled()){ // == 0 significa que ha sobreposiçao das entrys nas datas
                if(teams.contains(entryAgenda.getTeamAssigned())){
                    teams.remove(entryAgenda.getTeamAssigned());
                }else {
                    // If remove team from the Team List it is possible to have some problem with Collaborators there are in a new Team
                    // For this we can check if any team have a Collaborator there are assigned in any task
                    for (Team team : teams){
                        for (Collaborator collaborator : team.getTeamList()){
                            if (entry.getTeamAssigned() == null){
                                break;
                            }
                            for (Collaborator collaboratorCompare : entry.getTeamAssigned().getTeamList()){
                                if (collaborator.getEmail().equals(collaboratorCompare.getEmail())){
                                    teams.remove(team);
                                }
                            }
                        }
                    }
                }
            }
        }
        return teams;
    }
    public void saveToDB(){
        try {
            File file1=new File(MainApp.getEntryDataBaseFile());
            PrintWriter writer = new PrintWriter(new FileWriter(file1));
            writer.print("");
            writer.close();

            FileOutputStream file = new FileOutputStream(file1, true);
            ObjectOutputStream out;
            out=new ObjectOutputStream(file);
            out.writeObject(agenda);

            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    public void loadFromDataBase() throws IOException {
        List<Entry> loadEntry;
        File file= new File(MainApp.getEntryDataBaseFile());
        if (!file.exists()) {
            throw new IOException("Entry database file does not exist. Starting with an empty list.");
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

    private void loadInSystem(List<Entry> entries) throws CloneNotSupportedException {
        for (Entry entry : entries) {
            agenda.add(entry);
        }
    }

    @Override
    public int size() {
        return agenda.size();
    }

    @Override
    public boolean isEmpty() {
        return agenda.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return agenda.contains(o);
    }

    @Override
    public Iterator iterator() {
        return agenda.iterator();
    }

    @Override
    public Entry[] toArray() {
        return (Entry[]) agenda.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return agenda.toArray(a);
    }

    @Override
    public boolean add(Entry o) {
        return agenda.add((Entry) o);
    }

    @Override
    public boolean remove(Object o) {
        return agenda.remove(o);
    }

    @Override
    public boolean addAll(Collection c) {
        return agenda.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return agenda.addAll(index, c);
    }

    @Override
    public void clear() {
            agenda.clear();
    }

    @Override
    public Entry get(int index) {
        return agenda.get(index);

    }

    @Override
    public Entry set(int index, Entry element) {
        return agenda.set(index, (Entry) element);
    }

    @Override
    public void add(int index, Entry element) {
        agenda.add(index, (Entry) element);
    }

    @Override
    public Entry remove(int index) {
        return agenda.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }


    @Override
    public ListIterator listIterator() {
        return agenda.listIterator();
    }

    @Override
    public ListIterator listIterator(int index) {
        return agenda.listIterator(index);
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return List.of(agenda);
    }


    @Override
    public boolean retainAll(Collection c) {
        return agenda.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection c) {
        return agenda.removeAll(c);
    }

    @Override
    public boolean containsAll(Collection c) {
        return agenda.containsAll(c);
    }


}
