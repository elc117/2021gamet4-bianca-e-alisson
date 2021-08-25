package com.paradigmas.game.world;

import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.paradigmas.game.ParadigmasGame;
import com.paradigmas.game.bloco.Bloco;
import com.paradigmas.game.dictionary.Blocos;
import com.paradigmas.game.entity.EntitiesFactory;
import com.paradigmas.game.entity.system.MovimentSystem;
import com.paradigmas.game.entity.system.PlayerControllerSystem;
import com.paradigmas.game.entity.system.SpriteRenderSystem;
import com.paradigmas.game.entity.system.TileRenderSystem;

import net.namekdev.entity_tracker.EntityTracker;
import net.namekdev.entity_tracker.ui.EntityTrackerMainWindow;

import java.util.Random;

public class World {

    private EntityTrackerMainWindow entityTrackerWindow;
    private int[][][] map = new int[80][45][2];
    private com.artemis.World artemisWorld;

    private int player;
    private int seaLevel = 7;
    private float gravity = -570;
    private EntitiesFactory entitiesFactory;

    public World(OrthographicCamera camera) {
        WorldConfigurationBuilder worldConfigBuilder = new WorldConfigurationBuilder()
                .with(new PlayerControllerSystem())
                .with(new MovimentSystem(this))
                .with(new TileRenderSystem(this, camera))
                .with(new SpriteRenderSystem(camera));

        if (ParadigmasGame.DEBUG) {
            entityTrackerWindow = new EntityTrackerMainWindow(false, false);
            worldConfigBuilder.with(new EntityTracker(entityTrackerWindow));
        }

        WorldConfiguration config = worldConfigBuilder.build();
        artemisWorld = new com.artemis.World(config);

        entitiesFactory = new EntitiesFactory();
        artemisWorld.inject(entitiesFactory);

        player = entitiesFactory.createPlayer(artemisWorld, 0, getHeight() * Bloco.TILE_SIZE);
    }

    public void regenerate() {
        Random random = new Random();


        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                for (int i = 0; i < getLayer(); i++) {
                    Bloco bloco = null;

                    if (y < (getSeaLevel() - 5)) {
                        bloco = Blocos.OBSIDIAN;
                    } else if (y < (getSeaLevel() - 2)) {
                        bloco = Blocos.COBBLESTONE;
                    } else if (y < getSeaLevel()) {
                        bloco = Blocos.DIRT;
                    } else {
                        if (i == 0) {
                            bloco = Blocos.DIRT;
                        } else {
                            bloco = random.nextInt(100) < 10 ? Blocos.DIRT : Blocos.AIR;
                        }
                    }

                    map[x][y][i] = Blocos.getIdByBloco(bloco);
                }
            }
        }

        map[10][8][1] = Blocos.getIdByBloco(Blocos.DIRT);
    }

    public void update(float delta) {
        artemisWorld.setDelta(delta);
        artemisWorld.process();
    }

    public Bloco getBloco(int x, int y, int layer) {
        return Blocos.getBlocoById(map[x][y][layer]);
    }

    public Bloco getBloco(float x, float y, int layer) {
        return Blocos.getBlocoById(map[worldToMap(x)][worldToMap(y)][layer]);
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

    public int getPlayer() {
        return player;
    }

    public float getGravity() {
        return gravity;
    }

    public com.artemis.World getArtemisWorld() {
        return artemisWorld;
    }

    public EntityTrackerMainWindow getEntityTrackerWindow() {
        return entityTrackerWindow;
    }

    public void dispose() {
        artemisWorld.dispose();
    }

    public static float mapToWorld(int mapCoordinate) {
        return mapCoordinate * Bloco.TILE_SIZE;
    }

    public static int worldToMap(float worldCoordinate) {
        return (int) (worldCoordinate / Bloco.TILE_SIZE);
    }

    public boolean isValid(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }
}
