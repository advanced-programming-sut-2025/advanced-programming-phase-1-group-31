package io.github.some_example_name.View.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import io.github.some_example_name.Control.GameController;
import io.github.some_example_name.model.*;
import io.github.some_example_name.model.enums.creature.Animals;
import io.github.some_example_name.model.enums.creature.CoopsAndBarnsTypes;
import io.github.some_example_name.model.enums.foragings.ForagingCrops;
import io.github.some_example_name.model.enums.foragings.ForagingMinerals;
import io.github.some_example_name.model.enums.general.Direction;
import io.github.some_example_name.model.enums.general.Seasons;
import io.github.some_example_name.model.enums.general.TileType;
import io.github.some_example_name.model.enums.general.Weather;
import io.github.some_example_name.model.enums.plantable.Crops;
import io.github.some_example_name.model.enums.plantable.MixedSeedSeasons;
import io.github.some_example_name.model.enums.plantable.Seeds;
import io.github.some_example_name.model.enums.plantable.Trees;
import io.github.some_example_name.model.materials.*;
import io.github.some_example_name.model.materials.Foraging.ForagingCrop;
import io.github.some_example_name.model.materials.Foraging.ForagingMineral;
import io.github.some_example_name.model.materials.Products.AnimalProduct;
import io.github.some_example_name.model.materials.Tools.Shear;
import io.github.some_example_name.model.materials.Tools.Tool;
import io.github.some_example_name.model.materials.Tree;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Stream;

import static io.github.some_example_name.model.TimeAndDate.placeScaledImageAsTile;

public class GameMenuInputAdapter extends InputAdapter {
    private final GameController gameController;
    private final Set<Integer> keysHeld = new HashSet<>();
    private boolean spacePressed = false;

