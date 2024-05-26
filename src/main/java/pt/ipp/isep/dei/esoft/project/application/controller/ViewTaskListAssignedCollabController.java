package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.List;

public class ViewTaskListAssignedCollabController {
    private EntryRepository entryRepository;
    private ApplicationSession session;
    private CollaboratorRepository collaboratorRepository;
    private EntryMapper mapper;

    public ViewTaskListAssignedCollabController() {
        entryRepository = Repositories.getInstance().getEntryRepository();
        session = ApplicationSession.getInstance();
        mapper = new EntryMapper();
    }

    public List<EntryDto> getEntrysAssignedToMe(){
        return mapper.entryListToEntryDtoList(entryRepository.getEntrysByCollaboratorInAgenda(getCollaboratorByEmail(getCollaboratorFromSession())));
    }

    private Collaborator getCollaboratorByEmail(String email){
        return collaboratorRepository.getCollaboratorByEmail(email);
    }
    private String getCollaboratorFromSession(){
        return session.getCurrentSession().getUserEmail();
    }
}
