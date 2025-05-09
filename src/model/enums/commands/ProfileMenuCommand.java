package model.enums.commands;

public enum ProfileMenuCommand implements Command {
    CHANGE_USERNAME("\\s*change\\s+username\\s+-u\\s+(?<username>\\S+)\\s*"),
    CHANGE_NICKNAME("\\s*change\\s+nickname\\s+-u\\s+(?<nickname>\\S+)\\s*"),
    CHANGE_EMAIL("\\s*change\\s+email\\s+-e\\s+(?<email>\\S+)\\s*"),
    CHANGE_PASSWORD(
            "\\s*change\\s+password\\s+" +
                    "-p\\s+(?<newPassword>\\S+)\\s+" +
                    "-o\\s+(?<oldPassword>\\S+)\\s*"),
    SHOW_USER_INFO("\\s*user\\s+info\\s*");

    private final String pattern;

    ProfileMenuCommand(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }
}