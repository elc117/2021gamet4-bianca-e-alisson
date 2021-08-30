package com.paradigmas.game.entity.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.paradigmas.game.bloco.BlocoCodigo;
import com.paradigmas.game.dictionary.Blocos;
import com.paradigmas.game.entity.component.CollidableComponent;
import com.paradigmas.game.entity.component.RigidBodyComponent;
import com.paradigmas.game.entity.component.TransformComponent;
import com.paradigmas.game.world.World;

public class MovimentSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<RigidBodyComponent> mRigidBody;
    private ComponentMapper<CollidableComponent> mCollidable;
    private World world;
    Array<Rectangle> tiles = new Array<>();

    // Construtor
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

        cRigidBody.velocity.y += world.getGravity() * delta;

        // mCollidable != null
        if (mCollidable.has(entityId)) {
            cCollidable.onGround = false;
            cCollidable.onCeiling = false;
            cCollidable.onLeftWall = false;
            cCollidable.onRightWall = false;

            // Clona o vetor de velocidade
            final Vector2 velocity = new Vector2(cRigidBody.velocity);

            //scale
            // velocity*delta
            velocity.scl(delta);

            // pega a collision box do personagem
            final Rectangle rectangle = cCollidable.collisionBox;
            // Mantem a collision box na posição do personagem
            rectangle.setPosition(cTransform.position);

            ///Pegando os blocos que o personagem pode colidir:
            float startX, startY, endX, endY;

            // Definindo a area que vai ser passada para "getTilesRectangle",
            // para achar os blocos colidiveis ao redor:
            // (Na movimentação em X. Y está com  tamanho fixo. (de 2 blocos).)

            // está se deslocando em x, no sentido positivo. ->
            if (velocity.x > 0) {
                startX = endX = cTransform.position.x + rectangle.width + velocity.x;
            } else {
                // no sentido negativo. <-
                //                         a velocidade já tem um valor negativo, por isso +
                startX = endX = cTransform.position.x + velocity.x;
            }

            // definindo a altura da area a ser testada
            startY = cTransform.position.y;
            endY = cTransform.position.y + rectangle.height;

            // selecionando todos os blocos colidiveis
            tiles.clear();
            world.getTilesRectangle(startX, startY, endX, endY, tiles);

            rectangle.x += velocity.x;

            for(Rectangle tile : tiles) {
                // se colidir, a velocidade vai pra 0.
                if(rectangle.overlaps(tile)) {

                    int x = world.worldToMap(cTransform.position.x);
                    int y = world.worldToMap(cTransform.position.y);

                    if (velocity.x > 0) {
                        // Colidindo com uma parede à direita
                        BlocoCodigo bloco =  Blocos.getBlocoCodigoById(world.getMap()[x+1][y][1]);

                        if (bloco == Blocos.Codigo_1) {
                            world.getMap()[x+1][y][1] = 0;
                            World.quantObjetivos--;
                        } else {
                            cCollidable.onRightWall = true;
                            velocity.x = 0;
                        }
                    } else {
                        // Colidindo com uma parede à esquerda
                        BlocoCodigo bloco = Blocos.getBlocoCodigoById(world.getMap()[x-1][y][1]);

                        if (bloco == Blocos.Codigo_1) {
                            world.getMap()[x-1][y][1] = 0;
                            World.quantObjetivos--;
                        } else {
                            cCollidable.onLeftWall = true;
                            velocity.x = 0;
                        }
                    }

                    break;
                }
            }

            rectangle.x = cTransform.position.x;

            // Definindo a area que vai ser passada para "getTilesRectangle",
            // para achar os blocos colidiveis ao redor:
            // (Na movimentação em Y. X está com  tamanho fixo. (de 1 bloco).)
            if (velocity.y > 0) {
                startY = endY  =  cTransform.position.y + rectangle.height + velocity.y;
            } else {
                startY = endY = cTransform.position.y + velocity.y;
            }

            startX = cTransform.position.x;
            endX = cTransform.position.x + rectangle.width;

            tiles.clear();
            world.getTilesRectangle(startX, startY, endX, endY, tiles);

            rectangle.y += velocity.y;

            for(Rectangle tile : tiles) {
                // se colidir, a velocidade vai pra 0.
                if(rectangle.overlaps(tile)) {

                    int x = world.worldToMap(cTransform.position.x);
                    int y = world.worldToMap(cTransform.position.y);

                    // está subindo
                    if (velocity.y > 0) {
                        BlocoCodigo bloco =  Blocos.getBlocoCodigoById(world.getMap()[x][y][1]);

                        if (bloco == Blocos.Codigo_1) {
                            world.getMap()[x][y][1] = 0;
                            World.quantObjetivos--;
                        } else {
                            cTransform.position.y = tile.y - rectangle.height;

                            // Colidindo com o teto
                            cCollidable.onCeiling = true;
                            velocity.y = 0;
                        }
                    }
                    else {
                        // descendo
                        BlocoCodigo bloco =  Blocos.getBlocoCodigoById(world.getMap()[x][y-1][1]);

                        if (bloco == Blocos.Codigo_1) {
                            world.getMap()[x][y-1][1] = 0;
                            World.quantObjetivos--;
                        } else {
                            cTransform.position.y = tile.y + tile.height;

                            // Colidindo com o chão
                            cCollidable.onGround = true;
                            velocity.y = 0;
                        }
                    }

                    break;
                }
            }

            cTransform.position.add(velocity);
            // retorna pra velocidade normal
            velocity.scl(1 / delta);
            cRigidBody.velocity.set(velocity);

        } else {
            cTransform.position.mulAdd(cRigidBody.velocity, delta);//So + Vt
        }
    }
}
