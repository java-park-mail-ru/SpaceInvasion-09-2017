package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.*
import ru.spaceinvasion.mechanic.game.messages.*
import ru.spaceinvasion.mechanic.snaps.ServerSnap
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.models.Message
import ru.spaceinvasion.resources.Constants
import java.util.concurrent.atomic.AtomicLong
import kotlin.Unit

/**
 * Created by egor on 17.11.17.
 */
class Server(mediator: GamePartMediator,
             gamePartId: Long) : GamePart(mediator, gamePartId) {

    val ID_GENERATOR: AtomicLong = AtomicLong()
    val snaps: MutableMap<Int, MutableList<Message>> = HashMap()

    override fun notify(message: GameMessage) {
        when (message.javaClass) {
            NewUnitGameMessage::class.java-> {
                if(message.messageCreator is Player && (message.messageCreator as Player).race == Race.PEOPLE) {
                    mediator.registerColleague(
                            ru.spaceinvasion.mechanic.game.models.Unit::class.java,
                            Unit(mediator,
                                    message.messageId,
                                    Constants.COORDINATES_OF_UNIT1_START,
                                    Race.PEOPLE)
                    )
                }
                if(message.messageCreator is Player && (message.messageCreator as Player).race == Race.ALIENS) {
                    mediator.registerColleague(
                            ru.spaceinvasion.mechanic.game.models.Unit::class.java,
                            Unit(mediator,
                                    message.messageId,
                                    Constants.COORDINATES_OF_UNIT2_START,
                                    Race.ALIENS)
                    )
                }
            }
        }
    }

    fun startGame(playerPeopleId : Int, playerAliensId : Int) {
        mediator.registerColleague(
                Player::class.java,
                Player(mediator, ID_GENERATOR.decrementAndGet(),
                        playerPeopleId,
                        ID_GENERATOR, Race.PEOPLE))
        mediator.registerColleague(
                Player::class.java,
                Player(mediator, ID_GENERATOR.decrementAndGet(),
                        playerAliensId,
                        ID_GENERATOR, Race.ALIENS))
        snaps.put(playerPeopleId, ArrayList())
        snaps.put(playerAliensId, ArrayList())
    }

    fun newClientMove(clientId: Long, snapId: Long, coords: Coordinates) {
        mediator.send(MoveMessage(this, snapId, coords), Player::class.java, clientId)
        //TODO: send message where playerId != clientId
    }

    fun newClientTower(clientId: Long, snapId: Long, coords: Coordinates, direction: Direction) {
        mediator.send(BuildTowerMessage(this,snapId,coords,direction), Player::class.java, clientId)
        //TODO: send message where playerId != clientId
    }

    fun newClientBomb(clientId: Long, snapId: Long) {
        //TODO: send message where playerId != clientId
    }

    fun newClientShot(clientId: Long, snapId: Long, coords: Coordinates, direction: Direction) {
        //TODO: send message where playerId != clientId
    }

    fun newClientStateRequest(clientId: Long, snapId: Long): Unit {
        throw NotImplementedError()
    }

    fun tick() {
        //TODO:
    }


}