    public GameMenuInputAdapter(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public boolean keyDown(int keycode) {
        keysHeld.add(keycode);

        if (keycode >= Input.Keys.NUM_1 && keycode <= Input.Keys.NUM_9) {
            int selectedSlot = keycode - Input.Keys.NUM_1;
            // game.getPlayer().setSelectedSlot(selectedSlot);
            return true;
        }

        if (keycode == Input.Keys.ESCAPE) {
            gameController.getView().getRootTable().setVisible(
                !gameController.getView().getRootTable().isVisible());
            return true;
        }

        if (keycode == Input.Keys.N) {
            App.getCurrentGame().changeTurn();
        }

        if (keycode == Input.Keys.M) {
            gameController.getView().setShowFullMap(true);
        }

        if (keycode == Input.Keys.T) {
            gameController.getView().getToolBar().setVisible(
                !gameController.getView().getToolBar().isVisible());
            return true;
        }

        if (keycode == Input.Keys.SPACE) {
            spacePressed = true;
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.M) {
            gameController.getView().setShowFullMap(false);
        }

        if (keycode == Input.Keys.SPACE) {
            spacePressed = false;
        }

        keysHeld.remove(keycode);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // Inventory slot scrolling logic
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            if (App.getCurrentGame().getActivePlayer().getInHand() != null) {
                handleToolUse(screenX, screenY);
            }
            if (App.getCurrentGame().getActivePlayer().getSeedInHand() != null) {
                handleSeedUse(screenX, screenY);
            }
            return true;
        }

        if (button == Input.Buttons.RIGHT) {
            // Convert screen coordinates to tile position
            Vector3 worldCoords = gameController.getView().getCamera().unproject(new Vector3(screenX, screenY, 0));
            int tileX = (int)(worldCoords.x / 16); // tileSize = 16
            int tileY = (int)(worldCoords.y / 16);
            TiledMapTileLayer animalLayer = (TiledMapTileLayer)gameController.getView().getMap().getLayers().get("animal");
            if (animalLayer != null) {
//                System.out.println("A");
                if (animalLayer.getCell(tileX, tileY) != null) {
                    Player player = App.getCurrentGame().getActivePlayer();
                    Animal animal = Stream.concat(
                            player.getFarm().getCoops().stream().flatMap(c -> c.getAnimals().stream()),
                            player.getFarm().getBarns().stream().flatMap(b -> b.getAnimals().stream()))
                        .filter(a -> a.getLocation().x == tileX && a.getLocation().y == tileY).findFirst().orElse(null);
                    showAnimalMenu(animal);


                }
            }
        }
        return false;
    }
    private void showAnimalMenu(Animal animal) {
        // Create the context menu window with a better size
        Window menu = new Window("", gameController.getView().getSkin());
        menu.setModal(true);
        menu.setMovable(false);

        // Set a minimum width for better appearance
        menu.setWidth(500); // Increased from default size
        menu.setHeight(500); // Increased height to fit items better

        Table table = new Table();
        table.pad(15).defaults().expandX().fillX().space(8); // Increased padding and spacing

        // Create larger buttons with better padding
        TextButton infoBtn = createMenuButton("Info", menu.getSkin());
        infoBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = showAnimalInfo(animal);
                if (result.Success()) {
                    showSuccessDialog(result.Message());
                } else {
                    showErrorDialog(result.Message());
                }
                menu.remove();
            }
        });
        TextButton feedBtn = createMenuButton("Feed", menu.getSkin());
        feedBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = feedHayToAnimal(animal);
                if (result.Success()) {
                    TiledMapTileLayer layer = (TiledMapTileLayer) gameController.getView().getMap().getLayers().get("animal");
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    cell.setTile(animal.getAnimalType().getEating());
                    layer.setCell((int) animal.getLocation().x, (int) animal.getLocation().y, cell);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            placeScaledImageAsTile(
                                gameController.getView().getMap(),
                                "animal",
                                (int) animal.getLocation().x, (int) animal.getLocation().y,
                                animal.getTexturePath()
                            );
                        }
                    }, 5);

                    showSuccessDialog(result.Message());
                } else {
                    showErrorDialog(result.Message());
                }
                menu.remove();
            }
        });

        TextButton petBtn = createMenuButton("Pet", menu.getSkin());
        petBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                animal.getAnimalFriendship().pet();

                TiledMapTileLayer layer = (TiledMapTileLayer) gameController.getView().getMap().getLayers().get("animal");
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(animal.getAnimalType().getPet());
                layer.setCell((int) animal.getLocation().x, (int) animal.getLocation().y, cell);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        placeScaledImageAsTile(
                            gameController.getView().getMap(),
                            "animal",
                            (int) animal.getLocation().x, (int) animal.getLocation().y,
                            animal.getTexturePath()
                        );
                    }
                }, 5);
                Result result =  new Result(true, "You petted " + animal.getName() + ". Friendship is now "
                    + animal.getAnimalFriendship().getFriendshipPoints());
                showSuccessDialog(result.Message());
                menu.remove();
            }
        });

        TextButton collectBtn = createMenuButton("Collect", menu.getSkin());
        collectBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = collectProduct(animal);
                if (result.Success()) {
                    showSuccessDialog(result.Message());
                } else {
                    showErrorDialog(result.Message());
                }
                menu.remove();
            }
        });

        TextButton listUncollectedProductsBtn = createMenuButton("UnCollect", menu.getSkin());
        listUncollectedProductsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = listUncollectedProducts(animal);
                if (result.Success()) {
                    showSuccessDialog(result.Message());
                } else {
                    showErrorDialog(result.Message());
                }
                menu.remove();
            }
        });
        TextButton shepherdAnimalBtn = createMenuButton("shepherd Animal", menu.getSkin());
        shepherdAnimalBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = shepherdAnimal(animal);
