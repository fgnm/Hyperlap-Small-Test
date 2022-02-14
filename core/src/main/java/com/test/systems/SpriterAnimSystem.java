package com.test.systems;

import com.artemis.BaseEntitySystem;
import com.artemis.annotations.All;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Entity;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;
import com.brashmonkey.spriter.gdx.Drawer;
import com.brashmonkey.spriter.gdx.Loader;
import games.rednblack.editor.renderer.SceneLoader;

@All()
public class SpriterAnimSystem extends BaseEntitySystem {

    private SceneLoader mSceneLoader;
    private OrthographicCamera mCamera;

    private String path = "monster/basic_002.scml";
    private FileHandle scmlHandle;
    private SCMLReader reader;

    public static Data data;
    public static final Array<Player> players = new Array<Player>();
    public static Loader loader;
    public static Drawer drawer;
    public static boolean drawBoxes = false;
    public static boolean drawBones = false;

    public static ShapeRenderer renderer;
    public static SpriteBatch batch;
    public static OrthographicCamera camera;
    public static BitmapFont font;
    public static String information = "";
    public static Vector2 infoPosition = new Vector2(0, 0);
//    private static final InputMultiplexer input = new InputMultiplexer();

    private Player player;

    public void setSceneLoader(SceneLoader mSceneLoader, OrthographicCamera mCamera) {
        this.mSceneLoader = mSceneLoader;
        this.mCamera = mCamera;

        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        camera = mCamera;

        if(path != null){
            System.out.println("[SpriterAnimSystem] processSystem - Load Spriter File");
            scmlHandle = Gdx.files.internal(path);
            reader = new SCMLReader(scmlHandle.read());
            data = reader.getData();

            loader = new Loader(data);

            System.out.println();
            System.out.println();
            System.out.println();

//            System.out.println("file parent: " + scmlHandle.file().getParent());

            System.out.println();
            System.out.println();
            System.out.println();



//            loader.load(scmlHandle.file());
            loader.load("monster");

            drawer = new Drawer(loader, batch, renderer);

            player = createPlayer(data.getEntity(0));
            player.setScale(0.01f);
        }

//        Gdx.input.setInputProcessor(input);
    }

    public static Player createPlayer(Entity entity){
        Player player = new Player(entity);
        players.add(player);
        return player;
    }


    @Override
    protected void processSystem() {

        if (camera == null) {
            System.out.println("[SpriterAnimSystem] processSystem - camera is null");
            return;
        }

        camera.update();

        renderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        renderer.begin(ShapeRenderer.ShapeType.Line);

        for(Player player: players)
            player.update();
        for(Player player: players)
            drawer.draw(player);

        if(drawBones)
            for(Player player: players)
                drawer.drawBones(player);
        if(drawBoxes)
            for(Player player: players)
                drawer.drawBoxes(player);

        if(information != null)
            font.draw(batch, information, infoPosition.x, infoPosition.y, 0, Align.center, false);
        batch.end();
        renderer.end();
    }
}
