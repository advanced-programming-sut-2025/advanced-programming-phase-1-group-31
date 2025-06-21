package io.github.some_example_name;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;

import java.util.HashMap;

public class MapManager {
    private final TmxMapLoader loader = new TmxMapLoader();
    private final HashMap<String, TiledMap> mapCache = new HashMap<>();
    private boolean merged = false;

    public TiledMap getMap(String fileName) {
        if (mapCache.containsKey(fileName)) {
            return mapCache.get(fileName);
        }

        TiledMap map = loader.load(fileName);

        if (fileName.equals("bigmap.tmx") && !merged) {
            TiledMap smallMap = loader.load("untitled.tmx");
            mergeMapIntoBigMap(map, smallMap);
            merged = true;
        }

        mapCache.put(fileName, map);
        return map;
    }

    private void mergeMapIntoBigMap(TiledMap bigMap, TiledMap smallMap) {
        int tileHeight = bigMap.getProperties().get("tileheight", Integer.class);
        int offsetY = (bigMap.getProperties().get("height", Integer.class) -
            smallMap.getProperties().get("height", Integer.class)) * tileHeight;

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
        for (MapLayer layer : smallMap.getLayers()) {
            if (layer instanceof TiledMapTileLayer) {
                TiledMapTileLayer sourceLayer = (TiledMapTileLayer) layer;

                // ساخت لایه جدید با همان تنظیمات
                TiledMapTileLayer newLayer = new TiledMapTileLayer(
                    sourceLayer.getWidth(),
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
                            newLayer.setCell(x, y + (offsetY / tileHeight), newCell);
                        }
                    }
                }

                bigMap.getLayers().add(newLayer);
            } else {
                // object layers as before
                MapLayer newLayer = new MapLayer();
                newLayer.setName(layer.getName());

                for (MapObject obj : layer.getObjects()) {
                    MapObject copy = cloneMapObject(obj, offsetY);
                    newLayer.getObjects().add(copy);
                }

                bigMap.getLayers().add(newLayer);
            }
        }
    }

    private MapObject cloneMapObject(MapObject original, float offsetY) {
        MapObject copy;

        if (original instanceof RectangleMapObject) {
            Rectangle oldRect = ((RectangleMapObject) original).getRectangle();
            Rectangle newRect = new Rectangle(oldRect);
            newRect.y += offsetY;
            copy = new RectangleMapObject(newRect.x, newRect.y, newRect.width, newRect.height);
        } else {
            copy = new MapObject();
            Float y = original.getProperties().get("y", Float.class);
            if (y != null) {
                copy.getProperties().put("y", y + offsetY);
            }
        }

        copy.setName(original.getName());
        copy.getProperties().putAll(original.getProperties());
        return copy;
    }

    public void disposeAll() {
        for (TiledMap map : mapCache.values()) {
            map.dispose();
        }
        mapCache.clear();
    }
}
