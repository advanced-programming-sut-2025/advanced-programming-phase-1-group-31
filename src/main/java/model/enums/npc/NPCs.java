package model.enums.npc;

import model.Trade;
import model.enums.crafting.Foods;
import model.enums.creature.AnimalProducts;
import model.enums.crafting.Recipes;
import model.enums.farming.Seeds;
import model.enums.foragings.ForagingMinerals;
import model.materials.Animal;
import model.materials.Foraging.ForagingMineral;
import model.materials.Material;
import model.materials.MaterialType;
import model.materials.Products.AnimalProduct;
import model.materials.Products.CookedFood;
import model.materials.Farming.Seed;
import model.materials.Tools.Scarecrow;

import java.util.ArrayList;
import java.util.Arrays;

public enum NPCs implements MaterialType {
    سباستین(
            new Trade[]{
                    new Trade(new ForagingMineral(ForagingMinerals.Iron_Ore), new ForagingMineral(ForagingMinerals.Diamond), 50, 2),
                    new Trade(new CookedFood(Foods.PumpkinPie), null, 5000),
                    new Trade(new ForagingMineral(ForagingMinerals.Stone), ForagingMinerals.Quartz, 150, 50)
            },
            new Material[]{
                    new AnimalProduct(AnimalProducts.WOOL),
                    new CookedFood(Foods.PumpkinPie),
                    new CookedFood(Foods.Pizza)
            }
    ),

    ابیگیل(
            new Trade[]{
                    new Trade(new ForagingMineral(ForagingMinerals.Gold_Ore), null, 6),
                    new Trade(new Seed(Seeds.Corn), null, 1),
                    new Trade(null, null, 0, 50) // تحویل 50 عدد گندم: نیاز به تعریف جداگانه اگر آیتم گندم موجود باشد
            },
            new Material[]{
                    new ForagingMineral(ForagingMinerals.Stone),
                    new ForagingMineral(ForagingMinerals.Iron_Ore),
                    new CookedFood(Foods.Coffee)
            }
    ),

    هاروی(
            new Trade[]{
                    new Trade(null, null, 0, 750), // تحویل 12 تا از یک گیاه دلخواه
                    new Trade(new AnimalProduct(AnimalProducts.SALMON), null, 1),
                    new Trade(null, null, 0, 0) // بطری شراب نیاز به تعریف
            },
            new Material[]{
                    new CookedFood(Foods.Coffee),
                    new CookedFood(Foods.Pickles),
                    new CookedFood(Foods.Wine)
            }
    ),

    لیا(
            new Trade[]{
                    new Trade(new ForagingMineral(ForagingMinerals.Hardwood), null, 1),
                    new Trade(new AnimalProduct(AnimalProducts.SALMON), null, 1),
                    new Trade(null, null, 0, 0) // تحویل 200 چوب
            },
            new Material[]{
                    new CookedFood(Foods.Salad),
                    new CookedFood(Foods.GrapeJuice), // شراب انگور
                    new CookedFood(Foods.Wine)
            }
    ),

    رابین(
            new Trade[]{
                    new Trade(null, null, 0, 1000),
                    new Trade(new ForagingMineral(ForagingMinerals.Iron_Ore), null, 6),
                    new Trade(null, null, 0, 1000)
            },
            new Material[]{
                    new CookedFood(Foods.Spaghetti),
                    new ForagingMineral(ForagingMinerals.Hardwood),
                    new ForagingMineral(ForagingMinerals.Iron_Ore)
            }
    );

    public final ArrayList<Trade> quests = new ArrayList<>();
    public final ArrayList<Material> favoriteGifts = new ArrayList<>();

    NPCs(Trade[] quests, Material[] favorites) {
        this.quests.addAll(Arrays.asList(quests));
        this.favoriteGifts.addAll(Arrays.asList(favorites));
    }
}
