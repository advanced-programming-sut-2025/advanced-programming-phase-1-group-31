package view;

import controller.GameMenuController;
import model.enums.commands.GameMenuCommands;

import java.util.*;
import java.util.regex.Matcher;
import java.util.function.Consumer;

public class GameMenu implements Menu {
    private final GameMenuController controller;
    private final Map<GameMenuCommands, Consumer<Matcher>> commandHandlers = new HashMap<>();

    public GameMenu() {
        this.controller = new GameMenuController();
        initializeCommandHandlers();
    }

    private void initializeCommandHandlers() {
        commandHandlers.put(GameMenuCommands.CHOOSE_MAP, matcher -> {
            int mapId = Integer.parseInt(matcher.group("mapId"));
            controller.chooseMap(mapId);
        });

        commandHandlers.put(GameMenuCommands.LOAD_GAME, matcher -> {
            controller.loadGame();
        });

        commandHandlers.put(GameMenuCommands.EXIT_GAME, matcher -> {
            controller.exitGame();
        });

        commandHandlers.put(GameMenuCommands.TERMINATE_GAME, matcher -> {
            controller.terminateGame();
        });

        commandHandlers.put(GameMenuCommands.CHANGE_TURN, matcher -> {
            controller.changeTurn();
        });

        commandHandlers.put(GameMenuCommands.SHOW_TIME, matcher -> {
            controller.showTime();
        });

        commandHandlers.put(GameMenuCommands.SHOW_DATE, matcher -> {
            controller.showDate();
        });

        commandHandlers.put(GameMenuCommands.SHOW_DATE_AND_TIME, matcher -> {
            controller.showDateAndTime();
        });

        commandHandlers.put(GameMenuCommands.SHOW_WEEKDAY, matcher -> {
            controller.showWeekday();
        });

        commandHandlers.put(GameMenuCommands.CHEAT_ADVANCE_TIME, matcher -> {
            int number = Integer.parseInt(matcher.group("number"));
            controller.cheatAdvanceTime(number);
        });

        commandHandlers.put(GameMenuCommands.CHEAT_ADVANCE_DATE, matcher -> {
            int number = Integer.parseInt(matcher.group("number"));
            controller.cheatAdvanceDate(number);
        });

        commandHandlers.put(GameMenuCommands.SHOW_SEASON, matcher -> {
            controller.showSeason();
        });

        commandHandlers.put(GameMenuCommands.CHEAT_CREATE_THUNDER, matcher -> {
            int x = Integer.parseInt(matcher.group("X"));
            int y = Integer.parseInt(matcher.group("Y"));
            controller.cheatCreateThunder(x, y);
        });

        commandHandlers.put(GameMenuCommands.SHOW_WEATHER, matcher -> {
            controller.showWeather();
        });

        commandHandlers.put(GameMenuCommands.FORECAST_WEATHER, matcher -> {
            controller.forecastWeather();
        });

        commandHandlers.put(GameMenuCommands.CHEAT_CHANGE_WEATHER, matcher -> {
            String weather = matcher.group("weather");
            controller.cheatChangeWeather(weather);
        });

        commandHandlers.put(GameMenuCommands.BUILD_GREENHOUSE, matcher -> {
            controller.buildGreenhouse();
        });

        commandHandlers.put(GameMenuCommands.CHOOSE_FARM, matcher -> {
            String username = matcher.group("username");
            int farmId = Integer.parseInt(matcher.group("farmId"));
            controller.chooseFarm(username, farmId);
        });

        commandHandlers.put(GameMenuCommands.WALK, matcher -> {
            int x = Integer.parseInt(matcher.group("X"));
            int y = Integer.parseInt(matcher.group("Y"));
            controller.walk(x, y);
        });

        commandHandlers.put(GameMenuCommands.PRINT_MAP, matcher -> {
            int x = Integer.parseInt(matcher.group("X"));
            int y = Integer.parseInt(matcher.group("Y"));
            int size = Integer.parseInt(matcher.group("size"));
            controller.printMap(x, y, size);
        });

        commandHandlers.put(GameMenuCommands.SHOW_LEGEND, matcher -> {
            controller.showLegend();
        });

        commandHandlers.put(GameMenuCommands.SHOW_ENERGY, matcher -> {
            controller.showEnergy();
        });

        commandHandlers.put(GameMenuCommands.CHEAT_CHANGE_ENERGY, matcher -> {
            int value = Integer.parseInt(matcher.group("value"));
            controller.cheatChangeEnergy(value);
        });

        commandHandlers.put(GameMenuCommands.UNLIMITED_ENERGY, matcher -> {
            controller.unlimitedEnergy();
        });

        commandHandlers.put(GameMenuCommands.SHOW_INVENTORY, matcher -> {
            controller.showInventory();
        });

        commandHandlers.put(GameMenuCommands.DISCARD_ITEM, matcher -> {
            String itemName = matcher.group("itemname");
            String numberString = matcher.group("number");
            int number = (numberString != null) ? Integer.parseInt(numberString) : 1;
            controller.discardItem(itemName, number);
        });

        commandHandlers.put(GameMenuCommands.EQUIP_TOOL, matcher -> {
            String toolName = matcher.group("toolname");
            controller.equipTool(toolName);
        });

        commandHandlers.put(GameMenuCommands.SHOW_CURRENT_TOOL, matcher -> {
            controller.showCurrentTool();
        });

        commandHandlers.put(GameMenuCommands.SHOW_AVAILABLE_TOOLS, matcher -> {
            controller.showAvailableTools();
        });

        commandHandlers.put(GameMenuCommands.UPGRADE_TOOL, matcher -> {
            String toolName = matcher.group("toolname");
            controller.upgradeTool(toolName);
        });

        commandHandlers.put(GameMenuCommands.USE_TOOL, matcher -> {
            int direction = Integer.parseInt(matcher.group("direction"));
            controller.useTool(direction);
        });

        commandHandlers.put(GameMenuCommands.SHOW_CRAFT_INFO, matcher -> {
            String craftName = matcher.group("craft-name");
            controller.showCraftInfo(craftName);
        });

        commandHandlers.put(GameMenuCommands.PLANT_SEED, matcher -> {
            String seed = matcher.group("seed");
            int direction = Integer.parseInt(matcher.group("direction"));
            controller.plantSeed(seed, direction);
        });

        commandHandlers.put(GameMenuCommands.SHOW_PLANT, matcher -> {
            int x = Integer.parseInt(matcher.group("X"));
            int y = Integer.parseInt(matcher.group("Y"));
            controller.showPlant(x, y);
        });

        commandHandlers.put(GameMenuCommands.FERTILIZE, matcher -> {
            String fertilizer = matcher.group("fertilizer");
            int direction = Integer.parseInt(matcher.group("direction"));
            controller.fertilize(fertilizer, direction);
        });

        commandHandlers.put(GameMenuCommands.SHOW_WATER_LEFT, matcher -> {
            controller.showWaterLeft();
        });

        commandHandlers.put(GameMenuCommands.FISH, matcher -> {
            String fishingPole = matcher.group("fishingPole");
            controller.fish(fishingPole);
        });

        commandHandlers.put(GameMenuCommands.USE_ARTISAN, matcher -> {
            String artisan = matcher.group("artisan");
            String item = matcher.group("item");
            controller.useArtisan(artisan, item);
        });

        commandHandlers.put(GameMenuCommands.GET_ARTISAN, matcher -> {
            String artisan = matcher.group("artisan");
            controller.getArtisan(artisan);
        });

        commandHandlers.put(GameMenuCommands.TALK_WITH_FRIENDS, matcher -> {
            String username = matcher.group("username");
            String message = matcher.group("message");
            controller.talkWithFriends(username, message);
        });

        commandHandlers.put(GameMenuCommands.SHOW_TALK_HISTORY, matcher -> {
            String username = matcher.group("username");
            controller.showTalkHistory(username);//change enum
        });

        commandHandlers.put(GameMenuCommands.GIFT, matcher -> {
            String username = matcher.group("username");
            String item = matcher.group("item");
            int amount = Integer.parseInt(matcher.group("amount"));
            controller.gift(username, item, amount);
        });

        commandHandlers.put(GameMenuCommands.SHOW_GIFTS, matcher -> {
            controller.showGifts();
        });

        commandHandlers.put(GameMenuCommands.RATE_GIFT, matcher -> {
            int giftNumber = Integer.parseInt(matcher.group("giftNumber"));
            int rate = Integer.parseInt(matcher.group("rate"));
            controller.rateGift(giftNumber, rate);
        });

        commandHandlers.put(GameMenuCommands.SHOW_GIFT_HISTORY, matcher -> {
            String username = matcher.group("username");
            controller.showGiftHistory(username);//change enum
        });

        commandHandlers.put(GameMenuCommands.HUG, matcher -> {
            String username = matcher.group("username");
            controller.hug(username);
        });

        commandHandlers.put(GameMenuCommands.GIVE_FLOWER, matcher -> {
            String username = matcher.group("username");
            controller.giveFlower(username);
        });

        commandHandlers.put(GameMenuCommands.ASK_MARRIAGE, matcher -> {
            String username = matcher.group("username");
            String ring = matcher.group("ring");
            controller.askMarriage(username, ring);
        });

        commandHandlers.put(GameMenuCommands.RESPOND_TO_MARRIAGE, matcher -> {
            String response = matcher.group("response");
            String username = matcher.group("username");
            controller.respondToMarriage(response, username);
        });

        commandHandlers.put(GameMenuCommands.MEET_NPC, matcher -> {
            String npcName = matcher.group("npcName");
            controller.meetNPC(npcName);
        });

        commandHandlers.put(GameMenuCommands.GIFT_NPC, matcher -> {
            String npcName = matcher.group("npcName");
            String item = matcher.group("item");
            controller.giftNPC(npcName, item);
        });

        commandHandlers.put(GameMenuCommands.LIST_NPC_FRIENDSHIPS, matcher -> {
            controller.listNPCFriendships();
        });

        commandHandlers.put(GameMenuCommands.LIST_QUESTS, matcher -> {
            controller.listQuests();
        });

        commandHandlers.put(GameMenuCommands.COMPLETE_QUEST, matcher -> {
            int index = Integer.parseInt(matcher.group("index"));
            controller.completeQuest(index);
        });
    }

    @Override
    public void checkCommand(Scanner scanner) {
        String line = scanner.nextLine();
        boolean matched = false;

        for (GameMenuCommands command : GameMenuCommands.values()) {
            Matcher matcher = command.getMatcher(line);
            if (matcher.matches()) {
                matched = true;
                Consumer<Matcher> action = commandHandlers.get(command);
                if (action != null) {
                    action.accept(matcher);
                } else {
                    System.out.println("Command recognized but not implemented yet.");
                }
                break;
            }
        }

        if (!matched) {
            System.out.println("Invalid command!");
        }
    }
}
