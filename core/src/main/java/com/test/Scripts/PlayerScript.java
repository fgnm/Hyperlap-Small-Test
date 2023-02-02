package com.test.Scripts;


import com.artemis.ComponentMapper;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

import com.test.GameMain;
import com.test.components.PlayerComponent;
import games.rednblack.editor.renderer.components.MainItemComponent;
import games.rednblack.editor.renderer.components.NodeComponent;
import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.physics.PhysicsContact;
import games.rednblack.editor.renderer.scripts.BasicScript;
import games.rednblack.h2d.extension.spine.SpineComponent;

import java.util.Objects;

public class PlayerScript extends BasicScript implements PhysicsContact {

    protected ComponentMapper<PhysicsBodyComponent> physicsMapper;
    protected ComponentMapper<TransformComponent> transformMapper;
    protected ComponentMapper<PlayerComponent> playerMapper;
    protected ComponentMapper<MainItemComponent> mainItemMapper;
    protected ComponentMapper<NodeComponent> nodeMapper;
    protected ComponentMapper<SpineComponent> spineMapper;

    public static final int LEFT = 1;
    public static final int RIGHT = -1;
    public static final int JUMP = 2;
    public static final int IDLE = 0;

    private int playerEntity;
    private int spineEntity;
    private PhysicsBodyComponent mPhysicsBodyComponent;

    private final Vector2 impulse = new Vector2(0, 0);
    private final Vector2 speed = new Vector2(0, 0);

    private float velX;
    private float velY;



    @Override
    public void init(int item) {
        super.init(item);

        playerEntity = item;

        System.out.println("[PlayerScript] player entity: " + item);

        mPhysicsBodyComponent = physicsMapper.get(item);
    }

    @Override
    public void act(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movePlayer(LEFT);
            spineAnimation(LEFT);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movePlayer(RIGHT);
            spineAnimation(RIGHT);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            movePlayer(JUMP);
            spineAnimation(JUMP);
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            spineAnimation(IDLE);
        }


    }

    public void movePlayer(int direction) {

        Body body = mPhysicsBodyComponent.body;

        speed.set(body.getLinearVelocity());

        switch (direction) {
            case JUMP:
                TransformComponent transformComponent = transformMapper.get(playerEntity);

                impulse.set(speed.x, transformComponent.y < 6 ? 10 : speed.y);
                break;
            case LEFT:
                impulse.set(-6, speed.y);
                break;
            case RIGHT:
                impulse.set(6, speed.y);
                break;

        }

        body.applyLinearImpulse(impulse.sub(speed), body.getWorldCenter(), true);
    }

    public void spineAnimation(int direction) {

        TransformComponent transformComponent = transformMapper.get(playerEntity);
        PhysicsBodyComponent physicsBodyComponent = physicsMapper.get(playerEntity);
        if (physicsBodyComponent.body == null) return;

        velX = physicsBodyComponent.body.getLinearVelocity().x;
        velY = physicsBodyComponent.body.getLinearVelocity().y;

        // all spines will be scaled in moving direction
        if (velX < -0.5f) {
            if (transformComponent.scaleX == 1f)
                transformComponent.scaleX = -1f;


        }else if (velX > 0.5f) {
            if (transformComponent.scaleX == -1f)
                transformComponent.scaleX = 1f;
        }

        NodeComponent nodeComponent = nodeMapper.get(playerEntity);

        spineEntity = nodeComponent.children.get(0);

        SpineComponent spineComponent = spineMapper.get(spineEntity);

        switch (direction){
            case IDLE:
                if (!Objects.equals(spineComponent.currentAnimationName, "idle")) {

                    // if the previous one was the jump animation, it must finish
                    if (!animationIsComplete(spineComponent,"jump")) return;

                    spineComponent.state.setAnimation(0,"idle",true);
                    spineComponent.currentAnimationName = "idle";
                }
                break;
            case LEFT:
                if (!Objects.equals(spineComponent.currentAnimationName, "walk")) {

                    // if the previous one was the jump animation, it must finish
                    if (!animationIsComplete(spineComponent,"jump")) return;

                    spineComponent.state.setAnimation(0,"walk",true);
                    spineComponent.currentAnimationName = "walk";
                }
                break;
            case RIGHT:
                if (!Objects.equals(spineComponent.currentAnimationName, "walk")) {

                    // if the previous one was the jump animation, it must finish
                    if (!animationIsComplete(spineComponent,"jump")) return;

                    spineComponent.state.setAnimation(0,"walk",true);
                    spineComponent.currentAnimationName = "walk";
                }
                break;
            case JUMP:
                if (!Objects.equals(spineComponent.currentAnimationName, "jump")) {

                    spineComponent.state.setAnimation(0,"jump",false);
                    spineComponent.currentAnimationName = "jump";
                }
        }
    }

    private boolean animationIsComplete(SpineComponent spineComponent, String animationName) {

        if (spineComponent.currentAnimationName == animationName) {
            if (!spineComponent.state.getCurrent(0).isComplete()) return false;
        }

        return true;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void beginContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
        MainItemComponent mainItemComponent = mainItemMapper.get(contactEntity);

        PlayerComponent playerComponent = playerMapper.get(playerEntity);

        if (mainItemComponent.tags.contains("platform"))
            playerComponent.touchedPlatforms++;

    }

    @Override
    public void endContact(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
        MainItemComponent mainItemComponent = mainItemMapper.get(contactEntity);

        PlayerComponent playerComponent = playerMapper.get(playerEntity);

        if (mainItemComponent.tags.contains("platform"))
            playerComponent.touchedPlatforms--;
    }

    @Override
    public void preSolve(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
        TransformComponent transformComponent = transformMapper.get(this.playerEntity);


        TransformComponent colliderTransform = transformMapper.get(contactEntity);
    }

    @Override
    public void postSolve(int contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

    }
}




























