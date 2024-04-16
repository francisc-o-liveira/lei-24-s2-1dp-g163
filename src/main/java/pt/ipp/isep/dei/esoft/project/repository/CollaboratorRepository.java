package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
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
}
