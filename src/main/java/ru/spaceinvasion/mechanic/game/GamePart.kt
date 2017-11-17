package ru.spaceinvasion.mechanic.game

import ru.spaceinvasion.mechanic.game.messages.GameMessage

abstract class GamePart(protected var mediator: GamePartMediator,
                        val gamePartId: Long) {

    abstract fun notify(message: GameMessage)
}
