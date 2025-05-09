package view;

import controller.LoginMenuController;
import model.Result;

import java.util.Scanner;


public class LoginMenu implements Menu {
    private final LoginMenuController controller = new LoginMenuController();

    @Override
    public void checkCommand(Scanner scanner) {
        Result result = controller.run(scanner);
        if (result != null) {
            System.out.println(result.Message());
        }
    }
    
}
