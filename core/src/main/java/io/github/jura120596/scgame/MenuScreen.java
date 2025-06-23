package io.github.jura120596.scgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.jura120596.scgame.helpers.GameResources;
import io.github.jura120596.scgame.helpers.GameSettings;
import io.github.jura120596.scgame.views.ButtonView;
import io.github.jura120596.scgame.views.MovingBackgroundView;
import io.github.jura120596.scgame.views.TextView;

public class MenuScreen extends ScreenAdapter {

    Main game;
    MovingBackgroundView bg;

    TextView title;
    ButtonView startButtonView;
    ButtonView settingsButtonView;
    ButtonView exitButtonView;

    public MenuScreen(Main game) {
        this.game = game;
        bg = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        title = new TextView(game.largeWhiteFont, GameSettings.SCREEN_WIDTH / 2, 1000, "SCGame");
        startButtonView = new ButtonView(140, 646, 440, 70, game.blackFont, GameResources.LONG_BUTTON_PATH, "start");
        settingsButtonView = new ButtonView(140, 551, 440, 70, game.blackFont, GameResources.LONG_BUTTON_PATH, "settings");
        exitButtonView = new ButtonView(140, 456, 440, 70, game.blackFont, GameResources.LONG_BUTTON_PATH, "exit");

    }

    @Override
    public void render(float delta) {

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        game.batch.begin();

        bg.draw(game.batch);
        title.draw(game.batch);
        exitButtonView.draw(game.batch);
        settingsButtonView.draw(game.batch);
        startButtonView.draw(game.batch);

        game.batch.end();
        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            game.touch = game.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (startButtonView. isHit(game.touch)){
                game.setScreen(game.screenGame);
            }
            if (exitButtonView.isHit(game.touch)){
                Gdx.app.exit();
            }
            if (settingsButtonView.isHit(game.touch)){
                game.setScreen(game.screenSettings);
            }
        }
    }
}
