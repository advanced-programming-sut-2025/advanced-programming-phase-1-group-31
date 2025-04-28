package view;

import controller.HouseMenuController;

import java.util.Scanner;

public class HouseMenu implements Menu{
    HouseMenuController controller = new HouseMenuController();
    public void checkCommand(Scanner scanner){    
        if (controller.run(scanner).message() != null){
            System.out.println(controller.run(scanner).message());
        }  
    }
}
