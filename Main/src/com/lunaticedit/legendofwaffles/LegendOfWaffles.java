package com.lunaticedit.legendofwaffles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class LegendOfWaffles extends Game {
    @Override
    public void create() {
        try { this.setScreen(new GameScreen()); }
        catch (Exception e) { Gdx.app.log("Error", e.getMessage(), e); }
    }
}
