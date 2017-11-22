package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.Direction
import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.resources.Constants.DAMAGE_POWER_OF_TOWER

import ru.spaceinvasion.resources.Constants.HEALTH_OF_TOWER
import java.util.concurrent.atomic.AtomicLong

class Tower(mediator: GamePartMediator, gamePartId: Long,
            override var coordinates: Coordinates,
            val directionOfShooting: Direction,

            ID_GENERATOR: AtomicLong) : GamePart(mediator, gamePartId, ID_GENERATOR), Damaging, Placed {
    override var health: Int = HEALTH_OF_TOWER;
    override var isAlive: Boolean = true
    val damage_power: Int = DAMAGE_POWER_OF_TOWER;

    override fun notify(message: GameMessage) {

    }
}
