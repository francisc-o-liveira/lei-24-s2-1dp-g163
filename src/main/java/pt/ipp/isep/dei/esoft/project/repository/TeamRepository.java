package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.repository.serv.GenerateTeamServ;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;

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
        teams.remove(team);
        saveTeams();
    }

    public void saveFromTeamInDataBase(Team team) {
        if (!teams.contains(team)) {
            saveTeam(team);
            saveTeams();
        }
    }

    private void saveTeams() {
        cleanFile(MainApp.getTeamDataBaseFile());
        try (FileOutputStream fileOut = new FileOutputStream(MainApp.getTeamDataBaseFile());
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(teams);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cleanFile(String teamDataBaseFile) {
        File file = new File(MainApp.getTeamDataBaseFile());
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print("");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + file, e);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromTeamDataBase() throws CloneNotSupportedException, IOException {
        File file = new File(MainApp.getTeamDataBaseFile());
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Team database file did not exist and has been created. Starting with an empty list.");
                } else {
                    throw new IOException("Team database file does not exist and could not be created.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new IOException("An error occurred while trying to create the team database file.", e);
            }
        }
        try (FileInputStream fileIn = new FileInputStream(file)) {
            if (fileIn.getChannel().size()>0){
                ObjectInputStream in = new ObjectInputStream(fileIn);
                List<Team> teamList = (List<Team>) in.readObject();
                loadInSystem(teamList);
            }
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadInSystem(List<Team> teams) throws CloneNotSupportedException {
        for (Team team : teams) {
            saveTeam(team);
        }
    }
}
