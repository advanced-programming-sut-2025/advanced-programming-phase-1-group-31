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
import io.github.some_example_name.model.materials.*;
import io.github.some_example_name.model.materials.Foraging.ForagingMineral;

import java.awt.*;
import java.util.Objects;

import static io.github.some_example_name.model.TimeAndDate.placeScaledImageAsTile;

public class Scythe implements Tool, Material {
    private final int energyConsumption = 2;

    @Override
    public Result work(Direction direction) {
        Player player = App.getCurrentGame().getActivePlayer();
        Vector2 point = direction.apply(player.getPlace());

        // Convert position to tile coordinates
        int tileX = (int) (point.x / 16);
        int tileY = (int) (point.y / 16);

        double energyConsumption = 2.0;

        // Reduce energy consumption if foraging level is 4
        if (player.getSkills().getForagingLevel() == 4) {
            energyConsumption--;
        }

        // Check energy
        if (player.getEnergy().getEnergyAmount() < energyConsumption) {
            return new Result(false, "You're too tired to use the Scythe right now.");
        }

        if (player.getCurrentMapType() == MapType.FARM) {
            return harvestInFarm(player, tileX, tileY, energyConsumption);
        } else if (player.getCurrentMapType() == MapType.GREENHOUSE) {
            return harvestInGreenhouse(player, tileX, tileY, energyConsumption);
        }

        player.getEnergy().changeEnergy(-energyConsumption);
        return new Result(false, "You can't harvest anything here with the Scythe.");
    }

    private Result harvestInFarm(Player player, int tileX, int tileY, double energyConsumption) {
        FarmMap farmMap = App.getCurrentGame().getMapForPlayer(player, MapType.FARM);
        Tile tile = App.getCurrentGame().getMainMap().getTileByPoint(tileX, tileY);

        if (tile == null || tile.getMaterial() == null) {
            player.getEnergy().changeEnergy(-energyConsumption);
            return new Result(false, "There's nothing to harvest here.");
        }

        Material material = tile.getMaterial();
        if (material instanceof Crop crop) {
            return handleCropHarvest(player, farmMap, tile, tileX, tileY, crop, energyConsumption);
        } else if (material instanceof Tree tree) {
            return handleTreeHarvest(player, farmMap, tile, tileX, tileY, tree, energyConsumption);
        }

        player.getEnergy().changeEnergy(-energyConsumption);
        return new Result(false, "You can't harvest this with the Scythe.");
    }

    private Result harvestInGreenhouse(Player player, int tileX, int tileY, double energyConsumption) {
        FarmMap farmMap = App.getCurrentGame().getMapForPlayer(player, MapType.GREENHOUSE);
        Tile tile = farmMap.getTileByPoint(tileX, tileY);

        if (tile == null || tile.getMaterial() == null) {
            player.getEnergy().changeEnergy(-energyConsumption);
            return new Result(false, "There's nothing to harvest here.");
        }

        Material material = tile.getMaterial();
        if (material instanceof Crop crop) {
            return handleCropHarvest(player, farmMap, tile, tileX, tileY, crop, energyConsumption);
        } else if (material instanceof Tree tree) {
            return handleTreeHarvest(player, farmMap, tile, tileX, tileY, tree, energyConsumption);
        }

        player.getEnergy().changeEnergy(-energyConsumption);
        return new Result(false, "You can't harvest this with the Scythe.");
    }

    private Result handleCropHarvest(Player player, FarmMap farmMap, Tile tile, int tileX, int tileY, Crop crop, double energyConsumption) {
        if (crop.getAmount() <= 0) {
            player.getEnergy().changeEnergy(-energyConsumption);
            return new Result(false, "This crop isn't ready to harvest yet.");
        }

        player.getInventory().addElementToBackpack(crop, crop.getAmount());

        if (crop.getCrop().isOneTime()) {
            tile.setMaterial(null);
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) farmMap.getTmxMap().getLayers().get("craft");
            tileLayer.setCell(tileX, tileY, null);
        } else {
            crop.setAmount(0);
            placeScaledImageAsTile(farmMap.getTmxMap(), "craft", tileX, tileY, crop.getCrop().getStages().getLast().imagePath());
        }

        player.getEnergy().changeEnergy(-energyConsumption);
        player.getSkills().setFarmingLevel(40);
        return new Result(true, "You harvested " + crop.getAmount() + " " + crop.getName() + ".");
    }

    private Result handleTreeHarvest(Player player, FarmMap farmMap, Tile tile, int tileX, int tileY, Tree tree, double energyConsumption) {
        if (tree.getAmount() <= 0) {
            player.getEnergy().changeEnergy(-energyConsumption);
            return new Result(false, "This tree isn't ready to harvest yet.");
        }

        player.getInventory().addElementToBackpack(tree, tree.getAmount());

            tile.setMaterial(null);
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) farmMap.getTmxMap().getLayers().get("craft");
            tileLayer.setCell(tileX, tileY, null);


        player.getEnergy().changeEnergy(-energyConsumption);
        player.getSkills().setFarmingLevel(40);
        return new Result(true, "You harvested " + tree.getAmount() + " " + tree.getName() + ".");
    }

    private boolean isTilePlantableInGreenhouse(FarmMap farmMap, int tileX, int tileY) {
        MapLayer objectLayer = farmMap.getTmxMap().getLayers().get("Object");
        if (objectLayer == null) return false;

        for (MapObject obj : objectLayer.getObjects()) {
            if (obj instanceof PolygonMapObject && "plantable".equals(obj.getName())) {
                Polygon polygon = ((PolygonMapObject) obj).getPolygon();
                float worldX = tileX * 16 + 8;
                float worldY = tileY * 16 + 8;

                if (polygon.contains(worldX, worldY)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isTilePlantable(Player player, int tileX, int tileY) {
        for (MapObject obj : player.getFarm().getAllObjects()) {
            if (obj instanceof PolygonMapObject && "plantable".equals(obj.getName())) {
                Polygon polygon = ((PolygonMapObject) obj).getPolygon();
                float worldX = tileX * 16 + 8;
                float worldY = tileY * 16 + 8;

                if (polygon.contains(worldX, worldY)) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public String getTexturePath() {
        return "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Tools/Scythe.png";
    }

    @Override
    public String getName() {
        return "Scythe";
    }

    @Override
    public int baseSellPrice() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Tool tool))
            return false;
        return this.getClass().equals(tool.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }
}
