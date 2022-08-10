package com.litiengine.IAGame.abilities;

import java.util.Optional;

import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.abilities.AbilityInfo;
import de.gurkenlabs.litiengine.abilities.effects.EffectApplication;
import de.gurkenlabs.litiengine.abilities.effects.EffectTarget;
import de.gurkenlabs.litiengine.abilities.effects.ForceEffect;
import de.gurkenlabs.litiengine.entities.CollisionBox;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityPivotType;
import de.gurkenlabs.litiengine.entities.IMobileEntity;
import de.gurkenlabs.litiengine.physics.Force;
import de.gurkenlabs.litiengine.physics.GravityForce;

//sets the basic parameters for the jump ability (cooldown, height, and duration of the jump mechanic)
@AbilityInfo(cooldown = 100, origin = EntityPivotType.COLLISIONBOX_CENTER, duration = 200, value = 300)
public class Jump extends Ability {

    public Jump(Creature executor) {
        super(executor);

        this.addEffect(new JumpEffect(this));
    }

    //the jump mechanic will be applied a given force to its affected entities (player entity)
    private class JumpEffect extends ForceEffect {

        protected JumpEffect(Ability ability) {
            super(ability, ability.getAttributes().value().get().intValue(), EffectTarget.EXECUTINGENTITY);
        }

        @Override
        protected Force applyForce(IMobileEntity affectedEntity) {
            // create a new force and apply it to the player when the jump ability is used
            GravityForce force = new GravityForce(affectedEntity, this.getStrength(), Direction.UP);
            affectedEntity.movement().apply(force);
            return force;
        }

        @Override
        protected boolean hasEnded(final EffectApplication appliance) {
            return super.hasEnded(appliance) || this.isTouchingCeiling();
        }

        //The jump is cancelled if the player entity collides with a static collision box above it (game ceiling or
        //floating platform) - this is used to cancel the ForceEffect that allows the player to jump up.
        private boolean isTouchingCeiling() {

            Optional<CollisionBox> opt = Game.world().environment().getCollisionBoxes().stream().filter(x -> x.getBoundingBox().intersects(this.getAbility().getExecutor().getBoundingBox())).findFirst();
            if (!opt.isPresent()) {
                return false;
            }

            CollisionBox box = opt.get();
            return box.getCollisionBox().getMaxY() <= this.getAbility().getExecutor().getCollisionBox().getMinY();
        }
    }
}