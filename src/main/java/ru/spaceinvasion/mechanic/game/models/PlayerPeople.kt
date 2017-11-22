package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.Race
import ru.spaceinvasion.resources.Constants
import ru.spaceinvasion.resources.Constants.COORDINATES_OF_UNIT_ALIENS_START
import ru.spaceinvasion.resources.Constants.COORDINATES_OF_UNIT_PEOPLE_START
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by egor on 22.11.17.
 */
class PlayerPeople(mediator: GamePartMediator,
                   gamePartId: Long,
                   userId: Int,
                   ID_GENERATOR: AtomicLong
): Player(mediator, gamePartId, userId, ID_GENERATOR) {
    init {
        createUnit(COORDINATES_OF_UNIT_PEOPLE_START)
        createBase(Constants.COORDINATES_OF_BASE_PEOPLE_START)
    }
}