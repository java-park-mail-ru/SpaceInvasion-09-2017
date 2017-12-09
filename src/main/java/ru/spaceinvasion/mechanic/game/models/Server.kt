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

    val snaps: MutableMap<Long, MutableList<Message>> = HashMap()

    var playerPeopleLastProccesedSnapId : Long = 0
    var playerAliensLastProcessedSnapId : Long = 0

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
                snaps.filter { it.key == message.messageCreator.gamePartId * (-1) }.forEach { it.value.add(ServerSnap(message as RollbackMessage))}
            }
            (MoveMessage::class.java) -> {
                snaps.filter { it.key != (message.messageCreator as Unit).owner.gamePartId * (-1)  }.forEach { it.value.add(ServerSnap(message as MoveMessage))}
            }
            (BuildTowerMessage::class.java) -> {
                snaps.filter { it.key != message.messageCreator.gamePartId * (-1)  }.forEach { it.value.add(ServerSnap(message as BuildTowerMessage))}
            }
            (ShootMessage::class.java) -> {
                snaps.filter { it.key != ((message.messageCreator as Shot).shotMaker as Unit).owner.gamePartId * (-1) }.forEach { it.value.add(ServerSnap(message as ShootMessage))}
            }
            (DamageMessage::class.java) -> {
                //MessageCreator reports about damage to him
                //SrcOfDamage reports about guy who inflict damage
                snaps.forEach { it.value.add(ServerSnap(message as DamageMessage)) }
            }
            (BombInstallingMessage::class.java) -> {
                snaps.forEach { it.value.add(ServerSnap(message as BombInstallingMessage)) }
            }
            (UnitCreationMessage::class.java) -> {
                snaps.forEach { it.value.add(ServerSnap(message as UnitCreationMessage))}
            }
        }
    }

    fun startGame(playerPeopleId : Long, playerAliensId : Long) {
        snaps.put(playerPeopleId, ArrayList())
        snaps.put(playerAliensId, ArrayList())
        PlayerPeople(mediator, playerPeopleId * (-1), ID_GENERATOR)
        PlayerAliens(mediator, playerAliensId * (-1), ID_GENERATOR)
    }

    fun newClientMove(clientId: Long, snapId: Long, coords: Coordinates) {
        mediator.send(MoveMessage(this, snapId, coords), Player::class.java, clientId * (-1))
    }

    fun newClientTower(clientId: Long, snapId: Long, direction: Direction) {
        mediator.send(BuildTowerMessage(this,snapId,direction), Player::class.java, clientId * (-1))
    }

    fun newClientShot(clientId: Long, snapId: Long, direction: Direction) {
        mediator.send(ShootMessage(this, snapId, direction),Player::class.java, clientId * (-1))
    }

    fun newClientStateRequest(clientId: Long, snapId: Long) {
        throw NotImplementedError()
    }

    fun newClientBomb(clientId: Long, snapId: Long) {
        throw NotImplementedError()
    }

    fun tick() {
        mediator.send(TickMessage(this,0),Shot::class.java)
        mediator.send(TickMessage(this,0),Tower::class.java)
        mediator.send(TickMessage(this,0),Bomb::class.java)
        mediator.send(TickMessage(this,0),Player::class.java)
    }
}
