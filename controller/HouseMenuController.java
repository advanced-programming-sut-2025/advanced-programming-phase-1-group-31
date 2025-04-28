package controller;

import model.Result;
import model.enums.HouseMenuCommand;
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
                return function(matcher);
            }
            else if ((matcher = HouseMenuCommand.COOKING_SHOW_RECIPES.getMatcher(input)) != null) {
                showCookingRecipes();
            }
            else if ((matcher = HouseMenuCommand.COOKING_PREPARE.getMatcher(input)) != null) {
                prepareRecipe(matcher.group("recipe"));
            }
            else if ((matcher = HouseMenuCommand.EAT_FOOD.getMatcher(input)) != null) {
                eatFood(matcher.group("food"));
            }
            else if ((matcher = HouseMenuCommand.CRAFTING_SHOW_RECIPES.getMatcher(input)) != null) {
                showCraftingRecipes();
            }
            else if ((matcher = HouseMenuCommand.CRAFTING_CRAFT.getMatcher(input)) != null) {
                craftItem(matcher.group("item"));
            }
            else if ((matcher = HouseMenuCommand.PLACE_ITEM.getMatcher(input)) != null) {
                placeItem(
                        matcher.group("item"),
                        matcher.group("direction")
                );
            }
            else if ((matcher = HouseMenuCommand.CHEAT_ADD_ITEM.getMatcher(input)) != null) {
                cheatAddItem(
                        matcher.group("name"),
                        Integer.parseInt(matcher.group("count"))
                );
            }
            else {
                System.out.println("Invalid command!");
            }
    }

    private void handleRefrigeratorAction(String action, String item) {
        if (action.equals("put")) {
            putInRefrigerator(item);
        } else {
            pickFromRefrigerator(item);
        }
    }

    private void putInRefrigerator(String item) {
        // منطق قرار دادن آیتم در یخچال
    }

    private void pickFromRefrigerator(String item) {
        // منطق برداشتن آیتم از یخچال
    }

    private void showCookingRecipes() {
        // نمایش دستورات آشپزی
    }

    private void prepareRecipe(String recipe) {
        // آماده کردن دستور پخت
    }
    private void eatFood(String food) {

    }

    private void showCraftingRecipes() {
        // نمایش دستورات صنایع دستی
    }

    private void craftItem(String item) {
        // ساخت آیتم
    }

    private void placeItem(String item, String direction) {
        // قرار دادن آیتم در جهت مشخص
    }

    private void cheatAddItem(String name, int count) {
        // اضافه کردن آیتم به صورت تقلب
    }

    private Result function(Matcher matcher){
        String golabi = matcher.group("jsf").trim();
        
    }
}