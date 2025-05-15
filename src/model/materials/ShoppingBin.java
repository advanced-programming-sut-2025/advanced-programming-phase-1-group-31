package model.materials;

import model.*;
import model.enums.general.Direction;
import model.enums.general.TileType;
import model.materials.Material;

import java.awt.*;
import java.util.HashMap;

public class ShoppingBin implements Material{
    private final HashMap<String, HashMap<Material, Integer>> materialForSell = new HashMap<>();


    public Result work(String name, int amount) {
        if (!isNextToTrashBin()){
            return new Result(false, "You aren't next to the Trash Bin!");
        }
        Player player = Game.getActivePlayer();
        Backpack backpack = player.getInventory();
        Material material = backpack.isExistInBackpackOrNull(name);
        Result result = backpack.removeElementFromBackpack(material, amount);
        if (!result.isSuccessful()) return result;
        if (amount == -1) {
            int mainAmount = backpack.howManyInBackpack(material);
            materialForSell.putIfAbsent(player.getUsername(), new HashMap<>());
            materialForSell.get(player.getUsername()).put(material, mainAmount);
            return  new Result(true, "All of " + name + " have been left into Trash Bin.");
        }
        materialForSell.putIfAbsent(player.getUsername(), new HashMap<>());
        materialForSell.get(player.getUsername()).put(material, amount);
        return new Result(true, amount + " of " + name + " have been left into Trash Bin.");
    }


    public boolean isNextToTrashBin() {
        for (Direction dir : Direction.values()) {
            Point tilePoint = dir.apply(Game.getActivePlayer().getPlace());

            if (tilePoint.x < 0 || tilePoint.y < 0 ||
                    tilePoint.x >= 140 || tilePoint.y >= 100) {
                continue;
            }

            Tile tile = Game.getMainMap().getMainMap()[tilePoint.x][tilePoint.y];
            if (tile != null && tile.getType() == TileType.TRASH_BIN) {
                return true;
            }
        }
        return false;
    }

    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public String getName() {
        return "Trash Bin No No";
    }

    @Override
    public int baseSellPrice() {
        return 0;
    }
}
