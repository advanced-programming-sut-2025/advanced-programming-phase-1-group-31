package io.github.some_example_name.View;

import com.badlogic.gdx.Gdx; import com.badlogic.gdx.Screen; import com.badlogic.gdx.graphics.GL20; import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent; import com.badlogic.gdx.scenes.scene2d.Stage; import com.badlogic.gdx.scenes.scene2d.ui.*; import com.badlogic.gdx.scenes.scene2d.utils.ClickListener; import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable; import com.badlogic.gdx.utils.viewport.ScreenViewport; import io.github.some_example_name.Control.GameController;
import io.github.some_example_name.Control.PreGameMenuController;
import io.github.some_example_name.View.ui.OverlayImageButton;

import java.util.ArrayList; import java.util.List;

public class PreGameMenuView implements Screen { private final Stage stage; private final PreGameMenuController controller; private final List<String> playerUsernames = new ArrayList<>(); private final List<Label> usernameLabels = new ArrayList<>(); private final List<String> selectedMaps = new ArrayList<>(); private final Skin skin; private final Table table = new Table();

    public PreGameMenuView(PreGameMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        controller.setView(this);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        stage.addActor(table);
        setupPlayerSelection();
    }

    private void setupPlayerSelection() {
        table.clear();
        TextField usernameInput = new TextField("", skin);
        usernameInput.setMessageText("Enter Username");

        TextButton addPlayerButton = new TextButton("Add Player", skin);
        addPlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameInput.getText().trim();
                if (!username.isEmpty() && !playerUsernames.contains(username)) {
                    playerUsernames.add(username);
                    Label nameLabel = new Label(username, skin);
                    usernameLabels.add(nameLabel);
                    table.row();
                    table.add(nameLabel).colspan(2);
                    usernameInput.setText("");
                }
            }
        });

        TextButton confirmButton = new TextButton("Confirm Players", skin);
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.onConfirmPressed(playerUsernames);
            }
        });

        Label messageLabel = controller.getMessageLabel();
        messageLabel.setFontScale(1.2f);

        table.add(usernameInput).width(300).pad(10).colspan(2);
        table.row();
        table.add(addPlayerButton).width(200).pad(10).colspan(2);
        table.row();
        // دکمه اصلی
//        Texture overlayTex = new Texture("hotbar_selection.png");
//        OverlayImageButton customButton = new OverlayImageButton(skin.get("default", TextButton.TextButtonStyle.class), overlayTex);
//        customButton.setCount(5);
//        table.add(customButton).width(100).height(100).padTop(20).row();
//        ImageButton imageButton = new ImageButton( skin  , "Inventory");
//        imageButton.setSize(500000, 50000);
//        table.add(imageButton).width(100).height(100).padTop(20).row();
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

// دکمه‌های بالا (navigation bar)
//        Table topButtonsTable = new Table();
//        ImageButton mapBtn = new ImageButton(skin,"Map");
//        ImageButton settingsBtn = new ImageButton(skin, "Setting");
//        ImageButton inventoryBtn = new ImageButton( skin, "Inventory");
//        topButtonsTable.add(mapBtn).width(50).height(50);
//        topButtonsTable.add(settingsBtn).width(50).height(50);
//        topButtonsTable.add(inventoryBtn).width(50).height(50);
//
//// محتوای اصلی متغیر (وسط صفحه)
//        Stack contentStack = new Stack();
//        Window inventoryContent = createInventoryTable(skin);
//        Window settingsContent = createInventoryTable(skin);
//        contentStack.add(inventoryContent);   // Inventory بالاتر
//        contentStack.add(settingsContent);    // Settings زیرش
//        inventoryContent.setVisible(false);
//        settingsContent.setVisible(false);
//        inventoryBtn.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                inventoryContent.setVisible(true);
//                settingsContent.setVisible(false);
//            }
//        });
//
//        settingsBtn.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                inventoryContent.setVisible(false);
//                settingsContent.setVisible(true);
//            }
//        });
// چینش نهایی در root
//        rootTable.top();
//        rootTable.add(topButtonsTable).expandX().fillX().row();
//        rootTable.add(contentStack).expand().fill();
        table.add(confirmButton).width(200).height(50).padTop(20).colspan(2);
        table.row();
        table.add(messageLabel).colspan(2).center();
    }
//    private Window createInventoryTable(Skin skin) {
//        Window inventoryWindow = new Window("" , skin );
//        Table inventoryTable = new Table(); // بک‌گراند دلخواه
//
//
//        int columns = 4;
//        int rows = 3;
//
//        for (int i = 0; i < rows * columns; i++) {
//            OverlayImageButton slot = new OverlayImageButton(skin.get("Inventory", TextButton.TextButtonStyle.class)); // هر اسلات
//            slot.setName("slot_" + i);
//            inventoryTable.add(slot).size(128, 128).pad(30); // اندازه و فاصله بین دکمه‌ها
//
//            if ((i + 1) % columns == 0)
//                inventoryTable.row(); // پایان ردیف
//        }
//        inventoryWindow.add(inventoryTable).padRight(10000);
//
//        return inventoryWindow;
//    }
    public void setupMapSelection(List<String> playerUsernames) {
        table.clear();
        table.add(new Label("Select maps for players:", skin)).colspan(3).padBottom(20);
        table.row();

        String[] maps = {"farm1.png", "farm2.png", "farm1.png"};

        final List<ImageButton> playerButtons = new ArrayList<>();

        for (int i = 0; i < playerUsernames.size(); i++) {
            final int index = i;
            table.add(new Label("Player " + (i + 1), skin)).pad(5);

            List<ImageButton> mapButtons = new ArrayList<>();

            for (String map : maps) {
                Texture mapTexture = new Texture(Gdx.files.internal(map));
                ImageButton mapBtn = new ImageButton(new TextureRegionDrawable(mapTexture));
                mapButtons.add(mapBtn);

                mapBtn.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        // un-highlight others
                        for (ImageButton btn : mapButtons) {
                            btn.getImage().setColor(1f, 1f, 1f, 1f);  // default white
                        }

                        // highlight selected
                        mapBtn.getImage().setColor(0.6f, 0.9f, 0.6f, 1f); // green tint

                        while (selectedMaps.size() <= index)
                            selectedMaps.add(null);
                        selectedMaps.set(index, map);

                    }
                });

                table.add(mapBtn).width(100).height(60).pad(5);
            }
            table.row();
        }
        TextButton startButton = new TextButton("Start Game", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleStartGameWithMaps(selectedMaps , playerUsernames);
            }
        });


        table.add(startButton).colspan(3).padTop(20);
        table.row();
        Label messageLabel = controller.getMessageLabel();
        messageLabel.setFontScale(1.2f);
        messageLabel.setPosition(
            Gdx.graphics.getWidth() / 2f - 200, // adjust X
            40                                // Y: پایین صفحه
        );
        stage.addActor(messageLabel);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int w, int h) { stage.getViewport().update(w, h, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }

}
