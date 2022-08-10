package com.litiengine.IAGame;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;

public final class HealthGameLogic {

    private HealthGameLogic() {
    }

    //Initializes the game logic for the game
    public static void init() {

        //A camera is used in the game that is locked to the location of the player character
        Camera camera = new PositionLockCamera(Player.instance());
        camera.setClampToMap(true);
        Game.world().setCamera(camera);

        // set a basic gravity for the game
        Game.world().setGravity(120);

        // add default game logic for when the game world is loaded
        Game.world().onLoaded(e -> {

            // spawn the player instance on the spawn point with the name "Enter"
            Spawnpoint Enter = e.getSpawnpoint("Enter");
            if (Enter != null) {
                Enter.spawn(Player.instance());
            }
        });
    }
}