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
             gamePartId: Long,
             ID_GENERATOR: AtomicLong = AtomicLong()) : GamePart(mediator, gamePartId, ID_GENERATOR) {

    val snaps: MutableMap<Int, MutableList<Message>> = HashMap()

    init {
        mediator.registerColleague(
                CollisionEngine::class.java,
                CollisionEngine(
                        mediator,
                        ID_GENERATOR.decrementAndGet(),
                        ID_GENERATOR
                )
        )
    }

    override fun notify(message: GameMessage) {
        when (message.javaClass) {
        //TODO: Maybe not message of this type put into snaps? Think about it and change it
            (RollbackMessage::class.java) -> {
                snaps.filter { it.key == (message.messageCreator as Player).userId }.forEach { it.value.add(message)}
            }
            (MoveMessage::class.java) -> {
                snaps.filter { it.key != (message.messageCreator as Player).userId  }.forEach { it.value.add(message)}
            }
            (CashChangeMessage::class.java) -> {
                snaps.filter { it.key == (message.messageCreator as Player).userId }.forEach { it.value.add(message)}
            }
            (DisappearingMessage::class.java) -> {
                snaps.forEach { it.value.add(message) }
            }
        }
    }

    fun startGame(playerPeopleId : Int, playerAliensId : Int) {
        mediator.registerColleague(
                Player::class.java,
                PlayerPeople(mediator, ID_GENERATOR.decrementAndGet(),
                        playerPeopleId,
                        ID_GENERATOR))
        mediator.registerColleague(
                Player::class.java,
                PlayerAliens(mediator, ID_GENERATOR.decrementAndGet(),
                        playerAliensId,
                        ID_GENERATOR))
        snaps.put(playerPeopleId, ArrayList())
        snaps.put(playerAliensId, ArrayList())
    }

    fun newClientMove(clientId: Long, snapId: Long, coords: Coordinates) {
        mediator.send(MoveMessage(this, snapId, coords), Player::class.java, clientId)
    }

    fun newClientTower(clientId: Long, snapId: Long, coords: Coordinates, direction: Direction) {
        mediator.send(BuildTowerMessage(this,snapId,coords,direction), Player::class.java, clientId)
    }

    fun newClientBomb(clientId: Long, snapId: Long) {
    }

    fun newClientShot(clientId: Long, snapId: Long, coords: Coordinates, direction: Direction) {
    }

    fun newClientStateRequest(clientId: Long, snapId: Long): Unit {
        throw NotImplementedError()
    }

    fun tick() {
        //TODO:
    }


}
