package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.Mediator
import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.mechanic.game.messages.StartGameMessage

/**
 * Created by egor on 17.11.17.
 */
class Server(mediator: GamePartMediator,
             gamePartId: Long) : GamePart(mediator, gamePartId) {

    override fun notify(message: GameMessage) {

    }

    fun startGame(player1Id : Int, player2Id : Int) {
        mediator.send(StartGameMessage(this, 0L, player1Id, player2Id), Mechanics::class.java, null)
    }


}
