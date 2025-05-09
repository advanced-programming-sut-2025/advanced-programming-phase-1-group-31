package model.enums.commands;

import java.util.regex.Pattern;

public enum GameMenuCommand implements Command {
    GAME_NEW_PATTERN(
            "^\\s*game\\s+new\\s+-u\\s+(?<usernames>(\\w+\\s*){1,3})\\s*$"),
    MAP_SELECT_PATTERN("^game\\s+map\\s+(?<number>\\d+)$"),
    // CREATE_NEW_GAME(""),
    // CHOOSE_MAP("^game\\s+map\\s+(?<mapId>\\d+)\\s*$"),
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
    CHEAT_CREATE_THUNDER("^cheat\\s+Thor\\s+-l(?<X>\\d+)\\s+(?<Y>\\d+)\\s*$"),
    SHOW_WEATHER("^weather\\s*$"),
    FORECAST_WEATHER("^weather\\s+forecast\\s*$"),
    CHEAT_CHANGE_WEATHER("^cheat\\s+set\\s+weather\\s+(?<weather>\\S+)$"),
    BUILD_GREENHOUSE("^greenhouse\\s+build$"),
    CHOOSE_FARM("^choose\\s+farm\\s+-u(?<username>\\S+)\\s+-f(?<farmId>\\d+)\\s*$"),
    WALK("^walk\\s+-l(?<X>\\d+)\\s+(?<Y>\\d+)\\s*$"),
    PRINT_MAP("\\s*print\\s+map\\s+-l\\s+(?<X>\\d+)\\s+(?<Y>\\d+)\\s+-s\\s+(?<size>\\d+)\\s*$"),
    SHOW_LEGEND("^help\\s+reading\\s+map\\s*$"),
    SHOW_ENERGY("^energy\\s+show\\s*$"),
    CHEAT_CHANGE_ENERGY("^energy\\s+set\\-v\\s+(?<value>\\d+)\\s*$"),
    UNLIMITED_ENERGY("^energy\\s+unlimited\\s*$"),
    SHOW_INVENTORY("^inventory\\s+show\\s*$"),
    DISCARD_ITEM("^inventory\\s+trash\\s+-i\\s+(<itemname>\\S+)(?:\\s+-n\\s+(?<number>\\d+))?\\s*$"),
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
    GIFT("^gift\\s+-u\\s+(?<username>\\S+)\\s+-i\\s+(?<item>\\S+)\\s+-a\\s+(?<amount>\\d+)\\s*$"),
    SHOW_GIFTS("^gift\\s+list\\s*$"),
    RATE_GIFT("^gift\\s+rate\\s+-i\\s+(?<giftNumber>\\d+)\\s+-r\\s+(?<rate>\\d)\\s*$"),
    SHOW_GIFT_HISTORY("^gift\\s+history\\s+-u\\s+(?<username>\\S+)\\s*$"),
    HUG("^hug\\s+-u\\s+(?<username>\\S+)\\s*$"),
    GIVE_FLOWER("^flower\\s+-u\\s+(?<username>\\S+)\\s*$"),
    ASK_MARRIAGE("^ask\\s+marriage\\s+-u\\s+(?<username>\\S+)\\s+-r\\s+(?<ring>\\S+)\\s*$"),
    RESPOND_TO_MARRIAGE("^respond\\s+(?<response>-accept||-reject)\\s+-u\\s+(?<username>\\S+)\\s*$"),
    MEET_NPC("^meet\\s+NPC\\s+(?<npcName>\\S+)\\s*$"),
    GIFT_NPC("^gift\\s+NPC\\s+(?<npcName>\\S+)\\s+-i\\s+(?<item>\\S+)\\s*$"),
    LIST_NPC_FRIENDSHIPS("^friendship\\s+NPC\\s+list\\s*$"),
    LIST_QUESTS("^quests\\s+list\\s*$"),
    COMPLETE_QUEST("^quests\\s+finish\\s+-i\\s+(?<index>\\d+)\\s*$");

    private final String pattern;

    GameMenuCommand(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }
}
