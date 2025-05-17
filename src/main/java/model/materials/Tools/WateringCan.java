package model.materials.Tools;

import model.App;
import model.Player;
import model.Result;
import model.Tile;
import model.enums.foragings.ForagingMinerals;
import model.enums.general.Direction;
import model.enums.general.TileType;
import model.enums.toolTypes.WateringCanType;
import model.materials.*;
import model.materials.Foraging.ForagingMineral;
import model.materials.Foraging.ForagingTree;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

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


    public WateringCanType getWateringCanType() {
        return wateringCanType;
    }

    public void setWateringCanType(WateringCanType wateringCanType) {
        this.wateringCanType = wateringCanType;
    }

    @Override
    public Result work(Direction direction) {
        Player player = App.getCurrentGame().getActivePlayer();
        Point point = direction.apply(player.getPlace());
        double energyConsumption = wateringCanType.getEnergyConsumption();

        // Reduce energy consumption if foraging level is 4
        if (player.getSkills().getForagingLevel() == 4) {
            energyConsumption--;
        }

        // Check energy
        if (player.getEnergy().getEnergyAmount() < energyConsumption) {
            return new Result(false, "You don't have enough energy to use the Axe.");
        }

        // Check if the tile is within player's farm area
        if (!player.getFarm().getRectangle().contains(point)) {
            return new Result(false, "You only have access to tiles on your farm.");
        }

        Tile[][] map = App.getCurrentGame().getMainMap().getMainMap();
        Tile tile = map[point.x][point.y];
        Material material = tile.getMaterial();

        if (material instanceof Tree) {
            Tree tree = (Tree) material;
            tree.setDaysWithoutWater(0);
            player.getEnergy().changeEnergy(-energyConsumption);
            player.getSkills().setFarmingLevel(20);
            much--;
            return new Result(true, "You have successfully water the tree.");
        }
        if (material instanceof Crop) {
            Crop crop = (Crop) material;
            crop.setDaysWithoutWater(0);
            player.getEnergy().changeEnergy(-energyConsumption);
            player.getSkills().setFarmingLevel(20);
            much--;
            return new Result(true, "You have successfully chopped the tree.");
        }
        if (material instanceof Seed) {
            Seed seed = (Seed) material;
            seed.setDaysWithoutWater(0);
            player.getEnergy().changeEnergy(-energyConsumption);
            player.getSkills().setFarmingLevel(20);
            much--;
            return new Result(true, "You have successfully chopped the tree.");
        }
        if (tile.getMaterial() ==null && tile.getType() == TileType.LAKE) {
            much = wateringCanType.getCapacity();
            return new Result(true, "You have successfully chopped the tree.");
        }



        // Default: failed attempt, still costs energy
        player.getEnergy().changeEnergy(-energyConsumption);

        return new Result(false, "You can't use the wateringCan here. Your energy was wasted!");
    }

    @Override
    public MaterialType getType() {
        return wateringCanType;
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