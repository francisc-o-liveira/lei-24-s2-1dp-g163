package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.dto.EntryDto;
import pt.ipp.isep.dei.esoft.project.mapper.EntryMapper;
import pt.ipp.isep.dei.esoft.project.repository.EntryRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

public class ViewDetailsEntryController {

    private EntryRepository entryRepository;

    private EntryMapper mapper;

    public ViewDetailsEntryController(){
        this.entryRepository = Repositories.getInstance().getEntryRepository();
        this.mapper = new EntryMapper();
    }

    public boolean cancelEntry(EntryDto entryDto){
        entryDto.cancel();
        return entryRepository.cancelEntry(entryDto).isPresent();
    }


}
