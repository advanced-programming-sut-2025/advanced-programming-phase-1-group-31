package io.github.some_example_name.model.enums.commands;

public enum HouseMenuCommand implements Command {
    COOKING_REF_PUT_PICK("\\s*cooking\\s+refrigerator\\s+(?<action>(put|pick))\\s+(?<item>\\S+)\\s*"),
    COOKING_SHOW_RECIPES("\\s*cooking\\s+show\\s+recipes\\s*"),
    COOKING_PREPARE("\\s*cooking\\s+prepare\\s+(?<recipe>\\S+\\s?\\S+)\\s*"),
    EAT_FOOD("\\s*eat\\s+(?<food>\\S+\\s?\\S+)\\s*"),
    CRAFTING_SHOW_RECIPES("\\s*crafting\\s+show\\s+recipes\\s*"),
    CRAFTING_CRAFT("\\s*crafting\\s+craft\\s+(?<item>\\S+\\s?\\S+)\\s*"),
    PLACE_ITEM("\\s*place\\s+item\\s+-n\\s+(?<item>\\S+\\s?\\S+)\\s+-d\\s+(?<direction>\\S+)\\s*"),
    CHEAT_ADD_ITEM("\\s*cheat\\s+add\\s+item\\s+-n\\s+(?<name>\\S+\\s?\\S+)\\s+-c\\s+(?<count>\\d+)\\s*"),
    EXIT_HOUSE("\\s*exit\\s+house\\s*"),
    CHEAT_ADD_COOKING_RECIPE("\\s*cheat\\s+add\\s+cooking\\s+(?<recipe>\\S+\\s?\\S+)\\s*"),
    CHEAT_ADD_CRAFTING_RECIPE("\\s*cheat\\s+add\\s+crafting\\s+(?<recipe>\\S+\\s?\\S+)\\s*");

    private final String pattern;

    HouseMenuCommand(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }
}
