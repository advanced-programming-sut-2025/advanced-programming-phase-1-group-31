package io.github.some_example_name.model.materials;



import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.enums.crafting.Industrials;
import io.github.some_example_name.model.enums.general.Seasons;

import java.util.Map;
import java.util.Objects;

public class Industrial implements Material {
    private final Industrials industrialType;

    // These are now initialized later, not in the field declaration
    private final int startHour;
    private final int startDay;
    private final Seasons startSeason;

    public Industrial(Industrials industrials) {
        this.industrialType = industrials;

        this.startHour = GameApp.getTimeAndDate().getHour();
        this.startDay = GameApp.getTimeAndDate().getDay();
        this.startSeason = GameApp.getTimeAndDate().getSeason();
    }

    public Craftable getCraftable() {
        return industrialType.getCraftable();
    }

    public boolean isReady() {
        if(!startSeason.equals(GameApp.getTimeAndDate().getSeason())){
            return true;
        }
        int requiredTime = industrialType.getProcessingTime();
        int elapsedTime = (GameApp.getTimeAndDate().getDay() - startDay) * 13
                + (GameApp.getTimeAndDate().getHour() - startHour);
        return requiredTime <= elapsedTime;
    }

    @Override
    public MaterialType getType() {
        return industrialType;
    }

    @Override
    public String getName() {
        return industrialType.getName();
    }

    @Override
    public int baseSellPrice() {
        return 0;
    }

    public boolean isEdible(Material m) {
        return industrialType.calculateEnergy(m) != -1;
    }

    public int getEnergy(Material m) {
        return industrialType.calculateEnergy(m);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material tool)) return false;
        return this.getClass().equals(tool.getClass()) &&
                this.getType().equals(tool.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), getType());
    }

    public Map<Material, Integer> getIngredients() {
        return industrialType.getIngredients();
    }
}
