package pt.ipp.isep.dei.esoft.project.domain;


import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;
import pt.ipp.isep.dei.esoft.project.utilities.TimePeriod;

import java.util.Calendar;
import java.util.Comparator;

/**
 * Class Comparator to compare two dates
 */
public class ComparatorDates implements Comparator<Entry> {
    @Override
    public int compare(Entry a1, Entry a2) {
        TimePeriod time1 = a1.getTimePeriod();
        TimePeriod time2 = a2.getTimePeriod();
        Date dateStart1 = time1.getStartDate();
        Date dateStart2 = time2.getStartDate();
        Date dateEnd1 = time1.getEndDate();
        Date dateEnd2 = time2.getEndDate();
        Tempo time1Start = time1.getStartHour();
        Tempo time2Start = time2.getStartHour();
        Tempo time1End = time1.getEndHour();
        Tempo time2End = time2.getEndHour();
        // Combine date and time for complete comparison
        Calendar start1 = Calendar.getInstance();
        start1.setTime(new java.util.Date(dateStart1.getYear(),dateStart1.getMonth(),dateStart1.getDay()));
        start1.set(Calendar.HOUR_OF_DAY, time1Start.getHoras());
        start1.set(Calendar.MINUTE, time1Start.getMinutos());
        Calendar start2 = Calendar.getInstance();
        start2.setTime(new java.util.Date(dateStart2.getYear(),dateStart2.getMonth(),dateStart2.getDay()));
        start2.set(Calendar.HOUR_OF_DAY, time2Start.getHoras());
        start2.set(Calendar.MINUTE, time2Start.getMinutos());
        Calendar end1 = Calendar.getInstance();
        end1.setTime(new java.util.Date(dateEnd1.getYear(),dateEnd1.getMonth(),dateEnd1.getDay()));
        end1.set(Calendar.HOUR_OF_DAY, time1End.getHoras());
        end1.set(Calendar.MINUTE, time1End.getMinutos());

        Calendar end2 = Calendar.getInstance();
        end2.setTime(new java.util.Date(dateEnd2.getYear(),dateEnd2.getMonth(),dateEnd2.getDay()));
        end2.set(Calendar.HOUR_OF_DAY, time2End.getHoras());
        end2.set(Calendar.MINUTE, time2End.getMinutos());
        // Check if start and end are identical
        if (start1.equals(start2) && end1.equals(end2)) {
            return 0;
        }
        // Check if the start date is after the end date in either entry
        if (start1.after(end1) || start2.after(end2)) {
            return -1;
        }
        // Check for overlapping periods
        if ((start1.before(end2) && end1.after(start2)) || (start2.before(end1) && end2.after(start1))) {
            return 0; // Overlapping periods
        }
        // Compare by start date and time if no overlap is found
        return start1.compareTo(start2);
    }

}






