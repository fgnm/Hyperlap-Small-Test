package com.test.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.test.tools.HelperClass;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.components.ViewPortComponent;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.utils.ItemWrapper;

@All(ViewPortComponent.class)
public class CameraSystem extends IteratingSystem {

    private SceneLoader mSceneLoader;
    private ItemWrapper root;
    private int playerEntity = -1;

    protected ComponentMapper<TransformComponent> transformMapper;
    protected ComponentMapper<ViewPortComponent> viewportMapper;
    protected ComponentMapper<PhysicsBodyComponent> bodyMapper;
    private final float yMin, yMax;

    public CameraSystem(float yMin, float yMax) {
        this.yMin = yMin;
        this.yMax = yMax;
    }

    public void setSceneLoader(SceneLoader mSceneLoader) {
        this.mSceneLoader = mSceneLoader;
        playerEntity = -1;
    }

    Vector3 mVector3 = new Vector3();
    @Override
    protected void process(int entity) {

        if (playerEntity == -1) {
            playerEntity = HelperClass.setPlayerEntity(mSceneLoader,root,playerEntity,"CameraSystem");
            return;
        }

        ViewPortComponent viewPortComponent = viewportMapper.get(entity);//ComponentRetriever.get(entity, ViewPortComponent.class, getWorld());
        Camera camera = viewPortComponent.viewPort.getCamera();

        if (playerEntity != -1) {
            TransformComponent transformComponent = transformMapper.get(playerEntity);//ComponentRetriever.get(focus, TransformComponent.class, getWorld());
            PhysicsBodyComponent physicsBodyComponent = bodyMapper.get(playerEntity);
            if (physicsBodyComponent != null && physicsBodyComponent.body != null) {


                float x = transformComponent.x;
                float y = Math.max(yMin, Math.min(yMax, transformComponent.y));

                //Use a temporary variable Vector3 to store target position
                mVector3.set(x, y, 0);

                //Use lerp function to linear interpolate values to get smooth effect
                //Change alpha value to move faster or slower
                camera.position.x = RoundToNearestPixel(Interpolation.smooth2.apply(camera.position.x, mVector3.x, 0.2f), viewPortComponent.pixelsPerWU);
                camera.position.y = RoundToNearestPixel(Interpolation.smooth2.apply(camera.position.y, mVector3.y, 0.2f), viewPortComponent.pixelsPerWU);
            }
        }
    }

    public float RoundToNearestPixel(float unityUnits, float ppwu)  {
        float valueInPixels = unityUnits * ppwu;
        valueInPixels = MathUtils.round(valueInPixels);
        return valueInPixels * (1 / ppwu);
    }
}