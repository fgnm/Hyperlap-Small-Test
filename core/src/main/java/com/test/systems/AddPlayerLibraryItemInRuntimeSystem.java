package com.test.systems;

import com.artemis.BaseEntitySystem;
import com.artemis.Component;
import com.artemis.annotations.All;
import com.badlogic.gdx.Gdx;
import com.test.Scripts.PlayerScript;
import com.test.components.PlayerComponent;
import com.test.components.SpineMustManagedComponent;
import com.test.tools.HelperClass;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;

import java.util.ArrayList;

@All(PhysicsBodyComponent.class)
public class AddPlayerLibraryItemInRuntimeSystem extends BaseEntitySystem {

    protected boolean playerLoaded;

    protected SceneLoader mSceneLoader;

    private boolean playerScriptAdded = false;

    private int counter = 0;

    public AddPlayerLibraryItemInRuntimeSystem() {

        Gdx.app.debug("","[AddPlayerLibraryItemInRuntimeSystem] init");
        counter = 0;
        playerScriptAdded = false;
    }

    public void initStartValues() {
        Gdx.app.debug( "","[AddPlayerLibraryItemInRuntimeSystem] initStartValues");
        counter = 0;
        playerScriptAdded = false;
        playerLoaded = false;
    }

    @Override
    protected void processSystem() {

        if (counter < 100) {
            counter++;

            if (counter > 10 ) {
                if ( counter > 20 && playerLoaded && !playerScriptAdded) addPlayerScript();
                if (!playerLoaded) addPlayerAndComponents();
            }
        }

    }


    private void addPlayerAndComponents() {

        if (!playerLoaded) {
            ArrayList<Component> components = new ArrayList<Component>();
            components.add(new PlayerComponent());
            components.add(new SpineMustManagedComponent());

            HelperClass.loadCompositeFromLib(mSceneLoader,"SpineEiV2","Default",0,5,components);

            Gdx.app.debug("","[AddPlayerLibraryItemInRuntimeSystem] addComponentByTagName");
            playerLoaded = true;

            mSceneLoader.getEngine().process();
        }

    }

    private void addPlayerScript() {

        Gdx.app.debug("","[AddPlayerLibraryItemInRuntimeSystem] addPlayerScript - inside method with counter size: " + counter);
        Gdx.app.debug("","[AddPlayerLibraryItemInRuntimeSystem] addPlayerScript - check if there is a player script to add......");

        if (mSceneLoader != null && !playerScriptAdded) {
            mSceneLoader.addScriptByTagName("player", PlayerScript.class);
            Gdx.app.debug("","[AddPlayerLibraryItemInRuntimeSystem] addPlayerScript - script added !");
            playerScriptAdded = true;
        }

        mSceneLoader.getEngine().getSystem(SetFilterDataSystem.class).setNewScan(true);


    }


}
