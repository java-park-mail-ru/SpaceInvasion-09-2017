package ru.spaceinvasion.models;

import org.jetbrains.annotations.Nullable;

/**
 * Created by egor on 15.11.17.
 */
public class Shot {
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

        Shot shot = (Shot) o;

        if (id != null ? !id.equals(shot.id) : shot.id != null) return false;
        if (coordinates != null ? !coordinates.equals(shot.coordinates) : shot.coordinates != null) return false;
        return direction != null ? direction.equals(shot.direction) : shot.direction == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (coordinates != null ? coordinates.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    public Shot() { }

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
