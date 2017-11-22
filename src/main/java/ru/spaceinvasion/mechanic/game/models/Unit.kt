package ru.spaceinvasion.mechanic.game.models

import ch.qos.logback.core.util.ContextUtil
import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.Race
import ru.spaceinvasion.mechanic.game.messages.*
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.models.Tower
import ru.spaceinvasion.resources.Constants.*
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by egor on 17.11.17.
 */
class Unit(mediator: GamePartMediator,
           gamePartId: Long,
           override var coordinates: Coordinates,
           val owner: Player,
           ID_GENERATOR: AtomicLong) : GamePart(mediator, gamePartId, ID_GENERATOR), Moving, Damaging {
    override var health: Int = HEALTH_OF_UNIT
    override var speed: Int = SPEED_OF_UNIT
    val damage_power: Int = DAMAGE_POWER_OF_UNIT

    override fun notify(message: GameMessage) {
        when (message) {
            (MoveMessage::class.java) -> {
                mediator.send(
                        RequestCollisionsMessage(
                                this,
                                message.messageId,
                                (message as MoveMessage).coordinates
                        ),
                        CollisionEngine::class.java
                )
            }
            (AcceptedMoveMessage::class.java) -> {
                locate((message as AcceptedMoveMessage).coordinates)
                mediator.send(MoveMessage(this, message.messageId, message.coordinates), Server::class.java)
            }
            (CashChangeMessage::class.java) -> {
                mediator.send(message, Player::class.java, owner.gamePartId)
            }
            (BuildTowerMessage::class.java) -> {
            
            }
        }
    }
}
