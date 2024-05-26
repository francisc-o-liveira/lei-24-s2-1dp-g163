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
        int year1 = date1.getYear();
        int year2 = date2.getYear();
        if (year1 != year2) {
            return Integer.compare(year1, year2);
        }

        int month1 = date1.getMonth();
        int month2 = date2.getMonth();
        if (month1 != month2) {
            return Integer.compare(month1, month2);
        }

        int day1 = date1.getDay();
        int day2 = date2.getDay();
        return Integer.compare(day1, day2);
    }

}






