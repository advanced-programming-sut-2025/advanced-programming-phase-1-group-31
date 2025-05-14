package controller;

import model.*;
import model.Tools.Tool;
import model.Tools.TrashCan;
import model.enums.commands.GameMenuCommand;
import model.enums.general.Direction;
import model.enums.general.Weather;
import model.enums.toolTypes.ToolTypes;
import model.enums.toolTypes.TrashCanType;

import java.awt.*;
import java.util.Locale;
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
        else if (GameMenuCommand.SHOW_INVENTORY.getMatcher(input) != null)
            return showInventory();
        else if ((matcher = GameMenuCommand.EQUIP_TOOL.getMatcher(input)) != null)
            return equipTool(matcher);
        else if (GameMenuCommand.SHOW_CURRENT_TOOL.getMatcher(input) != null)
            return showCurrentTool();
        else if (GameMenuCommand.SHOW_AVAILABLE_TOOLS.getMatcher(input) != null)
            return showAvailableTools();
        else if ((matcher = GameMenuCommand.USE_TOOL.getMatcher(input)) != null)
            return useTool(matcher);
        else if ((matcher = GameMenuCommand.DISCARD_ITEM.getMatcher(input)) != null)
            return deleteFromBackpack(matcher);

        return new Result(false, "Invalid command.");
    }

    private Result deleteFromBackpack(Matcher matcher) {
        String name = matcher.group("itemname").trim();
        int amount = -1;
        if (matcher.group("number") != null){
            amount = Integer.parseInt(matcher.group("number").trim());
        }

        TrashCan trashCan = (TrashCan) Game.getActivePlayer().getInventory()
                .isExistToolOrNull(new TrashCan(TrashCanType.Initial));
        return trashCan.work(name, amount);
    }

    private Result useTool(Matcher matcher) {
        Direction direction = Direction.fromString(matcher.group("direction").trim());
        return Game.getActivePlayer().getInHand().work(direction);
    }

    private Result showAvailableTools() {
        return Game.getActivePlayer().getInventory().showTools();
    }

    private Result showCurrentTool() {
        Tool tool = Game.getActivePlayer().getInHand();
        if (tool == null) {
            return new Result(false, "You don't have anything in hand.");
        }
        return new Result(true, "You are now holding: " + tool.getName());
    }

    private Result equipTool(Matcher matcher) {
        String tool1 = matcher.group("toolname").trim();
        Tool tool = ToolTypes.fromString(tool1);
        Player player = Game.getActivePlayer();
        tool = player.getInventory().isExistToolOrNull(tool);
        if (tool == null){
            return new Result(false, "You haven't " + tool1);
        }
        player.setInHand(tool);
        return new Result(true, tool1 + " equipped successfully!");
    }

    private Result showInventory() {
        return Game.getActivePlayer().getInventory().showBackPack();
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
        if (x < 0 || x >= 55 || y < 0 || y >= 35){
            return new Result(false, "It is out of range.");
        }
        Game.getTimeAndDate().thunder(new Point(x, y), Game.getActivePlayer().getFarm().getMainMap());
        return new Result(true, "Thunder...!");
    }

    private Result dateCheating(Matcher matcher) {
        int number = Integer.parseInt(matcher.group("number").trim());
        if (number <= 0){
            return new Result(false, "Please enter positive number");
        }
        Game.getTimeAndDate().addDay(number);
        return showDate();
    }

    private Result timeCheating(Matcher matcher) {
        int number = Integer.parseInt(matcher.group("number").trim());
        if (number <= 0){
            return new Result(false, "Please enter positive number");
        }
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
