package io.github.some_example_name.model.enums.foragings;


import io.github.some_example_name.model.enums.general.Seasons;
import io.github.some_example_name.model.materials.MaterialType;

import java.util.List;

public enum ForagingTrees implements MaterialType {
    ACORNS("Acorns", List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Trees/Acorns/Acorn.png"),
    MAPLE_SEEDS("Maple Seeds", List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Trees/Maple/Maple_Stage_4.png"),
    PINE_CONES("Pine Cones", List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Trees/PineCones/Pine_Stage_4.png"),
    MAHOGANY_SEEDS("Mahogany Seeds", List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Trees/Mahogany/Mahogany_Stage_4.png"),
    MUSHROOM_TREE_SEEDS("Mushroom Tree Seeds",
            List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter), "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Trees/MushroomTree/MushroomTree_Stage_5.png");

    private final String name;
    private final List<Seasons> seasons;
    private final String imagePath;

    ForagingTrees(String name, List<Seasons> seasons, String imagePath) {
        this.name = name;
        this.seasons = seasons;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }

    public String getImagePath() {
        return imagePath;
    }

    public static ForagingTrees findByName(String name) {
        for (ForagingTrees tree : ForagingTrees.values()) {
            if (tree.getName().equalsIgnoreCase(name)) {
                return tree;
            }
        }
        return null;
    }

}
