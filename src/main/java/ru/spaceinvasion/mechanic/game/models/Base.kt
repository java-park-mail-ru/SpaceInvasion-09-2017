package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.Mediator
import ru.spaceinvasion.mechanic.game.messages.DamageMessage
import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.models.Coordinates

import ru.spaceinvasion.resources.Constants.HEALTH_OF_BASE
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by egor on 17.11.17.
 */
class Base(mediator: GamePartMediator,
           gamePartId: Long,
           val owner: Player,
           override var coordinates: Coordinates,
           ID_GENERATOR: AtomicLong): GamePart(mediator, gamePartId, ID_GENERATOR), Damaging, Placed {
    override var isAlive: Boolean = true
    override var health: Int = HEALTH_OF_BASE
    override fun notify(message: GameMessage) {
        when(message.javaClass) {
            (DamageMessage::class.java) -> {
                damage(1)
                if(isAlive) {
                    //TODO
                }
            }
        }
    }
}
