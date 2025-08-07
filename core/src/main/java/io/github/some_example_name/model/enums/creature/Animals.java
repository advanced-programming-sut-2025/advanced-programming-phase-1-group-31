package io.github.some_example_name.model.enums.creature;

import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import io.github.some_example_name.model.GameAssetManager;
import io.github.some_example_name.model.materials.MaterialType;

import java.util.List;

public enum Animals implements MaterialType {
    // Coop Animals
    CHICKEN("Chicken", 800, CoopsAndBarnsTypes.COOP, List.of(AnimalProducts.EGG, AnimalProducts.LARGE_EGG), 1,
        "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Brown_Chicken.png"),
    DUCK("Duck", 1200, CoopsAndBarnsTypes.LARGE_COOP, List.of(AnimalProducts.DUCK_EGG, AnimalProducts.DUCK_FEATHER), 2,
        "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Duck.png"),
    RABBIT("Rabbit", 8000, CoopsAndBarnsTypes.DELUXE_COOP, List.of(AnimalProducts.WOOL, AnimalProducts.RABBIT_FOOT), 4,
        "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Rabbit.png"),
    DINOSAUR("Dinosaur", 14000, CoopsAndBarnsTypes.LARGE_COOP, List.of(AnimalProducts.DINOSAUR_EGG), 7,
        "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Dinosaur.png"),

    // Barn Animals
    COW("Cow", 1500, CoopsAndBarnsTypes.BARN, List.of(AnimalProducts.MILK, AnimalProducts.LARGE_MILK), 1,
        "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Brown_Cow.png"),
    GOAT("Goat", 4000, CoopsAndBarnsTypes.LARGE_BARN, List.of(AnimalProducts.GOAT_MILK, AnimalProducts.LARGE_GOAT_MILK), 2,
        "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Goat.png"),
    SHEEP("Sheep", 8000, CoopsAndBarnsTypes.DELUXE_BARN, List.of(AnimalProducts.WOOL), 3,
        "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Sheep.png"),
    PIG("Pig", 16000, CoopsAndBarnsTypes.DELUXE_BARN, List.of(AnimalProducts.TRUFFLE), 0,
        "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Pig.png");

    private final String name;
    private final int purchasePrice;
    private final CoopsAndBarnsTypes housingType;
    private final List<AnimalProducts> products;
    private final int daysBetweenProductions;
    private final String imagePath;
    private AnimatedTiledMapTile eating;
    private AnimatedTiledMapTile pet;

    Animals(String name, int purchasePrice, CoopsAndBarnsTypes housingType,
            List<AnimalProducts> products, int daysBetweenProductions, String imagePath) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.housingType = housingType;
        this.products = products;
        this.daysBetweenProductions = daysBetweenProductions;
        this.imagePath = imagePath;
        this.eating = null; // Will be lazily initialized
        this.pet = null;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public CoopsAndBarnsTypes getHousingType() {
        return housingType;
    }

    public List<AnimalProducts> getProducts() {
        return products;
    }

    public AnimalProducts getFirstProduct() {
        return products.get(0);
    }

    public AnimalProducts getSecondProduct() {
        return products.size() > 1 ? products.get(1) : null;
    }

    public int getDaysBetweenProductions() {
        return daysBetweenProductions;
    }

    public boolean canProduceToday(int daysSinceLastProduction, boolean isFed) {
        if (!isFed) return false;
        return daysBetweenProductions == 0 ||
            daysSinceLastProduction >= daysBetweenProductions;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean needsSpecialTool() {
        return this == COW || this == GOAT || this == SHEEP;
    }

    public boolean needsToGoOutside() {
        return this == PIG;
    }

    public AnimatedTiledMapTile getEating() {
        if (eating == null) {
            // Lazy initialization of the animation
            String animalName = name().toLowerCase();
            eating = GameAssetManager.getInstance().getAnimalAnimation(animalName, "eating");
        }
        return eating;
    }
    public AnimatedTiledMapTile getPet() {
        if (pet == null) {
            // Lazy initialization of the animation
            String animalName = name().toLowerCase();
            pet = GameAssetManager.getInstance().getAnimalAnimation(animalName, "pet");
        }
        return pet;
    }

    public static Animals fromName(String name) {
        for (Animals type : values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with name: " + name);
    }
}
