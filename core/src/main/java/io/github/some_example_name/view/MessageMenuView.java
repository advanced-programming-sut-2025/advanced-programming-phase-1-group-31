package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.gson.reflect.TypeToken;
import common.Chat;
import common.Message;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.GameApp;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageMenuView implements Screen {

    private long time = System.currentTimeMillis();
    private final Stage stage;
    private final Table rootTable;
    private final Skin skin;
    private final TextButton back;

    // UI parts
    private Table playersTable;
    private Table chatMessagesTable;
    private ScrollPane messagesScroll;
    private TextField inputField;
    private TextButton sendButton;

    // data
    private ArrayList<Chat> chats = new ArrayList<>();
    private List<String> players = new ArrayList<>();
    private String currentRecipient = null; // null => public chat
    private String currentPlayer; // name of this player (determined via reflection helper)

    public MessageMenuView(Skin skin) {
        this.skin = skin;
        stage = new Stage(new ScreenViewport());
        rootTable = new Table();
        rootTable.defaults().pad(8);
        rootTable.setFillParent(true);
        Gdx.input.setInputProcessor(stage);

        back = new TextButton("Back", skin);

        // determine current player name (flexible, tries many getter names)
        currentPlayer = getCurrentPlayerName();

        // build UI
        buildUI();

        // initial load
        loadChats();
        refreshPlayersList();
        refreshMessagesView();
    }

    @Override
    public void show() {
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(GameApp.getGameView());
            }
        });
    }

    /**
     * بارگذاری چت‌ها از سرور. پاسخ باید بدنه‌ای شامل "chats" داشته باشد (ArrayList<Chat>).
     * این فراخوانی ممکن است همزمان با render صدا زده شود (مثل کد اولیهٔ شما).
     */
    private void loadChats() {
        try {
            Message message = GameApp.c2sConnectionThread.sendAndWaitForResponse(new Message(null, Message.Type.Get_Message));
            Type type = new TypeToken<ArrayList<Chat>>() {}.getType();
            ArrayList<Chat> loaded = message.getFromBodyType("chats", type);
            if (loaded != null) {
                this.chats = loaded;
            } else {
                this.chats = new ArrayList<>();
            }
            // update players list from lobby (refresh each load)
            try {
                players = GameApp.player.getUserInfo().getLobby().getPlayers();
            } catch (Exception e) {
                // اگر این متد وجود ندارد، players دست‌نخورده باقی می‌ماند
                Gdx.app.log("MessageMenuView", "Couldn't fetch players list from GameApp.player.getUserInfo().getLobby().getPlayers()");
            }
        } catch (Exception e) {
            Gdx.app.error("MessageMenuView", "Error loading chats", e);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.valueOf("#faa25a"));
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        // هر 1 ثانیه چت‌ها را بروز کن
        if (System.currentTimeMillis() - time > 1000) {
            loadChats();
            refreshPlayersList();
            refreshMessagesView();
            time = System.currentTimeMillis();
        }

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
    }

    /* ========================= UI BUILDERS ========================= */

    private void buildUI() {
        // top row: back + title
        rootTable.top();
        rootTable.add(back).left().colspan(2).row();

        // left column: players list (with Public chat button on top)
        Table leftCol = new Table(skin);
        leftCol.defaults().pad(6).fillX();

        TextButton publicBtn = new TextButton("Public Chat", skin);
        publicBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentRecipient = null;
                refreshPlayersList();
                refreshMessagesView();
            }
        });
        leftCol.add(publicBtn).fillX().row();

        playersTable = new Table(skin);
        playersTable.top();
        ScrollPane playersScroll = new ScrollPane(playersTable, skin);
        playersScroll.setFadeScrollBars(false);
        playersScroll.setFlickScroll(true);

        leftCol.add(playersScroll).expand().fill().row();

        // right column: chat messages + input
        Table rightCol = new Table(skin);
        rightCol.defaults().pad(6);

        chatMessagesTable = new Table(skin);
        chatMessagesTable.top();
        // allow multi-line wrapping
        chatMessagesTable.defaults().left().pad(4).growX();

        messagesScroll = new ScrollPane(chatMessagesTable, skin);
        messagesScroll.setFadeScrollBars(false);
        messagesScroll.setFlickScroll(true);
        messagesScroll.setScrollbarsVisible(true);
        rightCol.add(messagesScroll).expand().fill().row();

        // input row
        Table inputRow = new Table(skin);
        inputField = new TextField("", skin);
        sendButton = new TextButton("Send", skin);
        sendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sendCurrentMessage();
            }
        });

        inputRow.add(inputField).expandX().fillX();
        inputRow.add(sendButton).padLeft(6);
        rightCol.add(inputRow).fillX();

        // put columns in root
        rootTable.add(leftCol).width(220).fillY().top();
        rootTable.add(rightCol).expand().fill();

        stage.addActor(rootTable);
    }

    /**
     * بازسازی لیست پلیرها در ستون سمت چپ.
     * دکمه برای هر پلیر (با پرهیز از نمایش نام خود) ساخته می‌شود.
     */
    private void refreshPlayersList() {
        playersTable.clear();

        // show header (public or selected private)
        Label header = new Label("Players", skin);
        header.setFontScale(1.0f);
        playersTable.add(header).left().row();

        String me = currentPlayer != null ? currentPlayer : "";

        for (String p : players) {
            if (p == null) continue;
            if (p.equals(me)) continue; // skip self
            final String target = p;
            TextButton t = new TextButton(target, skin);
            // visual highlight for selected recipient
            if (target.equals(currentRecipient)) {
                t.setDisabled(true);
            }
            t.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    currentRecipient = target;
                    refreshPlayersList();
                    refreshMessagesView();
                }
            });
            playersTable.add(t).fillX().row();
        }
    }

    /**
     * بازسازی نمایش پیام‌ها بر اساس currentRecipient (null => public).
     */
    private void refreshMessagesView() {
        chatMessagesTable.clear();

        boolean isPublicView = (currentRecipient == null);

        // optional title at top of messages
        String title = isPublicView ? "Public Chat" : ("Private chat with: " + currentRecipient);
        Label titleLabel = new Label(title, skin);
        chatMessagesTable.add(titleLabel).colspan(2).left().padBottom(8).row();

        // iterate chats and add relevant ones
        for (Chat c : chats) {
            if (c == null) continue;
            if (isRelevantToCurrentView(c, isPublicView)) {
                // create label with wrap
                String text = c.getSender() + ": " + c.getMessage();
                Label msgLabel = new Label(text, skin);
                msgLabel.setWrap(true);

                boolean sentByMe = currentPlayer != null && currentPlayer.equals(c.getSender());

                // align to right if sent by me, left otherwise
                if (sentByMe) {
                    Table wrapper = new Table();
                    wrapper.add(msgLabel).width(Math.min(Gdx.graphics.getWidth() - 260, 500)).right();
                    chatMessagesTable.add(wrapper).expandX().fillX().row();
                } else {
                    chatMessagesTable.add(msgLabel).left().row();
                }
            }
        }

        // layout and scroll to bottom
        chatMessagesTable.layout();
        messagesScroll.layout();
        // try to scroll to bottom nicely
        try {
            messagesScroll.setScrollPercentY(1f);
        } catch (Exception ignored) {
            // نسخه‌های مختلف libGDX ممکن است این متد نداشته باشند؛ در آن صورت بگذار بماند.
        }
    }

    private boolean isRelevantToCurrentView(Chat c, boolean isPublicView) {
        if (isPublicView) {
            return c.getReceiver() == null;
        } else {
            // private view: we want messages where (sender==me && receiver==currentRecipient)
            // OR (sender==currentRecipient && receiver==me)
            if (c.getReceiver() == null) return false;
            String r = c.getReceiver();
            String s = c.getSender();
            String me = currentPlayer != null ? currentPlayer : "";

            return (me.equals(s) && currentRecipient.equals(r))
                || (currentRecipient.equals(s) && me.equals(r));
        }
    }

    /**
     * ساخت و ارسال پیام فعلی در inputField.
     */
    private void sendCurrentMessage() {
        String txt = inputField.getText();
        if (txt == null || txt.trim().isEmpty()) return;
        if (currentPlayer == null || currentPlayer.isEmpty()) {
            Gdx.app.log("MessageMenuView", "Cannot send message: current player name unknown.");
            return;
        }

        Chat out = new Chat(txt.trim(), currentPlayer, currentRecipient); // receiver may be null for public
        try {
            HashMap<String, Object> body = new HashMap<>();
            body.put("chat", out);
            GameApp.c2sConnectionThread.sendAndWaitForResponse(new Message(body, Message.Type.Get_Message));
            inputField.setText("");
            loadChats();
            refreshMessagesView();
        } catch (Exception e) {
            Gdx.app.error("MessageMenuView", "Failed to send message", e);
        }
    }

    /**
     * Helper: تلاش می‌کند نام بازیکن جاری را با امتحان چند getter معمول بدست بیاورد.
     * اگر هیچ‌کدام موجود نباشد، رشتهٔ خالی برمی‌گرداند و پیغام لاگ می‌شود.
     */
    private String getCurrentPlayerName() {
        try {
            Object userInfo = GameApp.player.getUserInfo();
            if (userInfo == null) return "";
            String[] tries = {"getUserName", "getUsername", "getName", "getNick", "getUser"};
            for (String m : tries) {
                try {
                    Method method = userInfo.getClass().getMethod(m);
                    Object res = method.invoke(userInfo);
                    if (res != null) return res.toString();
                } catch (NoSuchMethodException ignored) {
                    // متد وجود ندارد -> امتحان متد بعدی
                }
            }
            Gdx.app.log("MessageMenuView", "Couldn't find a name getter on userInfo; please adapt getCurrentPlayerName() if needed.");
        } catch (Exception e) {
            Gdx.app.error("MessageMenuView", "Error fetching current player name via reflection", e);
        }
        return "";
    }
}
