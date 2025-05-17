package model.Tools;

import model.*;
import model.enums.general.Direction;
import model.enums.general.TileType;
import model.enums.toolTypes.AxePickHoeType;

import java.awt.*;
import java.util.Objects;

public class Hoe implements Tool {

    private AxePickHoeType hoeType;

    public Hoe(AxePickHoeType hoeType) {
        this.hoeType = hoeType;
    }

    public void setHoeType(AxePickHoeType hoeType) {
        this.hoeType = hoeType;
    }

    public AxePickHoeType getHoeType() {
        return hoeType;
    }

    @Override
    public Result work(Direction direction) {
        Player player = App.getCurrentGame().getActivePlayer();
        Point point = direction.apply(player.getPlace());
        double energyConsumption = hoeType.getEnergyConsumption();

        // Reduce energy consumption if farming level is 4
        if (player.getSkills().getFarmingLevel() == 4) {
            energyConsumption--;
        }

        // Check energy
        if (player.getEnergy().getEnergyAmount() < energyConsumption) {
            return new Result(false, "You don't have enough energy to use the Hoe.");
        }

        // Check if the tile is within player's farm area
        if (!player.getFarm().getRectangle().contains(point)) {
            return new Result(false, "You only have access to tiles on your farm.");
        }

        Tile[][] map = App.getCurrentGame().getMainMap().getMainMap();
        Tile tile = map[point.x][point.y];

        // change the tile type if be empty
        if (tile.getType() == TileType.EMPTY) {
            tile.setType(TileType.PLANTING_SOIL);
            tile.setMaterial(null);
            player.getEnergy().changeEnergy(-energyConsumption);
            return new Result(true, "The soil is now ready for planting.");
        } else {
            player.getEnergy().changeEnergy(-energyConsumption); // Still consumes energy
            return new Result(false, "You can't use the Hoe here. Your energy was wasted!");
        }
    }

    @Override
    public String getName() {
        return "Hoe " + hoeType.name();
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
