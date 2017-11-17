package ru.spaceinvasion.mechanic.game

import ru.spaceinvasion.mechanic.game.messages.GameMessage

abstract class GamePart(protected var mediator: GamePartMediator,
                        val gamePartId: Int) {

    abstract fun notify(message: GameMessage)
}
