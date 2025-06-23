package io.github.jura120596.scgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import io.github.jura120596.scgame.helpers.AudioManager;
import io.github.jura120596.scgame.helpers.GameResources;
import io.github.jura120596.scgame.helpers.MemoryManager;
import io.github.jura120596.scgame.views.ButtonView;
import io.github.jura120596.scgame.views.ImageView;
import io.github.jura120596.scgame.views.MovingBackgroundView;
import io.github.jura120596.scgame.views.TextView;

public class SettingsScreen extends ScreenAdapter {
    Main game;
    MovingBackgroundView backgroundView;
    TextView titleTextView;
    ImageView blackoutImageView;
    ButtonView returnButton;
    TextView musicSettingView;
    TextView soundSettingView;
    TextView clearSettingView;
    private AudioManager audio;

    public SettingsScreen(Main game) {
        this.game = game;
        audio = game.audioManager;
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        titleTextView = new TextView(game.largeWhiteFont, 256, 956, "Settings");
        blackoutImageView = new ImageView(85, 365, GameResources.BLACKOUT_MIDDLE_IMG_PATH);
        musicSettingView = new TextView(game.whiteFont, 173, 717, "music: " + valToOnOff(audio.isMusicOn));
        soundSettingView = new TextView(game.whiteFont, 173, 658, "sound: " + valToOnOff(audio.isSoundOn));
        clearSettingView = new TextView(game.whiteFont, 173, 599, "clear records");
        returnButton = new ButtonView(
            280, 447,
            160, 70,
            game.blackFont,
            GameResources.SHORT_BUTTON_PATH,
            "return"
        );
    }
    @Override
    public void render(float delta) {
        handleInput();
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        game.batch.begin();

        backgroundView.draw(game.batch);
        titleTextView.draw(game.batch);
        blackoutImageView.draw(game.batch);
        returnButton.draw(game.batch);
        musicSettingView.draw(game.batch);
        soundSettingView.draw(game.batch);
        clearSettingView.draw(game.batch);

        game.batch.end();
    }
    private String valToOnOff(boolean state) {
        return state ? "ON" : "OFF";
    }

    public void  handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touch = game.touch = game.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (returnButton.isHit(touch)) {
                game.setScreen(game.screenMenu);
            }
            if (clearSettingView.isHit(touch)) {
                MemoryManager.setTableOfRecords(new ArrayList<>());
                clearSettingView.setText("clear records (cleared)");

            }
            if (musicSettingView.isHit(touch)) {
                audio.setMusicOn(!audio.isMusicOn);
                musicSettingView.setText("music: " + valToOnOff(audio.isMusicOn));
            }
            if (soundSettingView.isHit(touch)) {
                audio.setSoundOn(!audio.isSoundOn);
                soundSettingView.setText("sound: " + valToOnOff(audio.isSoundOn));
            }
        }
    }
}

