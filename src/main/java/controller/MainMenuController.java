package controller;

import model.App;
//import model.ChangeManager;
import model.Result;
import model.enums.general.Menus;
import model.enums.commands.MainMenuCommands;
import java.util.regex.Matcher;
import java.util.Scanner;


public class MainMenuController {
    public Result run(Scanner scanner){
        String line = scanner.nextLine();
        Matcher matcher;

        if((matcher = MainMenuCommands.MENU_ENTER.getMatcher(line)) != null){
            return changeMenu(matcher);
        } else if (MainMenuCommands.SHOW_CURRENT_MENU.getMatcher(line) != null) {
            return showCurrentMenu();
        } else if (MainMenuCommands.LOGOUT.getMatcher(line) != null) {
            return logout();
        } else {
            return new Result(false, "Invalid command!");
        }
    }

    private Result logout(){
        App.setCurrentMenu(Menus.LoginMenu);
//        ChangeManager.updatePlayer(App.getPlayerLoggedIn(), p -> p.setStayLoggedIn(false));
        return new Result(true, "Logged out successfully.");
    }

    private Result showCurrentMenu(){
        return new Result(true, "You're now in " + App.getCurrentMenu().getName() + ".");
    }

    private Result changeMenu(Matcher matcher){
        String menuName = matcher.group("menuName");
        if(menuName.equals("profile")){
            App.setCurrentMenu(Menus.ProfileMenu);
            return new Result(true, "You are now in profile menu.");
        } else if(menuName.equals("game")){
            App.setCurrentMenu(Menus.GameMenu);
            return new Result(true, "You are now in game menu.");
        } else {
            return new Result(false, "Invalid menu name.");
        }
    }
}

