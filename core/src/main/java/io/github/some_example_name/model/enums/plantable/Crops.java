package io.github.some_example_name.model.enums.plantable;

import io.github.some_example_name.model.enums.general.Seasons;
import io.github.some_example_name.model.materials.MaterialType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Crops implements MaterialType {
    Blue_Jazz("Blue Jazz", Seeds.JazzSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/jazz/Blue_Jazz_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/jazz/Blue_Jazz_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/jazz/Blue_Jazz_Stage_3.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/jazz/Blue_Jazz_Stage_4.png")
        ), 7, true, null, 50, true, 45, List.of(Seasons.Spring), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/jazz/Blue_Jazz.png"),

    Carrot("Carrot", Seeds.CarrotSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/carrot/Carrot_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/carrot/Carrot_Stage_2.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/carrot/Carrot_Stage_3.png")
        ), 3, true, null, 35, true, 75, List.of(Seasons.Spring), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/carrot/Carrot.png"),

    Cauliflower("Cauliflower", Seeds.CauliflowerSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/cauliflowers/Cauliflower_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/cauliflowers/Cauliflower_Stage_2.png"),
            new Stage(4, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/cauliflowers/Cauliflower_Stage_3.png"),
            new Stage(4, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/cauliflowers/Cauliflower_Stage_4.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/cauliflowers/Cauliflower_Stage_5.png")
        ), 12, true, null, 175, true, 75, List.of(Seasons.Spring), true, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/cauliflowers/Cauliflower.png"),

    Coffee_Bean("Coffee Bean", Seeds.CoffeeBean,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/coffeebean/Coffee_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/coffeebean/Coffee_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/coffeebean/Coffee_Stage_3.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/coffeebean/Coffee_Stage_4.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/coffeebean/Coffee_Stage_5.png")
        ), 10, false, 2, 15, false, 0, List.of(Seasons.Spring, Seasons.Summer), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/coffeebean/Coffee_Bean.png"),

    Garlic("Garlic", Seeds.GarlicSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/garlic/Garlic_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/garlic/Garlic_Stage_2.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/garlic/Garlic_Stage_3.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/garlic/Garlic_Stage_4.png")
        ), 4, true, null, 60, true, 20, List.of(Seasons.Spring), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/garlic/Garlic.png"),

    Green_Bean("Green Bean", Seeds.BeanStarter,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/beenstarter/Green_Bean_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/beenstarter/Green_Bean_Stage_2.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/beenstarter/Green_Bean_Stage_3.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/beenstarter/Green_Bean_Stage_4.png"),
            new Stage(4, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/beenstarter/Green_Bean_Stage_5.png")
        ), 10, false, 3, 40, true, 25, List.of(Seasons.Spring), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/beenstarter/Green_Bean.png"),

    Kale("Kale", Seeds.KaleSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/kale/Kale_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/kale/Kale_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/kale/Kale_Stage_3.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/kale/Kale_Stage_4.png")
        ), 6, true, null, 110, true, 50, List.of(Seasons.Spring), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/kale/Kale.png"),

    Parsnip("Parsnip", Seeds.ParsnipSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/parsnip/Parsnip_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/parsnip/Parsnip_Stage_2.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/parsnip/Parsnip_Stage_3.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/parsnip/Parsnip_Stage_4.png")
        ), 4, true, null, 35, true, 25, List.of(Seasons.Spring), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/parsnip/Parsnip.png"),

    Potato("Potato", Seeds.PotatoSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/potato/Potato_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/potato/Potato_Stage_2.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/potato/Potato_Stage_3.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/potato/Potato_Stage_4.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/potato/Potato_Stage_5.png")
        ), 6, true, null, 80, true, 25, List.of(Seasons.Spring), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/potato/Potato.png"),

    Rhubarb("Rhubarb", Seeds.RhubarbSeeds,
        List.of(
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/rhubarb/Rhubarb_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/rhubarb/Rhubarb_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/rhubarb/Rhubarb_Stage_3.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/rhubarb/Rhubarb_Stage_4.png"),
            new Stage(4, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/rhubarb/Rhubarb_Stage_5.png")
        ), 13, true, null, 220, false, 0, List.of(Seasons.Spring), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/rhubarb/Rhubarb.png"),

    Strawberry("Strawberry", Seeds.StrawberrySeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/strawberry/Strawberry_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/strawberry/Strawberry_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/strawberry/Strawberry_Stage_3.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/strawberry/Strawberry_Stage_4.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/strawberry/Strawberry_Stage_5.png")
        ), 8, false, 4, 120, true, 50, List.of(Seasons.Spring), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/strawberry/Strawberry.png"),

    Tulip("Tulip", Seeds.TulipBulb,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/TulipBulb/Tulip_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/TulipBulb/Tulip_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/TulipBulb/Tulip_Stage_3.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/TulipBulb/Tulip_Stage_4.png")
        ), 6, true, null, 30, true, 45, List.of(Seasons.Spring), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/TulipBulb/Tulip.png"),

    UnmilledRice("Unmilled Rice", Seeds.RiceShoot,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/RiceShoot/Unmilled_Rice_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/RiceShoot/Unmilled_Rice_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/RiceShoot/Unmilled_Rice_Stage_3.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/RiceShoot/Unmilled_Rice_Stage_4.png")
        ), 8, true, null, 30, true, 3, List.of(Seasons.Spring), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/RiceShoot/Unmilled_Rice.png"),

    Blueberry("Blueberry", Seeds.BlueberrySeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Blueberry/Blueberry_Stage_1.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Blueberry/Blueberry_Stage_2.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Blueberry/Blueberry_Stage_3.png"),
            new Stage(4, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Blueberry/Blueberry_Stage_4.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Blueberry/Blueberry_Stage_5.png")
        ), 13, false, 4, 50, true, 25, List.of(Seasons.Summer), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Blueberry/Blueberry.png"),

    Corn("Corn", Seeds.CornSeeds,
        List.of(
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Corn/Corn_Stage_1.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Corn/Corn_Stage_2.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Corn/Corn_Stage_3.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Corn/Corn_Stage_4.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Corn/Corn_Stage_5.png")
        ), 14, false, 4, 50, true, 25, List.of(Seasons.Summer, Seasons.Fall), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Corn/Corn.png"),

    Hops("Hops", Seeds.HopsStarter,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/HopsStarter/Hops_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/HopsStarter/Hops_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/HopsStarter/Hops_Stage_3.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/HopsStarter/Hops_Stage_4.png"),
            new Stage(4, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/HopsStarter/Hops_Stage_5.png")
        ), 11, false, 1, 25, true, 45, List.of(Seasons.Summer), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/HopsStarter/Hops.png"),

    Hot_Pepper("Hot Pepper", Seeds.PepperSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Pepper/Hot_Pepper_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Pepper/Hot_Pepper_Stage_2.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Pepper/Hot_Pepper_Stage_3.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Pepper/Hot_Pepper_Stage_4.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Pepper/Hot_Pepper_Stage_5.png")
        ), 5, false, 3, 40, true, 13, List.of(Seasons.Summer), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Pepper/Hot_Pepper.png"),

    Melon("Melon", Seeds.MelonSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Melon/Melon_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Melon/Melon_Stage_2.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Melon/Melon_Stage_3.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Melon/Melon_Stage_4.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Melon/Melon_Stage_5.png")
        ), 12, true, null, 250, true, 113, List.of(Seasons.Summer), true, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Melon/Melon.png"),

    Poppy("Poppy", Seeds.PoppySeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Poppy/Poppy_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Poppy/Poppy_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Poppy/Poppy_Stage_3.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Poppy/Poppy_Stage_4.png")
        ), 7, true, null, 140, true, 45, List.of(Seasons.Summer), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Poppy/Poppy.png"),

    Radish("Radish", Seeds.RadishSeeds,
        List.of(
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Radish/Radish_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Radish/Radish_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Radish/Radish_Stage_3.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Radish/Radish_Stage_4.png")
        ), 6, true, null, 90, true, 45, List.of(Seasons.Summer), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Radish/Radish.png"),

    Red_Cabbage("Red Cabbage", Seeds.RedCabbageSeeds,
        List.of(
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/RedCabbage/Red_Cabbage_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/RedCabbage/Red_Cabbage_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/RedCabbage/Red_Cabbage_Stage_3.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/RedCabbage/Red_Cabbage_Stage_4.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/RedCabbage/Red_Cabbage_Stage_5.png")
        ), 9, true, null, 260, true, 75, List.of(Seasons.Summer), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/RedCabbage/Red_Cabbage.png"),

    Starfruit("Starfruit", Seeds.StarfruitSeeds,
        List.of(
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Starfruit/Starfruit_Stage_1.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Starfruit/Starfruit_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Starfruit/Starfruit_Stage_3.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Starfruit/Starfruit_Stage_4.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Starfruit/Starfruit_Stage_5.png")
        ), 13, true, null, 750, true, 125, List.of(Seasons.Summer), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Starfruit/Starfruit.png"),

    Summer_Spangle("Summer Spangle", Seeds.SpangleSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Spangle/Summer_Spangle_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Spangle/Summer_Spangle_Stage_2.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Spangle/Summer_Spangle_Stage_3.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Spangle/Summer_Spangle_Stage_4.png")
        ), 8, true, null, 90, true, 45, List.of(Seasons.Summer), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Spangle/Summer_Spangle.png"),

    Summer_Squash("Summer Squash", Seeds.SummerSquashSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/SummerSquash/Summer_Squash_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/SummerSquash/Summer_Squash_Stage_2.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/SummerSquash/Summer_Squash_Stage_3.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/SummerSquash/Summer_Squash_Stage_4.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/SummerSquash/Summer_Squash_Stage_5.png")
        ), 6, false, 3, 45, true, 63, List.of(Seasons.Summer), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/SummerSquash/Summer_Squash.png"),

    Sunflower("Sunflower", Seeds.SunflowerSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Sunflower/Sunflower_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Sunflower/Sunflower_Stage_2.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Sunflower/Sunflower_Stage_3.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Sunflower/Sunflower_Stage_4.png")
        ), 8, true, null, 80, true, 45, List.of(Seasons.Summer, Seasons.Fall), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Sunflower/Sunflower.png"),

    Tomato("Tomato", Seeds.TomatoSeeds,
        List.of(
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Tomato/Tomato_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Tomato/Tomato_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Tomato/Tomato_Stage_3.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Tomato/Tomato_Stage_4.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Tomato/Tomato_Stage_5.png")
        ), 11, false, 4, 60, true, 20, List.of(Seasons.Summer), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Tomato/Tomato.png"),

    Wheat("Wheat", Seeds.WheatSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Wheat/Wheat_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Wheat/Wheat_Stage_2.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Wheat/Wheat_Stage_3.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Wheat/Wheat_Stage_4.png")
        ), 4, true, null, 25, false, 0, List.of(Seasons.Summer, Seasons.Fall), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Wheat/Wheat.png"),

    Amaranth("Amaranth", Seeds.AmaranthSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Amaranth/Amaranth_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Amaranth/Amaranth_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Amaranth/Amaranth_Stage_3.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Amaranth/Amaranth_Stage_4.png")
        ), 7, true, null, 150, true, 50, List.of(Seasons.Fall), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Amaranth/Amaranth.png"),

    Artichoke("Artichoke", Seeds.ArtichokeSeeds,
        List.of(
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Artichoke/Artichoke_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Artichoke/Artichoke_Stage_2.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Artichoke/Artichoke_Stage_3.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Artichoke/Artichoke_Stage_4.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Artichoke/Artichoke_Stage_5.png")
        ), 8, true, null, 160, true, 30, List.of(Seasons.Fall), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Artichoke/Artichoke.png"),

    Beet("Beet", Seeds.BeetSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/beet/Beet_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/beet/Beet_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/beet/Beet_Stage_3.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/beet/Beet_Stage_4.png")
        ), 6, true, null, 100, true, 30, List.of(Seasons.Fall), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/beet/Beet.png"),

    BokChoy("Bok Choy", Seeds.BokChoySeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/BokChoy/Bok_Choy_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/BokChoy/Bok_Choy_Stage_2.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/BokChoy/Bok_Choy_Stage_3.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/BokChoy/Bok_Choy_Stage_4.png")
        ), 4, true, null, 80, true, 25, List.of(Seasons.Fall), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/BokChoy/Bok_Choy.png"),

    Broccoli("Broccoli", Seeds.BroccoliSeeds,
        List.of(
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Broccoli/Broccoli_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Broccoli/Broccoli_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Broccoli/Broccoli_Stage_3.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Broccoli/Broccoli_Stage_4.png")
        ), 8, false, 4, 70, true, 63, List.of(Seasons.Fall), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Broccoli/Broccoli.png"),

    Cranberries("Cranberries", Seeds.CranberrySeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Cranberry/Cranberry_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Cranberry/Cranberry_Stage_2.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Cranberry/Cranberry_Stage_3.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Cranberry/Cranberry_Stage_4.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Cranberry/Cranberry_Stage_5.png")
        ), 7, false, 5, 75, true, 38, List.of(Seasons.Fall), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Cranberry/Cranberries.png"),

    Eggplant("Eggplant", Seeds.EggplantSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Eggplant/Eggplant_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Eggplant/Eggplant_Stage_2.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Eggplant/Eggplant_Stage_3.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Eggplant/Eggplant_Stage_4.png")
        ), 5, false, 5, 60, true, 20, List.of(Seasons.Fall), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Eggplant/Eggplant.png"),

    Fairy_Rose("Fairy Rose", Seeds.FairySeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Fairy/Fairy_Rose_Stage_1.png"),
            new Stage(4, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Fairy/Fairy_Rose_Stage_2.png"),
            new Stage(4, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Fairy/Fairy_Rose_Stage_3.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Fairy/Fairy_Rose_Stage_4.png")
        ), 12, true, null, 290, true, 45, List.of(Seasons.Fall), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Fairy/Fairy_Rose.png"),

    Grape("Grape", Seeds.GrapeStarter,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/GrapeStarter/Grape_Stage_1.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/GrapeStarter/Grape_Stage_2.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/GrapeStarter/Grape_Stage_3.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/GrapeStarter/Grape_Stage_4.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/GrapeStarter/Grape_Stage_5.png")
        ), 10, false, 3, 80, true, 38, List.of(Seasons.Fall), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/GrapeStarter/Grape.png"),

    Pumpkin("Pumpkin", Seeds.PumpkinSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Pumpkin/Pumpkin_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Pumpkin/Pumpkin_Stage_2.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Pumpkin/Pumpkin_Stage_3.png"),
            new Stage(4, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Pumpkin/Pumpkin_Stage_4.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Pumpkin/Pumpkin_Stage_5.png")
        ), 13, true, null, 320, false, 0, List.of(Seasons.Fall), true, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Pumpkin/Pumpkin.png"),

    Yam("Yam", Seeds.YamSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Yam/Yam_Stage_1.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Yam/Yam_Stage_2.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Yam/Yam_Stage_3.png"),
            new Stage(3, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Yam/Yam_Stage_4.png")
        ), 10, true, null, 160, true, 45, List.of(Seasons.Fall), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Yam/Yam.png"),

    Sweet_Gem_Berry("Sweet Gem Berry", Seeds.RareSeed,
        List.of(
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Rare/Sweet_Gem_Berry_Stage_1.png"),
            new Stage(4, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Rare/Sweet_Gem_Berry_Stage_2.png"),
            new Stage(6, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Rare/Sweet_Gem_Berry_Stage_3.png"),
            new Stage(6, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Rare/Sweet_Gem_Berry_Stage_4.png"),
            new Stage(6, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Rare/Sweet_Gem_Berry_Stage_5.png")
        ), 24, true, null, 3000, false, 0, List.of(Seasons.Fall), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Rare/Sweet_Gem_Berry.png"),

    PowderMelon("Powder Melon", Seeds.PowderMelonSeeds,
        List.of(
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/PowderMelon/Powdermelon_Stage_1.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/PowderMelon/Powdermelon_Stage_2.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/PowderMelon/Powdermelon_Stage_3.png"),
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/PowderMelon/Powdermelon_Stage_4.png"),
            new Stage(1, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/PowderMelon/Powdermelon_Stage_5.png")
        ), 7, true, null, 60, true, 63, List.of(Seasons.Winter), true, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/PowderMelon/Powdermelon.png"),

    Ancient_Fruit("Ancient Fruit", Seeds.AncientSeeds,
        List.of(
            new Stage(2, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Ancient/Ancient_Fruit_Stage_1.png"),
            new Stage(7, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Ancient/Ancient_Fruit_Stage_2.png"),
            new Stage(7, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Ancient/Ancient_Fruit_Stage_3.png"),
            new Stage(7, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Ancient/Ancient_Fruit_Stage_4.png"),
            new Stage(5, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Ancient/Ancient_Fruit_Stage_5.png")
        ), 28, false, 7, 550, false, 0, List.of(Seasons.Spring, Seasons.Summer, Seasons.Fall), false, "project/Stardew_Valley_Images-main/Stardew_Valley_Images-main/Crops/Ancient/Ancient_Fruit.png");
    // Rest of the class remains the same...
    private final String displayName;
    private final Seeds source;
    private final List<Stage> stages;
    private final int totalHarvestTime;
    private final boolean oneTime;
    private final Integer regrowthTime;
    private final int baseSellPrice;
    private final boolean isEdible;
    private final int energy;
    private final List<Seasons> seasons;
    private final boolean canBecomeGiant;
    private final String imagePath;

    public record Stage(int days, String imagePath) {}

    Crops(String displayName, Seeds source, List<Stage> stages, int totalHarvestTime, boolean oneTime, Integer regrowthTime,
          int baseSellPrice, boolean isEdible, int energy, List<Seasons> seasons, boolean canBecomeGiant, String imagePath) {
        this.displayName = displayName;
        this.source = source;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.oneTime = oneTime;
        this.regrowthTime = regrowthTime;
        this.baseSellPrice = baseSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        this.seasons = seasons;
        this.canBecomeGiant = canBecomeGiant;
        this.imagePath = imagePath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Seeds getSource() {
        return source;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public Integer getRegrowthTime() {
        return regrowthTime;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public int getEnergy() {
        return energy;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isCanBecomeGiant() {
        return canBecomeGiant;
    }

    @Override
    public String toString() {
        return displayName;
    }

    private static final Map<String, Crops> nameCrop = new HashMap<>();

    static {
        for (Crops crop : values()) {
            nameCrop.put(crop.getDisplayName().toLowerCase(), crop);
        }
    }

    public static Crops findByName(String name) {
        return nameCrop.get(name.toLowerCase());
    }
}
