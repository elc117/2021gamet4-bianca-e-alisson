package com.paradigmas.game.entity.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.paradigmas.game.entity.component.CollidableComponent;
import com.paradigmas.game.entity.component.JumpComponent;
import com.paradigmas.game.entity.component.PlayerComponent;
import com.paradigmas.game.entity.component.RigidBodyComponent;
import com.paradigmas.game.entity.component.SpriteComponent;
import com.paradigmas.game.entity.component.TransformComponent;
import com.paradigmas.game.resource.Assets;
import com.paradigmas.game.world.World;

public class PlayerControllerSystem extends IteratingSystem {

    private ComponentMapper<PlayerComponent> mPlayer;
    private ComponentMapper<RigidBodyComponent> mRigidBody;
    private ComponentMapper<CollidableComponent> mCollidable;
    private ComponentMapper<JumpComponent> mJump;
    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<SpriteComponent> mSprite;

    private final World world;
    private boolean moveRight;
    private boolean moveLeft;
    private boolean jump;
    private boolean coffe;
    private boolean temp;
    private long tempoFinal;
    private boolean pause;
    //private boolean menu;

    public PlayerControllerSystem(World world) {
        super(Aspect.all(PlayerComponent.class, RigidBodyComponent.class,
                JumpComponent.class, CollidableComponent.class));

        Gdx.input.setInputProcessor(new InputMultiplexer(new GameInputAdapter()));
        this.world = world;
        this.pause = false;
    }

    @Override
    protected void process(int entityId) {
        PlayerComponent cPlayer = mPlayer.get(entityId);
        RigidBodyComponent cRigidBody = mRigidBody.get(entityId);
        JumpComponent cJump = mJump.get(entityId);
        CollidableComponent cCollidable = mCollidable.get(entityId);
        TransformComponent cTransform = mTransform.get(entityId);
        final SpriteComponent cSprite = mSprite.get(entityId);

        float walkSpeed = 100;
        long tempoCorrente = System.currentTimeMillis();

        if (coffe && cPlayer.have_coffe()) {
            walkSpeed = cPlayer.buffedWalkSpeed;
            cPlayer.coffe--;

            coffe = false;
            temp = true;
            tempoFinal = tempoCorrente + 10000;
        } else if (!temp) {
            walkSpeed = cPlayer.normalWalkSpeed;
        }

        if (temp) {
            walkSpeed = cPlayer.buffedWalkSpeed;

            if (tempoCorrente > tempoFinal) {
                temp = false;
            }
        }

        if (cPlayer.canWalk) {
            if (moveLeft == moveRight) {
                cRigidBody.velocity.x = 0;
            } else if (moveLeft) {
                cSprite.sprite = new Sprite(Assets.manager.get(Assets.Idle_left_000));
                cRigidBody.velocity.x = -walkSpeed;

            } else if (moveRight) {
                cSprite.sprite = new Sprite(Assets.manager.get(Assets.Idle_right_000));
                cRigidBody.velocity.x = walkSpeed;
            }
        }

        if (cJump.canJump && cCollidable.onGround /*|| cCollidable.onRightWall || cCollidable.onLeftWall)*/ && jump) {
            cRigidBody.velocity.y = cJump.jumpSpeed;
        }
    }

    private class GameInputAdapter extends InputAdapter {
        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {

                /// Movimentação básica:
                // W
                case Input.Keys.UP:
                case Input.Keys.W:
                    jump = true;
                    break;

                // A
                case Input.Keys.LEFT:
                case Input.Keys.A:
                    moveLeft = true;
                    break;

                // S
                case Input.Keys.DOWN:
                case Input.Keys.V:
                case Input.Keys.S:
                    //down = true;
                    break;

                // D
                case Input.Keys.RIGHT:
                case Input.Keys.D:
                    moveRight = true;
                    break;
            }

            /// skils/buffs/extras
            // Z
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                coffe = true;
            }

            //Pause/resume
            if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                pause = !pause;
            }

            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode) {

                // W
                case Input.Keys.UP:
                case Input.Keys.W:
                    jump = false;
                    break;

                // A
                case Input.Keys.LEFT:
                case Input.Keys.A:
                    moveLeft = false;
                    break;

                //S
                case Input.Keys.DOWN:
                case Input.Keys.V:
                case Input.Keys.S:
                    //down = false;
                    break;

                // D
                case Input.Keys.RIGHT:
                case Input.Keys.D:
                    moveRight = false;
                    break;
            }

            return true;
        }
    }
}