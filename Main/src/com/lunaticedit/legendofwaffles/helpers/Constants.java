package com.lunaticedit.legendofwaffles.helpers;

import com.lunaticedit.legendofwaffles.enums.RepositoryType;

/**
 * Constant definitions to prevent 'magic numbers' and strings.
 */
public class Constants {
    /**
     * Title of the game, typically shown on the main window.
     */
    public static final String Title = "Legend of Waffles!";


    /**
     * The file that contains the tileset.
     */
    public static final String TilesetFile = "tileset.png";

    public static final String MainMenuBackgroundFile = "title.png";

    /**
     * Width of the rendering area in pixels.
     */
    public static final int GameWidth = 280;

    /**
     * Some platforms consider the title bar in the size metrics, this variable counteracts that.
     */
    public static final int VerticalReduce = 16;

    /**
     * Height of the rendering area in pixels.
     */
    public static final int GameHeight = 210;

    /**
     * Size of a tile in pixels.
     */
    public static final int TileSize = 8;

    /**
     * The type of backend repository to generate.
     */
    public static final RepositoryType RepositoryType = com.lunaticedit.legendofwaffles.enums.RepositoryType.Live;

    /**
     * The aspect ratio the game is displayed at. Used for scaling.
     */
    public static final float AspectRatio = (float)GameWidth/(float)GameHeight;

    public static final float PhysicsGravity = 10.0f;
    public static final float PhysicsRenderTime = 1.0f / 60f;

    public static final int FontStartTile = 960;
    public static String MainMenuSong = "superchip.ogg";
    public static String StageDefaultsFile = "StageInfo.xml";
}
