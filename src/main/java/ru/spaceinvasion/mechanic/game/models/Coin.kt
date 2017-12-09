package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.Mediator
import ru.spaceinvasion.mechanic.game.messages.DamageMessage
import ru.spaceinvasion.mechanic.game.messages.DisappearingMessage
import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.resources.Constants
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by egor on 17.11.17.
 */
class Coin(mediator: GamePartMediator,
           gamePartId: Long,
           ID_GENERATOR: AtomicLong,
           override var coordinates: Coordinates) : GamePart(mediator, gamePartId, ID_GENERATOR), Placed {
    override val width = Constants.COIN_WIDTH
    override val height = Constants.COIN_HEIGHT
    override fun notify(message: GameMessage) {
        when(message.javaClass) {
            (DisappearingMessage::class.java) -> {
                mediator.send(
                        DamageMessage(this, message.requestId, (message as DisappearingMessage).destroyer),
                        Server::class.java
                )
                mediator.removeColleague(Coin::class.java, this)
            }
        }
    }


}
