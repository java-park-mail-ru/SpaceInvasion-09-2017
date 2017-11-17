package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.models.Coordinates

import ru.spaceinvasion.resources.Constants.HEALTH_OF_UNIT

/**
 * Created by egor on 17.11.17.
 */
class Unit(mediator: GamePartMediator, gamePartId: Int, override var coordinates: Coordinates) : GamePart(mediator, gamePartId), Moving, Damaging {
    override var health: Int = HEALTH_OF_UNIT;

    override fun notify(message: GameMessage) {

    }
}
