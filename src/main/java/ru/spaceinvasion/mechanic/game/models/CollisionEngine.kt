package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.messages.GameMessage

/**
 * Created by egor on 19.11.17.
 */
class CollisionEngine(mediator: GamePartMediator,
                      gamePartId: Long) : GamePart(mediator, gamePartId) {

    override fun notify(message: GameMessage) {

    }

}