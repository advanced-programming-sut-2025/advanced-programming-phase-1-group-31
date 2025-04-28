package controller;

import model.enums.TradeMenuCommand;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenuController {
    private static final TradeMenuController instance = new TradeMenuController();

    public static TradeMenuController getInstance() {
        return instance;
    }

    public void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;

             if ((matcher = TradeMenuCommand.SELECT_FRIENDSHIPS.getMatcher(input)) != null) {
                showFriendships();
            }
            else if ((matcher = TradeMenuCommand.SELECT_TALK.getMatcher(input)) != null) {
                sendMessage(matcher.group("username"), matcher.group("message"));
            }
            else if ((matcher = TradeMenuCommand.SELECT_TALK_HISTORY.getMatcher(input)) != null) {
                showTalkHistory(matcher.group("username"));
            }
            else if ((matcher = TradeMenuCommand.SELECT_GIFT.getMatcher(input)) != null) {
                sendGift(matcher.group("username"), matcher.group("item"), matcher.group("amount"));
            }
            else if ((matcher = TradeMenuCommand.SELECT_LIST_GIFT.getMatcher(input)) != null) {
                listGifts();
            }
            else if ((matcher = TradeMenuCommand.SELECT_RATE_GIFT.getMatcher(input)) != null) {
                rateGift(matcher.group("giftNumber"), matcher.group("rate"));
            }
            else if ((matcher = TradeMenuCommand.SELECT_GIFT_HISTORY.getMatcher(input)) != null) {
                showGiftHistory(matcher.group("username"));
            }
            else if ((matcher = TradeMenuCommand.SELECT_HUG.getMatcher(input)) != null) {
                sendHug(matcher.group("username"));
            }
            else if ((matcher = TradeMenuCommand.SELECT_FLOWER.getMatcher(input)) != null) {
                sendFlower(matcher.group("username"));
            }
            else if ((matcher = TradeMenuCommand.SELECT_MARRIAGE_ASK.getMatcher(input)) != null) {
                askForMarriage(matcher.group("username"), matcher.group("ring"));
            }
            else if ((matcher = TradeMenuCommand.SELECT_MARRIAGE_RESPOND.getMatcher(input)) != null) {
                respondToMarriage(matcher.group("username"), matcher.group("respond"));
            }
            else if ((matcher = TradeMenuCommand.SELECT_TRADE_START.getMatcher(input)) != null) {
                startTrade();
            }
            else if ((matcher = TradeMenuCommand.SELECT_TRADE.getMatcher(input)) != null) {
                processTrade(
                        matcher.group("username"),
                        matcher.group("type"),
                        matcher.group("item"),
                        matcher.group("amount"),
                        matcher.group("price"),
                        matcher.group("targetItem"),
                        matcher.group("targetAmount")
                );
            }
            else if ((matcher = TradeMenuCommand.SELECT_LIST_TRADE.getMatcher(input)) != null) {
                listTrades();
            }
            else if ((matcher = TradeMenuCommand.SELECT_TRADE_RESPONSE.getMatcher(input)) != null) {
                respondToTrade(matcher.group("id"), matcher.group("respond"));
            }
            else if ((matcher = TradeMenuCommand.SELECT_TRADE_HISTORY.getMatcher(input)) != null) {
                showTradeHistory();
            }
            else {
                System.out.println("Invalid command!");
            }
        }
    }

    // Implement all the methods called above

    private void showFriendships() {
        // Implementation
    }

    private void sendMessage(String username, String message) {
        // Implementation
    }

    private void showTalkHistory(String username) {
        // Implementation
    }

    private void sendGift(String username, String item, String amount) {
        // Implementation
    }

    private void listGifts() {
        // Implementation
    }

    private void rateGift(String giftNumber, String rate) {
        // Implementation
    }

    private void showGiftHistory(String username) {
        // Implementation
    }

    private void sendHug(String username) {
        // Implementation
    }

    private void sendFlower(String username) {
        // Implementation
    }

    private void askForMarriage(String username, String ring) {
        // Implementation
    }

    private void respondToMarriage(String username, String respond) {
        // Implementation
    }

    private void startTrade() {
        // Implementation
    }

    private void processTrade(String username, String type, String item,
                              String amount, String price,
                              String targetItem, String targetAmount) {
        // Implementation
    }

    private void listTrades() {
        // Implementation
    }

    private void respondToTrade(String id, String respond) {
        // Implementation
    }

    private void showTradeHistory() {
        // Implementation
    }
}