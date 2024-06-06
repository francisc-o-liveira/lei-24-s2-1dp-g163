package pt.ipp.isep.dei.esoft.project.repository.sortingAlgorithmsServ;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;

import java.util.List;

/**
 * SortingAlgorithm2 is an implementation of the SortingList interface that sorts a list of GreenSpaceDto objects
 * based on the area in hectares in ascending order using the bubble sort algorithm.
 */
public class SortingAlgorithm2 implements SortingList {

    /**
     * Sorts a list of GreenSpaceDto objects based on the area in hectares in ascending order using the bubble sort algorithm.
     *
     * @param list The list of GreenSpaceDto objects to be sorted.
     * @return The sorted list of GreenSpaceDto objects.
     */
    @Override
    public List<GreenSpaceDto> sortingList(List<GreenSpaceDto> list) {
        boolean swapping;
        for (int i = 0; i < list.size() - 1; i++) {
            swapping = false;
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j).getAreaInHectares() > list.get(j + 1).getAreaInHectares()) {
                    GreenSpaceDto temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapping = true;
                }
            }
            if (!swapping) {
                break;
            }
        }
        return list;
    }
}
