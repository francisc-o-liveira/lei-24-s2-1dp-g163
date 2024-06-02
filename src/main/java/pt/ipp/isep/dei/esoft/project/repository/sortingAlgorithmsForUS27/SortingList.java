package pt.ipp.isep.dei.esoft.project.repository.sortingAlgorithmsForUS27;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;

import java.util.List;

public interface SortingList {

    public List<GreenSpaceDto> sortingList(List<GreenSpaceDto> greenSpaceDtoList);
}
