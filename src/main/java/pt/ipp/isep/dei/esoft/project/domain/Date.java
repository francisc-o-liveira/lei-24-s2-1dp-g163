package pt.ipp.isep.dei.esoft.project.domain;

import java.util.Calendar;

public class Date {
    private int year;
    private int month;
    private int day;

    public Date(int year, int month, int day){
        this.year=year;
        this.month=month;
        this.day=day;
    }

    public int getNumberOfDaysOfLife(Date outraData){
            int totalDias = this.contaDias();
            int totalDias1 = outraData.contaDias();

            return Math.abs(totalDias - totalDias1);
        }

    private int contaDias() {
        int totalDias = 0;

        for (int i = 1; i < ano; i++) {
            totalDias += isAnoBissexto(i) ? 366 : 365;
        }
        for (int i = 1; i < mes; i++) {
            totalDias += diasPorMes[i];
        }
        totalDias += (isAnoBissexto(ano) && mes > 2) ? 1 : 0;
        totalDias += dia;

        return totalDias;
    }
}
