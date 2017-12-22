package ru.spaceinvasion.resources;

import ru.spaceinvasion.models.Coordinates;

public class Constants {
    public static final Integer SERVER_FRAME_MILLIS = 25;
    public static final Integer HEALTH_OF_BASE = 3;
    public static final Integer HEALTH_OF_TOWER = 100;
    public static final Integer HEALTH_OF_UNIT = 100;
    public static final Integer SPEED_OF_UNIT = 3;
    public static final Double SPEED_OF_SHOT_PER_SECOND = 0.72;
    public static final Integer SPEED_OF_SHOT_PER_TICK = ((Double)(SPEED_OF_SHOT_PER_SECOND * SERVER_FRAME_MILLIS)).intValue();
    public static final Integer DAMAGE_POWER_OF_UNIT = 10;
    public static final Integer DAMAGE_POWER_OF_TOWER = 10;
    public static final Integer START_COINS = 10;
    public static final Integer UNIT_WIDTH = 50;
    public static final Integer UNIT_HEIGHT = 50;
    public static final Integer COIN_WIDTH = 30;
    public static final Integer COIN_HEIGHT = 30;
    public static final Integer BASE_WIDTH = 110;
    public static final Integer BASE_HEIGHT = 110;
    public static final Integer TOWER_WIDTH = 50;
    public static final Integer TOWER_HEIGHT = 100;
    public static final Integer SHOT_WIDTH = 25;
    public static final Integer SHOT_HEIGHT = 25;
    public static final Integer COST_OF_TOWER = 5;
    public static final Integer Y_OF_UPPER_MAP_BORDER = 0;
    public static final Integer Y_OF_LOWER_MAP_BORDER = 640;
    public static final Integer X_OF_LEFT_MAP_BORDER = 0;
    public static final Integer X_OF_RIGHT_MAP_BORDER = 960;
    public static final Integer X_OF_MIDDLE_MAP = Math.abs(X_OF_RIGHT_MAP_BORDER - X_OF_LEFT_MAP_BORDER) / 2 + X_OF_LEFT_MAP_BORDER;
    public static final Integer COST_OF_ONE_COIN = 10;
    public static final Coordinates COORDINATES_OF_UNIT_PEOPLE_START = new Coordinates(55, 320);
    public static final Coordinates COORDINATES_OF_BASE_PEOPLE_START = new Coordinates(5, 320);
    public static final Coordinates COORDINATES_OF_UNIT_ALIENS_START = new Coordinates(905, 320);
    public static final Coordinates COORDINATES_OF_BASE_ALIENS_START = new Coordinates(955, 320);
    public static final Integer TOWER_SHOOTS_PER_SECOND = 2;
    public static final Integer TICKS_UNTIL_TOWER_SHOOT = SERVER_FRAME_MILLIS / TOWER_SHOOTS_PER_SECOND;
    public static final Integer SECONDS_TO_BOMB_ACTION = 3;
    public static final Integer TICKS_UNITL_BOMB_ACTION = SERVER_FRAME_MILLIS * SECONDS_TO_BOMB_ACTION;
    public static final Integer SECONDS_TO_REBORN_UNIT = 3;
    public static final Integer TICKS_TO_REBORN_UNIT = SECONDS_TO_REBORN_UNIT * SERVER_FRAME_MILLIS;
    public static final Integer MILLIS_TO_CREATING_COIN = 5000;
    public static final Integer SECONDS_OF_SHOT_LIVE = 3;
    public static final Integer TICKS_OF_SHOT_LIVE = SECONDS_OF_SHOT_LIVE * SERVER_FRAME_MILLIS;
    public static final Integer VERTICAL_OFFSET_OF_COINS = (COIN_HEIGHT / 2) + 10;
    public static final Integer HORIZONTAL_OFFSET_OF_COINS = BASE_WIDTH + (COIN_WIDTH / 2) + 10;

}
