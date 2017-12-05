package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.Direction
import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.messages.*
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.resources.Constants.*

import java.util.concurrent.atomic.AtomicLong

class Tower(mediator: GamePartMediator, gamePartId: Long,
            override var coordinates: Coordinates,
            val directionOfShooting: Direction,

            ID_GENERATOR: AtomicLong) : GamePart(mediator, gamePartId, ID_GENERATOR), Damaging, Placed {
    override var health: Int = HEALTH_OF_TOWER
    override var isAlive: Boolean = true
    val damage_power: Int = DAMAGE_POWER_OF_TOWER
    private var tts: Int = TICKS_UNTIL_TOWER_SHOOT

    override val width = TOWER_WIDTH
    override val height = TOWER_HEIGHT

    override fun notify(message: GameMessage) {
        when (message.javaClass) {
            (TickMessage::class.java) -> {
                tts--
                if (tts == 0) {
                    tts = TICKS_UNTIL_TOWER_SHOOT

                    mediator.registerColleague(
                            Shot::class.java,
                            Shot(
                                    mediator,
                                    ID_GENERATOR.decrementAndGet(),
                                    gamePartId,
                                    getCoordinatesOfShot(),
                                    directionOfShooting,
                                    damage_power,
                                    ID_GENERATOR)
                    )
                }
            }
            (DamageMessage::class.java) -> {
                damage(((message as DamageMessage).srcOfDamageId as Shot).damage)
                if (!isAlive) {
                    mediator.registerColleague(Coin::class.java, Coin(mediator,message.requestId,ID_GENERATOR,coordinates))
                    mediator.removeColleague(Tower::class.java, this)
                }
                mediator.send(DamageMessage(message, this), Server::class.java)
            }
        }
    }
    private fun getCoordinatesOfShot() : Coordinates{
        when (directionOfShooting) {
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
