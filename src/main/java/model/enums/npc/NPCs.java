package model.enums.npc;

import model.Trade;
import model.enums.crafting.Foods;
import model.enums.creature.AnimalProducts;
import model.enums.foragings.ForagingMinerals;
import model.enums.general.Seasons;
import model.materials.Foraging.ForagingMineral;
import model.materials.Material;
import model.materials.MaterialType;
import model.materials.Products.AnimalProduct;

import java.util.*;

public enum NPCs implements MaterialType {
    SEBASTIAN(
            List.of(
                    new Trade(new ForagingMineral(ForagingMinerals.Iron_Ore), null, 50, 0),
                    new Trade(new Food(ّFoods.PUMPKIN_PIE), 5000),
                    new Trade(new ForagingMineral(ForagingMinerals.Stone), null, 150, 50)
            ),
            List.of(
                    new AnimalProduct(AnimalProducts.WOOL),
                    new Food(Foods.PumpkinPie),
                    new Food(Foods.Pizza)
            ),
            Map.of(
                    Seasons.Spring, Map.of(1, "Hey. The rain is kinda nice, I guess.", 2, "It’s peaceful, don’t you think?"),
                    Seasons.Summer, Map.of(1, "Ugh. I hate the sun.", 2, "Maybe we could hang out in the shade sometime."),
                    Seasons.Fall,   Map.of(1, "Fall is fine. Lots of shadows.", 2, "I like the mood Fall brings. You too?"),
                    Seasons.Winter, Map.of(1, "Snow. Cool.", 2, "I love coding when it’s snowing outside.")
            )
    ),

    ABIGAIL(
            List.of(
                    new Trade(new Food(Foods.PumpkinPie), 500),
                    new Trade(new Food(Foods.CactusFruit), 1000),
                    new Trade(new Food(Foods.Wheat), 50)
            ),
            List.of(
                    new ForagingMineral(ForagingMinerals.Amethyst),
                    new Food(Foods.ChocolateCake),
                    new Food(Foods.Coffee)
            ),
            Map.of(
                    Seasons.Spring, Map.of(1, "Flowers are blooming!", 2, "I picked a daffodil for you."),
                    Seasons.Summer, Map.of(1, "It’s too hot...", 2, "Want to go on an adventure today?"),
                    Seasons.Fall,   Map.of(1, "I love the mystery of Fall.", 2, "The crunch of leaves underfoot is so satisfying!"),
                    Seasons.Winter, Map.of(1, "Staying inside with hot chocolate sounds great.", 2, "Let’s play some games later! Cool? ")
            )
    ),

    HARVEY(
            List.of(
                    new Trade(null, null, 12, 750),
                    new Trade(new Food(Foods.Salmon), 0),
                    new Trade(null, null, 0, 5) // 5 wine bottles
            ),
            List.of(
                    new Food(Foods.Coffee),
                    new Food(Foods.Pickle),
                    new Food(Foods.Wine)
            ),
            Map.of(
                    Seasons.Spring, Map.of(1, "Stay healthy!", 2, "Morning walks are my favorite. Want to join?"),
                    Seasons.Summer, Map.of(1, "Don't forget sunscreen.", 2, "Warm weather is great for relaxing, too."),
                    Seasons.Fall,   Map.of(1, "Colds are common now.", 2, "Let’s enjoy this cozy season together."),
                    Seasons.Winter, Map.of(1, "Be careful not to slip on ice.", 2, "Winter snow is quite beautiful, isn't it?")
            )
    ),

    LEAH(
            List.of(
                    new Trade(new ForagingMineral(ForagingMinerals.Hardwood), 500),
                    new Trade(new Food(Foods.Salmon), 0),
                    new Trade(null, null, 200, 0)
            ),
            List.of(
                    new Food(Foods.Salad),
                    new Food(Foods.Wine),
                    new Food(Foods.FruitSalad)
            ),
            Map.of(
                    Seasons.Spring, Map.of(1, "Inspiration is everywhere in Spring!", 2, "Want to go sketch in the forest?"),
                    Seasons.Summer, Map.of(1, "I’m working on a new sculpture.", 2, "You inspire me to create more."),
                    Seasons.Fall,   Map.of(1, "The forest is full of color.", 2, "Let’s collect some leaves for my art."),
                    Seasons.Winter, Map.of(1, "I stay indoors more now.", 2, "This season lets me reflect... with you.")
            )
    ),

    ROBIN(
            List.of(
                    new Trade(null, null, 80, 1000),
                    new Trade(new ForagingMineral(ForagingMinerals.Iron_Ore), 0, 6, 0),
                    new Trade(null, null, 1000, 2500)
            ),
            List.of(
                    new Food(Foods.Spaghetti),
                    new ForagingMineral(ForagingMinerals.Wood),
                    new ForagingMineral(ForagingMinerals.Iron_Ore)
            ),
            Map.of(
                    Seasons.Spring, Map.of(1, "Spring keeps me busy!", 2, "Want to help with a new project?"),
                    Seasons.Summer, Map.of(1, "I love building outdoors.", 2, "Thanks for the help last time!"),
                    Seasons.Fall,   Map.of(1, "The cool air is perfect for work.", 2, "Fall upgrades are my favorite."),
                    Seasons.Winter, Map.of(1, "Cold, but work never stops!", 2, "Come inside, it’s warm by the fire.")
            )
    );

    public final String name;
    public final ArrayList<Trade> quests = new ArrayList<>();
    public final ArrayList<Material> favoriteGifts = new ArrayList<>();
    public final EnumMap<Seasons, Map<Integer, String>> seasonalDialogues = new EnumMap<>(Seasons.class);

    NPCs(List<Trade> quests, List<Material> favorites, Map<Seasons, Map<Integer, String>> dialogues, String name) {
        this.name = name;
        this.quests.addAll(quests);
        this.favoriteGifts.addAll(favorites);
        this.seasonalDialogues.putAll(dialogues);
    }

    public String getDialogue(Seasons season, int friendshipLevel) {
        Map<Integer, String> levelMap = seasonalDialogues.getOrDefault(season, Map.of());
        return levelMap.getOrDefault(friendshipLevel, "...");
    }

    public String getName() {
        return name;
    }

    public ArrayList<Trade> getQuests() {
        return quests;
    }

    public ArrayList<Material> getFavoriteGifts() {
        return favoriteGifts;
    }
}
