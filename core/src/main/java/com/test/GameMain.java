package com.test;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.test.components.PlayerComponent;
import com.test.screens.TestGameLEVEL1;
import com.test.systems.AddPlayerLibraryItemInRuntimeSystem;
import com.test.systems.CameraSystem;
import com.test.systems.SetFilterDataSystem;
import games.rednblack.editor.renderer.ExternalTypesConfiguration;
import games.rednblack.editor.renderer.SceneConfiguration;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;
import games.rednblack.editor.renderer.resources.ResourceManagerLoader;
import games.rednblack.editor.renderer.systems.strategy.HyperLap2dInvocationStrategy;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import games.rednblack.editor.renderer.utils.TextureArrayCpuPolygonSpriteBatch;
import games.rednblack.h2d.extension.spine.SpineItemType;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameMain extends Game {

	public static boolean playerDead;

	private SceneConfiguration config;

	private SceneLoader mSceneLoader;
	protected Viewport mViewport;
	private OrthographicCamera mCamera;
	private Box2DDebugRenderer box2DDebugRenderer;

	public AssetManager assetManager;
	private AsyncResourceManager resourceManager;

	private ExternalTypesConfiguration externalItemTypes;

	@Override
	public void create() {
		// Set log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		// init SceneConfiguration
//		config = new SceneConfiguration(20000);
		config = new SceneConfiguration(new TextureArrayCpuPolygonSpriteBatch(20000));

		//Create a basic camera and a viewport
		mCamera = new OrthographicCamera();
		//mViewport = new ExtendViewport(192, 120, 0, 0, mCamera);
		mViewport = new FitViewport(19,10,mCamera);

		assetManager = new AssetManager();
		// set asset manager logger
		assetManager.getLogger().setLevel(Logger.DEBUG);

		// Add SpineItemType
		externalItemTypes = new ExternalTypesConfiguration();
		externalItemTypes.addExternalItemType(new SpineItemType());
		config.setExternalItemTypes(externalItemTypes);

		// Sets a new AssetLoader for the given type
		assetManager.setLoader(AsyncResourceManager.class,new ResourceManagerLoader(externalItemTypes,assetManager.getFileHandleResolver()));

		// Adds the given asset to the loading queue of the AssetManager
		assetManager.load("project.dt",AsyncResourceManager.class);

		assetManager.finishLoading();

		// resourceManager will be init. here from asset manager
		resourceManager = assetManager.get("project.dt",AsyncResourceManager.class);

		config.setResourceRetriever(resourceManager);

		// add systems
		config.addSystem(new CameraSystem(-50,50));
		config.addSystem(new AddPlayerLibraryItemInRuntimeSystem());
		config.addSystem(new SetFilterDataSystem());

		// Initialize HyperLap2D's SceneLoader, all assets will be loaded here
		mSceneLoader = new SceneLoader(config);

		// add components to mapper
		ComponentRetriever.addMapper(PlayerComponent.class);

		box2DDebugRenderer = new Box2DDebugRenderer();
		setScreen();
	}

	@Override
	public void render() {

		//Clear screen
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Apply ViewPort and update SceneLoader's ECS engine
		mViewport.apply();
		mSceneLoader.getEngine().process();

		getScreen().render(Gdx.graphics.getDeltaTime());;

		//mViewport.apply();
		//box2DDebugRenderer.render(mSceneLoader.getWorld(),mCamera.combined);
	}

	public void setScreen() {

//		 TestGame
		setScreen(new TestGameLEVEL1(resourceManager,mSceneLoader,mViewport,assetManager,mCamera,this,"MainScene"));
	}

	@Override
	public void resize(int width, int height) {

		Gdx.app.debug("[GameMain] resize", " width: " + width + " height: " + height);

		mViewport.update(width, height);

		getScreen().resize(width,height);

		if (width != 0 && height !=0) {
			mSceneLoader.resize(width, height);
		}
	}

	@Override
	public void dispose() {
	}
}