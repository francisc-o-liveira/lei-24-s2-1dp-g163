package pt.ipp.isep.dei.esoft.project.domain.dto;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;

import java.util.List;

public class TeamDto {
    private List<Collaborator> collaboratorsTeam;
    private List<Skill> skillsSelected;
    private String teamName;
    public TeamDto(List<Collaborator> collaboratorsTeam, List<Skill> skillsSelected, String teamName) {
        this.skillsSelected = skillsSelected;
        this.teamName = teamName;
        this.collaboratorsTeam = collaboratorsTeam;
    }
    public List<Collaborator> getTeamList() {
        return collaboratorsTeam;
    }
    public List<Skill> getSkillsSelected() {
        return skillsSelected;
    }
    public String getTeamName() {
        return teamName;
    }
}
