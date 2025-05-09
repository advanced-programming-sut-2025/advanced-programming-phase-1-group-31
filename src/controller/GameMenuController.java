package controller;

import model.Game;
import model.Result;
import model.TimeAndDate;
import model.enums.commands.GameMenuCommand;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenuController {
    public Result run(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if (GameMenuCommand.SHOW_SEASON.getMatcher(input) != null)
            return showSeason();
        else if (GameMenuCommand.SHOW_DATE.getMatcher(input) != null)
            return showDate();
        else if (GameMenuCommand.SHOW_DATE_AND_TIME.getMatcher(input) != null)
            return showDateTime();
        else if (GameMenuCommand.SHOW_TIME.getMatcher(input) != null)
            return showTime();
        else if (GameMenuCommand.SHOW_WEATHER.getMatcher(input) != null)
            return showWeather();
        else if (GameMenuCommand.SHOW_WEEKDAY.getMatcher(input) != null)
            return showWeekday();
        else if ((matcher = GameMenuCommand.CHEAT_ADVANCE_TIME.getMatcher(input)) != null)
            return timeCheating(matcher);
        else if (GameMenuCommand.CHEAT_ADVANCE_DATE.getMatcher(input) != null)
            return dateCheating();
        else if (GameMenuCommand.CHEAT_CREATE_THUNDER.getMatcher(input) != null)
            return thunderCheating();
        else if (GameMenuCommand.FORECAST_WEATHER.getMatcher(input) != null)
            return forecastWeather();
        else if (GameMenuCommand.CHEAT_CHANGE_WEATHER.getMatcher(input) != null)
            return weatherCheating();

        return new Result(false, "Invalid command.");
    }

    private Result weatherCheating() {
    }

    private Result forecastWeather() {
    }

    private Result thunderCheating() {
    }

    private Result dateCheating(Matcher matcher) {
        int number = Integer.parseInt(matcher.group("number"));
        Game.getTimeAndDate().addDay(number);
        return showDate();
    }

    private Result timeCheating(Matcher matcher) {
        int number = Integer.parseInt(matcher.group("number"));
        Game.getTimeAndDate().addHour(number);
        return showTime();
    }

    private Result showWeekday() {
        return new Result(true, Game.getTimeAndDate().getDayOfWeek().name());
    }

    private Result showWeather() {
        return new Result(true, Game.getTimeAndDate().getWeather().name());
    }

    private Result showTime() {
        TimeAndDate time = Game.getTimeAndDate();
        if (time.getHour() >= 12) {
            return new Result(true, time.getHour() + ":00 PM");
        }
        return new Result(true, time.getHour() + ":00 AM");
    }

    private Result showDateTime() {
        TimeAndDate time = Game.getTimeAndDate();
        if (time.getHour() >= 12) {
            return new Result(true, "Day " + time.getDay() + " of "
                    + time.getSeason() + ", " + time.getHour() + ":00 PM");
        }
        return new Result(true, "Day " + time.getDay() + " of "
                + time.getSeason() + ", " + time.getHour() + ":00 AM");
    }

    private Result showDate() {
        TimeAndDate time = Game.getTimeAndDate();
        return new Result(true, "Day " + time.getDay() + " of " + time.getSeason());
    }

    private Result showSeason() {
        return new Result(true, Game.getTimeAndDate().getSeason().name());
    }

}
