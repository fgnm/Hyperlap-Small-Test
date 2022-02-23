package com.test.systems;

import com.artemis.BaseEntitySystem;
import com.artemis.Component;
import com.artemis.annotations.All;
import com.test.Scripts.PlayerScript;
import com.test.components.PlayerComponent;
import com.test.components.SpineMustManagedComponent;
import com.test.tools.HelperClass;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;

import java.util.ArrayList;

@All(PhysicsBodyComponent.class)
public class AddPlayerLibraryItemInRuntimeSystem extends BaseEntitySystem {

    private SceneLoader mSceneLoader;

    private boolean playerScriptAdded = false;

    private int counter = 0;

    public AddPlayerLibraryItemInRuntimeSystem() {
        System.out.println("[AddPlayerLibraryItemInRuntimeSystem] init");
    }

    public void setSceneLoader(SceneLoader mSceneLoader) {
        this.mSceneLoader = mSceneLoader;
        counter = 0;
        playerScriptAdded = false;


    }

    @Override
    protected void processSystem() {

        if (counter < 10) {
            counter++;
        }else if (counter == 10) {
            // add player with components
            addPlayerAndComponents();
            counter++;
        }else if (counter == 17) {
            // add player script
            // must called after one frame behind adding the player
            addPlayerScript();
            counter++;
        }else if (counter < 20) {
            counter++;
        }


    }


    private void addPlayerAndComponents() {

        ArrayList<Component> components = new ArrayList<Component>();
        components.add(new PlayerComponent());
        components.add(new SpineMustManagedComponent());

        HelperClass.loadCompositeFromLib(mSceneLoader,"SpineEiV1","Default",0,5,components);

        System.out.println("[AddPlayerLibraryItemInRuntimeSystem] addComponentByTagName");

    }

    private void addPlayerScript() {

        if (mSceneLoader != null && !playerScriptAdded) {
            mSceneLoader.addScriptByTagName("player", PlayerScript.class);
            System.out.println("[AddPlayerLibraryItemInRuntimeSystem] addPlayerScript");
            playerScriptAdded = true;
        }


    }


}
