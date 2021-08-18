package com.paradigmas.game.world;

import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.paradigmas.game.ParadigmasGame;
import com.paradigmas.game.bloco.Bloco;
import com.paradigmas.game.dictionary.Blocos;
import com.paradigmas.game.entity.EntitiesFactory;
import com.paradigmas.game.entity.system.MovimentSystem;
import com.paradigmas.game.entity.system.SpriteRenderSystem;
import com.paradigmas.game.entity.system.TileRenderSystem;

import net.namekdev.entity_tracker.EntityTracker;
import net.namekdev.entity_tracker.ui.EntityTrackerMainWindow;

public class World {

    private EntityTrackerMainWindow entityTrackerWindow;
    private int[][][] map = new int[80][45][2];
    private com.artemis.World world;

    private int player;
    private int seaLevel = 7;
    private float gravity = -576;
    private EntitiesFactory entitiesFactory;

    public int getPlayer() {
        return player;
    }

    public float getGravity() {
        return gravity;
    }

    public World(OrthographicCamera camera) {
        WorldConfigurationBuilder worldConfigBuilder = new WorldConfigurationBuilder()
                .with(new MovimentSystem(this))
                .with(new TileRenderSystem(this, camera))
                .with(new SpriteRenderSystem(camera));

        if (ParadigmasGame.DEBUG) {
            entityTrackerWindow = new EntityTrackerMainWindow(false, false);
            worldConfigBuilder.with(new EntityTracker(entityTrackerWindow));
        }

        WorldConfiguration config = worldConfigBuilder.build();
        world = new com.artemis.World(config);

        entitiesFactory = new EntitiesFactory();
        world.inject(entitiesFactory);

        player = entitiesFactory.createPlayer(world, 0, getHeight() * Bloco.TILE_SIZE);
    }

    public void regenerate() {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                for (int i = 0; i < getLayer(); i++) {
                    if (y < (getSeaLevel() - 5)) {
                        map[x][y][i] = Blocos.getIdByBloco(Blocos.OBSIDIAN);
                    } else if (y < (getSeaLevel() - 2)) {
                        map[x][y][i] = Blocos.getIdByBloco(Blocos.COBBLESTONE);
                    } else if (y < getSeaLevel()) {
                        map[x][y][i] = Blocos.getIdByBloco(Blocos.DIRT);
                    }
                }
            }
        }
    }

    public void update(float delta) {
        world.setDelta(delta);
        world.process();
    }

    public Bloco getBlocoByCord(int x, int y, int layer) {
        return Blocos.getBlocoById(map[x][y][layer]);
    }

    public int getWidth() {
        return map.length;
    }

    public int getHeight() {
        return map[0].length;
    }

    public int getLayer() {
        return map[0][0].length;
    }

    public int getSeaLevel() {
        return seaLevel;
    }

    public void dispose() {
        world.dispose();
    }

    public EntityTrackerMainWindow getEntityTrackerWindow() {
        return entityTrackerWindow;
    }

    public com.artemis.World getWorld(){
        return world;
    }
}
