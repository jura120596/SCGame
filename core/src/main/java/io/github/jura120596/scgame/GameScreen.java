package io.github.jura120596.scgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import io.github.jura120596.scgame.helpers.ContactManager;
import io.github.jura120596.scgame.helpers.FontBuilder;
import io.github.jura120596.scgame.helpers.GameResources;
import io.github.jura120596.scgame.helpers.GameSession;
import io.github.jura120596.scgame.helpers.GameSettings;
import io.github.jura120596.scgame.helpers.GameState;
import io.github.jura120596.scgame.helpers.MemoryManager;
import io.github.jura120596.scgame.objects.BulletObject;
import io.github.jura120596.scgame.objects.ShipObject;
import io.github.jura120596.scgame.objects.TrashObject;
import io.github.jura120596.scgame.views.ButtonView;
import io.github.jura120596.scgame.views.HearthView;
import io.github.jura120596.scgame.views.ImageView;
import io.github.jura120596.scgame.views.MovingBackgroundView;
import io.github.jura120596.scgame.views.RecordsListView;
import io.github.jura120596.scgame.views.TextView;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen extends ScreenAdapter {

    Main game;
    ShipObject shipObject;
    ArrayList<TrashObject> trashArray;
    ArrayList<BulletObject> bullets;

    private final GameSession gameSession;
    private final ContactManager contactManager;
    private final MovingBackgroundView backgroundView;

    ImageView topBlackoutView,fullBlackoutView;
    HearthView liveView;
    TextView scoreTextView,pauseTextView;
    ButtonView pauseButton, homeButton, continueButton;
    private int scores;
    TextView recordsTextView;
    RecordsListView recordsListView;
    ButtonView homeButton2;

    public GameScreen(Main myGdxGame) {
        this.game = myGdxGame;
        shipObject = new ShipObject(GameResources.SHIP_IMG_PATH, GameSettings.SCREEN_WIDTH / 2, 150,
            GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT, myGdxGame.world);
        trashArray = new ArrayList<>();
        bullets = new ArrayList<>();
        gameSession = new GameSession();
        contactManager = new ContactManager(this.game.world);
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        topBlackoutView = new ImageView(0, GameSettings.SCREEN_HEIGHT - 100, GameResources.BLACKOUT_TOP_IMG_PATH);
        fullBlackoutView = new ImageView(0, 0, GameResources.BLACKOUT_FULL_IMG_PATH);
        liveView = new HearthView(300, GameSettings.SCREEN_HEIGHT - 50);
        scoreTextView = new TextView(game.whiteFont, 150, GameSettings.SCREEN_HEIGHT - 50);
        pauseTextView = new TextView(game.whiteFont, GameSettings.SCREEN_WIDTH/2, GameSettings.SCREEN_HEIGHT/2+200, "PAUSE");
        pauseButton = new ButtonView(605, 1200, 46, 54, GameResources.PAUSE_IMG_PATH);
        int w = GameSettings.SCREEN_WIDTH / 5;
        homeButton = new ButtonView(w, GameSettings.SCREEN_HEIGHT/2, w, 54, game.blackFont, GameResources.SHORT_BUTTON_PATH, "HOME");
        continueButton = new ButtonView(w*3, GameSettings.SCREEN_HEIGHT/2, w,54,  game.blackFont,GameResources.SHORT_BUTTON_PATH, "RESUME");
        scores = 100;
        recordsListView = new RecordsListView(myGdxGame.whiteFont, 690);
        recordsTextView = new TextView(myGdxGame.largeWhiteFont, 206, 842, "Last records");
        homeButton2 = new ButtonView(
            280, 365,
            160, 70,
            myGdxGame.blackFont,
            GameResources.SHORT_BUTTON_PATH,
            "Home"
        );
    }

    private void updateTrash() {
        if (gameSession.shouldSpawnTrash()) {
            TrashObject trashObject = new TrashObject(
                GameResources.TRASH_IMG_PATH,
                GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                game.world
            );
            trashArray.add(trashObject);
        }
        for (int i = 0; i < trashArray.size(); i++) {
            TrashObject to = trashArray.get(i);

            if (!to.isAlive() && game.audioManager.isSoundOn) {
                game.audioManager.explosionSound.play(0.2f);
                gameSession.destructionRegistration();
            }
            if (to.hasToBeDestroyed()) {
                game.world.destroyBody(to.body);
                trashArray.remove(i--);
            }
        }
    }
    private void updateBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).hasToBeDestroyed()) {
                game.world.destroyBody(bullets.get(i).body);
                bullets.remove(i--);
            }
        }
        if (shipObject.needToShoot()) {
            BulletObject laserBullet = new BulletObject(
                GameResources.BULLET_IMG_PATH,
                shipObject.getX(), shipObject.getY() + GameSettings.SHIP_HEIGHT / 2,
                GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                game.world
            );
            bullets.add(laserBullet);
            if (game.audioManager.isSoundOn) game.audioManager.shootSound.play();
        }
    }

    @Override
    public void render(float delta) {

        if (gameSession.state == GameState.PLAYING) {
            game.stepWorld();
            scoreTextView.setText("Score: " + scores);
            liveView.setLeftLives(shipObject.getLivesLeft());
            backgroundView.move();
            updateTrash();
            updateBullets();
            gameSession.updateScore();
            scoreTextView.setText("Score: " + gameSession.getScore());
            if (!shipObject.isAlive()) {
                gameSession.endGame();
                recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }
        }
        draw();
        handleInput();
    }

    private void draw() {
        game.camera.update();
        SpriteBatch batch = game.batch;
        batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        batch.begin();

        backgroundView.draw(batch);

        shipObject.draw(batch);
        for (TrashObject trash : trashArray) trash.draw(batch);
        for (BulletObject bullet : bullets) bullet.draw(batch);

        topBlackoutView.draw(batch);
        liveView.draw(batch);
        scoreTextView.draw(batch);
        pauseButton.draw(batch);

        if (gameSession.state == GameState.PAUSED) {
            fullBlackoutView.draw(batch);
            pauseTextView.draw(batch);
            homeButton.draw(batch);
            continueButton.draw(batch);
        } else if (GameState.ENDED == gameSession.state) {
            fullBlackoutView.draw(batch);
            recordsTextView.draw(batch);
            recordsListView.draw(batch);
            homeButton2.draw(batch);
        }


        batch.end();

    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            game.touch = game.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            switch (gameSession.state) {
                case PLAYING:
                    if (pauseButton.isHit(game.touch)) {
                        gameSession.pauseGame();
                    } else {
                        shipObject.move(game.touch);
                    }
                    break;

                case PAUSED:
                    if (continueButton.isHit(game.touch)) {
                        gameSession.resumeGame();
                    }
                    if (homeButton.isHit(game.touch)) {
                        game.setScreen(game.screenMenu);
                    }
                    break;

                case ENDED:
                    if (homeButton2.isHit(game.touch)) {
                        game.setScreen(game.screenMenu);
                    }
                    break;
            }
        }

    }
    private void restartGame() {

        for (int i = 0; i < trashArray.size(); i++) {
            game.world.destroyBody(trashArray.get(i).body);
            trashArray.remove(i--);
        }

        if (shipObject != null) {
            game.world.destroyBody(shipObject.body);
        }

        shipObject = new ShipObject(
            GameResources.SHIP_IMG_PATH,
            GameSettings.SCREEN_WIDTH / 2, 150,
            GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
            game.world
        );

        bullets.clear();
        gameSession.startGame();
    }

    @Override
    public void show() {
        restartGame();
    }
}
