package com.paradigmas.game.entity.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.paradigmas.game.entity.component.SpriteComponent;
import com.paradigmas.game.entity.component.TransformComponent;

public class SpriteRenderSystem extends IteratingSystem
{
    SpriteBatch batch;
    OrthographicCamera camera;

    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<SpriteComponent> mSprite;

    public SpriteRenderSystem(OrthographicCamera camera) {
        super(Aspect.all(TransformComponent.class, SpriteComponent.class));
        batch = new SpriteBatch();
        this.camera = camera;
    }

    @Override
    protected void begin() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }

    @Override
    protected void process(int entityId) {
        TransformComponent cTransform = mTransform.get(entityId);
        SpriteComponent cSprite = mSprite.get(entityId);

        if(cTransform.originCenter)
        {
            cSprite.sprite.setOriginCenter();
        }
        else
        {
            cSprite.sprite.setOrigin(cTransform.origin.x, cTransform.origin.y);
        }

        cSprite.sprite.setScale(cTransform.scaleX, cTransform.scaleY);
        cSprite.sprite.setRotation(cTransform.rotation);
        cSprite.sprite.setPosition(cTransform.position.x, cTransform.position.y);

        cSprite.sprite.draw(batch);
    }

    @Override
    protected void end() {
        batch.end();
    }

    @Override
    protected void dispose() {
        batch.dispose();
    }
}
