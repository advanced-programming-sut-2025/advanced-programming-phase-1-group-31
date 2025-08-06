package io.github.some_example_name.model.enums.npc;



import io.github.some_example_name.model.Trade;
import io.github.some_example_name.model.enums.crafting.Craftables;
import io.github.some_example_name.model.enums.crafting.Foods;
import io.github.some_example_name.model.enums.crafting.Industrials;
import io.github.some_example_name.model.enums.creature.AnimalProducts;
import io.github.some_example_name.model.enums.creature.FishTypes;
import io.github.some_example_name.model.enums.foragings.ForagingMinerals;
import io.github.some_example_name.model.enums.general.Seasons;
import io.github.some_example_name.model.enums.plantable.Crops;
import io.github.some_example_name.model.materials.*;
import io.github.some_example_name.model.materials.Foraging.ForagingMineral;
import io.github.some_example_name.model.materials.Products.AnimalProduct;
import io.github.some_example_name.model.materials.Products.FishProducts;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public enum NPCs implements MaterialType {
    SEBASTIAN(
            List.of(
                    new Trade(new ForagingMineral(ForagingMinerals.IRON_ORE), new ForagingMineral(ForagingMinerals.Diamond), 50, 2),
                    new Trade(new Food(Foods.PUMPKIN_PIE), 1, 5000),
                    new Trade(new ForagingMineral(ForagingMinerals.STONE), new ForagingMineral(ForagingMinerals.Quartz), 150, 50)
            ),
            List.of(
                    new AnimalProduct(AnimalProducts.WOOL),
                    new Food(Foods.PUMPKIN_PIE),
                    new Food(Foods.PIZZA)
            ),
            Map.of(
                    Seasons.Spring, Map.of(
                            0, "Hey. The rain is kinda nice, I guess.",
                            1, "It’s peaceful, don’t you think?",
                            2, "Coding while it rains... now that’s perfect."
                    ),
                    Seasons.Summer, Map.of(
                            0, "Ugh. I hate the sun.",
                            1, "Maybe we could hang out in the shade sometime.",
                            2, "Ice-cold cola and pizza... saves the day."
                    ),
                    Seasons.Fall, Map.of(
                            0, "Fall is fine. Lots of shadows.",
                            1, "I like the mood Fall brings. You too?",
                            2, "That smell of wood smoke... calming."
                    ),
                    Seasons.Winter, Map.of(
                            0, "Snow. Cool. Staying at home...",
                            1, "I love coding when it’s snowing outside.",
                            2, "Hot pizza, snow outside... heaven."
                    )
            )
    , "Sebastian"),

    ABIGAIL(
            List.of(
                    new Trade(new Industrial(Industrials.GOLD_BAR), 1, -1),
                    new Trade(new Crop(Crops.Pumpkin), 1, 500),
                    new Trade(new Crop(Crops.Wheat), new Craftable(Craftables.IRIDIUM_SPRINKLER), 50, 1)
            ),
            List.of(
                    new ForagingMineral(ForagingMinerals.IRON_ORE),
                    new ForagingMineral(ForagingMinerals.STONE),
                    new Industrial(Industrials.COFFEE)
            ),
            Map.of(
                    Seasons.Spring, Map.of(
                            0, "Flowers are blooming!",
                            1, "I picked a daffodil for you.",
                            2, "Spring smells like adventure!"
                    ),
                    Seasons.Summer, Map.of(
                            0, "It’s too hot...",
                            1, "Want to go on an adventure today?",
                            2, "Chocolate cake in the sun? Why not!"
                    ),
                    Seasons.Fall, Map.of(
                            0, "I love the mystery of Fall.",
                            1, "The crunch of leaves underfoot is so satisfying!",
                            2, "Fall feels like a fantasy game."
                    ),
                    Seasons.Winter, Map.of(
                            0, "Staying inside with hot chocolate sounds great.",
                            1, "Let’s play some games later! Cool? ",
                            2, "Winter and coffee are best friends."
                    )
            )
    , "Abigail"),

    HARVEY(
            List.of(
                    new Trade(new Crop(Crops.Blue_Jazz), 12, 750),
                    new Trade(new FishProducts(FishTypes.SALMON), 1, -1),
                    new Trade(new Industrial(Industrials.OIL), new Food(Foods.SALAD), 1, 5)
            ),
            List.of(
                    new Industrial(Industrials.HONEY),
                    new Industrial(Industrials.CHEESE),
                    new Industrial(Industrials.COFFEE)
            ),
            Map.of(
                    Seasons.Spring, Map.of(
                            0, "Stay healthy!",
                            1, "Morning walks are my favorite. Want to join?",
                            2, "Coffee tastes better in Spring air."
                    ),
                    Seasons.Summer, Map.of(
                            0, "Don't forget sunscreen.",
                            1, "Warm weather is great for relaxing, too.",
                            2, "Cold wine in Summer is a blessing."
                    ),
                    Seasons.Fall, Map.of(
                            0, "Colds are common now.",
                            1, "Let’s enjoy this cozy season together.",
                            2, "Cheese and Fall? Perfect diet."
                    ),
                    Seasons.Winter, Map.of(
                            0, "Be careful not to slip on ice.",
                            1, "Winter snow is quite beautiful, isn't it?",
                            2, "Warm coffee keeps the spirit high."
                    )
            ),
    "Harvey"),

    LEAH(
            List.of(
                    new Trade(new ForagingMineral(ForagingMinerals.WOOD), 10, 500),
                    new Trade(new FishProducts(FishTypes.SALMON), 1, 700),
                    new Trade(new ForagingMineral(ForagingMinerals.WOOD),  200, 900)
            ),
            List.of(
                    new Food(Foods.SALAD),
                    new Industrial(Industrials.BEER),
                    new Crop(Crops.Grape)
            ),
            Map.of(
                    Seasons.Spring, Map.of(
                            0, "Inspiration is everywhere in Spring!",
                            1, "Want to go sketch in the forest?",
                            2, "Fruit salad makes a perfect art snack."
                    ),
                    Seasons.Summer, Map.of(
                            0, "I’m working on a new sculpture.",
                            1, "You inspire me to create more.",
                            2, "Wine under Summer stars is lovely."
                    ),
                    Seasons.Fall, Map.of(
                            0, "The forest is full of color.",
                            1, "Let’s collect some leaves for my art.",
                            2, "Salad with roasted nuts is so autumn."
                    ),
                    Seasons.Winter, Map.of(
                            0, "I stay indoors more now.",
                            1, "This season lets me reflect... with you.",
                            2, "Wine and sketches near the fireplace..."
                    )
            )
    , "Leah"),

    ROBIN(
            List.of(
                    new Trade(new ForagingMineral(ForagingMinerals.WOOD), 80, 1000),
                    new Trade(new ForagingMineral(ForagingMinerals.IRON_ORE), new Craftable(Craftables.BEE_HOUSE), 10, 3),
                    new Trade(new ForagingMineral(ForagingMinerals.WOOD) , 1000, 25000)
            ),
            List.of(
                    new Food(Foods.SPAGHETTI),
                    new ForagingMineral(ForagingMinerals.WOOD),
                    new ForagingMineral(ForagingMinerals.IRON_ORE)
            ),
            Map.of(
                    Seasons.Spring, Map.of(
                            0, "Spring keeps me busy!",
                            1, "Want to help with a new project?",
                            2, "Fresh wood and spaghetti... can't beat it."
                    ),
                    Seasons.Summer, Map.of(
                            0, "I love building outdoors.",
                            1, "Thanks for the help last time!",
                            2, "Spaghetti picnic after work? Yes!"
                    ),
                    Seasons.Fall, Map.of(
                            0, "The cool air is perfect for work.",
                            1, "Fall upgrades are my favorite.",
                            2, "Breeze smells like fresh lumber."
                    ),
                    Seasons.Winter, Map.of(
                            0, "Cold, but work never stops!",
                            1, "Come inside, it’s warm by the fire.",
                            2, "Iron, wood, and warmth — builder's joy."
                    )
            )
    , "Robin");

    public final ArrayList<Trade> quests = new ArrayList<>();
    public final ArrayList<Material> favoriteGifts = new ArrayList<>();
    public final EnumMap<Seasons, Map<Integer, String>> seasonalDialogues = new EnumMap<>(Seasons.class);
    public final String name;

    NPCs(List<Trade> quests, List<Material> favorites, Map<Seasons, Map<Integer, String>> dialogues, String name) {
        this.quests.addAll(quests);
        this.favoriteGifts.addAll(favorites);
        this.seasonalDialogues.putAll(dialogues);
        this.name = name;
    }

    public String getDialogue(Seasons season, int friendshipLevel) {
        Map<Integer, String> levelMap = seasonalDialogues.getOrDefault(season, Map.of());
        return levelMap.getOrDefault(friendshipLevel, "Hey, bro, you’re my best friend!");
    }

    public static NPCs findByName(String name) {
        for (NPCs npc : NPCs.values()) {
            if (npc.name().equalsIgnoreCase(name.trim())) {
                return npc;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }
}
