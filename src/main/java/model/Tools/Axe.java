package model.Tools;

import model.App;
import model.Player;
import model.Result;
import model.Tile;
import model.enums.foragings.ForagingMinerals;
import model.enums.general.Direction;
import model.enums.general.TileType;
import model.enums.toolTypes.AxePickHoeType;
import model.materials.Foraging.ForagingMineral;
import model.materials.Foraging.ForagingTree;
import model.materials.Material;
import model.materials.Tree;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Axe implements Tool {
    private AxePickHoeType axeType;

    public Axe(AxePickHoeType axeType) {
        this.axeType = axeType;
    }

    public void setAxeType(AxePickHoeType axeType) {
        this.axeType = axeType;
    }

    public AxePickHoeType getAxeType() {
        return axeType;
    }

    @Override
    public Result work(Direction direction) {
        Player player = App.getCurrentGame().getActivePlayer();
        Point point = direction.apply(player.getPlace());
        double energyConsumption = axeType.getEnergyConsumption();

        // Reduce energy consumption if foraging level is 4
//        if (player.getSkills().getForagingLevel() == 4) {
//            energyConsumption--;
//        }

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

        if (material instanceof Tree || material instanceof ForagingTree) {
            tile.setType(TileType.Wood);
            tile.setMaterial(new ForagingMineral(ForagingMinerals.Wood));
            player.getEnergy().changeEnergy(-energyConsumption);
            return new Result(true, "You have successfully chopped the tree.");
        }

        if (tile.getType() == TileType.Wood) {
            int wood = new Random().nextInt(100) + 60;
            tile.setType(TileType.EMPTY);
            tile.setMaterial(null);
            player.getInventory().addElementToBackpack(material, wood);
            player.getEnergy().changeEnergy(-energyConsumption);
            return new Result(true, "You collected " + wood + " wood.");
        }

        // Default: failed attempt, still costs energy
        player.getEnergy().changeEnergy(-energyConsumption);
        return new Result(false, "You can't use the Axe here. Your energy was wasted!");
    }


    @Override
    public String getName() {
        return "Axe " + axeType.name();
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