package com.paradigmas.game.entity.system;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.paradigmas.game.entity.component.CollidableComponent;
import com.paradigmas.game.entity.component.RigidBodyComponent;
import com.paradigmas.game.entity.component.TransformComponent;

public class CollisionDebugSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<RigidBodyComponent> mRigidBody;
    private ComponentMapper<CollidableComponent> mCollidable;

    Camera camera;
    ShapeRenderer shapeRenderer;

    public CollisionDebugSystem(Camera camera) {
        super(Aspect.all(TransformComponent.class, RigidBodyComponent.class, CollidableComponent.class));
        this.camera = camera;
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    protected void begin() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeType.Line);
    }

    @Override
    protected void process(int entityId) {

        TransformComponent cTransform = mTransform.get(entityId);
        RigidBodyComponent cRigidBody = mRigidBody.get(entityId);
        CollidableComponent cCollidable = mCollidable.get(entityId);

        Vector2 min = cCollidable.collisionBox.getPosition(new Vector2());
        Vector2 max = cCollidable.collisionBox.getSize(new Vector2()).add(min);
        Vector2 size = cCollidable.collisionBox.getSize(new Vector2());

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(min.x, min.y, size.x, size.y);

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.line(
                cTransform.position.x,
                cTransform.position.y,
                cRigidBody.velocity.x + cTransform.position.x,
                cRigidBody.velocity.y + cTransform.position.y);

        shapeRenderer.setColor(Color.RED);
        if (cCollidable.onGround) {
            shapeRenderer.line(min.x, min.y, max.x, min.y);
        }

        if (cCollidable.onCeiling) {
            shapeRenderer.line(min.x, max.y, max.x, max.y);
        }

        if (cCollidable.onLeftWall) {
            shapeRenderer.line(min.x, min.y, min.x, max.y);
        }

        if (cCollidable.onRightWall) {
            shapeRenderer.line(max.x, min.y, max.x, max.y);
        }
    }

    @Override
    protected void end() {
        shapeRenderer.end();
    }

    @Override
    protected void dispose() {
        shapeRenderer.dispose();
    }
}
