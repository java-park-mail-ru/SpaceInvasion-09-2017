package ru.spaceinvasion.models;

/**
 * Created by egor on 01.09.17.
 */
public class Weapon {
    private int damage;
    private int range;
    private int speedOfShot;
    private int accelerateOfShot;

    public Weapon(String nameOfWeapon) {
        //TODO: Найти характеристики по названию оружия
    }

    public Shot fire(Coordinates coordinates, double dirrection) {
         return new Shot(coordinates, range, dirrection, speedOfShot, accelerateOfShot);
    }
}
