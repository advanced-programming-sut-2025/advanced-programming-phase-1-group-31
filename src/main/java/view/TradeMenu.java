package view;

import controller.TradeMenuController;
import model.Result;

import java.util.Scanner;

public class TradeMenu implements Menu{
    TradeMenuController controller = new TradeMenuController();
    @Override
    public void checkCommand(Scanner scanner){
        Result result = controller.run(scanner);
        if (result != null) {
            System.out.println(result.Message());
        }
    }
}
