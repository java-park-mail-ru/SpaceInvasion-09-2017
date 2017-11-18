package ru.spaceinvasion.mechanic.game

import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.mechanic.game.models.Mechanics
import ru.spaceinvasion.mechanic.game.models.Player

import java.util.ArrayList
import java.util.HashMap
import java.util.concurrent.atomic.AtomicLong

class GamePartMediator : Mediator<GamePart> {

    init {
        registerColleague(Mechanics::class.java, Mechanics(this, ID_GENERATOR.decrementAndGet(), ID_GENERATOR))
    }

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

    override fun <T : GamePart> send(message: GameMessage, sendToGamePart: Class<T>, sendToGamePartId: Long?) {
        val gameParts = colleagues[sendToGamePart]
        gameParts!!
                .filter { //or equals?
                    sendToGamePartId == null || it.gamePartId == sendToGamePartId
                }
                .forEach { it.notify(message) }
    }

    companion object {
        private val ID_GENERATOR = AtomicLong(-1)
    }
}
