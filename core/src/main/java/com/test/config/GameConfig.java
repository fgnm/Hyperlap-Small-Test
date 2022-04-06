package com.test.config;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.utils.Array;

public class GameConfig {

    public static GameConfig GAMECONFIG = new GameConfig();

    public final Filter playerFilter = new Filter();         // Player Filter
    public final Filter playerButtomFilter = new Filter();
    public final Filter itemFilter = new Filter();           // Item Filter
    public final Filter groundFilter = new Filter();
    public final Filter jumpbuttonfilter = new Filter();
    public final Filter ballFilter = new Filter();
    public final Filter punchFilter = new Filter();
    public final Filter monsterCatFilter = new Filter();

    // island positions from RandomIslandsSystem
    public Array<Vector2> islandPositions;
    public int RandomIslandsMapWidth = 50;
    public int RandomIslandMapHeight = 50;


    private GameConfig() {
        playerFilter.categoryBits = 2;                 // init Filter
        itemFilter.categoryBits = 4;
        groundFilter.categoryBits = 8;
        jumpbuttonfilter.categoryBits = 16;
        ballFilter.categoryBits = 32;
        playerButtomFilter.categoryBits = 64;
        punchFilter.categoryBits = 128;
        monsterCatFilter.categoryBits = 256;

//        playerFilter.categoryBits = 2;                 // init Filter
//        itemFilter.categoryBits = 4;
//        groundFilter.categoryBits = 8;
//        jumpbuttonfilter.categoryBits = 16;
//        ballFilter.categoryBits = 32;
//        playerButtomFilter.categoryBits = 128;
//        punchFilter.categoryBits = 256;
    }

    // Attack values ###################################

    public float ballHitPlayer = 10f;






}
