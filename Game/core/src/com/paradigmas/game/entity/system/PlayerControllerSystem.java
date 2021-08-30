package com.paradigmas.game.entity.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.paradigmas.game.entity.component.CollidableComponent;
import com.paradigmas.game.entity.component.JumpComponent;
import com.paradigmas.game.entity.component.PlayerComponent;
import com.paradigmas.game.entity.component.RigidBodyComponent;
import com.paradigmas.game.entity.component.SpriteComponent;
import com.paradigmas.game.entity.component.TransformComponent;
import com.paradigmas.game.resource.Assets;
import com.paradigmas.game.world.World;

import java.util.Timer;

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
    private boolean down;
    private boolean coffe;
    private boolean temp;
    private long tempoFinal;
    private boolean pause;
    private boolean menu;

    int i = 0;
    final Array<AssetDescriptor<Texture>> assetDescriptors = new Array<>();
    Timer timer = new Timer();

    public static boolean NextFase;

    public PlayerControllerSystem(World world) {
        super(Aspect.all(PlayerComponent.class, RigidBodyComponent.class,
                JumpComponent.class, CollidableComponent.class));

        Gdx.input.setInputProcessor(new InputMultiplexer(new GameInputAdapter()));
        this.world = world;
    }

    @Override
    protected void process(int entityId) {
        PlayerComponent cPlayer = mPlayer.get(entityId);
        RigidBodyComponent cRigidBody = mRigidBody.get(entityId);
        JumpComponent cJump = mJump.get(entityId);
        CollidableComponent cCollidable = mCollidable.get(entityId);
        TransformComponent cTransform = mTransform.get(entityId);
        final SpriteComponent cSprite = mSprite.get(entityId);

        assetDescriptors.clear();
        assetDescriptors.add(Assets.playerH);

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
                cRigidBody.velocity.x = -walkSpeed;

            } else if (moveRight) {
                assetDescriptors.clear();
                assetDescriptors.add(Assets.Run__000);
                assetDescriptors.add(Assets.Run__001);
                assetDescriptors.add(Assets.Run__002);
                assetDescriptors.add(Assets.Run__003);
                assetDescriptors.add(Assets.Run__004);
                assetDescriptors.add(Assets.Run__005);
                assetDescriptors.add(Assets.Run__006);
                assetDescriptors.add(Assets.Run__007);
                assetDescriptors.add(Assets.Run__008);
                assetDescriptors.add(Assets.Run__009);

                cRigidBody.velocity.x = walkSpeed;

                /*
                if(cCollidable.onGround) {
                    cSprite.sprite = new Sprite(Assets.manager.get(assetDescriptors.get(i)));
                    if (i < 9) {
                        i++;
                    } else {
                        i = 0;
                    }
                }


                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        cSprite.sprite = new Sprite(Assets.manager.get(assetDescriptors.get(i)));
                        i++;
                    }
                }, 1, 200);*/
            }

            //timer.cancel();
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
            if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                coffe = true;
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                NextFase = true;
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