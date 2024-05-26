package pt.ipp.isep.dei.esoft.project.domain;


import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.TimePeriod;

import java.util.Comparator;

/**
 * Class Comparator to compare two dates
 */
public class ComparatorDates implements Comparator<Entry> {
    @Override
    public int compare(Entry a1, Entry a2) {
        TimePeriod time1 = a1.getTimePeriod();
        TimePeriod time2 = a2.getTimePeriod();
        Date dateStart1=time1.getStartDate();
        Date dateStart2=time2.getStartDate();
        Date dateEnd1=time1.getEndDate();
        Date dateEnd2=time2.getEndDate();

        if(dateStart1.compareTo(dateStart2)==0){
            return 0;
        }
        if(dateStart1.compareTo(dateEnd1)>0 || dateStart2.compareTo(dateEnd2)>0){ //data de inicio não pode ser anteiror á data do fim
            return -1;
        }
        if(dateStart1.compareTo(dateStart2)>0){ //data do postpone não pode ser anterior á atual
            return -1;
        }
        return 1;
    }

}






