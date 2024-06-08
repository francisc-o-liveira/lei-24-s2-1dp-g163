package pt.ipp.isep.dei.esoft.project.core.application.domain.dto;

import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Skill;

import java.util.List;

/**
 * Represents a Data Transfer Object for a Team in the system.
 */
public class TeamDto {

    private List<Collaborator> collaboratorsTeam;
    private List<Skill> skillsSelected;
    private String teamName;

    /**
     * Constructor for initializing a TeamDto with required parameters.
     *
     * @param collaboratorsTeam the list of collaborators in the team
     * @param skillsSelected    the list of skills selected for the team
     * @param teamName          the name of the team
     */
    public TeamDto(List<Collaborator> collaboratorsTeam, List<Skill> skillsSelected, String teamName) {
        this.skillsSelected = skillsSelected;
        this.teamName = teamName;
        this.collaboratorsTeam = collaboratorsTeam;
    }

    /**
     * Gets the list of collaborators in the team.
     *
     * @return the list of collaborators in the team
     */
    public List<Collaborator> getTeamList() {
        return collaboratorsTeam;
    }

    /**
     * Gets the list of skills selected for the team.
     *
     * @return the list of skills selected for the team
     */
    public List<Skill> getSkillsSelected() {
        return skillsSelected;
    }

    /**
     * Gets the name of the team.
     *
     * @return the name of the team
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Returns the name of the team as its string representation.
     *
     * @return the name of the team
     */
    @Override
    public String toString() {
        return String.format("%s", teamName);
    }
}
