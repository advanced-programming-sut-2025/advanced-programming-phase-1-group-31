package model.enums;

public enum TileType {
    EMPTY(".", "\u001B[40m"),  // پس‌زمینه سیاه
    TREE("T", "\u001B[42m"),
    FORAGING("F", "\u001B[102m"),
    STONE("S", "\u001B[47m"),
    LAKE("L", "\u001B[44m"),
    HOUSE("H", "\u001B[45m"),
    GREENHOUSE("G", "\u001B[46m"),
    MINE("M", "\u001B[41m"),
    WALL("W", "\u001B[41m"),
    QUARRY("Q", "\u001B[47m"),
    STORE("S", "\u001B[45m"),       // بنفش (Magenta) برای فروشگاه
    NPC("N", "\u001B[46m"),         // فیروزه‌ای (Cyan) برای NPC
    TRASH_BIN("T", "\u001B[41m");


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
