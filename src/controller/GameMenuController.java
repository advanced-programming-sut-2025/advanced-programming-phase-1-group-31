package controller;

import model.Game;
import model.Result;
import model.TimeAndDate;
import model.enums.commands.GameMenuCommand;
import model.enums.general.Weather;

import java.awt.*;
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
        else if ((matcher = GameMenuCommand.CHEAT_ADVANCE_DATE.getMatcher(input)) != null)
            return dateCheating(matcher);
        else if ((matcher = GameMenuCommand.CHEAT_CREATE_THUNDER.getMatcher(input)) != null)
            return thunderCheating(matcher);
        else if (GameMenuCommand.FORECAST_WEATHER.getMatcher(input) != null)
            return forecastWeather();
        else if ((matcher = GameMenuCommand.CHEAT_CHANGE_WEATHER.getMatcher(input)) != null)
            return weatherCheating(matcher);

        return new Result(false, "Invalid command.");
    }

    private Result weatherCheating(Matcher matcher) {
        String weatherString = matcher.group("weather").trim();
        Weather weather;
        try {
            weather = Weather.valueOf(weatherString);
        } catch (IllegalArgumentException e) {
            return new Result(false,
                    "Invalid weather name. Use one of: Sunny, Rainy, Stormy, Snowy.");
        }
        Game.getTimeAndDate().setTomorrowWeather(weather);
        return new Result(true, "Tomorrow weather has been manipulated...!");
    }

    private Result forecastWeather() {
        return new Result(true, "It will be \""
                + Game.getTimeAndDate().getTomorrowWeather() + "\" tomorrow!");
    }

    private Result thunderCheating(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("X").trim());
        int y = Integer.parseInt(matcher.group("Y").trim());
        // must be in range check it
        if ()
        Game.getTimeAndDate().thunder(new Point(x, y));
        return new Result(true, "Thunder...!");
    }

    private Result dateCheating(Matcher matcher) {
        int number = Integer.parseInt(matcher.group("number").trim());
        Game.getTimeAndDate().addDay(number);
        return showDate();
    }

    private Result timeCheating(Matcher matcher) {
        int number = Integer.parseInt(matcher.group("number").trim());
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
