//package io.github.some_example_name.model;
//
//import com.badlogic.gdx.maps.tiled.*; import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile; import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile; import com.badlogic.gdx.utils.Array;
//
//public class CharacterPlacer { private final TiledMap map; TiledMap mapchar = new TmxMapLoader().load("characters.tmx"); private final TiledMapTileLayer characterLayer; private final int tileWidth; private final int tileHeight;
//
//    public CharacterPlacer(TiledMap map) {
//        this.map = map;
//
//        // گرفتن ابعاد کاشی از map
//        this.tileWidth = map.getProperties().get("tilewidth", Integer.class);
//        this.tileHeight = map.getProperties().get("tileheight", Integer.class);
//
//        // ایجاد لایه جدید با همان ابعاد
//        int mapWidth = map.getProperties().get("width", Integer.class);
//        int mapHeight = map.getProperties().get("height", Integer.class);
//        characterLayer = new TiledMapTileLayer(mapWidth, mapHeight, tileWidth, tileHeight);
//        characterLayer.setName("characters");
//
//        // اضافه کردن به map
//        map.getLayers().add(characterLayer);
//    }
//
//    // افزودن کاراکتر به مختصات مشخص
//    public void addCharacter(int tileX, int tileY, int tileIdFromTileset) {
//        TiledMapTile tile = getTileById(tileIdFromTileset);
//        if (tile == null) {
//            System.err.println("Tile ID not found in tileset.");
//            return;
//        }
//
//        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
//        cell.setTile(tile);
//        characterLayer.setCell(tileX, tileY, cell);
//    }
//
//    // افزودن کاراکتر انیمیشنی
//    public void addAnimatedCharacter(int tileX, int tileY, int... tileIds) {
//        Array<StaticTiledMapTile> frames = new Array<>();
//        for (int id : tileIds) {
//            TiledMapTile tile = getTileById(id);
//            if (tile instanceof StaticTiledMapTile) {
//                frames.add((StaticTiledMapTile) tile);
//            } else {
//                System.err.println("Tile ID " + id + " is not a StaticTiledMapTile.");
//            }
//        }
//
//        if (frames.size == 0) return;
//
//        AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(0.1f, frames);
//        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
//        cell.setTile(animatedTile);
//        characterLayer.setCell(tileX, tileY, cell);
//    }
//
//    // حرکت دادن کاراکتر از موقعیت قبلی به جدید
//    public void moveCharacter(int fromX, int fromY, int toX, int toY) {
//        TiledMapTileLayer.Cell cell = characterLayer.getCell(fromX, fromY);
//        if (cell == null) return;
//
//        characterLayer.setCell(fromX, fromY, null);
//        characterLayer.setCell(toX, toY, cell);
//    }
//
//    // گرفتن tile از ID
//    private TiledMapTile getTileById(int id) {
//        for (TiledMapTileSet set : mapchar.getTileSets()) {
//            TiledMapTile tile = set.getTile(id);
//            if (tile != null) return tile;
//        }
//        return null;
//    }
//
//}
package io.github.some_example_name.model;

import com.badlogic.gdx.maps.tiled.*; import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile; import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile; import com.badlogic.gdx.utils.Array;

public class CharacterPlacer { private final TiledMap map; private final TiledMapTileLayer characterLayer; private final int tileWidth; private final int tileHeight; private final TiledMap mapchar = new TmxMapLoader().load("character.tmx");

    public CharacterPlacer(TiledMap map) {
        this.map = map;
        this.tileWidth = map.getProperties().get("tilewidth", Integer.class);
        this.tileHeight = map.getProperties().get("tileheight", Integer.class);

        int mapWidth = map.getProperties().get("width", Integer.class);
        int mapHeight = map.getProperties().get("height", Integer.class);
        if (map.getLayers().get("characters") == null) {

            characterLayer = new TiledMapTileLayer(mapWidth, mapHeight, tileWidth, tileHeight);
            characterLayer.setName("characters");

            map.getLayers().add(characterLayer);
        } else {
            characterLayer = (TiledMapTileLayer) map.getLayers().get("characters");
        }

    }

