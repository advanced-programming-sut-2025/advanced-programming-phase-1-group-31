package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.gson.reflect.TypeToken;
import common.Message;
import common.PlayerBoard;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameApp;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ScoreBoardMenuView implements Screen {
    private long time = System.currentTimeMillis();
    private final Stage stage;
    private final Table root;
    private final Skin skin;
    private final TextButton back;
    private final TextButton sortByMoney;
    private final TextButton sortBySkills;
    private final Table listTable;
    private final ScrollPane scrollPane;
    private ArrayList<PlayerBoard> playerBoards = new ArrayList<>();

    private enum SortMode {MONEY, SKILLS}
    private SortMode sortMode = SortMode.MONEY;

    public ScoreBoardMenuView(Skin skin) {
        this.skin = skin;
        stage = new Stage(new ScreenViewport());
        root = new Table();
        root.setFillParent(true);
        root.center();

        // Top controls
        back = new TextButton("Back", skin);
        sortByMoney = new TextButton("Sort: Money", skin);
        sortBySkills = new TextButton("Sort: Skills", skin);

        // Content list
        listTable = new Table();
        listTable.defaults().pad(8);
        scrollPane = new ScrollPane(listTable, skin);
        scrollPane.setFadeScrollBars(false);

        Gdx.input.setInputProcessor(stage);

        loadScoreBoard();
    }

    private void loadScoreBoard(){
        try {
            Message message = GameApp.c2sConnectionThread.sendAndWaitForResponse(new Message(new HashMap<>(), Message.Type.Get_Player_Board));
            Type type = new TypeToken<ArrayList<PlayerBoard>>() {}.getType();
            ArrayList<PlayerBoard> result = message.getFromBodyType("player-boards", type);
            if (result != null) playerBoards = result;
            else playerBoards = new ArrayList<>();
        } catch (Exception e) {
            playerBoards = new ArrayList<>();
        }
        updateList();
    }

    private void updateList(){
        List<PlayerBoard> copy = new ArrayList<>(playerBoards);
        if (sortMode == SortMode.MONEY) {
            copy.sort(Comparator.comparingInt(PlayerBoard::money).reversed());
        } else {
            copy.sort(Comparator.comparingInt(this::skillTotal).reversed());
        }

        listTable.clear();
        listTable.top();

        // header row inside the scroll area (so header stays with list when scrolling)
        Table listHeader = new Table();
        listHeader.add(new Label("#", skin)).width(48).left();
        Label nameH = new Label("Player", skin);
        nameH.setFontScale(1.5f);
        nameH.setAlignment(Align.left);
        listHeader.add(nameH).expandX().left();
        Label skillsH = new Label("Skills (F / Fi / Fo / M) ", skin);
        skillsH.setFontScale(1.5f);
        skillsH.setAlignment(Align.center);
        listHeader.add(skillsH).padLeft(8).padRight(20);
        Label moneyH = new Label("| Money", skin);
        moneyH.setFontScale(1.5f);
        moneyH.setAlignment(Align.right);
        listHeader.add(moneyH).width(110).right();
        listTable.add(listHeader).expandX().fillX().padBottom(6);
        listTable.row();

        int index = 0;
        for (PlayerBoard p : copy) {
            index++;
            Table row = new Table();
            row.defaults().pad(15);

            Label rank = new Label(String.format("%02d", index), skin);
            rank.setAlignment(Align.left);
            rank.setFontScale(1.2f);
            rank.setColor(Color.DARK_GRAY);
            rank.setWidth(48);

            Label name = new Label(p.name(), skin);
            name.setAlignment(Align.left);
            name.setFontScale(1.5f);
            Color color = (p.name().equals(GameApp.player.getUsername())) ?  Color.GREEN : Color.DARK_GRAY;
            name.setColor(color);

            // Skills compact presentation (no emojis)
            Table skills = new Table();
            skills.defaults().padRight(8).left();

            Label farming = smallStatLabel("F: ", p.farming());
            Label fishing = smallStatLabel("Fi: ", p.fishing());
            Label foraging = smallStatLabel("Fo: ", p.foraging());
            Label mining = smallStatLabel("M: ", p.mining());

            farming.setFontScale(1.25f);
            fishing.setFontScale(1.25f);
            foraging.setFontScale(1.25f);
            mining.setFontScale(1.25f);

            skills.add(farming).pad(15);
            skills.add(fishing).pad(15);
            skills.add(foraging).pad(15);
            skills.add(mining);

            Label money = new Label(String.format("%,d", p.money()), skin);
            money.setAlignment(Align.right);
            money.setFontScale(1.25f);
            money.setColor(Color.valueOf("#f39c12"));
            money.setWidth(110);

            row.add(rank).width(48).left();
            row.add(name).expandX().left();
            row.add(skills).padLeft(12).pad(20);
            row.add(money).width(110).right();

            listTable.add(row).expandX().fillX().padTop(4).padBottom(4);
            listTable.row();

            // separator
            try {
                Drawable sep = skin.newDrawable("white", Color.valueOf("#e0e0e0"));
                Image line = new Image(sep);
                line.setScaling(Scaling.stretch);
                line.setHeight(1);
                listTable.add(line).colspan(4).expandX().fillX().height(1).padTop(2).padBottom(2);
                listTable.row();
            } catch (Exception ignored) {
                // ignore if drawable creation fails
            }
        }

        // filler to push content to top
        listTable.add().expandY().row();
        scrollPane.setScrollPercentY(0f);
    }

    private Label smallStatLabel(String shortName, int value){
        Label l = new Label(shortName + value, skin);
        l.setFontScale(0.9f);
        l.setAlignment(Align.left);
        l.setColor(Color.DARK_GRAY);
        return l;
    }

    private int skillTotal(PlayerBoard p){
        return p.farming() + p.fishing() + p.foraging() + p.mining();
    }

    @Override
    public void show() {
        root.clear();

        // top header panel
        Table header = new Table();
        header.pad(12);
        try {
            Drawable hdrBg = skin.newDrawable("white", Color.valueOf("#2c3e50"));
            header.setBackground(hdrBg);
        } catch (Exception ignored) {}

        Label title = new Label("Score Board", skin);
        title.setFontScale(1.6f);
        title.setColor(Color.WHITE);
        header.add(title).left().padRight(8).expandX();

        // toggle style for active sort button
        updateSortButtonStyles();
        header.add(sortByMoney).pad(6);
        header.add(sortBySkills).pad(6);
        header.add(back).pad(6);

        root.add(header).expandX().fillX().row();

        // content area with padding and white background
        Table content = new Table(skin);
        content.setFillParent(false);
        try {
            Drawable contentBg = skin.newDrawable("white", Color.valueOf("#ffffff"));
            content.setBackground(contentBg);
        } catch (Exception ignored) {}
        content.pad(14);
        content.add(scrollPane).expand().fill().minHeight(200).minWidth(600);

        root.add(content).expand().fill().pad(18).row();

        stage.addActor(root);

        sortByMoney.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                sortMode = SortMode.MONEY;
                updateSortButtonStyles();
                updateList();
            }
        });

        sortBySkills.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                sortMode = SortMode.SKILLS;
                updateSortButtonStyles();
                updateList();
            }
        });

        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Main.getMain().setScreen(GameApp.getGameView());
            }
        });

        // initial population
        updateList();
    }

    private void updateSortButtonStyles(){
        // simple visual state: enabled/disabled
        if (sortMode == SortMode.MONEY) {
            sortByMoney.setDisabled(true);
            sortBySkills.setDisabled(false);
        } else {
            sortByMoney.setDisabled(false);
            sortBySkills.setDisabled(true);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.valueOf("#fafafa"));
        if (System.currentTimeMillis() - time > 5000) {
            loadScoreBoard();
            time = System.currentTimeMillis();
        }
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
