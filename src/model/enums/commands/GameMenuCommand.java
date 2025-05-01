package model.enums.commands;

import java.util.regex.Pattern;

public enum GameMenuCommand implements Command {
    GAME_NEW_PATTERN(
    "^\\s*game\\s+new\\s+-u\\s+(?<usernames>(\\w+\\s*){1,3})\\s*$"
);


    private final String pattern;

    GameMenuCommand(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }
}
