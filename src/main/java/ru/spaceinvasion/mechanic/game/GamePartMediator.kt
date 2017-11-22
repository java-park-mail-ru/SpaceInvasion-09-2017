package ru.spaceinvasion.mechanic.game

import ru.spaceinvasion.mechanic.game.messages.GameMessage

import java.util.ArrayList
import java.util.HashMap
import java.util.concurrent.atomic.AtomicLong

class GamePartMediator : Mediator<GamePart> {

    val colleagues = HashMap<Class<*>, MutableList<GamePart>>()

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

    fun <T : GamePart> sendToAllExclude(message: GameMessage, sendToGamePart: Class<T>, notSendToGamePartId: Long) {
        val gameParts = colleagues[sendToGamePart]
        gameParts
                ?.filter {
                    it.gamePartId != notSendToGamePartId
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

    fun <T : GamePart> returnColleagues(clazz: Class<T>) : List<GamePart>? {
        //TODO: Check how work casting mutable to immutable
        return (colleagues[clazz]?.toList())
    }

    override fun <_T : GamePart> removeColleague(clazz: Class<_T>, gamePart: GamePart) {
        val gameParts: MutableList<GamePart> = colleagues[clazz] as MutableList<GamePart>
        for ((index, gamePartInList) in gameParts.withIndex()) {
            if (gamePart.gamePartId == gamePartInList.gamePartId) {
                gameParts.removeAt(index)
                break
            }
        }
    }

    companion object {
        private val ID_GENERATOR = AtomicLong(-1)
    }
}
