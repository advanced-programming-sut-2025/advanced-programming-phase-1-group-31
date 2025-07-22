package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.model.enums.crafting.Industrials;
import io.github.some_example_name.model.enums.foragings.ForagingCrops;
import io.github.some_example_name.model.enums.foragings.ForagingMinerals;
import io.github.some_example_name.model.enums.general.Seasons;
import io.github.some_example_name.model.enums.general.TileType;
import io.github.some_example_name.model.enums.general.Weather;
import io.github.some_example_name.model.materials.*;
import io.github.some_example_name.model.materials.Foraging.ForagingCrop;
import io.github.some_example_name.model.materials.Foraging.ForagingMineral;
import io.github.some_example_name.model.materials.Foraging.ForagingTree;

import java.awt.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.StreamSupport;

//cheat code for changing weather must be added.

public class TimeAndDate {
    private final Random random = new Random();
    private int hour = 9;
    private int day = 1;
    private Seasons season = Seasons.Spring;
    private Weather weather = Weather.Sunny;
    private Weather tomorrowWeather = Weather.Sunny;

    private DayOfWeek dayOfWeek = DayOfWeek.SATURDAY;
    private ArrayList<ParticleEffect> rainEffect;

    private Texture hudTexture = new Texture("clock.png");;
    private BitmapFont hourFont = new BitmapFont();
    private BitmapFont dayFont = new BitmapFont();

    public void render(SpriteBatch batch) {
        // 1. رسم تصویر
        batch.draw(hudTexture, Gdx.graphics.getWidth() - 256 - 10, Gdx.graphics.getHeight() - 256 - 10, 250, 250); // تنظیم
                                                                                                                   // محل
                                                                                                                   // نمایش
                                                                                                                   // HUD

        String timeText = String.format("%02d", hour);
        String dayText = dayOfWeek.name();
        String seasonText = season.name();
        String weatherText = weather.name();

        hourFont.getData().setScale(1.1f);
        dayFont.getData().setScale(1.1f);
        hourFont.setColor(Color.BLACK);
        dayFont.setColor(Color.BLACK);

        // 3. رسم متن روی HUD
        hourFont.draw(batch, timeText + " " + seasonText + " " + weatherText, Gdx.graphics.getWidth() - 156 - 10,
                Gdx.graphics.getHeight() - 128 - 10); // ساعت
        dayFont.draw(batch, dayText + ". " + day, Gdx.graphics.getWidth() - 164, Gdx.graphics.getHeight() - 32 - 10); // روز
        // hourFont.draw(batch, seasonText, 700, 450); // فصل
    }

    public void renderEffect(SpriteBatch batch, float delta) {
        if (rainEffect == null)
            return;
        rainEffect.forEach(effect -> effect.update(delta));
        rainEffect.forEach(effect -> effect.draw(batch));
    }

