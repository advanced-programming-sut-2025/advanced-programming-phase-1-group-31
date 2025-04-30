package model.enums;

public enum ShopMenuCommand implements Command {
    SELECT_PRODUCTS_AVAILABLE("\\s*show\\s+all\\s+available\\s+products\\s*"),
    SELECT_PRODUCTS_ALL("\\s*show\\s+all\\s+products\\s*"),
    SELECT_PURCHASE("\\s*purchase\\s+(?<productName>\\S+)\\s+-n\\s+(?<count>\\S+)\\s*"),
    SELECT_ADD_DOLLARS("\\s*cheat\\s+add\\s+(?<count>\\S+)\\s+dollars\\s+"),
    SELECT_SELL("\\s*sell\\s+(?<productName>\\S+)\\s+-n\\s+(?<count>\\S+)\\s*");

    private final String pattern;

    ShopMenuCommand(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }

}