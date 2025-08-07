package io.github.some_example_name.model.materials.Tools;



import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.model.*;
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
import java.util.Random;

public class Pickaxe implements Tool, Material {
    private AxePickHoeType pickaxeType;

    public Pickaxe(AxePickHoeType pickaxeType) {
        this.pickaxeType = pickaxeType;
    }

    public void setPickaxeType(AxePickHoeType pickaxeType) {
        this.pickaxeType = pickaxeType;
    }

    public AxePickHoeType getPickaxeType() {
        return pickaxeType;
    }

    @Override
    public Result work(Direction direction) {
        Player player = App.getCurrentGame().getActivePlayer();
        Vector2 point = direction.apply(player.getPlace());

        // تبدیل موقعیت به tile coordinates
        int tileX = (int)(point.x / 16);
        int tileY = (int)(point.y / 16);
        double energyConsumption = pickaxeType.getEnergyConsumption();

        // Reduce energy consumption if foraging level is 4
        if (player.getSkills().getMiningLevel() == 4) {
            energyConsumption--;
        }

        // Check energy
        if (player.getEnergy().getEnergyAmount() < energyConsumption) {
            return new Result(false, "You don't have enough energy to use the PickAxe.");
        }

        FarmMap farmMap = App.getCurrentGame().getMapForPlayer(player, MapType.MINE);

        // بررسی آیا tile در منطقه قابل کشت است
        boolean isPlantable = false;
        MapLayer objectLayer = farmMap.getTmxMap().getLayers().get("Object");
        if (objectLayer == null) {
            return new Result(false, "You don't have enough energy to use the PickAxe.");
        }


        for (MapObject obj : objectLayer.getObjects()) {
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
            return new Result(false, "You can't use the PickAxe here. Your energy was wasted!h");
        }

        Tile tile = farmMap.getTileByPoint(tileX, tileY);
        if (tile == null || tile.getMaterial() == null) {
            player.getEnergy().changeEnergy(-energyConsumption);
            return new Result(false, "You can't use the PickAxe here. Your energy was wasted!v");
        }

        Material material = tile.getMaterial();

        if (material instanceof ForagingMineral) {
            tile.setMaterial(null);
            int amount = new Random().nextInt(20) + 10;
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) farmMap.getTmxMap().getLayers().get("mine");
            tileLayer.setCell(tileX , tileY , null);
            player.getInventory().addElementToBackpack(material, amount);
            player.getEnergy().changeEnergy(-energyConsumption);
            player.getSkills().setMiningLevel(180);
            return new Result(true, "You have successfully collected " + amount + " minerals");
        }

//        if (tile.getType() == TileType.Craftable) {
//            tile.setType(TileType.EMPTY);
//            tile.setMaterial(null);
//            player.getInventory().addElementToBackpack(material, 1);
//            player.getEnergy().changeEnergy(-energyConsumption);
//            return new Result(true, "You collected one craftable item.");
//        }

        // Default: failed attempt, still costs energy
        player.getEnergy().changeEnergy(-energyConsumption);
        return new Result(false, "You can't use the PickAxe here. Your energy was wasted!l");
    }


    @Override
    public MaterialType getType() {
        return pickaxeType;
    }

    @Override
    public String getTexturePath() {
        return pickaxeType.getPickName();
    }

    @Override
    public String getName() {
        return "Pick Axe " + pickaxeType.name();
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
