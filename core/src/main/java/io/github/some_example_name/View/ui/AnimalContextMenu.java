package io.github.some_example_name.View.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.some_example_name.model.materials.Animal;
import io.github.some_example_name.model.materials.Products.AnimalProduct;

import java.util.ArrayList;

public class AnimalContextMenu {

    public static void setupTileRightClick(Stage stage, Skin skin, TiledMapTileLayer animalLayer, ArrayList<Animal> animals) {
        if (animalLayer == null) {
            return;
        }
        // ایجاد یک Actor شفاف که کل لایه حیوانات را پوشش دهد
        Actor clickDetector = new Actor();
        clickDetector.setSize(animalLayer.getWidth() * animalLayer.getTileWidth(),
            animalLayer.getHeight() * animalLayer.getTileHeight());

        clickDetector.addListener(new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // تبدیل مختصات کلیک به مختصات تایل
                int tileX = (int) (x / 16);
                int tileY = (int) (y / 16);

                // بررسی وجود حیوان در این تایل
                if (animalLayer.getCell(tileX, tileY) == null) {
                    Animal animal = findAnimalAtTile(animals, tileX, tileY);
                    if (animal != null) {
                        showMenu(stage, skin, animal, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
                    }
                }
            }
        });

        stage.addActor(clickDetector);
    }

    private static Animal findAnimalAtTile(ArrayList<Animal> animals, int tileX, int tileY) {
        // اینجا باید منطق پیدا کردن حیوان بر اساس موقعیت تایل را پیاده‌سازی کنید
        // به عنوان مثال ساده، اولین حیوان را برمی‌گردانیم
        return animals.isEmpty() ? null : animals.getFirst();
    }

    private static void showMenu(Stage stage, Skin skin, Animal animal, float screenX, float screenY) {
        // Create context menu window
        Window menu = new Window("", skin);
        menu.setModal(true);
        menu.setMovable(false);

        // Set size
        float menuWidth = 200;
        float menuHeight = 250;

        // Adjust position if menu goes off-screen
        if (screenX + menuWidth > Gdx.graphics.getWidth()) {
            screenX = Gdx.graphics.getWidth() - menuWidth;
        }
        if (screenY + menuHeight > Gdx.graphics.getHeight()) {
            screenY = Gdx.graphics.getHeight() - menuHeight;
        }

        menu.setPosition(screenX, screenY);
        menu.setSize(menuWidth, menuHeight);

        // Create table for button layout
        Table table = new Table();
        table.pad(10).defaults().expandX().fillX().space(5);

        // Pet button
        TextButton petBtn = new TextButton("🐾 Pet", skin);
        petBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                animal.getAnimalFriendship().pet();
                menu.remove();
                System.out.println(animal.getName() + " was petted.");
            }
        });

        // Feed button
        TextButton feedBtn = new TextButton("🥬 Feed", skin);
        feedBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menu.remove();
                System.out.println("Fed " + animal.getName());
            }
        });

        // Collect product button
        TextButton collectBtn = new TextButton("📦 Collect", skin);
        collectBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menu.remove();
                AnimalProduct product = animal.collectProduct();
                if (product != null) {
                    System.out.println("Collected: " + product.getName());
                }
            }
        });

        // Sell button
        TextButton sellBtn = new TextButton("💰 Sell", skin);
        sellBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menu.remove();
                System.out.println("Sold " + animal.getName());
            }
        });

        // Close button
        TextButton closeBtn = new TextButton("❌ Close", skin);
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menu.remove();
            }
        });

        // Add buttons to table
        table.add(petBtn).row();
        table.add(feedBtn).row();
        table.add(collectBtn).row();
        table.add(sellBtn).row();
        table.add(closeBtn).row();

        menu.add(table);
        stage.addActor(menu);
    }
}
