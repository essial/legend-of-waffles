package com.lunaticedit.legendofwaffles;

import com.badlogic.gdx.Game;

public class LegendOfWaffles extends Game {
    @Override
    public void create() {
        try {
            this.setScreen(new GameScreen());
        } catch (Exception e) {
            // TODO: Proper error logging
            e.printStackTrace();
        }
    }
}
