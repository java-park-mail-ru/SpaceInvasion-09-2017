package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.*
import ru.spaceinvasion.mechanic.game.messages.*
import ru.spaceinvasion.mechanic.snaps.ServerSnap
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.models.Message
import ru.spaceinvasion.resources.Constants
import java.util.*
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by egor on 17.11.17.
 */
class Server(mediator: GamePartMediator,
             gamePartId: Long,
             ID_GENERATOR: AtomicLong = AtomicLong()) : GamePart(mediator, gamePartId, ID_GENERATOR) {

    val snaps: MutableMap<Long, MutableList<Message>> = HashMap()

    var tickToCoinCreating: Int? = null
    val randomGenerator = RandomGenerator()
    var playerPeopleLastProccesedSnapId: Long = 0
    var playerAliensLastProcessedSnapId: Long = 0
    var playerPeopleId: Long = 0
    var playerAliensId: Long = 0

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
        if (message.javaClass != RollbackMessage::class.java) {
            commitRequest(message);
        }
        when (message.javaClass) {
        //TODO: Maybe not message of this type put into snaps? Think about it and change it
            (RollbackMessage::class.java) -> {
                snaps.filter { it.key == message.messageCreator.gamePartId * (-1) }.forEach {
                    it.value.add(ServerSnap(message as RollbackMessage))
                }
            }
            (MoveMessage::class.java) -> {
                snaps.filter { it.key != (message.messageCreator as Unit).owner.gamePartId * (-1) }.forEach {
                    modifyRequestId(it.key,message)
                    it.value.add(ServerSnap(message as MoveMessage))
                }
            }
            (BuildTowerMessage::class.java) -> {
                snaps.filter { it.key != (message.messageCreator as Unit).owner.gamePartId * (-1) }.forEach {
                    modifyRequestId(it.key,message)
                    it.value.add(ServerSnap(message as BuildTowerMessage))
                }
            }
            (ShootMessage::class.java) -> {
                snaps.filter { it.key != ((message.messageCreator as Shot).shotMaker as Unit).owner.gamePartId * (-1) }.forEach {
                    modifyRequestId(it.key,message)
                    it.value.add(ServerSnap(message as ShootMessage))
                }
            }
            (DamageMessage::class.java) -> {
                //MessageCreator reports about damage to him
                //SrcOfDamage reports about guy who inflict damage
                snaps.forEach {
                    modifyRequestId(it.key,message)
                    it.value.add(ServerSnap(message as DamageMessage))
                }
            }
            (BombInstallingMessage::class.java) -> {
                snaps.forEach {
                    modifyRequestId(it.key,message)
                    it.value.add(ServerSnap(message as BombInstallingMessage))
                }
            }
            (UnitCreationMessage::class.java) -> {
                snaps.forEach {
                    modifyRequestId(it.key,message)
                    it.value.add(ServerSnap(message as UnitCreationMessage))
                }
            }
            (CoinAppearanceMessage::class.java) -> {
                snaps.forEach {
                    modifyRequestId(it.key,message)
                    it.value.add(ServerSnap(message as CoinAppearanceMessage))
                }
            }
        }
    }

    fun startGame(playerPeopleId: Long, playerAliensId: Long) {
        this.playerPeopleId = playerPeopleId
        this.playerAliensId = playerAliensId
        snaps.put(playerPeopleId, ArrayList())
        snaps.put(playerAliensId, ArrayList())
        PlayerPeople(mediator, playerPeopleId * (-1), ID_GENERATOR)
        PlayerAliens(mediator, playerAliensId * (-1), ID_GENERATOR)
        tickToCoinCreating = Constants.TICKS_TO_COIN_CREATING;
    }

    fun newClientMove(clientId: Long, snapId: Long, coords: Coordinates) {
        mediator.send(MoveMessage(this, snapId, coords), Player::class.java, clientId * (-1))
    }

    fun newClientTower(clientId: Long, snapId: Long, direction: Direction) {
        mediator.send(BuildTowerMessage(this, snapId, direction), Player::class.java, clientId * (-1))
    }

    fun newClientShot(clientId: Long, snapId: Long, direction: Direction) {
        mediator.send(ShootMessage(this, snapId, direction), Player::class.java, clientId * (-1))
    }

    fun newClientStateRequest(clientId: Long, snapId: Long) {
        throw NotImplementedError()
    }

    fun newClientBomb(clientId: Long, snapId: Long) {
        throw NotImplementedError()
    }

    fun tick() {
        mediator.send(TickMessage(this, 0), Shot::class.java)
        mediator.send(TickMessage(this, 0), Tower::class.java)
        mediator.send(TickMessage(this, 0), Bomb::class.java)
        mediator.send(TickMessage(this, 0), Player::class.java)
        if (tickToCoinCreating != null) {
            tickToCoinCreating = tickToCoinCreating!! - 1
            if (tickToCoinCreating == 0) {
                Coin(mediator, ID_GENERATOR.decrementAndGet(), ID_GENERATOR, randomGenerator.nextCoords())
                tickToCoinCreating = Constants.TICKS_TO_COIN_CREATING;
            }

        }
    }

    private fun commitRequest(request: GameMessage) {
        if (request.requestId == 0L) {
            return
        }
        if (request.requestId > 0L && request.requestId % 2L == 1L) {
            playerPeopleLastProccesedSnapId = request.requestId
        }
        if (request.requestId > 0L && request.requestId % 2L == 0L) {
            playerAliensLastProcessedSnapId = request.requestId
        }
    }

    private fun modifyRequestId(userId: Long, message: GameMessage) {
        if (message.requestId != 0L) {
            return
        }
        if (userId == minOf(playerAliensId, playerPeopleId)) {
            message.requestId = playerPeopleLastProccesedSnapId
        } else {
            message.requestId = playerAliensLastProcessedSnapId
        }
    }

    class RandomGenerator() {
        val yMin: Int = Constants.Y_OF_UPPER_MAP_BORDER + Constants.VERTICAL_OFFSET_OF_COINS;
        val yMax: Int = Constants.Y_OF_LOWER_MAP_BORDER - Constants.VERTICAL_OFFSET_OF_COINS;
        val xMin: Int = Constants.X_OF_LEFT_MAP_BORDER + Constants.HORIZONTAL_OFFSET_OF_COINS;
        val xMax: Int = Constants.X_OF_RIGHT_MAP_BORDER - Constants.HORIZONTAL_OFFSET_OF_COINS;
        val random = Random()

        fun nextCoords() : Coordinates {
            val y = random.nextInt(yMax - yMin) + yMin
            val x = random.nextInt(xMax - xMin) + xMin
            return Coordinates(x,y)
        }
    }
}
