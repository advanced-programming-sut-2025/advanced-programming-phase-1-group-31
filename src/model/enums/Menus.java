package model.enums;

import com.sun.tools.javac.Main;
import view.*;

public enum Menus {
    LoginMenu(new LoginMenu()),
    MainMenu(new MainMenu()),
    GameMenu(new GameMenu()),
    ProfileMenu(new ProfileMenu()),
    AvatarMenu(new AvatarMenu()),
    ShopMenu(new ShopMenu()),
    HouseMenu(new HouseMenu()),
    TradeMenu(new TradeMenu());



    private final Menu menu;

    Menus(Menu menu) {
        this.menu = menu;
    }
}
