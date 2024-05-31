package pt.ipp.isep.dei.esoft.project.sortingAlgorithmsForUS27;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;

import java.util.ArrayList;
import java.util.List;

/**Sorting algorithm for the green spaces*/
public class SortingAlgorithm1 implements SortingList{

    @Override
    public List<GreenSpaceDto> sortingList(List<GreenSpaceDto> list) {
        for(int i=0;i< list.size()-1;i++){
            int biggestIndex=i;
            for(int j=i+1; j<list.size(); j++){
                if(list.get(j).getAreaInHectares()>list.get(biggestIndex).getAreaInHectares()){
                    biggestIndex=j;
                }
            }
            GreenSpaceDto temp=list.get(biggestIndex);
            list.set(biggestIndex,list.get(i));
            list.set(i,temp);
        }
        return list;
    }
}
