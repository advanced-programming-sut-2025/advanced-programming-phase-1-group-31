package model.enums.commands;

public enum GameMenuCommand implements Command {
    GAME_NEW_PATTERN("^\\s*game\\s+new\\s+-u\\s+(?<usernames>(\\w+\\s*){1,3})\\s*$"),
    MAP_SELECT_PATTERN("^game\\s+map\\s+(?<number>\\d+)$"),
    CREATE_NEW_GAME(""),
    CHOOSE_MAP("^game\\s+map\\s+(?<mapId>\\d+)\\s*$"),
    LOAD_GAME("^load\\s+game\\s*$"),
    EXIT_GAME("^exit\\s+game\\s*$"),
    TERMINATE_GAME("^terminate\\s+game\\s*$"),
    CHANGE_TURN("^next\\s+turn\\s*$"),
    SHOW_TIME("^time\\s*$"),
    SHOW_DATE("^date\\s*$"),
    SHOW_DATE_AND_TIME("^datetime\\s*$"),
    SHOW_WEEKDAY("^day\\s+of\\s+the\\s+week\\s*$"),
    CHEAT_ADVANCE_TIME("^cheat\\s+advance\\s+time\\s+(?<number>\\d+)h\\s*$"),
    CHEAT_ADVANCE_DATE("^cheat\\s+advance\\s+date\\s+(?<number>\\d+)d\\s*$"),
    SHOW_SEASON("^season\\s*$"),
    CHEAT_CREATE_THUNDER("^cheat\\s+Thor\\s+-l(?<X>\\d+)\\s+(?<X>\\d+)\\s*$"),
    SHOW_WEATHER("^weather\\s*$"),
    FORECAST_WEATHER("^weather\\s+forecast\\s*$"),
    CHEAT_CHANGE_WEATHER("^cheat\\s+set\\s+weather\\s+(?<weather>\\S+)$"),
    BUILD_GREENHOUSE("^greenhouse\\s+build$"),
    CHOOSE_FARM("^choose\\s+farm\\s+-u(?<username>\\S+)\\s+-f(?<farmId>\\d+)\\s*$"),
    WALK("^walk\\s+-l(?<X>\\d+)\\s+(?<Y>\\d+)\\s*$"),
    PRINT_MAP("^print\\s+map\\s+-l\\s+(?<X>\\d+)\\s+(?<Y>\\d+)\\s+-s\\s+(?<size>\\d+)\\s*$"),
    SHOW_LEGEND("^help\\s+reading\\s+map\\s*$"),
    SHOW_ENERGY("^energy\\s+show\\s*$"),
    CHEAT_CHANGE_ENERGY("^energy\\s+set\\-v\\s+(?<value>\\d+)\\s*$"),
    UNLIMITED_ENERGY("^energy\\s+unlimited\\s*$"),
    SHOW_INVENTORY("^inventory\\s+show\\s*$"),
    DISCARD_ITEM("^inventory\\s+trash\\s+-i\\s+(?<itemname>\\S+)(?:\\s+-n\\s+(?<number>\\d+))?\\s*$"),
    EQUIP_TOOL("^tools\\s+equip\\s+(?<toolname>\\S+)\\s*$"),
    SHOW_CURRENT_TOOL("^tools\\s+show\\s+current\\s*$"),
    SHOW_AVAILABLE_TOOLS("^tools\\s+show\\s+available\\s*$"),
    UPGRADE_TOOL("^tools\\s+upgrade\\s+(?<toolname>\\S+)\\s*$"),
    USE_TOOL("^tools\\s+use\\s+-d(?<direction>\\d)\\s*$"),
    SHOW_CRAFT_INFO("^craftinfo\\s+-n\\s+(<craft-name>\\S+)\\s*$"),
    PLANT_SEED("^plant\\s+-s\\s+(?<seed>\\S+)\\s+-d\\s+(?<direction>\\d)\\s*$"),
    SHOW_PLANT("^showplant\\s+-l\\s+(?<X>\\d+)\\s+(?<Y>\\d+)\\s*$"),
    FERTILIZE("^fertilize\\s+-f\\s+(?<fertilizer>\\S+)\\s+(?<direction>\\d)\\s*$"),
    SHOW_WATER_LEFT("^how\\s*much\\s+water\\s*$"),
    FISH("^fishing\\s+-p\\s+(?<fishingPole>\\S+)\\s*$"),
    USE_ARTISAN("^artisan\\s+use\\s+(?<artisan>\\S+)\\s+(?<item>\\S+)\\s*$"),
    GET_ARTISAN("^artisan\\s+get\\s+(?<artisan>\\S+)\\s*$"),
    TALK_WITH_FRIENDS("^talk\\s+-u\\s+(?<username>\\S+)\\s+-m\\s+(?<message>\\S+)\\s*$"),
    SHOW_TALK_HISTORY("^talk\\s+history\\s+-u\\s+(?<username>\\S+)\\s*$"),
    GIFT("^Gift\\s+-u\\s+(?<username>\\S+)\\s+-i\\s+(?<item>\\S+)\\s+-a\\s+(?<amount>\\d+)\\s*$"),
    SHOW_GIFTS("^Gift\\s+list\\s*$"),
    RATE_GIFT("^Gift\\s+rate\\s+-i\\s+(?<giftNumber>\\d+)\\s+-r\\s+(?<rate>\\d)\\s*$"),
    SHOW_GIFT_HISTORY("^Gift\\s+history\\s+-u\\s+(?<username>\\S+)\\s*$"),
    HUG("^hug\\s+-u\\s+(?<username>\\S+)\\s*$"),
    GIVE_FLOWER("^flower\\s+-u\\s+(?<username>\\S+)\\s*$"),
    ASK_MARRIAGE("^ask\\s+marriage\\s+-u\\s+(?<username>\\S+)\\s+-r\\s+(?<ring>\\S+)\\s*$"),
    RESPOND_TO_MARRIAGE("^respond\\s+(?<response>-accept||-reject)\\s+-u\\s+(?<username>\\S+)\\s*$"),
    MEET_NPC("^meet\\s+NPC\\s+(?<npcName>\\S+)\\s*$"),
    GIFT_NPC("^Gift\\s+NPC\\s+(?<npcName>\\S+)\\s+-i\\s+(?<item>\\S+)\\s*$"),
    LIST_NPC_FRIENDSHIPS("^friendship\\s+NPC\\s+list\\s*$"),
    LIST_QUESTS("^quests\\s+list\\s*$"),
    TREE_INFO("^\\s*tree\\s+info\\s+(?<name>\\S+)\\s*$"),
    CROP_INFO("^\\s*crop\\s+info\\s+(?<name>\\S+)\\s*$"),
    FORAGING_CROP_INFO("^\\s*foraging\\s+crop\\s+info\\s+(?<name>\\S+)\\s*$"),
    FORAGING_TREE_INFO("^\\s*foraging\\s+tree\\s+info\\s+(?<name>\\S+)\\s*$"),
    FRUITS_INFO("^\\s*fruit\\s+info\\s+(?<name>\\S+)\\s*$"),
    COMPLETE_QUEST("^quests\\s+finish\\s+-i\\s+(?<index>\\d+)\\s*$"),
    SELECT_PRODUCTS_AVAILABLE("\\s*show\\s+all\\s+available\\s+products\\s*"),
    SELECT_PRODUCTS_ALL("\\s*show\\s+all\\s+products\\s*"),
    SELECT_PURCHASE("\\s*purchase\\s+(?<productName>\\S+)\\s+-n\\s+(?<count>\\S+)\\s*"),
    SELECT_ADD_DOLLARS("\\s*cheat\\s+add\\s+(?<count>\\S+)\\s+dollars\\s+"),
    SELECT_FRIENDSHIPS("\\s*friendships\\s*"),
    SELECT_TALK("\\s*talk\\s+-u\\s+(?<username>\\S+)\\s+-m\\s+(?<message>\\.+)\\s*"),
    SELECT_TALK_HISTORY("\\s*talk\\s+history\\s+-u\\s+(?<username>\\S+)\\s*"),
    SELECT_GIFT("\\s*Gift\\s+-u\\s+(?<username>\\S+)\\s+-i\\s+(?<item>\\S+)\\s+-a\\s+(?<amount>\\S+)\\s*"),
    SELECT_LIST_GIFT("\\s*Gift\\s+list\\s*"),
    SELECT_RATE_GIFT("\\s*Gift\\s+rate\\s+-i\\s+(?<giftNumber>\\S+)\\s+-r\\s+(?<rate>\\S+)\\s*"),
    SELECT_GIFT_HISTORY("\\s*Gift\\s+history\\s+-u\\s+(?<username>\\S+)\\s*"),
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
    SELECT_TRADE_HISTORY("\\s*trade\\s+history\\s*"),
    SELECT_SELL("\\s*sell\\s+(?<productName>\\S+)\\s+-n\\s+(?<count>\\S+)\\s*");


    private final String pattern;

    GameMenuCommand(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }
}
