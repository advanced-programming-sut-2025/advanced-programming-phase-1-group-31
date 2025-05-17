package model.enums.plantable;

import model.enums.general.Seasons;
import model.materials.MaterialType;

import java.util.List;
import java.util.Random;

public enum MixedSeedSeasons implements MaterialType {
    Spring("Spring Mixed Seed",
            List.of(
                    Seeds.CauliflowerSeeds,
                    Seeds.ParsnipSeeds,
                    Seeds.PotatoSeeds,
                    Seeds.JazzSeeds,     // Blue Jazz
                    Seeds.TulipBulb
            ),
            Seasons.Spring,
            75),  // base sell price

    Summer("Summer Mixed Seed",
            List.of(
                    Seeds.CornSeeds,
                    Seeds.PepperSeeds,   // Hot Pepper
                    Seeds.RadishSeeds,
                    Seeds.WheatSeeds,
                    Seeds.PoppySeeds,
                    Seeds.SunflowerSeeds,
                    Seeds.SpangleSeeds   // Summer Spangle
            ),
            Seasons.Summer,
            85),  // base sell price

    Fall("Fall Mixed Seed",
            List.of(
                    Seeds.ArtichokeSeeds,
                    Seeds.CornSeeds,
                    Seeds.EggplantSeeds,
                    Seeds.PumpkinSeeds,
                    Seeds.SunflowerSeeds,
                    Seeds.FairySeeds     // Fairy Rose
            ),
            Seasons.Fall,
            80),  // base sell price

    Winter("Winter Mixed Seed",
            List.of(
                    Seeds.PowderMelonSeeds
            ),
            Seasons.Winter,
            90);  // base sell price

    private final String name;
    private final List<Seeds> seeds;
    private final Seasons season;
    private final int baseSellPrice;

    MixedSeedSeasons(String name, List<Seeds> seeds, Seasons season, int baseSellPrice) {
        this.name = name;
        this.seeds = seeds;
        this.season = season;
        this.baseSellPrice = baseSellPrice;
    }

    public String getName() {
        return name;
    }

    public List<Seeds> getSeeds() {
        return seeds;
    }

    public Seasons getSeason() {
        return season;
    }
    public Seeds getRandomSeed() {
        Random random = new Random();
        return seeds.get(random.nextInt(seeds.size()));
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }
}
