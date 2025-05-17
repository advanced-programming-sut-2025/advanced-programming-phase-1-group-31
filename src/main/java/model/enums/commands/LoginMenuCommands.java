package model.enums.commands;


public enum LoginMenuCommands implements Command{
    EXIT_MENU("^menu\\s+exit\\s*$"),
    SHOW_CURRENT_MENU("^show\\s+current\\s+menu\\s*$"),
    REGISTER("^register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?:(?<flag>-random)|(?<password>\\S+)\\s+(?<passwordConfirmation>\\S+))\\s+-n\\s+(?<nickname>\\S+)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<gender>\\S+)\\s*$"),
    LOGIN("^login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s*(?<flag>\\s+-stay-logged-in)?\\s*$"),
    PASSWORD_RECOVERY("^forget\\s+password\\s+-u\\s+(?<username>\\S+)\\s*$");



    private final String pattern;

    LoginMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }
}