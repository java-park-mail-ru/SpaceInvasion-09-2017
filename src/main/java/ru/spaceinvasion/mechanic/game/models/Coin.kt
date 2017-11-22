package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.Mediator
import ru.spaceinvasion.mechanic.game.messages.DisappearingMessage
import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.models.Coordinates
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by egor on 17.11.17.
 */
class Coin(mediator: GamePartMediator,
           gamePartId: Long,
           playerWhoInstalled: Int,
           ID_GENERATOR: AtomicLong,
           override var coordinates: Coordinates) : GamePart(mediator, gamePartId, ID_GENERATOR), Placed {
    override fun notify(message: GameMessage) {
        when(message.javaClass) {
            (DisappearingMessage::class.java) -> {
                mediator.send(DisappearingMessage(this, message.messageId),Server::class.java)
                mediator.removeColleague(Coin::class.java, this)
            }
        }
    }


}
