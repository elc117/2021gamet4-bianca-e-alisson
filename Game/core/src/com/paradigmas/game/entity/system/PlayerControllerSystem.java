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

    /** teste de comentário */
    private boolean moveRight;
    private boolean moveLeft;
    private boolean jump;
    private boolean down;
    private boolean coffe;
    private boolean temp;
    private long tempoFinal;
    private boolean pause;
    private boolean menu;

    private boolean NextFase;

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

        float walkSpeed = 100;
        long tempoCorrente = System.currentTimeMillis();

        if(coffe && cPlayer.have_coffe()) {
            walkSpeed = cPlayer.buffedWalkSpeed;
            cPlayer.coffe--;

            coffe = false;
            temp = true;
            tempoFinal = tempoCorrente + 10000;
        } else if (!temp) {
            walkSpeed = cPlayer.normalWalkSpeed;
        }
        
        if(temp) {
            walkSpeed = cPlayer.buffedWalkSpeed;

            if(tempoCorrente > tempoFinal)
            {
                temp = false;
            }
        }

        if (cPlayer.canWalk) {
            if (moveLeft == moveRight) {
                cRigidBody.velocity.x = 0;
            } else if (moveLeft) {
                cRigidBody.velocity.x = -walkSpeed;
            } else if (moveRight) {
                cRigidBody.velocity.x = walkSpeed;
            }
        }

        if (cJump.canJump && cCollidable.onGround /*|| cCollidable.onRightWall || cCollidable.onLeftWall)*/ && jump) {
            cRigidBody.velocity.y = cJump.jumpSpeed;
        }


        /*if ((Gdx.input.isKeyJustPressed(Input.Keys.V) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
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
        }*/
    }

    private class GameInputAdapter extends InputAdapter {
        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {

                /// Movimentação básica:
                // W
                case Input.Keys.SPACE:
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
                    down = true;
                    break;

                // D
                case Input.Keys.RIGHT:
                case Input.Keys.D:
                    moveRight = true;
                    break;

                /// Pause/Resume / Menu
                case Input.Keys.P:
                    pause = true;
                    break;

                case Input.Keys.ESCAPE:
                    menu = true;
                    break;
            }

            /// skils/buffs/extras
            // Z
            if(Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                coffe = true;
            }

            if(Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                coffe = true;
            }

            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode) {

                // W
                case Input.Keys.SPACE:
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
                    down = false;
                    break;

                // D
                case Input.Keys.RIGHT:
                case Input.Keys.D:
                    moveRight = false;
                    break;

                /// skils/buffs/extras
                // Z
                // é por tempo;


                /// Pause/Resume / Menu
                case Input.Keys.P:
                    pause = false;
                    break;

                case Input.Keys.ESCAPE:
                    menu = false;
                    break;
            }

            return true;
        }
    }
}