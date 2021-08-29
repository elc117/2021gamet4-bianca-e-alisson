package com.paradigmas.game.entity.system;

import com.paradigmas.game.bloco.Bloco;
import com.paradigmas.game.dictionary.Blocos;
import com.paradigmas.game.world.World;

public class MapsMakerSystem { // TODO: extends IteratingSystem
    private final World world;
    private final int[][][] map;

    public MapsMakerSystem (World world, int[][][] map) {
        this.world = world;
        this.map = map;
    }

    /** getHeight() = SCREEN_WIDTH/TILE_SIZE = 840/54 = 35. <p>
     * eixo x vai de 0 a 34. <p>
     * getHeight() = 21. <p>
     * eixo Y vai de 0 a 20.
     */

    // Construção do mapa
    public int[][][] createMap(int cont) {
        /** Qualquer valor de x maior que 34, vai dar erro,
         *  ou valores de y maiores que 20 */

        /** TODO: Mudar todas as coordenadas para o seguinte formato:
             * N pode ser feito a partir de (quero que inicie no 3 bloco em x) n = MAX_X-3
             * (por começar em 0, a terceira posição é o 4º bloco, mas a parede equilibra isso.)
             * world.getHeight()-n = 34 - 31 = 3
             *
             * world.getHeight()-(MAX_X-3)
             *
             * gera_ParedeByCoord(world.getHeight()-n, world.getHeight()-n, world.getWidth()-n. ...)
             *
             * gera_PlataformaByCoord(world.getWidth()-n, world.getWidth()-n, world.getHeight()-n, ...)
             *
             * preenche(world.getWidth()-n, world.getHeight()-n, ...)
             *
             * Assim, o mapa fica mais dinamico, se a gente mudar o tamanho da tela de novo, vai permanecer na mesma proporção!
        */

        final int MAX_Y = world.getHeight()-1;
        final int MAX_X = world.getWidth()-1;

        // Básico
        gera_Fundo();   // preenche a camada 0 com terra e a 1 com ar
        gera_ground(Blocos.Mid_1); // faz o chão básico
        gera_ParedeByCoord(0, MAX_Y-1, 0, Blocos.Ground_Right_1, Blocos.Wall_Mid_Right_1); // parede direita
        gera_ParedeByCoord(0, MAX_Y-1, MAX_X-1, Blocos.Ground_Left_1, Blocos.Wall_Mid_Left_1); // parede esquerda


        if (cont == 2) {
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

            // plataforma flutuante primeiro nível
            gera_PlataformaByCoord(14, 18, 4, Blocos.Platt_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Right_1);

            // plataforma segundo nível
            gera_PlataformaByCoord(1, world.getWidth()-8, 7, Blocos.Ground_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Right_1);
            gera_PlataformaByCoord(world.getWidth()-4, world.getWidth()-2, 6, Blocos.Platt_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_PlataformaByCoord(1,5, 9, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Right_1);
            gera_PlataformaByCoord(8, 11, 11, Blocos.Platt_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Right_1);


            // plataforma terceiro nível
            gera_PlataformaByCoord(15, 30, 12, Blocos.Platt_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_PlataformaByCoord(30, world.getWidth()-9, 11, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Right_1);
            gera_PlataformaByCoord(world.getWidth()-6, world.getWidth()-2, 12, Blocos.Platt_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_PlataformaByCoord(world.getWidth()-3, world.getWidth()-2, 13, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

            // plataforma quarto nível
            gera_PlataformaByCoord(6, world.getWidth()-6, 16, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Right_1);
            gera_PlataformaByCoord(23, 32, 17, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_PlataformaByCoord(24, 31, 18, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_PlataformaByCoord(1, 6, 17, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_PlataformaByCoord(1, 6, 18, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_PlataformaByCoord(1, 6, 19, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

            // plataforma quinto nível
            /** TODO: ESSA PLATAFORMA ESTÁ FORA DO MAPA*/
            gera_PlataformaByCoord(9, 12, 22, Blocos.Platt_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_ParedeByCoord(19, 22, 13, Blocos.Ground_Right_1, Blocos.Wall_Mid_Right_1);
            gera_PlataformaByCoord(14, 16, 19, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_ParedeByCoord(19, 21, 17, Blocos.Ground_Right_1, Blocos.Wall_Mid_Right_1);
            gera_PlataformaByCoord(18, world.getWidth()-1, 21, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);


            map[5][4][1] = Blocos.getIdByBloco(Blocos.Codigo_1);
        }
        else if (cont == 1) {

            // chao
            gera_PlataformaByCoord(1, world.getWidth()-2, 1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

            // plataforma primeiro nível
            gera_PlataformaByCoord(1, 5, 6, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Right_1);
            gera_PlataformaByCoord(8, 10, 4, Blocos.Platt_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_ParedeByCoord(4, 8, 10, Blocos.Ground_Right_1, Blocos.Wall_Mid_Right_1);
            gera_PlataformaByCoord(world.getWidth()-7, world.getWidth()-5, 4, Blocos.Platt_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Right_1);

            // plataforma segundo nível
            gera_ParedeByCoord(3, 4, 15, Blocos.Wall_Mid_Right_1, Blocos.Wall_Mid_Right_1);
            gera_PlataformaByCoord(15, 25, 4, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_ParedeByCoord(5, 6, 25, Blocos.Wall_Mid_Right_1, Blocos.Wall_Mid_Right_1);
            gera_PlataformaByCoord(25, world.getWidth()-2, 6, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);



            // plataforma terceiro nível
            gera_PlataformaByCoord(2, world.getWidth()-4, 9, Blocos.Platt_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Right_1);
            gera_ParedeByCoord(10, 18, world.getWidth()-8, Blocos.Wall_Mid_Right_1, Blocos.Wall_Mid_Right_1);
            gera_PlataformaByCoord(world.getWidth()-5, world.getWidth()-2, 11, Blocos.Platt_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_PlataformaByCoord(world.getWidth()-7, world.getWidth()-6, 14, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Right_1);
            gera_PlataformaByCoord(world.getWidth()-5, world.getWidth()-2, 16, Blocos.Platt_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);

            // plataforma quarto nível
            gera_ParedeByCoord(18, 22, world.getWidth()-18, Blocos.Wall_Mid_Right_1, Blocos.Mid_1);
            gera_PlataformaByCoord(world.getWidth()-17, world.getWidth()-9, 18, Blocos.Mid_1, Blocos.Mid_1, Blocos.Mid_1);
            gera_PlataformaByCoord(world.getWidth()-17, world.getWidth()-13, 19, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_PlataformaByCoord(world.getWidth()-17, world.getWidth()-13, 20, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_PlataformaByCoord(world.getWidth()-17, world.getWidth()-13, 21, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_PlataformaByCoord(world.getWidth()-3, world.getWidth()-2, 23, Blocos.Platt_Left_1, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1);
            gera_PlataformaByCoord(world.getWidth()-5, world.getWidth()-5, 21, Blocos.Platt_Mid_1, Blocos.Platt_Mid_1, Blocos.Platt_Right_1);


        }

        return map;
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