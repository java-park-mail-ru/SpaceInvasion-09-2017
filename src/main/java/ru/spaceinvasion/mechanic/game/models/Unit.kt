package ru.spaceinvasion.mechanic.game.models

import com.sun.org.apache.xpath.internal.operations.Bool
import ru.spaceinvasion.mechanic.game.Direction
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
    override val width = UNIT_WIDTH
    override val height = UNIT_HEIGHT

    override fun notify(message: GameMessage) {
        when (message) {
            (MoveMessage::class.java) -> {
                mediator.send(
                        RequestCollisionsMessage(
                                this,
                                message.messageId,
                                Coordinates(
                                        (message as MoveMessage).coordinates.x + coordinates.x,
                                        message.coordinates.y + coordinates.y
                                )
                        ),
                        CollisionEngine::class.java
                )
            }
            (AcceptedMoveMessage::class.java) -> {
                val dx = ((message as AcceptedMoveMessage).coordinates.x - coordinates.x)
                val dy = ((message as AcceptedMoveMessage).coordinates.y - coordinates.y)
                move(dx,dy)
                mediator.send(MoveMessage(this, message.messageId, Coordinates(dx,dy)), Server::class.java)
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
                                gamePartId,
                                getCoordinatesOfShot((message as ShootMessage).direction),
                                message.direction,
                                damage_power,
                                ID_GENERATOR
                        )
                )
            }
            (DamageMessage::class.java) -> {
                damage(((message as DamageMessage).srcOfDamageId as Shot).damage)
                if (!isAlive) {
                    mediator.send(UnitStatusMessage(this, message.messageId, false), Player::class.java, owner.gamePartId)
                    mediator.removeColleague(Unit::class.java, this)
                }
                mediator.send(DamageMessage(message, this), Server::class.java)
            }
        }
    }

    private fun getCoordinatesOfShot(directionOfLastMove: Direction) : Coordinates{
        when (directionOfLastMove) {
            (Direction.UP) -> {
                return Coordinates(coordinates.x, coordinates.y - SHOT_HEIGHT - 1)
            }
            (Direction.UP_RIGHT) -> {
                return Coordinates(coordinates.x + SHOT_WIDTH + 1, coordinates.y - SHOT_HEIGHT - 1)
            }
            (Direction.RIGHT) -> {
                return Coordinates(coordinates.x + SHOT_WIDTH + 1,coordinates.y)
            }
            (Direction.DOWN_RIGHT) -> {
                return Coordinates(coordinates.x + SHOT_WIDTH + 1,coordinates.y + SHOT_HEIGHT + 1)
            }
            (Direction.DOWN) -> {
                return Coordinates(coordinates.x,coordinates.y + SHOT_HEIGHT + 1)
            }
            (Direction.DOWN_LEFT) -> {
                return Coordinates(coordinates.x - SHOT_WIDTH - 1,coordinates.y + SHOT_HEIGHT + 1)
            }
            (Direction.LEFT) -> {
                return Coordinates(coordinates.x - SHOT_WIDTH - 1,coordinates.y)
            }
            (Direction.UP_LEFT) -> {
                return Coordinates(coordinates.x - SHOT_WIDTH - 1,coordinates.y - SHOT_HEIGHT - 1)
            }
            else -> return coordinates
        }
    }

}