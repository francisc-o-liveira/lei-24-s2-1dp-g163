package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Team;

import java.util.ArrayList;
import java.util.List;

import static pt.ipp.isep.dei.esoft.project.domain.Collaborator.StatusType.Active;
import static pt.ipp.isep.dei.esoft.project.domain.Collaborator.StatusType.NotActive;

public class CollaboratorRepository {
    private List<Collaborator> collaboratorList;

    public List<Collaborator> getCollaboratorsNotActive(){
        List<Collaborator> collaboratorNotActive=new ArrayList<>();
        for(Collaborator c : collaboratorList){
            if(c.getStatus()==NotActive){
                collaboratorNotActive.add(c);
            }
        }
        return collaboratorNotActive;
    }

    public List<Collaborator> getCollaboratorsActive(Team team){
        List<Collaborator> collaboratorActive=new ArrayList<>();
        for(Collaborator c : collaboratorList){
            if(c.getStatus()==Active){
                collaboratorActive.add(c);
            }
        }
        return collaboratorActive;
    }

    public List<String> getCollaboratorIDList(List<Collaborator> allCollaborators){
        List<String> collaboratorsID=new ArrayList<>();
        for(Collaborator c : allCollaborators){
            collaboratorsID.add(c.getCollaboratorID());
        }
        return collaboratorsID;
    }

    public List<Collaborator> getCollaboratorsNotActiveBySkills(List<Skill> skill){
        List<Collaborator> collaboratorNotActiveBySkills=new ArrayList<>();
        for(int i=0; i<skill.size(); i++){
            for(Collaborator c : collaboratorList){
                if(c.getStatus()==NotActive && c.getSkill()==skill.get(i)){
                    collaboratorNotActiveBySkills.add(c);
                }
            }
        }
        sortCollaboratorsByNumberOfSkills(collaboratorNotActiveBySkills);
        return collaboratorNotActiveBySkills;
    }

    public List<Collaborator> getCollaboratorsNotActiveByOneSkill(Skill skill){
        List<Collaborator> collaboratorNotActiveBySkill=new ArrayList<>();
        for(Collaborator c : collaboratorList){
            if(c.getStatus()==NotActive && c.getSkill()==skill){
                collaboratorNotActiveBySkill.add(c);
            }
        }
        return collaboratorNotActiveBySkill;
    }

    public void sortCollaboratorsByNumberOfSkills(List<Collaborator> collaborators){
        Collaborator temp;
        for(int i=0; i<collaborators.size()-1; i++){
            for(int j=i+1; j< collaborators.size(); j++){
                if(collaborators.get(i).getNumberOfSkills()<collaborators.get(j).getNumberOfSkills()){
                    temp=collaborators.get(i);
                    collaborators.set(i, collaborators.get(j));
                    collaborators.set(j, temp);
                }
            }
        }
    }
}
