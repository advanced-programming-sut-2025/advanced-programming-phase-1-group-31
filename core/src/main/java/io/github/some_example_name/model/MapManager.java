package io.github.some_example_name.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.StreamSupport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.model.enums.foragings.ForagingCrops;
import io.github.some_example_name.model.enums.foragings.ForagingMinerals;
import io.github.some_example_name.model.enums.foragings.ForagingTrees;
import io.github.some_example_name.model.materials.Foraging.ForagingCrop;
import io.github.some_example_name.model.materials.Foraging.ForagingMineral;
import io.github.some_example_name.model.materials.Foraging.ForagingTree;
import io.github.some_example_name.model.materials.MaterialType;

public class MapManager {
    private final TmxMapLoader loader = new TmxMapLoader();
    private final HashMap<String, FarmMap> mapCache = new HashMap<>();
//    private boolean merged = false;

    public FarmMap getMap(String fileName) {
        if (mapCache.containsKey(fileName)) {
            return mapCache.get(fileName);
        }

        TiledMap tiledMap = loader.load(fileName);
//
//        if (fileName.equals("bigmap.tmx") && !merged) {
//            TiledMap smallMap = loader.load("untitled.tmx");
//            mergeMapIntoBigMap(map, smallMap);
//            merged = true;
//        }
        FarmMap map = new FarmMap();

        map.setTmxMap(tiledMap);
        mapCache.put(fileName, map);
        return map;
    }

    public void createMap(HashMap<Integer, String> selectedMaps, int number) {

        TiledMap bigMapTile = loader.load("bigFarm.tmx");
        String selectedTmx;


        boolean isThisPlayer;
        int tileHeight = bigMapTile.getProperties().get("tileheight", Integer.class);
        int tileWidth = bigMapTile.getProperties().get("tilewidth", Integer.class);

        selectedTmx = selectedMaps.get(0).replace(".png", ".tmx");
        TiledMap smallMap = loader.load(selectedTmx);
        int offsetY = (bigMapTile.getProperties().get("height", Integer.class) -
            smallMap.getProperties().get("height", Integer.class)) * tileHeight;
        int offsetX = 0;
        isThisPlayer = number == 0;
        mergeMapIntoBigMap(bigMapTile, smallMap, offsetX, offsetY, isThisPlayer);
        FarmMap map0 = new FarmMap();
        map0.setTmxMap(smallMap);
        mapCache.put(selectedMaps.get(0), map0);
        selectedTmx = selectedMaps.get(1).replace(".png", ".tmx");
        smallMap = loader.load(selectedTmx);
        offsetX = (bigMapTile.getProperties().get("width", Integer.class) - smallMap.getProperties().get("width", Integer.class)) * tileWidth;
        isThisPlayer = number == 1;
        mergeMapIntoBigMap(bigMapTile, smallMap, offsetX, offsetY, isThisPlayer);
        FarmMap map1 = new FarmMap();
        map1.setTmxMap(smallMap);
        mapCache.put(selectedMaps.get(1), map1);
        if (selectedMaps.size() > 2) {
            selectedTmx = selectedMaps.get(2).replace(".png", ".tmx");
            smallMap = loader.load(selectedTmx);
            offsetX = 0;
            offsetY = 0;
            isThisPlayer = number == 2;
            mergeMapIntoBigMap(bigMapTile, smallMap, offsetX, offsetY, isThisPlayer);
            FarmMap map2 = new FarmMap();
            map2.setTmxMap(smallMap);
            mapCache.put(selectedMaps.get(2), map2);
        }
        if (selectedMaps.size() > 3) {
            selectedTmx = selectedMaps.get(3).replace(".png", ".tmx");
            smallMap = loader.load(selectedTmx);
            offsetX = (bigMapTile.getProperties().get("width", Integer.class) - smallMap.getProperties().get("width", Integer.class)) * tileWidth;
            isThisPlayer = number == 3;
            mergeMapIntoBigMap(bigMapTile, smallMap, offsetX, offsetY, isThisPlayer);
            FarmMap map3 = new FarmMap();
            map3.setTmxMap(smallMap);
            mapCache.put(selectedMaps.get(3), map3);
        }
        FarmMap bigMap = new FarmMap();
        bigMap.setTmxMap(bigMapTile);
        randomGenerateMap(bigMap);

        randomGenerateMine();
        GameApp.player.setId(number);
        mapCache.put("bigFarm.tmx", bigMap);
    }


