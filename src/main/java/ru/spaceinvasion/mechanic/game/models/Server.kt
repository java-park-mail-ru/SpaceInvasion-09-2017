package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.*
import ru.spaceinvasion.mechanic.game.messages.*
import ru.spaceinvasion.mechanic.snaps.ServerSnap
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.models.Message
import ru.spaceinvasion.resources.Constants
import java.util.concurrent.atomic.AtomicLong

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
            (BuildTowerMessage::class.java) -> {
                snaps.filter { it.key != (message.messageCreator as Unit).owner.userId  }.forEach { it.value.add(message)}
            }
            (ShootMessage::class.java) -> {
                snaps.filter { it.key != (message.messageCreator as Unit).owner.userId }.forEach { it.value.add(message)}
            }
            (CashChangeMessage::class.java) -> {
                snaps.filter { it.key == (message.messageCreator as Player).userId }.forEach { it.value.add(message)}
            }
            (DisappearingMessage::class.java) -> {
                snaps.forEach { it.value.add(message) }
            }
            (DamageMessage::class.java) -> {
                //MessageCreator reports about damage to him
                //SrcOfDamage reports about guy who inflict damage
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

    fun newClientTower(clientId: Long, snapId: Long, direction: Direction) {
        mediator.send(BuildTowerMessage(this,snapId,direction), Player::class.java, clientId)
    }

    fun newClientShot(clientId: Long, snapId: Long, direction: Direction) {
        mediator.send(ShootMessage(this, snapId, direction),Player::class.java, clientId)
    }

    fun newClientStateRequest(clientId: Long, snapId: Long) {
        throw NotImplementedError()
    }

    fun newClientBomb(clientId: Long, snapId: Long) {
        throw NotImplementedError()
    }

    fun tick() {
        mediator.send(TickMessage(this,ID_GENERATOR.decrementAndGet()),Shot::class.java)
        mediator.send(TickMessage(this,ID_GENERATOR.decrementAndGet()),Tower::class.java)
        mediator.send(TickMessage(this,ID_GENERATOR.decrementAndGet()),Bomb::class.java)
        mediator.send(TickMessage(this,ID_GENERATOR.decrementAndGet()),Player::class.java)
    }


}
