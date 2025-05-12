package model.enums.foragings;

import model.enums.general.Seasons;
import model.materials.MaterialType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public enum ForagingSeeds implements MaterialType {
    Acorns("Acorns", List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),
    MapleSeeds("Maple Seeds", List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),
    PineCones("Pine Cones", List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),
    MahoganySeeds("Mahogany Seeds", List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),
    MushroomTreeSeeds("Mushroom Tree Seeds",
            List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter));

    private final String displayName;
    private final List<Seasons> growSeasons;

    ForagingSeeds(String displayName, List<Seasons> growSeasons) {
        this.displayName = displayName;
        this.growSeasons = growSeasons;
    }

    public String getDisplayName() {
        return displayName;
    }


    public List<Seasons> getGrowSeasons() {
        return growSeasons;
    }
    public static ForagingSeeds getRandomBySeason(Seasons season) {
        List<ForagingSeeds> filtered = new ArrayList<>();
        for (ForagingSeeds seed : values()) {
            if (seed.growSeasons.contains(season)) {
                filtered.add(seed);
            }
        }
        if (filtered.isEmpty()) return null;
        return filtered.get(ThreadLocalRandom.current().nextInt(filtered.size()));
    }

    @Override
    public String toString() {
        return displayName;
    }
}
