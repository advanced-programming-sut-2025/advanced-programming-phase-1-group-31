package controller;

import model.App;
import model.Farm;
import model.FarmFactory;
import model.Game;
import model.Player;
import model.Result;
import model.Tile;
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
        gameMap(matchedPlayers);
        return new Result(true, "Game started with " + matchedPlayers.size() + " new player(s).");
    }

    private boolean isPlayerAlreadyInGame(Player player) {
        return App.getGames().stream()
                .anyMatch(game -> game.getPlayers().contains(player));
    }

    public void gameMap(ArrayList<Player> players) {
        Scanner scanner = new Scanner(System.in);
        for (Player player : players) {
            System.out.println("Player " + player.getUsername() + ", please choose your map using 'game map <number>'");
            while (true) {
                displayFourMaps();
                String input = scanner.nextLine().trim();
                Matcher matcher = GameMenuCommand.MAP_SELECT_PATTERN.getMatcher(input);
                if (!matcher.matches()) {
                    System.out.println("Invalid command. Use 'game map <number>'");
                    continue;
                }

                int selected = Integer.parseInt(matcher.group("number"));
                if (selected < 1 || selected > 4) {
                    System.out.println("Invalid map number. Choose between 1 and 4.");
                } else {
                    player.setFarm(FarmFactory.getPreset(selected));
                    FarmFactory.randomGenerateFarm(player.getFarm());
                    System.out.println("Player " + player.getUsername() + " selected map " + selected);
                    break;
                }
            }
        }

        System.out.println("All players selected their maps. Game is starting...");
    }
    public static void displayFourMaps() {
        Tile[][] map1 = FarmFactory.getPreset(1).getMainMap();
        Tile[][] map2 = FarmFactory.getPreset(2).getMainMap();
        Tile[][] map3 = FarmFactory.getPreset(3).getMainMap();
        Tile[][] map4 = FarmFactory.getPreset(4).getMainMap();

        System.out.println("map1\t\tmap2\t\tmap3\t\tmap4");
        System.out.println("--------------------------------------------------");

        for (int y = 0; y < map1[0].length; y++) {
            for (Tile[] tiles : map1) {
                System.out.print(tiles[y].getType().getColor()+" " + tiles[y].getType().getSymbol() +" "+ "\u001B[0m");
            }
            System.out.print("\t");

            for (Tile[] tiles : map2) {
                System.out.print(tiles[y].getType().getColor()+" " + tiles[y].getType().getSymbol() +" "+ "\u001B[0m");
            }
            System.out.print("\t");

            for (Tile[] tiles : map3) {
                System.out.print(tiles[y].getType().getColor()+" " + tiles[y].getType().getSymbol() +" "+ "\u001B[0m");
            }
            System.out.print("\t");

            for (Tile[] tiles : map4) {
                System.out.print(tiles[y].getType().getColor()+" " + tiles[y].getType().getSymbol() +" "+ "\u001B[0m");
            }
            System.out.println();
        }
        System.out.println();
    }

}
