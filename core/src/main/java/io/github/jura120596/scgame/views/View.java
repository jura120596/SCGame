package io.github.jura120596.scgame.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

public class View  implements Disposable {
    float   x;

    Texture texture;
    float   y;
    float width;
    float height;

    public View(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public View(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isHit(Vector3 touch) {
        return isHit(touch.x, touch.y);
    }
    public boolean isHit(float tx, float ty) {
        return tx >= x && tx <= x + width && ty >= y && ty <= y + height;
    }

    public void draw(SpriteBatch batch) {
    }

    @Override
    public void dispose() {

    }

}
