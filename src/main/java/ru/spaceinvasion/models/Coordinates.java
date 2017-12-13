package ru.spaceinvasion.models;

import org.jetbrains.annotations.Nullable;

/**
 * Created by egor on 14.11.17.
 */
public class Coordinates {

    private Integer x;
    private Integer y;

    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Coordinates that = (Coordinates) o;

        return (x != null ? x.equals(that.x) : that.x == null) && (y != null ? y.equals(that.y) : that.y == null);
    }

    @Override
    public int hashCode() {
        int result = x != null ? x.hashCode() : 0;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
