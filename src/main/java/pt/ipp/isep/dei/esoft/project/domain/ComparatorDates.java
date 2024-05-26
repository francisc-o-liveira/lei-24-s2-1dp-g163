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
        // Verifica se as datas de início são iguais
        if (dateStart1.compareTo(dateStart2) == 0) {
            return 0;
        }
        // Verifica se a data de início é posterior à data de fim em qualquer das entradas
        if (dateStart1.compareTo(dateEnd1) > 0 || dateStart2.compareTo(dateEnd2) > 0) {
            return -1;
        }

        // Verifica se há sobreposição entre os períodos
        if ((dateStart1.before(dateEnd2) && dateEnd1.after(dateStart2)) || (dateStart2.before(dateEnd1) && dateEnd2.after(dateStart1))) {
            return 0; // Há sobreposição
        }

        // Se não houver sobreposição, compará-las pela data de início
        return dateStart1.compareTo(dateStart2);
    }

}






