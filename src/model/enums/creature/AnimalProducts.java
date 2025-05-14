package model.enums.creature;

public enum AnimalProducts {
        EGG("Egg", 50),
        LARGE_EGG("Large Egg", 95),
        DUCK_EGG("Duck Egg", 95),
        DUCK_FEATHER("Duck Feather", 250),
        WOOL("Wool", 340),
        RABBIT_FOOT("Rabbit Foot", 565),
        DINOSAUR_EGG("Dinosaur Egg", 350),
        MILK("Milk", 125),
        LARGE_MILK("Large Milk", 190),
        GOAT_MILK("Goat Milk", 225),
        LARGE_GOAT_MILK("Large Goat Milk", 345),
        TRUFFLE("Truffle", 625),
        TRUFFLE_OIL("Truffle Oil", 0), // Weight not specified
        CLOTH("Cloth", 0); // Weight not specified

        private final String englishName;
        private final int price;

        AnimalProducts(String englishName, int price) {
                this.englishName = englishName;
            this.price = price;
        }

        public String getEnglishName() {
                return englishName;
        }

        public int getPrice() {
                return price;
        }

        @Override
        public String toString() {
                return englishName + " - " + price + "g";
        }
}