package model.enums.commands;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public enum MainMenuCommands implements Command{
    LOGOUT("^user\\s+logout\\s*$"),
    SHOW_CURRENT_MENU("show\\s+current\\s+menu\\s*"),//fix this
    CHANGE_MENU("^change\\s+menu\\s+to\\s+(?<menuName>\\S+)\\s*$");

    private final String pattern;

    MainMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }
}
