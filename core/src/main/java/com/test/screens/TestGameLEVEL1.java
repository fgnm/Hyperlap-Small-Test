package com.test.screens;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.GameMain;
import com.test.systems.AddPlayerLibraryItemInRuntimeSystem;
import com.test.systems.CameraSystem;
import com.test.systems.SpriterAnimSystem;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;

public class TestGameLEVEL1 extends TestGameScreen {

    public TestGameLEVEL1(AsyncResourceManager resourceManager, SceneLoader mSceneLoader, Viewport mViewport, AssetManager assetManager, OrthographicCamera mCamera, GameMain gameMain, String sceneName) {
        super(resourceManager, mSceneLoader, mViewport, assetManager, mCamera, gameMain, sceneName);
    }

    @Override
    public void show() {

        System.out.println("[TestGameLEVEL1] new scene");

        // deactivate Systems
        // ---

        mSceneLoader.loadScene(super.sceneName, mViewport);


        // set SceneLoader
        mSceneLoader.getEngine().getSystem(CameraSystem.class).setSceneLoader(mSceneLoader);
        mSceneLoader.getEngine().getSystem(AddPlayerLibraryItemInRuntimeSystem.class).setSceneLoader(mSceneLoader);
        mSceneLoader.getEngine().getSystem(SpriterAnimSystem.class).setSceneLoader(mSceneLoader,mCamera);



    }

    @Override
    public void render(float delta) {

        newScene();
    }

    // needs to be more complex


    private void newScene() {
        // System "DeleteEntitys" set this flag to true if player dead
        if (firstScreenNewScene) {
            System.out.println("[TestGameLEVEL1] new scene");
            GameMain.playerDead = false;

            mSceneLoader.loadScene("MainScene", mViewport);

            // set SceneLoader
            mSceneLoader.getEngine().getSystem(CameraSystem.class).setSceneLoader(mSceneLoader);

            // set flag false - scene loaded and player alive
            firstScreenNewScene = false;


        }
    }

    @Override
    public void dispose() {
        System.out.println("[TestGameLEVEL1] dispose");
    }

}
