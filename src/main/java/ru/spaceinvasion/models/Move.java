package ru.spaceinvasion.models;

import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.Null;

/**
 * Created by egor on 15.11.17.
 */
public class Move {
    @Nullable
    private Coordinates coordinates;

    public Move(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Move() {

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

        Move move = (Move) o;

        return coordinates != null ? coordinates.equals(move.coordinates) : move.coordinates == null;
    }

    @Override
    public int hashCode() {
        return coordinates != null ? coordinates.hashCode() : 0;
    }
}