//                if (result.Success()) {
//                    showSuccessDialog(result.Message());
//                } else {
//                    showErrorDialog(result.Message());
//                }
                menu.remove();
            }
        });


        TextButton sellBtn = createMenuButton("Sell", menu.getSkin());
        sellBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = sellAnimal(animal);
                if (result.Success()) {
                    showSuccessDialog(result.Message());
                } else {
                    showErrorDialog(result.Message());
                }
                menu.remove();
            }
        });

        TextButton closeBtn = createMenuButton("Close", menu.getSkin());
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menu.remove();
            }
        });

        // Add buttons with better spacing
        table.add(infoBtn).padBottom(10).fillX().row();
        table.add(feedBtn).padBottom(10).fillX().row();
        table.add(petBtn).padBottom(10).fillX().row();
        table.add(listUncollectedProductsBtn).padBottom(10).fillX().row();
        table.add(collectBtn).padBottom(10).fillX().row();
        table.add(shepherdAnimalBtn).padBottom(10).fillX().row();
        table.add(sellBtn).padBottom(10).fillX().row();
        table.add(closeBtn).fillX().row();

        menu.add(table);
        menu.pack(); // This will respect our size settings

        // Center the menu on screen
        float centerX = (Gdx.graphics.getWidth() - menu.getWidth()) / 2;
        float centerY = (Gdx.graphics.getHeight() - menu.getHeight()) / 2;
        menu.setPosition(centerX, centerY);

        gameController.getView().getStage().addActor(menu);
    }
    private Result feedHayToAnimal(Animal animal) {


        Result result = App.getCurrentGame().getActivePlayer().getInventory()
            .removeElementFromBackpack(new ForagingCrop(ForagingCrops.Hey), 1);
        if (!result.Success()) {
            return result;
        }
        if (animal.getAnimalFriendship().isWasFedToday()) {
            return new Result(false, animal.getName() + " has already been fed today.");
        }
        animal.getAnimalFriendship().feed(false);
        // false: fed inside, but isOutside = true
        // return new Result(false, animalName + " must be outside the barn/coop to eat
        // hay.");
        return new Result(true, animal.getName() + " was successfully fed with hay.");
    }
    private Result showAnimalInfo(Animal animal) {
        if (animal == null) {
            return new Result(false, "No animal selected.");
        }

        Player player = App.getCurrentGame().getActivePlayer();
        AnimalFriendship friendship = animal.getAnimalFriendship();
        Animals animalType = (Animals) animal.getType();

        String info = String.format(
            "Name: %s\nType: %s\nFriendship: %.2f%%\nPetted Today: %s\nFed Today: %s\nOutside Tonight: %s",
            animal.getName(),
            animalType.getName(),
            friendship.getFriendshipPercentage(),
            friendship.isWasPettedToday() ? "Yes" : "No",
            friendship.isWasFedToday() ? "Yes" : "No",
            friendship.isStayedOutsideTonight() ? "Yes" : "No"
        );

        return new Result(true, info);
    }
    public Result collectProduct(Animal animal) {




        Animals type = animal.getAnimalType();
        if (type == Animals.COW || type == Animals.GOAT || type == Animals.SHEEP) {
            Shear shear = (Shear) App.getCurrentGame().getActivePlayer().getInventory().isExistToolOrNull(new Shear());

            if (shear == null)
                return new Result(false, "You haven't Shear.");
        }


        if (type.needsToGoOutside() && !animal.getAnimalFriendship().isStayedOutsideTonight()) {
            return new Result(false, "Pig must be outside to collect truffle.");
        }

        if (!animal.hasProduct()) {
            return new Result(false, animal.getName() + " has no product to collect.");
        }

        AnimalProduct collectedProduct = animal.collectProduct();
        animal.getAnimalFriendship().milkOrShear();
        Result result = App.getCurrentGame().getActivePlayer().getInventory().addElementToBackpack(collectedProduct, collectedProduct.getQuantity());
        if (!result.Success()) {
            return result;
        }
        return new Result(true,
            "Collected " + collectedProduct.getName() +
                " from " + animal.getName() + " (Quality: " + collectedProduct.getQuality() + ")");
    }
    public Result listUncollectedProducts(Animal animal) {
        List<String> uncollected = new ArrayList<>();

        if (animal.hasProduct()) {
            AnimalProduct product = animal.getTodayProduct();
            uncollected.add("- " + animal.getName() + " => " +
                product.getAnimalProducts().name() +
                ", Quality: " + product.getQuality());
        }

        if (uncollected.isEmpty()) {
            return new Result(false, "No uncollected products found.");
        } else {
            String message = "Uncollected products:\n" + String.join("\n", uncollected);
            return new Result(true, message);
        }
    }
    public Result shepherdAnimal(Animal animal) {
//        Player player = App.getCurrentGame().getActivePlayer();
            gameController.getView().getBuildModeHandler().enableBuildMode(
                animal,
                new Texture(animal.getTexturePath()),
                gameController.getView().getMap()
            );

        return new Result(true , "hh");


    }

    private record AnimalLocationContext(Animal animal, Material housing) {
    }
    private Result sellAnimal(Animal animal) {
        Player player = App.getCurrentGame().getActivePlayer();

        double multiplier = (animal.getAnimalFriendship().getFriendshipPercentage()) + 0.3;
        double price = (multiplier * animal.getAnimalType().getPurchasePrice());
        player.addMoney(price);
        // player.changeMoney(price);

        // Remove from its pen
        if (animal.getAnimalType().getHousingType().isBarn()) {
            player.getFarm().getBarns().forEach(b -> b.removeAnimalByName(animal , gameController.getView().getMap()));
        } else {
            player.getFarm().getCoops().forEach(c -> c.removeAnimalByName(animal , gameController.getView().getMap()));
        }
        return new Result(true, animal.getName() + " sold for " + "g.");

        // return new Result(true, animalName + " sold for " + price + "g.");
    }
    private TextButton createMenuButton(String text, Skin skin) {
        TextButton button = new TextButton(text, skin);
        button.getLabel().setFontScale(1.2f); // Larger text
        button.pad(12); // More padding for larger click area
        return button;
    }
    private void showErrorDialog(String message) {
        Dialog errorDialog = new Dialog("Error", gameController.getView().getSkin());
        errorDialog.getTitleLabel().setColor(Color.RED);
        errorDialog.text(message);
        errorDialog.button("OK");
        errorDialog.show(gameController.getView().getStage());
    }

    private void showSuccessDialog(String message) {
        Dialog successDialog = new Dialog("Success", gameController.getView().getSkin());
        successDialog.getTitleLabel().setColor(Color.GREEN);
        successDialog.text(message);
        successDialog.button("OK");
        successDialog.show(gameController.getView().getStage());
    }
