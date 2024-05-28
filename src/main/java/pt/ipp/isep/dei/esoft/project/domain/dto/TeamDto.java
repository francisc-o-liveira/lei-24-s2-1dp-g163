package pt.ipp.isep.dei.esoft.project.domain.dto;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;

import java.util.List;

public class TeamDto {
    private List<Collaborator> collaboratorsTeam;
    private int maxSizeTeam;
    private int minSizeTeam;
    private List<Skill> skillsSelected;
    private String teamName;

    public List<Collaborator> getList() {
        return collaboratorsTeam;
    }
}
