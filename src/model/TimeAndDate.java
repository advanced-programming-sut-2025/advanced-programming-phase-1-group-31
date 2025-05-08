package model;

import model.enums.WeekDays;

import java.time.LocalDateTime;
import java.util.Random;
//cheat code for changing weather must be added.




public class TimeAndDate {
    private static int hour = 9;
    private static int day = 1;
    // private static Seasons season = Seasons.Spring;
    // private static Weather weather = Weather.Sunny;

    public static int getHour() {
        return hour;
    }

    public static int getDay() {
        return day;
    }

    // public static Seasons getSeason() {
    //     return season;
    // }

    // public static Weather getWeather() {return weather;}

    public static void addHour(int addHour) {
        hour += addHour;
        if (hour > 22) {
            int countOfDay = hour / 22;
            hour = hour % 22;
            addDay(countOfDay);
        }
    }

    public static void addDay(int addDay) {
        day += addDay;
        if (day > 28) {
            int countOfSeason = day / 28;
            day = day % 28;
            for (int i = 0; i < countOfSeason; i++) {
                // changeSeason();
            }
        }
        Random random = new Random();

        // switch (season) {
        //     case Winter -> {
        //         weather = switch (random.nextInt(2)) {
        //             case 0 -> Weather.Sunny;
        //             case 1 -> Weather.Snowy;
        //             default -> Weather.Sunny; // fallback
        //         };
        //     }
        //     default -> {
        //         weather = switch (random.nextInt(3)) {
        //             case 0 -> Weather.Sunny;
        //             case 1 -> Weather.Rainy;
        //             case 2 -> Weather.Stormy;
        //             default -> Weather.Sunny; // fallback
        //         };
        //     }
        // }
    }

    // private static void changeSeason() {
    //     if (season.equals(Seasons.Spring)) season = Seasons.Summer;
    //     else if (season.equals(Seasons.Summer)) season = Seasons.Fall;
    //     else if (season.equals(Seasons.Fall)) season = Seasons.Winter;
    //     else season = Seasons.Spring;
    // }
}
