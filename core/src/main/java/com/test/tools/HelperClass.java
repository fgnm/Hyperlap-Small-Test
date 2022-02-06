package com.test.tools;


import com.artemis.*;
import com.artemis.utils.Bag;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.graphics.g2d.Animation;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.components.MainItemComponent;
import games.rednblack.editor.renderer.components.NodeComponent;
import games.rednblack.editor.renderer.components.sprite.SpriteAnimationComponent;
import games.rednblack.editor.renderer.components.sprite.SpriteAnimationStateComponent;
import games.rednblack.editor.renderer.data.CompositeItemVO;
import games.rednblack.editor.renderer.data.ProjectInfoVO;
import games.rednblack.editor.renderer.utils.ItemWrapper;

import java.util.ArrayList;
import java.util.Iterator;

public class HelperClass {

    private HelperClass(){}

    // When the player is inserted by AddLibraryItemsInRuntimeSystem, it takes at least one frame
    // until it has been upgraded in the system
    //
    // Set playerEntity = -1 in System start fields -  AND when loading screen again
    // Use method in process:
    //
    //         if (playerEntity == -1) {
    //            playerEntity = HelperClass.setPlayerEntity(mSceneLoader,root,playerEntity,"AttackSystem");
    //            return;
    //                                 }                                  }
    public static int setPlayerEntity(SceneLoader mSceneLoader, ItemWrapper root, int playerEntity, String className) {

        System.out.println("[HelperClass] setPlayerEntity in System: " + className);

        if (mSceneLoader == null) return -1;

        // get root entity
        root = new ItemWrapper(mSceneLoader.getRoot(),mSceneLoader.getEngine());
        // player entity
        playerEntity = root.getChild("playerC").getEntity();

        return playerEntity;
    }



    // Load Composite Item from HyperLap2D's Library and add component
    public static <T extends PooledComponent> void loadCompositeFromLib(SceneLoader mSceneLoader, String libraryName, String layer, float posX, float posY, PooledComponent componentClass){
//        System.out.println("HelperClass - loadCompositeFromLib");

        CompositeItemVO tmpComposite = mSceneLoader.loadVoFromLibrary(libraryName);
        tmpComposite.layerName = layer;
        tmpComposite.x = posX;
        tmpComposite.y = posY;

        int tmpEntity = mSceneLoader.getEntityFactory().createEntity(mSceneLoader.getRoot(),tmpComposite);
        mSceneLoader.getEntityFactory().initAllChildren(tmpEntity,tmpComposite);
        mSceneLoader.getEngine().edit(tmpEntity).create(componentClass.getClass());

    }

    // Load Composite Item from HyperLap2D's Library and add component
    public static <T extends PooledComponent> void loadCompositeFromLib(SceneLoader mSceneLoader, String libraryName, String layer, float posX, float posY, ArrayList<Component> components){
        System.out.println("HelperClass - loadCompositeFromLib with components array");

        ProjectInfoVO projectInfoVO = mSceneLoader.getRm().getProjectVO();
        CompositeItemVO tmpComposite = projectInfoVO.libraryItems.get(libraryName);

        tmpComposite.layerName = layer;
        tmpComposite.x = posX;
        tmpComposite.y = posY;

        int tmpEntity = mSceneLoader.getEntityFactory().createEntity(mSceneLoader.getRoot(),tmpComposite);
        mSceneLoader.getEntityFactory().initAllChildren(tmpEntity,tmpComposite);

        for (int i = 0; i < components.size(); i++) {
            mSceneLoader.getEngine().edit(tmpEntity).create(components.get(i).getClass());
            System.out.println("HelperClass - loadCompositeFromLib with components array round " + i);
        }

        Bag tmpBag = new Bag();
        mSceneLoader.getEngine().getEntity(tmpEntity).getComponents(tmpBag);
        System.out.println("HelperClass - loadCompositeFromLib with components array entity components: " + tmpBag.toString());

    }

//    // Load Composite Item from HyperLap2D's Library and add component
//    public static <T extends PooledComponent> void loadCompositeFromLib(SceneLoader mSceneLoader, String libraryName, String layer, float posX, float posY, ArrayList<Component> components){
//        System.out.println("HelperClass - loadCompositeFromLib with components array");
//
//        CompositeItemVO tmpComposite = mSceneLoader.loadVoFromLibrary(libraryName);
//        tmpComposite.layerName = layer;
//        tmpComposite.x = posX;
//        tmpComposite.y = posY;
//
//        int tmpEntity = mSceneLoader.getEntityFactory().createEntity(mSceneLoader.getRoot(),tmpComposite);
//        mSceneLoader.getEntityFactory().initAllChildren(tmpEntity,tmpComposite.composite);
//
//        for (int i = 0; i < components.size(); i++) {
//            mSceneLoader.getEngine().edit(tmpEntity).create(components.get(i).getClass());
//            System.out.println("HelperClass - loadCompositeFromLib with components array round " + i);
//        }
//
//        Bag tmpBag = new Bag();
//        mSceneLoader.getEngine().getEntity(tmpEntity).getComponents(tmpBag);
//        System.out.println("HelperClass - loadCompositeFromLib with components array entity components: " + tmpBag.toString());
//
//    }

    // Load Composite Item from HyperLap2D's Library directly inside a nodeComponent from a composite
    public static <T extends PooledComponent> void loadCompositeFromLibInsideNodeComponent(SceneLoader mSceneLoader, String libraryName, String layer, float posX, float posY, NodeComponent nodeComponent, int compositeEntity){
//        System.out.println("HelperClass - loadCompositeFromLib");

        CompositeItemVO tmpComposite = mSceneLoader.loadVoFromLibrary(libraryName);
        tmpComposite.layerName = layer;
        tmpComposite.x = posX;
        tmpComposite.y = posY;

        int tmpEntity = mSceneLoader.getEntityFactory().createEntity(compositeEntity,tmpComposite);
        mSceneLoader.getEntityFactory().initAllChildren(tmpEntity,tmpComposite);

    }

