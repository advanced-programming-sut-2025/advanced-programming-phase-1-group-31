package model.enums;

import java.util.List;

public enum ForagingTrees {
    ACORNS("Acorns", List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),
    MAPLE_SEEDS("Maple Seeds", List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),
    PINE_CONES("Pine Cones", List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),
    MAHOGANY_SEEDS("Mahogany Seeds", List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter)),
    MUSHROOM_TREE_SEEDS("Mushroom Tree Seeds",
            List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter));

    private final String name;
    private final List<Seasons> seasons;

    ForagingTrees(String name, List<Seasons> seasons) {
        this.name = name;
        this.seasons = seasons;
    }

    public String getName() {
        return name;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }
}
