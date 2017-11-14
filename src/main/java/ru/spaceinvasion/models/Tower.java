package ru.spaceinvasion.models;

import org.jetbrains.annotations.Nullable;

/**
 * Created by egor on 15.11.17.
 */
public class Tower {
    @Nullable
    private Integer id;

    @Nullable
    private Coordinates coordinates;

    @Nullable
    private Integer direction;

    public Tower() { }

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

        Tower tower = (Tower) o;

        return coordinates != null ? coordinates.equals(tower.coordinates) : tower.coordinates == null;
    }

    @Override
    public int hashCode() {
        return coordinates != null ? coordinates.hashCode() : 0;
    }
}
