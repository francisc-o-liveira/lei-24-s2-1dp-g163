package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.repository.serv.GenerateTeamServ;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;
import pt.ipp.isep.dei.esoft.project.utilities.AppendableObjectOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository class for managing teams.
 */
public class TeamRepository {

    private List<Team> teams;

    /**
     * Constructs a new TeamRepository.
     */
    public TeamRepository(){
        teams = new ArrayList<>();
    }

    /**
     * Adds a team to the repository.
     *
     * @param team the team to be added
     * @return an Optional containing the added Team, if successful, otherwise an empty Optional
     */
    public Optional<Team> addTeam(Team team){
        Optional<Team> newTeam = Optional.empty();
        boolean operationSucess = false;
        if(teamIsValid(team)){
            newTeam = Optional.of(team.clone());
            operationSucess = teams.add(newTeam.get());
            saveFromTeamInDataBase(team);
        }
        if (!operationSucess){
            newTeam = Optional.empty();
        }
        return newTeam;
    }

    private boolean teamIsValid(Team team) {
        boolean isValid = !teams.contains(team);
        return isValid;
    }

    /**
     * Removes a team from the repository.
     *
     * @param team the team to be removed
     * @return the list of teams after removal
     */
    public List<Team> removeTeam(Team team){
        if (teams.contains(team)){
            teams.remove(team);
            removeFromTeamDataBase(team);
        }
        return teams;
    }

    /**
     * Retrieves teams with specified skills.
     *
     * @param skills list of skills to filter teams by
     * @return list of teams with the specified skills
     */
    public List<Team> getTeamBySkill(List<Skill> skills){
        List<Team> teamWithSkills=new ArrayList<>();
        for(int i=0; i<skills.size(); i++){
            for(Team team : teams){
                if(team.getSkills().equals(skills.get(i))){
                    teamWithSkills.add(team);
                }
            }
        }
        return teamWithSkills;
    }

    /**
     * Retrieves all teams in the repository.
     *
     * @return the list of all teams in the repository
     */
    public List<Team> getTeams(){
        return teams;
    }

    /**
     * Saves a team to the repository.
     *
     * @param teamCreated the team to be saved
     * @return true if the team is successfully saved, otherwise false
     */
    public boolean saveTeam(Team teamCreated) {
        return teams.add(teamCreated);
    }

    public void removeFromTeamDataBase(Team team) {
        Team teamLoaded;
        try {
            FileOutputStream fileOut = new FileOutputStream(MainApp.getTeamDataBaseFile());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            if (!teams.contains(team)){
                out.writeObject(teams);
            }
            out.close();
            fileOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFromTeamInDataBase(Team team){
        try {
            FileOutputStream file = new FileOutputStream(MainApp.getTeamDataBaseFile());
            ObjectOutputStream out;
            // If the file already has content, we need to use the AppendableObjectOutputStream
            out = new ObjectOutputStream(file);
            if (teams.contains(team)){
                out.writeObject(teams);
            }
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromTeamDataBase(){
        List<Team> teamLoaded;
        try {
            FileInputStream file = new FileInputStream(MainApp.getTeamDataBaseFile());
            if (file.getChannel().size() > 0){
                ObjectInputStream in = new ObjectInputStream(file);
                while (true) {
                    try {
                        teamLoaded = (List<Team>) in.readObject();
                        loadInSystem(teamLoaded);
                    } catch (EOFException e) {
                        break;
                    }
                }
                in.close();
            }
            file.close();
        } catch (ClassNotFoundException | IOException | CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadInSystem(List<Team> team) throws CloneNotSupportedException {
        for (Team teamLoaded : team) {
            saveTeam(teamLoaded);
        }
    }
}
