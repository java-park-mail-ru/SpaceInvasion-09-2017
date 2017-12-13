package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.messages.*
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.resources.Constants.*

import java.util.concurrent.atomic.AtomicLong

/**
 * Created by egor on 17.11.17.
 */
class Base(mediator: GamePartMediator,
           gamePartId: Long,
           val owner: Player,
           override var coordinates: Coordinates,
           ID_GENERATOR: AtomicLong): GamePart(mediator, gamePartId, ID_GENERATOR), Damaging, Placed {
    override var isAlive: Boolean = true
    override var health: Int = HEALTH_OF_BASE
    override val width = BASE_WIDTH
    override val height = BASE_HEIGHT
    var isMined = false
    override fun notify(message: GameMessage) {
        when(message.javaClass) {
            (CollisionMessage::class.java) -> {
                damage(1)
                isMined = false
                if(!isAlive) {
                    mediator.send(LosingMessage(this,message.requestId),Player::class.java, owner.gamePartId)
                    mediator.sendToAllExclude(WiningMessage(this,message.requestId),Player::class.java, owner.gamePartId)
                    mediator.send(FinishMessage(this,0), Server::class.java)
                }
            }
            (BombInstallingMessage::class.java) -> {
                if (!isMined) {
                    isMined = true
                    mediator.registerColleague(
                            Bomb::class.java,
                            Bomb(
                                    mediator,
                                    message.requestId,
                                    gamePartId,
                                    ID_GENERATOR,
                                    owner.gamePartId
                            )
                    )
                }
            }
        }
    }
}
