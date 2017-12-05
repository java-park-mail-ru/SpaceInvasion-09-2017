package ru.spaceinvasion.resources;

import ru.spaceinvasion.models.Coordinates;

public class Constants {
    public final static Integer HEALTH_OF_BASE = 1000;
    public final static Integer HEALTH_OF_TOWER = 200;
    public final static Integer HEALTH_OF_UNIT = 100;
    public final static Integer SPEED_OF_UNIT = 10;
    public final static Integer SPEED_OF_SHOT = 12;
    public final static Integer DAMAGE_POWER_OF_UNIT = 10;
    public final static Integer DAMAGE_POWER_OF_TOWER = 10;
    public final static Integer START_COINS = 100;
    public final static Integer UNIT_WIDTH = 10;
    public final static Integer UNIT_HEIGHT = 20;
    public final static Integer COIN_WIDTH = 10;
    public final static Integer COIN_HEIGHT = 10;
    public final static Integer BASE_WIDTH = 20;
    public final static Integer BASE_HEIGHT = 20;
    public final static Integer TOWER_WIDTH = 10;
    public final static Integer TOWER_HEIGHT = 20;
    public final static Integer SHOT_WIDTH = 10;
    public final static Integer SHOT_HEIGHT = 10;
    public final static Integer COST_OF_TOWER = 10;
    public final static Integer Y_OF_UPPER_MAP_BORDER = 0;
    public final static Integer Y_OF_LOWER_MAP_BORDER = 500;
    public final static Integer X_OF_LEFT_MAP_BORDER = 0;
    public final static Integer X_OF_RIGHT_MAP_BORDER = 1000;
    public final static Integer X_OF_MIDDLE_MAP = (X_OF_RIGHT_MAP_BORDER + X_OF_LEFT_MAP_BORDER) / 2;
    public final static Integer COST_OF_ONE_COIN = 10;
    public final static Coordinates COORDINATES_OF_UNIT_PEOPLE_START = new Coordinates(150, 230);
    public final static Coordinates COORDINATES_OF_BASE_PEOPLE_START = new Coordinates(0, 230);
    public final static Coordinates COORDINATES_OF_UNIT_ALIENS_START = new Coordinates(600, 230);
    public final static Coordinates COORDINATES_OF_BASE_ALIENS_START = new Coordinates(630, 230);
    public final static Integer SERVER_FRAME_MILLIS = 25;
    public final static Integer TOWER_SHOOTS_PER_SECOND = 1;
    public final static Integer TICKS_UNTIL_TOWER_SHOOT = SERVER_FRAME_MILLIS / TOWER_SHOOTS_PER_SECOND;
    public final static Integer SECONDS_TO_BOMB_ACTION = 5;
    public final static Integer TICKS_UNITL_BOMB_ACTION = SERVER_FRAME_MILLIS * SECONDS_TO_BOMB_ACTION;
    public final static Integer SECONDS_TO_REBORN_UNIT = 3;
    public final static Integer TICKS_TO_REBORN_UNIT = SECONDS_TO_REBORN_UNIT * SERVER_FRAME_MILLIS;

}
