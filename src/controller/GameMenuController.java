package controller;

import model.App;
import model.Farm;
import model.FarmFactory;
import model.Game;
import model.Player;
import model.Result;
import model.Tile;
import model.enums.Menus;
import model.enums.TileType;
import model.enums.commands.GameMenuCommand;

import java.awt.Point;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import model.Map;

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

        Farm emptyFarm = FarmFactory.getEmptyFarm();
        Farm f1 = players.size() >= 1 ? players.get(0).getFarm() : emptyFarm;
        Farm f2 = players.size() >= 2 ? players.get(1).getFarm() : emptyFarm;
        Farm f3 = players.size() >= 3 ? players.get(2).getFarm() : emptyFarm;
        Farm f4 = players.size() >= 4 ? players.get(3).getFarm() : emptyFarm;

        Map map = new Map();
        integrateFarmsIntoMainMap(map, f1, f2, f3, f4);
        App.getCurrentGame().setMainMap(map);
    }

    public static void integrateFarmsIntoMainMap(Map map, Farm f1, Farm f2, Farm f3, Farm f4) {
        Tile[][] m1 = f1.getMainMap();
        Tile[][] m2 = f2.getMainMap();
        Tile[][] m3 = f3.getMainMap();
        Tile[][] m4 = f4.getMainMap();

        int farmHeight = m1[0].length;
        int farmWidth = m1.length;

        for (int x = 0; x < farmWidth; x++) {
            for (int y = 0; y < farmHeight; y++) {
                map.setMainMap(m1[x][y], x, y);
                m1[x][y].setPoint(new Point(x, y));
            }
        }
        if(!isEmptyFarm(f1)){
            addWallsAroundFarm(map ,f1, 0, 0);
        }

        for (int x = 0; x < farmWidth; x++) {
            for (int y = 0; y < farmHeight; y++) {
                map.setMainMap(m2[x][y], x + 85, y);
                m2[x][y].setPoint(new Point(x + 85, y));
            }
        }
        if (!isEmptyFarm(f2)){
            addWallsAroundFarm(map,f2, 85, 0);
            f2.getGreenhouse().getRectangle().translate(85, 0);
            f2.getCottage().getRectangle().translate(85, 0);
            f2.getLakeInFarm().forEach(l -> l.getRectangle().translate(85, 0));
            f2.getQuarryInFarm().forEach(q -> q.getRectangle().translate(85, 0));
            f2.getRectangle().translate(85, 0);
    
        }

       
        for (int x = 0; x < farmWidth; x++) {
            for (int y = 0; y < farmHeight; y++) {
                map.setMainMap(m3[x][y], x, y + 55);
                m3[x][y].setPoint(new Point(x, y + 55));
            }
        }
        if (!isEmptyFarm(f3)){
            addWallsAroundFarm(map,f3, 0, 55);
            f3.getGreenhouse().getRectangle().translate(0, 85);
            f3.getCottage().getRectangle().translate(0, 85);
            f3.getLakeInFarm().forEach(l -> l.getRectangle().translate(0, 85));
            f3.getQuarryInFarm().forEach(q -> q.getRectangle().translate(0, 85));
            f3.getRectangle().translate(0, 85);
    
        }
        
        for (int x = 0; x < farmWidth; x++) {
            for (int y = 0; y < farmHeight; y++) {
                map.setMainMap(m4[x][y], x + 85, y + 55);
                m4[x][y].setPoint(new Point(x + 85, y + 55));
            }
        }
        if (!isEmptyFarm(f4)){
            addWallsAroundFarm(map ,f4, 85, 55);
            f4.getGreenhouse().getRectangle().translate(85, 85);
            f4.getCottage().getRectangle().translate(85, 85);
            f4.getLakeInFarm().forEach(l -> l.getRectangle().translate(85, 85));
            f4.getQuarryInFarm().forEach(q -> q.getRectangle().translate(85, 85));
            f4.getRectangle().translate(85, 85);
    
        }
        
        for (int x = 0; x < 140; x++) {
            for (int y = 0; y < 90; y++) {
                if (x < 55 && y < 35)
                    continue; // مزرعه 1
                if (x >= 85 && y < 35)
                    continue; // مزرعه 2
                if (x < 55 && y >= 55)
                    continue; // مزرعه 3
                if (x >= 85 && y >= 55)
                    continue; // مزرعه 4
                Tile tile = new Tile();
                tile.setType(TileType.EMPTY);
                map.setMainMap(tile, x, y);
            }
        }
    }
    private static void addWallsAroundFarm(Map map, Farm farm, int offsetX, int offsetY) {
        int farmWidth = farm.getRectangle().width;
        int farmHeight = farm.getRectangle().height;
        
        for (int y = 0; y < farmHeight; y++) {
            Tile leftWall = new Tile();
            leftWall.setType(TileType.WALL);
            map.setMainMap(leftWall, offsetX, offsetY + y);
            
            Tile rightWall = new Tile();
            rightWall.setType(TileType.WALL);
            map.setMainMap(rightWall, offsetX + farmWidth - 1, offsetY + y);
        }
        
        for (int x = 1; x < farmWidth - 1; x++) {
            Tile topWall = new Tile();
            topWall.setType(TileType.WALL);
            map.setMainMap(topWall, offsetX + x, offsetY);
            
            Tile bottomWall = new Tile();
            bottomWall.setType(TileType.WALL);
            map.setMainMap(bottomWall, offsetX + x, offsetY + farmHeight - 1);
        }
        
        farm.getRectangle().setBounds(offsetX, offsetY, farmWidth, farmHeight);
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
                System.out.print(
                        tiles[y].getType().getColor() + " " + tiles[y].getType().getSymbol() + " " + "\u001B[0m");
            }
            System.out.print("\t");

            for (Tile[] tiles : map2) {
                System.out.print(
                        tiles[y].getType().getColor() + " " + tiles[y].getType().getSymbol() + " " + "\u001B[0m");
            }
            System.out.print("\t");

            for (Tile[] tiles : map3) {
                System.out.print(
                        tiles[y].getType().getColor() + " " + tiles[y].getType().getSymbol() + " " + "\u001B[0m");
            }
            System.out.print("\t");

            for (Tile[] tiles : map4) {
                System.out.print(
                        tiles[y].getType().getColor() + " " + tiles[y].getType().getSymbol() + " " + "\u001B[0m");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static boolean isEmptyFarm(Farm farm) {
        return farm.getMainMap() == null || Arrays.stream(farm.getMainMap())
            .flatMap(Arrays::stream)
            .allMatch(tile -> tile.getType() == TileType.EMPTY);
    }
    

}
