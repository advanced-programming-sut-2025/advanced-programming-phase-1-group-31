package view;

import controller.MainMenuController;
import model.enums.commands.MainMenuCommands;

import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.function.Consumer;

public class MainMenu implements Menu {
    private final MainMenuController controller;
    private final Map<MainMenuCommands, Consumer<Matcher>> commandHandlers = new EnumMap<>(MainMenuCommands.class);

    public MainMenu(){
        this.controller = new MainMenuController();
        initializeCommandHandlers();
    } 

    private void initializeCommandHandlers() {
        commandHandlers.put(MainMenuCommands.LOGOUT, matcher -> controller.logout());
        commandHandlers.put(MainMenuCommands.CHANGE_USERNAME, matcher -> {
            String username = matcher.group("username");
            controller.changeUsername(username);
        });
        commandHandlers.put(MainMenuCommands.CHANGE_NICKNAME, matcher -> {
            String nickname = matcher.group("nickname");
            controller.changeNickname(nickname);
        });
        commandHandlers.put(MainMenuCommands.CHANGE_PASSWORD, matcher -> {
            String newPassword = matcher.group("newPassword");
            String oldPassword = matcher.group("oldPassword");
            controller.changePassword(oldPassword, newPassword);
        });
        commandHandlers.put(MainMenuCommands.CHANGE_EMAIL, matcher -> {
            String email = matcher.group("email");
            controller.changeEmail(email);
        });
        commandHandlers.put(MainMenuCommands.SHOW_INFO, matcher -> controller.showInfo());
    }

    @Override
    public void checkCommand(Scanner scanner) {
        String line = scanner.nextLine();
        boolean matched = false;

        for (MainMenuCommands command : MainMenuCommands.values()) {
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
