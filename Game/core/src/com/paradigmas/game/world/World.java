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
import com.paradigmas.game.entity.system.MapsMakerSystem;
import com.paradigmas.game.entity.system.MovimentSystem;
import com.paradigmas.game.entity.system.PlayerControllerSystem;
import com.paradigmas.game.entity.system.SpriteRenderSystem;
import com.paradigmas.game.entity.system.TileRenderSystem;

import net.namekdev.entity_tracker.EntityTracker;
import net.namekdev.entity_tracker.ui.EntityTrackerMainWindow;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class World {

    private EntityTrackerMainWindow entityTrackerWindow;
    private int[][][] map = new int[ParadigmasGame.SCREEN_WIDTH / 24][(ParadigmasGame.SCREEN_HEIGHT / 24)+2][2];
    private final com.artemis.World artemisWorld;
    private final EntitiesFactory entitiesFactory;
    private final float gravity = -570;
    private final int player;
    public static int quantObjetivos;
    public static long tempo;
    Graphics2D graphic;

    // construtor
    public World(OrthographicCamera camera) {
        WorldConfigurationBuilder worldConfigBuilder = new WorldConfigurationBuilder()
                .with(new PlayerControllerSystem(this))
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

        BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
        graphic = image.createGraphics();


        //public void paint(Graphics g) { Graphics2D g2d = (Graphics2D)g.create(); g2d.draw(image,x,y,null); g2d.dispose(); }

        // TODO: definir um lugar certo para o spawn do personagem
        player = entitiesFactory.createPlayer(artemisWorld, 2 * Bloco.TILE_SIZE, 6 * Bloco.TILE_SIZE); // getHeight()
    }

    public void regenerate(int level) {
        map = new MapsMakerSystem(map, getWidth(), getHeight()).createMap(level);
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

    // recebe uma coordenada do mapa e retorna se aquele bloco ?? s??lido.
    public boolean isSolid(int x, int y) {
        //valida          pega o bloco e verifica se ?? solido
        return isValid(x, y) && getBloco(x, y, 1).isSolid();
    }

    // Recebe uma coord do world -> converte pra mapa e verifica se ?? solido.
    public boolean isSolid(float x, float y) {
        return isSolid(worldToMap(x), worldToMap(y));
    }

    // Recebe uma coord, se for solido, instanciamos um rectangle nesse ponto do wolrd,
    // com as dimen????es de um bloco.
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

    // Coloca dentro do Array passado, todos os blocos colidiveis dentro do espa??o definido.
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

    // convers??o de coordenadas do mapa em coordenadas do world
    public static float mapToWorld(int mapCoordinate) {
        return mapCoordinate * Bloco.TILE_SIZE;
    }

    // convers??o de coordenadas do world em coordenadas do mapa
    public static int worldToMap(float worldCoordinate) {
        return (int) (worldCoordinate / Bloco.TILE_SIZE);
    }

    // valida se a coordenada est?? dentro do espa??o de jogo
    public boolean isValid(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }

    public int[][][] getMap() {
        return map;
    }
}
