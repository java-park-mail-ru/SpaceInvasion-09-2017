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
           val shotMakerId: Long,
           override var coordinates: Coordinates,
           private val direction: Direction,
           val damage: Int,
           ID_GENERATOR: AtomicLong) : GamePart(mediator, gamePartId, ID_GENERATOR), Moving {

    init {
        mediator.send(ShootMessage(this, ID_GENERATOR.decrementAndGet(), direction), Server::class.java)
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
                                message.messageId,
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
