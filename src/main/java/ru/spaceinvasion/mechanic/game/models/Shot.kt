package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.Direction
import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.Mediator
import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.models.Coordinates

/**
 * Created by egor on 17.11.17.
 */
class Shot(mediator: Mediator<*>,
           gamePartId: Int,
           override var coordinates: Coordinates,
           private val direction: Direction?) : GamePart(mediator, gamePartId), Moving {

    override fun notify(message: GameMessage) {

    }
}
