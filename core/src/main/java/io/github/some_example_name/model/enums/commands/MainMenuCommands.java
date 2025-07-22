package io.github.some_example_name.model.enums.commands;

public enum MainMenuCommands implements Command{
    MENU_ENTER("\\s*menu\\s+enter\\s+(?<menuName>\\S+)\\s*"),
    LOGOUT("^user\\s+logout\\s*$"),
    SHOW_CURRENT_MENU("show\\s+current\\s+menu\\s*");
    private final String pattern;

    MainMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }
}
