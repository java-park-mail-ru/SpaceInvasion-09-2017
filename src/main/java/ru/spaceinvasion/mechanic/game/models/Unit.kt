package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.Race
import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.resources.Constants.*

/**
 * Created by egor on 17.11.17.
 */
class Unit(mediator: GamePartMediator,
           gamePartId: Long,
           override var coordinates: Coordinates,
           val race: Race) : GamePart(mediator, gamePartId), Moving, Damaging {
    override var health: Int = HEALTH_OF_UNIT
    override var speed: Int = SPEED_OF_UNIT
    val damage_power: Int = DAMAGE_POWER_OF_UNIT

    override fun notify(message: GameMessage) {

    }
}
