package pt.ipp.isep.dei.esoft.project.repository.sortingAlgorithmsServ;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;

import java.util.List;

/**
 * SortingAlgorithm1 is an implementation of the SortingList interface that sorts a list of GreenSpaceDto objects
 * based on the area in hectares in descending order.
 */
public class SortingAlgorithm1 implements SortingList {

    /**
     * Sorts a list of GreenSpaceDto objects based on the area in hectares in descending order.
     *
     * @param list The list of GreenSpaceDto objects to be sorted.
     * @return The sorted list of GreenSpaceDto objects.
     */
    @Override
    public List<GreenSpaceDto> sortingList(List<GreenSpaceDto> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            int biggestIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).getAreaInHectares() > list.get(biggestIndex).getAreaInHectares()) {
                    biggestIndex = j;
                }
            }
            GreenSpaceDto temp = list.get(biggestIndex);
            list.set(biggestIndex, list.get(i));
            list.set(i, temp);
        }
        return list;
    }
}
