package io.github.jura120596.scgame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    public Music backgroundMusic;
    public Sound shootSound;
    public Sound explosionSound;

    public boolean isSoundOn = true;

    public boolean isMusicOn = true;

    public AudioManager() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.BACKGROUND_MUSIC_PATH));
        shootSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.SHOOT_MUSIC_PATH));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.DESTROY_MUSIC_PATH));
        backgroundMusic.setVolume(0.2f);
        backgroundMusic.setLooping(true);

        isSoundOn = MemoryManager.loadIsSoundOn();
        isMusicOn = MemoryManager.loadIsMusicOn();
        updateMusicFlag();
    }
    public void updateMusicFlag() {
        if (isMusicOn) backgroundMusic.play();
        else backgroundMusic.stop();
    }
    public void setSoundOn(boolean soundOn) {
        isSoundOn = soundOn;
        MemoryManager.saveSoundSettings(soundOn);
    }

    public void setMusicOn(boolean musicOn) {
        isMusicOn = musicOn;
        MemoryManager.saveSoundSettings(musicOn);
        updateMusicFlag();
    }
}
