package com.litiengine.IAGame;

import java.awt.geom.Rectangle2D;

import com.litiengine.IAGame.abilities.Jump;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Action;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.input.PlatformingMovementController;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.physics.IMovementController;

//basic information for the player entity (height, width, movement speed and collision settings)
@EntityInfo(width = 18, height = 18)
@MovementInfo(velocity = 100)
@CollisionInfo(collisionBoxWidth = 6, collisionBoxHeight = 14, collision = true)
public class Player extends Creature implements IUpdateable {
    public static final int MAX_ADDITIONAL_JUMPS = 1; //number of additional mid-air jumps (used for the double jump
    // mechanic or for the mid-air jump)

    private static Player instance;

    private final Jump jump;

    private int consecutiveJumps;

    private Player() {
        super("doctor"); //the sprite sheet with the name 'doctor' will be the player entity

        // setup the player's abilities (jumping)
        this.jump = new Jump(this);
    }

    //singleton pattern for sprite - only allow the existence of one single instance of Player at all times
    public static Player instance() {
        if (instance == null) {
            instance = new Player();
        }

        return instance;
    }

    @Override
    public void update() {
        // reset the number of consecutive jumps to 0 when touching the ground
        // (jump resetting mechanic)
        if (this.isTouchingGround()) {
            this.consecutiveJumps = 0;
        }
    }

    @Override
    protected IMovementController createMovementController() {
        // setup movement controller (use A, D and space bar keys to move character)
        return new PlatformingMovementController<>(this);
    }

    //Checks whether the player instance can currently jump and then performs the Jump ability.
    @Action(description = "This performs the jump ability for the player's entity.")
    public void jump() {
        if (this.consecutiveJumps >= MAX_ADDITIONAL_JUMPS || !this.jump.canCast()) {
            return;
        }

        this.jump.cast();
        this.consecutiveJumps++;
    }

    private boolean isTouchingGround() {
        // the idea of this ground check is to see if the player collision box
        // a) collides with any static collision box (land, floating platforms or NPCs)
        Rectangle2D groundCheck = new Rectangle2D.Double(this.getCollisionBox().getX(),
                this.getCollisionBox().getY(), this.getCollisionBoxWidth(), this.getCollisionBoxHeight() + 1);

        // or b) it collides with the map's boundaries
        if (groundCheck.getMaxY() > Game.physics().getBounds().getMaxY()) {
            return true;
        }

        return Game.physics().collides(groundCheck, Collision.STATIC);
    }
}