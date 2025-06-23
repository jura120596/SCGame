package io.github.jura120596.scgame.helpers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import io.github.jura120596.scgame.objects.GameObject;

public class ContactManager {
    protected World world;

    public ContactManager(World world) {
        this.world = world;
        init();
    }

    private void init() {
        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();
                GameObject a = (GameObject) fixA.getUserData();
                GameObject b = (GameObject) fixB.getUserData();
                int bitA = fixA.getFilterData().categoryBits;
                int bitB = fixB.getFilterData().categoryBits;

                int bits = bitA | bitB;
                boolean isBullet = (bits & GameSettings.BULLET_BIT) > 0;
                boolean isShip = (bits & GameSettings.SHIP_BIT) > 0;
                boolean isTrash = (bits & GameSettings.TRASH_BIT) > 0;
                if (isBullet && isTrash || isShip && isTrash) {
                    if (a != null) a.hit(b);
                    if (b != null) b.hit(a);
                }
            }

            @Override
            public void endContact(Contact contact) {
                // код, выполняющийся после завершения контакта
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                // код, выполняющийся перед вычислением всех контактоа
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                // код, выполняющийся сразу после вычислений контактов
            }
        });
    }
}
