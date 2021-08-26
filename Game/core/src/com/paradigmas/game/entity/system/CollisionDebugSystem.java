package com.paradigmas.game.entity.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.paradigmas.game.entity.component.CollidableComponent;
import com.paradigmas.game.entity.component.RigidBodyComponent;
import com.paradigmas.game.entity.component.TransformComponent;
import com.paradigmas.game.world.World;

public class CollisionDebugSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<RigidBodyComponent> mRigidBody;
    private ComponentMapper<CollidableComponent> mCollidable;

    Camera camera;
    World GameWorld;
    ShapeRenderer shapeRenderer;

    public CollisionDebugSystem(Camera camera, World world) {
        super(Aspect.all(TransformComponent.class, RigidBodyComponent.class, CollidableComponent.class));
        this.camera = camera;
        this.GameWorld = world;
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    protected void begin() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        Array<Rectangle> rectangles = new Array<>();
        Rectangle collisionBox = world.getEntity(GameWorld.getPlayer()).getComponent(CollidableComponent.class).collisionBox;

        GameWorld.getTilesRectangle(
                collisionBox.x,
                collisionBox.y,
                collisionBox.x + collisionBox.width,
                collisionBox.y + collisionBox.height,
                rectangles);

        for (int i = 0; i < GameWorld.getWidth(); i++) {
            for (int j = 0; j < GameWorld.getHeight(); j++) {
                //  x, y
                Rectangle rectangle = GameWorld.getTitleRectangle(i, j);

                if (rectangle != null) {

                    if (rectangles.contains(rectangle, false)) {
                        shapeRenderer.setColor(Color.CYAN);
                    } else {
                        shapeRenderer.setColor(Color.YELLOW);
                    }

                    shapeRenderer.rect(
                            rectangle.x,
                            rectangle.y,
                            rectangle.width,
                            rectangle.height);
                }
            }
        }
    }

    @Override
    protected void process(int entityId) {

        TransformComponent cTransform = mTransform.get(entityId);
        RigidBodyComponent cRigidBody = mRigidBody.get(entityId);
        CollidableComponent cCollidable = mCollidable.get(entityId);

        Vector2 min = cCollidable.collisionBox.getPosition(new Vector2());
        Vector2 max = cCollidable.collisionBox.getSize(new Vector2()).add(min);
        Vector2 size = cCollidable.collisionBox.getSize(new Vector2());

        // setCor Verde
        shapeRenderer.setColor(Color.GREEN);
        // Cria um retangulo em volta do personagem
        shapeRenderer.rect(min.x, min.y, size.x, size.y);

        // setCor Azul
        shapeRenderer.setColor(Color.BLUE);
        // Cria uma linha azul, que tem o comprimento da velocidade
        shapeRenderer.line(
                cTransform.position.x,
                cTransform.position.y,
                cRigidBody.velocity.x + cTransform.position.x,
                cRigidBody.velocity.y + cTransform.position.y);

        // Em caso do personagem estar em uma das extremidades do mapa,
        // o lado correspondente, do retangulo que cerca o personagem, fica vermelho.
        // Ex: onGround = parte de baixo fica vermelha.
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
