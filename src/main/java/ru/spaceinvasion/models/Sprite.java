package ru.spaceinvasion.models;

/**
 * Created by egor on 01.09.17.
 */
public abstract class Sprite {
    private boolean exists;
    private Coordinates coordinates;

    public abstract void update();

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    protected boolean isExists() {
        return exists;
    }

    protected void remove() {
        exists = false;
    }

    protected Sprite() {
        exists = true;
    }
}
