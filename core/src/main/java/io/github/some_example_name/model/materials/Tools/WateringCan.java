package io.github.some_example_name.model.materials.Tools;



import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.model.*;
import io.github.some_example_name.model.enums.general.Direction;
import io.github.some_example_name.model.enums.general.TileType;
import io.github.some_example_name.model.enums.toolTypes.WateringCanType;
import io.github.some_example_name.model.materials.*;

import java.awt.*;
import java.util.Objects;

public class WateringCan implements Tool, Material {
    private WateringCanType wateringCanType;
    private int much =0;

    public int getMuch() {
        return much;
    }

    public void setMuch(int much) {
        this.much = much;
    }

    public WateringCan(WateringCanType wateringCanType) {
        this.wateringCanType = wateringCanType;
        much = wateringCanType.getCapacity();
    }

    @Override
    public Result work(Direction direction) {
        Player player = App.getCurrentGame().getActivePlayer();
        Vector2 point = direction.apply(player.getPlace());

        // تبدیل موقعیت به tile coordinates
        int tileX = (int)(point.x / 16);
        int tileY = (int)(point.y / 16);
        double energyConsumption = wateringCanType.getEnergyConsumption();

        // Reduce energy consumption if foraging level is 4
        if (player.getSkills().getForagingLevel() == 4) {
            energyConsumption--;
        }
        Tile tile = null;
        // Check energy
        if (player.getEnergy().getEnergyAmount() < energyConsumption) {
            return new Result(false, "You don't have enough energy to use the Axe.");
        }

        // Check if the tile is within player's farm area
//        if (!player.getFarm().getRectangle().contains(point)) {
//            return new Result(false, "You only have access to tiles on your farm.");
//        }

        if (player.getCurrentMapType() == MapType.FARM) {
            FarmMap farmMap = App.getCurrentGame().getMapForPlayer(player, MapType.FARM);
            Iterable<MapObject> objects = player.getFarm().getAllObjects();

            // بررسی آیا tile در منطقه قابل کشت است
            boolean isNearLake = false;
            for (MapObject obj : objects) {
                if (obj instanceof PolygonMapObject && "lake".equals(obj.getName())) {
                    Polygon polygon = ((PolygonMapObject) obj).getPolygon();

                    // تبدیل مختصات tile به مختصات دنیا (مرکز tile)
                    float worldX = tileX * 16 + 8; // 16 = tileSize, 8 = نیمه tile
                    float worldY = tileY * 16 + 8;

                    if (polygon.contains(worldX, worldY)) {
                        isNearLake = true;
                        break;
                    }
                }
            }

            if (isNearLake) {
                much = wateringCanType.getCapacity();
                return new Result(true, "You have successfully Water");
            }
            tile = App.getCurrentGame().getMainMap().getTileByPoint(tileX, tileY);
        } else if (player.getCurrentMapType() == MapType.GREENHOUSE) {
            FarmMap farmMap = App.getCurrentGame().getMapForPlayer(player, MapType.GREENHOUSE);
            tile = farmMap.getTileByPoint(tileX, tileY);
        }


        if (tile == null || tile.getMaterial() == null) {
            player.getEnergy().changeEnergy(-energyConsumption);

            return new Result(false, "You can't use the wateringCan here. Your energy was wasted!4");
        }
        Material material = tile.getMaterial();

        if (material instanceof Tree tree) {
            tree.setDaysWithoutWater(0);
            player.getEnergy().changeEnergy(-energyConsumption);
            player.getSkills().setFarmingLevel(20);
            much--;
            return new Result(true, "You have successfully water the tree.");
        }
        if (material instanceof Crop crop) {
            crop.setDaysWithoutWater(0);
            player.getEnergy().changeEnergy(-energyConsumption);
            player.getSkills().setFarmingLevel(20);
            much--;
            return new Result(true, "You have successfully water the crop.");
        }
        if (material instanceof Seed seed) {
            seed.setDaysWithoutWater(0);
            player.getEnergy().changeEnergy(-energyConsumption);
            player.getSkills().setFarmingLevel(20);
            much--;
            return new Result(true, "You have successfully chopped the tree.");
        }



        // Default: failed attempt, still costs energy
        player.getEnergy().changeEnergy(-energyConsumption);

        return new Result(false, "You can't use the wateringCan here. Your energy was wasted!g");
    }

    @Override
    public MaterialType getType() {
        return wateringCanType;
    }

    @Override
    public String getTexturePath() {
        return wateringCanType.getWateringCanName();
    }

    @Override
    public String getName() {
        return "Watering Can " + wateringCanType.name();
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
