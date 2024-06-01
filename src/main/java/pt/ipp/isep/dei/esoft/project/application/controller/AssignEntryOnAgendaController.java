package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.domain.task.EntryState;
import pt.ipp.isep.dei.esoft.project.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssignEntryOnAgendaController {

    private EntryRepository entryRepository;

    private EntryMapper mapper;

    public AssignEntryOnAgendaController() {
        entryRepository = Repositories.getInstance().getEntryRepository();
        mapper=new EntryMapper();
    }

    public List<EntryDto> getToDoList(){
        return mapper.entryListToEntryDtoList(entryRepository.getToDo());
    }

    public boolean assignEntryOnAgenda(EntryDto entryDto){
        return entryRepository.assignEntryOnAgenda(entryDto).isPresent();
    }

    public List<EntryState.State> getStates(){
        return Arrays.asList(EntryState.State.values());
    }

}
