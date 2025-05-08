package controller;

import java.util.Scanner;
import java.util.regex.Matcher;

import model.App;
import model.Result;
import model.enums.Menus;
import model.enums.commands.MainMenuCommands;

public class MainMenuController {
    public Result run(Scanner scanner){
        String line = scanner.nextLine();
        Matcher matcher;

        if ((matcher = MainMenuCommands.MENU_ENTER.getMatcher(line)) != null) {
            return menuEnter(matcher);
        } else{
            return new Result(false, "Invalid command!");
        }
    }
    private Result menuEnter(Matcher matcher){
        String menuName = matcher.group("menuName").trim();
        if (menuName.equals(Menus.GameMenu.getName())) {
            App.setCurrentMenu(Menus.GameMenu);
            return new Result(true,"You are in gameMenu");
        }
        return new Result(false,menuName + "dosent exist");


    }
    public void logout() {}
    public void changeUsername(String username) {}
    public void changeNickname(String nickname) {}
    public void changePassword(String oldPassword, String newPassword) {}
    public void changeEmail(String email) {}
    public void showInfo() {}
}
