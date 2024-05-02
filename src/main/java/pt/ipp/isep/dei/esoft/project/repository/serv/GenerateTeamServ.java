package pt.ipp.isep.dei.esoft.project.repository.serv;

import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;

import java.util.List;
import java.util.Optional;

public interface GenerateTeamServ {

    public Optional<Team> generateTeam(int minSizeTeam, int maxSizeTeam, List<Skill> skillsSelected, List<Integer> numberCollabForSkill, List<Collaborator> collaboratorsForTeam);

    public boolean[][] getCollabOrderWithMoreSkill(List<Skill> skillsSelected, List<Collaborator> collaboratorsForTeam);

    public void sortCollabsWithMoreSkill(boolean[][] yesOrNoSkill, List<Collaborator> collaboratorsForTeam);

    public int numberSkillCollab(boolean[][] yesOrNoSkill, int i);

    public boolean collabororatorUpgradeTeam(boolean[] yesOrNoSkill, int[] generatedTrue);

    public boolean teamIsPossible(Team create);

    public boolean teamIsCompleted(int[] generatedTrue);

    public int[] getNumberSkill(List<Integer> numberCollabForSkill);

    public void getSkillByOrder(List<Skill> skillsSelected, List<Integer> numberCollabForSkill);

}

