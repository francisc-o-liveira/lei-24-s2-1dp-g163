package pt.ipp.isep.dei.esoft.project.core.application.repository.sortingAlgorithmsServ;

import pt.ipp.isep.dei.esoft.project.core.application.domain.dto.GreenSpaceDto;

import java.util.List;

/**
 * The SortingList interface defines a contract for sorting algorithms applied to lists of GreenSpaceDto objects.
 */
public interface SortingList {

    /**
     * Sorts a list of GreenSpaceDto objects according to a specific criterion.
     *
     * @param greenSpaceDtoList The list of GreenSpaceDto objects to be sorted.
     * @return The sorted list of GreenSpaceDto objects.
     */
    List<GreenSpaceDto> sortingList(List<GreenSpaceDto> greenSpaceDtoList);
}
