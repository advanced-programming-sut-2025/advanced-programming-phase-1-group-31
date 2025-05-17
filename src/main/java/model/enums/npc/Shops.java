package model.enums.npc;

import model.MaterialInShop;
import model.enums.creature.Animals;
import model.enums.creature.CoopsAndBarnsTypes;
import model.enums.foragings.ForagingCrops;
import model.enums.foragings.ForagingMinerals;
import model.enums.general.Seasons;
import model.enums.plantable.Seeds;
import model.enums.toolTypes.AxePickHoeType;
import model.enums.toolTypes.FishingPoleType;
import model.enums.toolTypes.TrashCanType;
import model.materials.*;
import model.materials.Foraging.ForagingCrop;
import model.materials.Foraging.ForagingMineral;
import model.materials.Tools.*;

import java.util.List;

public enum Shops implements MaterialType {
    BlackSmith(
            List.of(
                    new MaterialInShop(new ForagingMineral(ForagingMinerals.Copper_Ore), 75, 75, 20000000, null),
                    new MaterialInShop(new ForagingMineral(ForagingMinerals.Iron_Ore), 150, 150, 20000000, null),
                    new MaterialInShop(new ForagingMineral(ForagingMinerals.Gold_Ore), 400, 400, 20000000, null),
                    new MaterialInShop(new ForagingMineral(ForagingMinerals.Coal), 150, 150, 1, null),
                    new MaterialInShop(new Hoe(AxePickHoeType.Copper), 2000, 2000, 1, null),
                    new MaterialInShop(new Hoe(AxePickHoeType.Iron), 5000, 5000, 1, null),
                    new MaterialInShop(new Hoe(AxePickHoeType.Gold), 10000, 10000, 1, null),
                    new MaterialInShop(new Hoe(AxePickHoeType.Iridium), 25000, 25000, 1, null),
                    new MaterialInShop(new TrashCan(TrashCanType.Copper), 1000, 1000, 20000000, null),
                    new MaterialInShop(new TrashCan(TrashCanType.Iron), 2500, 2500, 20000000, null),
                    new MaterialInShop(new TrashCan(TrashCanType.Gold), 5000, 5000, 20000000, null),
                    new MaterialInShop(new TrashCan(TrashCanType.Iridium), 12500, 12500, 20000000, null))
    ),

    MarnieRanch(
            List.of(
                    new MaterialInShop(new ForagingCrop(ForagingCrops.Hey), 50, 50, 20000000, null),
                    new MaterialInShop(new MilkPail(), 1000, 1000, 1, null),
                    new MaterialInShop(new Shear(), 1000, 1000, 1, null),
                    new MaterialInShop(new Animal("CHICKEN", Animals.CHICKEN), 800, 800, 2, null),
                    new MaterialInShop(new Animal("COW", Animals.COW), 1500, 1500, 2, null),
                    new MaterialInShop(new Animal("GOAT", Animals.GOAT), 2000, 2000, 2, null),
                    new MaterialInShop(new Animal("DUCK", Animals.DUCK), 1200, 1200, 2, null),
                    new MaterialInShop(new Animal("SHEEP", Animals.SHEEP), 8000, 8000, 2, null),
                    new MaterialInShop(new Animal("RABBIT", Animals.RABBIT), 8000, 8000, 2, null),
                    new MaterialInShop(new Animal("DINOSAUR", Animals.DINOSAUR), 14000, 14000, 2, null),
                    new MaterialInShop(new Animal("PIG", Animals.PIG), 16000, 16000, 2, null))
    ),


    StardropSaloon(
            List.of(
                    new MaterialInShop(new ForagingMineral(ForagingMinerals.Wood), 10, 10, 20000000, null),
                    new MaterialInShop(new ForagingMineral(ForagingMinerals.Stone), 20, 20, 20000000, null),
                    new MaterialInShop(new Barn(CoopsAndBarnsTypes.BARN), 6000, 6000, 1, null),
                    new MaterialInShop(new Barn(CoopsAndBarnsTypes.LARGE_BARN), 12000, 12000, 1, null),
                    new MaterialInShop(new Barn(CoopsAndBarnsTypes.DELUXE_BARN), 25000, 25000, 1, null),
                    new MaterialInShop(new Coop(CoopsAndBarnsTypes.COOP), 4000, 4000, 1, null),
                    new MaterialInShop(new Coop(CoopsAndBarnsTypes.LARGE_COOP), 10000, 10000, 1, null),
                    new MaterialInShop(new Coop(CoopsAndBarnsTypes.DELUXE_COOP), 20000, 20000, 1, null))
    ),

    CarpenterShop(
            List.of(
                    new MaterialInShop(new ForagingMineral(ForagingMinerals.Wood), 10, 10, 20000000, null),
                    new MaterialInShop(new ForagingMineral(ForagingMinerals.Stone), 20, 20, 20000000, null),
                    new MaterialInShop(new Barn(CoopsAndBarnsTypes.BARN), 6000, 6000, 1, null),
                    new MaterialInShop(new Barn(CoopsAndBarnsTypes.LARGE_BARN), 12000, 12000, 1, null),
                    new MaterialInShop(new Barn(CoopsAndBarnsTypes.DELUXE_BARN), 25000, 25000, 1, null),
                    new MaterialInShop(new Coop(CoopsAndBarnsTypes.COOP), 4000, 4000, 1, null),
                    new MaterialInShop(new Coop(CoopsAndBarnsTypes.LARGE_COOP), 10000, 10000, 1, null),
                    new MaterialInShop(new Coop(CoopsAndBarnsTypes.DELUXE_COOP), 20000, 20000, 1, null))
    ),

