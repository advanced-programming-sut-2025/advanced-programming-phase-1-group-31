package model.enums.commands;

public enum TradeMenuCommand implements Command {
    SELECT_TRADE_START(
            "^\\s*start\\s+trade\\s*$"
    ),

    SELECT_TRADE(
            "^\\s*trade\\s+-u\\s+(?<username>\\S+)\\s+-t\\s+"
                    + "(?<type>request|offer)"
                    + "(?:\\s+-i\\s+(?<item>\\S+)\\s+-a\\s+(?<amount>\\d+))?"
                    + "(?:\\s+-p\\s+(?<price>\\d+\\.?\\d*))?"
                    + "(?:\\s+-ti\\s+(?<targetItem>\\S+)\\s+-ta\\s+(?<targetAmount>\\d+))?"
                    + "\\s*$"
    ),

    SELECT_LIST_TRADE(
            "^\\s*trade\\s+list\\s*$"
    ),
    SELECT_TRADE_RESPONSE(
            "^\\s*trade\\s+respond\\s+--(?<respond>accept|reject)"
                    + "\\s+-i\\s+(?<id>\\d+)\\s*$"
    ),

    SELECT_TRADE_HISTORY(
            "^\\s*trade\\s+history\\s*$"
    ),

    Exit_From_TradeManu("^\\s*exit\\s+trade\\s*$");

    private final String pattern;

    TradeMenuCommand(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }

}