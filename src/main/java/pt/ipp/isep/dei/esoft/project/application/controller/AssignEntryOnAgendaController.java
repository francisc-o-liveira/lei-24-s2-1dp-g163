package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.List;

public class AssignEntryOnAgendaController {

    private EntryRepository entryRepository;

    private EntryMapper mapper;

    public AssignEntryOnAgendaController() {
        entryRepository = Repositories.getInstance().getEntryRepository();
    }

    public List<EntryDto> getToDoList(){
        return mapper.entryListToEntryDtoList(entryRepository.getToDo());
    }

    public boolean assignEntryOnAgenda(EntryDto entryDto){
        return entryRepository.assignEntryOnAgenda(entryDto).isPresent();
    }
}
