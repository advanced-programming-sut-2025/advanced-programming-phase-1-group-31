package io.github.some_example_name.model.materials.Tools;



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
import io.github.some_example_name.model.materials.Foraging.ForagingTree;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.MaterialType;
import io.github.some_example_name.model.materials.Tree;

import java.awt.*;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;

import static io.github.some_example_name.model.TimeAndDate.placeScaledImageAsTile;

public class Axe implements Tool, Material {
    private final AxePickHoeType axeType;

    public Axe(AxePickHoeType axeType) {
        this.axeType = axeType;
    }

    @Override
    public Result work(Direction direction) {
        Player player = App.getCurrentGame().getActivePlayer();
        Vector2 point = direction.apply(player.getPlace());

        // تبدیل موقعیت به tile coordinates
        int tileX = (int)(point.x / 16);
        int tileY = (int)(point.y / 16);
        double energyConsumption = axeType.getEnergyConsumption();

        // Check energy
        if (player.getEnergy().getEnergyAmount() < energyConsumption) {
            return new Result(false, "You don't have enough energy to use the Axe.");
        }

        // Check if the tile is within player's farm area
//        if (!player.getFarm().getRectangle().contains(point)) {
//            return new Result(false, "You only have access to tiles on your farm.");
//        }
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

        Tile tile = App.getCurrentGame().getMainMap().getTileByPoint(tileX, tileY);
        if (tile == null || tile.getMaterial() == null) {
            player.getEnergy().changeEnergy(-energyConsumption);
            return new Result(false, "You can't use the Axe here. Your energy was wasted!");
        }
        Material material = tile.getMaterial();

        if (material instanceof Tree || material instanceof ForagingTree) {
            tile.setMaterial(new ForagingMineral(ForagingMinerals.WOOD));
            placeScaledImageAsTile(farmMap.getTmxMap(), "craft", tileX, tileY, tile.getMaterial().getTexturePath());
            player.getEnergy().changeEnergy(-energyConsumption);
            player.getSkills().setForagingLevel(40);
            return new Result(true, "You have successfully chopped the tree.");
        }

        if (tile.getMaterial().equals(new ForagingMineral(ForagingMinerals.WOOD))) {
            int wood = new Random().nextInt(100) + 60;
            tile.setMaterial(null);
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) farmMap.getTmxMap().getLayers().get("craft");
            tileLayer.setCell(tileX , tileY , null);
            player.getInventory().addElementToBackpack(material, wood);
            player.getEnergy().changeEnergy(-energyConsumption);
            return new Result(true, "You collected " + wood + " wood.");
        }

        // Default: failed attempt, still costs energy
        player.getEnergy().changeEnergy(-energyConsumption);
        return new Result(false, "You can't use the Axe here. Your energy was wasted!");
    }


    @Override
    public MaterialType getType() {
        return axeType;
    }

    @Override
    public String getTexturePath() {
        return axeType.getAxeName();
    }

    @Override
    public String getName() {
        return "Axe " + axeType.name();
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
