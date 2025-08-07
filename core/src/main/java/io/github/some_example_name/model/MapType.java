package io.github.some_example_name.model;

public enum MapType {
    FARM(true, "bigFarm.tmx"),
    FARM1(true, "bigFarm1.tmx"),
    FARM2(true, "bigFarm2.tmx"),
    BEACH(true, "bigBeach.tmx"),
    STORE(true, "bigfarm.tmx"),
    BLACKSMITH(true, "blacksmith.tmx"),
    GREENHOUSE(false, "greenhouse.tmx"),
    STARDROPSALOON(true, "stardropsaloon.tmx"),
    PIERREGENERALSTORE(true, "pierregeneralstore.tmx"),
    JOJAMART(true, "jojamart.tmx"),
    CARPENTERSHOP(true, "carpentershop.tmx"),
    MARNIERANCH(true, "marnieranch.tmx"),
    FISHSHOP(true, "fishshop.tmx"),
    HOUSE(false, "house.tmx"),
    COOP(false, "coop.tmx"),
    DELUXE_COOP(false, "deluxeCoop.tmx"),
    BARN(false, "barn.tmx"),
    LARGE_BARN(false, "largebarn.tmx"),
    DELUXE_BARN(false, "deluxebarn.tmx"),
    LARGE_COOP(false, "largecop.tmx"),
    MINE(false, "mine.tmx");

    private final boolean shared;
    private final String filename;

    MapType(boolean shared, String filename) {
        this.shared = shared;
        this.filename = filename;
    }

    public boolean isShared() {
        return shared;
    }

    public String getFilename() {
        return filename;
    }
}
