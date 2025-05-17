package controller;

import model.*;
import model.Map;
import model.enums.npc.Shops;
import model.enums.toolTypes.FishingPoleType;
import model.materials.Tools.*;
import model.enums.creature.Animals;
import model.enums.creature.FishTypes;
import model.enums.foragings.ForagingCrops;
import model.enums.foragings.ForagingMinerals;
import model.enums.foragings.ForagingTrees;
import model.enums.general.*;
import model.enums.plantable.*;
import model.enums.toolTypes.ToolTypes;
import model.enums.toolTypes.TrashCanType;
import model.materials.*;
import model.materials.Foraging.ForagingMineral;
import model.materials.Products.AnimalProduct;
import model.materials.Animal;
import model.enums.commands.GameMenuCommand;
import model.enums.creature.CoopsAndBarnsTypes;
import model.materials.Products.FishProducts;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameMenuController {
    public Result run(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = GameMenuCommand.GAME_NEW_PATTERN.getMatcher(input)) != null) {
            return gameNew(matcher);
        } else if ((matcher = GameMenuCommand.PRINT_MAP.getMatcher(input)) != null) {
            return printMap(matcher);
        } else if ((matcher = GameMenuCommand.WALK.getMatcher(input)) != null) {
            return walk(matcher);
        } else if (GameMenuCommand.SHOW_ENERGY.getMatcher(input) != null) {
            return showEnergy();
        } else if ((matcher = GameMenuCommand.CHEAT_CHANGE_ENERGY.getMatcher(input)) != null) {
            return cheatChangeEnergy(matcher);
        } else if (GameMenuCommand.UNLIMITED_ENERGY.getMatcher(input) != null) {
            return unlimitedEnergy();
        } else if ((matcher = GameMenuCommand.PLANT_SEED.getMatcher(input)) != null) {
            return handlePlantCommand(matcher);
        } else if ((matcher = GameMenuCommand.SHOW_PLANT.getMatcher(input)) != null) {
            return showPlant(matcher);
        } else if (GameMenuCommand.BUILD_GREENHOUSE.getMatcher(input) != null) {
            return buildGreenhouse();
        } else if ((matcher = GameMenuCommand.BUILD_BARN.getMatcher(input)) != null) {
            return buildStructure(matcher);
        } else if ((matcher = GameMenuCommand.BUY_ANIMAL.getMatcher(input)) != null) {
            return buyAnimal(matcher);
        } else if ((matcher = GameMenuCommand.PET_ANIMAL.getMatcher(input)) != null) {
            return petAnimalByName(matcher);
        } else if (GameMenuCommand.SHOW_ANIMALS.getMatcher(input) != null) {
            return showAnimals();
        } else if ((matcher = GameMenuCommand.CHEAT_SET_FRIENDSHIP.getMatcher(input)) != null) {
            return cheatSetFriendship(matcher);
        } else if ((matcher = GameMenuCommand.SHEPHERD_ANIMALS.getMatcher(input)) != null) {
            return shepherdAnimal(matcher);
        } else if ((matcher = GameMenuCommand.FEED_HAY.getMatcher(input)) != null) {
            return feedHayToAnimal(matcher);
        } else if (GameMenuCommand.SHOW_PRODUCES.getMatcher(input) != null) {
            listUncollectedProducts();
        } else if ((matcher = GameMenuCommand.COLLECT_PRODUCE.getMatcher(input)) != null) {
            return collectProduct(matcher);
        } else if ((matcher = GameMenuCommand.SELL_ANIMAL.getMatcher(input)) != null) {
            return sellAnimal(matcher);
        } else if ((matcher = GameMenuCommand.FISHING.getMatcher(input)) != null) {
            return fish(matcher);
        } else if (GameMenuCommand.CHANGE_TURN.getMatcher(input) != null) {
            return changeTurn();
        } else if ((matcher = GameMenuCommand.SELECT_ADD_DOLLARS.getMatcher(input)) != null) {
            return cheatAddDollars(matcher);
        } else if ((matcher = GameMenuCommand.SHOW_MONEY.getMatcher(input)) != null) {
            return showMoney();
        } else if (GameMenuCommand.SHOW_SEASON.getMatcher(input) != null)
            return showSeason();
        else if (GameMenuCommand.SHOW_DATE.getMatcher(input) != null)
            return showDate();
        else if (GameMenuCommand.SHOW_DATE_AND_TIME.getMatcher(input) != null)
            return showDateTime();
        else if (GameMenuCommand.SHOW_TIME.getMatcher(input) != null)
            return showTime();
        else if (GameMenuCommand.SHOW_WEATHER.getMatcher(input) != null)
            return showWeather();
        else if (GameMenuCommand.SHOW_WEEKDAY.getMatcher(input) != null)
            return showWeekday();
        else if ((matcher = GameMenuCommand.CHEAT_ADVANCE_TIME.getMatcher(input)) != null)
            return timeCheating(matcher);
        else if ((matcher = GameMenuCommand.CHEAT_ADVANCE_DATE.getMatcher(input)) != null)
            return dateCheating(matcher);
        else if ((matcher = GameMenuCommand.CHEAT_CREATE_THUNDER.getMatcher(input)) != null)
            return thunderCheating(matcher);
        else if (GameMenuCommand.FORECAST_WEATHER.getMatcher(input) != null)
            return forecastWeather();
        else if ((matcher = GameMenuCommand.CHEAT_CHANGE_WEATHER.getMatcher(input)) != null)
            return weatherCheating(matcher);
        else if (GameMenuCommand.SHOW_INVENTORY.getMatcher(input) != null)
            return showInventory();
        else if ((matcher = GameMenuCommand.EQUIP_TOOL.getMatcher(input)) != null)
            return equipTool(matcher);
        else if (GameMenuCommand.SHOW_CURRENT_TOOL.getMatcher(input) != null)
            return showCurrentTool();
        else if (GameMenuCommand.SHOW_AVAILABLE_TOOLS.getMatcher(input) != null)
            return showAvailableTools();
        else if ((matcher = GameMenuCommand.USE_TOOL.getMatcher(input)) != null)
            return useTool(matcher);
        else if ((matcher = GameMenuCommand.DISCARD_ITEM.getMatcher(input)) != null)
            return deleteFromBackpack(matcher);
        else if ((matcher = GameMenuCommand.CROP_INFO.getMatcher(input)) != null)
            return cropInfo(matcher);
        else if ((matcher = GameMenuCommand.FORAGING_CROP_INFO.getMatcher(input)) != null)
            return foragingCropInfo(matcher);
        else if ((matcher = GameMenuCommand.TREE_INFO.getMatcher(input)) != null)
            return treeInfo(matcher);
        else if ((matcher = GameMenuCommand.FORAGING_TREE_INFO.getMatcher(input)) != null)
            return foragingTreeInfo(matcher);
        else if ((matcher = GameMenuCommand.FRUITS_INFO.getMatcher(input)) != null)
            return fruitInfo(matcher);
        else if ((matcher = GameMenuCommand.SELECT_PRODUCTS_AVAILABLE.getMatcher(input)) != null) {
            return showAllAvailableProducts(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_PRODUCTS_ALL.getMatcher(input)) != null) {
            return showAllProducts(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_PURCHASE.getMatcher(input)) != null) {
            return purchaseProduct(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_SELL.getMatcher(input)) != null) {
            return sellProduct(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_FRIENDSHIPS.getMatcher(input)) != null) {
            return showFriendships(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_TALK.getMatcher(input)) != null) {
            return sendMessage(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_TALK_HISTORY.getMatcher(input)) != null) {
            return showTalkHistory(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_GIFT.getMatcher(input)) != null) {
            return sendGift(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_LIST_GIFT.getMatcher(input)) != null) {
            return listGifts(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_RATE_GIFT.getMatcher(input)) != null) {
            return rateGift(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_GIFT_HISTORY.getMatcher(input)) != null) {
            return showGiftHistory(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_HUG.getMatcher(input)) != null) {
            return sendHug(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_FLOWER.getMatcher(input)) != null) {
            return sendFlower(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_MARRIAGE_ASK.getMatcher(input)) != null) {
            return askForMarriage(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_MARRIAGE_RESPOND.getMatcher(input)) != null) {
            return respondToMarriage(matcher);
        } else if ((matcher = GameMenuCommand.SELECT_TRADE_START.getMatcher(input)) != null) {
            return startTrade(matcher);
        } else if (GameMenuCommand.SHOW_LEGEND.getMatcher(input) != null) {
            return getLegendAsResult();
        } else if (GameMenuCommand.SHOW_SKILL.getMatcher(input) != null) {
            return showSkill();
        }


        return new Result(false, "Invalid command.");
    }

    private Result showSkill() {
        Skill s = App.getCurrentGame().getActivePlayer().getSkills();
        String result = "Farming: " + s.getFarmingLevel() +
                "\nForaging: " + s.getForagingLevel() +
                "\nMining: " + s.getMiningLevel() +
                "\nFishing: " + s.getFishingLevel();
        return new Result(true, result);
    }

    public Result getLegendAsResult() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== Map Reading Help =====\n");
        for (TileType type : TileType.values()) {
            sb.append(type.getColor())
                    .append(" ")
                    .append(type.getSymbol())
                    .append(" \u001B[0m")
                    .append(" = ")
                    .append(type.name())
                    .append("\n");
        }
        return new Result(true, sb.toString());
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
        game.addFriendShip();
        return new Result(true, "Game started with " + matchedPlayers.size()
                + " new player(s)." + "\n" + "It's " + App.getCurrentGame().getActivePlayer().getUsername() + " turn.");
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
        int x, y, size;
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

        for (int j = startY; j < endY; j++) {
            for (int i = startX; i < endX; i++) {
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
                && map[destX][destY].getType() != TileType.PLANTING_SOIL))
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

        double energyLoss = path.size() / 10.0;
        player.getEnergy().changeEnergy(-energyLoss);

        return new Result(true, "Moved to: (" + dest.x + "," + dest.y + ")");
    }

    private boolean inBounds(Point p, Tile[][] map) {
        return p.x >= 0 && p.y >= 0 && p.x < map.length && p.y < map[0].length;
    }

    private List<Point> bfs(Point start, Point dest, Tile[][] map) {
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
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
                        || map[next.x][next.y].getType() != TileType.PLANTING_SOIL)) {
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

    public Result showEnergy() {
        if (App.getCurrentGame().getActivePlayer().getEnergy().getEnergyAmount() == Double.POSITIVE_INFINITY) {
            return new Result(true,
                    "your energy " + App.getCurrentGame().getActivePlayer().getEnergy().getEnergyAmount());
        } else {
            return new Result(false, "your energy "
                    + (int) Math.round(App.getCurrentGame().getActivePlayer().getEnergy().getEnergyAmount()));
        }
    }

    public Result cheatChangeEnergy(Matcher matcher) {
        double amountEnergy = 0.0;
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

    public Result unlimitedEnergy() {
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

        if (targetTile.getType() != TileType.PLANTING_SOIL && targetTile.getType() != TileType.GREENHOUSE_BUILT)
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
            if (!trees.getFruit().getHarvestSeasons().contains(App.getCurrentGame().getTimeAndDate().getSeason())) {
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
        int x, y;
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

    public Result buildGreenhouse() {
        GreenHouse greenHouse = App.getCurrentGame().getActivePlayer().getFarm().getGreenhouse();
        if (greenHouse.isHasBeenMade()) {
            return new Result(false, "You have already made a greenhouse.");
        }
        Result result = App.getCurrentGame().getActivePlayer().getInventory()
                .removeElementFromBackpack(new ForagingMineral(ForagingMinerals.Wood), 1000);
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

    public Result buildStructure(Matcher matcher) {
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

        return new Result(true, buildingName + " built successfully at (" +
                origin.x + "," + origin.y + ")");
    }

    private Result buyAnimal(Matcher matcher) {
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

        if (player.deductMoney(animalType.getPurchasePrice())) {
            return new Result(false, "not enough money.");
        }
        if (addedToBarn) {
            return new Result(true, givenName + " the " + animalName + " was added to a Barn.");
        }


        return new Result(false, "No available housing with free space for this animal.");

    }

    private Result petAnimalByName(Matcher matcher) {
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

    private Result cheatSetFriendship(Matcher matcher) {
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

    private Result showAnimals() {
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



    public Result shepherdAnimal(Matcher matcher) {
        Player player = App.getCurrentGame().getActivePlayer();
        Tile[][] map = App.getCurrentGame().getMainMap().getMainMap();
        String name = matcher.group("name").trim();
        int x = 0, y = 0;
        try {
            x = Integer.parseInt(matcher.group("X"));
            y = Integer.parseInt(matcher.group("Y"));
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
        Point destination = new Point(x, y);
        Optional<AnimalLocationContext> contextOpt = Stream.concat(
                        player.getFarm().getBarns().stream()
                                .flatMap(b -> b.getAnimals().stream().map(a -> new AnimalLocationContext(a, b))),
                        player.getFarm().getCoops().stream()
                                .flatMap(c -> c.getAnimals().stream().map(a -> new AnimalLocationContext(a, c))))
                .filter(ctx -> ctx.animal().getName().equals(name)).findFirst();

        if (contextOpt.isEmpty())
            return new Result(false, "Animal not found.");
        if (!player.getFarm().getRectangle().contains(destination)) {
            return new Result(false, "You cannot take your animal outside your farm.");
        }

        AnimalLocationContext ctx = contextOpt.get();
        Animal animal = ctx.animal();
        Material housing = ctx.housing();
        Point current = animal.getLocation();
        Tile currentTile = map[current.x][current.y];
        Tile destTile = map[destination.x][destination.y];
        Rectangle area = (housing instanceof Barn b) ? b.getArea() : ((Coop) housing).getArea();
        boolean isGoingInside = area.contains(destination);
        boolean isCurrentlyInside = area.contains(current);
        if (destination.x == current.x && destination.y == current.y) {
            return new Result(false, "You are already where you want to go.");
        }
        Weather currentWeather = App.getCurrentGame().getTimeAndDate().getWeather();
        if (EnumSet.of(Weather.Rainy, Weather.Snowy, Weather.Stormy).contains(currentWeather)) {
            return new Result(false, "You cannot take your animal outside in bad weather");
        }

        if (isGoingInside) {
            // returning to housing
            Animals animals = (Animals) animal.getType();
            boolean typeMatch = (housing instanceof Barn barn && barn.getType() == animals.getHousingType()) ||
                    (housing instanceof Coop coop && coop.getCoopType() == animals.getHousingType());
            if (!typeMatch)
                return new Result(false, "Invalid housing type.");

            TileType housingType = housing instanceof Barn ? TileType.BARN : TileType.COOP;
            if (destTile.getType() != housingType || destTile.getMaterial() != housing)
                return new Result(false, "Destination not empty.");

            if (!isCurrentlyInside) {
                currentTile.setType(TileType.EMPTY);
                currentTile.setMaterial(null);
            } else {
                // moving inside housing
                currentTile.setType(housingType);
                currentTile.setMaterial(housing);
            }
            destTile.setType(TileType.ANIMAL);
            destTile.setMaterial(animal);
            animal.setLocation(destination);
            animal.getAnimalFriendship().setStayedOutsideTonight(false);
            return new Result(true, name + " moved inside.");

        } else {
            // going outside
            if ((destTile.getType() != TileType.EMPTY || destTile.getMaterial() != null))
                return new Result(false, "Destination not empty.");

            TileType housingType = housing instanceof Barn ? TileType.BARN : TileType.COOP;

            if (isCurrentlyInside) {
                currentTile.setType(housingType);
                currentTile.setMaterial(housing);
            } else {
                currentTile.setType(TileType.EMPTY);
                currentTile.setMaterial(null);
            }

            destTile.setType(TileType.ANIMAL);
            destTile.setMaterial(animal);
            animal.setLocation(destination);
            animal.getAnimalFriendship().setStayedOutsideTonight(true);
            animal.getAnimalFriendship().setWasFedToday(true);
            return new Result(true, name + " moved outside.");
        }

    }

    private record AnimalLocationContext(Animal animal, Material housing) {
    }

    private Result feedHayToAnimal(Matcher matcher) {
        String animalName = matcher.group("name").trim();
        Player player = App.getCurrentGame().getActivePlayer();

        Optional<Animal> optionalAnimal = player.getFarm().getBarns().stream()
                .flatMap(barn -> barn.getAnimals().stream())
                .filter(animal -> animal.getName().equalsIgnoreCase(animalName))
                .findFirst();

        if (optionalAnimal.isEmpty()) {
            optionalAnimal = player.getFarm().getCoops().stream()
                    .flatMap(coop -> coop.getAnimals().stream())
                    .filter(animal -> animal.getName().equalsIgnoreCase(animalName))
                    .findFirst();
        }

        if (optionalAnimal.isEmpty()) {
            return new Result(false, "No animal with the name '" + animalName + "' found.");
        }

        Animal animal = optionalAnimal.get();
        if (animal.getAnimalFriendship().isWasFedToday()) {
            return new Result(false, animalName + " has already been fed today.");
        }
        animal.getAnimalFriendship().feed(false);
        // false: fed inside, but isOutside = true
        // return new Result(false, animalName + " must be outside the barn/coop to eat
        // hay.");
        return new Result(true, animalName + " was successfully fed with hay.");
    }

    public Result listUncollectedProducts() {
        Player player = App.getCurrentGame().getActivePlayer();
        List<Animal> animals = getAllAnimals(player);
        List<String> uncollected = new ArrayList<>();

        for (Animal animal : animals) {
            if (animal.hasProduct()) {
                AnimalProduct product = animal.getTodayProduct();
                uncollected.add("- " + animal.getName() + " => " +
                        product.getAnimalProducts().name() +
                        ", Quality: " + product.getQuality());
            }
        }

        if (uncollected.isEmpty()) {
            return new Result(false, "No uncollected products found.");
        } else {
            String message = "Uncollected products:\n" + String.join("\n", uncollected);
            return new Result(true, message);
        }
    }

    public Result collectProduct(Matcher matcher) {
        Player player = App.getCurrentGame().getActivePlayer();
        String animalName = matcher.group("name").trim();
        Optional<AnimalLocationContext> contextOpt = Stream.concat(
                        player.getFarm().getBarns().stream()
                                .flatMap(b -> b.getAnimals().stream().map(a -> new AnimalLocationContext(a, b))),
                        player.getFarm().getCoops().stream()
                                .flatMap(c -> c.getAnimals().stream().map(a -> new AnimalLocationContext(a, c))))
                .filter(ctx -> ctx.animal().getName().equals(animalName)).findFirst();

        if (contextOpt.isEmpty())
            return new Result(false, "Animal not found.");


        AnimalLocationContext ctx = contextOpt.get();
        Animal animal = ctx.animal();
        Material housing = ctx.housing();


        Animals type = animal.getAnimalType();

        Shear shear = (Shear) player.getInventory().isExistToolOrNull(new Shear());

        if (shear == null) return new Result(false, "You haven't Shear.");

        Rectangle area = (housing instanceof Barn b) ? b.getArea() : ((Coop) housing).getArea();
        if (type.needsToGoOutside() && area.contains(animal.getLocation())) {
            return new Result(false, "Pig must be outside to collect truffle.");
        }

        if (!animal.hasProduct()) {
            return new Result(false, animalName + " has no product to collect.");
        }

        AnimalProduct collectedProduct = animal.collectProduct();
        animal.getAnimalFriendship().milkOrShear();
        Result result = player.getInventory().addElementToBackpack(collectedProduct, collectedProduct.getQuantity());
        if (!result.Success()) {
            return result;
        }
        return new Result(true,
                "Collected " + collectedProduct.getName() +
                        " from " + animalName + " (Quality: " + collectedProduct.getQuality() + ")");
    }

    private Result sellAnimal(Matcher matcher) {
        String animalName = matcher.group("name").trim();
        Player player = App.getCurrentGame().getActivePlayer();
        Tile[][] map = player.getFarm().getMainMap();

        List<Animal> allAnimals = player.getFarm().getBarns().stream()
                .flatMap(b -> b.getAnimals().stream())
                .filter(a -> a.getName().equalsIgnoreCase(animalName))
                .toList();

        if (allAnimals.isEmpty()) {
            allAnimals = player.getFarm().getCoops().stream()
                    .flatMap(c -> c.getAnimals().stream())
                    .filter(a -> a.getName().equalsIgnoreCase(animalName))
                    .toList();
        }

        if (allAnimals.isEmpty()) {
            return new Result(false, "No animal with name '" + animalName + "' found.");
        }

        Animal animal = allAnimals.get(0);
        double multiplier = (animal.getAnimalFriendship().getFriendshipPercentage()) + 0.3;
        double price = (multiplier * animal.getAnimalType().getPurchasePrice());
        player.addMoney(price);
//        player.changeMoney(price);

        // Remove from its pen
        if (animal.getAnimalType().getHousingType().isBarn()) {
            player.getFarm().getBarns().forEach(b -> b.removeAnimalByName(animalName, map));
        } else {
            player.getFarm().getCoops().forEach(c -> c.removeAnimalByName(animalName, map));
        }
        return new Result(true, animalName + " sold for " + "g.");

//        return new Result(true, animalName + " sold for " + price + "g.");
    }

    public Result fish(Matcher matcher) {
        Player player = App.getCurrentGame().getActivePlayer();
        if (!isNearWater(player)) {
            return new Result(false, "You need to be near water to fish!");
        }
//        String pole = matcher.group("pole").trim();
        FishingPole fishingPole = (FishingPole) player.getInventory().isExistToolOrNull(new FishingPole(FishingPoleType.Training));
        if (fishingPole == null) return new Result(false, "You can't fishing because you haven't pole.");
        double poleMultiplier = ProductQualityCalculator.getPoleMultiplier(fishingPole.getFishingPoleType());
        Weather weather = App.getCurrentGame().getTimeAndDate().getWeather();
        Seasons season = App.getCurrentGame().getTimeAndDate().getSeason();
        Random random = new Random();
        int skill = player.getSkills().getFishingLevel();
        double M = ProductQualityCalculator.getSeasonalMultiplier(weather);
        double R = ThreadLocalRandom.current().nextDouble(0, 1);


        int count = (int) Math.min(6, R * M * (skill + 2));

        List<FishTypes> validFish = Arrays.stream(FishTypes.values())
                .filter(f -> !f.isLegendary() && f.getSeason() == season)
                .collect(Collectors.toList());

        // اگر مهارت کافی باشد، ماهی‌های افسانه‌ای اضافه می‌شوند
        if (skill >= 4) {
            List<FishTypes> legendary = Arrays.stream(FishTypes.values())
                    .filter(f -> f.isLegendary() && f.getSeason() == season)
                    .toList();
            validFish.addAll(legendary);
        }

        if (validFish.isEmpty()) {
            return new Result(false, "No fish available to catch in this season!");
        }
//        int count = (int) Math.min(6, Math.ceil((skill + 2) * M * Math.ceil(R * 7 - M)));

        List<FishProducts> caughtFish = new ArrayList<>();
        StringBuilder fishDetails = new StringBuilder();

        for (int i = 0; i < count; i++) {
            FishTypes selected = validFish.get(random.nextInt(validFish.size()));

            double qualityScore = random.nextDouble() * (skill + 2) * poleMultiplier / (7 - M);
            var quality = ProductQualityCalculator.calculateQualityScore(qualityScore);
            FishProducts fish = new FishProducts(selected, quality, 1);
            caughtFish.add(fish);
            fishDetails.append("- ").append(fish.getName())
                    .append(" | Quality: ").append(fish.getQuality())
                    .append("\n");
            Result result = App.getCurrentGame().getActivePlayer().getInventory().addElementToBackpack(fish, 1);
            if (!result.Success()) {
                String message = "Fishing successful! You caught:\n" + fishDetails.toString() + "but" +
                        result.Message();
                return new Result(true, message);
            }
        }

        if (caughtFish.isEmpty()) {
            return new Result(false, "You didn't catch any fish this time!");
        }

        player.getEnergy().changeEnergy((-1 * fishingPole.getFishingPoleType().getEnergyConsumption()));
        player.getSkills().setFishingLevel(40);
        String message = "Fishing successful! You caught:\n" + fishDetails.toString();
        return new Result(true, message);
    }

    public boolean isNearWater(Player player) {
        int FISHING_DISTANCE = 1;
        Point playerLocation = player.getPlace();
        Farm farm = player.getFarm();

        if (farm == null || farm.getLakeInFarm() == null) return false;

        for (Lake lake : farm.getLakeInFarm()) {
            Rectangle area = lake.getRectangle();
            Rectangle expanded = new Rectangle(
                    area.x - FISHING_DISTANCE,
                    area.y - FISHING_DISTANCE,
                    area.width + 2 * FISHING_DISTANCE,
                    area.height + 2 * FISHING_DISTANCE
            );

            if (expanded.contains(playerLocation)) {
                return true;
            }
        }

        return false;
    }

    private Result changeTurn() {
        App.getCurrentGame().changeTurn();
        String result = "You have changed the turn!\n" +
                "It's " + App.getCurrentGame().getActivePlayer().getUsername() + " turn";

        String result1 = showUnreadMessages(App.getCurrentGame().getActivePlayer());
        return new Result(true, result + "\n" + result1);
    }

    public Result cheatAddDollars(Matcher matcher) {
        String countStr = matcher.group("count");
        Player player = App.getCurrentGame().getActivePlayer();

        int count;
        try {
            count = Integer.parseInt(countStr);
            if (count < 0) {
                return new Result(false, "Amount must be positive.");
            }
        } catch (NumberFormatException e) {
            return new Result(false, "Invalid amount: " + countStr);
        }

        player.addMoney(count);
        return new Result(true, count + " gold added to your wallet. New balance: " + (int) player.getMoney());
    }
    public Result showMoney(){
        return new Result(true , "you have :" + (int)App.getCurrentGame().getActivePlayer().getMoney());
    }


    private static List<Animal> getAllAnimals(Player player) {
        List<Animal> animals = new ArrayList<>();
        player.getFarm().getCoops().forEach(coop -> animals.addAll(coop.getAnimals()));
        player.getFarm().getBarns().forEach(barn -> animals.addAll(barn.getAnimals()));
        return animals;
    }

    private boolean isAdjacent(Point a, Point b) {
        int dx = Math.abs(a.x - b.x);
        int dy = Math.abs(a.y - b.y);
        return dx <= 1 && dy <= 1 && !(dx == 0 && dy == 0);
    }

    private Dimension getOptimalDimension(int capacity) {
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

    private boolean isAreaEmpty(Farm farm, Rectangle area) {
        Tile[][] map = farm.getMainMap();
        for (int i = area.x; i < area.x + area.width; i++) {
            for (int j = area.y; j < area.y + area.height; j++) {
                if (map[i][j].getType() != TileType.EMPTY)
                    return false;
            }
        }
        return true;
    }

    private void setStructure(Farm farm, Rectangle area, CoopsAndBarnsTypes type) {
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
            assert material instanceof Barn;
            Barn barn = (Barn) material;
            barn.setArea(area);
            farm.getBarns().add(barn);
        } else {
            assert material instanceof Coop;
            Coop coop = (Coop) material;
            coop.setArea(area);
            farm.getCoops().add(coop);
        }
    }

    public Seed findCropBySeed(String seedName) {
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

    public void integrateFarmsIntoMainMap(Map map, Farm f1, Farm f2, Farm f3, Farm f4) {
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

    public void displayFourMaps() {
        Tile[][] map1 = FarmFactory.getPreset(1).getMainMap();
        Tile[][] map2 = FarmFactory.getPreset(2).getMainMap();
        Tile[][] map3 = FarmFactory.getPreset(3).getMainMap();
        Tile[][] map4 = FarmFactory.getPreset(4).getMainMap();

        System.out.println("map1\t\tmap2\t\tmap3\t\tmap4");
        System.out.println("--------------------------------------------------");

        for (int y = 0; y < map1[0].length; y++) {
            for (int x = 0; x < map1.length; x++) {
                System.out.print(
                        map1[x][y].getType().getColor() + " " + map1[x][y].getType().getSymbol() + " " + "\u001B[0m");
            }
            System.out.print("\t");

            for (int x = 0; x < map2.length; x++) {
                System.out.print(
                        map2[x][y].getType().getColor() + " " + map2[x][y].getType().getSymbol() + " " + "\u001B[0m");
            }
            System.out.print("\t");

            for (int x = 0; x < map3.length; x++) {
                System.out.print(
                        map3[x][y].getType().getColor() + " " + map3[x][y].getType().getSymbol() + " " + "\u001B[0m");
            }
            System.out.print("\t");

            for (int x = 0; x < map4.length; x++) {
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

    public boolean isEmptyFarm(Farm farm) {
        return farm.getMainMap() == null || Arrays.stream(farm.getMainMap())
                .flatMap(Arrays::stream)
                .allMatch(tile -> tile.getType() == TileType.EMPTY);
    }

    private Result cropInfo(Matcher matcher) {
        String name = matcher.group("name").trim();
        Crops crops = Crops.findByName(name);
        if (crops == null) return new Result(false, name + " doesn't exist");
        String result = "Name: " + crops.getDisplayName() + "\n" +
                "Source: " + crops.getSource().getName() + "\n" +
                "Stages: " + crops.getStages() + "\n" +
                "Total Harvest Time: " + crops.getTotalHarvestTime() + "\n" +
                "One Time: " + crops.isOneTime() + "\n" +
                "Regrowth Time: " + crops.getRegrowthTime() + "\n" +
                "Base Sell Price: " + crops.getBaseSellPrice() + "\n" +
                "Is Edible: " + crops.isEdible() + "\n" +
                "Base Energy: " + crops.getEnergy() + "\n" +
                "Season: " + crops.getSeasons() + "\n" +
                "Can Become Giant: " + crops.isCanBecomeGiant();
        return new Result(true, result);
    }

    private Result foragingCropInfo(Matcher matcher) {
        String name = matcher.group("name").trim();
        ForagingCrops crops = ForagingCrops.findByName(name);
        if (crops == null) return new Result(false, name + " doesn't exist");
        String result = "Name: " + crops.getDisplayName() + ":\n" +
                "Base Sell Price: " + crops.getBaseSellPrice() + "\n" +
                "Base Energy: " + crops.getEnergy() + "\n" +
                "Season: " + crops.getSeasons() + "\n";
        return new Result(true, result);
    }

    private Result treeInfo(Matcher matcher) {
        String name = matcher.group("name").trim();
        Trees trees = Trees.findByName(name);
        if (trees == null) return new Result(false, name + " doesn't exist");
        String result = "Name: " + trees.getName() + "\n" +
                "Source: " + trees.getSource().getName() + "\n" +
                "Stages: " + trees.getStages() + "\n" +
                "Total Harvest Time: " + trees.getTotalHarvestTime() + "\n" +
                "Fruit Name: " + trees.getFruit().getName() + "\n" +
                "Harvest Cycle: " + trees.getHarvestCycle() + "\n";
        return new Result(true, result);
    }

    private Result foragingTreeInfo(Matcher matcher) {
        String name = matcher.group("name").trim();
        ForagingTrees tree = ForagingTrees.findByName(name);
        if (tree == null) return new Result(false, name + " doesn't exist");
        String result = "Name: " + tree.getName() + ":\n" +
                "Season: " + tree.getSeasons() + "\n";
        return new Result(true, result);
    }

    private Result fruitInfo(Matcher matcher) {
        String name = matcher.group("name").trim();
        Fruits fruit = Fruits.findByName(name);
        if (fruit == null) return new Result(false, name + " doesn't exist");
        String result = "Name: " + fruit.getName() + "\n" +
                "Base Sell Price: " + fruit.getBaseSellPrice() + "\n" +
                "Is Edible: " + fruit.isEdible() + "\n" +
                "Base Energy: " + fruit.getEnergy() + "\n" +
                "Season: " + fruit.getHarvestSeasons() + "\n";
        return new Result(true, result);
    }

    private Result deleteFromBackpack(Matcher matcher) {
        String name = matcher.group("itemname").trim();
        int amount = -1;
        if (matcher.group("number") != null) {
            amount = Integer.parseInt(matcher.group("number").trim());
        }

        TrashCan trashCan = (TrashCan) App.getCurrentGame().getActivePlayer().getInventory()
                .isExistToolOrNull(new TrashCan(TrashCanType.Initial));
        return trashCan.work(name, amount);
    }

    private Result showAvailableTools() {
        return App.getCurrentGame().getActivePlayer().getInventory().showTools();
    }

    private Result showCurrentTool() {
        Tool tool = App.getCurrentGame().getActivePlayer().getInHand();
        if (tool == null) {
            return new Result(false, "You don't have anything in hand.");
        }
        return new Result(true, "You are now holding: " + tool.getName());
    }

    private Result equipTool(Matcher matcher) {
        String tool1 = matcher.group("toolname").trim();
        Tool tool = ToolTypes.fromString(tool1);
        Player player = App.getCurrentGame().getActivePlayer();
        tool = player.getInventory().isExistToolOrNull(tool);
        if (tool == null) {
            return new Result(false, "You haven't " + tool1);
        }
        player.setInHand(tool);
        return new Result(true, tool1 + " equipped successfully!");
    }

    private Result showInventory() {
        return App.getCurrentGame().getActivePlayer().getInventory().showBackPack();
    }

    private Result weatherCheating(Matcher matcher) {
        String weatherString = matcher.group("weather").trim();
        Weather weather;
        try {
            weather = Weather.valueOf(weatherString);
        } catch (IllegalArgumentException e) {
            return new Result(false,
                    "Invalid weather name. Use one of: Sunny, Rainy, Stormy, Snowy.");
        }
        App.getCurrentGame().getTimeAndDate().setTomorrowWeather(weather);
        return new Result(true, "Tomorrow weather has been manipulated...!");
    }

    private Result forecastWeather() {
        return new Result(true, "It will be \""
                + App.getCurrentGame().getTimeAndDate().getTomorrowWeather() + "\" tomorrow!");
    }

    private Result thunderCheating(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("X").trim());
        int y = Integer.parseInt(matcher.group("Y").trim());
        if (x < 0 || x >= 55 || y < 0 || y >= 35) {
            return new Result(false, "It is out of range.");
        }
        App.getCurrentGame().getTimeAndDate().thunder(new Point(x, y), App.getCurrentGame().getActivePlayer().
                getFarm().getMainMap());
        return new Result(true, "Thunder...!");
    }

    private Result dateCheating(Matcher matcher) {
        int number = Integer.parseInt(matcher.group("number").trim());
        if (number <= 0) {
            return new Result(false, "Please enter positive number");
        }
        App.getCurrentGame().getTimeAndDate().addDay(number);
        return showDate();
    }

    private Result timeCheating(Matcher matcher) {
        int number = Integer.parseInt(matcher.group("number").trim());
        if (number <= 0) {
            return new Result(false, "Please enter positive number");
        }
        App.getCurrentGame().getTimeAndDate().addHour(number);
        return showTime();
    }

    private Result showWeekday() {
        return new Result(true, App.getCurrentGame().getTimeAndDate().getDayOfWeek().name());
    }

    private Result showWeather() {
        return new Result(true, App.getCurrentGame().getTimeAndDate().
                getWeather().name());
    }

    private Result showTime() {
        TimeAndDate time = App.getCurrentGame().getTimeAndDate();
        if (time.getHour() >= 12) {
            return new Result(true, time.getHour() + ":00 PM");
        }
        return new Result(true, time.getHour() + ":00 AM");
    }

    private Result showDateTime() {
        TimeAndDate time = App.getCurrentGame().getTimeAndDate();
        if (time.getHour() >= 12) {
            return new Result(true, "Day " + time.getDay() + " of "
                    + time.getSeason() + ", " + time.getHour() + ":00 PM");
        }
        return new Result(true, "Day " + time.getDay() + " of "
                + time.getSeason() + ", " + time.getHour() + ":00 AM");
    }

    private Result showDate() {
        TimeAndDate time = App.getCurrentGame().getTimeAndDate();
        return new Result(true, "Day " + time.getDay() + " of " + time.getSeason());
    }

    private Result showSeason() {
        return new Result(true, App.getCurrentGame().getTimeAndDate().
                getSeason().name());
    }

    private Result sellProduct(Matcher matcher) {
        String productName = matcher.group("productName");
        int amount = -1;
        if (matcher.group("count") != null) {
            amount = Integer.parseInt(matcher.group("count").trim());
        }
        return App.getCurrentGame().getShoppingBin().work(productName, amount);
    }

    private Result sendMessage(Matcher matcher) {
        String username = matcher.group("username");
        String message = matcher.group("message");
        Player player1 = App.getCurrentGame().getActivePlayer();
        Player player2 = App.getCurrentGame().findPlayerByUsername(username);
        if (player2 == null) return new Result(false, "The player not found");
        Friendship friendship1 = friendshipWithPlayer(player1, username);
        Friendship friendship2 = friendshipWithPlayer(player2, player1.getUsername());
        if (!isNextToPlayer(player2)) return new Result(false, "You aren't next to the " + username);
        player1.addSMS(new SMS(message, true, player1.getUsername(), player2.getUsername(), false));
        player2.addSMS(new SMS(message, false, player1.getUsername(), player2.getUsername(), false));
        friendship1.addFriendshipLevel(20);
        friendship2.addFriendshipLevel(20);
        return new Result(true, "Message successfully sent.");
    }

    public boolean isNextToPlayer(Player player) {
        for (Direction dir : Direction.values()) {
            Point tilePoint = dir.apply(App.getCurrentGame().getActivePlayer().getPlace());
            if (tilePoint.x < 0 || tilePoint.y < 0 ||
                    tilePoint.x >= 140 || tilePoint.y >= 100) {
                continue;
            }
            Tile tile = App.getCurrentGame().getMainMap().getMainMap()[tilePoint.x][tilePoint.y];
            if (tile != null && player.getPlace().equals(tilePoint)) {
                return true;
            }
        }
        return false;
    }

    private Result showTalkHistory(Matcher matcher) {
        String username = matcher.group("username");
        return new Result(true, showConversationWith(App.getCurrentGame().getActivePlayer(), username));
    }

    private Result sendGift(Matcher matcher) {
        String username = matcher.group("username");
        String item = matcher.group("item");
        int amount = Integer.parseInt(matcher.group("amount"));
        Player player1 = App.getCurrentGame().getActivePlayer();
        Player player2 = App.getCurrentGame().findPlayerByUsername(username);
        if (player2 == null) return new Result(false, "The player not found");
        if (!isNextToPlayer(player2)) return new Result(false, "You aren't next to the " + username);
        if (friendshipWithPlayer(player1, player2.getUsername()).getFriendshipLevel() < 1) {
            return new Result(false, "Your friendship is not enough to give gifts.");
        }
        Material material = player1.getInventory().isExistInBackpackOrNull(item);
        Result result = player1.getInventory().removeElementFromBackpack(material, amount);
        if (!result.Success()) return result;
        Result result1 = player2.getInventory().addElementToBackpack(material, amount);
        if (!result1.Success()) {
            player1.getInventory().addElementToBackpack(material, amount);
            return new Result(false, "You can't gift to " + username);
        }
        Gift gift = new Gift(player1.getUsername(), player2.getUsername(), material, amount);
        player1.addGift(gift);
        player2.addGift(gift);
        return new Result(true, "Your gift successfully is gave to " + username);
    }

    private Result listGifts(Matcher matcher) {
        return new Result(true, showUnratedGifts(App.getCurrentGame().getActivePlayer()));
    }

    private Result rateGift(Matcher matcher) {
        int giftNumber = Integer.parseInt(matcher.group("giftNumber"));
        int rate = Integer.parseInt(matcher.group("rate"));
        if (rate < 1 || rate > 5) return new Result(false, "Please rate between 1-5");
        Player player = App.getCurrentGame().getActivePlayer();
        Gift gift = getGiftOrNull(player, giftNumber);
        if (gift == null) return new Result(false, "Gift not found with this number");
        Player player2 = App.getCurrentGame().findPlayerByUsername(gift.getSender());
        assert player2 != null;
        int friendXP = ((rate - 3) * 30) + 15;
        friendshipWithPlayer(player, player2.getUsername()).addFriendshipLevel(friendXP);
        friendshipWithPlayer(player2, player.getUsername()).addFriendshipLevel(friendXP);
        gift.setRate(rate);
        gift.setId(-1);
        return new Result(true, "The gift successfully has been rated");
    }

    private Result showGiftHistory(Matcher matcher) {
        String username = matcher.group("username");
        return new Result(true, showGiftsWith(App.getCurrentGame().getActivePlayer(), username));
    }

    private Result sendHug(Matcher matcher) {
        String username = matcher.group("username");
        Player player1 = App.getCurrentGame().getActivePlayer();
        Player player2 = App.getCurrentGame().findPlayerByUsername(username);
        if (player2 == null) return new Result(false, "Player not found");
        if (!isNextToPlayer(player2)) return new Result(false, "You aren't next to the " + username);
        if (friendshipWithPlayer(player1, player2.getUsername()).getFriendshipLevel() < 2) {
            return new Result(false, "Your friendship is not enough to hug each other.");
        }
        friendshipWithPlayer(player1, player2.getUsername()).addFriendshipLevel(60);
        friendshipWithPlayer(player2, player1.getUsername()).addFriendshipLevel(60);
        return new Result(true, "You hug each other!");
    }

    private Result showFriendships(Matcher matcher) {
        Player player = App.getCurrentGame().getActivePlayer();
        StringBuilder result = new StringBuilder();
        result.append("Show all friendships:");
        for (Friendship friendship : player.getFriendships()) {
            result.
                    append("\n").
                    append(friendship.getFriend().
                            getUsername()).
                    append(" --→ ").
                    append(friendship.getFriendshipLevel()).append("\n------");
        }
        return new Result(true, result.toString());
    }

    private Result sendFlower(Matcher matcher) {
        String username = matcher.group("username");
        Player player1 = App.getCurrentGame().getActivePlayer();
        Player player2 = App.getCurrentGame().findPlayerByUsername(username);
        if (player2 == null) return new Result(false, "Player not found");
        if (!isNextToPlayer(player2)) return new Result(false, "You aren't next to the " + username);
        Friendship friendship1 = friendshipWithPlayer(player1, username);
        if (friendship1.getFriendshipLevel() < 2 || friendship1.getFriendshipUnit() < 300)
            return new Result(false, "Your friendship isn't enough to send Flower");

        if (friendship1.hasFlower())
            return new Result(false, "You have already sent a Flower to this player.");

        Material material = player1.getInventory().isExistInBackpackOrNull("Bouquet");
        if (material == null) return new Result(false, "You haven't any Bouquet in your backpack");
        player1.getInventory().removeElementFromBackpack(material, 1);
        Result result1 = player2.getInventory().addElementToBackpack(material, 1);
        if (!result1.Success()) {
            player1.getInventory().addElementToBackpack(material, 1);
            return new Result(false, "You can't sent flower to " + username);
        }
        friendship1.setFlower(true);
        friendshipWithPlayer(player2, player1.getUsername()).setFlower(true);
        friendship1.addFriendshipLevel(1);
        friendshipWithPlayer(player2, player1.getUsername()).addFriendshipLevel(1);
        return new Result(true, "Your flower is successfully sent to " + username);

    }

    private Result askForMarriage(Matcher matcher) {
        String username = matcher.group("username");
        Player player1 = App.getCurrentGame().getActivePlayer();
        Player player2 = App.getCurrentGame().findPlayerByUsername(username);
        if (player2 == null) return new Result(false, "Player not found");
        if (!isNextToPlayer(player2)) return new Result(false, "You aren't next to the " + username);
        if (player1.getGender() == player2.getGender())
            return new Result(false, "You can't request to same gender");
        Friendship friendship1 = friendshipWithPlayer(player1, player2.getUsername());
        if (friendship1.getFriendshipLevel() < 3)
            return new Result(false, "Your friendship isn't enough to send Marriage request");

        Material material = player1.getInventory().isExistInBackpackOrNull("Ring");
        if (material == null) return new Result(false, "You haven't any Ring in your backpack");
        return new Result(true, "Your Marriage request is successfully sent to " + username);
    }

    private Result respondToMarriage(Matcher matcher) {
        String username = matcher.group("username");
        String respond = matcher.group("respond");
        Player player1 = App.getCurrentGame().getActivePlayer();
        Player player2 = App.getCurrentGame().findPlayerByUsername(username);

        if (player2 == null)
            return new Result(false, "Player not found.");

        if (!hasMarriageRequestFrom(player1, username))
            return new Result(false, "You have no marriage request from " + username + ".");

        if ("reject".equalsIgnoreCase(respond)) {
            friendshipWithPlayer(player1, username).addFriendshipLevel(-3000);
            friendshipWithPlayer(player2, player1.getUsername()).addFriendshipLevel(-3000);
            removeMarriageRequestFrom(player1, username);
            return new Result(true, "You rejected the marriage request from " + username + ".");
        } else if ("accept".equalsIgnoreCase(respond)) {
            Material ring = player2.getInventory().isExistInBackpackOrNull("Ring");
            if (ring == null)
                return new Result(false, username + " doesn't have any Ring.");

            player2.getInventory().removeElementFromBackpack(ring, 1);

            Result result = player1.getInventory().addElementToBackpack(ring, 1);
            if (!result.Success()) return result;

            friendshipWithPlayer(player1, username).addFriendshipLevel(3000);
            friendshipWithPlayer(player2, player1.getUsername()).addFriendshipLevel(3000);

//            player1.marry(player2);
            removeMarriageRequestFrom(player1, username);

            return new Result(true, "Congratulations! You are now married to " + username + ".");
        } else {
            return new Result(false, "Please respond with 'accept' or 'reject'.");
        }
    }

    public String showConversationWith(Player player, String otherName) {
        StringBuilder result = new StringBuilder();
        boolean hasAny = false;

        result.append("Conversation between You and ").append(otherName).append(":\n");

        for (SMS sms : App.getPlayerLoggedIn().getSMSs()) {
            boolean sentByActive = sms.getSender().equals(player.getUsername()) && sms.getReceiver().equals(otherName);
            boolean receivedByActive = sms.getReceiver().equals(player.getUsername()) && sms.getSender().equals(otherName);

            if (sentByActive || receivedByActive) {
                hasAny = true;

                String senderDisplay = sms.getSender().equals(player.getUsername()) ? "You" : sms.getSender();
                String receiverDisplay = sms.getReceiver().equals(player.getUsername()) ? "You" : sms.getReceiver();

                result.append("From: ").append(senderDisplay).append(" --→ ")
                        .append("To: ").append(receiverDisplay).append("\n")
                        .append("Message: ").append(sms.getMessage()).append("\n------------\n");

                if (!sms.isRead() && sms.getReceiver().equals(player.getUsername())) {
                    sms.setRead(true);
                }
            }
        }

        if (!hasAny) {
            return "You have no messages with " + otherName + ".";
        }

        return result.toString();
    }

    private String showUnreadMessages(Player player) {
        StringBuilder result = new StringBuilder();
        boolean hasUnread = false;

        result.append("Your Unread Messages:\n");

        for (SMS sms : App.getPlayerLoggedIn().getSMSs()) {
            if (!sms.isRead() && sms.getReceiver().equals(player.getUsername())) {
                hasUnread = true;

                String senderDisplay = sms.getSender().equals(player.getUsername()) ? "You" : sms.getSender();

                result.append("From: ").append(senderDisplay).append("\n")
                        .append("Message: ").append(sms.getMessage()).append("\n------------\n");

                sms.setRead(true);
            }
        }

        if (!hasUnread) {
            return "You don't have any unread messages.";
        }

        return result.toString();
    }

    public Friendship friendshipWithPlayer(Player player, String name) {
        for (Friendship friendship : player.getFriendships()) {
            if (friendship.getFriend().getUsername().equals(name)) return friendship;
        }
        return null;
    }

    public Gift getGiftOrNull(Player player, int Id) {
        for (Gift gift : player.getGifts()) {
            if (gift.getId() == Id) return gift;
        }
        return null;
    }

    public String showGiftsWith(Player player, String otherName) {
        StringBuilder result = new StringBuilder();
        boolean hasAny = false;

        result.append("Gifts between You and ").append(otherName).append(":\n");

        for (Gift gift : player.getGifts()) {
            boolean sentByYou = gift.getSender().equals(player.getUsername()) && gift.getReceiver().equals(otherName);
            boolean receivedByYou = gift.getReceiver().equals(player.getUsername()) && gift.getSender().
                    equals(otherName);

            if (sentByYou || receivedByYou) {
                hasAny = true;

                String senderDisplay = gift.getSender().equals(player.getUsername()) ? "You" : gift.getSender();
                String receiverDisplay = gift.getReceiver().equals(player.getUsername()) ? "You" : gift.getReceiver();

                result.append("From: ").append(senderDisplay).append(" --→ ")
                        .append("To: ").append(receiverDisplay).append("\n")
                        .append("Gift: ").append(gift.getAmount()).append(" of ").
                        append(gift.getMaterial().getName()).append("\n")
                        .append("Rate: ").append(gift.getRate()).append("/5\n------------\n");
            }
        }

        if (!hasAny) {
            return "You have no gift with " + otherName + ".";
        }

        return result.toString();
    }

    public String showUnratedGifts(Player player) {
        StringBuilder result = new StringBuilder();
        int tempId = 1;
        boolean hasUnrated = false;

        result.append("Your Unrated Gifts:\n");

        for (Gift gift : player.getGifts()) {
            if (gift.getRate() == 0 && gift.getReceiver().equals(player.getUsername())) {
                hasUnrated = true;

                result.append("Gift ID: ").append(tempId).append("\n")
                        .append("From: ").append(gift.getSender()).append("\n")
                        .append("Gift: ").append(gift.getAmount()).append(" of ").
                        append(gift.getMaterial().getName()).append("\n")
                        .append("------------\n");

                gift.setId(tempId);
                tempId++;
            }
        }

        if (!hasUnrated) {
            return "You don't have any unrated gifts.";
        }

        return result.toString();
    }

    public boolean hasMarriageRequestFrom(Player player, String username) {
        for (SMS sms : player.getSMSs()) {
            if (sms.isForMarriage() && sms.getSender().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void removeMarriageRequestFrom(Player player, String username) {
        player.getSMSs().removeIf(sms -> sms.isForMarriage() && sms.getSender().equals(username));
    }

    private Result showAllAvailableProducts(Matcher matcher) {
        Shop shop = (Shop) whichShopIsPlayer();
        if (shop == null) return new Result(false, "You aren't near a shop");
        StringBuilder result = new StringBuilder();
        result.append("All Available Products:");
        for (MaterialInShop material : shop.getShopName().getMaterials()){
            if (material.getSeasons() == null ||
                    material.getSeasons().equals(App.getCurrentGame().getTimeAndDate().getSeason())){
                result.
                        append("\n").
                        append("Name: ").append(material.getMaterial().getName()).
                        append("Price: ").append(material.getOrdinaryPrice()).
                        append("Daily limit: ").append(material.getDailyLimit()).
                        append("\n-------");
            }
            if (shop.getShopName().equals(Shops.PierreGeneralStore) &&
                    !material.getSeasons().equals(App.getCurrentGame().getTimeAndDate().getSeason())){
                result.
                        append("\n").
                        append("Name: ").append(material.getMaterial().getName()).
                        append("Price: ").append(material.getOutOfSeasonPrice()).
                        append("Daily limit: ").append(material.getDailyLimit()).
                        append("\n-------");
            }
        }

        return new Result(true, result.toString());
    }

    private Result showAllProducts(Matcher matcher) {
        Shop shop = (Shop) whichShopIsPlayer();
        if (shop == null) return new Result(false, "You aren't near a shop");
        StringBuilder result = new StringBuilder();
        result.append("All Products:");
        for (MaterialInShop material : shop.getShopName().getMaterials()){
            result.
                    append("\n").
                    append("Name: ").append(material.getMaterial().getName()).
                    append("Daily limit: ").append(material.getDailyLimit()).
                    append("Season: ").append(material.getSeasons()).
                    append("Price: ").append(material.getOrdinaryPrice()).
                    append("\n-------");
        }

        return new Result(true, result.toString());
    }

    private Result purchaseProduct(Matcher matcher) {
        String productName = matcher.group("productName");
        int amount = -1;
        if (matcher.group("number") != null) {
            amount = Integer.parseInt(matcher.group("number").trim());
        }
        Player player = App.getCurrentGame().getActivePlayer();
        Shop shop = (Shop) whichShopIsPlayer();
        if (shop == null) return new Result(false, "You aren't near a shop");
        MaterialInShop material = materialIsInTheShop(shop, productName);
        if (material == null) return new Result(false, "This item isn't in this shop.");
        if (amount > material.getDailyLimit()) return new Result(false, "Your amount is higher than daily limit");
        if (amount == -1){
            amount = material.getDailyLimit();
        }
        int total = 0;
        if (material.getSeasons() == null ||
                material.getSeasons().equals(App.getCurrentGame().getTimeAndDate().getSeason())){
            total += material.getOrdinaryPrice() * amount;
            if (total > player.getMoney()) return new Result(false, "You haven't enough money.");
        }
        else  {
            total += material.getOutOfSeasonPrice() * amount;
            if (total > player.getMoney()) return new Result(false, "You haven't enough money.");
        }
        player.deductMoney(total);
        if (material.getMaterial() instanceof Tool tool){
            boolean replaced = false;
            for (int i = 0; i < player.getInventory().getTools().size(); i++) {
                if (player.getInventory().getTools().get(i).getClass() == tool.getClass()) {
                    player.getInventory().getTools().set(i, tool);
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                player.getInventory().getTools().add(tool);
            }

            return new Result(true, "Your " + tool.getName() + " has been updated");
        } else {
            Result result = player.getInventory().addElementToBackpack(material.getMaterial(), amount);
            if (!result.Success()) return result;
            return new Result(true, "Purchased " + amount + " × " + material.getMaterial().getName());
        }
    }

    private Result startTrade(Matcher matcher) {
        App.setCurrentMenu(Menus.TradeMenu);

        return new Result(true, "Your are now in Trade menu.\n" + showUnreadTrades().Message());
    }

    public Result showUnreadTrades() {
        Player player = App.getCurrentGame().getActivePlayer();
        StringBuilder result = new StringBuilder();
        int tempId = 1;
        boolean hasUnread = false;

        result.append("Your Unread Trades:\n");

        for (Trade trade : player.getTradeHistory()) {
            if (!trade.isRead() && trade.getReceiver().getUsername().equals(player.getUsername())) {
                hasUnread = true;
                trade.setId(tempId);

                result.append("Trade ID: ").append(tempId).append("\n")
                        .append(trade.getSender().getUsername()).append(" --> ").append(trade.getReceiver().getUsername()).append("\n")
                        .append(trade.getMaterialToSell().getName()).append(" x ").append(trade.getAmountToSell());

                if (trade.getPrice() == null) {
                    result.append(" --> ").append(trade.getMaterialToReceive().getName()).append(" x ").append(trade.getAmountToReceive());
                } else {
                    result.append(" --> ").append(trade.getPrice()).append(" gold");
                }

                result.append("\n--------\n");
                tempId++;
            }
        }

        if (!hasUnread) {
            return new Result(false, "You don't have any unread trade.");
        }

        return new Result(true, result.toString());
    }

    public Material whichShopIsPlayer(){
        Player player = App.getCurrentGame().getActivePlayer();
        Tile tile = App.getCurrentGame().getMainMap().getMainMap()[player.getPlace().x][player.getPlace().y];
        if (tile.getType().equals(TileType.SHOP)){
            return tile.getMaterial();
        }
        return null;
    }

    public MaterialInShop materialIsInTheShop(Shop shop, String name){
        for (MaterialInShop material : shop.getShopName().getMaterials()){
            if (material.getMaterial().getName().equalsIgnoreCase(name)){
                return material;
            }
        }
        return null;
    }
}
