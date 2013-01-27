package com.lunaticedit.legendofwaffles;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lunaticedit.legendofwaffles.helpers.Constants;

import java.awt.*;

public class DesktopStarter {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = Constants.Title;
        cfg.useGL20 = true;

        final boolean fullscreen = false; // TODO: From settings

        if (!fullscreen) {
            cfg.width = Constants.GameWidth * 2;
            cfg.height = Constants.GameHeight * 2;
        } else {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension deskRes = toolkit.getScreenSize();
            cfg.width = deskRes.width;
            cfg.height = deskRes.height;
            cfg.fullscreen = true;
        }
        cfg.vSyncEnabled = true;
        cfg.useCPUSynch = false;
        cfg.resizable = false;
        new LwjglApplication(new LegendOfWaffles(), cfg);
    }
}
