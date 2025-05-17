package model.materials.Tools;

import model.App;
import model.Player;
import model.Result;
import model.Tile;
import model.enums.general.Direction;
import model.enums.general.TileType;
import model.materials.Crop;
import model.materials.Material;
import model.materials.MaterialType;
import model.materials.Tree;

import java.awt.Point;
import java.util.Objects;

public class Scythe implements Tool, Material {
    private int energyConsumption = 2;

    @Override
    public Result work(Direction direction) {
        Player player = App.getCurrentGame().getActivePlayer();
        Point point = direction.apply(player.getPlace());
        double energyConsumption = 2.0;

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
        if (tile.getType() == TileType.CROPS && material instanceof Crop crop) {
            player.getInventory().addElementToBackpack(crop, crop.getAmount());
            if (crop.getCrop().isOneTime()) {
                tile.setType(TileType.EMPTY);
                tile.setMaterial(null);
            }
            player.getEnergy().changeEnergy(-energyConsumption);
            return new Result(true, "You successfully harvested from the crops.");
        } else if (tile.getType() == TileType.TREE && material instanceof Tree tree) {
            player.getInventory().addElementToBackpack(tree, tree.getAmount());
            return new Result(true, "You successfully harvested from the tree.");
        }
        player.getEnergy().changeEnergy(-energyConsumption);
        return new Result(false, "You can't use the Scythe here. Your energy was wasted!");
    }

    @Override
    public MaterialType getType() {
        return null;
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