package com.paradigmas.game.world;

import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.paradigmas.game.ParadigmasGame;
import com.paradigmas.game.bloco.Bloco;
import com.paradigmas.game.dictionary.Blocos;
import com.paradigmas.game.entity.EntitiesFactory;
import com.paradigmas.game.entity.system.CollisionDebugSystem;
import com.paradigmas.game.entity.system.MovimentSystem;
import com.paradigmas.game.entity.system.PlayerControllerSystem;
import com.paradigmas.game.entity.system.SpriteRenderSystem;
import com.paradigmas.game.entity.system.TileRenderSystem;

import net.namekdev.entity_tracker.EntityTracker;
import net.namekdev.entity_tracker.ui.EntityTrackerMainWindow;

import java.util.Random;

public class World {

    public static final int BG = 0; //BackGround (layer 0)
    public static final int FG = 1; //ForeGround (layer 1)

    private EntityTrackerMainWindow entityTrackerWindow;
    private final int[][][] map = new int[80][45][2];
    private final com.artemis.World artemisWorld;

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
            worldConfigBuilder.with(new CollisionDebugSystem(camera, this));

            if (Gdx.app.getType().equals(Application.ApplicationType.Desktop)) {
                entityTrackerWindow = new EntityTrackerMainWindow(false, false);
                worldConfigBuilder.with(new EntityTracker(entityTrackerWindow));
            }
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
                    Bloco bloco;

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

        //map[10][8][1] = Blocos.getIdByBloco(Blocos.DIRT);
    }

    public void update(float delta) {
        artemisWorld.setDelta(delta);
        artemisWorld.process();
    }

    // pega um bloco a partir das coodenadas do mapa
    public Bloco getBloco(int x, int y, int layer) {
        return Blocos.getBlocoById(map[x][y][layer]);
    }

    // pega um bloco a partir das coodenadas do world
    public Bloco getBloco(float x, float y, int layer) {
        return getBloco(worldToMap(x), worldToMap(y), layer);
    }

    // recebe uma coordenada do mapa e retorna se aquele bloco é sólido.
    public boolean isSolid(int x, int y) {
        //valida          pega o bloco e verifica se é solido
        return isValid(x, y) && getBloco(x, y, FG).isSolid();
    }

    // Recebe uma coord do world -> converte pra mapa e verifica se é solido.
    public boolean isSolid(float x, float y) {
        return isSolid(worldToMap(x), worldToMap(y));
    }

    // Recebe uma coord, se for solido, instanciamos um rectangle nesse ponto do wolrd,
    // com as dimenções de um bloco.
    public Rectangle getTitleRectangle(int x, int y) {
        Rectangle rectangle = null;

        if (isSolid(x, y)) {
            rectangle = new Rectangle(mapToWorld(x), mapToWorld(y), Bloco.TILE_SIZE, Bloco.TILE_SIZE);
        }

        return rectangle;
    }

    public void getTilesRectangle(float startX, float startY, float endX, float endY, Array<Rectangle> tilesRectangles) {
        getTilesRectangle(worldToMap(startX), worldToMap(startY), worldToMap(endX), worldToMap(endY), tilesRectangles);
    }

    // Coloca dentro do Array passado, todos os blocos colidiveis dentro do espaço definido.
    public void getTilesRectangle(int startX, int startY, int endX, int endY, Array<Rectangle> tilesRectangles) {
        Rectangle rectangle;
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                rectangle = getTitleRectangle(x, y);

                if (rectangle != null) {
                    tilesRectangles.add(rectangle);
                }
            }
        }
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

    // free
    public void dispose() {
        artemisWorld.dispose();
    }

    // conversão de coordenadas do mapa em coordenadas do world
    public static float mapToWorld(int mapCoordinate) {
        return mapCoordinate * Bloco.TILE_SIZE;
    }

    // conversão de coordenadas do world em coordenadas do mapa
    public static int worldToMap(float worldCoordinate) {
        return (int) (worldCoordinate / Bloco.TILE_SIZE);
    }

    // valida se a coordenada está dentro do espaço de jogo
    public boolean isValid(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }
}
