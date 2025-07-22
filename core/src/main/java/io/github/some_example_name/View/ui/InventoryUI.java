package io.github.some_example_name.View.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class InventoryUI extends Window {
    private final Stack contentStack = new Stack();
    private final Table inventoryTab = new Table();
    private final Table skillsTab = new Table();
    private final Table socialTab = new Table();
    private final Table mapTab = new Table();

    public InventoryUI(Skin skin) {
        super("Inventory", skin);
        setSize(1000, 1000);
        setMovable(true);
        setVisible(false);
        center();

        Table tabButtons = new Table();
        TextButton invBtn = new TextButton("Inventory", skin);
        TextButton skillsBtn = new TextButton("Skills", skin);
        TextButton socialBtn = new TextButton("Social", skin);
        TextButton mapBtn = new TextButton("Map", skin);

        tabButtons.add(invBtn).pad(5);
        tabButtons.add(skillsBtn).pad(5);
        tabButtons.add(socialBtn).pad(5);
        tabButtons.add(mapBtn).pad(5);

        invBtn.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                contentStack.clearChildren();
                contentStack.add(inventoryTab);
            }
        });
        // بقیه تب‌ها همینطور...

        // TODO: add content to each tab
        inventoryTab.add(new Label("Inventory Content", skin));
        skillsTab.add(new Label("Skills Content", skin));
        socialTab.add(new Label("Friends & NPCs", skin));
        mapTab.add(new Label("Map View", skin));

        contentStack.add(inventoryTab);
        contentStack.add(skillsTab);
        contentStack.add(socialTab);
        contentStack.add(mapTab);
        contentStack.setVisible(false);

        clear();
        add(tabButtons).row();
        add(contentStack).expand().fill();
    }

    public void toggle() {
        setVisible(!isVisible());
    }

    public void update() {
        // for future animation/tooltips etc.
    }
}
