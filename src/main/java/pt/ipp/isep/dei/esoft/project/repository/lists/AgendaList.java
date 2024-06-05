package pt.ipp.isep.dei.esoft.project.repository.lists;

import pt.ipp.isep.dei.esoft.project.domain.ComparatorDates;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AgendaList implements Serializable {
    private static List<Entry> agenda = new ArrayList<>();

    public List<Entry> getList() {
        return agenda;
    }

    public static Task.DegreeUrgency[] getDegreeOfUrgency() {
        return Entry.getDegreeOfUrgency();
    }

    public void add(final Entry entry) {
       agenda.add(entry);
    }

    public Entry getLast(){
        return agenda.getLast();
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
                if(entry.getStartDate().equals(first) || entry.getStartDate().equals(second)){
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

}
