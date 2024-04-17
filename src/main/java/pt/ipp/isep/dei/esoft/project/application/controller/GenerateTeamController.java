package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Team;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.GenerateTeamUI;

import java.util.ArrayList;
import java.util.List;

public class GenerateTeamController {
    public TeamRepository teamRepository;
    public CollaboratorRepository collaboratorRepository;
    public SkillRepository skillRepository;

    /** Team com várias Skills
     * */
    public void generateTeam(List<Collaborator> collaborators, int sizeTeam, List<Skill> skillsSelected, int maxSize, int minSize){
            List<Collaborator> collaboratorsForTeam=new ArrayList<>();
            collaboratorsForTeam=getCollaboratorsNotActiveBySkills(skillsSelected);
            List<Collaborator> collaboratorsTeam= new ArrayList<>();
            for(Collaborator c : collaboratorsForTeam){
                if(GenerateTeamUI.getSelected(c) && sizeTeam<maxSize){
                    collaboratorsTeam.add(c);
                    sizeTeam++;
                }
            }
            Team team=new Team(collaboratorsTeam,sizeTeam,skillsSelected);
    }
    public List<Collaborator> getCollaboratorsNotActiveBySkills(List<Skill> skillsSelected){
        return collaboratorRepository.getCollaboratorsNotActiveBySkills(skillsSelected);
    }

    /** Team com uma Skill*/

    public void generateTeamWithOneSkill(List<Collaborator> collaborators, int sizeTeam, Skill skillSelected, int maxSize, int minSize) {
        List<Collaborator> collaboratorsForTeam = new ArrayList<>();
        collaboratorsForTeam = getCollaboratorsNotActiveByOneSkill(skillSelected);
        List<Collaborator> collaboratorsTeam = new ArrayList<>();
        for (Collaborator c : collaboratorsForTeam) {
            if (GenerateTeamUI.getSelected(c) && sizeTeam < maxSize) {
                collaboratorsTeam.add(c);
                sizeTeam++;
            }
        }
        Team team = new Team(collaboratorsTeam, sizeTeam, skillSelected);
    }
    public List<Collaborator> getCollaboratorsNotActiveByOneSkill(Skill skillSelected){
        return collaboratorRepository.getCollaboratorsNotActiveByOneSkill(skillSelected);
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


    /*public List<Team> getTeam(){
        return teamRepository.getTeam();
    }*/
    /*+ RegisterCollaboratorController(collaboratorRepository, jobCategoryRepository)
    - getHRMFromSession()*/

}
