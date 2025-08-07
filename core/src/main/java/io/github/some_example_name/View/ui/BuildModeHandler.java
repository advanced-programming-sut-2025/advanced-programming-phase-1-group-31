package io.github.some_example_name.View.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Timer;
import io.github.some_example_name.View.CoopAndBarnView;
import io.github.some_example_name.View.FarmView;
import io.github.some_example_name.model.*;
import io.github.some_example_name.model.enums.creature.CoopsAndBarnsTypes;
import io.github.some_example_name.model.materials.Animal;
import io.github.some_example_name.model.materials.Barn;
import io.github.some_example_name.model.materials.Coop;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.some_example_name.model.materials.Material;


import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import static io.github.some_example_name.model.TimeAndDate.placeScaledImageAsTile;

public class BuildModeHandler {
    private Texture buildingPreview;
    private boolean isBuildMode = false;
    private TiledMap map;
    private int buildingWidth = 3;
    private int buildingHeight = 3;
    private int TILE_SIZE = 16;
    CoopsAndBarnsTypes coopsAndBarnsTypes;
    Animal animal;

    public void enableBuildMode(CoopsAndBarnsTypes coopsAndBarnsTypes ,Texture previewTexture, TiledMap map) {
        this.buildingPreview = previewTexture;
        this.map = map;
        isBuildMode = true;
        this.coopsAndBarnsTypes = coopsAndBarnsTypes;
    }
    public void enableBuildMode(Animal animal,Texture previewTexture, TiledMap map) {
        this.buildingPreview = previewTexture;
        this.map = map;
        isBuildMode = true;
        this.animal = animal;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        if (!isBuildMode) return;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        int tileX = (int) (mousePos.x / TILE_SIZE);
        int tileY = (int) (mousePos.y / TILE_SIZE);
        boolean isValid;
        if (coopsAndBarnsTypes == null){
            isValid = isBuildLocationValidForAnimal(tileX, tileY);
        }else {
            isValid = isBuildLocationValidForCoop(tileX, tileY);

        }


        // اگر جای درست بود، سبز؛ اگر غلط بود، قرمز
        if (isValid)
            batch.setColor(0, 1, 0, 0.5f); // سبز شفاف
        else
            batch.setColor(1, 0, 0, 0.5f); // قرمز شفاف


        if (coopsAndBarnsTypes == null){
            batch.draw(buildingPreview, tileX * TILE_SIZE, tileY * TILE_SIZE,
                 TILE_SIZE, TILE_SIZE);
        } else {
            batch.draw(buildingPreview, tileX * TILE_SIZE, tileY * TILE_SIZE,
                buildingWidth * TILE_SIZE, buildingHeight * TILE_SIZE);
        }

        batch.setColor(Color.WHITE); // بازگشت رنگ به حالت عادی

        if (Gdx.input.justTouched() && isValid) {
            if (coopsAndBarnsTypes == null) {
                placeBuildingForAnimal(tileX, tileY);
            } else {
                placeBuildingForCoop(tileX, tileY);
            }

            isBuildMode = false;
            App.getCurrentGame().getGameView().setMap(App.getCurrentGame().getMapForPlayer(App.getCurrentGame().getActivePlayer() , App.getCurrentGame().getActivePlayer().getCurrentMapType()));
            App.getCurrentGame().getGameView().setMapRenderer(new OrthogonalTiledMapRenderer(App.getCurrentGame().getGameView().getMap()));

        }
    }

