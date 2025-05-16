package controller;

import model.App;
import model.ChangeManager;
import model.Result;
import model.enums.commands.ProfileMenuCommand;
import model.enums.general.Menus;
import model.Player;
import java.util.*;
import java.util.regex.Matcher;

public class ProfileMenuController {
    public Result run(Scanner scanner){
        String line = scanner.nextLine();
        Matcher matcher;

        if(ProfileMenuCommand.SHOW_INFO.getMatcher(line).matches()){
            return showInfo();
        } else if((matcher = ProfileMenuCommand.CHANGE_USERNAME.getMatcher(line)) != null){
            return changeUsername(matcher);
        } else if((matcher = ProfileMenuCommand.CHANGE_EMAIL.getMatcher(line)) != null){
            return changeEmail(matcher);
        } else if((matcher = ProfileMenuCommand.CHANGE_NICKNAME.getMatcher(line)) != null){
            return changeNickname(matcher);
        } else if((matcher = ProfileMenuCommand.CHANGE_PASSWORD.getMatcher(line)) != null){
            return changePassword(matcher);
        } else if(ProfileMenuCommand.LOGOUT.getMatcher(line) != null){
            return logout();
        } else {
            return new Result(false, "Invalid command!");
        }
    }

    private Result showInfo() {

        String stringBuilder = "Username: " + App.getPlayerLoggedIn().getUsername() + "\n" +
                "Nickname: " + App.getPlayerLoggedIn().getNickname() + "\n" +
                "Highscore: " + App.getPlayerLoggedIn().getHighScore() + "\n" +
                "Games Played: " + App.getPlayerLoggedIn().getGameCount() + "\n";

        return new Result(true, stringBuilder);
    }

    private Result logout() {
        App.setCurrentMenu(Menus.LoginMenu);
        return new Result(true, "You have logged out!");
    }

    private Result changeUsername(Matcher matcher) {
        String username = matcher.group("username");

        if(username.equals(App.getPlayerLoggedIn().getUsername())){
            return new Result(false, "Username same as before.");
        }

        if(!isUsernameValid(username)){
            return new Result(false, "Username is invalid.");
        }

        if(!isUsernameUnique(username)){
            return new Result(false, "Username is already used.");
        }

        ChangeManager.updatePlayer(App.getPlayerLoggedIn(), p -> p.setUsername(username));

        return new Result(true, "Successfully changed username to " + username);

    }
    private Result changeNickname(Matcher matcher) {
        String nickname = matcher.group("nickname");
        if(nickname.equals(App.getPlayerLoggedIn().getNickname())){
            return new Result(false, "Nickname same as before.");
        }

        if(!isNicknameValid(nickname)){
            return new Result(false, "Nickname is invalid.");
        }

        ChangeManager.updatePlayer(App.getPlayerLoggedIn(), p -> p.setNickname(nickname));
        return new Result(true, "Successfully changed nickname to " + nickname);
    }

    private Result changePassword(Matcher matcher) {
        String newPassword = matcher.group("newPassword");
        String oldPassword = matcher.group("oldPassword");

        if(!oldPassword.equals(App.getPlayerLoggedIn().getPassword())){
            return new Result(false, "Former password incorrect.");
        }

        if(newPassword.equals(App.getPlayerLoggedIn().getPassword())){
            return new Result(false, "Password same as before.");
        }

        if(!isPasswordValid(newPassword)){
            return new Result(false, "Password is invalid.");
        }

        ChangeManager.updatePlayer(App.getPlayerLoggedIn(), p -> p.setPassword(newPassword));
        return new Result(true, "Successfully changed password to " + newPassword);
    }

    private Result changeEmail(Matcher matcher) {
        String email = matcher.group("email");

        if(email.equals(App.getPlayerLoggedIn().getEmail())){
            return new Result(false, "Email same as before.");
        }

        if(!isEmailValid(email)){
            return new Result(false, "Email is invalid.");
        }

        ChangeManager.updatePlayer(App.getPlayerLoggedIn(), p -> p.setEmail(email));
        return new Result(true, "Successfully changed email to " + email);
    }

    private boolean isUsernameValid(String username){
        if(!username.matches("^[A-Za-z0-9.-]{4,15}$")){
            System.out.println("Username can only have letters and be 4 to 15 letters long.");
            return false;
        }
        return true;
    }

    private boolean isUsernameUnique(String username){
        for(Player player : App.getRegisteredPlayers()){
            if(player.getUsername().equals(username)){
                System.out.println("Username already exists.");
                return false;
            }
        }

        return true;
    }

    private boolean isEmailValid(String email){
        if(!email.contains("@") || email.indexOf("@") != email.lastIndexOf("@")) {
            System.out.println("Email must have exactly one @.");
            return false;
        }

        if (!email.matches("^(?=.*@)(?!.*[?><,\"';:/|\\]\\[}{+=)(*&^%$#!\\\\])[^\\s@]+@[^\\s@]+$")){
            System.out.println("Email can't use illegal symbols.");
            return false;
        }

        String userEmail = email.substring(0, email.indexOf("@"));

        if(email.lastIndexOf(".") == -1 || email.lastIndexOf(".") < email.indexOf("@")){
            System.out.println("Email must have a proper TLD(top-level domain).");
            return false;
        }

        String domainEmail = email.substring(email.indexOf("@") + 1, email.lastIndexOf("."));
        String topLevel = email.substring(email.lastIndexOf(".") + 1);

        if(!userEmail.matches("^(?!.*\\.\\.)[A-Za-z0-9][A-Za-z0-9._-]*[A-Za-z0-9]$")){
            System.out.println("Email username must start " +
                    " with numbers or any letter, it must only include numbers, any letter or these symbols(._-)" +
                    " also, no consecutive dots.");
            return false;
        }

        if(!domainEmail.matches("^(?=.*\\.)[A-Za-z0-9][A-Za-z0-9.]*[A-Za-z0-9]$")){
            System.out.println("Email domain must only include letters and a single dot somewhere in the middle.");
            return false;
        }

        if(!topLevel.matches("^[a-z]{2,}$")){
            System.out.println("TLD must be at least two letters long.");
            return false;
        }

        return true;
    }

    private boolean isPasswordValid(String password){
        if(password.length() < 8){
            System.out.println("Password must be at least 8 characters long.");
            return false;
        }

        if(!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*?~])[A-Za-z0-9!@#$%^&*?~]{8,}$")) {
            System.out.println("Password must include capital letter" +
                    " lowercase letter, number and special symbol(!@#$%^&*?~).");
            return false;
        }

        return true;
    }

    private boolean isNicknameValid(String nickname){
        if(nickname.length() > 15 || nickname.length() < 3){
            System.out.println("Nickname must be 3 to 15 letters long.");
            return false;
        }

        if(!nickname.matches("^[A-Za-z]{3,15}$")){
            System.out.println("Nickname can only have letters.");
            return false;
        }

        return true;
    }
}
