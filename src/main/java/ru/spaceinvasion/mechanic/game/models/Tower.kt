package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.Mediator
import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.mechanic.game.models.Damaging
import ru.spaceinvasion.models.Coordinates

import ru.spaceinvasion.resources.Constants.HEALTH_OF_TOWER

/**
 * Created by egor on 17.11.17.
 */
class Tower(mediator: Mediator<*>, gamePartId: Int,
            var coordinates: Coordinates) : GamePart(mediator, gamePartId), Damaging {
    override var health: Int = HEALTH_OF_TOWER;

    override fun notify(message: GameMessage) {

    }
}
