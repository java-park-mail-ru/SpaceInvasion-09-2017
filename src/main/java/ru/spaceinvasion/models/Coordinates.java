package ru.spaceinvasion.models;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egor on 14.11.17.
 */

@Immutable
public class Coordinates {

    private final Integer x;
    private final Integer y;

    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(Coordinates coordinates) {
        this.x = coordinates.x;
        this.y = coordinates.y;
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

    public Integer getY() {
        return y;
    }

}
