package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.messages.*
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.resources.Constants.*
import sun.reflect.generics.reflectiveObjects.NotImplementedException
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
                                            message.messageId,
                                            message.messageId,
                                            "Unit try to leave field"
                                    ),
                                    Player::class.java,
                                    (message.messageCreator as Unit).owner.gamePartId
                            )
                        } else {
                            val coin: Coin? = collisionWithCoin(message.potentialCoordinates, UNIT_WIDTH, UNIT_HEIGHT)
                            if (coin != null) {
                                mediator.send(
                                        DisappearingMessage(this,message.messageId),
                                        Coin::class.java,
                                        coin.gamePartId)
                                mediator.send(
                                        CashChangeMessage(this,message.messageId, COST_OF_ONE_COIN),
                                        Unit::class.java,
                                        message.messageCreator.gamePartId)
                            } else {
                                val base: Base? = collisionWithBase(message.potentialCoordinates, UNIT_WIDTH, UNIT_HEIGHT)
                                if (base != null && base.owner.gamePartId != (message.messageCreator as Unit).owner.gamePartId) {
                                    mediator.registerColleague(
                                            Bomb::class.java,
                                            Bomb(
                                                    mediator,
                                                    message.messageId,
                                                    (message.messageCreator as Unit).owner.gamePartId,
                                                    ID_GENERATOR
                                            )
                                    )
                                }
                            }
                            mediator.send(
                                    AcceptedMoveMessage(this,message.messageId, message.potentialCoordinates),
                                    Unit::class.java,
                                    message.messageCreator.gamePartId)
                        }
                    }
                    (Shot::class.java) -> {
                        //TODO;
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

    private fun collisionWithCoin(coordinates: Coordinates, widthOfObject: Int, heightOfObject: Int): Coin? {
        val coins: List<Coin>? = mediator.returnColleagues(Coin::class.java) as List<Coin>?
        if (coins == null) {
            return null
        }
        coins.forEach { it ->
            if (isIntersect(coordinates, widthOfObject, heightOfObject, it.coordinates, COIN_WIDTH, COIN_HEIGHT)) {
                return it;
            }
        }
        return null
    }

    private fun  collisionWithBase(coordinates: Coordinates, widthOfObject: Int, heightOfObject: Int): Base? {
        val bases: List<Base>? = mediator.returnColleagues(Base::class.java) as List<Base>?
        if (bases == null) {
            return null
        }
        bases.forEach { it ->
            if (isIntersect(coordinates, widthOfObject, heightOfObject, it.coordinates, BASE_WIDTH, BASE_HEIGTH)) {
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