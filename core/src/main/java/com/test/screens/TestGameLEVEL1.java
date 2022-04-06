package com.test.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.GameMain;
import com.test.systems.AddPlayerLibraryItemInRuntimeSystem;
import com.test.systems.CameraSystem;
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
        mSceneLoader.getEngine().process();

        //Ensure that all bodies and z-index order are created
        mSceneLoader.getEngine().process();
        mSceneLoader.getEngine().process();

        // set SceneLoader
        mSceneLoader.getEngine().getSystem(CameraSystem.class).initStartValues();



    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            gameMain.setScreen(new TestGameLEVEL1(resourceManager,mSceneLoader,mViewport,assetManager,mCamera,gameMain,"MainScene"));
            mSceneLoader.getEngine().getSystem(AddPlayerLibraryItemInRuntimeSystem.class).initStartValues();
        }

        newScene();
    }

    // needs to be more complex


    private void newScene() {

        // System "DeleteEntitys" set this flag to true if player dead
        if (firstScreenNewScene) {
            gameMain.setScreen(new TestGameLEVEL1(resourceManager,mSceneLoader,mViewport,assetManager,mCamera,gameMain,"MainScene"));
            mSceneLoader.getEngine().getSystem(AddPlayerLibraryItemInRuntimeSystem.class).initStartValues();

        }
    }

    @Override
    public void dispose() {
        System.out.println("[TestGameLEVEL1] dispose");
    }

}
