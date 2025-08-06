package io.github.some_example_name.model.materials.Tools;



import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.Player;
import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.Tile;
import io.github.some_example_name.model.enums.general.Direction;
import io.github.some_example_name.model.enums.general.TileType;
import io.github.some_example_name.model.enums.toolTypes.AxePickHoeType;
import io.github.some_example_name.model.materials.Foraging.ForagingMineral;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.MaterialType;

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
        Player player = GameApp.getPlayer();
        Vector2 point = direction.apply(player.getPlace());
        double energyConsumption = pickaxeType.getEnergyConsumption();

        // Reduce energy consumption if foraging level is 4
        if (player.getSkills().getMiningLevel() == 4) {
            energyConsumption--;
        }

        // Check energy
        if (player.getEnergy().getEnergyAmount() < energyConsumption) {
            return new Result(false, "You don't have enough energy to use the PickAxe.");
        }

        Tile[][] map = GameApp.getMainMap().getMainMap();
        Tile tile;
        try {
            tile = map[(int) point.x][(int) point.y];
        } catch (Exception e){
            return new Result(false, "This direction isn't in map");
        }

        Material material = tile.getMaterial();

        if (material instanceof ForagingMineral) {
            tile.setType(TileType.EMPTY);
            tile.setMaterial(null);
            int amount = new Random().nextInt(20) + 10;
            player.getInventory().addElementToBackpack(material, amount);
            player.getEnergy().changeEnergy(-energyConsumption);
            player.getSkills().setMiningLevel(180);
            return new Result(true, "You have successfully collected " + amount + " minerals");
        }

        if (tile.getType() == TileType.Craftable) {
            tile.setType(TileType.EMPTY);
            tile.setMaterial(null);
            player.getInventory().addElementToBackpack(material, 1);
            player.getEnergy().changeEnergy(-energyConsumption);
            return new Result(true, "You collected one craftable item.");
        }

        // Default: failed attempt, still costs energy
        player.getEnergy().changeEnergy(-energyConsumption);
        return new Result(false, "You can't use the PickAxe here. Your energy was wasted!");
    }


    @Override
    public MaterialType getType() {
        return pickaxeType;
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
