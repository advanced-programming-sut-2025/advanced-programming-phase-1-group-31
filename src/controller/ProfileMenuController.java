package controller;

import model.Result;
import model.enums.commands.ProfileMenuCommand;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenuController {
    private static final ProfileMenuController instance = new ProfileMenuController();

    public static ProfileMenuController getInstance() {
        return instance;
    }

    public Result run(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;

        if ((matcher = ProfileMenuCommand.CHANGE_USERNAME.getMatcher(input)) != null) {
            return changeUsername(matcher);
        } else if ((matcher = ProfileMenuCommand.CHANGE_NICKNAME.getMatcher(input)) != null) {
            return changeNickname(matcher);
        } else if ((matcher = ProfileMenuCommand.CHANGE_EMAIL.getMatcher(input)) != null) {
            return changeEmail(matcher);
        } else if ((matcher = ProfileMenuCommand.CHANGE_PASSWORD.getMatcher(input)) != null) {
            return changePassword(matcher);
        } else if ((matcher = ProfileMenuCommand.SHOW_USER_INFO.getMatcher(input)) != null) {
            return showUserInfo(matcher);
        } else {
            return new Result(false, "Invalid command!");
        }
    }

    private Result changeUsername(Matcher matcher) {
        String newUsername = matcher.group("username");
        return new Result(true, "Username changed to: " + newUsername);
    }

    private Result changeNickname(Matcher matcher) {
        String newNickname = matcher.group("nickname");
        return new Result(true, "Nickname changed to: " + newNickname);
    }

    private Result changeEmail(Matcher matcher) {
        String newEmail = matcher.group("email");
        return new Result(true, "Email changed to: " + newEmail);
    }

    private Result changePassword(Matcher matcher) {
        String oldPassword = matcher.group("oldPassword");
        String newPassword = matcher.group("newPassword");
        return new Result(true, "Password changed from " + oldPassword + " to " + newPassword);
    }

    private Result showUserInfo(Matcher matcher) {
        return new Result(true, "Showing user information...");
    }
}
