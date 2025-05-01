package controller;

import model.App;
import model.Game;
import model.Player;
import model.Result;
import model.enums.Menus;
import model.enums.commands.GameMenuCommand;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class GameMenuController {
    private static final GameMenuController instance = new GameMenuController();

    public static GameMenuController getInstance() {
        return instance;
    }

    public Result run(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = GameMenuCommand.GAME_NEW_PATTERN.getMatcher(input)) != null) {
            return gameNew(matcher);
        }
        return new Result(false, "Invalid command.");
    }

    private Result gameNew(Matcher matcher) {
        String[] usernames = matcher.group("usernames").trim().split("\\s+");

        if (usernames.length < 1)
            return new Result(false, "At least 1 username is required.");

        if (usernames.length > 3)
            return new Result(false, "A maximum of 3 usernames is allowed.");
        if (isPlayerAlreadyInGame(App.getPlayerLoggedIn()))
            return new Result(false, "User already in game: " + App.getPlayerLoggedIn().getUsername());
        for (String username : usernames) {
            Player player = App.getRegisteredPlayers().stream().filter(b -> b.getUsername().equals(username))
                    .findFirst().orElse(null);
            if (player == null)
                return new Result(false, "Invalid username: " + username);
            if (isPlayerAlreadyInGame(player))
                return new Result(false, "User already in game: " + username);
        }
        ArrayList<Player> matchedPlayers = App.getRegisteredPlayers().stream()
                .filter(player -> Arrays.asList(usernames).contains(player.getUsername()))
                .collect(Collectors.toCollection(ArrayList::new));
        matchedPlayers.add(0, App.getPlayerLoggedIn()); 
        matchedPlayers.forEach(p -> p.setCurrentMenu(Menus.GameMenu));
        Game game = new Game(matchedPlayers);
        App.addGames(game);
        App.setCurrentGame(game);
        return new Result(true, "Game started with " + usernames.length + " new player(s).");
    }

    private boolean isPlayerAlreadyInGame(Player player) {
        return App.getGames().stream()
                .anyMatch(game -> game.getPlayers().contains(player));
    }

}
