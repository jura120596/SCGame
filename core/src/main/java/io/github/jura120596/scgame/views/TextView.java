package io.github.jura120596.scgame.views;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.jura120596.scgame.helpers.FontBuilder;

public class TextView  extends View{
    protected BitmapFont font;
    protected String text;

    public TextView(BitmapFont font, float x, float y) {
        super(x, y);
        this.font = font;
    }

    public TextView(BitmapFont font, float x, float y, String text) {
        this(font, x, y);
        this.setText(text);
    }
    public void setText(String text) {
        this.text = text;
        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        width = glyphLayout.width;
        height = glyphLayout.height;
    }

    @Override
    public void draw(SpriteBatch batch) {
        font.draw(batch, text, x - width/2, y);
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
