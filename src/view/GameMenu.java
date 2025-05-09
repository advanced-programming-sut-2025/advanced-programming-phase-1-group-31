package view;

import controller.GameMenuController;
import controller.MainMenuController;
import model.Result;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameMenu implements Menu{
    private final GameMenuController controller = new GameMenuController();
    @Override
    public void checkCommand(Scanner scanner){
        Result result = controller.run(scanner);
            if (result != null) {
                System.out.println(result.message());
            }
    }
}
