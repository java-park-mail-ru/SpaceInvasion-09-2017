package ru.spaceinvasion.mechanic.game

import ru.spaceinvasion.mechanic.game.messages.GameMessage

import java.util.concurrent.atomic.AtomicLong
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class GamePartMediator : Mediator<GamePart> {

    val colleagues = HashMap<Class<*>, MutableList<GamePart>>()

    override fun <T : GamePart> registerColleague(clazz: Class<T>, colleague: GamePart) {
        var gameParts: MutableList<GamePart>
        if (colleagues.containsKey(clazz)) {
            gameParts = colleagues[clazz]!!
            gameParts.add(colleague)
        } else {
            gameParts = ArrayList<GamePart>()
            gameParts.add(colleague)
            colleagues.put(clazz, gameParts)
        }
    }

    override fun <T : GamePart> send(message: GameMessage, sendToColleague: Class<T>, sendToColleagueId: Long) {
        val gameParts = colleagues[sendToColleague]
        var recipient: T? = null
        gameParts
                ?.filter {
                    it.gamePartId == sendToColleagueId
                }?.forEach {
                    @Suppress("UNCHECKED_CAST")
                    recipient = it as T
                }
        recipient?.notify(message)
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

    override fun <_T : GamePart> send(message: GameMessage, sendToColleague: Class<_T>) {
        val gameParts = colleagues[sendToColleague]
        val recipients = ArrayList<_T>()
        gameParts?.forEach {
            @Suppress("UNCHECKED_CAST")
            recipients.add(it as _T)
        }
        recipients.forEach {
            if (authenticateColleague(sendToColleague, it)) {
                send(message, sendToColleague, it.gamePartId)
            }
        }
    }

    fun <T : GamePart> returnColleagues(clazz: Class<T>) : List<GamePart>? {
        return (colleagues[clazz]?.toList())
    }

    override fun <_T : GamePart> removeColleague(clazz: Class<_T>, colleague: GamePart) {
        val gameParts: MutableList<GamePart> = colleagues[clazz] as MutableList<GamePart>
        for ((index, gamePartInList) in gameParts.withIndex()) {
            if (colleague.gamePartId == gamePartInList.gamePartId) {
                gameParts.removeAt(index)
                break
            }
        }
    }

    fun <_T : GamePart> authenticateColleague(clazz: Class<_T>, gamePart: GamePart) : Boolean {
        val gameParts: MutableList<GamePart> = colleagues[clazz] as MutableList<GamePart>
        return gameParts.any { gamePart.gamePartId == it.gamePartId }
    }

    companion object {
        private val ID_GENERATOR = AtomicLong(-1)
    }
}
