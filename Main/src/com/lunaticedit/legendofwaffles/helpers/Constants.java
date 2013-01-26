package com.lunaticedit.legendofwaffles.helpers;

import com.lunaticedit.legendofwaffles.enums.RepositoryType;

public class Constants {
    public static final String Title = "Legend of Waffles!";
    public static final String TilesetFile = "tileset.png";
    public static final String MainMenuBackgroundFile = "title.png";
    public static final String GameBG1File = "background1.png";
    public static final String GameBG2File = "background2.png";
    public static final int GameWidth = 280;
    public static final int VerticalReduce = 16;
    public static final int GameHeight = 210;
    public static final int TileSize = 8;
    public static final RepositoryType RepositoryType = com.lunaticedit.legendofwaffles.enums.RepositoryType.Live;
    public static final float AspectRatio = (float)GameWidth/(float)GameHeight;
    public static final float PhysicsGravity = 10.0f;
    public static final float PhysicsRenderTime = 1.0f / 60f;
    public static final long RenderSlice = 1000 / 60;
    public static final long ProcessSlice = 1000 / 60;
    public static final int FontStartTile = 960;
    public static final String MainMenuSong = "superchip.ogg";
    public static final String GameSong = "back2blue.ogg";
    public static final String StageDefaultsFile = "StageInfo.xml";
    public static final int AttackSpacing = 300; // How long between attacks
    public static final int AttackDuration = 150; // How long the attack is 'active'
    public static final int StaggerDuration = 150; // How long something 'staggers'
}
