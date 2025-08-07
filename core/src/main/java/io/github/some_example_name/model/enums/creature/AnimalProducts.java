package io.github.some_example_name.model.enums.creature;

import io.github.some_example_name.model.materials.MaterialType;

public enum AnimalProducts implements MaterialType {
    EGG("Egg", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animal_product/Egg.png", 50),
    LARGE_EGG("Large Egg", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animal_product/Large_Egg.png", 95),
    DUCK_EGG("Duck Egg", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animal_product/Duck_Egg.png", 95),
    DUCK_FEATHER("Duck Feather", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animal_product/Duck_Feather.png", 250),
    WOOL("Wool", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animal_product/Wool.png", 340),
    RABBIT_FOOT("Rabbit Foot", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animal_product/Rabbit%27s_Foot.png", 565),
    DINOSAUR_EGG("Dinosaur Egg", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animal_product/Dinosaur_Egg.png", 350),
    MILK("Milk", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animal_product/Milk.png", 125),
    LARGE_MILK("Large Milk", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animal_product/Large_Milk.png", 190),
    GOAT_MILK("Goat Milk", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animal_product/Goat_Milk.png", 225),
    LARGE_GOAT_MILK("Large Goat Milk", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animal_product/Large_Goat_Milk.png", 345),
    TRUFFLE("Truffle", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animal_product/Truffle.png", 625),
    TRUFFLE_OIL("Truffle Oil", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animal_product/Truffle.png", 0), // Assuming same image as Truffle
    CLOTH("Cloth", "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animal_product/Wool.png", 0); // Assuming same image as Wool

    private final String englishName;
    private final String imagePath;
    private final int price;

    AnimalProducts(String englishName, String imagePath, int price) {
        this.englishName = englishName;
        this.imagePath = imagePath;
        this.price = price;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return englishName + " - " + price + "g";
    }
}
