package model;

import java.awt.*;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // تولید چهار مپ مختلف
        Farm farm1 = generateFarm1();  // مپ پایه با یک دریاچه
        Farm farm2 = generateFarm2();  // مپ با دو دریاچه
        Farm farm3 = generateFarm3();  // مپ با طرح L شکل
        Farm farm4 = generateFarm4();  // مپ با طرح متقارن

        System.out.println("\n=== نمایش مپ‌های موجود ===");
        displayFourMaps(farm1, farm2, farm3, farm4);

        System.out.println("\n=== انتخاب مپ برای شروع بازی ===");
        System.out.println("1. مپ اول (طرح پایه با یک دریاچه)");
        System.out.println("2. مپ دوم (طرح با دو دریاچه)");
        System.out.println("3. مپ سوم (طرح L شکل)");
        System.out.println("4. مپ چهارم (طرح متقارن)");
        System.out.print("لطفاً عدد مربوط به مپ مورد نظر را انتخاب کنید (1-4): ");

        int choice = scanner.nextInt();
        Farm selectedFarm = switch(choice) {
            case 1 -> farm1;
            case 2 -> farm2;
            case 3 -> farm3;
            case 4 -> farm4;
            default -> {
                System.out.println("انتخاب نامعتبر! مپ اول به طور پیش‌فرض انتخاب شد.");
                yield farm1;
            }
        };

        startGame(selectedFarm);
        randomGenerateFarm(selectedFarm);
        Tile[][] map1 = selectedFarm.getMainMap();
        for (int y = 0; y < map1[0].length; y++) {
            // مپ 1
            for (Tile[] tiles : map1) {
                System.out.print(tiles[y].getType().getColor() + tiles[y].getType().getSymbol() + "\u001B[0m");
            }
            System.out.println();
        }
    }
    public static void randomGenerateFarm(Farm farm) {
        int randomNumberOfTree = ThreadLocalRandom.current().nextInt(10, 21);
        for (int i = 0; i < randomNumberOfTree; i++) {
//            int randomTypeOfTree = ThreadLocalRandom.current().nextInt(1, 4);
//            if (randomTypeOfTree == 1) {
//
//            }
//            int randomIndex = ThreadLocalRandom.current().nextInt(0, randomNumberOfTree);
            Point point = randomPoint(farm);
            farm.getMainMap()[point.x][point.y].setType(TileType.TREE);

        }
        int randomNumberOfStone = ThreadLocalRandom.current().nextInt(10, 21);
        for (int i = 0; i < randomNumberOfStone; i++) {
            Point point = randomPoint(farm);
            farm.getMainMap()[point.x][point.y].setType(TileType.STONE);
        }
        int randomNumberOfForaging = ThreadLocalRandom.current().nextInt(10, 21);
        for (int i = 0; i < randomNumberOfForaging; i++) {
            Point point = randomPoint(farm);
            farm.getMainMap()[point.x][point.y].setType(TileType.FORAGING);
        }
    }

    public static void displayFourMaps(Farm f1, Farm f2, Farm f3, Farm f4) {
        Tile[][] map1 = f1.getMainMap();
        Tile[][] map2 = f2.getMainMap();
        Tile[][] map3 = f3.getMainMap();
        Tile[][] map4 = f4.getMainMap();

        System.out.println("مپ 1\t\tمپ 2\t\tمپ 3\t\tمپ 4");
        System.out.println("--------------------------------------------------");

        for (int y = 0; y < map1[0].length; y++) {
            // مپ 1
            for (Tile[] tiles : map1) {
                System.out.print(tiles[y].getType().getColor() + tiles[y].getType().getSymbol() + "\u001B[0m");
            }
            System.out.print("\t");

            // مپ 2
            for (Tile[] tiles : map2) {
                System.out.print(tiles[y].getType().getColor() + tiles[y].getType().getSymbol() + "\u001B[0m");
            }
            System.out.print("\t");

            // مپ 3
            for (Tile[] tiles : map3) {
                System.out.print(tiles[y].getType().getColor() + tiles[y].getType().getSymbol() + "\u001B[0m");
            }
            System.out.print("\t");

            // مپ 4
            for (Tile[] tiles : map4) {
                System.out.print(tiles[y].getType().getColor() + tiles[y].getType().getSymbol() + "\u001B[0m");
            }
            System.out.println();
        }

        System.out.println("\nنمادها: C=کلبه, G=گلخانه, Q=معدن, L=دریاچه");
    }

    public static void startGame(Farm farm) {
        System.out.println("\nبازی با مپ انتخاب شده شروع شد!");
    }

    public static Farm generateFarm1() {
        Farm farm = new Farm();

        placeBuilding(farm, TileType.HOUSE, new Rectangle(0, 0, 7, 7));
        placeBuilding(farm, TileType.GREENHOUSE, new Rectangle(10, 10, 5, 6));
        placeBuilding(farm, TileType.QUARRY, new Rectangle(farm.getRectangle().width-6, 0, 4, 4));
        placeBuilding(farm, TileType.LAKE, new Rectangle(farm.getRectangle().width/3, farm.getRectangle().height-6, 5, 5));

        return farm;
    }

    public static Farm generateFarm2() {
        Farm farm = new Farm();

        placeBuilding(farm, TileType.HOUSE, new Rectangle(0, 0, 7, 7));  // Cottage
        placeBuilding(farm, TileType.GREENHOUSE, new Rectangle(farm.getRectangle().width-15, 0, 5, 6));
        placeBuilding(farm, TileType.QUARRY, new Rectangle(farm.getRectangle().width-6, 0, 4, 4));
        placeBuilding(farm, TileType.LAKE, new Rectangle(farm.getRectangle().width/3, farm.getRectangle().height-6, 5, 5));
        placeBuilding(farm, TileType.LAKE, new Rectangle(farm.getRectangle().width/3, farm.getRectangle().height/2, 4, 4));

        return farm;
    }

    public static Farm generateFarm3() {
        Farm farm = new Farm();

        placeBuilding(farm, TileType.HOUSE, new Rectangle(1, 1, 6, 6));
        placeBuilding(farm, TileType.GREENHOUSE, new Rectangle(15, 1, 5, 5));
        placeBuilding(farm, TileType.QUARRY, new Rectangle(1, 15, 5, 5));
        placeBuilding(farm, TileType.LAKE, new Rectangle(15, 15, 5, 5));

        placeBuilding(farm, TileType.LAKE, new Rectangle(8, 1, 5, 10));
        placeBuilding(farm, TileType.LAKE, new Rectangle(8, 15, 5, 5));

        return farm;
    }

    public static Farm generateFarm4() {
        Farm farm = new Farm();

        placeBuilding(farm, TileType.HOUSE, new Rectangle(10, 5, 6, 6));
        placeBuilding(farm, TileType.GREENHOUSE, new Rectangle(5, 5, 4, 4));
        placeBuilding(farm, TileType.GREENHOUSE, new Rectangle(17, 5, 4, 4));
        placeBuilding(farm, TileType.QUARRY, new Rectangle(5, 15, 4, 4));
        placeBuilding(farm, TileType.QUARRY, new Rectangle(17, 15, 4, 4));

        placeBuilding(farm, TileType.LAKE, new Rectangle(10, 10, 6, 6));

        return farm;
    }

    private static void placeBuilding(Farm farm, TileType type, Rectangle area) {
        setTileType(type, area, farm);

        switch (type) {
            case HOUSE:
                Cottage cottage = new Cottage();
                cottage.setRectangle(area);
                farm.setCottage(cottage);
                break;
            case GREENHOUSE:
                GreenHouse greenhouse = new GreenHouse();
                greenhouse.setRectangle(area);
                farm.setGreenhouse(greenhouse);
                break;
            case QUARRY:
                Quarry quarry = new Quarry();
                quarry.setRectangle(area);
                farm.getQuarryInFarm().add(quarry);
                break;
            case LAKE:
                Lake lake = new Lake();
                lake.setRectangle(area);
                farm.getLakeInFarm().add(lake);
                break;
        }
    }

    private static void setTileType(TileType type, Rectangle rectangle, Farm farm) {
        Tile[][] tiles = farm.getMainMap();
        for (int i = rectangle.x; i < rectangle.x + rectangle.width; i++) {
            for (int j = rectangle.y; j < rectangle.y + rectangle.height; j++) {
                tiles[i][j].setType(type);
            }
        }
    }
    public static Point randomPoint(Farm farm) {
        int x = 0;
        int y =0;
        do {
            x = ThreadLocalRandom.current().nextInt(55);
            y = ThreadLocalRandom.current().nextInt(35);
        } while (farm.getMainMap()[x][y].getType() != TileType.EMPTY);
        return new Point(x, y);
    }
}