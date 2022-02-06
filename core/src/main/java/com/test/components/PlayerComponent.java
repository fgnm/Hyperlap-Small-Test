package com.test.components;


import com.artemis.PooledComponent;

public class PlayerComponent extends PooledComponent {

    public int touchedPlatforms = 0;

    public boolean jump = false;
    public boolean climb = false;
    public boolean punch = false;
    public float contactHeightMultiplicator = 0.087f;

    public float stateTime = 0;

    // max livescore = 100 - because live-bar max. length = 100
    public float liveScore = 100;


    @Override
    public void reset() {

        jump = false;
        climb = false;
        punch = false;
        stateTime = 0;
        liveScore = 100;
    }


}
