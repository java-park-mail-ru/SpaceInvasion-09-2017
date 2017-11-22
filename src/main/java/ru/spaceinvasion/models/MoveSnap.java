package ru.spaceinvasion.models;

import org.jetbrains.annotations.Nullable;

/**
 * Created by egor on 15.11.17.
 */
public class MoveSnap {
    @Nullable
    private Coordinates coordinates;

    public MoveSnap(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public MoveSnap() {

    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoveSnap moveSnap = (MoveSnap) o;

        return coordinates != null ? coordinates.equals(moveSnap.coordinates) : moveSnap.coordinates == null;
    }

    @Override
    public int hashCode() {
        return coordinates != null ? coordinates.hashCode() : 0;
    }
}
