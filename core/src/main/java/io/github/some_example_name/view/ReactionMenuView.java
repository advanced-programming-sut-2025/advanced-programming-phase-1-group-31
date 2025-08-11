package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import common.Message;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameApp;
import io.github.some_example_name.model.Reactions;

import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ReactionMenuView implements Screen {

    private final Stage stage;
    private final Table rootTable;
    private final Skin skin;

    private final List<String> defaultReactionPaths = new ArrayList<>(Arrays.asList(
        "reactions/reaction1.png",
        "reactions/reaction2.png",
        "reactions/reaction3.png",
        "reactions/reaction4.png",
        "reactions/reaction5.png",
        "reactions/reaction6.png",
        "reactions/reaction7.png",
        "reactions/reaction8.png",
        "reactions/reaction9.png"
    ));

    private final List<String> reactionsThatShown = new ArrayList<>(Arrays.asList(
        "reactions/reaction1.png",
        "reactions/reaction2.png",
        "reactions/reaction3.png"
    ));

    private Table reactionTable;
    private Table editPanel;
    private TextField reactionTextField;

    public ReactionMenuView(Skin skin) {
        this.skin = skin;
        stage = new Stage(new ScreenViewport());
        rootTable = new Table(skin);
        rootTable.defaults().pad(10);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        rootTable.clearChildren();

        Label title = new Label("Choose Your Reaction", skin);
        title.setFontScale(2f);
        title.setColor(Color.YELLOW);
        title.setAlignment(Align.center);

        reactionTable = new Table();
        reactionTable.defaults().width(100).height(100).pad(5);
        refreshReactionTable();

        ScrollPane reactionScrollPane = new ScrollPane(reactionTable, skin);
        reactionScrollPane.setFadeScrollBars(false);
        reactionScrollPane.setScrollingDisabled(false, false);
        reactionScrollPane.setScrollBarPositions(true, true);

        TextButton editButton = new TextButton("Edit", skin);
        editButton.getLabel().setFontScale(1.2f);

        editPanel = createEditPanel();
        editPanel.setVisible(false);

        editButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                editPanel.setVisible(!editPanel.isVisible());
            }
        });

        TextButton back = new TextButton("Back", skin);
        back.getLabel().setFontScale(1.5f);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(GameApp.getGameView());
            }
        });

        int cols = 3;
        rootTable.add(title).colspan(cols).padBottom(20).row();
        rootTable.add(editButton).colspan(cols).padBottom(10).row();

        rootTable.add(reactionScrollPane).colspan(cols).height(350).fillX().row();

        rootTable.add(editPanel).colspan(cols).padTop(20).row();
        rootTable.add(back).colspan(cols).padTop(20);

        ScrollPane rootScrollPane = new ScrollPane(rootTable, skin);
        rootScrollPane.setFillParent(true);
        rootScrollPane.setFadeScrollBars(false);
        rootScrollPane.setScrollingDisabled(false, false);

        stage.addActor(rootScrollPane);
    }

    private Table createEditPanel() {
        Table panel = new Table(skin);
        panel.defaults().pad(5);

        Label chooseLabel = new Label("Select Reactions to Show", skin);
        panel.add(chooseLabel).colspan(3).row();

        Table checkBoxTable = new Table(skin);
        checkBoxTable.defaults().pad(5);

        int cols = 3;
        int count = 0;

        for (String path : defaultReactionPaths) {
            CheckBox checkBox = new CheckBox(" " + path.substring(path.lastIndexOf('/') + 1), skin);
            checkBox.setChecked(reactionsThatShown.contains(path));
            checkBox.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (checkBox.isChecked()) {
                        if (!reactionsThatShown.contains(path)) {
                            reactionsThatShown.add(path);
                        }
                    } else {
                        reactionsThatShown.remove(path);
                    }
                    refreshReactionTable();
                }
            });

            checkBoxTable.add(checkBox).pad(15);
            count++;
            if (count % cols == 0) checkBoxTable.row();
        }
        panel.add(checkBoxTable).colspan(cols).left().row();

        Label addTextLabel = new Label("Add Text Reaction (max 10 chars)", skin);
        panel.add(addTextLabel).colspan(4).row();

        reactionTextField = new TextField("", skin);
        reactionTextField.setMessageText("Type here...");
        reactionTextField.setMaxLength(10);
        panel.add(reactionTextField).width(400).colspan(2);

        TextButton addTextButton = new TextButton("Add", skin);
        addTextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String text = reactionTextField.getText().trim();
                if (!text.isEmpty() && text.length() <= 10) {
                    if (!reactionsThatShown.contains(text)) {
                        reactionsThatShown.add(text);
                        reactionTextField.setText("");
                        refreshReactionTable();
                    }
                }
            }
        });
        panel.add(addTextButton).colspan(2).row();

        return panel;
    }

    private void refreshReactionTable() {
        if (reactionTable == null) return;
        reactionTable.clearChildren();

        int cols = 3;
        int count = 0;

        // تنظیمات بهتر برای فاصله دکمه‌ها
        reactionTable.defaults().pad(20).space(15);  // padding اطراف هر سلول و فاصله بین سلول‌ها

        for (String reaction : reactionsThatShown) {
            if (reaction.endsWith(".png")) {
                ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
                Texture texture;
                try {
                    texture = new Texture(Gdx.files.internal(reaction));
                } catch (Exception e) {
                    texture = null;
                }

                if (texture != null) {
                    style.imageUp = new TextureRegionDrawable(new TextureRegion(texture));
                    ImageButton btn = new ImageButton(style);
                    btn.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            onReactionSelected(reaction);
                        }
                    });
                    reactionTable.add(btn).width(120).height(120).pad(15);
                } else {
                    TextButton btn = new TextButton("Image\nNot Found", skin);
                    btn.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            onReactionSelected(reaction);
                        }
                    });
                    reactionTable.add(btn).width(120).height(120).pad(15);
                }
            } else {
                TextButton btn = new TextButton(reaction, skin);
                btn.getLabel().setFontScale(1.5f);
                btn.pad(20);  // padding داخل دکمه
                btn.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        onReactionSelected(reaction);
                    }
                });
                reactionTable.add(btn).width(250).height(100).pad(60);
            }

            count++;
            if (count % cols == 0) reactionTable.row();
        }
    }


    private void onReactionSelected(String reaction) {
        HashMap<String, Object> body = new HashMap<>();
        Reactions reactions = new Reactions(reaction, GameApp.getPlayer().getId());
        body.put("reaction", reactions);
        GameApp.c2sConnectionThread.sendMessage(new Message(body, Message.Type.Get_Reaction));
        Main.getMain().setScreen(GameApp.getGameView());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.valueOf("#faa25a"));
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
