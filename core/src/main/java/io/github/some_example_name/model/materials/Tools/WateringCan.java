package io.github.some_example_name.model.materials.Tools;



import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.Player;
import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.Tile;
import io.github.some_example_name.model.enums.general.Direction;
import io.github.some_example_name.model.enums.general.TileType;
import io.github.some_example_name.model.enums.toolTypes.WateringCanType;
import io.github.some_example_name.model.materials.*;

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
        Player player = GameApp.getPlayer();
        Vector2 point = direction.apply(player.getPlace());
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

        Tile[][] map = GameApp.getMainMap().getMainMap();
        Tile tile = map[(int) point.x][(int) point.y];
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
            return new Result(true, "You have successfully chopped the tree.");
        }
        if (material instanceof Seed seed) {
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
