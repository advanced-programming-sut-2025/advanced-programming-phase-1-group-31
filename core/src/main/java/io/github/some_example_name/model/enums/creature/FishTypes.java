package io.github.some_example_name.model.enums.creature;

import io.github.some_example_name.model.enums.general.Seasons;
import io.github.some_example_name.model.materials.MaterialType;

public enum FishTypes implements MaterialType {
    SALMON("Salmon", 75, Seasons.Fall, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Salmon.png"),
    SARDINE("Sardine", 40, Seasons.Fall, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Anchovy.png"), // Assuming Sardine uses Anchovy image
    SHAD("Shad", 60, Seasons.Fall, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Shad.png"),
    BLUE_DISCUS("Blue Discus", 120, Seasons.Fall, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Blue_Discus.png"),
    MIDNIGHT_CARP("Midnight Carp", 150, Seasons.Winter, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Midnight_Carp.png"),
    SQUID("Squid", 80, Seasons.Winter, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Squid.png"),
    TUNA("Tuna", 100, Seasons.Winter, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Tuna.png"),
    PERCH("Perch", 55, Seasons.Winter, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Perch.png"),
    FLOUNDER("Flounder", 100, Seasons.Spring, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Flounder.png"),
    LIONFISH("Lionfish", 100, Seasons.Spring, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Lionfish.png"),
    HERRING("Herring", 30, Seasons.Spring, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Herring.png"),
    GHOSTFISH("Ghostfish", 45, Seasons.Spring, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Ghostfish.png"),
    TILAPIA("Tilapia", 75, Seasons.Summer, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Tilapia.png"),
    DORADO("Dorado", 100, Seasons.Summer, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Dorado.png"),
    SUNFISH("Sunfish", 30, Seasons.Summer, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Sunfish.png"),
    RAINBOW_TROUT("Rainbow Trout", 65, Seasons.Summer, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Rainbow_Trout.png"),

    LEGEND("Legend", 5000, Seasons.Spring, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Legend.png"),
    GLACIERFISH("Glacierfish", 1000, Seasons.Winter, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Glacierfish.png"),
    ANGLER("Angler", 900, Seasons.Fall, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Angler.png"),
    CRIMSONFISH("Crimsonfish", 1500, Seasons.Summer, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Fish/Crimsonfish.png");

    private final String name;
    private final int basePrice;
    private final Seasons season;
    private final String imagePath;

    FishTypes(String name, int basePrice, Seasons season, String imagePath) {
        this.name = name;
        this.basePrice = basePrice;
        this.season = season;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public Seasons getSeason() {
        return season;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isLegendary() {
        return this == LEGEND || this == GLACIERFISH ||
            this == ANGLER || this == CRIMSONFISH;
    }

    @Override
    public String toString() {
        return name + " - " + basePrice + "g (" + season + ")";
    }
}
