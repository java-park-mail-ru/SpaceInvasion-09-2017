package ru.spaceinvasion.models;

import org.jetbrains.annotations.Nullable;

/**
 * Created by egor on 15.11.17.
 */
public class ShotSnap {
    @Nullable
    private Integer id;

    @Nullable
    private Coordinates coordinates;

    @Nullable
    private Integer direction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShotSnap shotSnap = (ShotSnap) o;

        if (id != null ? !id.equals(shotSnap.id) : shotSnap.id != null) return false;
        if (coordinates != null ? !coordinates.equals(shotSnap.coordinates) : shotSnap.coordinates != null) return false;
        return direction != null ? direction.equals(shotSnap.direction) : shotSnap.direction == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (coordinates != null ? coordinates.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    public ShotSnap() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }
}
