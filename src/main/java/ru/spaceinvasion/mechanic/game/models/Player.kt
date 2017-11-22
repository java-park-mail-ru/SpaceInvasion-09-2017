package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.Race
import ru.spaceinvasion.mechanic.game.messages.*
import ru.spaceinvasion.mechanic.snaps.ServerSnap
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.resources.Constants
import ru.spaceinvasion.resources.Constants.COST_OF_TOWER
import ru.spaceinvasion.resources.Constants.START_COINS
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by egor on 17.11.17.
 */
abstract class Player(
        mediator: GamePartMediator,
        gamePartId: Long,
        val userId: Int,
        ID_GENERATOR: AtomicLong
) : GamePart(mediator, gamePartId, ID_GENERATOR) {

    var coins: Int = START_COINS
    var curUnit: Long? = null

    override fun notify(message: GameMessage) {
        when(message.javaClass) {
            (MoveMessage::class.java) -> {
                if (curUnit == null) {
                    mediator.send(
                            RollbackMessage(
                                    this,
                                    ID_GENERATOR.decrementAndGet(),
                                    message.messageId,
                                    "No existing unit => no move"
                            ),
                            Server::class.java
                    )
                } else {
                    mediator.send(message, Unit::class.java, curUnit!!)
                }
            }
            (CashChangeMessage::class.java) -> {
                coins += (message as CashChangeMessage).getdCash()
                mediator.send(CashChangeMessage(
                        this,
                        message.messageId, message.getdCash()),
                        Server::class.java)
            }
            (RollbackMessage::class.java) -> {
                mediator.send(RollbackMessage(message as RollbackMessage, this), Server::class.java)
            }
            (BuildTowerMessage::class.java) -> {
                if (curUnit == null) {
                    mediator.send(
                            RollbackMessage(
                                    this,
                                    message.messageId,
                                    message.messageId,
                                    "No existing unit => no tower"
                            ),
                            Server::class.java
                    )
                } else {
                    if (coins >= COST_OF_TOWER) {
                        coins -= COST_OF_TOWER
                        mediator.send(BuildTowerMessage(message as BuildTowerMessage, this),
                                Unit::class.java, curUnit!!)
                    } else {
                        mediator.send(
                                RollbackMessage(
                                    this,
                                    message.messageId,
                                    message.messageId,
                                    "No money => no tower"
                            ),
                            Server::class.java
                        )
                    }
                }
            }
            (ShootMessage::class.java) -> {
                if (curUnit == null) {
                    mediator.send(
                            RollbackMessage(
                                    this,
                                    message.messageId,
                                    message.messageId,
                                    "No existing unit => no shots"
                            ),
                            Server::class.java
                    )
                } else {
                    mediator.send(
                            ShootMessage(message as ShootMessage, this),
                            Unit::class.java,
                            curUnit!!
                    )
                }
            }
            (BombInstallingMessage::class.java) -> {
                if (curUnit == null) {
                    mediator.send(
                            RollbackMessage(
                                    this,
                                    message.messageId,
                                    message.messageId,
                                    "No existing unit => no bomb"
                            ),
                            Server::class.java
                    )
                } else {
                    mediator.send(
                            BombInstallingMessage(message as BombInstallingMessage, this),
                            Unit::class.java,
                            curUnit!!
                    )
                }
            }
        }
    }

    protected fun createUnit(coordinates: Coordinates) {
        val unit: Unit = Unit(
                mediator,
                ID_GENERATOR.decrementAndGet(),
                coordinates,
                this,
                ID_GENERATOR
        )
        curUnit = unit.gamePartId
        mediator.registerColleague(Unit::class.java, unit)
    }

    protected fun createBase(coordinates: Coordinates) {
        val base: Base = Base(
                mediator,
                ID_GENERATOR.decrementAndGet(),
                this,
                coordinates,
                ID_GENERATOR
        )
        mediator.registerColleague(Base::class.java, base)
    }
}
