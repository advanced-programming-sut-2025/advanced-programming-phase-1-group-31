package model.enums.creature;

import model.materials.Material;
import model.materials.MaterialType;

import java.util.List;

public enum Animals implements MaterialType {
    // Coop Animals
    CHICKEN("Chicken", 800, CoopsAndBarnsTypes.COOP, List.of(AnimalProducts.EGG, AnimalProducts.LARGE_EGG), 1),
    DUCK("Duck", 1200, CoopsAndBarnsTypes.LARGE_COOP, List.of(AnimalProducts.DUCK_EGG, AnimalProducts.DUCK_FEATHER), 2),
    RABBIT("Rabbit", 8000, CoopsAndBarnsTypes.DELUXE_COOP, List.of(AnimalProducts.WOOL, AnimalProducts.RABBIT_FOOT), 4),
    DINOSAUR("Dinosaur", 14000, CoopsAndBarnsTypes.LARGE_COOP, List.of(AnimalProducts.DINOSAUR_EGG), 7),

    // Barn Animals
    COW("Cow", 1500, CoopsAndBarnsTypes.BARN, List.of(AnimalProducts.MILK, AnimalProducts.LARGE_MILK), 1),
    GOAT("Goat", 4000, CoopsAndBarnsTypes.LARGE_BARN, List.of(AnimalProducts.GOAT_MILK, AnimalProducts.LARGE_GOAT_MILK), 2),
    SHEEP("Sheep", 8000, CoopsAndBarnsTypes.DELUXE_BARN, List.of(AnimalProducts.WOOL), 3),
    PIG("Pig", 16000, CoopsAndBarnsTypes.DELUXE_BARN, List.of(AnimalProducts.TRUFFLE), 0);

    private final String name;
    private final int purchasePrice;
    private final CoopsAndBarnsTypes housingType;
    private final List<AnimalProducts> products;
    private final int daysBetweenProductions;

    Animals(String name, int purchasePrice, CoopsAndBarnsTypes housingType,
            List<AnimalProducts> products, int daysBetweenProductions) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.housingType = housingType;
        this.products = products;
        this.daysBetweenProductions = daysBetweenProductions;
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

    public boolean needsSpecialTool() {
        return this == COW || this == GOAT || this == SHEEP;
    }

    public boolean needsToGoOutside() {
        return this == PIG;
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