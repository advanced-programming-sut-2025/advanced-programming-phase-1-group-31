package view;

import controller.LoginMenuController;
import model.enums.commands.LoginMenuCommands;

import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.function.Consumer;

public class LoginMenu implements Menu {
    private final LoginMenuController controller;
    private final Map<LoginMenuCommands, Consumer<Matcher>> commandHandlers = new EnumMap<>(LoginMenuCommands.class);

    public LoginMenu(){
        this.controller = new LoginMenuController();
        initializeCommandHandlers();
    }
    
    private void initializeCommandHandlers() {
        commandHandlers.put(LoginMenuCommands.ENTER_MENU, matcher -> {
            String menu = matcher.group("menu");
            controller.enterMenu(menu);
        });
        commandHandlers.put(LoginMenuCommands.EXIT_MENU, matcher -> controller.exitMenu());
        commandHandlers.put(LoginMenuCommands.SHOW_CURRENT_MENU, matcher -> controller.showCurrentMenu());
        commandHandlers.put(LoginMenuCommands.REGISTER, matcher -> {
            String username = matcher.group("username");
            String password = matcher.group("password");
            String passwordConfirmation = matcher.group("passwordConfirmation");
            String nickname = matcher.group("nickname");
            String email = matcher.group("email");
            String gender = matcher.group("gender");
            controller.register(username, password, passwordConfirmation, nickname, email, gender);
        });
        commandHandlers.put(LoginMenuCommands.LOGIN, matcher -> {
            String username = matcher.group("username");
            String password = matcher.group("password");
            boolean stayLoggedIn = matcher.group("flag") != null;
            controller.login(username, password, stayLoggedIn);
        });
        commandHandlers.put(LoginMenuCommands.PASSWORD_RECOVERY, matcher -> {
            String username = matcher.group("username");
            controller.recoverPassword(username);
        });
    }

    @Override
    public void checkCommand(Scanner scanner) {
        String line = scanner.nextLine();
        boolean matched = false;

        for (LoginMenuCommands command : LoginMenuCommands.values()) {
            Matcher matcher = command.getMatcher(line);
            if (matcher.matches()) {
                commandHandlers.get(command).accept(matcher);
                matched = true;
                break;
            }
        }

        if (!matched) {
            System.out.println("Invalid command!");
        }
    }
}
