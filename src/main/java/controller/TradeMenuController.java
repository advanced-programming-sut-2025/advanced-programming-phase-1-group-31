package controller;

import model.*;
import model.enums.commands.TradeMenuCommand;
import model.enums.general.Menus;
import model.materials.Material;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenuController {

    public Result run(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;

        if ((matcher = TradeMenuCommand.SELECT_TRADE.getMatcher(input)) != null) {
            return processTrade(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_TRADE_RESPONSE.getMatcher(input)) != null) {
            return respondToTrade(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_TRADE_HISTORY.getMatcher(input)) != null) {
            return showTradeHistory(matcher);
        } else if ((matcher = TradeMenuCommand.Exit_From_TradeManu.getMatcher(input)) != null) {
            return exitTradeMenu(matcher);
        } else {
            return new Result(false, "Invalid command!");
        }
    }

    private Result exitTradeMenu(Matcher matcher) {
        App.setCurrentMenu(Menus.GameMenu);
        return new Result(true, "You are now in the Game Menu.");
    }

    private Result processTrade(Matcher matcher) {
        String username = matcher.group("username");
        String type = matcher.group("type");
        boolean isOffer = type.equalsIgnoreCase("offer");
        String item = matcher.group("item");
        int amount = Integer.parseInt(matcher.group("amount"));

        Player player1 = App.getCurrentGame().getActivePlayer();
        Player player2 = App.getCurrentGame().findPlayerByUsername(username);
        if (player2 == null) return new Result(false, "The player does not exist");

        Material material = player1.getInventory().isExistInBackpackOrNull(item);
        if (material == null) return new Result(false, "You don't have this item in your backpack");
        if (player1.getInventory().howManyInBackpack(material) < amount)
            return new Result(false, "You don't have enough of this item in your backpack");

        if (isOffer) {
            int price = Integer.parseInt(matcher.group("price"));
            Trade trade = new Trade(player1, player2, material, amount, price);
            player1.getTradeHistory().add(trade);
            player2.getTradeHistory().add(trade);
            return new Result(true, "Your offer was successfully sent to " + player2.getUsername());
        } else {
            String targetItem = matcher.group("targetItem");
            int targetAmount = Integer.parseInt(matcher.group("targetAmount"));
            Material targetMaterial = player1.getInventory().isExistInBackpackOrNull(targetItem);

            if (targetMaterial == null) return new Result(false, "You don't have the requested item in your backpack");
            if (player1.getInventory().howManyInBackpack(material) < amount)
                return new Result(false, "You don't have enough of this item in your backpack");

            Trade trade = new Trade(player1, player2, material, amount, targetMaterial, targetAmount);
            player1.getTradeHistory().add(trade);
            player2.getTradeHistory().add(trade);
            return new Result(true, "Your trade request was successfully sent to " + player2.getUsername());
        }
    }

    private Result respondToTrade(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        String respond = matcher.group("respond");

        boolean isAccept = respond.equalsIgnoreCase("accept");
        Trade trade = getTradeByTempId(id);
        if (trade == null) return new Result(false, "You don't have any trade with that ID");

        Player player1 = trade.getSender();
        Player player2 = trade.getReceiver();

        if (!isAccept) {
            trade.setRead(true);
            trade.setAccepted(false);
            trade.setId(-1);
            Friendship friendship1 = friendshipWithPlayer(player1, player2.getUsername());
            Friendship friendship2 = friendshipWithPlayer(player2, player1.getUsername());
            friendship1.addFriendshipLevel(-30);
            friendship2.addFriendshipLevel(-30);
            return new Result(true, "Trade rejected successfully.");
        }

        if (trade.getPrice() == null) {
            Result removal = player2.getInventory().removeElementFromBackpack(trade.getMaterialToReceive(), trade.getAmountToReceive());
            if (!removal.Success()) return removal;

            Result result1 = player1.getInventory().addElementToBackpack(trade.getMaterialToReceive(), trade.getAmountToReceive());
            if (!result1.Success()) {
                trade.setRead(true);
                trade.setId(-1);
                player2.getInventory().addElementToBackpack(trade.getMaterialToReceive(), trade.getAmountToReceive());
                return new Result(false, player1.getUsername() + " can't receive the " + trade.getMaterialToReceive().getName());
            }

            Result result2 = player2.getInventory().addElementToBackpack(trade.getMaterialToSell(), trade.getAmountToSell());
            if (!result2.Success()) {
                player2.getInventory().addElementToBackpack(trade.getMaterialToReceive(), trade.getAmountToReceive());
                player1.getInventory().removeElementFromBackpack(trade.getMaterialToReceive(), trade.getAmountToReceive());
                return new Result(false, "You can't receive the " + trade.getMaterialToSell().getName());
            }

            trade.setRead(true);
            trade.setAccepted(true);
            trade.setId(-1);
            Friendship friendship1 = friendshipWithPlayer(player1, player2.getUsername());
            Friendship friendship2 = friendshipWithPlayer(player2, player1.getUsername());
            friendship1.addFriendshipLevel(50);
            friendship2.addFriendshipLevel(50);
            return new Result(true, "Trade accepted successfully.");
        }

        int price = trade.getPrice();
        if (price > player2.getMoney()) return new Result(false, "You don't have enough money");

        player2.deductMoney(price);
        Result giveItem = player2.getInventory().addElementToBackpack(trade.getMaterialToSell(), trade.getAmountToSell());
        if (!giveItem.Success()) {
            player2.addMoney(price);
            return new Result(false, "You can't receive the " + trade.getMaterialToSell().getName());
        }

        player1.addMoney(price);
        trade.setRead(true);
        trade.setAccepted(true);
        trade.setId(-1);
        return new Result(true, "Trade accepted successfully.");
    }

    private Trade getTradeByTempId(int id) {
        Player player = App.getCurrentGame().getActivePlayer();
        for (Trade trade : player.getTradeHistory()) {
            if (trade.getId() == id && trade.getReceiver().getUsername().equals(player.getUsername())) {
                return trade;
            }
        }
        return null;
    }

    private Result showTradeHistory(Matcher matcher) {
        Player player = App.getCurrentGame().getActivePlayer();
        StringBuilder result = new StringBuilder();
        boolean hasAny = false;

        result.append("Your Trades:\n");

        for (Trade trade : player.getTradeHistory()) {
            result.append(trade.getSender().getUsername()).append(" --> ").append(trade.getReceiver().getUsername()).append("\n")
                    .append(trade.getMaterialToSell().getName()).append(" x ").append(trade.getAmountToSell());

            if (trade.getPrice() == null) {
                result.append(" --> ").append(trade.getMaterialToReceive().getName()).append(" x ").append(trade.getAmountToReceive());
            } else {
                result.append(" --> ").append(trade.getPrice()).append(" gold");
            }

            result.append("\n--------\n");
            hasAny = true;
        }

        if (!hasAny) return new Result(false, "You have no trades.");

        return new Result(true, result.toString());
    }

    public Friendship friendshipWithPlayer(Player player, String name) {
        for (Friendship friendship : player.getFriendships()) {
            if (friendship.getFriend().getUsername().equals(name)) return friendship;
        }
        return null;
    }
}
