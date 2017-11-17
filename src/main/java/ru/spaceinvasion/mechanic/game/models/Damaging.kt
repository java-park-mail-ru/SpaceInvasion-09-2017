package ru.spaceinvasion.mechanic.game.models

import ru.spaceinvasion.mechanic.game.GamePart
import ru.spaceinvasion.mechanic.game.Mediator

/**
 * Created by egor on 17.11.17.
 */

 interface Damaging{

    var health: Int

    fun damage(damage: Int) {
        health -= damage
        if (health <= 0) {
            kill()
        }
    }

    fun regenerate(health: Int) {
        this.health += health
    }

    fun kill() {
        health = 0
    }
}