    private void mergeMapIntoBigMap(TiledMap bigMap, TiledMap smallMap, int offsetX, int offsetY, boolean isThisPlayer) {
        int tileHeight = bigMap.getProperties().get("tileheight", Integer.class);
        int tileWidth = bigMap.getProperties().get("tilewidth", Integer.class);


        // پیدا کردن آخرین لایه‌ی تایل‌دار
        TiledMapTileLayer targetLayer = null;
        for (int i = bigMap.getLayers().getCount() - 1; i >= 0; i--) {
            if (bigMap.getLayers().get(i) instanceof TiledMapTileLayer) {
                targetLayer = (TiledMapTileLayer) bigMap.getLayers().get(i);
                break;
            }
        }
        if (targetLayer == null) return;


        // کپی‌کردن تایل‌های smallMap در موقعیت مناسب به targetLayer
//        player.setCharacterPlacer(new CharacterPlacer(bigMap));
//        player.setPlace(new Vector2(640 + offsetX, 520+ offsetY));
//        player.setPlayerRectangle(new  Rectangle(640 + offsetX, 520+ offsetY , 20 , 20)) ;
        cloneSmallMap(bigMap, smallMap, offsetY, tileHeight, offsetX, tileWidth, isThisPlayer);
    }

    private void cloneSmallMap(TiledMap bigMap, TiledMap smallMap, int offsetY, int tileHeight, int offsetX, int tileWidth, boolean isThisPlayer) {
        Farm farm = new Farm();
        for (MapLayer layer : smallMap.getLayers()) {
            if (layer instanceof TiledMapTileLayer) {
                TiledMapTileLayer sourceLayer = (TiledMapTileLayer) layer;

                // ساخت لایه جدید با همان تنظیمات
                TiledMapTileLayer newLayer = new TiledMapTileLayer(
                    sourceLayer.getWidth() + (offsetX / tileWidth),
                    sourceLayer.getHeight() + (offsetY / tileHeight),
                    sourceLayer.getTileWidth(),
                    sourceLayer.getTileHeight()
                );
                newLayer.setName(sourceLayer.getName());


                // کپی کردن سلول‌ها با offset
                for (int x = 0; x < sourceLayer.getWidth(); x++) {
                    for (int y = 0; y < sourceLayer.getHeight(); y++) {
                        Cell cell = sourceLayer.getCell(x, y);
                        if (cell != null && cell.getTile() != null) {
                            Cell newCell = new Cell();
                            newCell.setTile(cell.getTile());
                            newLayer.setCell(x + (offsetX / tileWidth), y + (offsetY / tileHeight), newCell);
                        }
                    }
                }
                if (newLayer.getName().equals("Buildings3")) {
                    farm.setBlockLayer(newLayer);
                }

                bigMap.getLayers().add(newLayer);
            } else {
                // object layers as before
                MapLayer newLayer = new MapLayer();
                newLayer.setName(layer.getName());

                for (MapObject obj : layer.getObjects()) {
                    MapObject copy = cloneMapObject(obj, offsetX, offsetY);
                    newLayer.getObjects().add(copy);
                }
                farm.setObjectLayer(newLayer);


                bigMap.getLayers().add(newLayer);
            }
        }
        if (isThisPlayer) {
            GameApp.player.setFarm(farm);

        }
    }

