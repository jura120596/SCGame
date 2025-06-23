package io.github.jura120596.scgame.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.jura120596.scgame.helpers.GameResources;
import io.github.jura120596.scgame.helpers.GameSettings;

public class HearthView extends ImageView{
    private int leftLives;
    private int livePadding = GameSettings.LIVE_PADDING;

    public HearthView(float x, float y) {
        super(x, y, GameResources.BLACKOUT_HEARTH_IMG_PATH);
        leftLives = 0;
    }
    public void setLeftLives(int leftLives) {
        this.leftLives = leftLives;
    }
    @Override
    public void draw(SpriteBatch batch) {
        if (leftLives > 0) batch.draw(texture, x + (texture.getWidth() + livePadding), y, width, height);
        if (leftLives > 1) batch.draw(texture, x, y, width, height);
        if (leftLives > 2) batch.draw(texture, x + 2 * (texture.getWidth() + livePadding), y, width, height);
    }
}
