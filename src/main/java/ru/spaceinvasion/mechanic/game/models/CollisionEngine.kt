package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.messages.*
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.resources.Constants.*
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by egor on 19.11.17.
 */
class CollisionEngine(mediator: GamePartMediator,
                      gamePartId: Long,
                      ID_GENERATOR: AtomicLong) : GamePart(mediator, gamePartId, ID_GENERATOR) {

    private val yOfUpperMapBorder: Int = Y_OF_UPPER_MAP_BORDER;
    private val yOfLowerMapBorder: Int = Y_OF_LOWER_MAP_BORDER
    private val xOfLeftMapBorder: Int = X_OF_LEFT_MAP_BORDER
    private val xOfRightMapBorder: Int = X_OF_RIGHT_MAP_BORDER

    override fun notify(message: GameMessage) {
        when (message.javaClass) {
            (RequestCollisionsMessage::class.java) -> {
                when (message.messageCreator.javaClass) {
                    (Unit::class.java) -> {
                        if (isCrossedMapLimits(
                                (message as RequestCollisionsMessage).potentialCoordinates,
                                UNIT_WIDTH,
                                UNIT_HEIGHT)
                                ) {
                            mediator.send(
                                    RollbackMessage(
                                            this,
                                            message.requestId,
                                            message.requestId,
                                            "Unit try to leave field"
                                    ),
                                    Player::class.java,
                                    (message.messageCreator as Unit).owner.gamePartId
                            )
                        } else {
                            val coin: Coin? = collisionWith(Coin::class.java, message.potentialCoordinates, UNIT_WIDTH, UNIT_HEIGHT)
                            if (coin != null) {
                                mediator.send(
                                        DisappearingMessage(this,message.requestId),
                                        Coin::class.java,
                                        coin.gamePartId)
                                mediator.send(
                                        CashChangeMessage(this,message.requestId, COST_OF_ONE_COIN),
                                        Unit::class.java,
                                        message.messageCreator.gamePartId)
                            } else {
                                val base: Base? = collisionWith(Base::class.java, message.potentialCoordinates, UNIT_WIDTH, UNIT_HEIGHT)
                                if (base != null && base.owner.gamePartId != (message.messageCreator as Unit).owner.gamePartId) {
                                    mediator.registerColleague(
                                            Bomb::class.java,
                                            Bomb(
                                                    mediator,
                                                    message.requestId,
                                                    base.gamePartId,
                                                    ID_GENERATOR
                                            )
                                    )
                                }
                            }
                            mediator.send(
                                    AcceptedMoveMessage(this,message.requestId, message.potentialCoordinates),
                                    Unit::class.java,
                                    message.messageCreator.gamePartId)
                        }
                    }
                    (Shot::class.java) -> {
                        if (isCrossedMapLimits(
                                (message as RequestCollisionsMessage).potentialCoordinates,
                                SHOT_WIDTH,
                                SHOT_HEIGHT)
                                ) {
                            mediator.removeColleague(
                                    Shot::class.java,
                                    (message.messageCreator)
                            )
                        } else {
                            val tower: Tower? = collisionWith(Tower::class.java, message.potentialCoordinates, SHOT_WIDTH, SHOT_HEIGHT)
                            if (tower == null) {
                                val unit: Unit? = collisionWith(Unit::class.java, message.potentialCoordinates, SHOT_WIDTH, SHOT_HEIGHT)
                                if (unit == null) {
                                    mediator.send(
                                            AcceptedMoveMessage(this,message.requestId, message.potentialCoordinates),
                                            Shot::class.java,
                                            message.messageCreator.gamePartId)
                                } else {
                                    mediator.send(
                                            DamageMessage(this, message.requestId, message.messageCreator.gamePartId),
                                            Unit::class.java,
                                            unit.gamePartId)
                                    mediator.removeColleague(Shot::class.java, message.messageCreator)
                                }
                            } else {
                                mediator.send(
                                        DamageMessage(this, message.requestId, message.messageCreator.gamePartId),
                                        Tower::class.java,
                                        tower.gamePartId)
                                mediator.removeColleague(Shot::class.java, message.messageCreator)
                            }

                        }
                    }
                    (BombInstallingMessage::class.java) -> {

                    }
                }
            }
        }
    }

    private fun isCrossedMapLimits(coordinates: Coordinates, widthOfObject: Int, heightOfObject: Int): Boolean {
        return  (coordinates.x < xOfLeftMapBorder) ||
                (coordinates.y < yOfUpperMapBorder) ||
                (coordinates.x + widthOfObject > xOfRightMapBorder) ||
                (coordinates.y + heightOfObject > yOfLowerMapBorder)
    }

    private fun <T> collisionWith(
            clazz: Class<T>, coordinates: Coordinates,
            widthOfObject: Int, heightOfObject: Int
    ): T? where T: GamePart, T: Placed{
        val gameParts: List<T>? = mediator.returnColleagues(clazz) as List<T>?
        if (gameParts == null) {
            return null
        }
        gameParts.forEach { it ->
            if (isIntersect(coordinates, widthOfObject, heightOfObject, it.coordinates, it.width, it.height)) {
                return it;
            }
        }
        return null
    }

    private fun isIntersect(coordinates1: Coordinates,
                            widthOfRect1: Int,
                            heightOfRect1: Int,
                            coordinates2: Coordinates,
                            widthOfRect2: Int,
                            heightOfRect2: Int): Boolean {
        val xL1 = coordinates1.x
        val xR1 = coordinates1.x + widthOfRect1
        val yU1 = coordinates1.y
        val yD1 = coordinates1.y + heightOfRect1
        val xL2 = coordinates2.x
        val xR2 = coordinates2.x + widthOfRect2
        val yU2 = coordinates2.y
        val yD2 = coordinates2.y + heightOfRect2
        val yUR = maxOf(yU1,yU2)
        val xLR = maxOf(xL1,xL2)
        val yDR = minOf(yD1,yD2)
        val xRR = minOf(xR1,xR2)
        return yDR >= yUR && xRR >= xLR
    }

//    private fun isIntersect(coordinates1: Coordinates,
//                            coordinates2: Coordinates,
//                            widthOfRect: Int,
//                            heightOfRect: Int) :Boolean {
//        return  coordinates1.x >= coordinates2.x &&
//                coordinates1.x <= coordinates2.x + widthOfRect &&
//                coordinates1.y >= coordinates2.y &&
//                coordinates1.y <= coordinates2.y + heightOfRect
//    }

}