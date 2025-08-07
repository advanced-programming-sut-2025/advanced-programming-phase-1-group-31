//package io.github.some_example_name.View.ui;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.*;
//import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import io.github.some_example_name.model.App;
//import io.github.some_example_name.model.Backpack;
//import io.github.some_example_name.model.Result;
//import io.github.some_example_name.model.materials.Material;
//
//import java.util.Map;
//
//public class InventoryDisplay {
//    private final Backpack backpack;
//    private Stage stage;
//    private Table inventoryTable;
//    private Skin skin;
//    private boolean isInventoryOpen = false;
//    private Material selectedItem;
//    private int selectedAmount = 1;
//
//    public InventoryDisplay(Backpack backpack , Skin skin) {
//        this.backpack = backpack;
//        this.skin = skin;
//        this.stage = new Stage();
//
//        createInventoryUI();
//    }
//
//    private void createInventoryUI() {
//        inventoryTable = new Table(skin);
//        inventoryTable.setFillParent(true);
//        inventoryTable.center();
//        inventoryTable.setVisible(false);
//
//        stage.addActor(inventoryTable);
//    }
//
//    public void openInventory() {
//        isInventoryOpen = true;
//        selectedItem = null;
//        selectedAmount = 1;
//        refreshInventoryUI();
//    }
//
//    private void refreshInventoryUI() {
//        inventoryTable.clear();
//
//        // عنوان
//        inventoryTable.add("Inventory - Select Items to Sell").colspan(4).row();
//
//        // لیست آیتم‌ها در اینونتوری
//        for (Map.Entry<Material, Integer> entry : backpack.getElements().entrySet()) {
//            Material material = entry.getKey();
//            int count = entry.getValue();
//
//            // دکمه انتخاب آیتم
//            TextButton selectButton = new TextButton(material.getName() + " (" + count + ")", skin);
//            selectButton.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    selectedItem = material;
//                    selectedAmount = 1;
//                    refreshInventoryUI();
//                }
//            });
//
//            // هایلایت کردن آیتم انتخاب شده
//            if (material.equals(selectedItem)) {
//                selectButton.setColor(Color.YELLOW);
//            }
//
//            inventoryTable.add(selectButton).pad(5).width(200);
//        }
//        inventoryTable.row();
//
//        // نمایش آیتم انتخاب شده و کنترل‌های مقدار
//        if (selectedItem != null) {
//            int maxAmount = backpack.howManyInBackpack(selectedItem);
//
//            Label selectedLabel = new Label("Selected: " + selectedItem.getName(), skin);
//            inventoryTable.add(selectedLabel).colspan(2).pad(10).row();
//
//            // کاهش مقدار
//            TextButton decreaseButton = new TextButton("-", skin);
//            decreaseButton.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    if (selectedAmount > 1) {
//                        selectedAmount--;
//                        refreshInventoryUI();
//                    }
//                }
//            });
//
//            // نمایش مقدار انتخاب شده
//            Label amountLabel = new Label(String.valueOf(selectedAmount), skin);
//
//            // افزایش مقدار
//            TextButton increaseButton = new TextButton("+", skin);
//            increaseButton.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    if (selectedAmount < maxAmount) {
//                        selectedAmount++;
//                        refreshInventoryUI();
//                    }
//                }
//            });
//
//            // اسلایدر مقدار
//            Slider amountSlider = new Slider(1, maxAmount, 1, false, skin);
//            amountSlider.setValue(selectedAmount);
//            amountSlider.addListener(new ChangeListener() {
//                @Override
//                public void changed(ChangeEvent event, Actor actor) {
//                    selectedAmount = (int) amountSlider.getValue();
//                    refreshInventoryUI();
//                }
//            });
//
//            inventoryTable.add(decreaseButton).width(50);
//            inventoryTable.add(amountLabel).width(50);
//            inventoryTable.add(increaseButton).width(50);
//            inventoryTable.add(amountSlider).width(200).row();
//
//            // دکمه فروش
//            TextButton sellButton = new TextButton("Sell " + selectedAmount + " for " +
//                (selectedItem.baseSellPrice() * selectedAmount) + " coins", skin);
//            sellButton.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    Result result = sellItem(selectedItem, selectedAmount);
//                    showNotification(result.Message(), result.Success() ? Color.GREEN : Color.RED);
//                    if (result.Success()) {
//                        selectedItem = null;
//                        refreshInventoryUI();
//                    }
//                }
//            });
//
//            inventoryTable.add(sellButton).colspan(4).padTop(20).row();
//        }
//
//        // دکمه بستن
//        TextButton closeButton = new TextButton("Close Inventory", skin);
//        closeButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                closeInventory();
//            }
//        });
//        inventoryTable.add(closeButton).colspan(4).padTop(20).row();
//
//        inventoryTable.setVisible(true);
//    }
//
//    private Result sellItem(Material item, int amount) {
//        // بررسی وجود آیتم در اینونتوری
//        if (!backpack.getElements().containsKey(item)) {
//            return new Result(false, "Item not found in inventory!");
//        }
//
//        // بررسی مقدار کافی
//        int currentAmount = backpack.howManyInBackpack(item);
//        if (amount > currentAmount) {
//            return new Result(false, "Not enough items in inventory!");
//        }
//
//        // محاسبه قیمت کل
//        int totalPrice = item.baseSellPrice() * amount;
//
//        // حذف آیتم از اینونتوری
//        backpack.removeElementFromBackpack(item, amount);
//
//        // افزودن پول به بازیکن
//        App.getCurrentGame().getActivePlayer().addMoney(totalPrice);
//
//        return new Result(true, "Sold " + amount + " " + item.getName() +
//            " for " + totalPrice + " coins!");
//    }
//
//    public void closeInventory() {
//        isInventoryOpen = false;
//        inventoryTable.setVisible(false);
//    }
//
//    public void render() {
//        if (isInventoryOpen) {
//            stage.act(Gdx.graphics.getDeltaTime());
//            stage.draw();
//        }
//    }
//
//    public boolean isInventoryOpen() {
//        return isInventoryOpen;
//    }
//
//    public void dispose() {
//        stage.dispose();
//        skin.dispose();
//    }
//}
