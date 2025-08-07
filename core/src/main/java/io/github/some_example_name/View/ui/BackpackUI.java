package io.github.some_example_name.View.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.Backpack;
import io.github.some_example_name.model.enums.toolTypes.TrashCanType;
import io.github.some_example_name.model.materials.Material;
import io.github.some_example_name.model.materials.Tools.Tool;
import io.github.some_example_name.model.materials.Tools.TrashCan;

import java.util.Map;

public class BackpackUI extends Window {
    private final Backpack backpack;
    private final DragAndDrop dragAndDrop;
    private final Texture trashTexture;
    private final ImageButton trashButton;

    public BackpackUI(Backpack backpack, Skin skin) {
        super("Backpack", skin);
        this.backpack = backpack;
        this.dragAndDrop = new DragAndDrop();
        TrashCan trashCan = (TrashCan) App.getCurrentGame().getActivePlayer().getInventory()
            .isExistToolOrNull(new TrashCan(TrashCanType.Initial));
        this.trashTexture = new Texture(Gdx.files.internal(trashCan.getTexturePath()));

        // تنظیمات پنجره
        setSize(1800, 900);
        setPosition(30, 30);
        setMovable(true);
        setResizable(false);

        // ایجاد سطل آشغال
        trashButton = new ImageButton(new Image(trashTexture).getDrawable());
        trashButton.setSize(64, 64);
        add(trashButton).size(64).align(Align.right).padRight(20);
        row();

        // ایجاد اسلات‌های بکپک
        refreshBackpackItems();

        // تنظیم Drag and Drop برای سطل آشغال
        setupTrashDropTarget();
        setVisible(false);
    }

    public void refreshBackpackItems() {
        // پاک کردن آیتم‌های قبلی
        clearChildren();
        add(trashButton).size(64).align(Align.right).padRight(20);
        row();

        // اضافه کردن آیتم‌های جدید
        Table itemsTable = new Table();
//        itemsTable.defaults().size(80).pad(5);

        int i = 0;
        for (Map.Entry<Material, Integer> entry : backpack.getElements().entrySet()) {
            Material material = entry.getKey();
            int count = entry.getValue();

            Texture itemTexture = new Texture(Gdx.files.internal(material.getTexturePath()));
            OverlayImageButton itemButton = new OverlayImageButton(getSkin().get("Inventory", TextButton.TextButtonStyle.class), itemTexture);
            itemButton.setCount(count);

            itemsTable.add(itemButton).size(128, 128).pad(30);
            if ((i + 1) % 3 == 0)
                itemsTable.row();  // توجه: اینجا باید itemsTable.row() باشد نه itemButton.row()
            setupItemDragSource(itemButton, material);
            i++;
        }
        for (int j =0; j < backpack.getTools().size(); j++) {
            Material material = (Material) backpack.getTools().get(j) ;

            Texture itemTexture = new Texture(Gdx.files.internal(material.getTexturePath()));
            OverlayImageButton itemButton = new OverlayImageButton(getSkin().get("Inventory", TextButton.TextButtonStyle.class), itemTexture);

            itemsTable.add(itemButton).size(128, 128).pad(30);
            if ((j + 1) % 3 == 0)
                itemsTable.row();

            // تنظیم Drag and Drop برای هر آیتم
            setupItemDragSource(itemButton, material);
        }

        add(itemsTable).colspan(2).expand().fill();
    }

    private void setupItemDragSource(OverlayImageButton button, Material material) {
        dragAndDrop.addSource(new DragAndDrop.Source(button) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject(material);

                // نمایش تصویر هنگام درگ
                Image dragImage = new Image(button.getOverlayTexture());
                payload.setDragActor(dragImage);

                // نمایش تصویر اصلی در مکان اصلی
                Image validImage = new Image(button.getOverlayTexture());
                validImage.setColor(0, 1, 0, 0.5f);
                payload.setValidDragActor(validImage);

                Image invalidImage = new Image(button.getOverlayTexture());
                invalidImage.setColor(1, 0, 0, 0.5f);
                payload.setInvalidDragActor(invalidImage);


                return payload;
            }
        });
    }

    private void setupTrashDropTarget() {
        dragAndDrop.addTarget(new DragAndDrop.Target(trashButton) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                Material material = (Material) payload.getObject();
                if (material instanceof Tool tool) {
                    backpack.getTools().remove(tool);
                }
                backpack.removeElementFromBackpack(material, -1); // حذف همه آیتم‌ها
                refreshBackpackItems(); // بروزرسانی نمایش
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void dispose() {
        trashTexture.dispose();
    }
}
