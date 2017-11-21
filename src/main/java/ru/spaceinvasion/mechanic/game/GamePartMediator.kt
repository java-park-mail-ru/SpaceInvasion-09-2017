package ru.spaceinvasion.mechanic.game

import ru.spaceinvasion.mechanic.game.messages.GameMessage

import java.util.ArrayList
import java.util.HashMap
import java.util.concurrent.atomic.AtomicLong

class GamePartMediator : Mediator<GamePart> {

    private val colleagues = HashMap<Class<*>, MutableList<GamePart>>()

    override fun <T : GamePart> registerColleague(clazz: Class<T>, gamePart: GamePart) {
        var gameParts: MutableList<GamePart>
        if (colleagues.containsKey(clazz)) {
            gameParts = colleagues[clazz]!!
            gameParts.add(gamePart)
        } else {
            gameParts = ArrayList<GamePart>()
            gameParts.add(gamePart)
            colleagues.put(clazz, gameParts)
        }
    }

    override fun <T : GamePart> send(message: GameMessage, sendToGamePart: Class<T>, sendToGamePartId: Long) {
        val gameParts = colleagues[sendToGamePart]
        gameParts
                ?.filter {
                    it.gamePartId == sendToGamePartId
                }?.forEach {
                    it.notify(message)
                }
    }

    override fun <_T : GamePart> send(message: GameMessage, sendToGamePart: Class<_T>) {
        val gameParts = colleagues[sendToGamePart]
        gameParts?.forEach {
            send(message, sendToGamePart, it.gamePartId)
        }
    }

    companion object {
        private val ID_GENERATOR = AtomicLong(-1)
    }
}
