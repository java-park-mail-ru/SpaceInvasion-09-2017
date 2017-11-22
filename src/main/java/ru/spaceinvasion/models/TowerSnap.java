package ru.spaceinvasion.models;

import org.jetbrains.annotations.Nullable;

/**
 * Created by egor on 15.11.17.
 */
public class TowerSnap {
    @Nullable
    private Integer id;

    @Nullable
    private Coordinates coordinates;

    @Nullable
    private Integer direction;

    public TowerSnap() { }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TowerSnap towerSnap = (TowerSnap) o;

        return coordinates != null ? coordinates.equals(towerSnap.coordinates) : towerSnap.coordinates == null;
    }

    @Override
    public int hashCode() {
        return coordinates != null ? coordinates.hashCode() : 0;
    }
}
