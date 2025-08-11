package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
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
import java.util.stream.Collectors;

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
        back = new TextButton("back", skin);
        sortByMoney = new TextButton("Sort: Money", skin);
        sortBySkills = new TextButton("Sort: Skills", skin);
        listTable = new Table();
        scrollPane = new ScrollPane(listTable, skin);
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
        listTable.top().defaults().pad(8).expandX().fillX();
        for (PlayerBoard p : copy) {
            Table row = new Table();
            Label name = new Label(p.name(), skin);
            name.setAlignment(Align.left);
            name.setColor(Color.valueOf("#0f76f0"));
            name.setFontScale(1.15f);
            Label money = new Label(String.valueOf(p.money()), skin);
            money.setAlignment(Align.right);
            money.setColor(Color.valueOf("#f4c542"));
            money.setFontScale(1.05f);
            Table skills = new Table();
            skills.defaults().padRight(6);
            Label farming = new Label("🌾 " + p.farming(), skin);
            farming.setColor(Color.valueOf("#7bc043"));
            farming.setFontScale(0.95f);
            Label fishing = new Label("🎣 " + p.fishing(), skin);
            fishing.setColor(Color.valueOf("#34a1c8"));
            fishing.setFontScale(0.95f);
            Label foraging = new Label("🍄 " + p.foraging(), skin);
            foraging.setColor(Color.valueOf("#9b59b6"));
            foraging.setFontScale(0.95f);
            Label mining = new Label("⛏️ " + p.mining(), skin);
            mining.setColor(Color.valueOf("#d35400"));
            mining.setFontScale(0.95f);
            skills.add(farming);
            skills.add(fishing);
            skills.add(foraging);
            skills.add(mining);
            row.add(name).expandX().left();
            row.add(skills).padLeft(10);
            row.add(money).width(80).right();
            listTable.add(row).expandX().fillX();
            listTable.row();
        }
        listTable.add().expandY().row();
        scrollPane.setScrollPercentY(0f);
    }

    private int skillTotal(PlayerBoard p){
        return p.farming() + p.fishing() + p.foraging() + p.mining();
    }

    @Override
    public void show() {
        root.clear();
        Table header = new Table();
        Label title = new Label("Score Board", skin);
        title.setFontScale(1.6f);
        title.setColor(Color.valueOf("#ffffff"));
        header.add(title).left().pad(10).expandX();
        header.add(sortByMoney).pad(6);
        header.add(sortBySkills).pad(6);
        header.add(back).pad(6);
        root.add(header).expandX().fillX().row();
        root.add(scrollPane).expand().fill().pad(18);
        stage.addActor(root);

        sortByMoney.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                sortMode = SortMode.MONEY;
                updateList();
            }
        });

        sortBySkills.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                sortMode = SortMode.SKILLS;
                updateList();
            }
        });

        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Main.getMain().setScreen(GameApp.getGameView());
            }
        });

        updateList();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.valueOf("#faa25a"));
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