//    private List<Animal> getAllAnimals() {
//        // Return all animals in the game
//        List<Animal> animals = new ArrayList<>();
//        if (gameController.getBarn() != null) {
//            animals.addAll(gameController.getBarn().getAnimals());
//        }
//        if (gameController.getCoop() != null) {
//            animals.addAll(gameController.getCoop().getAnimals());
//        }
//        return animals;
//    }

    private void handleToolUse(int screenX, int screenY) {
        Tool selectedTool = App.getCurrentGame().getActivePlayer().getInHand();
        Direction dir = gameController.getView().getMouseDirection(screenX, screenY);
        Result result = selectedTool.work(dir);
        if (result.Success()) {
            gameController.showSuccessMessage(result.Message());
        }else {
            gameController.showErrorMessage(result.Message());
        }
        System.out.println(result.Message());
    }
    private void handleSeedUse(int screenX, int screenY) {
        Seed selectedSeed = App.getCurrentGame().getActivePlayer().getSeedInHand();
        Direction dir = gameController.getView().getMouseDirection(screenX, screenY);
        Result result = plant(selectedSeed , dir);
        if (result.Success()) {
            gameController.showSuccessMessage(result.Message());
        }else {
            gameController.showErrorMessage(result.Message());
        }
    }
    private Result plant(Seed selectedSeed, Direction dir) {
        Player player = App.getCurrentGame().getActivePlayer();
        Vector2 point = dir.apply(player.getPlace());

        // Convert position to tile coordinates
        int tileX = (int) (point.x / 16);
        int tileY = (int) (point.y / 16);

        // Check planting based on current map type
        if (player.getCurrentMapType() == MapType.FARM) {
            return plantInFarm(player, selectedSeed, tileX, tileY);
        } else if (player.getCurrentMapType() == MapType.GREENHOUSE) {
            return plantInGreenhouse(player, selectedSeed, tileX, tileY);
        }

        return new Result(false, "Soil is not tilled!");
    }

    private Result plantInFarm(Player player, Seed selectedSeed, int tileX, int tileY) {
        FarmMap farmMap = App.getCurrentGame().getMapForPlayer(player, MapType.FARM);

        // Check if tile is in plantable area
        if (!isTilePlantable(player, tileX, tileY)) {
            return new Result(false, "Soil is not tilled!");
        }

        // Check season restrictions
        Result seasonCheck = checkSeasonRestrictions(selectedSeed);
        if (!seasonCheck.Success()) {
            return seasonCheck;
        }
        Tile tile = App.getCurrentGame().getMainMap().getTileByPoint(tileX, tileY);
        if (tile == null || !tile.getMaterial().equals(new ForagingMineral(ForagingMinerals.SOIL))) {
            return new Result(false, "Soil is not tilled!");
        }
        // Plant the seed
        return plantSeed(player, selectedSeed, tileX, tileY, farmMap);
    }

    private Result plantInGreenhouse(Player player, Seed selectedSeed, int tileX, int tileY) {
        FarmMap farmMap = App.getCurrentGame().getMapForPlayer(player, MapType.GREENHOUSE);

        // Check if tile is in plantable area
        if (!isTilePlantableInGreenhouse(farmMap, tileX, tileY)) {
            return new Result(false, "Cannot plant here!");
        }

        // Greenhouse has no season restrictions, so plant directly
        return plantSeed(player, selectedSeed, tileX, tileY, farmMap);
    }

    private boolean isTilePlantable(Player player, int tileX, int tileY) {
        for (MapObject obj : player.getFarm().getAllObjects()) {
            if (obj instanceof PolygonMapObject && "plantable".equals(obj.getName())) {
                Polygon polygon = ((PolygonMapObject) obj).getPolygon();
                float worldX = tileX * 16 + 8; // Center of tile
                float worldY = tileY * 16 + 8;

                if (polygon.contains(worldX, worldY)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isTilePlantableInGreenhouse(FarmMap farmMap, int tileX, int tileY) {
        MapLayer objectLayer = farmMap.getTmxMap().getLayers().get("Object");
        if (objectLayer == null) return false;

        for (MapObject obj : objectLayer.getObjects()) {
            if (obj instanceof PolygonMapObject && "plantable".equals(obj.getName())) {
                Polygon polygon = ((PolygonMapObject) obj).getPolygon();
                float worldX = tileX * 16 + 8;
                float worldY = tileY * 16 + 8;

                if (polygon.contains(worldX, worldY)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Result checkSeasonRestrictions(Seed selectedSeed) {
        Crops crops = selectedSeed.getCorrespondingCrop();
        Trees trees = selectedSeed.getCorrespondingTrees();
        Seasons currentSeason = App.getCurrentGame().getTimeAndDate().getSeason();

        if (crops != null && !crops.getSeasons().contains(currentSeason)) {
            return new Result(false, "This crop cannot be planted in this season.");
        }

        if (trees != null && !trees.getFruit().getHarvestSeasons().contains(currentSeason)) {
            return new Result(false, "This tree cannot be planted in this season.");
        }

        Seeds seeds = getCorrespondingMixedSeasons(selectedSeed.getName());
        if (seeds != null) {
            for (MixedSeedSeasons mixedSeed : MixedSeedSeasons.values()) {
                if (mixedSeed.getName().equals(selectedSeed.getName()) &&
                    mixedSeed.getSeason() != currentSeason) {
                    return new Result(false, "This mixed season cannot be planted in this season.");
                }
            }
        }

        return new Result(true, "");
    }

    private Result plantSeed(Player player, Seed selectedSeed, int tileX, int tileY, FarmMap farmMap) {
        Tile tile = App.getCurrentGame().getMainMap().getTileByPoint(tileX, tileY);
        if (tile == null){
            tile = new Tile();
            tile.setPoint(new Point(tileX, tileY));
          farmMap.addTile(tile);

        }


        Crops crops = selectedSeed.getCorrespondingCrop();
        Trees trees = selectedSeed.getCorrespondingTrees();
        String imagePath = null;

        if (crops != null) {
            tile.setMaterial(new Crop(crops));
            imagePath = crops.getStages().getFirst().imagePath();
        } else if (trees != null) {
            tile.setMaterial(new Tree(trees));
            imagePath = trees.getStages().getFirst().imagePath();
        } else {
            return new Result(false, "Invalid seed type!");
        }

        placeScaledImageAsTile(farmMap.getTmxMap(), "craft", tileX, tileY, imagePath);
        player.getInventory().removeElementFromBackpack(selectedSeed, 1);

        return new Result(true, selectedSeed.getName() + " planted!");
    }
    public Seeds getCorrespondingMixedSeasons(String season) {
        for (MixedSeedSeasons mixedSeed : MixedSeedSeasons.values()) {
            if (mixedSeed.getName().equals(season)) {
                return mixedSeed.getRandomSeed();
            }
        }
        return null;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector3 worldCoordinates = gameController.getView().getCamera().unproject(new Vector3(screenX, screenY, 0));
        if (gameController.getToolController()!= null) {
            gameController.getToolController().handleToolRotation(worldCoordinates.x, worldCoordinates.y);
        }
        return true;
    }
    public void update(float delta) {
        handleMovement(delta);
    }

    private void handleMovement(float delta) {
        int oldTileX = (int) (App.getCurrentGame().getActivePlayer().getPlace().x / gameController.getView().getTILE_SIZE());
        int oldTileY = (int) (App.getCurrentGame().getActivePlayer().getPlace().y / gameController.getView().getTILE_SIZE());

        // Teleport cooldown management
        if (gameController.getView().getTeleportCooldown() > 0) {
            gameController.getView().setTeleportCooldown(gameController.getView().getTeleportCooldown() - delta);
            gameController.getView().setJustTeleported(true);
        } else {
            gameController.getView().setJustTeleported(false);
        }

        boolean moved = false;
        CharacterPlacer.Direction direction = null;

        float nextX = App.getCurrentGame().getActivePlayer().getPlace().x;
        float nextY = App.getCurrentGame().getActivePlayer().getPlace().y;

        // Process movement input
        if (keysHeld.contains(Input.Keys.A)) {
            nextX -= gameController.getView().getSpeed() * delta;
            direction = CharacterPlacer.Direction.LEFT;
        } else if (keysHeld.contains(Input.Keys.D)) {
            nextX += gameController.getView().getSpeed() * delta;
            direction = CharacterPlacer.Direction.RIGHT;
        } else if (keysHeld.contains(Input.Keys.W)) {
            nextY += gameController.getView().getSpeed() * delta;
            direction = CharacterPlacer.Direction.UP;
        } else if (keysHeld.contains(Input.Keys.S)) {
            nextY -= gameController.getView().getSpeed() * delta;
            direction = CharacterPlacer.Direction.DOWN;
        }
        if (App.getCurrentGame().getActivePlayer().getEnergy().getEnergyAmount() < 0) {
            App.getCurrentGame().getActivePlayer().getCharacterPlacer().faint(oldTileX, oldTileY + 2);
            App.getCurrentGame().getPlayers().stream()
                .filter(player -> player != App.getCurrentGame().getActivePlayer())
                .forEach(player -> {
                    player.getCharacterPlacer().dontMove(
                        (int) (player.getPlace().x / gameController.getView().getTILE_SIZE()),
                        (int) (player.getPlace().y / gameController.getView().getTILE_SIZE()));
                });
            return;
        }

        // Check movement and obstacles
        if (direction != null && !gameController.isBlocked(nextX, nextY)) {
            App.getCurrentGame().getActivePlayer().setPlace(new Vector2(nextX, nextY));
            moved = true;
            App.getCurrentGame().getActivePlayer().getEnergy().changeEnergy(-0.005);
        }

        int newTileX = (int) (App.getCurrentGame().getActivePlayer().getPlace().x / gameController.getView().getTILE_SIZE());
        int newTileY = (int) (App.getCurrentGame().getActivePlayer().getPlace().y / gameController.getView().getTILE_SIZE());

        // Manage character display
        if (moved && (newTileX != oldTileX || newTileY != oldTileY)) {
            App.getCurrentGame().getActivePlayer().getCharacterPlacer().clearCharacter(oldTileX, oldTileY);
            App.getCurrentGame().getActivePlayer().getCharacterPlacer().placeCharacter(newTileX, newTileY, direction);
        } else if (!moved) {
            App.getCurrentGame().getActivePlayer().getCharacterPlacer().clearCharacter(newTileX, newTileY);
            App.getCurrentGame().getActivePlayer().getCharacterPlacer().dontMove(newTileX, newTileY);
        }



        App.getCurrentGame().getPlayers().stream()
            .filter(player -> player != App.getCurrentGame().getActivePlayer())
            .forEach(player -> {
                player.getCharacterPlacer().dontMove(
                    (int) (player.getPlace().x / gameController.getView().getTILE_SIZE()),
                    (int) (player.getPlace().y / gameController.getView().getTILE_SIZE()));
            });
        App.getCurrentGame().getActivePlayer().getPlayerRectangle().setPosition(nextX, nextY);

    }

    public Set<Integer> getKeysHeld() {
        return keysHeld;
    }

    private void performAction(int screenX, int screenY) {
        // Handle click actions
    }
}
