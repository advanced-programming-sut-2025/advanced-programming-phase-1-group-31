package model;

import model.enums.foragings.ForagingMinerals;
import model.enums.general.Seasons;
import model.enums.general.TileType;
import model.enums.general.Weather;
import model.materials.Crop;
import model.materials.Material;
import model.materials.Seed;
import model.materials.Tree;
import model.materials.Foraging.ForagingMineral;
import model.materials.Foraging.ForagingTree;

import java.awt.*;
import java.time.DayOfWeek;
import java.util.Random;

//cheat code for changing weather must be added.

public class TimeAndDate {
    private final Random random = new Random();
    private int hour = 9;
    private int day = 1;
    private Seasons season = Seasons.Spring;
    private Weather weather = Weather.Sunny;
    private Weather tomorrowWeather = Weather.Sunny;
    private DayOfWeek dayOfWeek = DayOfWeek.SATURDAY;

    public void addHour(int addHour) {
        // this algorithm to keep hour between 9-22
        hour -= 8;
        hour += addHour;
        if (hour > 14) {
            int countOfDay = hour / 14;
            hour = (hour - 1) % 14 + 1;
            addDay(countOfDay);
        }
        hour += 8;
    }

    public void addDay(int addDay) {
        day += addDay;
        int countOfSeason = (day - 1) / 28;
        day = (day - 1) % 28 + 1;
        for (int i = 0; i < countOfSeason; i++) {
            changeSeason();
        }
        int countOfWeek = addDay % 7;
        for (int i = 0; i < countOfWeek; i++) {
            changeWeek();
        }

        weather = tomorrowWeather;

        if (season == Seasons.Winter) {
            tomorrowWeather = (random.nextInt(2) == 0) ? Weather.Sunny : Weather.Snowy;
        } else {
            tomorrowWeather = switch (random.nextInt(3)) {
                case 1 -> Weather.Rainy;
                case 2 -> Weather.Stormy;
                default -> Weather.Sunny;
            };
        }
        for (int i = 0; i < addDay; i++) {
            if (weather.equals(Weather.Stormy))
                thunder();
            App.getCurrentGame().getMainMap();
            changeForagingAndCrops();
            // amirabbas
        }
    }

    private void changeWeek() {
        switch (dayOfWeek) {
            case SATURDAY -> dayOfWeek = DayOfWeek.SUNDAY;
            case SUNDAY -> dayOfWeek = DayOfWeek.MONDAY;
            case MONDAY -> dayOfWeek = DayOfWeek.TUESDAY;
            case TUESDAY -> dayOfWeek = DayOfWeek.WEDNESDAY;
            case WEDNESDAY -> dayOfWeek = DayOfWeek.THURSDAY;
            case THURSDAY -> dayOfWeek = DayOfWeek.FRIDAY;
            case FRIDAY -> dayOfWeek = DayOfWeek.SATURDAY;
        }
    }

    private void changeSeason() {
        switch (season) {
            case Spring -> season = Seasons.Summer;
            case Summer -> season = Seasons.Fall;
            case Fall -> season = Seasons.Winter;
            case Winter -> season = Seasons.Spring;
        }
    }

    public void changeSeason(Seasons seasons) {
        this.season = seasons;
    }

    public void setTomorrowWeather(Weather tomorrowWeather) {
        this.tomorrowWeather = tomorrowWeather;
    }

    public Weather getTomorrowWeather() {
        return tomorrowWeather;
    }

    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }

    public Seasons getSeason() {
        return season;
    }

    public Weather getWeather() {
        return weather;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    private void thunder() {
        for (Player player : App.getCurrentGame().getPlayers()) {
            for (int i = 0; i < 3; i++) {
                int x = random.nextInt(55);
                int y = random.nextInt(35);
                thunder(new Point(x, y), player.getFarm().getMainMap());
            }
        }

    }

    public void thunder(Point point, Tile[][] farmMap) {
        Tile tile = farmMap[point.x][point.y];
        if (tile.getMaterial() instanceof Tree || tile.getMaterial() instanceof ForagingTree) {
            tile.setType(TileType.FORAGING_CROPS);
            tile.setMaterial(new ForagingMineral(ForagingMinerals.Coal));
        }

    }

    public void changeForagingAndCrops() {
        Map map = App.getCurrentGame().getMainMap(); 
        for (int i = 0; i < map.getMainMap().length; i++) {
            for (int j = 0; j < map.getMainMap()[i].length; j++) {
                Tile tile = map.getMainMap(i, j);
                if (tile.getType()==TileType.SEED) {
                    Material material = tile.getMaterial();
                      if (material instanceof Seed seed) {
                    seed.grow(); 
                    if (seed.getDaysWithoutWater() >= 2) {
                        tile.setType(TileType.EMPTY); 
                        tile.setMaterial(null); 
                    } else if (seed.isFullyGrown()) {
                        if (seed.getCorrespondingCrop() != null) {
                            tile.setType(TileType.CROPS);
                            tile.setMaterial(new Crop(seed.getCorrespondingCrop()));
                        } else if (seed.getCorrespondingTrees() != null) {
                            tile.setType(TileType.TREE);
                            tile.setMaterial(new Tree(seed.getCorrespondingTrees()));

                        }
                    }
                }
                // updateTile(tile);
            }
        }
    }
}
}