package com.test.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.GameMain;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;

/** First screen of the application. Displayed after the application is created. */
public class TestGameScreen extends ScreenAdapter {

	public GameMain gameMain;

	public SceneLoader mSceneLoader;
	public AsyncResourceManager resourceManager;
	public final Viewport mViewport;
	public AssetManager assetManager;
	public OrthographicCamera mCamera;
	public Viewport hubViewport;

	public String sceneName;

	public float playerDeadTimer = 0;

	public static boolean firstScreenNewScene = false;

	public TestGameScreen(AsyncResourceManager resourceManager, SceneLoader mSceneLoader, Viewport mViewport, AssetManager assetManager, OrthographicCamera mCamera, GameMain gameMain, String sceneName) {
		System.out.println("[TestGameScreen] constructor");
		this.mSceneLoader = mSceneLoader;
		this.resourceManager = resourceManager;
		this.mViewport = mViewport;
		this.assetManager = assetManager;
		this.mCamera = mCamera;
		this.gameMain = gameMain;
		this.sceneName = sceneName;
		hubViewport = new FitViewport(950,500);
	}

	@Override
	public void resize(int width, int height) {
		hubViewport.update(width, height,true);
		System.out.println("[TestGameScreen] resize -  Size: witdh: " + hubViewport.getScreenWidth() + " height: " + hubViewport.getScreenHeight());
	}
}

