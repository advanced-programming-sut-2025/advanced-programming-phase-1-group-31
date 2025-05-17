package view;

import controller.LoginMenuController;
import controller.MainMenuController;
import model.Result;

import java.util.Scanner;

public class MainMenu implements Menu {
    private final MainMenuController controller = new MainMenuController();
    @Override
    public void checkCommand(Scanner scanner){
        Result result = controller.run(scanner);
            if (result != null) {
                System.out.println(result.Message());
            }
    }
}
