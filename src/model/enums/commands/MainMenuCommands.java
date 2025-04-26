package model.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
    LOGOUT("^user\\s+logout\\s*$"),
    CHANGE_USERNAME("^change\\s+username\\s+-u\\s+(?<username>\\S+)\\s*$"),
    CHANGE_NICKNAME("^change\\s+nickname\\s+-u\\s+(?<nickname>\\S+)\\s*$"),
    CHANGE_EMAIL("^change\\s+email\\s+-e\\s+(?<email>\\S+)\\s*$"),
    CHANGE_PASSWORD("^change\\s+password\\s+-p\\s+(?<newPassword>\\S+)\\s+-o\\s+(?<oldPassword>\\S+)\\s*$"),
    SHOW_INFO("^user\\s+info\\s*$");


    private final Pattern pattern;
    
    MainMenuCommands(String pattern){this.pattern = Pattern.compile(pattern);}

    public Matcher getMatcher(String input){
        return pattern.matcher(input);
    } 
}
