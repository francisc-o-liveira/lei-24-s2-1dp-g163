package pt.ipp.isep.dei.esoft.project.utilities;

public class TimePeriod {
    private Date startDate;
    private Date endDate;
    private Tempo timeOfWork;


    public TimePeriod(Date date2, Tempo time2, Tempo timeOfWork) {
        int minutes = time2.toMinutos();
        int  minutesTotalByDay = timeOfWork.toMinutos();
        int minutesByDay = timeOfWork.getMinutos();
        int daysOfWork = minutes / minutesTotalByDay;
        if(minutes % minutesTotalByDay < 0.5){
            daysOfWork++;
        }
        startDate = date2;
        // END DATE
        int monthsForEnd = 0;
        int yearsForEnd =  0;
        int dayEnd = startDate.getDay()+daysOfWork;
        while (dayEnd>30){
            dayEnd -= 30;
            monthsForEnd ++;
        }
        int monthEnd = startDate.getMonth()+monthsForEnd;
        while (monthEnd > 12){
            monthEnd -= 12;
            yearsForEnd++;
        }
        int yearEnd = startDate.getYear()+yearsForEnd;
        endDate = new Date(yearEnd, monthEnd, dayEnd);
        this.timeOfWork = timeOfWork;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }
}
