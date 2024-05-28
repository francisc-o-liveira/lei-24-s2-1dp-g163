package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;
import pt.ipp.isep.dei.esoft.project.domain.task.Task;
import pt.ipp.isep.dei.esoft.project.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.mapper.GreenSpaceMapper;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Organization;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.List;

public class RegisterTaskController {
    private Organization org;
    private static GreenSpaceMapper greenMapper;

    private ApplicationSession session;

    private EntryMapper mapper;

    private EntryRepository entryRepository;

    public RegisterTaskController() {
        org = Repositories.getInstance().getOrganizationRepository();
        greenMapper = new GreenSpaceMapper();
        entryRepository = Repositories.getInstance().getEntryRepository();
        session = ApplicationSession.getInstance();
        mapper = new EntryMapper();
    }

    public List<GreenSpaceDto> getGreenSpaceList(){
        return greenMapper.greenSpaceListToGreenSpaceDto(org.getGreenSpaceListByManagerEmail(getManagerFromSession()));
    }

    public static Task.DegreeUrgency[] getDegreOfUrgency(){
        return EntryRepository.getDegreeOfUrgency();
    }

    public boolean registerTaskEntry(EntryDto entryDto){
        if (entryRepository.registerNewTask(entryDto).isPresent()){
           return true;
        }
        return false;
    }

    public List<EntryDto> getToDoList(){
        return mapper.entryListToEntryDtoList(entryRepository.getToDo());
    }

    private String getManagerFromSession(){
        return session.getCurrentSession().getUserEmail();
    }
}
