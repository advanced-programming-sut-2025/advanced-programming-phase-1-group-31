package model.enums;

public enum ForagingSeeds {
    ;

    private final String name;
    private final Seasons seasons;

    ForagingSeeds(String name, Seasons seasons) {
        this.name = name;
        this.seasons = seasons;
    }
}
