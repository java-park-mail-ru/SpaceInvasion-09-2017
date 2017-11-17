package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.Mediator
import ru.spaceinvasion.mechanic.game.messages.GameMessage

/**
 * Created by egor on 17.11.17.
 */
class Bomb(mediator: Mediator<*>, gamePartId: Int) : GamePart(mediator, gamePartId) {

    override fun notify(message: GameMessage) {

    }
}