    private TiledMapTile getTileById(int id ,String tileName) {

        for (TiledMapTileSet set : mapchar.getTileSets()) {

            TiledMapTile tile = set.getTile(id);
            if (tile != null && set.getName().equals(tileName)) return tile;
        }
        return null;
    }

    private AnimatedTiledMapTile makeAnimation(String tileName, int... ids) {
        Array<StaticTiledMapTile> frames = new Array<>();
        for (int id : ids) {

            TiledMapTile tile = getTileById(id , tileName );
            if (tile == null) {
                System.err.println("Tile " + id + " not found");
            }
            if (tile instanceof StaticTiledMapTile) {
                frames.add((StaticTiledMapTile) tile);
            } else {
                System.err.println("Tile ID " + id + " is not a StaticTiledMapTile.");
            }
        }
        if (tileName.equals("PlayerStatusList")){
            return new AnimatedTiledMapTile(0.1f, frames);
        }
        return new AnimatedTiledMapTile(0.4f, frames);
    }

    public void placeCharacter(int tileX, int tileY, Direction direction) {
        AnimatedTiledMapTile animTileUp = null;
        AnimatedTiledMapTile animTileDown = null;
        switch (direction) {
            case UP:
                animTileUp = makeAnimation("Shane",17, 18, 19 , 20);
                animTileDown = makeAnimation("Shane",21, 22, 23 , 24);
                break;
            case DOWN:
                animTileUp = makeAnimation("Shane",1, 2, 3 , 4);
                animTileDown = makeAnimation("Shane",5, 6, 7 , 8);
                break;
            case LEFT:
                animTileUp = makeAnimation("Shane",25, 26, 27 , 28);
                animTileDown = makeAnimation("Shane",29, 30, 31 , 32);
                break;
            case RIGHT:
                animTileUp = makeAnimation("Shane",9, 10, 11, 12);
                animTileDown = makeAnimation("Shane",13, 14, 15 , 16);
                break;

        }
        if (animTileUp == null) return;
        if (animTileDown == null) return;

        TiledMapTileLayer.Cell topCell = new TiledMapTileLayer.Cell();
        topCell.setTile(animTileUp);
        TiledMapTileLayer.Cell bottomCell = new TiledMapTileLayer.Cell();
        bottomCell.setTile(animTileDown);

        characterLayer.setCell(tileX, tileY + 1, topCell);
        characterLayer.setCell(tileX, tileY, bottomCell);
    }


    public void clearCharacter(int tileX, int tileY) {
        characterLayer.setCell(tileX, tileY, null);
        characterLayer.setCell(tileX, tileY + 1, null);
    }
    public void dontMove(int tileX, int tileY) {
        AnimatedTiledMapTile animTileUp = null;
        AnimatedTiledMapTile animTileDown = null;
        animTileUp = makeAnimation("Shane",73);
        animTileDown = makeAnimation( "Shane",77);
        if (animTileUp == null) return;
        if (animTileDown == null) return;

        TiledMapTileLayer.Cell topCell = new TiledMapTileLayer.Cell();
        topCell.setTile(animTileUp);
        TiledMapTileLayer.Cell bottomCell = new TiledMapTileLayer.Cell();
        bottomCell.setTile(animTileDown);

        characterLayer.setCell(tileX, tileY + 1, topCell);
        characterLayer.setCell(tileX, tileY, bottomCell);
    }
    public void faint(int tileX, int tileY) {
//        AnimatedTiledMapTile animTileUp;
        AnimatedTiledMapTile animTileDown;
        animTileDown = makeAnimation("PlayerStatusList",105, 109, 113 , 117);
//        animTileUp = makeAnimation("PlayerStatusList",105, 109, 113 , 117);
//        if (animTileUp == null) return;
        if (animTileDown == null) return;

//        TiledMapTileLayer.Cell topCell = new TiledMapTileLayer.Cell();
//        topCell.setTile(animTileUp);
        TiledMapTileLayer.Cell bottomCell = new TiledMapTileLayer.Cell();
        bottomCell.setTile(animTileDown);

//        characterLayer.setCell(tileX, tileY + 1, topCell);
        characterLayer.setCell(tileX, tileY, bottomCell);
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

}
