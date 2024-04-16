package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Employee;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;

import java.util.List;

public class AssignSkillsController {

    public AssignSkillsController(CollaboratorRepository collaboratorRepository, SkillRepository skillRepository){

    }

    public void assignSkills(Collaborator collaboratorID, Skill skills){

    }

    public boolean checkAuthorization(Employee userEmail){

    }

    public List<Collaborator> getCollaboratorIDList(){

    }

    public String getCollaboratorDetails(Employee collaboratorID){

    }

    public void filterAssignedSkills(Collaborator collaboratorSkills, Skill allSkills){

    }

}
