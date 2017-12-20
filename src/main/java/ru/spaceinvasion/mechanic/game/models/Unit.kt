package ru.spaceinvasion.mechanic.game.models

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

    init {
        mediator.send(UnitCreationMessage(this, 0) ,Server::class.java)
    }

    override fun notify(message: GameMessage) {
        when (message.javaClass) {
            (MoveMessage::class.java) -> {
                mediator.send(
                        RequestCollisionsMessage(
                                this,
                                message.requestId,
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
                val dy = (message.coordinates.y - coordinates.y)
                move(dx,dy)
                mediator.send(MoveMessage(this, message.requestId, message.coordinates), Server::class.java)
            }
            (CashChangeMessage::class.java) -> {
                mediator.send(message, Player::class.java, owner.gamePartId)
            }
            (BuildTowerMessage::class.java) -> {
                if ((owner.javaClass == PlayerPeople::class.java && coordinates.x > X_OF_MIDDLE_MAP) ||
                        (owner.javaClass == PlayerAliens::class.java && coordinates.x < X_OF_MIDDLE_MAP)) {
                    mediator.send(RollbackMessage(
                            this,
                            message.requestId,
                            "Not your ground => No tower"),
                            Player::class.java,
                            owner.gamePartId
                    )
                } else {
                    mediator.registerColleague(
                            Tower::class.java,
                            Tower(
                                    mediator,
                                    message.requestId,
                                    coordinates,
                                    (message as BuildTowerMessage).direction,
                                    ID_GENERATOR
                            )

                    )
                    mediator.send(BuildTowerMessage(message,this,coordinates), Server::class.java)
                }
            }
            (ShootMessage::class.java) -> {
                mediator.registerColleague(
                        Shot::class.java,
                        Shot(
                                mediator,
                                message.requestId,
                                this,
                                getCoordinatesOfShot((message as ShootMessage).direction),
                                message.direction,
                                damage_power,
                                ID_GENERATOR,
                                null
                        )
                )
            }
            (DamageTowerMessage::class.java) -> {
                damage(((message as DamageTowerMessage).srcOfDamage as Tower).damage_power)
                if (!isAlive) {
                    mediator.registerColleague(Coin::class.java, Coin(mediator,message.requestId,ID_GENERATOR,coordinates))
                    mediator.removeColleague(Unit::class.java, this)
                    mediator.send(UnitStatusMessage(this,message.requestId,false),Player::class.java,owner.gamePartId)
                }
                mediator.send(DamageTowerMessage(message, this), Server::class.java)
            }
            (DamageShotMessage::class.java) -> {
                damage(((message as DamageShotMessage).srcOfDamage as Shot).damage)
                if (!isAlive) {
                    mediator.registerColleague(Coin::class.java, Coin(mediator,message.requestId,ID_GENERATOR,coordinates))
                    mediator.removeColleague(Unit::class.java, this)
                    mediator.send(UnitStatusMessage(this,message.requestId,false),Player::class.java,owner.gamePartId)
                }
                mediator.send(DamageShotMessage(message, this), Server::class.java)
            }
        }
    }

    private fun getCoordinatesOfShot(directionOfLastMove: Direction) : Coordinates{
        val dy = ((SHOT_HEIGHT + height) / 2 + 1)
        val dx = ((SHOT_WIDTH + width) / 2 + 1)
        when (directionOfLastMove) {
            (Direction.UP) -> {
                return Coordinates(coordinates.x, coordinates.y - dy)
            }
            (Direction.UP_RIGHT) -> {
                return Coordinates(coordinates.x + dx, coordinates.y - dy)
            }
            (Direction.RIGHT) -> {
                return Coordinates(coordinates.x + dx, coordinates.y)
            }
            (Direction.DOWN_RIGHT) -> {
                return Coordinates(coordinates.x + dx, coordinates.y + dy)
            }
            (Direction.DOWN) -> {
                return Coordinates(coordinates.x, coordinates.y + dy)
            }
            (Direction.DOWN_LEFT) -> {
                return Coordinates(coordinates.x - dx, coordinates.y + dy)
            }
            (Direction.LEFT) -> {
                return Coordinates(coordinates.x - dx, coordinates.y)
            }
            (Direction.UP_LEFT) -> {
                return Coordinates(coordinates.x - dx, coordinates.y - dy)
            }
            else -> throw RuntimeException()
        }
    }

}
