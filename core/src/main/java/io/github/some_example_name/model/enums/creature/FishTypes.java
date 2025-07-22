package io.github.some_example_name.model.enums.creature;


import io.github.some_example_name.model.enums.general.Seasons;
import io.github.some_example_name.model.materials.MaterialType;

public enum FishTypes implements MaterialType {
    SALMON("Salmon", 75, Seasons.Fall),
    SARDINE("Sardine", 40, Seasons.Fall),
    SHAD("Shad", 60, Seasons.Fall),
    BLUE_DISCUS("Blue Discus", 120, Seasons.Fall),
    MIDNIGHT_CARP("Midnight Carp", 150, Seasons.Winter),
    SQUID("Squid", 80, Seasons.Winter),
    TUNA("Tuna", 100, Seasons.Winter),
    PERCH("Perch", 55, Seasons.Winter),
    FLOUNDER("Flounder", 100, Seasons.Spring),
    LIONFISH("Lionfish", 100, Seasons.Spring),
    HERRING("Herring", 30, Seasons.Spring),
    GHOSTFISH("Ghostfish", 45, Seasons.Spring),
    TILAPIA("Tilapia", 75, Seasons.Summer),
    DORADO("Dorado", 100, Seasons.Summer),
    SUNFISH("Sunfish", 30, Seasons.Summer),
    RAINBOW_TROUT("Rainbow Trout", 65, Seasons.Summer),

    LEGEND("Legend", 5000, Seasons.Spring),
    GLACIERFISH("Glacierfish", 1000, Seasons.Winter),
    ANGLER("Angler", 900, Seasons.Fall),
    CRIMSONFISH("Crimsonfish", 1500, Seasons.Summer);

    private final String name;
    private final int basePrice;
    private final Seasons season;

    FishTypes(String name, int basePrice, Seasons season) {
        this.name = name;
        this.basePrice = basePrice;
        this.season = season;
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



    public boolean isLegendary() {
        return this == LEGEND || this == GLACIERFISH ||
               this == ANGLER || this == CRIMSONFISH;
    }
}
