package view;

import model.Player;
import model.enums.general.Menus;

import java.util.Scanner;

public class AppView {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        do {
            Player.getCurrentMenu().check(scanner);
        } while (Player.getCurrentMenu() != Menus.ExitMenu);
    }
}
//check goes to enums and there it calls the checkcommand of the related menu and there the
//command is identified
