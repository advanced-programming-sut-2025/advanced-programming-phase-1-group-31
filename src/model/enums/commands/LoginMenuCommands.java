package model.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    EXIT_MENU("^menu\\s+exit\\s*$"),
    SHOW_CURRENT_MENU("^show\\s+current\\s+menu\\s*$"),
    REGISTER("^register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?:(?<flag>-random)|(?<password>\\S+)\\s+(?<passwordConfirmation>\\S+))\\s+-n\\s+(?<nickname>\\S+)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<gender>\\S+)\\s*$"),
    LOGIN("^login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s*(?<flag>\\s+-stay-logged-in)?\\s*$"),
    PASSWORD_RECOVERY("^forget\\s+password\\s+-u\\s+(?<username>\\S+)\\s*$");

    private final Pattern pattern;

    LoginMenuCommands(String pattern){this.pattern = Pattern.compile(pattern);}

    public Matcher getMatcher(String input){
        return pattern.matcher(input);
    } 
}
