package model.Tools;

import model.Game;
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

public class Pickaxe implements Tool {
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
        Player player = Game.getActivePlayer();
        Point point = direction.apply(player.getPlace());
        double energyConsumption = pickaxeType.getEnergyConsumption();

        // Reduce energy consumption if foraging level is 4
        if (player.getSkills().getMiningLevel() == 4) {
            energyConsumption--;
        }

        // Check energy
        if (player.getEnergy().getEnergyAmount() < energyConsumption) {
            return new Result(false, "You don't have enough energy to use the PickAxe.");
        }

        Tile[][] map = Game.getMainMap().getMainMap();
        Tile tile;
        try {
            tile = map[point.x][point.y];
        } catch (Exception e){
            return new Result(false, "This direction isn't in map");
        }

        Material material = tile.getMaterial();

        if (material instanceof ForagingMineral) {
            tile.setType(TileType.EMPTY);
            tile.setMaterial(null);
            int amount = new Random().nextInt(20) + 10;
            Result result = player.getInventory().addElementToBackpack(material, amount);
            if (!result.isSuccessful()) return result;
            player.getEnergy().changeEnergy(-energyConsumption);
            return new Result(true, "You have successfully collected " + amount + " minerals");
        }

        if (tile.getType() == TileType.Craftable) {
            tile.setType(TileType.EMPTY);
            tile.setMaterial(null);
            Result result = player.getInventory().addElementToBackpack(material, 1);
            if (!result.isSuccessful()) return result;
            player.getEnergy().changeEnergy(-energyConsumption);
            return new Result(true, "You collected one craftable item.");
        }

        // Default: failed attempt, still costs energy
        player.getEnergy().changeEnergy(-energyConsumption);
        return new Result(false, "You can't use the PickAxe here. Your energy was wasted!");
    }

    @Override
    public String getName() {
        return "Pick Axe " + pickaxeType.name();
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
