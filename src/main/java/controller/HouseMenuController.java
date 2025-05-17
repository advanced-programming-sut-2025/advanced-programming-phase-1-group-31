package controller;

import model.Result;
import model.enums.commands.HouseMenuCommand;
import java.util.Scanner;
import java.util.regex.Matcher;

public class HouseMenuController {
    private static final HouseMenuController instance = new HouseMenuController();

    public static HouseMenuController getInstance() {
        return instance;
    }

    public Result run(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;

        if ((matcher = HouseMenuCommand.COOKING_REF_PUT_PICK.getMatcher(input)) != null) {
            return handleRefrigeratorAction(matcher);
        } else if ((matcher = HouseMenuCommand.COOKING_SHOW_RECIPES.getMatcher(input)) != null) {
            return showCookingRecipes(matcher);
        } else if ((matcher = HouseMenuCommand.COOKING_PREPARE.getMatcher(input)) != null) {
            return prepareRecipe(matcher);
        } else if ((matcher = HouseMenuCommand.EAT_FOOD.getMatcher(input)) != null) {
            return eatFood(matcher);
        } else if ((matcher = HouseMenuCommand.CRAFTING_SHOW_RECIPES.getMatcher(input)) != null) {
            return showCraftingRecipes(matcher);
        } else if ((matcher = HouseMenuCommand.CRAFTING_CRAFT.getMatcher(input)) != null) {
            return craftItem(matcher);
        } else if ((matcher = HouseMenuCommand.PLACE_ITEM.getMatcher(input)) != null) {
            return placeItem(matcher);
        } else if ((matcher = HouseMenuCommand.CHEAT_ADD_ITEM.getMatcher(input)) != null) {
            return cheatAddItem(matcher);
        } else {
            return new Result(false, "Invalid command!");
        }
    }

    private Result handleRefrigeratorAction(Matcher matcher) {
        String action = matcher.group("action");
        String item = matcher.group("item");

        if (action.equals("put")) {
            return new Result(true, "Put " + item + " in refrigerator.");
        } else {
            return new Result(true, "Picked " + item + " from refrigerator.");
        }
    }

    private Result showCookingRecipes(Matcher matcher) {
        return new Result(true, "Showing cooking recipes.");
    }

    private Result prepareRecipe(Matcher matcher) {
        String recipe = matcher.group("recipe");
        return new Result(true, "Preparing recipe: " + recipe);
    }

    private Result eatFood(Matcher matcher) {
        String food = matcher.group("food");
        return new Result(true, "Ate food: " + food);
    }

    private Result showCraftingRecipes(Matcher matcher) {
        return new Result(true, "Showing crafting recipes.");
    }

    private Result craftItem(Matcher matcher) {
        String item = matcher.group("item");
        return new Result(true, "Crafted item: " + item);
    }

    private Result placeItem(Matcher matcher) {
        String item = matcher.group("item");
        String direction = matcher.group("direction");
        return new Result(true, "Placed " + item + " to the " + direction);
    }

    private Result cheatAddItem(Matcher matcher) {
        String name = matcher.group("name");
        int count = Integer.parseInt(matcher.group("count"));
        return new Result(true, "Added " + count + "x " + name + " (cheat)");
    }
}
