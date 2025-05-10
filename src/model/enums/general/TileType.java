package model.enums.general;

import model.NPC;
import model.Shop;
import model.materials.Foraging.ForagingCrop;
import model.materials.Foraging.ForagingMineral;
import model.materials.Foraging.ForagingSeed;
import model.materials.Foraging.ForagingTree;
import model.materials.Material;

public enum TileType {
    EMPTY(".", "\u001B[40m", null),
    FORAGING_TREE("T", "\u001B[42m", new ForagingTree()),
    FORAGING_CROPS("F", "\u001B[102m", new ForagingCrop()),
    FORAGING_SEED("F", "\u001B[102m", new ForagingSeed()),
    FORAGING_MINERAL("S", "\u001B[47m", new ForagingMineral()),
    LAKE("L", "\u001B[44m", null),
    HOUSE("H", "\u001B[45m", null),
    GREENHOUSE("G", "\u001B[46m", null),
    MINE("M", "\u001B[41m", null),
    WALL("w", "\u001B[48;5;94m", null),
    QUARRY("Q", "\u001B[47m", null),
    SHOP("S", "\u001B[45m", new Shop()),
    NPC("N", "\u001B[46m", new NPC());


    private final String symbol;
    private final String color;
    private final Material material;

    TileType(String symbol, String color, Material material) {
        this.symbol = symbol;
        this.color = color;
        this.material = material;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getColor() {
        return color;
    }

    public Material getMaterial() {
        return material;
    }
}
