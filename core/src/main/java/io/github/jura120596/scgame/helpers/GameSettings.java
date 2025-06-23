package io.github.jura120596.scgame.helpers;

public class GameSettings {

    // Device settings

    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 1280;
    public static final float STEP_TIME = 1f / 60;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = 0.05f;

    public static final int SHIP_WIDTH = 150;
    public static final int SHIP_HEIGHT = 150;
    public static final float SHIP_FORCE_RATIO = 10;

    public static final float TRASH_VELOCITY = 10;
    public static final long STARTING_TRASH_APPEARANCE_COOL_DOWN = 2000;
    public static final int TRASH_WIDTH = 140;
    public static final int TRASH_HEIGHT = 100;

    public static final int BULLET_WIDTH = 15;
    public static final int BULLET_HEIGHT = 45;
    public static final int BULLET_VELOCITY = 200;
    public static final int SHOOTING_COOL_DOWN  = 200;


    public static final short TRASH_BIT = 1;
    public static final short SHIP_BIT = 2;
    public static final short BULLET_BIT = 4;
    public static final int LIVE_PADDING = 20;
}
