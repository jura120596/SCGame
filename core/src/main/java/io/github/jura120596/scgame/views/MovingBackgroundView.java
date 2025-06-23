package io.github.jura120596.scgame.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.jura120596.scgame.helpers.GameSettings;

public class MovingBackgroundView extends View {


    Texture texture;
    int texture1Y;
    int texture2Y;
    int speed = 2;

    public MovingBackgroundView(String pathToTexture) {
        super(0, 0);
        texture1Y = 0;
        texture2Y = GameSettings.SCREEN_HEIGHT;
        texture = new Texture(pathToTexture);
    }

    public void move() {
        texture1Y -= speed;
        texture2Y -= speed;

        if (texture1Y < -GameSettings.SCREEN_HEIGHT) {
            texture1Y = GameSettings.SCREEN_HEIGHT;
        }
        if (texture2Y < -GameSettings.SCREEN_HEIGHT) {
            texture2Y = GameSettings.SCREEN_HEIGHT;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, texture1Y);
        batch.draw(texture, x, texture2Y);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
