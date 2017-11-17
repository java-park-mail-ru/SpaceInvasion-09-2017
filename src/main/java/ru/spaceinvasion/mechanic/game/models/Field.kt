package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.*
import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.mechanic.game.messages.StartGameMessage
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.resources.Constants

import java.util.ArrayList

/**
 * Created by egor on 17.11.17.
 */
class Field(mediator: Mediator<GamePart>,
            gamePartId: Int) : GamePart(mediator, gamePartId) {
    private var unit1: Unit? = null
    private var unit2: Unit? = null
    private val towers = ArrayList<Tower>()
    private val shots = ArrayList<Shot>()
    private var installedBombOn1 = false
    private var installedBombOn2 = false

    override fun notify(message: GameMessage) {
        when (message.javaClass) {
            StartGameMessage::class.java-> {
                unit1 = Unit(mediator, message.messageId, Constants.COORDINATES_OF_UNIT1_START);
                unit1 = Unit(mediator, message.messageId, Constants.COORDINATES_OF_UNIT2_START);
                mediator.registerColleague(Unit::class.java, unit1!!)
                mediator.registerColleague(Unit::class.java, unit2!!)
            }
        }
    }
}
