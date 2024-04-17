package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.DocType;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pt.ipp.isep.dei.esoft.project.domain.Collaborator.StatusType.Active;
import static pt.ipp.isep.dei.esoft.project.domain.Collaborator.StatusType.NotActive;

public class CollaboratorRepository {
    private List<Collaborator> collaboratorList;
    public boolean validateDocType(DocType.Type type, int docTypeNumber){
        DocType.Type[] values = DocType.Type.values();
        boolean valueVerify = false;
        if(type==values[0]){
            if(docTypeNumber>0 && docTypeNumber<999999999){
                valueVerify=true;
            }
        } else if(type==values[1]){
            if(docTypeNumber>0 && docTypeNumber<999999999){
                valueVerify=true;
            }
        } else if (type==values[2]){
            if(docTypeNumber>0 && docTypeNumber<999999999){
                valueVerify=true;
            }
        }else {
            throw new IllegalStateException("DocType Not Recognized: " + type);
        }
        return valueVerify;
    }
    public List<Collaborator> getCollaboratorsNotActive(){
        List<Collaborator> collaboratorNotActive=new ArrayList<>();
        for(Collaborator c : collaboratorList){
            if(c.getStatus()==NotActive){
                collaboratorNotActive.add(c);
            }
        }
        return collaboratorNotActive;
    }

    public void activateCollaborators(Team team){
        for(Collaborator c : team.getTeamList()){
            if(c.getStatus()==NotActive){
                c.setStatus(Active);
            }
        }
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

    public Optional<Collaborator> addCollaborator(Collaborator collaborator){
        Optional<Collaborator> newCollaborator = Optional.empty();
        newCollaborator = Optional.of(collaborator);
        if (isValidCollaborator(collaborator)){
            saveCollaborator(collaborator);
        }
        return newCollaborator;
    }

    private void saveCollaborator(Collaborator collaborator) {
        collaboratorList.add(collaborator);
    }

    private boolean isValidCollaborator(Collaborator collaborator) {
        boolean isValid = !collaboratorList.contains(collaborator);
        return isValid;
    }
}
