package pt.ipp.isep.dei.esoft.project.domain;


import pt.ipp.isep.dei.esoft.project.domain.task.Entry;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.ipp.isep.dei.esoft.project.utilities.Tempo;
import pt.ipp.isep.dei.esoft.project.utilities.TimePeriod;

import java.util.Calendar;
import java.util.Comparator;

/**
 * The type Comparator dates.
 */
public class ComparatorDates implements Comparator<Entry> {

    /**
     * Compare two announcements by their publish date
     * @param a1 announcement to compare
     * @param a2 announcement to compare
     * @return result of the comparison
     */
    @Override
    public int compare(Entry a1, Entry a2, Tempo timeOfWork) {
        Date date1 = a1.getStartDate();
        Date date2 = a2.getStartDate();
        Tempo time1 = a1.getExpectedDuration();
        Tempo time2 = a2.getExpectedDuration();
        TimePeriod timePeriod1 = new TimePeriod(date1,time1,timeOfWork);
        TimePeriod timePeriod2 = new TimePeriod(date2,time2,timeOfWork);
        compareDates(timePeriod1,timePeriod2);
        return compareDates(date1, date2);
    }

    /**
     * This method compares two dates
     * @param cal1 date to compare
     * @param cal2 date to compare
     * @return result of the comparison
     */
    private int compareDates(TimePeriod t1, TimePeriod t2) {

        int year1 = cal1.getYear();
        int year2 = cal2.getYear();
        if (year1 != year2) {
            return Integer.compare(year1, year2);
        }

        int month1 = cal1.getMonth();
        int month2 = cal2.getMonth();
        if (month1 != month2) {
            return Integer.compare(month1, month2);
        }

        int day1 = cal1.getDay();
        int day2 = cal2.getDay();
        return Integer.compare(day1, day2);
    }

}






