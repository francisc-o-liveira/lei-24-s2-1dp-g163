package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;

import java.util.List;

public class ViewTaskListAssignedCollabController {
    private EntryRepository entryRepository;
    private ApplicationSession session;
    private CollaboratorRepository collaboratorRepository;
    private EntryMapper mapper;

    public ViewTaskListAssignedCollabController() {
        entryRepository = Repositories.getInstance().getEntryRepository();
        collaboratorRepository= Repositories.getInstance().getCollaboratorRepository();
        session = ApplicationSession.getInstance();
        mapper = new EntryMapper();
    }

    public List<EntryDto> getEntrysAssignedToMe(Date firstDate, Date secondDate){
        return mapper.entryListToEntryDtoList(entryRepository.getEntrysByCollaboratorInAgenda(getCollaboratorByEmail(getCollaboratorFromSession()),firstDate,secondDate));
    }

    private Collaborator getCollaboratorByEmail(String email){
        return collaboratorRepository.getCollaboratorByEmail(email);
    }
    private String getCollaboratorFromSession(){
        return session.getCurrentSession().getUserEmail();
    }




    public boolean assignEntryCompleted(EntryDto entryDto, Date completedDate){
        entryDto.completeTask(completedDate, getCollaboratorByEmail(getCollaboratorFromSession()));
        return entryRepository.completeTaskCollaborator(entryDto).isPresent();
    }
}
