package com.paradigmas.game.entity;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.paradigmas.game.entity.component.CollidableComponent;
import com.paradigmas.game.entity.component.JumpComponent;
import com.paradigmas.game.entity.component.PlayerComponent;
import com.paradigmas.game.entity.component.RigidBodyComponent;
import com.paradigmas.game.entity.component.SpriteComponent;
import com.paradigmas.game.entity.component.TransformComponent;
import com.paradigmas.game.resource.Assets;

public class EntitiesFactory {
    private ComponentMapper<PlayerComponent> mPlayer;
    private ComponentMapper<SpriteComponent> mSprite;
    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<RigidBodyComponent> mRigidBody;
    private ComponentMapper<CollidableComponent> mCollidable;
    private ComponentMapper<JumpComponent> mJump;

    private Texture texture;

    public int createPlayer(World world, float x, float y) {
        int entity = world.create();

        TransformComponent cTransform = mTransform.create(entity);
        cTransform.position.set(x, y);

        texture = Assets.manager.get(Assets.playerH);

        SpriteComponent cSprite = mSprite.create(entity);
        cSprite.sprite = new Sprite(texture);

        PlayerComponent cPlayer = mPlayer.create(entity);

        RigidBodyComponent cRigidBody = mRigidBody.create(entity);

        CollidableComponent cCollidable = mCollidable.create(entity);
        cCollidable.collisionBox.setSize(texture.getWidth(), texture.getHeight());
        cCollidable.collisionBox.setCenter(new Vector2(x, y));

        JumpComponent cJump = mJump.create(entity);

        return entity;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
