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
        } else if ((matcher = GameMenuCommand.PRINT_MAP.getMatcher(input)) != null) {
            return printMap(matcher);
        } else if ((matcher = GameMenuCommand.WALK.getMatcher(input)) != null) {
            return walk(matcher);
        }
        return new Result(false, "Invalid command.");
    }

    private Result gameNew(Matcher matcher) {
        String[] usernames = matcher.group("usernames").trim().split("\\s+");

        if (usernames.length < 1)
            return new Result(false, "At least 1 username is required.");

        if (Arrays.asList(usernames).contains(App.getPlayerLoggedIn().getUsername())) {
            return new Result(false, "you can not chose own");
        }

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
        game.setAdminPlayer(App.getPlayerLoggedIn());
        game.setActivePlayer(App.getPlayerLoggedIn());
        App.addGames(game);
        App.setCurrentGame(game);
        gameMap(matchedPlayers);
        return new Result(true, "Game started with " + matchedPlayers.size() + " new player(s).");
    }

    public void gameMap(ArrayList<Player> players) {
        Scanner scanner = new Scanner(System.in);
        for (Player player : players) {
            System.out.println("Player " + player.getUsername() + ", please choose your map using 'game map <number>'");
            while (true) {
                displayFourMaps();
                String input = scanner.nextLine().trim();
                Matcher matcher;

                if ((matcher = GameMenuCommand.MAP_SELECT_PATTERN.getMatcher(input)) == null) {
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
        setPlacePlayer();
    }

    private boolean isPlayerAlreadyInGame(Player player) {
        return App.getGames().stream()
                .anyMatch(game -> game.getPlayers().contains(player));
    }

    private Result printMap(Matcher matcher) {
        int x = 0, y = 0, size = 0;
        try {
            x = Integer.parseInt(matcher.group("X"));
            y = Integer.parseInt(matcher.group("Y"));
            size = Integer.parseInt(matcher.group("size"));
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }

        Point pos = new Point(x, y);
        int startX = pos.x;
        int startY = pos.y;
        int endX = Math.min(140, pos.x + size + 1);
        int endY = Math.min(100, pos.y + size + 1);

        System.out.println("موقعیت بازیکن: (" + pos.x + "," + pos.y + ")");

        for (int i = startX; i < endX; i++) {
            for (int j = startY; j < endY; j++) {
                Tile tile = App.getCurrentGame().getMainMap().getMainMap()[i][j];
                System.out.print(tile.getType().getColor() + tile.getType().getSymbol() + "\u001B[0m");
            }
            System.out.println();
        }

        return new Result(true, "نقشه با موفقیت چاپ شد");
    }

    public Result walk(Matcher matcher) {
        int x = 0, y = 0;
        Point place = App.getCurrentGame().getActivePlayer().getPlace();
        try {
            x = Integer.parseInt(matcher.group("X"));
            y = Integer.parseInt(matcher.group("Y"));
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        } 
        if (App.getCurrentGame().getActivePlayer().getFarm().getRectangle().contains(place) && !App.getCurrentGame().getActivePlayer().getFarm().getRectangle().contains(new Point(x, y))) {
            return new Result(false, "you are in your farm please use the door");
        }
        if (App.getCurrentGame().getMainMap().getMainMap()[x][y].getType()!=TileType.EMPTY) {
            System.out.println("Tile at (" + x + "," + y + ") type: " + App.getCurrentGame().getMainMap().getMainMap()[x][y].getType());
            return new Result(false, "gtg");
        }
        App.getCurrentGame().getMainMap().getMainMap()[place.x][place.y].setType(TileType.EMPTY);
        App.getCurrentGame().getMainMap().getMainMap()[x][y].setType(App.getCurrentGame().getActivePlayer().getType());
        place.move(x,y);
        App.getCurrentGame().getActivePlayer().setPlace(place);
        return new Result(true,"موقعیت کنونی بازیکن: (" + place.x + "," + place.y + ")");
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
        if (!isEmptyFarm(f1)) {

        }

        for (int x = 0; x < farmWidth; x++) {
            for (int y = 0; y < farmHeight; y++) {
                map.setMainMap(m2[x][y], x + 85, y);
                m2[x][y].setPoint(new Point(x + 85, y));
            }
        }
        if (!isEmptyFarm(f2)) {

            f2.getGreenhouse().getRectangle().translate(85, 0);
            f2.getCottage().getRectangle().translate(85, 0);
            f2.getLakeInFarm().forEach(l -> l.getRectangle().translate(85, 0));
            f2.getQuarryInFarm().forEach(q -> q.getRectangle().translate(85, 0));
            f2.getRectangle().translate(85, 0);
        }

        for (int x = 0; x < farmWidth; x++) {
            for (int y = 0; y < farmHeight; y++) {
                map.setMainMap(m3[x][y], x, y + 65);
                m3[x][y].setPoint(new Point(x, y + 65));
            }
        }
        if (!isEmptyFarm(f3)) {
            f3.getGreenhouse().getRectangle().translate(0, 65);
            f3.getCottage().getRectangle().translate(0, 65);
            f3.getLakeInFarm().forEach(l -> l.getRectangle().translate(0, 65));
            f3.getQuarryInFarm().forEach(q -> q.getRectangle().translate(0, 65));
            f3.getRectangle().translate(0, 65);

        }

        for (int x = 0; x < farmWidth; x++) {
            for (int y = 0; y < farmHeight; y++) {
                map.setMainMap(m4[x][y], x + 85, y + 65);
                m4[x][y].setPoint(new Point(x + 85, y + 65));
            }
        }
        if (!isEmptyFarm(f4)) {
            f4.getGreenhouse().getRectangle().translate(85, 65);
            f4.getCottage().getRectangle().translate(85, 65);
            f4.getLakeInFarm().forEach(l -> l.getRectangle().translate(85, 65));
            f4.getQuarryInFarm().forEach(q -> q.getRectangle().translate(85, 65));
            f4.getRectangle().translate(85, 65);

        }
        Farm marketFarm = FarmFactory.generateStors();
        for (int x = 0; x < marketFarm.getMainMap().length; x++) {
            for (int y = 0; y < marketFarm.getMainMap()[0].length; y++) {
                map.setMainMap(marketFarm.getMainMap()[x][y], x + 30, y + 40);
                marketFarm.getMainMap()[x][y].setPoint(new Point(x + 30, y + 40));
            }
        }
        for (int x = 0; x < 140; x++) {
            for (int y = 0; y < 100; y++) {
                if (x < 55 && y < 35)
                    continue; // مزرعه 1
                if (x >= 85 && y < 35)
                    continue; // مزرعه 2
                if (x < 55 && y >= 65)
                    continue; // مزرعه 3
                if (x >= 85 && y >= 65)
                    continue; // مزرعه 4
                if (x >= 30 && y >= 40 && y <= 60 && x <= 90) {
                    continue;
                }
                Tile tile = new Tile();
                tile.setType(TileType.EMPTY);
                map.setMainMap(tile, x, y);
            }
        }

    }

    public static void displayFourMaps() {
        Tile[][] map1 = FarmFactory.getPreset(1).getMainMap();
        Tile[][] map2 = FarmFactory.getPreset(2).getMainMap();
        Tile[][] map3 = FarmFactory.getPreset(3).getMainMap();
        Tile[][] map4 = FarmFactory.getPreset(4).getMainMap();

        System.out.println("map1\t\tmap2\t\tmap3\t\tmap4");
        System.out.println("--------------------------------------------------");

        for (int x = 0; x < map1.length; x++) {
            for (int y = 0; y < map1[0].length; y++) {
                System.out.print(
                        map1[x][y].getType().getColor() + " " + map1[x][y].getType().getSymbol() + " " + "\u001B[0m");
            }
            System.out.print("\t");

            for (int y = 0; y < map2[0].length; y++) {
                System.out.print(
                        map2[x][y].getType().getColor() + " " + map2[x][y].getType().getSymbol() + " " + "\u001B[0m");
            }
            System.out.print("\t");

            for (int y = 0; y < map3[0].length; y++) {
                System.out.print(
                        map3[x][y].getType().getColor() + " " + map3[x][y].getType().getSymbol() + " " + "\u001B[0m");
            }
            System.out.print("\t");

            for (int y = 0; y < map4[0].length; y++) {
                System.out.print(
                        map4[x][y].getType().getColor() + " " + map4[x][y].getType().getSymbol() + " " + "\u001B[0m");
            }
            System.out.println();
        }

        System.out.println();
    }

    private void setPlacePlayer() {
        List<TileType> symbols = List.of(
                TileType.PLAYER1,
                TileType.PLAYER2,
                TileType.PLAYER3,
                TileType.PLAYER4);

        List<Player> players = App.getCurrentGame().getPlayers();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            TileType symbol = symbols.get(i);

            Point startPoint = new Point(
                    player.getFarm().getRectangle().x + player.getFarm().getRectangle().width / 2,
                    player.getFarm().getRectangle().y + player.getFarm().getRectangle().height / 2);
            player.setPlace(startPoint);
            player.setType(symbol);
            App.getCurrentGame().getMainMap().getMainMap()[startPoint.x][startPoint.y].setType(symbol);
        }
    }

    public static boolean isEmptyFarm(Farm farm) {
        return farm.getMainMap() == null || Arrays.stream(farm.getMainMap())
                .flatMap(Arrays::stream)
                .allMatch(tile -> tile.getType() == TileType.EMPTY);
    }

}
