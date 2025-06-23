package io.github.jura120596.scgame.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ImageView extends View{
    Texture texture;
    public ImageView(float x, float y, String path) {
        super(x, y);
        texture = new Texture(path);
        this.width = texture.getWidth() ;
        this.height = texture.getHeight() ;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
