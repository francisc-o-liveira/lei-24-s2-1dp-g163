package pt.ipp.isep.dei.esoft.project.domain;

import java.util.Calendar;

public class Date {
    private int year;
    private int month;
    private int day;
    private static int[] diasPorMes = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30,
            31, 30, 31};

    public Date(int year, int month, int day){
        this.year=year;
        this.month=month;
        this.day=day;
    }

    public static boolean isAnoBissexto(int ano) {
        return ano % 4 == 0 && ano % 100 != 0 || ano % 400 == 0;
    }

    public int getNumberOfDaysOfLife(Date outraData){
            int totalDias = this.contaDias();
            int totalDias1 = outraData.contaDias();

            return Math.abs(totalDias - totalDias1);
        }

    private int contaDias() {
        int totalDias = 0;

        for (int i = 1; i < year; i++) {
            totalDias += isAnoBissexto(i) ? 366
                                            : 365;
        }
        for (int i = 1; i < month; i++) {
            totalDias += diasPorMes[i];
        }
        totalDias += (isAnoBissexto(year) && month > 2) ? 1
                                                        : 0;
        totalDias += day;

        return totalDias;
    }
}
