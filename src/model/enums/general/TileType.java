package model.enums.general;

import model.NPC;
import model.Shop;
import model.materials.Foraging.ForagingCrop;
import model.materials.Foraging.ForagingMineral;
import model.materials.Foraging.ForagingSeed;
import model.materials.Foraging.ForagingTree;
import model.materials.Material;

public enum TileType {
    EMPTY(".", "\u001B[40m"),
    FORAGING_TREE("T", "\u001B[42m"),
    FORAGING_CROPS("F", "\u001B[102m"),
    FORAGING_SEED("F", "\u001B[102m"),
    FORAGING_MINERAL("S", "\u001B[47m"),
    LAKE("L", "\u001B[44m"),
    HOUSE("H", "\u001B[45m"),
    GREENHOUSE("G", "\u001B[46m"),
    MINE("M", "\u001B[41m"),
    WALL("w", "\u001B[48;5;94m"),
    QUARRY("Q", "\u001B[47m"),
    SHOP("S", "\u001B[45m"),
    PLANTING_SOIL("P", "\u001B[48;5;94m"),
    Wood("W", "\u001B[48;5;130m"),
    Craftable("C", "\u001B[48;5;28m"),
    NPC("N", "\u001B[46m");



    private final String symbol;
    private final String color;

    TileType(String symbol, String color) {
        this.symbol = symbol;
        this.color = color;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getColor() {
        return color;
    }
}
