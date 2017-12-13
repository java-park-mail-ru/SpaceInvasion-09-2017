package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.Direction
import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.messages.*
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.resources.Constants
import ru.spaceinvasion.resources.Constants.SPEED_OF_SHOT
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by egor on 17.11.17.
 */
class Shot(mediator: GamePartMediator,
           gamePartId: Long,
           val shotMaker: GamePart,
           override var coordinates: Coordinates,
           private val direction: Direction,
           val damage: Int,
           ID_GENERATOR: AtomicLong,
           val numOfShot : Long?) : GamePart(mediator, gamePartId, ID_GENERATOR), Moving {

    init {
        if (shotMaker::class.java == Unit::class.java)
            mediator.send(ShootMessage(this, gamePartId, direction, coordinates), Server::class.java)
    }

    override val width = Constants.SHOT_WIDTH
    override val height = Constants.SHOT_HEIGHT
    override var speed: Int = SPEED_OF_SHOT

    override fun notify(message: GameMessage) {
        when (message.javaClass) {
            (TickMessage::class.java) -> {
                mediator.send(
                        RequestCollisionsMessage(
                                this,
                                message.requestId,
                                getPotentialCoordinates()
                        ),
                        CollisionEngine::class.java
                )
            }
            (AcceptedMoveMessage::class.java) -> {
                val dx = ((message as AcceptedMoveMessage).coordinates.x - coordinates.x)
                val dy = (message.coordinates.y - coordinates.y)
                move(dx,dy)
            }
            (HitMessage::class.java) -> {
                if (shotMaker.javaClass == Tower::class.java) {
                    val collisionMessage = DamageTowerMessage(this, message.requestId, shotMaker, numOfShot)
                    mediator.send(
                            collisionMessage,
                            (message as HitMessage).target.javaClass,
                            message.target.gamePartId
                    )
                } else if (shotMaker.javaClass == Unit::class.java) {
                    val collisionMessage = DamageShotMessage(this, message.requestId, this)
                    mediator.send(
                            collisionMessage,
                            (message as HitMessage).target.javaClass,
                            message.target.gamePartId
                    )
                } else {
                    throw RuntimeException();
                }
                mediator.removeColleague(Shot::class.java, this)
            }
        }
    }

    fun getPotentialCoordinates() : Coordinates{
        when (direction) {
            (Direction.UP) -> {
                return Coordinates(coordinates.x, coordinates.y - speed)
            }
            (Direction.UP_RIGHT) -> {
                return Coordinates(coordinates.x + speed, coordinates.y - speed)
            }
            (Direction.RIGHT) -> {
                return Coordinates(coordinates.x + speed, coordinates.y)
            }
            (Direction.DOWN_RIGHT) -> {
                return Coordinates(coordinates.x + speed, coordinates.y + speed)
            }
            (Direction.DOWN) -> {
                return Coordinates(coordinates.x, coordinates.y + speed)
            }
            (Direction.DOWN_LEFT) -> {
                return Coordinates(coordinates.x - speed, coordinates.y + speed)
            }
            (Direction.LEFT) -> {
                return Coordinates(coordinates.x - speed, coordinates.y)
            }
            (Direction.UP_LEFT) -> {
                return Coordinates(coordinates.x - speed, coordinates.y - speed)
            }
            else -> return Coordinates(coordinates.x, coordinates.y)
        }
    }
}
