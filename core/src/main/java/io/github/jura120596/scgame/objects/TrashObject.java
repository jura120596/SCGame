package io.github.jura120596.scgame.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import io.github.jura120596.scgame.helpers.GameSettings;

public class TrashObject  extends GameObject{
    private static final int paddingHorizontal = 30;

    public TrashObject(String texturePath, int width, int height, World world) {
        super(
            texturePath,
            width / 2 + paddingHorizontal + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width)),
            GameSettings.SCREEN_HEIGHT + height / 2,
            width, height,
            world,
            GameSettings.TRASH_BIT
        );
        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
        setLivesLeft(1);
    }

    public boolean isInFrame() {
        return getY() + height/2 > 0;
    }

    @Override
    public boolean hasToBeDestroyed() {
        return super.hasToBeDestroyed() || !isInFrame() ;
    }
}
