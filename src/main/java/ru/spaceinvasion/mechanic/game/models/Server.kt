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
    private val randomGenerator = RandomGenerator()
    var playerPeopleLastProccesedSnapId: Long = 0
    var playerAliensLastProcessedSnapId: Long = 0
    var playerPeopleId: Long = 0
    var playerAliensId: Long = 0
    var playerPeopleHasRollback = false
    var playerAliensHasRollback = false
    var gameIsEnded = false



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
            (RollbackMessage::class.java) -> {
                snaps.filter { it.key == message.messageCreator.gamePartId * (-1) }.forEach {
                    it.value.add(ServerSnap(getLastRequestId(it.key), message as RollbackMessage))
                }
                addRollbackBlock(message as RollbackMessage)
            }
            (MoveMessage::class.java) -> {
                snaps.filter { it.key != (message.messageCreator as Unit).owner.gamePartId * (-1) }.forEach {
                    it.value.add(ServerSnap(getLastRequestId(it.key), message as MoveMessage))
                }
            }
            (BuildTowerMessage::class.java) -> {
                snaps.filter { it.key != (message.messageCreator as Unit).owner.gamePartId * (-1) }.forEach {
                    it.value.add(ServerSnap(getLastRequestId(it.key), message as BuildTowerMessage))
                }
            }
            (ShootMessage::class.java) -> {
                snaps.filter { it.key != ((message.messageCreator as Shot).shotMaker as Unit).owner.gamePartId * (-1) }.forEach {
                    it.value.add(ServerSnap(getLastRequestId(it.key), message as ShootMessage))
                }
            }
            (CollisionMessage::class.java) -> {
                //MessageCreator reports about damage to him
                //SrcOfDamage reports about guy who inflict damage
                snaps.forEach {
                    it.value.add(ServerSnap(getLastRequestId(it.key), message as CollisionMessage))
                }
            }

            (DamageTowerMessage::class.java) -> {
                //MessageCreator reports about damage to him
                //SrcOfDamage reports about guy who inflict damage
                snaps.forEach {
                    it.value.add(ServerSnap(getLastRequestId(it.key), message as DamageTowerMessage))
                }
            }
            (DamageShotMessage::class.java) -> {
                //MessageCreator reports about damage to him
                //SrcOfDamage reports about guy who inflict damage
                snaps.forEach {
                    it.value.add(ServerSnap(getLastRequestId(it.key), message as DamageShotMessage))
                }
            }
            (BombInstallingMessage::class.java) -> {
                snaps.forEach {
                    it.value.add(ServerSnap(getLastRequestId(it.key), message as BombInstallingMessage))
                }
            }
            (UnitCreationMessage::class.java) -> {
                snaps.forEach {
                    it.value.add(ServerSnap(getLastRequestId(it.key), message as UnitCreationMessage))
                }
            }
            (CoinAppearanceMessage::class.java) -> {
                snaps.forEach {
                    it.value.add(ServerSnap(getLastRequestId(it.key), message as CoinAppearanceMessage))
                }
            }
            (FinishMessage::class.java) -> {
                gameIsEnded = true
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
    }

    fun newClientMove(clientId: Long, snapId: Long, coords: Coordinates) {
        if (checkRollbackBlock(clientId)) return
        mediator.send(MoveMessage(this, snapId, coords), Player::class.java, clientId * (-1))
    }

    fun newClientTower(clientId: Long, snapId: Long, direction: Direction) {
        if (checkRollbackBlock(clientId)) return
        mediator.send(BuildTowerMessage(this, snapId, direction), Player::class.java, clientId * (-1))
    }

    fun newClientShot(clientId: Long, snapId: Long, direction: Direction) {
        if (checkRollbackBlock(clientId)) return
        mediator.send(ShootMessage(this, snapId, direction), Player::class.java, clientId * (-1))
    }

    fun newClientStateRequest(clientId: Long, snapId: Long) {
        throw NotImplementedError()
    }

    fun newClientBomb(clientId: Long, snapId: Long) {
        if (checkRollbackBlock(clientId)) return
        throw NotImplementedError()
    }

    fun newRollback(clientId: Long, lastSnapId: Long) {
        snaps.filter { it.key == clientId }.forEach {
            it.value.add(ServerSnap(getLastRequestId(it.key), RollbackMessage(this, 0, "A lot of requests per server snap")))
        }
    }

    fun newAcceptRollback(clientId: Long) {
        if (clientId == minOf(playerAliensId, playerPeopleId)) {
            playerPeopleHasRollback = false
        } else {
            playerAliensHasRollback = false
        }
    }

    fun newCoin() {
        Coin(mediator, ID_GENERATOR.decrementAndGet(), ID_GENERATOR, randomGenerator.nextCoords())
    }

    fun tick() {
        mediator.send(TickMessage(this, 0), Shot::class.java)
        mediator.send(TickMessage(this, 0), Tower::class.java)
        mediator.send(TickMessage(this, 0), Bomb::class.java)
        mediator.send(TickMessage(this, 0), Player::class.java)
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

    private fun getLastRequestId(userId: Long) : Long {
        if (userId == minOf(playerAliensId, playerPeopleId)) {
            return playerPeopleLastProccesedSnapId
        } else {
            return playerAliensLastProcessedSnapId
        }
    }

    private fun addRollbackBlock(message: RollbackMessage) {
        if (message.messageCreator.gamePartId * (-1) == minOf(playerAliensId, playerPeopleId)) {
            playerPeopleHasRollback = true;
        } else {
            playerAliensHasRollback = true;
        }
    }

    private fun checkRollbackBlock(userId: Long) : Boolean {
        if (userId == minOf(playerAliensId, playerPeopleId)) {
            return playerPeopleHasRollback
        } else {
            return playerAliensHasRollback
        }
    }

    class RandomGenerator {
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
