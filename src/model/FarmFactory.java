// File: factory/FarmFactory.java
package model;

import model.enums.foragings.ForagingCrops;
import model.enums.foragings.ForagingMinerals;
import model.enums.foragings.ForagingTrees;
import model.enums.general.TileType;
import model.materials.Foraging.ForagingCrop;
import model.materials.Foraging.ForagingMineral;
import model.materials.Foraging.ForagingTree;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class FarmFactory {

    public static Farm getPreset(int number) {
        return switch (number) {
            case 1 -> generateFarm1();
            case 2 -> generateFarm2();
            case 3 -> generateFarm3();
            case 4 -> generateFarm4();
            default -> throw new IllegalArgumentException("Invalid map number: " + number);
        };
    }

    public static Farm generateStors() {

        Farm marketFarm = new Farm(new Point(60, 20));
        addWall(marketFarm);
        int width = marketFarm.getMainMap().length;
        int height = marketFarm.getMainMap()[0].length;

        marketFarm.getMainMap()[width / 2][0].setType(TileType.DOOR);
        marketFarm.getMainMap()[width / 2][height - 1].setType(TileType.DOOR);
        marketFarm.getMainMap()[0][height / 2].setType(TileType.DOOR);
        marketFarm.getMainMap()[width - 1][height / 2].setType(TileType.DOOR);

        int storeCount = 7, npcCount = 5, trashCount = 4;

        for (int i = 0; i < storeCount; i++) {
            if (!tryPlace(marketFarm, TileType.SHOP, 6, 6)) {
                System.out.println("⚠ Could not place Store #" + i);
            }
        }

        for (int i = 0; i < npcCount; i++) {
            if (!tryPlace(marketFarm, TileType.NPC, 1, 1)) {
                System.out.println("⚠ Could not place NPC #" + i);
            }
        }

        for (int i = 0; i < trashCount; i++) {
            if (!tryPlace(marketFarm, TileType.TRASH_BIN, 4, 4)) {
                System.out.println("⚠ Could not place Trash Bin #" + i);
            }
        }
        return marketFarm;
    }

    private static Farm generateFarm1() {
        Farm farm = new Farm(new Point(55, 35));
        addWall(farm);
        placeBuilding(farm, TileType.HOUSE, new Rectangle(1, 1, 7, 7));
        placeBuilding(farm, TileType.GREENHOUSE_BROKEN, new Rectangle(10, 10, 6, 7));
        placeBuilding(farm, TileType.QUARRY, new Rectangle(farm.getRectangle().width - 6, 1, 4, 4));
        placeBuilding(farm, TileType.LAKE,
                new Rectangle(farm.getRectangle().width / 3, farm.getRectangle().height - 6, 5, 5));
        return farm;
    }

    private static Farm generateFarm2() {
        Farm farm = new Farm(new Point(55, 35));
        addWall(farm);
        placeBuilding(farm, TileType.HOUSE, new Rectangle(1, 1, 7, 7));
        placeBuilding(farm, TileType.GREENHOUSE_BROKEN, new Rectangle(farm.getRectangle().width - 15, 1, 6, 7));
        placeBuilding(farm, TileType.QUARRY, new Rectangle(farm.getRectangle().width - 6, 1, 4, 4));
        placeBuilding(farm, TileType.LAKE,
                new Rectangle(farm.getRectangle().width / 3, farm.getRectangle().height - 6, 5, 5));
        placeBuilding(farm, TileType.LAKE,
                new Rectangle(farm.getRectangle().width / 3, farm.getRectangle().height / 2, 4, 4));
        return farm;
    }

    private static Farm generateFarm3() {
        Farm farm = new Farm(new Point(55, 35));
        addWall(farm);
        placeBuilding(farm, TileType.HOUSE, new Rectangle(1, 1, 6, 6));
        placeBuilding(farm, TileType.GREENHOUSE_BROKEN, new Rectangle(15, 1, 6, 7));
        placeBuilding(farm, TileType.QUARRY, new Rectangle(1, 15, 5, 5));
        placeBuilding(farm, TileType.LAKE, new Rectangle(15, 15, 5, 5));
        placeBuilding(farm, TileType.LAKE, new Rectangle(8, 1, 5, 10));
        placeBuilding(farm, TileType.LAKE, new Rectangle(8, 15, 5, 5));
        return farm;
    }

    private static Farm generateFarm4() {
        Farm farm = new Farm(new Point(55, 35));
        addWall(farm);
        placeBuilding(farm, TileType.HOUSE, new Rectangle(10, 5, 6, 6));
        placeBuilding(farm, TileType.GREENHOUSE_BROKEN, new Rectangle(5, 5, 4, 4));
        placeBuilding(farm, TileType.GREENHOUSE_BROKEN, new Rectangle(17, 5, 4, 4));
        placeBuilding(farm, TileType.QUARRY, new Rectangle(5, 15, 4, 4));
        placeBuilding(farm, TileType.QUARRY, new Rectangle(17, 15, 4, 4));
        placeBuilding(farm, TileType.LAKE, new Rectangle(10, 10, 6, 6));
        return farm;
    }

    private static void placeBuilding(Farm farm, TileType type, Rectangle area) {
        setTileType(type, area, farm);
        switch (type) {
            case HOUSE -> {
                Cottage cottage = new Cottage();
                cottage.setRectangle(area);
                farm.setCottage(cottage);
            }
            case GREENHOUSE_BROKEN -> {
                setTileTypeGreenHouseBroken(type, area, farm);
                GreenHouse greenhouse = new GreenHouse();
                greenhouse.setRectangle(area);
                farm.setGreenhouse(greenhouse);
            }
            case QUARRY -> {
                Quarry quarry = new Quarry();
                quarry.setRectangle(area);
                farm.getQuarryInFarm().add(quarry);
            }
            case LAKE -> {
                Lake lake = new Lake();
                lake.setRectangle(area);
                farm.getLakeInFarm().add(lake);
            }
        }
    }

    public static void addWall(Farm farm) {
        int width = farm.getMainMap().length;
        int height = farm.getMainMap()[0].length;

        placeBuilding(farm, TileType.WALL, new Rectangle(0, 0, width, 1));
        placeBuilding(farm, TileType.WALL, new Rectangle(0, height - 1, width, 1));

        placeBuilding(farm, TileType.WALL, new Rectangle(0, 0, 1, height));
        placeBuilding(farm, TileType.WALL, new Rectangle(width - 1, 0, 1, height));
    }

    private static void setTileType(TileType type, Rectangle rectangle, Farm farm) {
        Tile[][] tiles = farm.getMainMap();
        for (int i = rectangle.x; i < rectangle.x + rectangle.width; i++) {
            for (int j = rectangle.y; j < rectangle.y + rectangle.height; j++) {
                tiles[i][j].setType(type);
            }
        }
    }

    private static void setTileTypeGreenHouseBroken(TileType type, Rectangle rectangle, Farm farm) {
        Tile[][] tiles = farm.getMainMap();
        for (int i = rectangle.x; i < rectangle.x + rectangle.width; i++) {
            for (int j = rectangle.y; j < rectangle.y + rectangle.height; j++) {
                tiles[i][j].setType(type);
            }
        }
        placeBuilding(farm, TileType.WALL, new Rectangle(rectangle.x, rectangle.y, rectangle.width, 1));
        placeBuilding(farm, TileType.WALL,
                new Rectangle(rectangle.x, rectangle.y + rectangle.height - 1, rectangle.width, 1));
        placeBuilding(farm, TileType.WALL, new Rectangle(rectangle.x, rectangle.y, 1, rectangle.height));
        placeBuilding(farm, TileType.WALL,
                new Rectangle(rectangle.x + rectangle.width - 1, rectangle.y, 1, rectangle.height));
    }

    public static void setTileTypeGreenHouseBuilt(TileType type, Rectangle greenhouseArea, Farm farm) {

        for (int i = greenhouseArea.x + 1; i < greenhouseArea.x + greenhouseArea.width - 1; i++) {
            for (int j = greenhouseArea.y + 1; j < greenhouseArea.y + greenhouseArea.height - 1; j++) {
                farm.getMainMap()[i][j].setType(TileType.GREENHOUSE_BUILT);
            }
        }

        placeBuilding(farm, TileType.WALL, new Rectangle(greenhouseArea.x, greenhouseArea.y, greenhouseArea.width, 1));
        placeBuilding(farm, TileType.WALL,
                new Rectangle(greenhouseArea.x, greenhouseArea.y + greenhouseArea.height - 1, greenhouseArea.width, 1));
        placeBuilding(farm, TileType.WALL, new Rectangle(greenhouseArea.x, greenhouseArea.y, 1, greenhouseArea.height));
        placeBuilding(farm, TileType.WALL,
                new Rectangle(greenhouseArea.x + greenhouseArea.width - 1, greenhouseArea.y, 1, greenhouseArea.height));

        int midY = greenhouseArea.y + (greenhouseArea.height / 2);
        farm.getMainMap()[greenhouseArea.x][midY].setType(TileType.LAKE);
    }

    public static void randomGenerateFarm(Farm farm) {

        ForagingTrees[] treeTypes = ForagingTrees.values();
        ForagingMinerals[] mineralTypes = ForagingMinerals.values();
        ForagingCrops[] cropTypes = ForagingCrops.values();
        int treeCount = ThreadLocalRandom.current().nextInt(10, 21);
        for (int i = 0; i < treeCount; i++) {
            Point point = randomPoint(farm);
            Tile tile = farm.getMainMap()[point.x][point.y];
            tile.setType(TileType.FORAGING_TREE);

            ForagingTrees type = randomEnum(treeTypes);

            tile.setMaterial(new ForagingTree(type));
        }

        int stoneCount = ThreadLocalRandom.current().nextInt(10, 21);
        for (int i = 0; i < stoneCount; i++) {
            Point point = randomPoint(farm);
            Tile tile = farm.getMainMap()[point.x][point.y];
            tile.setType(TileType.FORAGING_MINERAL);
            ForagingMinerals type = randomEnum(mineralTypes);
            tile.setMaterial(new ForagingMineral(type));

        }

        int cropCount = ThreadLocalRandom.current().nextInt(10, 21);
        for (int i = 0; i < cropCount; i++) {
            Point point = randomPoint(farm);
            Tile tile = farm.getMainMap()[point.x][point.y];
            tile.setType(TileType.FORAGING_CROPS);
            ForagingCrops type = randomEnum(cropTypes);
            tile.setMaterial(new ForagingCrop(type));
        }
    }

    private static <T> T randomEnum(T[] values) {
        return values[ThreadLocalRandom.current().nextInt(values.length)];
    }

    public static Point randomPoint(Farm farm) {
        int x = 0;
        int y = 0;
        do {
            x = ThreadLocalRandom.current().nextInt(55);
            y = ThreadLocalRandom.current().nextInt(35);
        } while (farm.getMainMap()[x][y].getType() != TileType.EMPTY);
        return new Point(x, y);
    }

    public static Farm getEmptyFarm() {
        Farm farm = new Farm(new Point(55, 35));
        Tile[][] map = new Tile[55][35];
        for (int x = 0; x < 55; x++) {
            for (int y = 0; y < 35; y++) {
                Tile tile = new Tile();
                tile.setType(TileType.EMPTY);
                map[x][y] = tile;
            }
        }
        farm.setMainMap(map);
        return farm;
    }

    public static boolean tryPlace(Farm farm, TileType type, int width, int height) {
        for (int attempt = 0; attempt < 100; attempt++) {
            int x = ThreadLocalRandom.current().nextInt(farm.getRectangle().width - width);
            int y = ThreadLocalRandom.current().nextInt(farm.getRectangle().height - height);
            boolean canPlace = true;

            for (int i = x; i < x + width && canPlace; i++) {
                for (int j = y; j < y + height; j++) {
                    if (farm.getMainMap()[i][j].getType() != TileType.EMPTY) {
                        canPlace = false;
                        break;
                    }
                }
            }

            if (canPlace) {
                for (int i = x; i < x + width; i++) {
                    for (int j = y; j < y + height; j++) {
                        farm.getMainMap()[i][j].setType(type);
                    }
                }
                return true;
            }
        }
        return false;
    }

}
