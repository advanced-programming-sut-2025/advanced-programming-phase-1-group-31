package io.github.some_example_name.View.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import io.github.some_example_name.model.enums.toolTypes.WateringCanType;
import io.github.some_example_name.model.materials.Tools.WateringCan;

public class OverlayImageButton extends TextButton {
    private Texture overlayTexture;
    private final BitmapFont font = new BitmapFont();
    private final GlyphLayout layout = new GlyphLayout();
    private int count = -1;
    private boolean isSelected = false; // برای رسم هاتبار سلکشن
    private WateringCan wateringCan ;
    private final Texture selectionTexture = new Texture(Gdx.files.internal("hotbar_selection.png"));

    public OverlayImageButton(TextButtonStyle style, Texture overlayTexture) {
        super("", style);
        this.overlayTexture = overlayTexture;
    }

    public OverlayImageButton(TextButtonStyle style) {
        super("", style);
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public Texture getOverlayTexture() {
        return overlayTexture;
    }

    public WateringCan getWateringCan() {
        return wateringCan;
    }

    public void setWateringCan(WateringCan wateringCan) {
        this.wateringCan = wateringCan;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (isSelected && selectionTexture != null) {
            batch.setColor(1, 1, 1, parentAlpha);
            batch.draw(selectionTexture, getX(), getY(), getWidth(), getHeight());
        }

        if (overlayTexture != null) {
            float scale = 0.7f;
            float iconWidth = getWidth() * scale;
            float iconHeight = getHeight() * scale;
            float overlayX = getX() + (getWidth() - iconWidth) / 2f;
            float overlayY = getY() + (getHeight() - iconHeight) / 2f;
            batch.setColor(getColor());
            batch.draw(overlayTexture, overlayX, overlayY, iconWidth, iconHeight);
        }

        if (count >= 0) {
            String text = String.valueOf(count);
            font.getData().setScale(1.2f);
            layout.setText(font, text);
            float textX = getX() + getWidth() - layout.width - 20;
            float textY = getY() + getHeight() - 20;
            font.setColor(Color.BLACK);
            font.draw(batch, layout, textX, textY);
        }
        if (wateringCan != null) {
            float barWidth = 10f;
            float barHeight = getHeight() * 0.8f;
            float barX = getX() + getWidth() - barWidth - 5;
            float barY = getY() + (getHeight() - barHeight) / 2f;

            WateringCanType wateringCanType = (WateringCanType) wateringCan.getType();

            float max = wateringCanType.getCapacity();
            float current = wateringCan.getMuch();
            float filledHeight = (current / max) * barHeight;

            batch.setColor(Color.DARK_GRAY); // پس‌زمینه
            batch.draw(overlayTexture, barX, barY, barWidth, barHeight);

            batch.setColor(Color.BLUE); // آب
            batch.draw(overlayTexture, barX, barY, barWidth, filledHeight);

            batch.setColor(Color.WHITE); // ریست رنگ
        }
    }
    public static ProgressBar createWaterProgressBar(WateringCan wateringCan, Skin skin) {
        WateringCanType type = (WateringCanType) wateringCan.getType();
        float max = type.getCapacity();
        float current = wateringCan.getMuch();

        ProgressBar bar = new ProgressBar(0f, max, 1f, true, skin , "Water");
        bar.setValue(current);
        bar.setAnimateDuration(0.3f); // انیمیشن پر شدن

        bar.setSize(16, 16); // اندازه عمودی
        bar.setAnimateInterpolation(Interpolation.pow3In); // دلخواه
        return bar;
    }
}
