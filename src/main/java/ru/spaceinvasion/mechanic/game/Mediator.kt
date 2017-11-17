package ru.spaceinvasion.mechanic.game

import ru.spaceinvasion.mechanic.game.messages.GameMessage

/**
 * Created by egor on 17.11.17.
 */

interface Mediator<in T> {

    fun <_T : T> send(message: GameMessage, sendToColleague: Class<_T>, sendToColleagueId: Long?)

    fun <_T : T> registerColleague(clazz: Class<_T>, colleague: T)

}
