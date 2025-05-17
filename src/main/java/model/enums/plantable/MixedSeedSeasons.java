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
            Seasons.Spring),

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
            Seasons.Summer),

    Fall("Fall Mixed Seed",
            List.of(
                    Seeds.ArtichokeSeeds,
                    Seeds.CornSeeds,
                    Seeds.EggplantSeeds,
                    Seeds.PumpkinSeeds,
                    Seeds.SunflowerSeeds,
                    Seeds.FairySeeds     // Fairy Rose
            ),
            Seasons.Fall),

    Winter("Winter Mixed Seed",
            List.of(
                    Seeds.PowderMelonSeeds
            ),
            Seasons.Winter);

    private final String name;
    private final List<Seeds> seeds;
    private final Seasons season;

    MixedSeedSeasons(String name, List<Seeds> seeds, Seasons season) {
        this.name = name;
        this.seeds = seeds;
        this.season = season;
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

}
