package ru.spaceinvasion.models;

/**
 * Created by egor on 01.09.17.
 */
public abstract class Unit extends Sprite {
    private int team;
    private Weapon weapon;
    private int health;
    private int speedOfRegeneration;

    private static final int HEALTH = 1000;

    public Unit() {
        setCoordinates(new Coordinates());
        team = 1;
        weapon = new Weapon("assault_rifle");
        health = HEALTH;
        speedOfRegeneration = 0;
    }

    public Shot fire(double dirrection) {
        return weapon.fire(getCoordinates(), dirrection);
    }


}
