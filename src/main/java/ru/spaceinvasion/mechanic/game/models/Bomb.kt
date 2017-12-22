package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.messages.BombInstallingMessage
import ru.spaceinvasion.mechanic.game.messages.CollisionMessage
import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.mechanic.game.messages.TickMessage
import ru.spaceinvasion.resources.Constants
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by egor on 17.11.17.
 */
class Bomb(mediator: GamePartMediator,
           gamePartId: Long,
           private val installedOnBaseWithId: Long,
           ID_GENERATOR: AtomicLong,
           private val installedOnBaseOfPlayer: Long) : GamePart(mediator, gamePartId, ID_GENERATOR) {
    private var ttl = Constants.TICKS_UNITL_BOMB_ACTION

    init {
        mediator.send(BombInstallingMessage(this,gamePartId,installedOnBaseOfPlayer),Server::class.java)
    }
    override fun notify(message: GameMessage) {
        when (message.javaClass) {
            (TickMessage::class.java) -> {
                ttl--
                if(ttl == 0) {
                    mediator.send(CollisionMessage(this, gamePartId, this), Base::class.java, installedOnBaseWithId)
                    mediator.removeColleague(Bomb::class.java, this)
                }
            }
        }
    }
}
