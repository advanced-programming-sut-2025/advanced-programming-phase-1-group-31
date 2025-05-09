package model.enums.commands;

public enum MainMenuCommands implements Command {
    MENU_ENTER("\\s*menu\\s+enter\\s+(?<menuName>\\S+)\\s*"),
    LOGOUT("^user\\s+logout\\s*$"),
    CHANGE_USERNAME("^change\\s+username\\s+-u\\s+(?<username>\\S+)\\s*$"),
    CHANGE_NICKNAME("^change\\s+nickname\\s+-u\\s+(?<nickname>\\S+)\\s*$"),
    CHANGE_EMAIL("^change\\s+email\\s+-e\\s+(?<email>\\S+)\\s*$"),
    CHANGE_PASSWORD("^change\\s+password\\s+-p\\s+(?<newPassword>\\S+)\\s+-o\\s+(?<oldPassword>\\S+)\\s*$"),
    SHOW_INFO("^user\\s+info\\s*$");


    private final String pattern;


    MainMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }
    
}