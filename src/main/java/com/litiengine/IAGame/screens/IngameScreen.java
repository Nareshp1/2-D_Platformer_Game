package com.litiengine.IAGame.screens;

import de.gurkenlabs.litiengine.gui.screens.GameScreen;

public class IngameScreen extends GameScreen {
    public static final String NAME = "INGAME-SCREEN";

    //Since there is previously no Screen present, adding a new one will also call ScreenManager.display(),
    // making the newly added IngameScreen the currently visible screen. (This is what the player will see
    //when playing the game
    public IngameScreen() {
        super(NAME);
    }
}