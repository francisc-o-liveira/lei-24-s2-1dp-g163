package pt.ipp.isep.dei.esoft.project.repository.sortingAlgorithmsServ;

import pt.ipp.isep.dei.esoft.project.domain.dto.GreenSpaceDto;

import java.util.List;

/**Sorting algorithm for the green spaces*/
public class SortingAlgorithm2 implements SortingList {
    @Override
    public List<GreenSpaceDto> sortingList(List<GreenSpaceDto> list) {
        boolean swapping;
        for(int i=0; i<list.size()-1; i++){
            swapping=false;
            for(int j=0;j<list.size()-i-1;j++){
                if(list.get(j).getAreaInHectares()>list.get(j+1).getAreaInHectares()){
                    GreenSpaceDto temp=list.get(j);
                    list.set(j,list.get(j+1));
                    list.set(j+1,temp);
                    swapping=true;
                }
            }
            if(swapping==false){
                break;
            }
        }
        return list;
    }
}
