package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.Mediator
import ru.spaceinvasion.mechanic.game.messages.GameMessage

import ru.spaceinvasion.resources.Constants.HEALTH_OF_BASE

/**
 * Created by egor on 17.11.17.
 */
class Base(mediator: GamePartMediator,
           gamePartId: Long) : GamePart(mediator, gamePartId), Damaging {
    override var health: Int = HEALTH_OF_BASE
    override fun notify(message: GameMessage) {

    }
}
