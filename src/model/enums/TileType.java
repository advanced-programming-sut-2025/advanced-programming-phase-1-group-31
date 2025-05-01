package model.enums;

public enum TileType {
    EMPTY(".", "\u001B[0m"),
    TREE("T", "\u001B[32m"),
    FORAGING("F", "\u001B[92m"),
    STONE("S", "\u001B[37m"),
    LAKE("L", "\u001B[34m"),
    HOUSE("H", "\u001B[35m"),
    GREENHOUSE("G", "\u001B[36m"),
    MINE("M", "\u001B[31m"),
    QUARRY("Q", "\u001B[37m");


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
