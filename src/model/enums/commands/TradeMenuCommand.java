package model.enums.commands;

public enum TradeMenuCommand implements Command {
    SELECT_PRODUCTS_AVAILABLE("\\s*show\\s+all\\s+available\\s+products\\s*"),
    SELECT_PRODUCTS_ALL("\\s*show\\s+all\\s+products\\s*"),
    SELECT_PURCHASE("\\s*purchase\\s+(?<productName>\\S+)\\s+-n\\s+(?<count>\\S+)\\s*"),
    SELECT_ADD_DOLLARS("\\s*cheat\\s+add\\s+(?<count>\\S+)\\s+dollars\\s+"),
    SELECT_SELL("\\s*sell\\s+(?<productName>\\S+)\\s+-n\\s+(?<count>\\S+)\\s*"),
    SELECT_FRIENDSHIPS("\\s*friendships\\s*"),
    SELECT_TALK("\\s*talk\\s+-u\\s+(?<username>\\S+)\\s+-m\\s+(?<message>\\.+)\\s*"),
    SELECT_TALK_HISTORY("\\s*talk\\s+history\\s+-u\\s+(?<username>\\S+)\\s*"),
    SELECT_GIFT("\\s*gift\\s+-u\\s+(?<username>\\S+)\\s+-i\\s+(?<item>\\S+)\\s+-a\\s+(?<amount>\\S+)\\s*"),
    SELECT_LIST_GIFT("\\s*gift\\s+list\\s*"),
    SELECT_RATE_GIFT("\\s*gift\\s+rate\\s+-i\\s+(?<giftNumber>\\S+)\\s+-r\\s+(?<rate>\\S+)\\s*"),
    SELECT_GIFT_HISTORY("\\s*gift\\s+history\\s+-u\\s+(?<username>\\S+)\\s*"),
    SELECT_HUG("\\s*hug\\s+-u\\s+(?<username>\\S+)\\s*"),
    SELECT_FLOWER("\\s*flower\\s+-u\\s+(?<username>\\S+)\\s*"),
    SELECT_MARRIAGE_ASK("\\s*ask\\s+marriage\\s*-u\\s+(?<username>\\S+)\\s+-r\\s+(?<ring>\\S+)\\s*"),
    SELECT_MARRIAGE_RESPOND("\\s*respond\\s+(?<respond>(-accept|-reject))\\s+-u\\s+(?<username>\\S+)\\s*"),
    SELECT_TRADE_START("\\s*start\\s+trade\\s*"),
    SELECT_TRADE("\\s*trade\\s+" +
            "-u\\s+(?<username>\\S+)\\s+" +
            "-t\\s+(?<type>request|offer)\\s+" +
            "(?:-i\\s+(?<item>\\S+)\\s+-a\\s+(?<amount>\\d+)\\s+)?" +
            "(?:-p\\s+(?<price>\\d+\\.?\\d*)\\s+)?" +
            "(?:-ti\\s+(?<targetItem>\\S+)\\s+-ta\\s+(?<targetAmount>\\d+)\\s+)?\\s*"),
    SELECT_LIST_TRADE("\\s*trade\\s+list\\s*"),
    SELECT_TRADE_RESPONSE("\\s*trade\\s+respond\\s+(?<respond>(-accept|-reject))\\s+-i\\s+(?<id>\\S+)\\s*"),
    SELECT_TRADE_HISTORY("\\s*trade\\s+history\\s*");

    private final String pattern;


    TradeMenuCommand(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }

}