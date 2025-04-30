package model.enums;

import com.sun.tools.javac.Main;
import view.*;

import java.util.Scanner;

public enum Menus {
    LoginMenu(new LoginMenu()),
    MainMenu(new MainMenu()),
    GameMenu(new GameMenu()),
    ProfileMenu(new ProfileMenu()),
    ShopMenu(new ShopMenu()),
    HouseMenu(new HouseMenu()),
    ExitMenu(new ExitMenu()),
    TradeMenu(new TradeMenu());



    private final Menu menu;

    Menus(Menu menu) {
        this.menu = menu;
    }

    public void check(Scanner scanner){
        this.menu.checkCommand(scanner);
    }
}
