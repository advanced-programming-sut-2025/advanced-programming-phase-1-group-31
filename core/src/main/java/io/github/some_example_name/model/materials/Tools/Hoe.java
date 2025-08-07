package io.github.some_example_name.model.materials.Tools;


import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.model.*;
import io.github.some_example_name.model.enums.foragings.ForagingMinerals;
import io.github.some_example_name.model.enums.general.Direction;
import io.github.some_example_name.model.enums.general.TileType;
import io.github.some_example_name.model.enums.toolTypes.AxePickHoeType;
import io.github.some_example_name.model.materials.Foraging.ForagingMineral;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.MaterialType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.some_example_name.model.TimeAndDate.placeScaledImageAsTile;

public class Hoe implements Tool, Material {

    private final AxePickHoeType hoeType;

    public Hoe(AxePickHoeType hoeType) {
        this.hoeType = hoeType;
    }

    public AxePickHoeType getHoeType() {
        return hoeType;
    }

    @Override
    public Result work(Direction direction) {
//        System.out.println(direction.name());
        Player player = App.getCurrentGame().getActivePlayer();
        Vector2 point = direction.apply(player.getPlace());

        // تبدیل موقعیت به tile coordinates
        int tileX = (int)(point.x / 16);
        int tileY = (int)(point.y / 16);
        Vector2 tilePos = new Vector2(tileX, tileY);

        double energyConsumption = hoeType.getEnergyConsumption();

        // کاهش مصرف انرژی اگر سطح کشاورزی 4 باشد
        if (player.getSkills().getFarmingLevel() == 4) {
            energyConsumption--;
        }

        // بررسی انرژی
        if (player.getEnergy().getEnergyAmount() < energyConsumption) {
            return new Result(false, "You don't have enough energy to use the Hoe.");
        }

        FarmMap farmMap = App.getCurrentGame().getMapForPlayer(player, MapType.FARM);
        Iterable<MapObject> objects = player.getFarm().getAllObjects();

        // بررسی آیا tile در منطقه قابل کشت است
        boolean isPlantable = false;
        for (MapObject obj : objects) {
            if (obj instanceof PolygonMapObject && "plantable".equals(obj.getName())) {
                Polygon polygon = ((PolygonMapObject) obj).getPolygon();

                // تبدیل مختصات tile به مختصات دنیا (مرکز tile)
                float worldX = tileX * 16 + 8; // 16 = tileSize, 8 = نیمه tile
                float worldY = tileY * 16 + 8;

                if (polygon.contains(worldX, worldY)) {
                    isPlantable = true;
                    break;
                }
            }
        }

        if (!isPlantable) {
            player.getEnergy().changeEnergy(-energyConsumption);
            return new Result(false, "You can't use the Hoe here. Your energy was wasted!");
        }

        // بررسی نوع tile و material
        Tile tile = App.getCurrentGame().getMainMap().getTileByPoint(tileX, tileY);
        if (tile == null) {
                tile = new Tile();
                placeScaledImageAsTile(farmMap.getTmxMap(), "craft", tileX, tileY, "soil.png");
                tile.setMaterial(new ForagingMineral(ForagingMinerals.SOIL));
                tile.setPoint(new Point(tileX , tileY));
            App.getCurrentGame().getMainMap().addTile(tile);
                player.getEnergy().changeEnergy(-energyConsumption);
                player.getSkills().setFarmingLevel(40);
                return new Result(true, "The soil is now ready for planting.");

        }else {
            Material material = tile.getMaterial();
            if (material == null) {
                // بررسی وجود لایه craft
                MapLayer craftLayer = farmMap.getTmxMap().getLayers().get("craft");
                if (craftLayer instanceof TiledMapTileLayer tileLayer) {
                    TiledMapTileLayer.Cell cell = tileLayer.getCell(tileX, tileY);
                    if (cell == null) {
                        // کدهای شما برای ایجاد tile جدید
                        placeScaledImageAsTile(farmMap.getTmxMap(), "craft", tileX, tileY, "soil.png");
                        tile.setType(TileType.PLANTING_SOIL);
                        tile.setMaterial(new ForagingMineral(ForagingMinerals.SOIL));
                        player.getEnergy().changeEnergy(-energyConsumption);
                        player.getSkills().setFarmingLevel(40);
                        return new Result(true, "The soil is now ready for planting.");
                    }
                }
            }
        }

        player.getEnergy().changeEnergy(-energyConsumption);
        return new Result(false, "You can't use the Hoe here. The tile is not emptyc.");
    }
    @Override
    public MaterialType getType() {
        return hoeType;
    }
    private boolean containsPoint(List<Vector2> list, Vector2 point, float epsilon) {
        for (Vector2 p : list) {
            if (Math.abs(p.x - point.x) < epsilon && Math.abs(p.y - point.y) < epsilon) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String getTexturePath() {
        return hoeType.getHoeName();
    }

    private int getMapProperty(FarmMap map, String key) {
        return map.getTmxMap().getProperties().get(key, Integer.class);
    }

    private Point getTileSize(FarmMap map) {
        return new Point(getMapProperty(map, "tilewidth"), getMapProperty(map, "tileheight"));
    }
    @Override
    public String getName() {
        return "Hoe " + hoeType.name();
    }

    @Override
    public int baseSellPrice() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tool tool)) return false;
        return this.getClass().equals(tool.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }
}
