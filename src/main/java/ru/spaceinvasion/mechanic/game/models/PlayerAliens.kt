package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.Race
import ru.spaceinvasion.resources.Constants.COORDINATES_OF_BASE_ALIENS_START
import ru.spaceinvasion.resources.Constants.COORDINATES_OF_UNIT_ALIENS_START
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by egor on 22.11.17.
 */
class PlayerAliens(mediator: GamePartMediator,
                   gamePartId: Long,
                   ID_GENERATOR: AtomicLong
): Player(mediator, gamePartId, ID_GENERATOR) {

    init {
        createUnit()
        createBase(COORDINATES_OF_BASE_ALIENS_START)
    }

    override fun createUnit() {
        createUnit(COORDINATES_OF_UNIT_ALIENS_START)
    }
}