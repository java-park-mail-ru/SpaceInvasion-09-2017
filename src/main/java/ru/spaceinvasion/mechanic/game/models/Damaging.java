package ru.spaceinvasion.mechanic.game.models;

import org.jetbrains.annotations.NotNull;
import ru.spaceinvasion.mechanic.game.GamePart;
import ru.spaceinvasion.mechanic.game.Mediator;

/**
 * Created by egor on 17.11.17.
 */
public abstract class Damaging extends GamePart {

    Integer health;

    public Damaging(@NotNull Mediator mediator,
                    @NotNull Integer gamePartId,
                    @NotNull Integer health) {
        super(mediator, gamePartId);
        this.health = health;
    }

    protected Integer getHealth() {
        return health;
    }

    protected void damage(Integer damage) {
        health -= damage;
        if (health <= 0) {
            kill();
        }
    }

    protected void regenerate(Integer health) {
        this.health += health;
    }

    private void kill() {
        health = 0;
        setExisted(false);
    }
}
