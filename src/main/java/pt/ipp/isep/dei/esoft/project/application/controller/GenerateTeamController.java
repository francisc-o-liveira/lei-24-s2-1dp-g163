package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Team;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;

import java.util.List;

public class GenerateTeamController {
    public TeamRepository teamRepository;
    public CollaboratorRepository collaboratorRepository;
    public SkillRepository skillRepository;
    public void generateTeam(List<Collaborator> collaboratorsTeam, int sizeTeam, Skill skillsSelected, int maxSize, int minSize){
        if(sizeTeam>maxSize || sizeTeam<minSize){
            System.out.printf("Invalid team size.");
        }
        Team team= new Team(collaboratorsTeam,sizeTeam,skillsSelected);
    }

    public List<Team> getTeam(){
        return teamRepository.getTeam();
    }

    public List<Team> getTeamBySkills(Skill skills){
        return teamRepository.getTeamBySkill(skills);
    }

    public List<Skill> getSkillList(){
        return skillRepository.getSkillList();
    }

    public List<Collaborator> getCollaboratorsNotActive(){
        return collaboratorRepository.getCollaboratorsNotActive();
    }

    public List<Collaborator> activeCollaboratorsList(Team team){
        return collaboratorRepository.getCollaboratorsActive(team);
    }



    /*+ RegisterCollaboratorController(collaboratorRepository, jobCategoryRepository)
    - getHRMFromSession()*/

}
