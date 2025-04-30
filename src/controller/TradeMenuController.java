package controller;

import model.Result;
import model.enums.TradeMenuCommand;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenuController {
    private static final TradeMenuController instance = new TradeMenuController();

    public static TradeMenuController getInstance() {
        return instance;
    }

    public Result run(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;

        if ((matcher = TradeMenuCommand.SELECT_FRIENDSHIPS.getMatcher(input)) != null) {
            return showFriendships(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_TALK.getMatcher(input)) != null) {
            return sendMessage(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_TALK_HISTORY.getMatcher(input)) != null) {
            return showTalkHistory(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_GIFT.getMatcher(input)) != null) {
            return sendGift(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_LIST_GIFT.getMatcher(input)) != null) {
            return listGifts(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_RATE_GIFT.getMatcher(input)) != null) {
            return rateGift(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_GIFT_HISTORY.getMatcher(input)) != null) {
            return showGiftHistory(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_HUG.getMatcher(input)) != null) {
            return sendHug(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_FLOWER.getMatcher(input)) != null) {
            return sendFlower(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_MARRIAGE_ASK.getMatcher(input)) != null) {
            return askForMarriage(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_MARRIAGE_RESPOND.getMatcher(input)) != null) {
            return respondToMarriage(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_TRADE_START.getMatcher(input)) != null) {
            return startTrade(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_TRADE.getMatcher(input)) != null) {
            return processTrade(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_LIST_TRADE.getMatcher(input)) != null) {
            return listTrades(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_TRADE_RESPONSE.getMatcher(input)) != null) {
            return respondToTrade(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_TRADE_HISTORY.getMatcher(input)) != null) {
            return showTradeHistory(matcher);
        } else {
            return new Result(false, "Invalid command!");
        }
    }

    private Result showFriendships(Matcher matcher) {
        return new Result(true, "Showing friendships.");
    }

    private Result sendMessage(Matcher matcher) {
        String username = matcher.group("username");
        String message = matcher.group("message");
        return new Result(true, "Sent message to " + username + ": " + message);
    }

    private Result showTalkHistory(Matcher matcher) {
        String username = matcher.group("username");
        return new Result(true, "Showing talk history with " + username);
    }

    private Result sendGift(Matcher matcher) {
        String username = matcher.group("username");
        String item = matcher.group("item");
        String amount = matcher.group("amount");
        return new Result(true, "Sent " + amount + " of " + item + " to " + username);
    }

    private Result listGifts(Matcher matcher) {
        return new Result(true, "Listing all gifts.");
    }

    private Result rateGift(Matcher matcher) {
        String giftNumber = matcher.group("giftNumber");
        String rate = matcher.group("rate");
        return new Result(true, "Rated gift #" + giftNumber + " with " + rate);
    }

    private Result showGiftHistory(Matcher matcher) {
        String username = matcher.group("username");
        return new Result(true, "Showing gift history with " + username);
    }

    private Result sendHug(Matcher matcher) {
        String username = matcher.group("username");
        return new Result(true, "Sent a hug to " + username);
    }

    private Result sendFlower(Matcher matcher) {
        String username = matcher.group("username");
        return new Result(true, "Sent a flower to " + username);
    }

    private Result askForMarriage(Matcher matcher) {
        String username = matcher.group("username");
        String ring = matcher.group("ring");
        return new Result(true, "Asked " + username + " to marry with a " + ring);
    }

    private Result respondToMarriage(Matcher matcher) {
        String username = matcher.group("username");
        String respond = matcher.group("respond");
        return new Result(true, "Marriage response to " + username + ": " + respond);
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
