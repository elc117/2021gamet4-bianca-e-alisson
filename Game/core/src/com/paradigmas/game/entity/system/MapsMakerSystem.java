package com.paradigmas.game.entity.system;

import com.paradigmas.game.bloco.Bloco;
import com.paradigmas.game.dictionary.Blocos;
import com.paradigmas.game.world.World;

public class MapsMakerSystem {  // TODO: extends IteratingSystem
    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private static final int LEVEL_3 = 3;
    private static final int LEVEL_4 = 4;
    private static final int LEVEL_5 = 5;
    private static final int LEVEL_6 = 6;

    private final int[][][] map;

    private final int MAX_Y;
    private final int MAX_X;

    public MapsMakerSystem (int[][][] map, int maX, int maY) {
        this.map = map;
        this.MAX_X = maX;
        this.MAX_Y = maY;
    }

    /** getHeight() = SCREEN_WIDTH/TILE_SIZE = 840/54 = 35. <p>
     * eixo x vai de 0 a 34. <p>
     * getHeight() = 21. <p>
     * eixo Y vai de 0 a 20.
     */

    // Construção do mapa
    public int[][][] createMap(int level) {
        /* Qualquer valor de x maior que 34, vai dar erro,
           ou valores de y maiores que 20 */

        // Básico
        gera_Fundo();   // preenche a camada 0 com terra e a 1 com ar
        gera_ground(Blocos.Mid_1); // faz o chão básico
        gera_ParedeByCoord(0, MAX_Y-1, 0, Blocos.Ground_Right_1, Blocos.Wall_Mid_Right_1); // parede direita
        gera_ParedeByCoord(0, MAX_Y-1, MAX_X-1, Blocos.Ground_Left_1, Blocos.Wall_Mid_Left_1); // parede esquerda

        switch (level)
        {
            case LEVEL_1:
                mapa_level_1();
                World.quantObjetivos = 5;

                break;

            case LEVEL_2:
                mapa_level_2();
                World.quantObjetivos = 5;
                break;

            case LEVEL_3:
                mapa_level_3();
                World.quantObjetivos = 5;
                break;

            case LEVEL_4:
                mapa_level_4();
                break;

            case LEVEL_5:
                mapa_level_5();
                break;

            case LEVEL_6:
                mapa_level_6();
                break;
        }

        return map;
    }


