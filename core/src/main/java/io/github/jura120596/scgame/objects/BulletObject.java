package io.github.jura120596.scgame.objects;

import com.badlogic.gdx.physics.box2d.World;

import io.github.jura120596.scgame.helpers.GameSettings;

public class BulletObject extends GameObject{

    public BulletObject(String texturePath, int x, int y, int width, int height, World world) {
        super(texturePath, x, y, width, height, world, GameSettings.BULLET_BIT);
        body.setLinearVelocity(0 , GameSettings.BULLET_VELOCITY);
        body.setBullet(true);
        setLivesLeft(1);
    }
    public boolean hasToBeDestroyed() {

        return super.hasToBeDestroyed() || getY() - height/2 > GameSettings.SCREEN_HEIGHT ;
    }

}
