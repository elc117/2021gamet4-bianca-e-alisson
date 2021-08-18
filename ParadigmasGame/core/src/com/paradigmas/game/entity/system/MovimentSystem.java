package com.paradigmas.game.entity.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.paradigmas.game.bloco.Bloco;
import com.paradigmas.game.entity.component.CollidableComponent;
import com.paradigmas.game.entity.component.RigidBodyComponent;
import com.paradigmas.game.entity.component.TransformComponent;
import com.paradigmas.game.world.World;

public class MovimentSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<RigidBodyComponent> mRigidBody;
    private ComponentMapper<CollidableComponent> mCollidable;
    private com.paradigmas.game.world.World world;

    public MovimentSystem(World world) {
        super(Aspect.all(TransformComponent.class, RigidBodyComponent.class));
        this.world = world;
    }


    @Override
    protected void process(int entityId) {
        TransformComponent cTransform = mTransform.get(entityId);
        RigidBodyComponent cRigidBody = mRigidBody.get(entityId);
        CollidableComponent cCollidable = mCollidable.get(entityId);

        float delta = super.world.getDelta();

        cTransform.position.mulAdd(cRigidBody.velocity, delta);//So + Vt
        cRigidBody.velocity.y += world.getGravity() * delta;

        if(cCollidable != null) {
            if (cTransform.position.y < world.getSeaLevel() * Bloco.TILE_SIZE) {
                cRigidBody.velocity.y = 0;
                cTransform.position.y = world.getSeaLevel() * Bloco.TILE_SIZE;

                cCollidable.onGround = true;
            }
        }
        else{
            cCollidable.onGround = false;
        }
    }
}
