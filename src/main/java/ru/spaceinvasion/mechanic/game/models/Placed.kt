package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.models.Coordinates

/**
 * Created by egor on 21.11.17.
 */
interface Placed {
    var coordinates: Coordinates;

    fun setCoordinates(x: Int, y: Int) {
        coordinates.x = x;
        coordinates.y = y;
    }
}