package controller;

import model.*;
import model.Map;
import model.enums.creature.Animals;
import model.enums.foragings.ForagingMinerals;
import model.enums.general.Direction;
import model.enums.general.Menus;
import model.enums.general.TileType;
import model.enums.plantable.Crops;
import model.enums.plantable.MixedSeedSeasons;
import model.enums.plantable.Seeds;
import model.enums.plantable.Trees;
import model.materials.*;
import model.materials.Foraging.ForagingMineral;
import model.enums.commands.GameMenuCommand;
import model.enums.creature.CoopsAndBarnsTypes;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        } else if ((matcher = GameMenuCommand.SHOW_ENERGY.getMatcher(input)) != null) {
            return showEnergy(matcher);
        } else if ((matcher = GameMenuCommand.CHEAT_CHANGE_ENERGY.getMatcher(input)) != null) {
            return cheatChangeEnergy(matcher);
        } else if ((matcher = GameMenuCommand.UNLIMITED_ENERGY.getMatcher(input)) != null) {
            return unlimitedEnergy(matcher);
        } else if ((matcher = GameMenuCommand.PLANT_SEED.getMatcher(input)) != null) {
            return handlePlantCommand(matcher);
        } else if ((matcher = GameMenuCommand.SHOW_CRAFT_INFO.getMatcher(input)) != null) {
            return showPlant(matcher);
        } else if ((matcher = GameMenuCommand.USE_TOOL.getMatcher(input)) != null) {
            return useTool(matcher);
        } else if ((matcher = GameMenuCommand.BUILD_GREENHOUSE.getMatcher(input)) != null) {
            return buildGreenhouse(matcher);
        } else if ((matcher = GameMenuCommand.BUILD_BARN.getMatcher(input)) != null) {
            return buildStructure(matcher);
        } else if ((matcher = GameMenuCommand.BUY_ANIMAL.getMatcher(input)) != null) {
            return buyAnimal(matcher);
        } else if ((matcher = GameMenuCommand.PET_ANIMAL.getMatcher(input)) != null) {
            return petAnimalByName(matcher);
        } else if ((matcher = GameMenuCommand.SHOW_ANIMALS.getMatcher(input)) != null) {
            return showAnimals();
        }else if ((matcher = GameMenuCommand.CHEAT_SET_FRIENDSHIP.getMatcher(input)) != null) {
            return cheatSetFriendship(matcher);
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
        int endX = Math.min(140, pos.x + size);
        int endY = Math.min(100, pos.y + size);

        System.out.println("موقعیت بازیکن: (" + pos.x + "," + pos.y + ")");

        Tile[][] mainMap = App.getCurrentGame().getMainMap().getMainMap();

        for (int i = startX; i < endX; i++) {
            for (int j = startY; j < endY; j++) {
                Point current = new Point(i, j);
                Player playerAtPos = App.getCurrentGame().getPlayers()
                        .stream()
                        .filter(p -> p.getPlace().equals(current))
                        .findFirst()
                        .orElse(null);

                if (playerAtPos != null) {
                    System.out
                            .print(playerAtPos.getType().getColor() + playerAtPos.getType().getSymbol() + "\u001B[0m");
                } else {
                    Tile tile = mainMap[i][j];
                    System.out.print(tile.getType().getColor() + tile.getType().getSymbol() + "\u001B[0m");
                }
            }
            System.out.println();
        }

        return new Result(true, "نقشه با موفقیت چاپ شد");
    }

    public Result walk(Matcher matcher) {
        int destX, destY;
        try {
            destX = Integer.parseInt(matcher.group("X"));
            destY = Integer.parseInt(matcher.group("Y"));
        } catch (Exception e) {
            return new Result(false, "Invalid coordinates: " + e.getMessage());
        }

        Player player = App.getCurrentGame().getActivePlayer();
        Point start = player.getPlace();
        Point dest = new Point(destX, destY);
        Tile[][] map = App.getCurrentGame().getMainMap().getMainMap();
        Tile prevTile = map[start.x][start.y];
        TileType prevType = prevTile.getType();
        if (!inBounds(dest, map) || (map[destX][destY].getType() != TileType.EMPTY
                && map[destX][destY].getType() != TileType.GREENHOUSE_BUILT
                && map[destX][destY].getType() != TileType.PLANTINGSOIL))
            return new Result(false, "Destination is blocked.");

        for (Player p : App.getCurrentGame().getPlayers()) {
            if (!p.equals(player) && p.getFarm().getRectangle().contains(dest))
                return new Result(false, "Can't enter other player's farm.");
        }

        List<Point> path = bfs(start, dest, map);
        if (path == null)
            return new Result(false, "No path found.");

        map[start.x][start.y].setType(prevType);
        player.setPlace(dest);

        double energyLoss = path.size() / 20.0;
        player.getEnergy().changeEnergy(-energyLoss);

        return new Result(true, "Moved to: (" + dest.x + "," + dest.y + ")");
    }

    private boolean inBounds(Point p, Tile[][] map) {
        return p.x >= 0 && p.y >= 0 && p.x < map.length && p.y < map[0].length;
    }

    private List<Point> bfs(Point start, Point dest, Tile[][] map) {
        int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        HashMap<Point, Point> parent = new HashMap<>();
        Queue<Point> queue = new LinkedList<>();
        Set<Point> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.equals(dest))
                break;

            for (int[] d : dirs) {
                Point next = new Point(current.x + d[0], current.y + d[1]);
                if (inBounds(next, map) && !visited.contains(next)
                        && (map[next.x][next.y].getType() == TileType.EMPTY
                                || map[next.x][next.y].getType() != TileType.GREENHOUSE_BUILT
                                || map[next.x][next.y].getType() != TileType.PLANTINGSOIL)) {
                    queue.add(next);
                    visited.add(next);
                    parent.put(next, current);
                }
            }
        }

        if (!parent.containsKey(dest))
            return null;

        List<Point> path = new ArrayList<>();
        for (Point at = dest; at != null; at = parent.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    public Result showEnergy(Matcher matcher) {
        if (App.getCurrentGame().getActivePlayer().getEnergy().getEnergyAmount() == Double.POSITIVE_INFINITY) {
            return new Result(true,
                    "your energy " + App.getCurrentGame().getActivePlayer().getEnergy().getEnergyAmount());
        } else {
            return new Result(false, "your energy "
                    + (int) Math.round(App.getCurrentGame().getActivePlayer().getEnergy().getEnergyAmount()));
        }
    }

    public Result cheatChangeEnergy(Matcher matcher) {
        Double amountEnergy = 0.0;
        try {
            amountEnergy = Double.parseDouble(matcher.group("value"));
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
        Energy energy = App.getCurrentGame().getActivePlayer().getEnergy();
        if (amountEnergy >= energy.getMaxEnergy()) {
            Double maxEnergy = energy.getMaxEnergy();
            int maxEnergyInt = (int) Math.round(maxEnergy);
            return new Result(false, "your energy is bigger than " + maxEnergyInt);
        }
        if (energy.getMaxEnergy() == Double.POSITIVE_INFINITY) {
            energy.setMaxEnergy(200.0);
        }
        App.getCurrentGame().getActivePlayer().getEnergy().setEnergyAmount(amountEnergy);
        return new Result(false, "your energy set to " + (int) Math.round(amountEnergy));
    }

    public Result unlimitedEnergy(Matcher matcher) {
        Energy energy = App.getCurrentGame().getActivePlayer().getEnergy();
        energy.setEnergyAmount(Double.POSITIVE_INFINITY);
        energy.setMaxEnergy(Double.POSITIVE_INFINITY);
        return new Result(false, "your energy set to unlimited");
    }

    public Result handlePlantCommand(Matcher matcher) {
        String seedName = matcher.group("seed");
        String direction = matcher.group("direction");
        Player player = App.getCurrentGame().getActivePlayer();
        Point pos = player.getPlace();
        Point target = Direction.fromString(direction).apply(pos);

        Tile[][] map = player.getFarm().getMainMap();
        Tile targetTile = map[target.x][target.y];

        if (targetTile.getType() != TileType.PLANTINGSOIL && targetTile.getType() != TileType.GREENHOUSE_BUILT)
            return new Result(false, "Soil is not tilled!");
        // if (!targetTile.isPlantable()) return new Result(false, "Can't plant here!");
        Seed seed = findCropBySeed(seedName);
        if (seed == null)
            return new Result(false, "Invalid seed!");
        if (targetTile.getType() != TileType.GREENHOUSE_BUILT) {
            targetTile.setType(TileType.SEED);
            targetTile.setMaterial(seed);
            return new Result(true, seedName + " planted!");
        }
        Crops crops = seed.getCorrespondingCrop();
        if (crops != null) {
            if (!crops.getSeasons().contains(App.getCurrentGame().getTimeAndDate().getSeason())) {
                return new Result(false, "This crop cannot be planted in this season.");
            }
        }
        Trees trees = seed.getCorrespondingTrees();
        if (trees != null) {
            if (!trees.getSeasons().contains(App.getCurrentGame().getTimeAndDate().getSeason())) {
                return new Result(false, "This tree cannot be planted in this season.");
            }
        }
        Seeds seeds = getCorrespondingMixedSeasons(seedName);
        if (seeds != null) {
            for (MixedSeedSeasons mixedSeed : MixedSeedSeasons.values()) {
                if (mixedSeed.getName().equals(seedName)) {
                    if (mixedSeed.getSeason() != App.getCurrentGame().getTimeAndDate().getSeason()) {
                        return new Result(false, "This mixed season cannot be planted in this season.");
                    }
                }
            }
        }

        targetTile.setType(TileType.SEED);
        targetTile.setMaterial(seed);
        return new Result(true, seedName + " planted!");
    }

    public Result showPlant(Matcher matcher) {
        Map map = App.getCurrentGame().getMainMap();
        int x = 0, y = 0;
        Player player = App.getCurrentGame().getActivePlayer();
        try {
            x = Integer.parseInt(matcher.group("X"));
            y = Integer.parseInt(matcher.group("Y"));
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
        if (player.getFarm().getRectangle().contains(x, y)) {
            return new Result(false, "You do not have access to another farm.");
        }
        Tile tile = map.getMainMap(x, y);
        if (tile.getType() == TileType.SEED) {
            Material material = tile.getMaterial();
            if (material instanceof Seed seed) {
                return new Result(true, seed.getPlantInfo());
            }
        }
        return new Result(false, "No seeds were found here.");

    }

    public static Result buildGreenhouse(Matcher matcher) {
        GreenHouse greenHouse = App.getCurrentGame().getActivePlayer().getFarm().getGreenhouse();
        if (greenHouse.isHasBeenMade()) {
            return new Result(false, "You have already made a greenhouse.");
        }
        Result result = App.getCurrentGame().getActivePlayer().getInventory()
                .removeElementFromBackpack(new ForagingMineral(ForagingMinerals.Wood), 1000);
        // + کم شدن پول + پول کم بود ارور مناسب
        if (result.Success()) {
            greenHouse.setHasBeenMade(true);
            FarmFactory.setTileTypeGreenHouseBuilt(TileType.GREENHOUSE_BUILT, greenHouse.getRectangle(),
                    App.getCurrentGame().getActivePlayer().getFarm());
            return new Result(true, "You have been made a greenhouse.");
        }
        return result;
    }

    private Result useTool(Matcher matcher) {
        Direction direction = Direction.fromString(matcher.group("direction").trim());
        return App.getCurrentGame().getActivePlayer().getInHand().work(direction);
    }

    public static Result buildStructure(Matcher matcher) {
        String buildingName = matcher.group("buildingname").trim();
        int x = 0, y = 0;
        try {
            x = Integer.parseInt(matcher.group("X"));
            y = Integer.parseInt(matcher.group("Y"));
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
        Point origin = new Point(x, y);
        Farm farm = App.getCurrentGame().getActivePlayer().getFarm();
        CoopsAndBarnsTypes type = CoopsAndBarnsTypes.fromName(buildingName);
        if (type == null)
            return new Result(false, "Invalid building type.");
        if (farm.getBarns().stream().anyMatch(barn -> barn.getType().equals(type)))
            return new Result(false, "You have already made this barn.");
        if (farm.getCoops().stream().anyMatch(coop -> coop.getType().equals(type))) {
            return new Result(false, "You have already made this coop.");
        }

        Dimension size = getOptimalDimension(type.getCapacity());
        Rectangle area = new Rectangle(origin.x, origin.y, size.width, size.height);

        if (!farm.getRectangle().contains(area))
            return new Result(false, "Out of farm bounds.");
        if (!isAreaEmpty(farm, area))
            return new Result(false, "Not enough free space.");

        // Player player = App.getCurrentGame().getActivePlayer();
        // if (player.getMoney() < type.getCost()) return new Result(false, "Not enough
        // money.");

        // player.setMoney(player.getMoney() - type.getCost());
        setStructure(farm, area, type);

        return new Result(true, buildingName + " built successfully at (" + origin.x + "," + origin.y + ")");
    }

    private static Result buyAnimal(Matcher matcher) {
        String animalName = matcher.group("animal").trim();
        String givenName = matcher.group("name").trim();
        Animals animalType;

        try {
            animalType = Animals.fromName(animalName);
        } catch (IllegalArgumentException e) {
            return new Result(false, "Invalid animal type: " + animalName);
        }

        Player player = App.getCurrentGame().getActivePlayer();
        Tile[][] map = App.getCurrentGame().getMainMap().getMainMap();
        Animal newAnimal = new Animal(givenName, animalType);
        boolean duplicateName = Stream.concat(
                player.getFarm().getCoops().stream()
                        .flatMap(c -> c.getAnimals().stream()),
                player.getFarm().getBarns().stream()
                        .flatMap(b -> b.getAnimals().stream()))
                .anyMatch(a -> a.getName().equals(givenName));

        if (duplicateName) {
            return new Result(false, "An animal with this name already exists.");
        }
        // Check Coops
        boolean addedToCoop = player.getFarm().getCoops().stream()
                .filter(coop -> coop.getCoopType() == animalType.getHousingType())
                .filter(Coop::hasSpace)
                .findFirst()
                .map(coop -> {
                    coop.addAnimal(newAnimal, map);
                    return true;
                })
                .orElse(false);

        if (addedToCoop) {
            return new Result(true, givenName + " the " + animalName + " was added to a Coop.");
        }

        // Check Barns
        boolean addedToBarn = player.getFarm().getBarns().stream()
                .filter(barn -> barn.getType() == animalType.getHousingType())
                .filter(Barn::hasSpace)
                .findFirst()
                .map(barn -> {
                    barn.addAnimal(newAnimal, map);
                    return true;
                })
                .orElse(false);

        if (addedToBarn) {
            return new Result(true, givenName + " the " + animalName + " was added to a Barn.");
        }

        return new Result(false, "No available housing with free space for this animal.");

    }

    private static Result petAnimalByName(Matcher matcher) {
        String name = matcher.group("name").trim();
        Player player = App.getCurrentGame().getActivePlayer();
        Point playerPos = player.getPlace();
        Tile[][] map = App.getCurrentGame().getMainMap().getMainMap();

        Optional<Animal> foundAnimal = Stream.concat(
                player.getFarm().getCoops().stream().flatMap(coop -> coop.getAnimals().stream()),
                player.getFarm().getBarns().stream().flatMap(barn -> barn.getAnimals().stream()))
                .filter(animal -> name.equals(animal.getName()))
                .findFirst();

        if (foundAnimal.isEmpty()) {
            return new Result(false, "No animal with this name found.");
        }

        Animal animal = foundAnimal.get();
        Point animalPos = animal.getLocation();
        if (isAdjacent(playerPos, animalPos)) {
            animal.getAnimalFriendship().pet();
            return new Result(true, "You petted " + animal.getName() + ". Friendship is now "
                    + animal.getAnimalFriendship().getFriendshipPoints());
        } else {
            return new Result(false, "You must be adjacent to the animal to pet it.");
        }
    }

    private static Result cheatSetFriendship(Matcher matcher) {
        String name = matcher.group("name").trim();
        int amount;
        try {
            amount = Integer.parseInt(matcher.group("amount"));
        } catch (NumberFormatException e) {
            return new Result(false, "Invalid amount");
        }

        Player player = App.getCurrentGame().getActivePlayer();

        return Stream.concat(
                player.getFarm().getCoops().stream()
                        .flatMap(coop -> coop.getAnimals().stream()),
                player.getFarm().getBarns().stream()
                        .flatMap(barn -> barn.getAnimals().stream()))
                .filter(animal -> animal.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(animal -> {
                    animal.getAnimalFriendship().setFriendshipPoints(amount);
                    return new Result(true, "Friendship set to " + amount + " for " + name);
                })
                .orElse(new Result(false, "No animal named " + name + " found."));
    }

    private static Result showAnimals() {
        Player player = App.getCurrentGame().getActivePlayer();

        List<String> infoList = Stream.concat(
                player.getFarm().getCoops().stream()
                        .flatMap(coop -> coop.getAnimals().stream()),
                player.getFarm().getBarns().stream()
                        .flatMap(barn -> barn.getAnimals().stream()))
                .map(animal -> {
                    AnimalFriendship f = animal.getAnimalFriendship();
                    Animals animals = (Animals) animal.getType();
                    return String.format("Name: %s | Type: %s | Friendship: %d | Petted: %b | Fed: %b | Outside: %b",
                            animal.getName(), animals.getName(), f.getFriendshipPoints(),
                            f.isWasPettedToday(), f.isWasFedToday(), f.isStayedOutsideTonight());
                })
                .toList();

        if (infoList.isEmpty()) {
            return new Result(false, "No animals found.");
        }

        infoList.forEach(System.out::println);
        return new Result(true, "Animals listed.");
    }

    private static boolean isAdjacent(Point a, Point b) {
        int dx = Math.abs(a.x - b.x);
        int dy = Math.abs(a.y - b.y);
        return dx <= 1 && dy <= 1 && !(dx == 0 && dy == 0);
    }

    private static Dimension getOptimalDimension(int capacity) {
        int bestWidth = 1, bestHeight = capacity, minDiff = Integer.MAX_VALUE;
        for (int w = 1; w <= capacity; w++) {
            int h = (int) Math.ceil((double) capacity / w);
            if (w * h >= capacity) {
                int diff = Math.abs(w - h);
                if (diff < minDiff) {
                    minDiff = diff;
                    bestWidth = w;
                    bestHeight = h;
                }
            }
        }
        return new Dimension(bestWidth, bestHeight);
    }

    private static boolean isAreaEmpty(Farm farm, Rectangle area) {
        Tile[][] map = farm.getMainMap();
        for (int i = area.x; i < area.x + area.width; i++) {
            for (int j = area.y; j < area.y + area.height; j++) {
                if (map[i][j].getType() != TileType.EMPTY)
                    return false;
            }
        }
        return true;
    }

    private static void setStructure(Farm farm, Rectangle area, CoopsAndBarnsTypes type) {
        Tile[][] map = farm.getMainMap();
        TileType tileType = type.isBarn() ? TileType.BARN : TileType.COOP;
        Material material = type.isBarn() ? new Barn(type) : new Coop(type);

        for (int i = area.x; i < area.x + area.width; i++) {
            for (int j = area.y; j < area.y + area.height; j++) {
                map[i][j].setType(tileType);
                map[i][j].setMaterial(material);
            }
        }

        if (type.isBarn()) {
            Barn barn = new Barn(type);
            barn.setArea(area);
            farm.getBarns().add(barn);
        } else {
            Coop coop = new Coop(type);
            coop.setArea(area);
            farm.getCoops().add(coop);
        }
    }

    public static Seed findCropBySeed(String seedName) {
        Seeds seedType = Seeds.getByName(seedName);
        if (seedType == null)
            return null;
        Seed seed = new Seed(seedType);
        return seed;

    }

    public Seeds getCorrespondingMixedSeasons(String season) {
        for (MixedSeedSeasons mixedSeed : MixedSeedSeasons.values()) {
            if (mixedSeed.getName().equals(season)) {
                return mixedSeed.getRandomSeed();
            }
        }
        return null;
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
            System.out.printf("F1 door at: (%d, %d)\n",
                    f1.getRectangle().x + f1.getRectangle().width - 1,
                    f1.getRectangle().y + f1.getRectangle().height / 2);
            map.getMainMap()[f1.getRectangle().x
                    + f1.getRectangle().width / 2][f1.getRectangle().y + f1.getRectangle().height - 1]
                    .setType(TileType.DOOR);
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
            map.getMainMap()[f2.getRectangle().x
                    + f2.getRectangle().width / 2][f2.getRectangle().y + f2.getRectangle().height - 1]
                    .setType(TileType.DOOR);
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
            map.getMainMap()[f3.getRectangle().x + f3.getRectangle().width / 2][f3.getRectangle().y]
                    .setType(TileType.DOOR);

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
            map.getMainMap()[f4.getRectangle().x + f4.getRectangle().width / 2][f4.getRectangle().y]
                    .setType(TileType.DOOR);

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
            player.setEnergy(new Energy());
            player.getEnergy().setEnergyAmount(player.getEnergy().getMaxEnergy());
        }
    }

    public static boolean isEmptyFarm(Farm farm) {
        return farm.getMainMap() == null || Arrays.stream(farm.getMainMap())
                .flatMap(Arrays::stream)
                .allMatch(tile -> tile.getType() == TileType.EMPTY);
    }

}
