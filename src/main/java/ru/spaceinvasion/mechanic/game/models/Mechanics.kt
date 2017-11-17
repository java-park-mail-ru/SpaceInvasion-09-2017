package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.*
import ru.spaceinvasion.mechanic.game.messages.GameMessage
import ru.spaceinvasion.mechanic.game.messages.NewUnitGameMessage
import ru.spaceinvasion.mechanic.game.messages.StartGameMessage
import ru.spaceinvasion.models.Coordinates
import ru.spaceinvasion.resources.Constants

import java.util.ArrayList
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by egor on 17.11.17.
 */
class Mechanics(mediator: GamePartMediator,
                gamePartId: Long,
                val ID_GENERATOR: AtomicLong) : GamePart(mediator, gamePartId) {

    override fun notify(message: GameMessage) {
        when (message.javaClass) {
            StartGameMessage::class.java -> {
                mediator.registerColleague(
                        Player::class.java,
                        Player(mediator, ID_GENERATOR.decrementAndGet(),
                                (message as StartGameMessage).player1Id,
                                ID_GENERATOR, Race.PEOPLE))
                mediator.registerColleague(
                        Player::class.java,
                        Player(mediator, ID_GENERATOR.decrementAndGet(),
                                (message as StartGameMessage).player2Id,
                                ID_GENERATOR, Race.ALIENS))
            }
            NewUnitGameMessage::class.java-> {
                if(message.messageCreator is Player && (message.messageCreator as Player).race == Race.PEOPLE) {
                    mediator.registerColleague(
                            Unit::class.java,
                            Unit(mediator,
                                    message.messageId,
                                    Constants.COORDINATES_OF_UNIT1_START,
                                    Race.PEOPLE)
                    )
                }
                if(message.messageCreator is Player && (message.messageCreator as Player).race == Race.ALIENS) {
                    mediator.registerColleague(
                            Unit::class.java,
                            Unit(mediator,
                                    message.messageId,
                                    Constants.COORDINATES_OF_UNIT2_START,
                                    Race.ALIENS)
                    )
                }
            }
        }
    }
}
