package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.messages.*
import ru.spaceinvasion.models.Coordinates
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
    override var isAlive: Boolean = true
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
                if ((owner.javaClass == PlayerPeople::class.java && coordinates.x > X_OF_MIDDLE_MAP) ||
                        (owner.javaClass == PlayerAliens::class.java && coordinates.y > X_OF_MIDDLE_MAP)) {
                    mediator.send(RollbackMessage(
                            this,
                            message.messageId,
                            message.messageId,"Not your ground => No tower"),
                            Player::class.java,
                            owner.curUnit!!
                    )
                } else {
                    mediator.registerColleague(
                            Tower::class.java,
                            Tower(
                                    mediator,
                                    message.messageId,
                                    coordinates,
                                    (message as BuildTowerMessage).direction,
                                    ID_GENERATOR
                            )
                    )
                    mediator.send(BuildTowerMessage(message, this), Server::class.java)
                }
            }
            (ShootMessage::class.java) -> {
                mediator.registerColleague(
                        Shot::class.java,
                        Shot(
                                mediator,
                                message.messageId,
                                coordinates,
                                (message as ShootMessage).direction,
                                damage_power,
                                ID_GENERATOR
                        )
                )
                mediator.send(ShootMessage(message, this), Server::class.java)
            }
        }
    }
}
