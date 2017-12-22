package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.models.Coordinates

/**
 * Created by egor on 21.11.17.
 */
interface Placed {
    var coordinates: Coordinates;
    val width: Int
    val height: Int


    fun setCoordinates(x: Int, y: Int) {
        this.coordinates = Coordinates(x,y)
    }
}