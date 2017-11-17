package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.Mediator
import ru.spaceinvasion.mechanic.game.Race
import ru.spaceinvasion.mechanic.game.messages.GameMessage

/**
 * Created by egor on 17.11.17.
 */
class Player(mediator: Mediator<*>,
             gamePartId: Int,
             private val userId: Int,
             private val race: Race) : GamePart(mediator, gamePartId) {

    override fun notify(message: GameMessage) {

    }
}
