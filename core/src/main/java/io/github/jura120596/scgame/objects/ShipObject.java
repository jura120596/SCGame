package io.github.jura120596.scgame.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

import io.github.jura120596.scgame.helpers.GameSettings;

public class ShipObject extends GameObject {
    private long lastShotTime = 0;
    private int livesLeft;

    public ShipObject(String texturePath, int x, int y, int width, int height, World world) {
        super(texturePath, x, y, width, height, world, GameSettings.SHIP_BIT);
        body.setLinearDamping(5);
        this.setLivesLeft(3);
    }

    private void putInFrame() {
        if (getY() > (GameSettings.SCREEN_HEIGHT / 2f - height / 2f)) {
            setY(GameSettings.SCREEN_HEIGHT / 2 - height / 2);
        }
        if (getY() <= (height / 2f)) {
            setY(height / 2);
        }
        if (getX() < (-width / 2f)) {
            setX(GameSettings.SCREEN_WIDTH);
        }
        if (getX() > (GameSettings.SCREEN_WIDTH + width / 2f)) {
            setX(0);
        }
    }
    @Override
    public void draw(SpriteBatch batch) {
        putInFrame();
        super.draw(batch);
    }


    public boolean needToShoot() {
        if (TimeUtils.millis() - this.lastShotTime >= GameSettings.SHOOTING_COOL_DOWN) {
            lastShotTime = TimeUtils.millis();
            return true;
        }
        return false;
    }

    @Override
    public void hit(GameObject hitObject) {
        super.hit(hitObject);
    }
}
