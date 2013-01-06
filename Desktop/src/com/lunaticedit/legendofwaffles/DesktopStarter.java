package com.lunaticedit.legendofwaffles;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lunaticedit.legendofwaffles.helpers.Constants;

public class DesktopStarter {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = Constants.Title;
        cfg.useGL20 = true;
        cfg.width = Constants.GameWidth * 2;
        cfg.height = Constants.GameHeight * 2;
        cfg.resizable = false;
        new LwjglApplication(new LegendOfWaffles(), cfg);
    }
}
