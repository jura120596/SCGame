package io.github.jura120596.scgame;

import static io.github.jura120596.scgame.helpers.GameSettings.POSITION_ITERATIONS;
import static io.github.jura120596.scgame.helpers.GameSettings.STEP_TIME;
import static io.github.jura120596.scgame.helpers.GameSettings.VELOCITY_ITERATIONS;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

import java.awt.Menu;

import io.github.jura120596.scgame.helpers.AudioManager;
import io.github.jura120596.scgame.helpers.FontBuilder;
import io.github.jura120596.scgame.helpers.GameResources;
import io.github.jura120596.scgame.helpers.GameSettings;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public SpriteBatch batch;
    public OrthographicCamera camera;

    public GameScreen screenGame;
    public MenuScreen screenMenu;
    public SettingsScreen screenSettings;
    public Vector3 touch;
    protected World world;
    private float accumulator;
    public  BitmapFont whiteFont, largeWhiteFont, blackFont;
    public AudioManager audioManager;

    @Override
    public void create() {
        Box2D.init();
        world = new World(new Vector2(0, 0), true);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        whiteFont = FontBuilder.generate(24, Color.WHITE, GameResources.FONT_PATH);
        largeWhiteFont = FontBuilder.generate(40, Color.WHITE, GameResources.FONT_PATH);
        blackFont = FontBuilder.generate(24, Color.BLACK, GameResources.FONT_PATH);
        audioManager = new AudioManager();
        screenGame = new GameScreen(this);
        screenMenu = new MenuScreen(this);
        screenSettings = new SettingsScreen(this);
        setScreen(screenMenu);


    }
    public void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += delta;

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
