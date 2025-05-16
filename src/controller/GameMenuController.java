package controller;

import model.*;
import model.Tools.Tool;
import model.Tools.TrashCan;
import model.enums.commands.GameMenuCommand;
import model.enums.foragings.ForagingCrops;
import model.enums.foragings.ForagingTrees;
import model.enums.general.Direction;
import model.enums.general.Weather;
import model.enums.plantable.Crops;
import model.enums.plantable.Fruits;
import model.enums.plantable.Trees;
import model.enums.toolTypes.ToolTypes;
import model.enums.toolTypes.TrashCanType;
import model.materials.Material;

import java.awt.*;
import java.util.ArrayList;
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
        else if ((matcher = GameMenuCommand.CROP_INFO.getMatcher(input)) != null)
            return cropInfo(matcher);
        else if ((matcher = GameMenuCommand.FORAGING_CROP_INFO.getMatcher(input)) != null)
            return foragingCropInfo(matcher);
        else if ((matcher = GameMenuCommand.TREE_INFO.getMatcher(input)) != null)
            return treeInfo(matcher);
        else if ((matcher = GameMenuCommand.FORAGING_TREE_INFO.getMatcher(input)) != null)
            return foragingTreeInfo(matcher);
        else if ((matcher = GameMenuCommand.FRUITS_INFO.getMatcher(input)) != null)
            return fruitInfo(matcher);
        else if ((matcher = GameMenuCommand.SELECT_PRODUCTS_AVAILABLE.getMatcher(input)) != null) {
            return showAllAvailableProducts(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_PRODUCTS_ALL.getMatcher(input)) != null) {
            return showAllProducts(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_PURCHASE.getMatcher(input)) != null) {
            return purchaseProduct(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_ADD_DOLLARS.getMatcher(input)) != null) {
            return addDollars(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_SELL.getMatcher(input)) != null) {
            return sellProduct(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_FRIENDSHIPS.getMatcher(input)) != null) {
            return showFriendships(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_TALK.getMatcher(input)) != null) {
            return sendMessage(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_TALK_HISTORY.getMatcher(input)) != null) {
            return showTalkHistory(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_GIFT.getMatcher(input)) != null) {
            return sendGift(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_LIST_GIFT.getMatcher(input)) != null) {
            return listGifts(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_RATE_GIFT.getMatcher(input)) != null) {
            return rateGift(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_GIFT_HISTORY.getMatcher(input)) != null) {
            return showGiftHistory(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_HUG.getMatcher(input)) != null) {
            return sendHug(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_FLOWER.getMatcher(input)) != null) {
            return sendFlower(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_MARRIAGE_ASK.getMatcher(input)) != null) {
            return askForMarriage(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_MARRIAGE_RESPOND.getMatcher(input)) != null) {
            return respondToMarriage(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_TRADE_START.getMatcher(input)) != null) {
            return startTrade(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_TRADE.getMatcher(input)) != null) {
            return processTrade(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_LIST_TRADE.getMatcher(input)) != null) {
            return listTrades(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_TRADE_RESPONSE.getMatcher(input)) != null) {
            return respondToTrade(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_TRADE_HISTORY.getMatcher(input)) != null) {
            return showTradeHistory(matcher);
        }
        return new Result(false, "Invalid command.");
    }

    private Result cropInfo(Matcher matcher) {
        String name = matcher.group("name").trim();
        Crops crops = Crops.findByName(name);
        if (crops == null) return new Result(false, name + " doesn't exist");
        String result = "Name: " + crops.getDisplayName() + "\n" +
                "Source: " + crops.getSource().getName() + "\n" +
                "Stages: " + crops.getStages() + "\n" +
                "Total Harvest Time: " + crops.getTotalHarvestTime() + "\n" +
                "One Time: " + crops.isOneTime() + "\n" +
                "Regrowth Time: " + crops.getRegrowthTime() + "\n" +
                "Base Sell Price: " + crops.getBaseSellPrice() + "\n" +
                "Is Edible: " + crops.isEdible() + "\n" +
                "Base Energy: " + crops.getEnergy() + "\n" +
                "Season: " + crops.getSeasons() + "\n" +
                "Can Become Giant: " + crops.isCanBecomeGiant();
        return new Result(true, result);
    }

    private Result foragingCropInfo(Matcher matcher) {
        String name = matcher.group("name").trim();
        ForagingCrops crops = ForagingCrops.findByName(name);
        if (crops == null) return new Result(false, name + " doesn't exist");
        String result = "Name: " + crops.getDisplayName() + ":\n" +
                "Base Sell Price: " + crops.getBaseSellPrice() + "\n" +
                "Base Energy: " + crops.getEnergy() + "\n" +
                "Season: " + crops.getSeasons() + "\n";
        return new Result(true, result);
    }

    private Result treeInfo(Matcher matcher) {
        String name = matcher.group("name").trim();
        Trees trees = Trees.findByName(name);
        if (trees == null) return new Result(false, name + " doesn't exist");
        String result = "Name: " + trees.getName() + "\n" +
                "Source: " + trees.getSource().getName() + "\n" +
                "Stages: " + trees.getStages() + "\n" +
                "Total Harvest Time: " + trees.getTotalHarvestTime() + "\n" +
                "Fruit Name: " + trees.getFruit().getName() + "\n" +
                "Harvest Cycle: " + trees.getHarvestCycle() + "\n";
        return new Result(true, result);
    }

    private Result foragingTreeInfo(Matcher matcher) {
        String name = matcher.group("name").trim();
        ForagingTrees tree = ForagingTrees.findByName(name);
        if (tree == null) return new Result(false, name + " doesn't exist");
        String result = "Name: " + tree.getName() + ":\n" +
                "Season: " + tree.getSeasons() + "\n";
        return new Result(true, result);
    }

    private Result fruitInfo(Matcher matcher) {
        String name = matcher.group("name").trim();
        Fruits fruit = Fruits.findByName(name);
        if (fruit == null) return new Result(false, name + " doesn't exist");
        String result = "Name: " + fruit.getName() + "\n" +
                "Base Sell Price: " + fruit.getBaseSellPrice() + "\n" +
                "Is Edible: " + fruit.isEdible() + "\n" +
                "Base Energy: " + fruit.getEnergy() + "\n" +
                "Season: " + fruit.getHarvestSeasons() + "\n";
        return new Result(true, result);
    }

    private Result deleteFromBackpack(Matcher matcher) {
        String name = matcher.group("itemname").trim();
        int amount = -1;
        if (matcher.group("number") != null) {
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
        if (tool == null) {
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
        if (x < 0 || x >= 55 || y < 0 || y >= 35) {
            return new Result(false, "It is out of range.");
        }
        Game.getTimeAndDate().thunder(new Point(x, y), Game.getActivePlayer().getFarm().getMainMap());
        return new Result(true, "Thunder...!");
    }

    private Result dateCheating(Matcher matcher) {
        int number = Integer.parseInt(matcher.group("number").trim());
        if (number <= 0) {
            return new Result(false, "Please enter positive number");
        }
        Game.getTimeAndDate().addDay(number);
        return showDate();
    }

    private Result timeCheating(Matcher matcher) {
        int number = Integer.parseInt(matcher.group("number").trim());
        if (number <= 0) {
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

    private Result sellProduct(Matcher matcher) {
        String productName = matcher.group("productName");
        int amount = -1;
        if (matcher.group("count") != null) {
            amount = Integer.parseInt(matcher.group("count").trim());
        }
        return Game.getShoppingBin().work(productName, amount);
    }

    private Result sendMessage(Matcher matcher) {
        String username = matcher.group("username");
        String message = matcher.group("message");
        Player player1 = Game.getActivePlayer();
        Player player2 = Game.findPlayerByUsername(username);
        if (player2 == null) return new Result(false, "The player not found");
        Friendship friendship1 = player1.friendshipWithPlayer(username);
        Friendship friendship2 = player2.friendshipWithPlayer(player1.getUsername());
        if (!isNextToPlayer(player2)) return new Result(false, "You aren't next to the " + username);
        player1.addSMS(new SMS(message, true, player1.getUsername(), player2.getUsername(), false));
        player2.addSMS(new SMS(message, false, player1.getUsername(), player2.getUsername(), false));
        friendship1.addFriendshipLevel(20);
        friendship2.addFriendshipLevel(20);
        return new Result(true, "Message successfully sent.");
    }

    public boolean isNextToPlayer(Player player) {
        for (Direction dir : Direction.values()) {
            Point tilePoint = dir.apply(Game.getActivePlayer().getPlace());
            if (tilePoint.x < 0 || tilePoint.y < 0 ||
                    tilePoint.x >= 140 || tilePoint.y >= 100) {
                continue;
            }
            Tile tile = Game.getMainMap().getMainMap()[tilePoint.x][tilePoint.y];
            if (tile != null && player.getPlace().equals(tilePoint)) {
                return true;
            }
        }
        return false;
    }

    private Result showTalkHistory(Matcher matcher) {
        String username = matcher.group("username");
        return new Result(true, Game.getActivePlayer().showConversationWith(username));
    }

    private Result sendGift(Matcher matcher) {
        String username = matcher.group("username");
        String item = matcher.group("item");
        int amount = Integer.parseInt(matcher.group("amount"));
        Player player1 = Game.getActivePlayer();
        Player player2 = Game.findPlayerByUsername(username);
        if (player2 == null) return new Result(false, "The player not found");
        if (!isNextToPlayer(player2)) return new Result(false, "You aren't next to the " + username);
        if (player1.friendshipWithPlayer(player2.getUsername()).getFriendshipLevel() < 1) {
            return new Result(false, "Your friendship is not enough to give gifts.");
        }
        Material material = player1.getInventory().isExistInBackpackOrNull(item);
        Result result = player1.getInventory().removeElementFromBackpack(material, amount);
        if (!result.isSuccessful()) return result;
        Result result1 = player2.getInventory().addElementToBackpack(material, amount);
        if (!result1.isSuccessful()) {
            player1.getInventory().addElementToBackpack(material, amount);
            return new Result(false, "You can't gift to " + username);
        }
        Gift gift = new Gift(player1.getUsername(), player2.getUsername(), material, amount);
        player1.addGift(gift);
        player2.addGift(gift);
        return new Result(true, "Your gift successfully is gave to " + username);
    }

    private Result listGifts(Matcher matcher) {
        return new Result(true, Game.getActivePlayer().showUnratedGifts());
    }

    private Result rateGift(Matcher matcher) {
        int giftNumber = Integer.parseInt(matcher.group("giftNumber"));
        int rate = Integer.parseInt(matcher.group("rate"));
        if (rate < 1 || rate > 5) return new Result(false, "Please rate between 1-5");
        Player player = Game.getActivePlayer();
        Gift gift = player.getGiftOrNull(giftNumber);
        if (gift == null) return new Result(false, "Gift not found with this number");
        Player player2 = Game.findPlayerByUsername(gift.getSender());
        assert player2 != null;
        int friendXP = ((rate - 3) * 30) + 15;
        player.friendshipWithPlayer(player2.getUsername()).addFriendshipLevel(friendXP);
        player2.friendshipWithPlayer(player.getUsername()).addFriendshipLevel(friendXP);
        gift.setRate(rate);
        gift.setId(-1);
        return new Result(true, "The gift successfully has been rated");
    }

    private Result showGiftHistory(Matcher matcher) {
        String username = matcher.group("username");
        return new Result(true, Game.getActivePlayer().showGiftsWith(username));
    }

    private Result sendHug(Matcher matcher) {
        String username = matcher.group("username");
        Player player1 = Game.getActivePlayer();
        Player player2 = Game.findPlayerByUsername(username);
        if (player2 == null) return new Result(false, "Player not found");
        if (!isNextToPlayer(player2)) return new Result(false, "You aren't next to the " + username);
        if (player1.friendshipWithPlayer(player2.getUsername()).getFriendshipLevel() < 2) {
            return new Result(false, "Your friendship is not enough to hug each other.");
        }
        player1.friendshipWithPlayer(player2.getUsername()).addFriendshipLevel(60);
        player2.friendshipWithPlayer(player1.getUsername()).addFriendshipLevel(60);
        return new Result(true, "You hug each other!");
    }

    private Result showFriendships(Matcher matcher) {
        Player player = Game.getActivePlayer();
        StringBuilder result = new StringBuilder();
        result.append("Show all friendships:");
        for (Friendship friendship : player.getFriendships()){
            result.
                    append("\n").
                    append(friendship.getFriend().
                            getUsername()).
                    append(" --→ ").
                    append(friendship.getFriendshipLevel()).append("\n------");
        }
        return new Result(true, result.toString());
    }

    private Result sendFlower(Matcher matcher) {
        String username = matcher.group("username");
        Player player1 = Game.getActivePlayer();
        Player player2 = Game.findPlayerByUsername(username);
        if (player2 == null) return new Result(false, "Player not found");
        if (!isNextToPlayer(player2)) return new Result(false, "You aren't next to the " + username);
        Friendship friendship1 = player1.friendshipWithPlayer(username);
        if (friendship1.getFriendshipLevel() < 2 || friendship1.getFriendshipUnit() < 300)
            return new Result(false, "Your friendship isn't enough to send Flower");

        if (friendship1.hasFlower())
            return new Result(false, "You have already sent a Flower to this player.");

        Material material = player1.getInventory().isExistInBackpackOrNull("Bouquet");
        if (material == null) return new Result(false, "You haven't any Bouquet in your backpack");
        player1.getInventory().removeElementFromBackpack(material, 1);
        Result result1 = player2.getInventory().addElementToBackpack(material, 1);
        if (!result1.isSuccessful()) {
            player1.getInventory().addElementToBackpack(material, 1);
            return new Result(false, "You can't sent flower to " + username);
        }
        friendship1.setFlower(true);
        player2.friendshipWithPlayer(player1.getUsername()).setFlower(true);
        friendship1.addFriendshipLevel(1);
        player2.friendshipWithPlayer(player1.getUsername()).addFriendshipLevel(1);
        return new Result(true, "Your flower is successfully sent to " + username);

    }

    private Result askForMarriage(Matcher matcher) {
        String username = matcher.group("username");
        String ring = matcher.group("ring");
        Player player1 = Game.getActivePlayer();
        Player player2 = Game.findPlayerByUsername(username);
        if (player2 == null) return new Result(false, "Player not found");
        if (!isNextToPlayer(player2)) return new Result(false, "You aren't next to the " + username);
        if (player1.getGender() == player2.getGender())
            return new Result(false, "You can't request to same gender");
        Friendship friendship1 = player1.friendshipWithPlayer(player2.getUsername());
        if (friendship1.getFriendshipLevel() < 3)
            return new Result(false, "Your friendship isn't enough to send Marriage request");

        Material material = player1.getInventory().isExistInBackpackOrNull("Ring");
        if (material == null) return new Result(false, "You haven't any Ring in your backpack");
        return new Result(true, "Your Marriage request is successfully sent to " + username);
    }

    private Result respondToMarriage(Matcher matcher) {
//        String username = matcher.group("username");
//        String respond = matcher.group("respond");
//        Player player = Game.getActivePlayer();
//        for ()
    }

    // uncompleted

    private Result showAllAvailableProducts(Matcher matcher) {
        return new Result(true, "Showing all available products.");
    }

    private Result showAllProducts(Matcher matcher) {
        return new Result(true, "Showing all products.");
    }

    private Result purchaseProduct(Matcher matcher) {
        String productName = matcher.group("productName");
        String count = matcher.group("count");
        return new Result(true, "Purchased " + count + " of " + productName);
    }

    private Result addDollars(Matcher matcher) {
        String count = matcher.group("count");
        return new Result(true, "Added " + count + " dollars to account.");
    }

    private Result startTrade(Matcher matcher) {
        return new Result(true, "Trade started.");
    }

    private Result processTrade(Matcher matcher) {
        String username = matcher.group("username");
        String type = matcher.group("type");
        String item = matcher.group("item");
        String amount = matcher.group("amount");
        String price = matcher.group("price");
        String targetItem = matcher.group("targetItem");
        String targetAmount = matcher.group("targetAmount");
        return new Result(true, String.format("Processed trade with %s: %s %sx%s for %s %sx%s",
                username, type, amount, item, price, targetAmount, targetItem));
    }

    private Result listTrades(Matcher matcher) {
        return new Result(true, "Listing all trades.");
    }

    private Result respondToTrade(Matcher matcher) {
        String id = matcher.group("id");
        String respond = matcher.group("respond");
        return new Result(true, "Responded to trade #" + id + ": " + respond);
    }

    private Result showTradeHistory(Matcher matcher) {
        return new Result(true, "Showing trade history.");
    }
}

