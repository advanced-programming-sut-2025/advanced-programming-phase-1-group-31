package io.github.some_example_name.View;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.some_example_name.Control.GameController;
import io.github.some_example_name.model.*;
import io.github.some_example_name.model.enums.creature.CoopsAndBarnsTypes;
import io.github.some_example_name.model.enums.npc.Shops;
import io.github.some_example_name.model.materials.Animal;
import io.github.some_example_name.model.materials.Barn;
import io.github.some_example_name.model.materials.Coop;
import io.github.some_example_name.model.materials.Shop;
import io.github.some_example_name.model.materials.Tools.Tool;

import java.util.List;
import java.util.stream.Stream;

public class StoreView extends GameView{
    private boolean showOnlyAvailable = false;
    private final Shops shopType ;
    private Table mainTable = new Table();

    public StoreView(GameController gameController, Skin skin , MapType mapType , Shops shopType) {
        super(gameController, skin ,mapType);
        this.shopType = shopType;
    }

    @Override
    public MapType getMapType() {
        return mapType ;
    }

    public void setupUI() {
        mainTable.clear(); // به جای حذف کامل، فقط پاکسازی محتوای قبلی
        mainTable.setFillParent(true);
        if (!mainTable.hasParent()) getStage().addActor(mainTable);

        TextButton filterButton = new TextButton(showOnlyAvailable ? "Show All" : "Available Only", getSkin());
        filterButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showOnlyAvailable = !showOnlyAvailable;
                setupUI(); // بدون setVisible
            }
        });
        mainTable.add(filterButton).colspan(2).padBottom(20).row();

        Table productsTable = new Table();
        ScrollPane scrollPane = new ScrollPane(productsTable, getSkin());
        scrollPane.setScrollingDisabled(true, false);

        for (MaterialInShop item : shopType.getMaterials()) {
            if (showOnlyAvailable && !isAvailable(item)) continue;

            Label.LabelStyle style = new Label.LabelStyle(getSkin().getFont("Impact"),
                isAvailable(item) ? Color.WHITE : Color.DARK_GRAY);

            Label nameLabel = new Label(item.getMaterial().getName(), style);
            Label priceLabel = new Label(String.valueOf(getCurrentPrice(item)), style);

            productsTable.add(nameLabel).pad(10).left();
            productsTable.add(priceLabel).pad(10).right();
            productsTable.row();

            if (isAvailable(item)) {
                nameLabel.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (item.getMaterial() instanceof Animal){

                        }
                        showPurchaseDialog(item);
                    }
                });
            }
        }

        mainTable.add(scrollPane).colspan(2).grow().row();

        TextButton exitButton = new TextButton("Back", getSkin());
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainTable.remove(); // حذف کل جدول
            }
        });
        mainTable.add(exitButton).colspan(2).padTop(20).row();
    }

    private boolean isAvailable(MaterialInShop item) {
        return item.getSeasons() == null || item.getSeasons() == App.getCurrentGame().getTimeAndDate().getSeason();
    }

    private int getCurrentPrice(MaterialInShop item) {
        return isAvailable(item) ? item.getOrdinaryPrice() : item.getOutOfSeasonPrice();
    }

    private void showPurchaseDialog(MaterialInShop item) {
        Dialog dialog = new Dialog("Purchase", getSkin());
        dialog.getContentTable().defaults().pad(10);

        // Display product info
        dialog.getContentTable().add(new Label("Item: " + item.getMaterial().getName(), getSkin())).row();
        dialog.getContentTable().add(new Label("Price: " + getCurrentPrice(item), getSkin())).row();

        // Special handling for animals
        final TextField nameInput = new TextField("", getSkin());
        if (item.getMaterial() instanceof Animal) {
            dialog.getContentTable().add(new Label("Name your animal:", getSkin())).row();
            dialog.getContentTable().add(nameInput).width(200).row();
        }

        // Quantity control (for non-animal, non-tool items)
        final int[] quantity = {1};
        if (!(item.getMaterial() instanceof Tool) && !(item.getMaterial() instanceof Animal)) {
            Label quantityLabel = new Label("Quantity: " + quantity[0], getSkin());

            TextButton increaseBtn = new TextButton("+", getSkin());
            increaseBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    quantity[0] = Math.min(quantity[0] + 1, item.getDailyLimit());
                    quantityLabel.setText("Quantity: " + quantity[0]);
                }
            });

            TextButton decreaseBtn = new TextButton("-", getSkin());
            decreaseBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    quantity[0] = Math.max(1, quantity[0] - 1);
                    quantityLabel.setText("Quantity: " + quantity[0]);
                }
            });

            Table quantityTable = new Table();
            quantityTable.add(decreaseBtn);
            quantityTable.add(quantityLabel);
            quantityTable.add(increaseBtn);
            dialog.getContentTable().add(quantityTable).row();
        }

        // Confirm/Cancel buttons
        TextButton buyButton = new TextButton("Buy", getSkin());
        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result;

                if (item.getMaterial() instanceof Animal) {
                    String animalName = nameInput.getText().trim();
                    if (animalName.isEmpty()) {
                        showErrorDialog("Please enter a name for your animal");
                        return;
                    }
                    result = purchaseAnimal(item, animalName);
                } else {
                    result = purchaseProduct(item, quantity[0]);
                }

                if (!result.Success()) {
                    showErrorDialog(result.Message());
                } else {


                    // Special handling for buildings
                    if (item.getMaterial() instanceof Barn || item.getMaterial() instanceof Coop) {
                        setMap(App.getCurrentGame().getMapForPlayer(
                            App.getCurrentGame().getActivePlayer(),
                            MapType.FARM
                        ));
                        setMapRenderer(new OrthogonalTiledMapRenderer(getMap()));

                        getBuildModeHandler().enableBuildMode(
                            (CoopsAndBarnsTypes) item.getMaterial().getType(),
                            new Texture(item.getMaterial().getTexturePath()),
                            getMap()
                        );
                    } else {
                        showSuccessDialog(result.Message());
                    }
                }
                mainTable.remove();
                dialog.hide();
            }
        });

        TextButton cancelButton = new TextButton("Cancel", getSkin());
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });

        dialog.getButtonTable().add(buyButton);
        dialog.getButtonTable().add(cancelButton);
        dialog.show(getStage());
    }

    private void showErrorDialog(String message) {
        Dialog errorDialog = new Dialog("Error", getSkin());
        errorDialog.getTitleLabel().setColor(Color.RED);
        errorDialog.text(message);
        errorDialog.button("OK");
        errorDialog.show(getStage());
    }

    private void showSuccessDialog(String message) {
        Dialog successDialog = new Dialog("Success", getSkin());
        successDialog.getTitleLabel().setColor(Color.GREEN);
        successDialog.text(message);
        successDialog.button("OK");
        successDialog.show(getStage());
    }

    private Result purchaseAnimal(MaterialInShop material, String animalName) {
        Player player = App.getCurrentGame().getActivePlayer();

        // Check if player is near a shop (you may need to implement whichShopIsPlayer())


        // Deduct money
        int price = isAvailable(material) ? material.getOrdinaryPrice() : material.getOutOfSeasonPrice();
        Result moneyResult = player.deductMoney(price);
        if (!moneyResult.Success()) {
            return moneyResult;
        }

        // Create the animal
        Animal animal = (Animal) material.getMaterial();
        Animal newAnimal = new Animal(animalName, animal.getAnimalType());

        // Check for duplicate names
        boolean duplicateName = Stream.concat(
                player.getFarm().getCoops().stream().flatMap(c -> c.getAnimals().stream()),
                player.getFarm().getBarns().stream().flatMap(b -> b.getAnimals().stream()))
            .anyMatch(a -> a.getName().equals(animalName));

        if (duplicateName) {
            player.addMoney(price); // Refund
            return new Result(false, "An animal with this name already exists.");
        }


        // Try to add to appropriate housing
        boolean added = false;

        // Check Coops first
        if (!newAnimal.getAnimalType().getHousingType().isBarn()) {
            added = player.getFarm().getCoops().stream()
                .filter(coop -> coop.getCoopType() == newAnimal.getAnimalType().getHousingType())
                .filter(Coop::hasSpace)
                .findFirst()
                .map(coop -> {
                    coop.getAnimals().add(newAnimal);
                    return true;
                })
                .orElse(false);
        }

        // Then check Barns
        if (!added && newAnimal.getAnimalType().getHousingType().isBarn()) {
            added = player.getFarm().getBarns().stream()
                .filter(barn -> barn.getType() == newAnimal.getAnimalType().getHousingType())
                .filter(Barn::hasSpace)
                .findFirst()
                .map(barn -> {
                    barn.getAnimals().add(newAnimal);
                    return true;
                })
                .orElse(false);
        }

        if (added) {
            return new Result(true, animalName + " the " + newAnimal.getAnimalType().name() + " was added!");
        }

        // If no space available
        player.addMoney(price); // Refund
        return new Result(false, "No available housing with free space for this animal.");
    }
    private Result purchaseProduct(MaterialInShop material, int amount) {
        Player player = App.getCurrentGame().getActivePlayer();
        Shop shop = new Shop(shopType);
        if (shop == null)
            return new Result(false, "You aren't near a shop");

        if (amount > material.getDailyLimit())
            return new Result(false, "Your amount is higher than daily limit");
        if (amount <= 0)
            return new Result(false, "Invalid quantity");

        int total;
        if (material.getSeasons() == null || material.getSeasons().equals(App.getCurrentGame().getTimeAndDate().getSeason())) {
            total = material.getOrdinaryPrice() * amount;
        } else {
            total = material.getOutOfSeasonPrice() * amount;
        }

        Result moneyResult = player.deductMoney(total);
        if (!moneyResult.Success())
            return moneyResult;

        if (material.getMaterial() instanceof Tool tool) {
            boolean replaced = false;
            for (int i = 0; i < player.getInventory().getTools().size(); i++) {
                if (player.getInventory().getTools().get(i).getClass() == tool.getClass()) {
                    player.getInventory().getTools().set(i, tool);
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                player.getInventory().getTools().add(tool);
            }

            return new Result(true, "Your " + tool.getName() + " has been updated");
        } else {
            Result result = player.getInventory().addElementToBackpack(material.getMaterial(), amount);
            if (!result.Success())
                return result;
            return new Result(true, "Purchased " + amount + " × " + material.getMaterial().getName());
        }
    }

}
