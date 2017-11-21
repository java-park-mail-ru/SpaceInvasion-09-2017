package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.Direction
import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.resources.Constants.SPEED_OF_SHOT

/**
 * Created by egor on 17.11.17.
 */
class Shot(mediator: GamePartMediator,
           gamePartId: Long,
           override var coordinates: Coordinates,
           private val direction: Direction,
           private val damage: Int) : GamePart(mediator, gamePartId), Moving {

    override var speed: Int = SPEED_OF_SHOT

    override fun notify(message: GameMessage) {

    }
}
