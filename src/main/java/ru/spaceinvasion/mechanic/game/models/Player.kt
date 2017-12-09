package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.Race
import ru.spaceinvasion.mechanic.game.messages.*
import ru.spaceinvasion.mechanic.snaps.ServerSnap
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.resources.Constants
import ru.spaceinvasion.resources.Constants.*
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by egor on 17.11.17.
 */
abstract class Player(
        mediator: GamePartMediator,
        gamePartId: Long,
        ID_GENERATOR: AtomicLong
) : GamePart(mediator, gamePartId, ID_GENERATOR) {


    var coins: Int = START_COINS
    var curUnit: Long? = null
    var base: Base? = null
    var ttc: Int? = null

    init {
        mediator.registerColleague(Player::class.java, this)
    }

    override fun notify(message: GameMessage) {
        when(message.javaClass) {
            (MoveMessage::class.java) -> {
                if (curUnit == null) {
                    mediator.send(
                            RollbackMessage(
                                    this,
                                    ID_GENERATOR.decrementAndGet(),
                                    message.requestId,
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
            }
            (RollbackMessage::class.java) -> {
                mediator.send(RollbackMessage(message as RollbackMessage, this), Server::class.java)
            }
            (BuildTowerMessage::class.java) -> {
                if (curUnit == null) {
                    mediator.send(
                            RollbackMessage(
                                    this,
                                    message.requestId,
                                    message.requestId,
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
                                    message.requestId,
                                    message.requestId,
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
                                    message.requestId,
                                    message.requestId,
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
            (LosingMessage::class.java) -> {
                mediator.send(LosingMessage(message as LosingMessage, this), Server::class.java)
            }
            (WiningMessage::class.java) -> {
                mediator.send(WiningMessage(message as WiningMessage, this), Server::class.java)
            }
            (UnitStatusMessage::class.java) -> {
                if (!(message as UnitStatusMessage).isAlive) {
                    curUnit = null
                    ttc = TICKS_TO_REBORN_UNIT
                }
            }
            (TickMessage::class.java) -> {
                //TODO: Rewrite this place after tests
                if (ttc != null) {
                    ttc = ttc!! - 1
                    if (ttc!! == 0) {
                        createUnit()
                        ttc == null
                    }
                }
            }
        }
    }

    abstract fun createUnit()

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
        base = Base(
                mediator,
                ID_GENERATOR.decrementAndGet(),
                this,
                coordinates,
                ID_GENERATOR
        )
        mediator.registerColleague(Base::class.java, base!!)
    }

}
