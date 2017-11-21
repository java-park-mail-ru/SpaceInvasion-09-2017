import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.GamePartMediator
import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.mechanic.game.models.Damaging
import ru.spaceinvasion.mechanic.game.models.Moving
import ru.spaceinvasion.mechanic.game.models.Placed
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.resources.Constants.DAMAGE_POWER_OF_TOWER

import ru.spaceinvasion.resources.Constants.HEALTH_OF_TOWER

/**
 * Created by egor on 17.11.17.
 */
class Tower(mediator: GamePartMediator, gamePartId: Long,
            override var coordinates: Coordinates) : GamePart(mediator, gamePartId), Damaging, Placed {
    override var health: Int = HEALTH_OF_TOWER;
    val damage_power: Int = DAMAGE_POWER_OF_TOWER;

    override fun notify(message: GameMessage) {

    }
}
