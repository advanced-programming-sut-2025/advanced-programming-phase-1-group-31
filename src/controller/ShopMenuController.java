package controller;

import model.Result;
import model.enums.commands.TradeMenuCommand;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenuController {
    private static final ShopMenuController instance = new ShopMenuController();

    public static ShopMenuController getInstance() {
        return instance;
    }

    public Result run(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;

        if ((matcher = TradeMenuCommand.SELECT_PRODUCTS_AVAILABLE.getMatcher(input)) != null) {
            return showAllAvailableProducts(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_PRODUCTS_ALL.getMatcher(input)) != null) {
            return showAllProducts(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_PURCHASE.getMatcher(input)) != null) {
            return purchaseProduct(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_ADD_DOLLARS.getMatcher(input)) != null) {
            return addDollars(matcher);
        } else if ((matcher = TradeMenuCommand.SELECT_SELL.getMatcher(input)) != null) {
            return sellProduct(matcher);
        } else {
            return new Result(false, "Invalid command!");
        }
    }

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

    private Result sellProduct(Matcher matcher) {
        String productName = matcher.group("productName");
        String count = matcher.group("count");
        return new Result(true, "Sold " + count + " of " + productName);
    }
}
