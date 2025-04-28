package controller;

import model.enums.TradeMenuCommand;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenuController {
    private static final ShopMenuController instance = new ShopMenuController();

    public static ShopMenuController getInstance() {
        return instance;
    }

    public void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;

            if ((matcher = TradeMenuCommand.SELECT_PRODUCTS_AVAILABLE.getMatcher(input)) != null) {
                showAllAvailableProducts();
            }
            else if ((matcher = TradeMenuCommand.SELECT_PRODUCTS_ALL.getMatcher(input)) != null) {
                showAllProducts();
            }
            else if ((matcher = TradeMenuCommand.SELECT_PURCHASE.getMatcher(input)) != null) {
                purchaseProduct(matcher.group("productName"), matcher.group("count"));
            }
            else if ((matcher = TradeMenuCommand.SELECT_ADD_DOLLARS.getMatcher(input)) != null) {
                addDollars(matcher.group("count"));
            }
            else if ((matcher = TradeMenuCommand.SELECT_SELL.getMatcher(input)) != null) {
                sellProduct(matcher.group("productName"), matcher.group("count"));
            }
            else {
                System.out.println("Invalid command!");
            }
        }
    }

    // Implement all the methods called above
    private void showAllAvailableProducts() {
        // Implementation
    }

    private void showAllProducts() {
        // Implementation
    }

    private void purchaseProduct(String productName, String count) {
        // Implementation
    }

    private void addDollars(String count) {
        // Implementation
    }

    private void sellProduct(String productName, String count) {
        // Implementation
    }

}
