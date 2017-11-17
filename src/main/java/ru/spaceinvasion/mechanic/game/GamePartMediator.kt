package ru.spaceinvasion.mechanic.game

import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.mechanic.game.models.Field
import ru.spaceinvasion.mechanic.game.models.Player

import java.util.ArrayList
import java.util.HashMap

class GamePartMediator( player1 : Player, player2: Player ) : Mediator<GamePart> {

    init {
        registerColleague(Player::class.java, player1)
        registerColleague(Player::class.java, player2)
        registerColleague(Field::class.java, Field(this,-3))
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

    override fun send(message: GameMessage, gamePart: GamePart, gamePartId: Int?) {
        val gameParts = colleagues[gamePart.javaClass]
        for (_gamePart in gameParts!!) {
            //or equals?
            if (gamePartId == null || _gamePart.gamePartId == gamePartId) {
                _gamePart.notify(message)
            }
        }
    }
}