    private MapObject cloneMapObject(MapObject original, float offsetX, float offsetY) {
        MapObject copy;

        if (original instanceof RectangleMapObject) {
            Rectangle rect = ((RectangleMapObject) original).getRectangle();
            Rectangle newRect = new Rectangle(rect);
            newRect.x += offsetX;
            newRect.y += offsetY;
            copy = new RectangleMapObject(newRect.x, newRect.y, newRect.width, newRect.height);
        } else if (original instanceof EllipseMapObject) {
            Ellipse ellipse = ((EllipseMapObject) original).getEllipse();
            Ellipse newEllipse = new Ellipse(ellipse);
            newEllipse.x += offsetX;
            newEllipse.y += offsetY;
            copy = new EllipseMapObject(newEllipse.x, newEllipse.y, newEllipse.width, newEllipse.height);
        } else if (original instanceof CircleMapObject) {
            Circle circle = ((CircleMapObject) original).getCircle();
            Circle newCircle = new Circle(circle);
            newCircle.x += offsetX;
            newCircle.y += offsetY;
            copy = new CircleMapObject(newCircle.x, newCircle.y, newCircle.radius);
        } else if (original instanceof PolylineMapObject) {
            Polyline polyline = ((PolylineMapObject) original).getPolyline();
            float[] oldVertices = polyline.getTransformedVertices(); // یا getVertices() بسته به نیاز
            float[] newVertices = new float[oldVertices.length];
            for (int i = 0; i < oldVertices.length; i += 2) {
                newVertices[i] = oldVertices[i] + offsetX;
                newVertices[i + 1] = oldVertices[i + 1] + offsetY;
            }
            Polyline newPolyline = new Polyline(newVertices);
            copy = new PolylineMapObject(newPolyline);
        } else if (original instanceof PolygonMapObject) {
            Polygon polygon = ((PolygonMapObject) original).getPolygon();
            float[] oldVertices = polygon.getVertices(); // vertices نسبت به position هستن
            float[] newVertices = new float[oldVertices.length];
            System.arraycopy(oldVertices, 0, newVertices, 0, oldVertices.length);

            Polygon newPolygon = new Polygon(newVertices);
            newPolygon.setPosition(polygon.getX() + offsetX, polygon.getY() + offsetY);
            newPolygon.setRotation(polygon.getRotation());
            newPolygon.setScale(polygon.getScaleX(), polygon.getScaleY());


            copy = new PolygonMapObject(newPolygon);
        }
//        else if (original instanceof TiledMapTileMapObject) {
//            TiledMapTileMapObject tileObj = (TiledMapTileMapObject) original;
//            copy = new TiledMapTileMapObject(tileObj.getTile());
//            ((TiledMapTileMapObject) copy).setX(tileObj.getX() + offsetX);
//            ((TiledMapTileMapObject) copy).setY(tileObj.getY() + offsetY);
//            ((TiledMapTileMapObject) copy).setRotation(tileObj.getRotation());
//            ((TiledMapTileMapObject) copy).setScale(tileObj.getScaleX(), tileObj.getScaleY());
//            ((TiledMapTileMapObject) copy).setFlipHorizontally(tileObj.isFlipHorizontally());
//            ((TiledMapTileMapObject) copy).setFlipVertically(tileObj.isFlipVertically());
//        }
        else {
            // Default fallback: copy properties and offset x/y if available
            copy = new MapObject();
            Float y = original.getProperties().get("y", Float.class);
            Float x = original.getProperties().get("x", Float.class);
            if (y != null) {
                copy.getProperties().put("y", y + offsetY);
            }
            if (x != null) {
                copy.getProperties().put("x", x + offsetX);
            }
        }

        // Copy name and all properties
        copy.setName(original.getName());
        copy.getProperties().putAll(original.getProperties());

        return copy;
    }

    private void randomGenerateMine() {
        Player player = GameApp.player;
        FarmMap map = GameApp.getMapForPlayer(player, MapType.MINE);
        int mapWidth = map.getTmxMap().getProperties().get("width", Integer.class);
        int mapHeight = map.getTmxMap().getProperties().get("height", Integer.class);
        int tileWidth = map.getTmxMap().getProperties().get("tilewidth", Integer.class);
        int tileHeight = map.getTmxMap().getProperties().get("tileheight", Integer.class);

        // Create mine layer if it doesn't exist
        TiledMapTileLayer mineLayer;
        mineLayer = new TiledMapTileLayer(mapWidth, mapHeight, tileWidth, tileHeight);
        mineLayer.setName("mine");
        map.getTmxMap().getLayers().add(mineLayer);

        // Get plantable areas
        MapLayer objectLayer = map.getTmxMap().getLayers().get("Object");
        if (objectLayer == null) {
            return; // Skip if no object layer
        }

        List<Polygon> plantablePolygons = new ArrayList<>();
        for (MapObject obj : objectLayer.getObjects()) {
            if (obj instanceof PolygonMapObject && "plantable".equals(obj.getName())) {
                plantablePolygons.add(((PolygonMapObject) obj).getPolygon());
            }
        }

        List<Vector2> validWorldPoints = new ArrayList<>();
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                float worldX = x * tileWidth + tileWidth / 2f;
                float worldY = y * tileHeight + tileHeight / 2f;

                for (Polygon p : plantablePolygons) {
                    if (p.contains(worldX, worldY)) {
                        validWorldPoints.add(new Vector2(worldX, worldY));
                        break;
                    }
                }
            }
        }

        if (!validWorldPoints.isEmpty()) {
            randomPlaceMaterial(map, validWorldPoints, ForagingMinerals.values(), tileWidth, tileHeight, "mine");
        }

    }

    public void randomGenerateMap(FarmMap map) {
        int mapWidth = map.getTmxMap().getProperties().get("width", Integer.class);
        int mapHeight = map.getTmxMap().getProperties().get("height", Integer.class);
        int tileWidth = map.getTmxMap().getProperties().get("tilewidth", Integer.class);
        int tileHeight = map.getTmxMap().getProperties().get("tileheight", Integer.class);

        TiledMapTileLayer craftLayer = new TiledMapTileLayer(mapWidth, mapHeight, tileWidth, tileHeight);
        craftLayer.setName("craft");
        map.getTmxMap().getLayers().add(craftLayer);
        Player player = GameApp.player;
        if (player == null || player.getFarm() == null) {
            return;
        }

        List<Polygon> plantablePolygons = StreamSupport.stream(player.getFarm().getAllObjects().spliterator(), false)
            .filter(Objects::nonNull)
            .filter(obj -> obj instanceof PolygonMapObject && "plantable".equals(obj.getName()))
            .map(obj -> ((PolygonMapObject) obj).getPolygon())
            .toList();

        List<Vector2> validWorldPoints = new ArrayList<>();
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                float worldX = x * tileWidth + tileWidth / 2f;
                float worldY = y * tileHeight + tileHeight / 2f;

                if (plantablePolygons.stream().anyMatch(p -> p.contains(worldX, worldY))) {
                    validWorldPoints.add(new Vector2(worldX, worldY));
//                    placeScaledImageAsTile(map, "craft", x, y, "soil.png");
                }
            }
        }

        randomPlaceMaterial(map, validWorldPoints, ForagingTrees.values(), tileWidth, tileHeight, "craft");
