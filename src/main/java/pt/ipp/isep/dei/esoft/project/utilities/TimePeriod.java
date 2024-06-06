package pt.ipp.isep.dei.esoft.project.utilities;

import java.io.Serializable;
import java.util.Calendar;

public class TimePeriod implements Serializable {

        private Date startDate;
        private Date endDate;
        private Tempo timeOfWork;
        private Tempo startHour;
        private Tempo endHour;

        public TimePeriod(Tempo startHour, Date date2, Tempo expectedDuration, Tempo timeOfWork) {
            this.startDate = date2;
            this.timeOfWork = timeOfWork;
            this.startHour = startHour;

            int totalMinutes = expectedDuration.toMinutos();
            int minutesPerDay = timeOfWork.toMinutos();

            // Calculate days required to complete the work
            int daysOfWork = totalMinutes / minutesPerDay;
            int remainingMinutes = totalMinutes % minutesPerDay;

            if (remainingMinutes > 0) {
                daysOfWork++;
            }

            // Calculate end date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date(startDate.getYear(),startDate.getMonth(), startDate.getDay()));
            calendar.add(Calendar.DAY_OF_YEAR, daysOfWork);

            // Calculate end hour
            int startHourMinutes = startHour.toMinutos();
            int endHourMinutes = startHourMinutes + remainingMinutes;

            int endHour = endHourMinutes / 60;
            int endMinute = endHourMinutes % 60;

            this.endHour = new Tempo(endHour, endMinute);

            calendar.set(Calendar.HOUR_OF_DAY, endHour);
            calendar.set(Calendar.MINUTE, endMinute);

            this.endDate = new Date(calendar.getTime());
        }

        public Date getEndDate() {
            return endDate;
        }

        public Date getStartDate() {
            return startDate;
        }

    public Tempo getStartHour() {
            return startHour;
    }
    public Tempo getEndHour() {
            return endHour;
    }
}
