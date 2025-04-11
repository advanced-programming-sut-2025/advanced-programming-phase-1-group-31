package model;

import model.enums.Seasons;
import model.enums.WeekDays;

import java.time.LocalDateTime;

public class TimeAndDate {
    private static int hour = 9;
    private static int day = 1;
    private static Seasons season = Seasons.Spring;

    public static int getHour() {
        return hour;
    }

    public static int getDay() {
        return day;
    }

    public static Seasons getSeason() {
        return season;
    }

    public static void addHour(int addHour) {
        hour += addHour;
        if (hour > 24){
            int countOfDay = hour / 24;
            hour = hour % 24;
            addDay(countOfDay);
        }
    }

    public static void addDay(int addDay) {
        day += addDay;
        if (day > 28){
            int countOfSeason = day / 28;
            day = day % 28;
            for (int i = 0; i < countOfSeason; i++){
                changeSeason();
            }
        }
    }

    private static void changeSeason() {
        if (season.equals(Seasons.Spring)) season = Seasons.Summer;
        else if (season.equals(Seasons.Summer)) season = Seasons.Fall;
        else if (season.equals(Seasons.Fall)) season = Seasons.Winter;
        else season = Seasons.Spring;
    }
}
