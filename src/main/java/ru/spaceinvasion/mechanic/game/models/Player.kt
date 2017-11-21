package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.Race
import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.mechanic.game.messages.NewUnitGameMessage
import ru.spaceinvasion.resources.Constants.START_COINS
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by egor on 17.11.17.
 */
class Player(mediator: GamePartMediator,
             gamePartId: Long,
             private val userId: Int,
             ID_GENERATOR: AtomicLong,
             var race: Race) : GamePart(mediator, gamePartId) {

    var coins: Int = START_COINS

    init {
        mediator.send(NewUnitGameMessage(this, ID_GENERATOR.decrementAndGet()), Server::class.java)
    }

    override fun notify(message: GameMessage) {

    }
}