    // Load Composite Item from HyperLap2D's Library to engine
    public static void loadCompositeFromLib(SceneLoader mSceneLoader, String libraryName, String layer, float posX, float posY){
//        System.out.println("HelperClass - loadCompositeFromLib");

        CompositeItemVO tmpComposite = mSceneLoader.loadVoFromLibrary(libraryName);
        tmpComposite.layerName = layer;
        tmpComposite.x = posX;
        tmpComposite.y = posY;

        mSceneLoader.getEntityFactory().createEntity(mSceneLoader.getRoot(),tmpComposite);
        mSceneLoader.getEntityFactory().initAllChildren(mSceneLoader.getRoot(),tmpComposite);
    }

    // Load Composite Item from HyperLap2D's Library to engine
    public static int getCompositeEntityFromLib(SceneLoader mSceneLoader, String libraryName, String layer, float posX, float posY){
        CompositeItemVO tmpComposite = mSceneLoader.loadVoFromLibrary(libraryName);
        tmpComposite.layerName = layer;
        tmpComposite.x = posX;
        tmpComposite.y = posY;

        int tmpEntity = mSceneLoader.getEntityFactory().createEntity(mSceneLoader.getRoot(),tmpComposite);
        mSceneLoader.getEntityFactory().initAllChildren(mSceneLoader.getRoot(),tmpComposite);
        return tmpEntity;
    }


    // Gibt das Entity für den TAG zurück.
    // Es muss die Component Klasse angegeben in der sich das Entity befindet (um die Suche einzugrenzen)
    public static <T extends Component> Integer getEntityFromTAG(String tag, Class<T> componentClass, World engine) {

        ComponentMapper<MainItemComponent> mainItemComponentComponentMapper = new ComponentMapper<>(MainItemComponent.class,engine);
        MainItemComponent mainItemComponent;

        // Aspect -> like Family in ashley
        // EntitySubscription -> Maintains the list of entities matched by an aspect
        // IntBag -> Collection type a bit like ArrayList but does not preserve the order of its entities

        IntBag entities = engine.getAspectSubscriptionManager().get(Aspect.all(componentClass)).getEntities();

        for (int entity : entities.getData()){
            mainItemComponent = mainItemComponentComponentMapper.get(entity);
            Iterator<String> tmp = mainItemComponent.tags.iterator();

            while (tmp.hasNext()){
                if (tmp.next().equals(tag)){

                    return entity;

                }
            }
        }
        System.out.println("<Contact Listener - getEntityFromTAG> Fehler. Tag nicht gefunden");
        return null;
    }

    // Composite mit Sprite, Sprite an welcher Stelle ?, Animationsname, fps und Playmode
    public static void setAnimationToComposite(int Inentity, int inNodeComponent, String animation, int fps, Animation.PlayMode playMode, World engine){

        ComponentMapper<NodeComponent> nodeComponentComponentMapper = (ComponentMapper<NodeComponent>) ComponentMapper.getFor(NodeComponent.class,engine);
        ComponentMapper<SpriteAnimationComponent> spriteAnimationComponentComponentMapper = (ComponentMapper<SpriteAnimationComponent>) ComponentMapper.getFor(SpriteAnimationComponent.class,engine);
        ComponentMapper<SpriteAnimationStateComponent> spriteAnimationStateComponentComponentMapper = (ComponentMapper<SpriteAnimationStateComponent>) ComponentMapper.getFor(SpriteAnimationStateComponent.class,engine);

        NodeComponent nodeComponent = nodeComponentComponentMapper.get(Inentity);
        SpriteAnimationComponent spriteAnimationComponent = spriteAnimationComponentComponentMapper.get(Inentity);


        // Auf childs eines composites zugreifen. Hier ein Sprite (das im Hyperlap im composite an erster Stelle steht)
        int tmpEntity = nodeComponent.children.get(inNodeComponent);

        // Componenten vom entity init
        SpriteAnimationStateComponent spriteAnimationStateComponent = spriteAnimationStateComponentComponentMapper.get(tmpEntity);

        // Animation setzen
        if (!spriteAnimationComponent.currentAnimation.equals(animation)) {
            // animations configuration

            spriteAnimationComponent.fps = fps;
            spriteAnimationComponent.currentAnimation = animation;
            spriteAnimationComponent.playMode = playMode;

            // Hier wird die Animation gestartet - dies darf nicht in jedem Frame laufen,
            // dann startet die Animation jeden Frame wieder von vorne
            spriteAnimationStateComponent.set(spriteAnimationComponent);
        }

    }


//    public static CompositeVO SaveSceneToCompositeVO(int rootEntity, World engine) {
//        CompositeVO newVo = new CompositeVO();
//        newVo.loadFromEntity(rootEntity,engine);
//
//        System.out.println("[HelperClass] SaveSceneToCompositeVO newVo sComposites Size: " + newVo.sComposites.size());
//
//        return newVo;
//    }

//    public static SceneVO SaveSceneToCompositeVO(int rootEntity, World engine) {
//        CompositeVO newVo = new CompositeVO();
//        newVo.loadFromEntity(rootEntity,engine);
//        SceneVO sceneVo = new SceneVO();
//        sceneVo.composite = newVo;
//
//        return sceneVo;
//    }

}
