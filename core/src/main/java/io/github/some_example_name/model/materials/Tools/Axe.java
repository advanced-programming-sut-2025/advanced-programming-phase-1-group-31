package io.github.some_example_name.model.materials.Tools;



import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.Player;
import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.Tile;
import io.github.some_example_name.model.enums.foragings.ForagingMinerals;
import io.github.some_example_name.model.enums.general.Direction;
import io.github.some_example_name.model.enums.general.TileType;
import io.github.some_example_name.model.enums.toolTypes.AxePickHoeType;
import io.github.some_example_name.model.materials.Foraging.ForagingMineral;
import io.github.some_example_name.model.materials.Foraging.ForagingTree;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.MaterialType;
import io.github.some_example_name.model.materials.Tree;

import java.util.Objects;
import java.util.Random;

public class Axe implements Tool, Material {
    private final AxePickHoeType axeType;

    public Axe(AxePickHoeType axeType) {
        this.axeType = axeType;
    }

    @Override
    public Result work(Direction direction) {
        Player player = GameApp.getPlayer();
        Vector2 point = direction.apply(player.getPlace());
        double energyConsumption = axeType.getEnergyConsumption();

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

        if (material instanceof Tree || material instanceof ForagingTree) {
            tile.setType(TileType.Wood);
            tile.setMaterial(new ForagingMineral(ForagingMinerals.WOOD));
            player.getEnergy().changeEnergy(-energyConsumption);
            player.getSkills().setForagingLevel(40);
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
    public MaterialType getType() {
        return axeType;
    }

    @Override
    public String getName() {
        return "Axe " + axeType.name();
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
