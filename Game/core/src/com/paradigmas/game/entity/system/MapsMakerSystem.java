package com.paradigmas.game.entity.system;

import com.badlogic.gdx.utils.Array;
import com.paradigmas.game.bloco.Bloco;
import com.paradigmas.game.dictionary.Blocos;
import com.paradigmas.game.world.World;

public class MapsMakerSystem { // TODO: extends IteratingSystem
    private final World world;
    private final int[][][] map;
    private final Array<int[][][]> maps = new Array<>();

    public MapsMakerSystem (World world, int[][][] map) {
        this.world = world;
        this.map = map;
    }

    // Construção do mapa
    private void createMap(int cont) {
        // Básico
        gera_Fundo();   // preenche a camada 0 com terra e a 1 com ar
        gera_ground(Blocos.Mid_1); // faz o chão básico
        gera_ParedeByCoord(0, world.getHeight()-1, 0, Blocos.Ground_Right_1, Blocos.Wall_Mid_Right_1); // parede direita
        gera_ParedeByCoord(0, world.getHeight()-1, world.getWidth()-1, Blocos.Ground_Left_1, Blocos.Wall_Mid_Left_1); // parede esquerda


        if (cont == 1) {
            // plataforma (1-10, 3) -> (início-fim; altura)
            gera_PlataformaByCoord(1, 10, 3, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

            // parede (1-3, 11) -> (base-fim; posição em x)
            gera_ParedeByCoord(1, 3, 11, Blocos.Ground_Right_1, Blocos.Wall_Mid_Right_1);

            gera_PlataformaByCoord(12, 20, 1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_ParedeByCoord(1, 3, 21, Blocos.Ground_Left_1, Blocos.Wall_Mid_Left_1);

            gera_PlataformaByCoord(22, world.getWidth()-2, 3, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

            // Preenche areas vazias até o chão
            // TODO: Alterar para escolher até onde preencher.
            preenche(22, 2, Blocos.Mid_1);
            preenche(1, 3, Blocos.Mid_1);

            gera_PlataformaByCoord(14, 18, 4, Blocos.Platt_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Right_1);

            gera_PlataformaByCoord(22, world.getWidth()-2, 6, Blocos.Ground_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

            map[5][4][1] = Blocos.getIdByBloco(Blocos.Codigo_1);
        }
        else if (cont == 2) {
            // parede (1-3, 11) -> (base-fim; posição em x)
            gera_ParedeByCoord(1, 3, 11, Blocos.Ground_Right_1, Blocos.Wall_Mid_Right_1);

            gera_PlataformaByCoord(12, 20, 1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_ParedeByCoord(1, 3, 21, Blocos.Ground_Left_1, Blocos.Wall_Mid_Left_1);

            gera_PlataformaByCoord(22, world.getWidth()-2, 3, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

            // Preenche areas vazias até o chão
            // TODO: Alterar para escolher até onde preencher.
            preenche(22, 2, Blocos.Mid_1);
            preenche(1, 3, Blocos.Mid_1);

            gera_PlataformaByCoord(14, 18, 4, Blocos.Platt_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Right_1);

            gera_PlataformaByCoord(22, world.getWidth()-2, 6, Blocos.Ground_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        }

        maps.add(map);
    }

    public Array<int[][][]> createMaps() {
        createMap(2);
        createMap(1);

        return maps;
    }


    // FERRAMENTAS
    // Varre o mapa colocando DIRT na layer 0 e AIR na 1.
    private void gera_Fundo() {
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                for (int i = 0; i < world.getLayer(); i++) {
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
        for (int x = 1; x < world.getWidth()-1; x++) {
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
                map[xx][y][0] = Blocos.getIdByBloco(bloco);
                xx  ++;
            }
        }
    }
}