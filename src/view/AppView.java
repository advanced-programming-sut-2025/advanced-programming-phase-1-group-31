package view;

import model.App;
import model.enums.Menus;

import java.util.Scanner;

public class AppView {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        do {
            App.getCurrentMenu().check(scanner);
        } while (App.getCurrentMenu() != Menus.ExitMenu);
    }
}
//check goes to enums and there it calls the checkcommand of the related menu and there the
//command is identified
