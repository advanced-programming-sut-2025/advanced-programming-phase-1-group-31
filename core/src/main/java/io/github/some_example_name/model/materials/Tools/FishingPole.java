package io.github.some_example_name.model.materials.Tools;



import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.model.*;
import io.github.some_example_name.model.enums.creature.FishTypes;
import io.github.some_example_name.model.enums.general.Direction;
import io.github.some_example_name.model.enums.general.Seasons;
import io.github.some_example_name.model.enums.general.Weather;
import io.github.some_example_name.model.enums.toolTypes.FishingPoleType;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.MaterialType;
import io.github.some_example_name.model.materials.Products.FishProducts;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class FishingPole implements Tool, Material {
    private FishingPoleType fishingPoleType;

    public FishingPole(FishingPoleType fishingPoleType) {
        this.fishingPoleType = fishingPoleType;
    }

    public void setFishingPoleType(FishingPoleType fishingPoleType) {
        this.fishingPoleType = fishingPoleType;
    }

    public FishingPoleType getFishingPoleType() {
        return fishingPoleType;
    }

    @Override
    public Result work(Direction direction) {
        Player player = App.getCurrentGame().getActivePlayer();
        Vector2 point = direction.apply(player.getPlace());

        // تبدیل موقعیت به tile coordinates
        int tileX = (int)(point.x / 16);
        int tileY = (int)(point.y / 16);
        double energyConsumption = fishingPoleType.getEnergyConsumption();
        if (player.getSkills().getFishingLevel() == 4) {
            energyConsumption--;
        }

        // Check energy
        if (player.getEnergy().getEnergyAmount() < energyConsumption) {
            return new Result(false, "You don't have enough energy to use the FishingPole.");
        }
        FarmMap farmMap = App.getCurrentGame().getMapForPlayer(player, MapType.BEACH);

        // بررسی آیا tile در منطقه قابل کشت است
        boolean isFhising = false;
        MapLayer objectLayer = farmMap.getTmxMap().getLayers().get("object1");
        if (objectLayer == null) {
            return new Result(false, "You can't use the FishingPole here. Your energy was wasted!");
        }


        for (MapObject obj : objectLayer.getObjects()) {
            if (obj instanceof PolygonMapObject && "fishing".equals(obj.getName())) {
                Polygon polygon = ((PolygonMapObject) obj).getPolygon();

                // تبدیل مختصات tile به مختصات دنیا (مرکز tile)
                float worldX = tileX * 16 + 8; // 16 = tileSize, 8 = نیمه tile
                float worldY = tileY * 16 + 8;

                if (polygon.contains(worldX, worldY)) {
                    isFhising = true;
                    break;
                }
            }
        }
        if (!isFhising) {
            player.getEnergy().changeEnergy(-energyConsumption);
            return new Result(false, "You can't use the FishingPole here. Your energy was wasted!");
        }
        double poleMultiplier = ProductQualityCalculator.getPoleMultiplier(this.getFishingPoleType());
        Weather weather = App.getCurrentGame().getTimeAndDate().getWeather();
        Seasons season = App.getCurrentGame().getTimeAndDate().getSeason();
        Random random = new Random();
        int skill = player.getSkills().getFishingLevel();
        double M = ProductQualityCalculator.getSeasonalMultiplier(weather);
        double R = ThreadLocalRandom.current().nextDouble(0, 1);

        int count = (int) Math.min(6, R * M * (skill + 2));

        List<FishTypes> validFish = Arrays.stream(FishTypes.values())
            .filter(f -> !f.isLegendary() && f.getSeason() == season)
            .collect(Collectors.toList());

        if (skill >= 4) {
            List<FishTypes> legendary = Arrays.stream(FishTypes.values())
                .filter(f -> f.isLegendary() && f.getSeason() == season)
                .toList();
            validFish.addAll(legendary);
        }

        if (validFish.isEmpty()) {
            return new Result(false, "No fish available to catch in this season!");
        }

        List<FishProducts> caughtFish = new ArrayList<>();
        StringBuilder fishDetails = new StringBuilder();

        for (int i = 0; i < count; i++) {
            FishTypes selected = validFish.get(random.nextInt(validFish.size()));

            double qualityScore = random.nextDouble() * (skill + 2) * poleMultiplier / (7 - M);
            var quality = ProductQualityCalculator.calculateQualityScore(qualityScore);
            FishProducts fish = new FishProducts(selected, quality, 1);
            caughtFish.add(fish);
            fishDetails.append("- ").append(fish.getName())
                .append(" | Quality: ").append(fish.getQuality())
                .append("\n");
            Result result = App.getCurrentGame().getActivePlayer().getInventory().addElementToBackpack(fish, 1);
            if (!result.Success()) {
                String message = "Fishing successful! You caught:\n" + fishDetails + "but" +
                    result.Message();
                return new Result(true, message);
            }
        }

        if (caughtFish.isEmpty()) {
            return new Result(false, "You didn't catch any fish this time!");
        }

        player.getEnergy().changeEnergy((-1 * this.getFishingPoleType().getEnergyConsumption()));
        player.getSkills().setFishingLevel(40);
        String message = "Fishing successful! You caught:\n" + fishDetails;
        return new Result(true, message);


    }

    @Override
    public MaterialType getType() {
        return fishingPoleType;
    }

    @Override
    public String getTexturePath() {
        return fishingPoleType.getFishingPoleName();
    }

    @Override
    public String getName() {
        return "Fishing Pole " + fishingPoleType.name();
    }

    @Override
    public int baseSellPrice() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tool tool)) return false;
        return this.getClass().equals(tool.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }
}
