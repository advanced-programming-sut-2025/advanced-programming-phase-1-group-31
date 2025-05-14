package model.Tools;

import model.Game;
import model.Player;
import model.Result;
import model.Tile;
import model.enums.general.Direction;
import model.enums.general.TileType;
import model.enums.toolTypes.WateringCanType;
import model.materials.Crop;
import model.materials.Material;
import model.materials.Tree;

import java.awt.*;
import java.util.Objects;

public class WateringCan implements Tool {
    private WateringCanType wateringCanType;
    private int waterAmount = 0;

    public WateringCan(WateringCanType wateringCanType) {
        this.wateringCanType = wateringCanType;
    }


    public WateringCanType getWateringCanType() {
        return wateringCanType;
    }

    public void setWateringCanType(WateringCanType wateringCanType) {
        this.wateringCanType = wateringCanType;
    }

    @Override
    public Result work(Direction direction) {
        Player player = Game.getActivePlayer();
        Point point = direction.apply(player.getPlace());
        double energyConsumption = wateringCanType.getEnergyConsumption();

        // Reduce energy consumption if farming level is 4
        if (player.getSkills().getFarmingLevel() == 4) {
            energyConsumption--;
        }

        // Check energy
        if (player.getEnergy().getEnergyAmount() < energyConsumption) {
            return new Result(false, "You don't have enough energy to use the Watering Can.");
        }

        // Check if the tile is within player's farm area
        if (!player.getFarm().getRectangle().contains(point)) {
            return new Result(false, "You only have access to tiles on your farm.");
        }

        Tile[][] map = Game.getMainMap().getMainMap();
        Tile tile = map[point.x][point.y];
        Material material = tile.getMaterial();


        player.getEnergy().changeEnergy(-energyConsumption);
        // change the tile type if be empty
        if (tile.getType() == TileType.LAKE) {
            if (waterAmount != wateringCanType.getCapacity()) {
                waterAmount = wateringCanType.getCapacity();
                return new Result(true, "Your Watering Can is full now!");
            }
            return new Result(true, "Your Watering Can was already full");
        } else if (material instanceof ?????){
            return // آب خورد گیاهت

        } else {
            return new Result(false, "You can't use the Watering Can here. Your energy was wasted!");
        }
    }

    @Override
    public String getName() {
        return "Watering Can " + wateringCanType.name();
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