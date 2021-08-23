package com.paradigmas.game.entity.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.paradigmas.game.entity.component.CollidableComponent;
import com.paradigmas.game.entity.component.JumpComponent;
import com.paradigmas.game.entity.component.PlayerComponent;
import com.paradigmas.game.entity.component.RigidBodyComponent;
import com.paradigmas.game.entity.component.TransformComponent;

public class PlayerControllerSystem extends IteratingSystem {

    private ComponentMapper<PlayerComponent> mPlayer;
    private ComponentMapper<RigidBodyComponent> mRigidBody;
    private ComponentMapper<CollidableComponent> mCollidable;
    private ComponentMapper<JumpComponent> mJump;
    private ComponentMapper<TransformComponent> mTransform;

    private boolean moveRight;
    private boolean moveLeft;
    private boolean jump;
    private boolean down;

    public PlayerControllerSystem() {
        super(Aspect.all(PlayerComponent.class, RigidBodyComponent.class,
                JumpComponent.class, CollidableComponent.class));

        Gdx.input.setInputProcessor(new InputMultiplexer(new GameInputAdapter()));
    }

    @Override
    protected void process(int entityId) {
        PlayerComponent cPlayer = mPlayer.get(entityId);
        RigidBodyComponent cRigidBody = mRigidBody.get(entityId);
        JumpComponent cJump = mJump.get(entityId);
        CollidableComponent cCollidable = mCollidable.get(entityId);
        TransformComponent cTransform = mTransform.get(entityId);

        if (cPlayer.canWalk) {
            if (moveLeft == moveRight) {
                cRigidBody.velocity.x = 0;
            } else if (moveLeft) {
                cRigidBody.velocity.x = -cPlayer.walkSpeed;
            } else if (moveRight) {
                cRigidBody.velocity.x = cPlayer.walkSpeed;
            }
        }

        if (cJump.canJump && cCollidable.onGround && jump) {
            cRigidBody.velocity.y = cJump.jumpSpeed;
        }

        if ((Gdx.input.isKeyJustPressed(Input.Keys.V) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
                && cCollidable.onGround) {
            if (down) {
                cPlayer.walkSpeed *= 2;
                cTransform.scaleY *= 2;
                down = false;
            } else {
                cPlayer.walkSpeed /= 2;
                cTransform.scaleY /= 2;
                down = true;
            }
        }
    }

    private class GameInputAdapter extends InputAdapter {
        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.RIGHT:
                case Input.Keys.D:
                    moveRight = true;
                    break;

                case Input.Keys.LEFT:
                case Input.Keys.A:
                    moveLeft = true;
                    break;

                case Input.Keys.SPACE:
                case Input.Keys.UP:
                    jump = true;
                    break;
            }

            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode) {
                case Input.Keys.RIGHT:
                case Input.Keys.D:
                    moveRight = false;
                    break;

                case Input.Keys.LEFT:
                case Input.Keys.A:
                    moveLeft = false;
                    break;

                case Input.Keys.SPACE:
                case Input.Keys.UP:
                    jump = false;
                    break;
            }

            return true;
        }
    }
}
