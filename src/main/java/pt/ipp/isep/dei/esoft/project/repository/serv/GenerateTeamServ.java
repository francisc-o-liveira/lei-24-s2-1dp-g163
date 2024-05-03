package pt.ipp.isep.dei.esoft.project.repository.serv;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for generating teams.
 */
public interface GenerateTeamServ {

    /**
     * Generates a team based on specified criteria.
     *
     * @param minSizeTeam           minimum size for the team
     * @param maxSizeTeam           maximum size for the team
     * @param skillsSelected        list of skills required for the team
     * @param numberCollabForSkill  list of the number of collaborators required for each skill
     * @param collaboratorsForTeam list of available collaborators to form the team
     * @return an Optional containing the generated Team, if successful, otherwise an empty Optional
     */
    public Optional<Team> generateTeam(int minSizeTeam, int maxSizeTeam, List<Skill> skillsSelected, List<Integer> numberCollabForSkill, List<Collaborator> collaboratorsForTeam, String teamName);

    /**
     * Determines the order of collaborators with the most skills.
     *
     * @param skillsSelected        list of skills selected for the team
     * @param collaboratorsForTeam list of collaborators considered for the team
     * @return a boolean 2D array indicating whether each collaborator has the required skills
     */
    public boolean[][] getCollabOrderWithMoreSkill(List<Skill> skillsSelected, List<Collaborator> collaboratorsForTeam);

    /**
     * Sorts the collaborators based on the order of skills.
     *
     * @param yesOrNoSkill         boolean 2D array indicating whether each collaborator has the required skills
     * @param collaboratorsForTeam list of collaborators considered for the team
     */
    public void sortCollabsWithMoreSkill(boolean[][] yesOrNoSkill, List<Collaborator> collaboratorsForTeam);

    /**
     * Counts the number of skills each collaborator possesses.
     *
     * @param yesOrNoSkill boolean 2D array indicating whether each collaborator has the required skills
     * @param i            index of the collaborator
     * @return the number of skills possessed by the collaborator at the specified index
     */
    public int numberSkillCollab(boolean[][] yesOrNoSkill, int i);

    /**
     * Upgrades collaborator's status to being part of the team.
     *
     * @param yesOrNoSkill  boolean array indicating whether each collaborator has the required skills
     * @param generatedTrue array containing indices of collaborators selected for the team
     * @return true if collaborator is upgraded, otherwise false
     */
    public boolean collabororatorUpgradeTeam(boolean[] yesOrNoSkill, int[] generatedTrue);

    /**
     * Checks if the team formation is possible.
     *
     * @param create the Team object to be checked
     * @return true if team formation is possible, otherwise false
     */
    public boolean teamIsPossible(Team create);

    /**
     * Checks if the team is complete.
     *
     * @param generatedTrue array containing indices of collaborators selected for the team
     * @return true if the team is complete, otherwise false
     */
    public boolean teamIsCompleted(int[] generatedTrue);

    /**
     * Retrieves the number of collaborators required for each skill.
     *
     * @param numberCollabForSkill list of the number of collaborators required for each skill
     * @return an array containing the number of collaborators required for each skill
     */
    public int[] getNumberSkill(List<Integer> numberCollabForSkill);

    /**
     * Retrieves the skills required for the team in the specified order.
     *
     * @param skillsSelected        list of skills selected for the team
     * @param numberCollabForSkill list of the number of collaborators required for each skill
     */
    public void getSkillByOrder(List<Skill> skillsSelected, List<Integer> numberCollabForSkill);
}