    // FERRAMENTAS
    // Varre o mapa colocando DIRT na layer 0 e AIR na 1.
    private void gera_Fundo() {
        for (int x = 0; x < MAX_X; x++) {
            for (int y = 0; y < MAX_Y; y++) {
                for (int i = 0; i < 2; i++) {
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
        for (int x = 1; x < MAX_X; x++) {
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
            while(Blocos.AIR == Blocos.getBlocoById(map[xx][y][1])) {
                map[xx][y][1] = Blocos.getIdByBloco(bloco);
                map[xx][y][0] = Blocos.getIdByBloco(bloco);
                xx++;
            }
        }
    }


    /// MAPAS
    private void mapa_level_1() {
       
        // primeiro nivel
        gera_PlataformaByCoord(MAX_X-(MAX_X-14), MAX_X-(MAX_X-15), MAX_Y-(MAX_Y-1), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-18), MAX_X-(MAX_X-19), MAX_Y-(MAX_Y-1), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_ParedeByCoord(MAX_Y-(MAX_Y-1), MAX_Y-(MAX_Y-2), MAX_X-(MAX_X-28), Blocos.Ground_Left_1, Blocos.Wall_Mid_Left_1);
        gera_ParedeByCoord(MAX_Y-(MAX_Y-1), MAX_Y-(MAX_Y-2), MAX_X-(MAX_X-29), Blocos.Ground_Right_1, Blocos.Wall_Mid_Right_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-30), MAX_X-(MAX_X-31), MAX_Y-(MAX_Y-1), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

        map[MAX_X-2][MAX_Y-(MAX_Y-1)][1] = Blocos.getIdByBloco(Blocos.Codigo_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-1), MAX_X-(MAX_X-1), MAX_Y-(MAX_Y-3), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

        // segundo nivel
        gera_PlataformaByCoord(MAX_X-(MAX_X-3), MAX_X-1, MAX_Y-(MAX_Y-4), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-18), MAX_X-(MAX_X-20), MAX_Y-(MAX_Y-7), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-24), MAX_X-(MAX_X-25), MAX_Y-(MAX_Y-6), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_ParedeByCoord(MAX_Y-(MAX_Y-5), MAX_Y-(MAX_Y-6), MAX_X-(MAX_X-30), Blocos.Ground_Left_1, Blocos.Wall_Mid_Left_1);
        gera_ParedeByCoord(MAX_Y-(MAX_Y-5), MAX_Y-(MAX_Y-6), MAX_X-(MAX_X-31), Blocos.Ground_Right_1, Blocos.Wall_Mid_Right_1);

        map[MAX_X-(MAX_X-19)][MAX_Y-(MAX_Y-8)][1] = Blocos.getIdByBloco(Blocos.Codigo_1);
        gera_PlataformaByCoord(MAX_X-2, MAX_X-2, MAX_Y-(MAX_Y-7), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

        // terceiro nivel
        gera_PlataformaByCoord(MAX_X-(MAX_X-15), MAX_X-(MAX_X-31), MAX_Y-(MAX_Y-9), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-1), MAX_X-(MAX_X-15), MAX_Y-(MAX_Y-8), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_ParedeByCoord(MAX_Y-(MAX_Y-9), MAX_Y-(MAX_Y-12), MAX_X-(MAX_X-3), Blocos.Ground_Left_1, Blocos.Wall_Mid_Left_1);
        gera_ParedeByCoord(MAX_Y-(MAX_Y-9), MAX_Y-(MAX_Y-12), MAX_X-(MAX_X-4), Blocos.Ground_Right_1, Blocos.Wall_Mid_Right_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-2), MAX_X-(MAX_X-2), MAX_Y-(MAX_Y-11), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-6), MAX_X-(MAX_X-8), MAX_Y-(MAX_Y-10), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-17), MAX_X-(MAX_X-18), MAX_Y-(MAX_Y-12), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

        map[MAX_X-(MAX_X-1)][MAX_Y-(MAX_Y-9)][1] = Blocos.getIdByBloco(Blocos.Codigo_1);
        gera_PlataformaByCoord(MAX_X-2, MAX_X-2, MAX_Y-(MAX_Y-11), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

        // quarto nivel
        gera_PlataformaByCoord(MAX_X-(MAX_X-1), MAX_X-(MAX_X-14), MAX_Y-(MAX_Y-14), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_ParedeByCoord(MAX_Y-(MAX_Y-14), MAX_Y-(MAX_Y-16), MAX_X-(MAX_X-14), Blocos.Ground_Right_1, Blocos.Wall_Mid_Right_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-20), MAX_X-2, MAX_Y-(MAX_Y-14), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-1), MAX_X-(MAX_X-1), MAX_Y-(MAX_Y-17), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_ParedeByCoord(MAX_Y-(MAX_Y-17), MAX_Y-(MAX_Y-19), MAX_X-(MAX_X-2), Blocos.Ground_Right_1, Blocos.Wall_Mid_Right_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-5), MAX_X-(MAX_X-6), MAX_Y-(MAX_Y-18), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-10), MAX_X-(MAX_X-11), MAX_Y-(MAX_Y-17), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-17), MAX_X-(MAX_X-18), MAX_Y-(MAX_Y-16), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-21), MAX_X-(MAX_X-21), MAX_Y-(MAX_Y-18), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-25), MAX_X-(MAX_X-25), MAX_Y-(MAX_Y-16), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-28), MAX_X-(MAX_X-28), MAX_Y-(MAX_Y-18), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-30), MAX_X-(MAX_X-31), MAX_Y-(MAX_Y-17), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-28), MAX_X-(MAX_X-28), MAX_Y-(MAX_Y-18), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-2, MAX_X-2, MAX_Y-(MAX_Y-18), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

        map[MAX_X-2][MAX_Y-(MAX_Y-19)][1] = Blocos.getIdByBloco(Blocos.Codigo_1);
        map[MAX_X-(MAX_X-1)][MAX_Y-(MAX_Y-18)][1] = Blocos.getIdByBloco(Blocos.Codigo_1);


    }


    private void mapa_level_2() {

        // primeiro nivel
        gera_PlataformaByCoord(MAX_X-(MAX_X-1), MAX_X-(MAX_X-2), MAX_Y-(MAX_Y-3), Blocos.Mid_1, Blocos.Mid_1, Blocos.Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-1), MAX_X-(MAX_X-2), MAX_Y-(MAX_Y-4), Blocos.Mid_1, Blocos.Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-1), MAX_X-(MAX_X-1), MAX_Y-(MAX_Y-5), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-8), MAX_X-(MAX_X-9), MAX_Y-(MAX_Y-2), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

        map[MAX_X-(MAX_X-31)][MAX_Y-(MAX_Y-3)][1] = Blocos.getIdByBloco(Blocos.Codigo_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-5), MAX_X-(MAX_X-5), MAX_Y-(MAX_Y-2), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

        gera_PlataformaByCoord(MAX_X-(MAX_X-12), MAX_X-(MAX_X-16), MAX_Y-(MAX_Y-4), Blocos.Mid_1, Blocos.Mid_1, Blocos.Mid_1);
        gera_ParedeByCoord(MAX_Y-(MAX_Y-1), MAX_Y-(MAX_Y-3), MAX_X-(MAX_X-12), Blocos.Mid_1, Blocos.Mid_1);
        gera_ParedeByCoord(MAX_Y-(MAX_Y-1), MAX_Y-(MAX_Y-3), MAX_X-(MAX_X-16), Blocos.Mid_1, Blocos.Mid_1);
        preenche(MAX_X-(MAX_X-13), MAX_Y-(MAX_Y-3), Blocos.Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-19), MAX_X-(MAX_X-20), MAX_Y-(MAX_Y-2), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

        gera_PlataformaByCoord(MAX_X-(MAX_X-23), MAX_X-(MAX_X-27), MAX_Y-(MAX_Y-4), Blocos.Mid_1, Blocos.Mid_1, Blocos.Mid_1);
        gera_ParedeByCoord(MAX_Y-(MAX_Y-1), MAX_Y-(MAX_Y-3), MAX_X-(MAX_X-23), Blocos.Mid_1, Blocos.Mid_1);
        gera_ParedeByCoord(MAX_Y-(MAX_Y-1), MAX_Y-(MAX_Y-3), MAX_X-(MAX_X-27), Blocos.Mid_1, Blocos.Mid_1);
        preenche(MAX_X-(MAX_X-24), MAX_Y-(MAX_Y-3), Blocos.Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-30), MAX_X-(MAX_X-31), MAX_Y-(MAX_Y-2), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

        // segundo nivel
        gera_PlataformaByCoord(MAX_X-(MAX_X-2), MAX_X-(MAX_X-24), MAX_Y-(MAX_Y-8), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-25), MAX_X-2, MAX_Y-(MAX_Y-8), Blocos.Mid_1, Blocos.Mid_1, Blocos.Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-25), MAX_X-(MAX_X-28), MAX_Y-(MAX_Y-9), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-29), MAX_X-2, MAX_Y-(MAX_Y-9), Blocos.Mid_1, Blocos.Mid_1, Blocos.Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-29), MAX_X-2, MAX_Y-(MAX_Y-10), Blocos.Mid_1, Blocos.Mid_1, Blocos.Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-29), MAX_X-2, MAX_Y-(MAX_Y-11), Blocos.Mid_1, Blocos.Mid_1, Blocos.Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-29), MAX_X-2, MAX_Y-(MAX_Y-12), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-1), MAX_X-(MAX_X-2), MAX_Y-(MAX_Y-12), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-1), MAX_X-(MAX_X-1), MAX_Y-(MAX_Y-13), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-4), MAX_X-(MAX_X-5), MAX_Y-(MAX_Y-11), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-8), MAX_X-(MAX_X-9), MAX_Y-(MAX_Y-10), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-12), MAX_X-(MAX_X-14), MAX_Y-(MAX_Y-13), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

        map[MAX_X-(MAX_X-32)][MAX_Y-(MAX_Y-14)][1] = Blocos.getIdByBloco(Blocos.Codigo_1);
        map[MAX_X-(MAX_X-13)][MAX_Y-(MAX_Y-14)][1] = Blocos.getIdByBloco(Blocos.Codigo_1);

        gera_PlataformaByCoord(MAX_X-(MAX_X-17), MAX_X-(MAX_X-19), MAX_Y-(MAX_Y-10), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

        // terceiro nivel
        gera_PlataformaByCoord(MAX_X-(MAX_X-2), MAX_X-(MAX_X-3), MAX_Y-(MAX_Y-18), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-3), MAX_X-(MAX_X-6), MAX_Y-(MAX_Y-15), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-6), MAX_X-(MAX_X-21), MAX_Y-(MAX_Y-16), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_ParedeByCoord(MAX_Y-(MAX_Y-13), MAX_Y-(MAX_Y-16), MAX_X-(MAX_X-21), Blocos.Ground_Left_1, Blocos.Wall_Mid_Left_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-6), MAX_X-(MAX_X-21), MAX_Y-(MAX_Y-16), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-22), MAX_X-(MAX_X-25), MAX_Y-(MAX_Y-13), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        gera_ParedeByCoord(MAX_Y-(MAX_Y-13), MAX_Y-(MAX_Y-17), MAX_X-(MAX_X-26), Blocos.Ground_Left_1, Blocos.Wall_Mid_Left_1);
        gera_PlataformaByCoord(MAX_X-(MAX_X-26), MAX_X-(MAX_X-30), MAX_Y-(MAX_Y-17), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

        map[MAX_X-2][MAX_Y-(MAX_Y-20)][1] = Blocos.getIdByBloco(Blocos.Codigo_1);
        map[MAX_X-(MAX_X-2)][MAX_Y-(MAX_Y-19)][1] = Blocos.getIdByBloco(Blocos.Codigo_1);

        gera_PlataformaByCoord(MAX_X-3, MAX_X-2, MAX_Y-(MAX_Y-19), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
        
    }

    private void mapa_level_3() {
        // plataforma (1-10, 3) -> (início-fim; altura)
        gera_PlataformaByCoord(MAX_X-(MAX_X-1), MAX_X-(MAX_X-10), MAX_Y-(MAX_Y-3), Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Ground_Right_1);

        // parede (1-3, 11) -> (base-fim; posição em x)
        gera_ParedeByCoord(MAX_Y-(MAX_Y-1), MAX_X-(MAX_X-3), MAX_X-(MAX_X-11), Blocos.Ground_Right_1, Blocos.Wall_Mid_Right_1);

        map[9][4][1] = Blocos.getIdByBloco(Blocos.Codigo_1);
        map[16][4][1] = Blocos.getIdByBloco(Blocos.Codigo_1);
        map[20][4][1] = Blocos.getIdByBloco(Blocos.Codigo_1);
        map[11][4][1] = Blocos.getIdByBloco(Blocos.Codigo_1);
        map[3][7][1] = Blocos.getIdByBloco(Blocos.Codigo_1);
        //map[16][7][1] = Blocos.getIdByBloco(Blocos.Codigo_1);
    }

    private void mapa_level_4() {
    }

    private void mapa_level_5() {
    }

    private void mapa_level_6() {
    }
}