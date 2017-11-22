package ru.spaceinvasion.mechanic.game

import ru.spaceinvasion.mechanic.game.messages.GameMessage
import java.util.concurrent.atomic.AtomicLong

abstract class GamePart(protected var mediator: GamePartMediator,
                        val gamePartId: Long,
                        protected val ID_GENERATOR: AtomicLong) {

    abstract fun notify(message: GameMessage)
}