    Jojamart(
            List.of(
                    new MaterialInShop(new Seed(Seeds.ParsnipSeeds), 25, 25, 5, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.BeanStarter), 75, 75, 5, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.CauliflowerSeeds), 100, 100, 5, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.PotatoSeeds), 62, 62, 5, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.StrawberrySeeds), 100, 100, 5, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.TulipBulb), 25, 25, 5, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.KaleSeeds), 87, 87, 5, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.CoffeeBean), 200, 200, 1, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.CarrotSeeds), 5, 5, 10, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.RhubarbSeeds), 100, 100, 5, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.JazzSeeds), 37, 37, 5, Seasons.Spring),

                    new MaterialInShop(new Seed(Seeds.TomatoSeeds), 62, 62, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.PepperSeeds), 50, 50, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.WheatSeeds), 12, 12, 10, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.SummerSquashSeeds), 10, 10, 10, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.RadishSeeds), 50, 50, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.MelonSeeds), 100, 100, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.HopsStarter), 75, 75, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.PoppySeeds), 125, 125, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.SpangleSeeds), 62, 62, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.StarfruitSeeds), 400, 400, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.CoffeeBean), 200, 200, 1, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.SunflowerSeeds), 125, 125, 5, Seasons.Summer),

                    new MaterialInShop(new Seed(Seeds.CornSeeds), 187, 187, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.EggplantSeeds), 25, 25, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.PumpkinSeeds), 125, 125, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.BroccoliSeeds), 15, 15, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.AmaranthSeeds), 87, 87, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.GrapeStarter), 75, 75, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.BeetSeeds), 20, 20, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.YamSeeds), 75, 75, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.BokChoySeeds), 62, 62, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.CranberrySeeds), 300, 300, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.SunflowerSeeds), 125, 125, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.FairySeeds), 250, 250, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.RareSeed), 1000, 1000, 1, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.WheatSeeds), 12, 12, 5, Seasons.Fall),

                    new MaterialInShop(new Seed(Seeds.PowderMelonSeeds), 20, 20, 10, Seasons.Winter))
    ),


    PierreGeneralStore(
            List.of(
                    // Spring Stock
                    new MaterialInShop(new Seed(Seeds.ParsnipSeeds), 20, 30, 5, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.BeanStarter), 60, 90, 5, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.CauliflowerSeeds), 80, 120, 5, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.PotatoSeeds), 50, 75, 5, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.TulipBulb), 20, 30, 5, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.KaleSeeds), 70, 105, 5, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.JazzSeeds), 30, 45, 5, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.GarlicSeeds), 40, 60, 5, Seasons.Spring),
                    new MaterialInShop(new Seed(Seeds.RiceShoot), 40, 60, 5, Seasons.Spring),

                    // Summer Stock
                    new MaterialInShop(new Seed(Seeds.MelonSeeds), 80, 120, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.TomatoSeeds), 50, 75, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.BlueberrySeeds), 80, 120, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.PepperSeeds), 40, 60, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.WheatSeeds), 10, 15, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.RadishSeeds), 40, 60, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.PoppySeeds), 100, 150, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.SpangleSeeds), 50, 75, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.HopsStarter), 60, 90, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.CornSeeds), 150, 225, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.SunflowerSeeds), 200, 300, 5, Seasons.Summer),
                    new MaterialInShop(new Seed(Seeds.RedCabbageSeeds), 100, 150, 5, Seasons.Summer),

                    // Fall Stock
                    new MaterialInShop(new Seed(Seeds.EggplantSeeds), 20, 30, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.CornSeeds), 150, 225, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.PumpkinSeeds), 100, 150, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.BokChoySeeds), 50, 75, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.YamSeeds), 60, 90, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.CranberrySeeds), 240, 360, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.SunflowerSeeds), 200, 300, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.FairySeeds), 200, 300, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.AmaranthSeeds), 70, 105, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.GrapeStarter), 60, 90, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.WheatSeeds), 10, 15, 5, Seasons.Fall),
                    new MaterialInShop(new Seed(Seeds.ArtichokeSeeds), 30, 45, 5, Seasons.Fall)
            )
    ),

    FishShop(
            List.of(
                    new MaterialInShop(new FishingPole(FishingPoleType.Training), 25, 25, 1, null),
                    new MaterialInShop(new FishingPole(FishingPoleType.Bamboo), 500, 500, 1, null),
                    new MaterialInShop(new FishingPole(FishingPoleType.Iridium), 7500, 7500, 1, null),
                    new MaterialInShop(new FishingPole(FishingPoleType.Fiberglass), 1800, 1800, 1, null))
    );


    private final List<MaterialInShop> materials;

    Shops(List<MaterialInShop> materials) {
        this.materials = materials;
    }

    public List<MaterialInShop> getMaterials() {
        return materials;
    }
}