//        randomPlaceMaterial(map, validWorldPoints, ForagingMinerals.values(), tileWidth, tileHeight);
        randomPlaceMaterial(map, validWorldPoints, ForagingCrops.values(), tileWidth, tileHeight, "craft");

    }

    private <T extends Enum<T> & MaterialType> void randomPlaceMaterial(
        FarmMap map,
        List<Vector2> validWorldPoints,
        T[] types,
        int tileWidth,
        int tileHeight,
        String layerName
    ) {
        Random rand = new Random();
        int count = rand.nextInt(10, 21);


        for (int i = 0; i < count && !validWorldPoints.isEmpty(); i++) {
            Vector2 worldPoint = validWorldPoints.remove(rand.nextInt(validWorldPoints.size()));

            int tileX = (int) (worldPoint.x / tileWidth);
            int tileY = (int) (worldPoint.y / tileHeight);
//            System.out.println(worldPoint.x / tileWidth + " " + worldPoint.y / tileHeight);
//            System.out.println(tileX + " " + tileY);
            T type = randomEnum(types);
            if (type instanceof ForagingTrees foragingTrees) {
                placeScaledImageAsTile(map.getTmxMap(), layerName, tileX, tileY, foragingTrees.getImagePath());
                map.createAndAddTile(new Point(tileX, tileY), new ForagingTree(foragingTrees));
            } else if (type instanceof ForagingCrops foragingCrops) {
                placeScaledImageAsTile(map.getTmxMap(), layerName, tileX, tileY, foragingCrops.getImagePath());
                map.createAndAddTile(new Point(tileX, tileY), new ForagingCrop(foragingCrops));
            } else if (type instanceof ForagingMinerals foragingMinerals) {
                placeScaledImageAsTile(map.getTmxMap(), layerName, tileX, tileY, foragingMinerals.getImagePath());
                map.createAndAddTile(new Point(tileX, tileY), new ForagingMineral(foragingMinerals));
            }
        }
    }


    private static <T> T randomEnum(T[] values) {
        return values[ThreadLocalRandom.current().nextInt(values.length)];
    }

    public void placeScaledImageAsTile(TiledMap map, String layerName, int tileX, int tileY, String imagePath) {
        int tileSize = 16; // یا از map.getProperties() بگیر

        // مرحله 1: بارگذاری و کوچک کردن تصویر
        Pixmap pixmap = new Pixmap(Gdx.files.internal(imagePath));
        Pixmap resized = new Pixmap(tileSize, tileSize, pixmap.getFormat());
        resized.drawPixmap(pixmap,
            0, 0, pixmap.getWidth(), pixmap.getHeight(), // from full image
            0, 0, tileSize, tileSize                    // resize to tile size
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

    public void disposeAll() {
        for (FarmMap map : mapCache.values()) {
            map.getTmxMap().dispose();
        }
        mapCache.clear();
    }
}
