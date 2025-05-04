package view;

import model.Game;
import model.enums.general.Menus;

import java.util.Scanner;

public class AppView {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        do {
            Game.getActivePlayer().getCurrentMenu().check(scanner);
        } while (Game.getActivePlayer().getCurrentMenu() != Menus.ExitMenu);
    }
}
//check goes to enums and there it calls the checkcommand of the related menu and there the
//command is identified
