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

public class World {

    public static final int BG = 0; //BackGround (layer 0)
    public static final int FG = 1; //ForeGround (layer 1)

    private EntityTrackerMainWindow entityTrackerWindow;
    private final int[][][] map = new int[720 / 24][528 / 24][2]; // ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT
    private final com.artemis.World artemisWorld;

    private int player;
    private int seaLevel = 7;
    private float gravity = -570;
    private EntitiesFactory entitiesFactory;

    // construtor
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

        // TODO: definir um lugar certo para o spawn do personagem
        player = entitiesFactory.createPlayer(artemisWorld, Bloco.TILE_SIZE, getHeight() * Bloco.TILE_SIZE);
    }

    public void regenerate() {
        // Básico
        gera_Fundo();   // preenche a camada 0 com terra e a 1 com ar
        gera_ground(Blocos.Mid_1); // faz o chão básico
        gera_ParedeByCoord(0, getHeight()-1, 0, Blocos.Ground_Right_1, Blocos.Wall_Mid_Right_1); // parede direita
        gera_ParedeByCoord(0, getHeight()-1, getWidth()-1, Blocos.Ground_Left_1, Blocos.Wall_Mid_Left_1); // parede esquerda

        // plataforma (1-10, 3) -> (início-fim; altura)
        gera_PlataformaByCoord(1, 10, 3, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

        // parede (1-3, 11) -> (base-fim; posição em x)
        gera_ParedeByCoord(1, 3, 11, Blocos.Ground_Right_1, Blocos.Wall_Mid_Right_1);

        gera_PlataformaByCoord(12, 20, 1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_ParedeByCoord(1, 3, 21, Blocos.Ground_Left_1, Blocos.Wall_Mid_Left_1);

        gera_PlataformaByCoord(22, getWidth()-2, 3, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

        // Preenche areas vazias até o chão
        // TODO: Alterar para escolher até onde preencher.
        preenche(22, 2, Blocos.Mid_1);
        preenche(1, 3, Blocos.Mid_1);

        gera_PlataformaByCoord(14, 18, 4, Blocos.Platt_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Right_1);

        gera_PlataformaByCoord(22, getWidth()-2, 6, Blocos.Ground_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
    }

    // Varre o mapa colocando DIRT na layer 0 e AIR na 1.
    private void gera_Fundo() {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                for (int i = 0; i < getLayer(); i++) {
                    Bloco bloco  = Blocos.AIR;

                    if (i == 0) {
                        bloco = Blocos.DIRT;
                    }

                    map[x][y][i] = Blocos.getIdByBloco(bloco);
                }
            }
        }
    }

    // Faz uma linha na parte inferior da tela, exceto nos 2 cantos, com blocos de preenchimento,
    // formando um chão base
    private void gera_ground(Bloco bloco) {
        for (int x = 1; x < getWidth()-1; x++) {
            map[x][0][1] = Blocos.getIdByBloco(bloco);
        }
    }

    // Informe (em x) as coordenadas de inicio, fim e altura da plataforma
    // e a textura que quer colocar em cada ponta e no meio.
    private void gera_PlataformaByCoord(int startX, int endX, int height, Bloco Left, Bloco Mid, Bloco Right) {
        for (int x = startX; x <= endX; x++) {
            Bloco bloco  = Mid;

            if (x == startX) {
                bloco = Left;
            } else if (x == endX) {
                bloco = Right;
            }

            map[x][height][1] = Blocos.getIdByBloco(bloco);
        }
    }

    // Informe (em y) as coordenadas de inicio, fim e posição em x da parede
    // e a textura que quer colocar no top e no restante.
    private void gera_ParedeByCoord(int startY, int endY, int x, Bloco Top, Bloco Mid) {
        for (int y = startY; y <= endY; y++) {
            Bloco bloco = Mid;

            if (y == endY) {
                bloco = Top;
            }

            map[x][y][1] = Blocos.getIdByBloco(bloco);
        }
    }

    // Informe a primeira coordenada de um espaço vazio (cercado de blocos) que será preenchido,
    // por enquanto, até o chão. TODO: mudar isso.
    private void preenche(int x, int y, Bloco bloco) {
        for(; y > 0; y--) {
            int xx = x;
            while(Blocos.getBlocoById(map[xx][y][1]) == Blocos.AIR) {
                map[xx][y][1] = Blocos.getIdByBloco(bloco);
                xx  ++;
            }
        }
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