    public ArrayList<ParticleEffect> loadEffectsForFullMap(TiledMap map, String effectName, int cols, int rows) {
        ArrayList<ParticleEffect> effects = new ArrayList<>();

        String path = "Particle-Park-master/particles/packs/" + effectName + "/";

        int mapWidth = map.getProperties().get("width", Integer.class);
        int mapHeight = map.getProperties().get("height", Integer.class);
        int tileWidth = map.getProperties().get("tilewidth", Integer.class);
        int tileHeight = map.getProperties().get("tileheight", Integer.class);

        float worldWidth = mapWidth * tileWidth;
        float worldHeight = mapHeight * tileHeight;

        float chunkWidth = worldWidth / cols;
        float chunkHeight = worldHeight / rows;

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                ParticleEffect effect = new ParticleEffect();
                effect.load(Gdx.files.internal(path + effectName + ".p"), Gdx.files.internal(path));
                effect.start();

                float centerX = col * chunkWidth + chunkWidth / 2f;
                float centerY = row * chunkHeight + chunkHeight / 2f;

                for (ParticleEmitter emitter : effect.getEmitters()) {
                    emitter.getSpawnWidth().setHigh(chunkWidth);
                    emitter.getSpawnHeight().setHigh(chunkHeight);
                    emitter.setPosition(centerX, centerY);
                }

                effects.add(effect);
            }
        }

        return effects;
    }

    public void dispose() {
        hudTexture.dispose();
        hourFont.dispose();
        dayFont.dispose();
    }

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
        Weather previousWeather = this.weather;
        weather = tomorrowWeather;
        if (!previousWeather.equals(this.weather)) {
            updateWeatherEffect();
        }

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
            // if (weather.equals(Weather.Stormy)) thunder();
            // App.getCurrentGame().getMainMap();
            updateAnimalOutdoorsStatus();
            changeForagingAndCrops(
                    App.getCurrentGame().getMapForPlayer(App.getCurrentGame().getActivePlayer(), MapType.FARM));
            App.getCurrentGame().getPlayers().forEach(player -> player.getEnergy().onNewDay());
            for (Player player : App.getCurrentGame().getPlayers()) {
                App.getCurrentGame().getShoppingBin().addMoney(player);
            }
        }

    }

    private void updateWeatherEffect() {
        // قطع و پاکسازی افکت قبلی
        if (rainEffect != null) {
            rainEffect.forEach(ParticleEffect::dispose);
            rainEffect.clear();
        }

        // انتخاب اسم مناسب با توجه به آب‌و‌هوا
        String effectName = weather.getEffectName();

        // اگر Sunny بود، افکتی لود نکن
        if (effectName != null) {
            TiledMap currentMap = App.getCurrentGame().getMainMap().getTmxMap(); // 👈 یا map مستقیم
            if (weather == Weather.Stormy) {
                rainEffect = loadEffectsForFullMap(currentMap, effectName, 10, 10);
            } else if (weather == Weather.Sunny) {
                rainEffect = loadEffectsForFullMap(currentMap, effectName, 0, 0);
            } else {
                rainEffect = loadEffectsForFullMap(currentMap, effectName, 4, 2);
            }

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
            tile.setType(TileType.Craftable);
            tile.setMaterial(new Industrial(Industrials.COAL));
        }

    }

    public void changeForagingAndCrops(FarmMap farmMap) {
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player == null || player.getFarm() == null)
                continue;

            generateForagingCrops(farmMap, player.getFarm().getAllObjects());
            generateForagingMinerals(player);
        }

    }

    private void generateForagingCrops(FarmMap map, Iterable<MapObject> mapObjects) {
        List<Vector2> validPoints = collectValidPoints(map, mapObjects, "craft");
        randomPlaceMaterial(map, validPoints, getTileSize(map));
    }

    private void generateForagingMinerals(Player player) {
        FarmMap mineMap = App.getCurrentGame().getMapForPlayer(player, MapType.MINE);
        List<Vector2> validPoints = collectValidPoints(mineMap,
                mineMap.getTmxMap().getLayers().get("Object").getObjects(), "mine");
        randomPlaceMineral(mineMap, validPoints, getTileSize(mineMap));
    }


    private List<Vector2> collectValidPoints(FarmMap map, Iterable<MapObject> objects, String layerName) {
        int width = getMapProperty(map, "width");
        int height = getMapProperty(map, "height");
        int tileWidth = getMapProperty(map, "tilewidth");
        int tileHeight = getMapProperty(map, "tileheight");

        List<Polygon> polygons = new ArrayList<>();
        for (MapObject obj : objects) {
            if (obj instanceof PolygonMapObject && "plantable".equals(obj.getName())) {
                polygons.add(((PolygonMapObject) obj).getPolygon());
            }
        }

        List<Vector2> points = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float worldX = x * tileWidth + tileWidth / 2f;
                float worldY = y * tileHeight + tileHeight / 2f;

                for (Polygon polygon : polygons) {
                    if (polygon.contains(worldX, worldY)) {
                        points.add(new Vector2(worldX, worldY));
                        break;
                    }
                }
            }
        }
        return points;

    }

    private void randomPlaceMineral(FarmMap map, List<Vector2> points, Point tileSize) {
        Random rand = new Random();
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getTmxMap().getLayers().get("mine");

        while (!points.isEmpty()) {
            Vector2 world = points.remove(rand.nextInt(points.size()));
            int x = (int) (world.x / tileSize.x);
            int y = (int) (world.y / tileSize.y);

            double chance = ThreadLocalRandom.current().nextDouble();
            if (chance <= 0.05 && layer.getCell(x, y) == null && map.getTileByPoint(x, y) == null
                && isEmptyTile(x, y)) {
                ForagingMinerals mineral = ForagingMinerals.getRandom();
                placeScaledImageAsTile(map.getTmxMap(), "mine", x, y, mineral.getImagePath());
                map.createAndAddTile(new Point(x, y), new ForagingMineral(mineral));
            }
        }
    }

    private void randomPlaceMaterial(FarmMap map, List<Vector2> points, Point tileSize) {
        Random rand = new Random();
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getTmxMap().getLayers().get("craft");
        Seasons season = App.getCurrentGame().getTimeAndDate().getSeason();

        while (!points.isEmpty()) {
            Vector2 world = points.remove(rand.nextInt(points.size()));
            int x = (int) (world.x / tileSize.x);
            int y = (int) (world.y / tileSize.y);

            double chance = ThreadLocalRandom.current().nextDouble();
            if (chance <= 0.05 && layer.getCell(x, y) == null && map.getTileByPoint(x, y) == null
                && isEmptyTile(x, y)) {
                ForagingCrops crop = ForagingCrops.getRandomBySeason(season);
                placeScaledImageAsTile(map.getTmxMap(), "craft", x, y, crop.getImagePath());
                map.createAndAddTile(new Point(x, y), new ForagingCrop(crop));
            }
        }
    }

    private boolean isEmptyTile(int tileX, int tileY) {
        return App.getCurrentGame().getPlayers().stream()
                .noneMatch(p -> p.getPlace().x == tileX || p.getPlace().y == tileY);
    }

    private int getMapProperty(FarmMap map, String key) {
        return map.getTmxMap().getProperties().get(key, Integer.class);
    }

    private Point getTileSize(FarmMap map) {
        return new Point(getMapProperty(map, "tilewidth"), getMapProperty(map, "tileheight"));
    }

    public void placeScaledImageAsTile(TiledMap map, String layerName, int tileX, int tileY, String imagePath) {
        int tileSize = 16; // یا از map.getProperties() بگیر

        // مرحله 1: بارگذاری و کوچک کردن تصویر
        Pixmap pixmap = new Pixmap(Gdx.files.internal(imagePath));
        Pixmap resized = new Pixmap(tileSize, tileSize, pixmap.getFormat());
        resized.drawPixmap(pixmap,
                0, 0, pixmap.getWidth(), pixmap.getHeight(), // from full image
                0, 0, tileSize, tileSize // resize to tile size
        );
        Texture texture = new Texture(resized);
        TextureRegion region = new TextureRegion(texture);

        // مرحله 2: تبدیل به تایل
        StaticTiledMapTile tile = new StaticTiledMapTile(region);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(tile);

        // مرحله 3: پیدا کردن لایه (یا ساختن)
        TiledMapTileLayer layer = null;
        MapLayer existingLayer = map.getLayers().get(layerName);
        if (existingLayer instanceof TiledMapTileLayer) {
            layer = (TiledMapTileLayer) existingLayer;
        } else {
            int width = map.getProperties().get("width", Integer.class);
            int height = map.getProperties().get("height", Integer.class);
            layer = new TiledMapTileLayer(width, height, tileSize, tileSize);
            layer.setName(layerName);
            map.getLayers().add(layer);
        }

        // مرحله 4: گذاشتن در مختصات مشخص
        layer.setCell(tileX, tileY, cell);

        // آزادسازی منابع
        pixmap.dispose();
        resized.dispose();
    }

    public static void updateAnimalOutdoorsStatus() {
        for (Coop coop : App.getCurrentGame().getPlayers().stream()
                .flatMap(player -> player.getFarm().getCoops().stream()).toList()) {
            for (Animal animal : coop.getAnimals()) {
                animal.generateProduct();
                animal.getAnimalFriendship().endDay();
            }
        }

        for (Barn barn : App.getCurrentGame().getPlayers().stream()
                .flatMap(player -> player.getFarm().getBarns().stream()).toList()) {
            for (Animal animal : barn.getAnimals()) {
                animal.generateProduct();
                animal.getAnimalFriendship().endDay();
            }
        }
    }

    public Texture getHudTexture() {
        return hudTexture;
    }

    public void setHudTexture(Texture hudTexture) {
        this.hudTexture = hudTexture;
    }

    public BitmapFont getFont() {
        return hourFont;
    }

    public void setFont(BitmapFont font) {
        this.hourFont = font;
    }

    public ArrayList<ParticleEffect> getRainEffect() {
        return rainEffect;
    }

    public void setRainEffect(ArrayList<ParticleEffect> rainEffect) {
        this.rainEffect = rainEffect;
    }

}