    private boolean isBuildLocationValidForCoop(int tileX, int tileY) {
        FarmMap farmMap = App.getCurrentGame().getMapForPlayer(App.getCurrentGame().getActivePlayer(), MapType.FARM);
        Iterable<MapObject> objects = App.getCurrentGame().getActivePlayer().getFarm().getAllObjects();

        // همه نواحی plantable رو ذخیره کن
        ArrayList<Polygon> plantableAreas = new ArrayList<>();
        for (MapObject obj : objects) {
            if (obj instanceof PolygonMapObject && "plantable".equals(obj.getName())) {
                plantableAreas.add(((PolygonMapObject) obj).getPolygon());
            }
        }

        // برای هر تایل از ساختمون، بررسی کن
        for (int dx = 0; dx < buildingWidth; dx++) {
            for (int dy = 0; dy < buildingHeight; dy++) {
                int checkX = tileX + dx;
                int checkY = tileY + dy;

                float worldX = checkX * TILE_SIZE + TILE_SIZE / 2f;
                float worldY = checkY * TILE_SIZE + TILE_SIZE / 2f;

                // بررسی وجود Tile در این نقطه
                Tile tile = farmMap.getTileByPoint(checkX, checkY);
                if (tile != null) return false; // اگه تایل قبلاً گرفته شده

                boolean found = false;
                for (Polygon poly : plantableAreas) {
                    if (poly.contains(worldX, worldY)) {
                        found = true;
                        break;
                    }
                }

                if (!found) return false; // این تایل قابل ساخت نیست
            }
        }

        return true; // همه تایل‌ها معتبر بودن
    }
    private boolean isBuildLocationValidForAnimal(int tileX, int tileY) {

        TiledMapTileLayer layer = null;
        String layerName = "animal";
        int tileSize = TILE_SIZE;
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
        Vector2 tilePos = new Vector2(tileX, tileY);
        if (animal.getLocation().dst(tilePos) >= 10) {
            return false;
        }

        if (!animal.getAnimalFriendship().isStayedOutsideTonight()){
            if (layer.getCell(tileX, tileY) != null) {
                return false;
            }
        }
        return true;


    }
    private void placeBuildingForCoop(int tileX, int tileY) {
        String imagePath = coopsAndBarnsTypes.getImagePath();
        String layerName = "building";
        int tileSize = TILE_SIZE; // فرض: 32 یا 64

        // بارگذاری تکسچر اصلی
        Texture texture = new Texture(Gdx.files.internal(imagePath));

        // برش تصویر به تکه‌های tileSize×tileSize

        TextureRegion textureRegion = new TextureRegion(texture);
        textureRegion = resizeTo3x3(textureRegion);
        TextureRegion[][] regions = TextureRegion.split(textureRegion.getTexture(), tileSize, tileSize);
        int cols = textureRegion.getRegionWidth() / tileSize;
        int rows = textureRegion.getRegionHeight() / tileSize;

        // ایجاد یا دریافت لایه
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

        RectangleMapObject rectangleMapObject = new RectangleMapObject(
            tileX * tileSize,
            tileY * tileSize,
            cols * tileSize,
            rows * tileSize
        );
        rectangleMapObject.setName(coopsAndBarnsTypes.toString());
        rectangleMapObject.getProperties().put("targetMap", coopsAndBarnsTypes.toString() );
        if (coopsAndBarnsTypes.isBarn()){
            Barn barn = new Barn(coopsAndBarnsTypes);

            rectangleMapObject.getProperties().put("coopandbarn", barn);
            barn.setArea(rectangleMapObject.getRectangle());
            App.getCurrentGame().getActivePlayer().getFarm().getBarns().add(barn);
            RectangleMapObject rectangleMapObject2 = new RectangleMapObject(
                (tileX+cols -3) * tileSize,
                (tileY+rows -5) * tileSize,
                0,
                0
            );
            rectangleMapObject2.setName("out");
            barn.setOut(rectangleMapObject2);


            // گذاشتن تکه‌های برش‌خورده سر جای خودشون
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    int cellX = tileX + col;
                    int cellY = tileY + (rows - 1 - row); // چون Tiled بالا به پایینه

                    TextureRegion region = regions[row][col];
                    StaticTiledMapTile tile = new StaticTiledMapTile(region);
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    cell.setTile(tile);
                    App.getCurrentGame().getMapForPlayer(App.getCurrentGame().getActivePlayer() , MapType.FARM).createAndAddTile(new Point(cellX, cellY), barn);
                    layer.setCell(cellX, cellY, cell);
                }
            }
            App.getCurrentGame().getActivePlayer().getFarm().getAllObjects().add(rectangleMapObject);
        } else {
            Coop coop = new Coop(coopsAndBarnsTypes);
            rectangleMapObject.getProperties().put("coopandbarn", coop);
            coop.setArea(rectangleMapObject.getRectangle());
            App.getCurrentGame().getActivePlayer().getFarm().getCoops().add(coop);
            RectangleMapObject rectangleMapObject2 = new RectangleMapObject(
                (tileX+cols -3) * tileSize,
                (tileY+rows -5) * tileSize,
                0,
                0
            );
            rectangleMapObject2.setName("out");
            coop.setOut(rectangleMapObject2);


            // گذاشتن تکه‌های برش‌خورده سر جای خودشون
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    int cellX = tileX + col;
                    int cellY = tileY + (rows - 1 - row); // چون Tiled بالا به پایینه

                    TextureRegion region = regions[row][col];
                    StaticTiledMapTile tile = new StaticTiledMapTile(region);
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    cell.setTile(tile);
                    App.getCurrentGame().getMapForPlayer(App.getCurrentGame().getActivePlayer() , MapType.FARM).createAndAddTile(new Point(cellX, cellY), coop);
                    layer.setCell(cellX, cellY, cell);
                }
            }
            App.getCurrentGame().getActivePlayer().getFarm().getAllObjects().add(rectangleMapObject);
        }



    }
    private void placeBuildingForAnimal(int tileX, int tileY) {
        String layerName = "animal";
        int tileSize = TILE_SIZE;

        // دریافت یا ایجاد لایه حیوان
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

        // مختصات اولیه حیوان
        final int startX = (int) animal.getLocation().x;
        final int startY = (int) animal.getLocation().y;

        // تعداد گام‌های انیمیشن
        final int steps = 10;
        final float delay = 0.5f; // فاصله بین هر گام (ثانیه)
        final int dx = tileX - startX;
        final int dy = tileY - startY;

        final TextureRegion texture = new TextureRegion(new Texture(Gdx.files.internal(animal.getTexturePath())));

        TiledMapTileLayer finalLayer = layer;
        Timer.schedule(new Timer.Task() {
            int step = 0;
            int lastX = startX;
            int lastY = startY;

            @Override
            public void run() {
                // حذف تایل قبلی
                finalLayer.setCell(lastX, lastY, null);

                // محاسبه موقعیت جدید
                float progress = (float)(step + 1) / steps;
                int currentX = Math.round(startX + dx * progress);
                int currentY = Math.round(startY + dy * progress);

                // تنظیم تایل جدید
                placeScaledImageAsTile(map, layerName , currentX , currentY , animal.getTexturePath());

                // ذخیره موقعیت برای حذف در مرحله بعد
                lastX = currentX;
                lastY = currentY;

                step++;

                // پایان
                if (step >= steps) {
                    this.cancel();

                    // ذخیره موقعیت جدید در مدل حیوان
                    animal.setLocation(new Vector2(tileX, tileY));
                    Player player = App.getCurrentGame().getActivePlayer();
                    if (App.getCurrentGame().getGameView() instanceof CoopAndBarnView) {
                        RectangleMapObject rectangleMapObject = (RectangleMapObject) map.getLayers().get("Object").getObjects().get("out");
                        if (rectangleMapObject.getRectangle().contains(tileX*16, tileY*16)) {
                            Optional<AnimalLocationContext> contextOpt = Stream.concat(
                                    player.getFarm().getBarns().stream()
                                        .flatMap(b -> b.getAnimals().stream().map(a -> new AnimalLocationContext(a, b))),
                                    player.getFarm().getCoops().stream()
                                        .flatMap(c -> c.getAnimals().stream().map(a -> new AnimalLocationContext(a, c))))
                                .filter(ctx -> ctx.animal().getName().equals(animal.getName())).findFirst();

                            if (contextOpt.isEmpty())
                                return;


                            System.out.println("i");
                            AnimalLocationContext ctx = contextOpt.get();
                            Material housing = ctx.housing();
                            animal.setLocation(new Vector2(tileX, tileY));
                            RectangleMapObject out = new RectangleMapObject();
                            if (housing instanceof Coop coop) {
                                out = coop.getOut();
                            } else if (housing instanceof Barn barn) {
                                out = barn.getOut();
                                System.out.println("j");
                            }
                            Rectangle outRectangle = out.getRectangle();
                            finalLayer.setCell((int) animal.getLocation().x, (int) animal.getLocation().y, null);
                            animal.setLocation(new Vector2(outRectangle.x/16, outRectangle.y/16));
                            animal.getAnimalFriendship().setStayedOutsideTonight(true);
                            placeScaledImageAsTile(App.getCurrentGame().getMapForPlayer(player , MapType.FARM).getTmxMap(), layerName , (int) outRectangle.x/16, (int) outRectangle.y/16, animal.getTexturePath());
                        }
                    }
                    else if (App.getCurrentGame().getGameView() instanceof FarmView) {

                            Optional<AnimalLocationContext> contextOpt = Stream.concat(
                                    player.getFarm().getBarns().stream()
                                        .flatMap(b -> b.getAnimals().stream().map(a -> new AnimalLocationContext(a, b))),
                                    player.getFarm().getCoops().stream()
                                        .flatMap(c -> c.getAnimals().stream().map(a -> new AnimalLocationContext(a, c))))
                                .filter(ctx -> ctx.animal().getName().equals(animal.getName())).findFirst();

                            if (contextOpt.isEmpty())
                                return;


                            AnimalLocationContext ctx = contextOpt.get();
                            Material housing = ctx.housing();
                            animal.setLocation(new Vector2(tileX, tileY));
                            Rectangle out = new Rectangle();
                            if (housing instanceof Coop coop) {
                                out = coop.getArea();
                            } else if (housing instanceof Barn barn) {
                                out = barn.getArea();
                            }
                            if (out.contains(tileX*16, tileY*16)) {
                                finalLayer.setCell((int) animal.getLocation().x, (int) animal.getLocation().y, null);
                                animal.getAnimalFriendship().setStayedOutsideTonight(false);
                            } else {
                                animal.getAnimalFriendship().setStayedOutsideTonight(true);
                            }

                        }
                    }
            }
        }, 0, delay);


    }
    private record AnimalLocationContext(Animal animal, Material housing) {
    }

    public TextureRegion resizeTo3x3(TextureRegion region) {
        // اندازه‌ی تایل‌های فعلی (مثلاً 16 پیکسل)
        int tileSize = 16;

        // اندازه‌ی جدید (۳ تایل در عرض و ۳ تایل در ارتفاع)
        int newWidth = tileSize * 3;
        int newHeight = tileSize * 3;

        Texture texture = region.getTexture();

        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }

        Pixmap original = texture.getTextureData().consumePixmap();

        Pixmap source = new Pixmap(region.getRegionWidth(), region.getRegionHeight(), original.getFormat());
        source.drawPixmap(original,
            0, 0, region.getRegionX(), region.getRegionY(),
            region.getRegionWidth(), region.getRegionHeight());

        Pixmap resized = new Pixmap(newWidth, newHeight, original.getFormat());

        // resize دستی (بدون interpolation)
        resized.drawPixmap(source,
            0, 0, source.getWidth(), source.getHeight(),
            0, 0, newWidth, newHeight);

        Texture newTexture = new Texture(resized);
        TextureRegion resizedRegion = new TextureRegion(newTexture);

        // آزادسازی منابع
        original.dispose();
        source.dispose();
        resized.dispose();

        return resizedRegion;
    }

    public boolean isBuildMode() {
        return isBuildMode;
    }

    public void setBuildMode(boolean buildMode) {
        isBuildMode = buildMode;
    }
}
