package io.github.some_example_name.model.materials.Tools;



import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.Player;
import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.Tile;
import io.github.some_example_name.model.enums.general.Direction;
import io.github.some_example_name.model.enums.general.TileType;
import io.github.some_example_name.model.materials.Crop;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.MaterialType;
import io.github.some_example_name.model.materials.Tree;

import java.util.Objects;

public class Scythe implements Tool, Material {
    private final int energyConsumption = 2;

    @Override
    public Result work(Direction direction) {
        Player player = GameApp.getPlayer();
        Vector2 point = direction.apply(player.getPlace());
        double energyConsumption = 2.0;

        // Reduce energy consumption if foraging level is 4
        if (player.getSkills().getForagingLevel() == 4) {
            energyConsumption--;
        }

        // Check energy
        if (player.getEnergy().getEnergyAmount() < energyConsumption) {
            return new Result(false, "You don't have enough energy to use the Scythe.");
        }

        // Check if the tile is within player's farm area
        if (!player.getFarm().getRectangle().contains(point)) {
            return new Result(false, "You only have access to tiles on your farm.");
        }

        Tile[][] map = GameApp.getMainMap().getMainMap();
        Tile tile = map[(int) point.x][(int) point.y];
        Material material = tile.getMaterial();
        if (tile.getType() == TileType.CROPS && material instanceof Crop crop) {
            player.getInventory().addElementToBackpack(crop, crop.getAmount());
            if (crop.getCrop().isOneTime()) {
                tile.setType(TileType.EMPTY);
                tile.setMaterial(null);
            }
            player.getEnergy().changeEnergy(-energyConsumption);
            player.getSkills().setFarmingLevel(40);
            return new Result(true, "You successfully harvested from the crops.");
        } else if (tile.getType() == TileType.TREE && material instanceof Tree tree) {
            player.getInventory().addElementToBackpack(tree, tree.getAmount());
            player.getSkills().setFarmingLevel(40);
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
