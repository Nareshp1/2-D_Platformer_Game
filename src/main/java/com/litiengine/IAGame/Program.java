package com.litiengine.IAGame;

import com.litiengine.IAGame.screens.IngameScreen;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;

public class Program {

    public static void main(String[] args) {
        // set meta information about the game
        Game.info().setName("Health Game");
        Game.info().setSubTitle("");
        Game.info().setVersion("v1.0.0");
        Game.info().setDescription("Computer Science IA Program");

        // initialize the game infrastructure
        Game.init(args);

        // load data from the utiLITI game file
        Resources.load("game.litidata");

        //initialize the player input and game logic
        PlayerInput.init();
        HealthGameLogic.init();

        // add the screens that will help  organize the different states of the game
        Game.screens().add(new IngameScreen());

        // load the game level 
        Game.world().loadEnvironment("GameMap");

        //start the game
        Game.start();
    }
}