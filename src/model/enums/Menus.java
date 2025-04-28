package model.enums;

import view.*;

import java.util.Scanner;

public enum Menus {
    LoginMenu(new LoginMenu(), "login"),
    MainMenu(new MainMenu(), "main"),
    GameMenu(new GameMenu(), "game"),
    ProfileMenu(new ProfileMenu(), "profile"),
    ShopMenu(new ShopMenu(), "shop"),
    HouseMenu(new HouseMenu(), "house"),
    ExitMenu(new ExitMenu(), "exit"),
    TradeMenu(new TradeMenu(), "trade");


    private final Menu menu;
    private final String name;

    Menus(Menu menu, String name) {
        this.menu = menu;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void check(Scanner scanner){
        this.menu.checkCommand(scanner);
    }
}
