package com.test.systems;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.test.config.GameConfig;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.MainItemComponent;
import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;

import java.util.Iterator;

@All(PhysicsBodyComponent.class)
public class SetFilterDataSystem extends BaseEntitySystem {

    protected SceneLoader mSceneLoader;

    protected ComponentMapper<MainItemComponent> mainItemComponentComponentMapper;
    protected ComponentMapper<PhysicsBodyComponent> physicsBodyComponentComponentMapper;
    protected ComponentMapper<DimensionsComponent> dimensionsComponentComponentMapper;
    protected ComponentMapper<TransformComponent> transformComponentComponentMapper;

    protected MainItemComponent mainItemComponent;
    protected PhysicsBodyComponent physicsBodyComponent;
    protected DimensionsComponent dimensionsComponent;
    protected TransformComponent transformComponent;

    protected boolean firstStart;
    public boolean newScan = false;
    public int count = 0;

    public SetFilterDataSystem() {
        System.out.println("[SetFilterDataSystem] init");


    }

    @Override
    protected void begin() {
        // only count as long as necessary
        if (count < 10) count++;
        // We prefer to wait 3 frames so that all hyperlap res. are fully loaded
        if (count == 9) firstStart = true;
    }

    @Override
    protected void processSystem() {


        if (firstStart) {

            IntBag entities = mSceneLoader.getEngine().getAspectSubscriptionManager()
                    .get(Aspect.all(PhysicsBodyComponent.class))
                    .getEntities();

            for (int ii = 0; ii < entities.size(); ii++ ) {
                updateComponents(entities.get(ii));

                for(String name : mainItemComponent.tags) {
                    System.out.println("Tag name inside mainItemComponent: " + name);
                }

                // all tags from entity
                Iterator<String> iterator = mainItemComponent.tags.iterator();
                System.out.println("[SetFilterDataSystemNew] process - tag sizes: " + mainItemComponent.tags.size);


                // solange es weitere strings im iterator gibt....Schau ob folgende Tags dabei sind
                while (iterator.hasNext()){
                    System.out.println("inside while");
                    String tmp = iterator.next();
                    System.out.println("tag inside while iterator: " + tmp);

                    // Check if Tag = item
                    if (tmp.equals("item")){
                        // set user data
                        physicsBodyComponent.body.getFixtureList().get(0).setFilterData(GameConfig.GAMECONFIG.itemFilter);
                        System.out.println("[SetFilterDataSystemNew] process - Set item Filter Data");

                    }
                    // Check if Tag = player
                    if (tmp.equals("player")){

                        // check if player & bottom already have filter !
                        // it there is only one filter -> all filters has been set !

                        if (physicsBodyComponent.body.getFixtureList().get(0).getFilterData() == GameConfig.GAMECONFIG.playerFilter ||
                                physicsBodyComponent.body.getFixtureList().get(0).getFilterData() == GameConfig.GAMECONFIG.playerButtomFilter) {
                            System.out.println("[SetFilterDataSystemNew] process - player tag: Player already has filters ! filterdata: ");
                            continue;
                        }else {

                            // set user data for each fixture
                            for (int j = 0; j < physicsBodyComponent.body.getFixtureList().size; j++) {
                                physicsBodyComponent.body.getFixtureList().get(j).setFilterData(GameConfig.GAMECONFIG.playerFilter);
                                System.out.println("[SetFilterDataSystemNew] process - Set player Filter Data - Fixture: " + (j + 1));
                            }

                            // playerButtom fixture: physicsBodyComponent.centerX / .centerY gibt es nicht mehr !

                            // playerButtom fixture
                            PolygonShape shape = new PolygonShape();
                            shape.setAsBox(0.15f, 0.15f, new Vector2(
                                    physicsBodyComponent.centerOfMass.x,
                                    physicsBodyComponent.centerOfMass.y - 0.1f), 0);
                            FixtureDef fixtureDef = new FixtureDef();
                            fixtureDef.shape = shape;
                            fixtureDef.isSensor = true;

                            // Type:
                            // SHAPE are used to define the may shape of the object
                            // SENSORS are fixture created and managed by hyperlap sensor component while
                            // USER_DEFINED are your own custom fixtures that hyperlap never touch (recycle or remove)

                            // userData: used for pseudo3d lights data....can be null
                            physicsBodyComponent.createFixture(PhysicsBodyComponent.FIXTURE_TYPE_USER_DEFINED, fixtureDef, null).setFilterData(GameConfig.GAMECONFIG.playerButtomFilter);
                            System.out.println("[SetFilterDataSystemNew] process - Set player buttom Filter Data");
                            shape.dispose();

                        }

                    }
                    // Check if Tag = ground
                    if (tmp.equals("ground")){
                        // set user data
                        for (int i = 0; i < physicsBodyComponent.body.getFixtureList().size; i++){
                            physicsBodyComponent.body.getFixtureList().get(i).setFilterData(GameConfig.GAMECONFIG.groundFilter);
                            System.out.println("[SetFilterDataSystemNew] process - Set ground Filter Data - Fixture: " + (i + 1));
                        }

                    }
                    // Check if Tag = jumpbutton
                    if (tmp.equals("jumpbutton")){
                        // set user data
                        physicsBodyComponent.body.getFixtureList().get(0).setFilterData(GameConfig.GAMECONFIG.jumpbuttonfilter);
                        System.out.println("[SetFilterDataSystemNew] process - Set jumpfilter Filter Data");

                    }

                    // Check if Tag = ball
                    if (tmp.equals("ball")){
                        // set user data
                        physicsBodyComponent.body.getFixtureList().get(0).setFilterData(GameConfig.GAMECONFIG.ballFilter);
                        System.out.println("[SetFilterDataSystemNew] process - Set ball Filter Data");

                    }

                    // Check if Tag = ball
                    if (tmp.equals("MonsterCat")){
                        // set user data

                        // set user data for each fixture
                        for (int i = 0; i < physicsBodyComponent.body.getFixtureList().size; i++){
                            physicsBodyComponent.body.getFixtureList().get(i).setFilterData(GameConfig.GAMECONFIG.monsterCatFilter);
                            System.out.println("[SetFilterDataSystemNew] process - Set MonsterCat Filter Data - Fixture: " + (i + 1));
                        }
                    }
                }
            }
            System.out.println("[SetFilterDataSystemNew] process - Set firstStart / newScan to false");
            firstStart = false;
            newScan = false;
        }
    }

    private void updateComponents(int entity) {
        mainItemComponent = mainItemComponentComponentMapper.get(entity);
        physicsBodyComponent = physicsBodyComponentComponentMapper.get(entity);
        dimensionsComponent = dimensionsComponentComponentMapper.get(entity);
        transformComponent = transformComponentComponentMapper.get(entity);
    }
}
