package io.github.some_example_name.model.materials;

import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.model.*;
import io.github.some_example_name.model.enums.general.Direction;
import io.github.some_example_name.model.enums.general.TileType;


import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class ShoppingBin implements Material{
    private final HashMap<String, HashMap<Material, Integer>> materialForSell = new HashMap<>();


    public Result work(String name, int amount) {
//        if (!isNextToTrashBin()){
//            return new Result(false, "You aren't next to the Trash Bin!");
//        }
        Player player = App.getCurrentGame().getActivePlayer();
        Backpack backpack = player.getInventory();
        Material material = backpack.isExistInBackpackOrNull(name);
        if (material == null) {
            return new Result(false, "No such material '" + name + "' in your inventory.");
        }

        int qtyToSell;
        if (amount == -1) {
            qtyToSell = backpack.howManyInBackpack(material);
        } else {
            qtyToSell = amount;
        }

        Result removal = backpack.removeElementFromBackpack(material, qtyToSell);
        if (!removal.Success()) {
            return removal;
        }

        materialForSell
                .computeIfAbsent(player.getUsername(), k -> new HashMap<>())
                .merge(material, qtyToSell, Integer::sum);

        String msg = (amount == -1)
                ? "All of " + name + " (" + qtyToSell + ") left in the Trash Bin."
                : qtyToSell + " of " + name + " left in the Trash Bin.";
        return new Result(true, msg);
    }

    public void addMoney(Player player) {
        String user = player.getUsername();
        Map<Material,Integer> basket = materialForSell.get(user);
        if (basket == null || basket.isEmpty()) {
            return;
        }

        double totalGold = 0;
        for (var e : basket.entrySet()) {
            Random random = new Random();
            double percentage = random.nextInt(85) + 15;
            totalGold += e.getKey().baseSellPrice() * e.getValue() * percentage / 100;
        }

        player.addMoney(totalGold);
        materialForSell.remove(user);
    }


//    public boolean isNextToTrashBin() {
//        for (Direction dir : Direction.values()) {
//            Vector2 tilePoint = dir.apply(App.getCurrentGame().getActivePlayer().getPlace());
//
//            if (tilePoint.x < 0 || tilePoint.y < 0 ||
//                    tilePoint.x >= 140 || tilePoint.y >= 100) {
//                continue;
//            }
//
//            Tile tile = App.getCurrentGame().getMainMap().getMainMap()[(int) tilePoint.x][(int) tilePoint.y];
//            if (tile != null && tile.getType() == TileType.TRASH_BIN) {
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public MaterialType getType() {
        return null;
    }

    @Override
    public String getTexturePath() {
        return "";
    }

    @Override
    public String getName() {
        return "Trash Bin No No";
    }

    @Override
    public int baseSellPrice() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material tool)) return false;
        return this.getClass().equals(tool.getClass()) &&
                this.getType().equals(tool.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), getType());
    }
}
