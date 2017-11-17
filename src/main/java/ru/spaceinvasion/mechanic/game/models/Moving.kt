package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.Mediator
import ru.spaceinvasion.models.Coordinates

/**
 * Created by egor on 17.11.17.
 */
interface Moving {

    var coordinates: Coordinates;


    fun move(dx: Int, dy: Int) {
        coordinates.x += dx;
        coordinates.y += dy;
    }

    fun setCoordinates(x: Int, y: Int) {
        coordinates.x = x;
        coordinates.y = y;
    }
}
