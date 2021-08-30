package com.paradigmas.game.dictionary;

import com.badlogic.gdx.utils.IntMap;
import com.paradigmas.game.bloco.Bloco;
import com.paradigmas.game.bloco.BlocoAir;
import com.paradigmas.game.bloco.BlocoCodigo;
import com.paradigmas.game.resource.Assets;

public class Blocos
{
    public static final IntMap<Bloco> REGISTRY = new IntMap<>();
    /// ID:
    private static int ID = 0;
    public static final int AIR_ID = ID++;
    public static final int DIRT_ID = ID++;
    public static final int COBBLESTONE_ID = ID++;
    public static final int OBSIDIAN_ID = ID++;

    public static final int Ground_Left_1_ID = ID++;
    public static final int Ground_Mid_1_ID = ID++;
    public static final int Ground_Right_1_ID = ID++;

    public static final int Ramp_Left_1_ID = ID++;
    //public static final int Ramp_Right_1_ID = ID++;

    public static final int Mid_1_ID = ID++;

    public static final int Platt_Left_1_ID = ID++;
    public static final int Platt_Mid_1_ID = ID++;
    public static final int Platt_Right_1_ID = ID++;

    public static final int Wall_Mid_Left_1_ID = ID++;
    public static final int Wall_Mid_Right_1_ID = ID++;


    public static final int Codigo_1_ID = ID++;

    // BLOCOS
    public static final Bloco AIR;
    public static final Bloco DIRT;
    public static final Bloco COBBLESTONE;
    public static final Bloco OBSIDIAN;

    public static final Bloco Ground_Left_1;
    public static final Bloco Ground_Mid_1;
    public static final Bloco Ground_Right_1;

    public static final Bloco Ramp_Left_1;
    //public static final Bloco Ramp_Right_1;

    public static final Bloco Mid_1;

    public static final Bloco Platt_Left_1;
    public static final Bloco Platt_Mid_1;
    public static final Bloco Platt_Right_1;

    public static final Bloco Wall_Mid_Left_1;
    public static final Bloco Wall_Mid_Right_1;

    public static final Bloco Codigo_1;

    // O mapa é uma matriz de ID's, passe uma posição da matriz para saber qual bloco está lá
    public static Bloco getBlocoById(int id)
    {
        return REGISTRY.get(id);
    }

    public static BlocoCodigo getBlocoCodigoById(int id)
    {
        if (REGISTRY.get(id) == Blocos.Codigo_1){
            return (BlocoCodigo) REGISTRY.get(id);
        }

        return null;
    }

    // Informe um bloco para saber seu ID
    public static int getIdByBloco(Bloco bloco)
    {
        return REGISTRY.findKey(bloco, true, AIR_ID);
    }

    private static Bloco register(int id, Bloco bloco) {
        REGISTRY.put(id, bloco);

        return bloco;
    }

    // Registrodos blocos com seus respectivos ID's e texturas.
    static
    {
        // TODO: mudar o backGround
        AIR = register(AIR_ID, new BlocoAir());
        DIRT = register(DIRT_ID, new Bloco(Assets.manager.get(Assets.dirt)));
        COBBLESTONE = register(COBBLESTONE_ID, new Bloco(Assets.manager.get(Assets.cobblestone)));
        OBSIDIAN = register(OBSIDIAN_ID, new Bloco(Assets.manager.get(Assets.obsidian)));

        // GROUND:
        Ground_Left_1 = register(Ground_Left_1_ID, new Bloco(Assets.manager.get(Assets.Ground_Left_1)));
        Ground_Mid_1 = register(Ground_Mid_1_ID, new Bloco(Assets.manager.get(Assets.Ground_Mid_1)));
        Ground_Right_1 = register(Ground_Right_1_ID, new Bloco(Assets.manager.get(Assets.Ground_Right_1)));

        // RAMP:
        Ramp_Left_1 = register(Ramp_Left_1_ID, new Bloco(Assets.manager.get(Assets.Ramp_Left_1)));
        //Ramp_Right_1 = register(Ramp_Right_1_ID, new Bloco(Assets.manager.get(Assets.Ramp_Right_1)));

        // Preenchimento
        Mid_1 = register(Mid_1_ID, new Bloco(Assets.manager.get(Assets.Mid_1)));

        // PLATAFFORM
        Platt_Left_1 = register(Platt_Left_1_ID, new Bloco(Assets.manager.get(Assets.Platt_Left_1)));
        Platt_Mid_1 = register(Platt_Mid_1_ID, new Bloco(Assets.manager.get(Assets.Platt_Mid_1)));
        Platt_Right_1 = register(Platt_Right_1_ID, new Bloco(Assets.manager.get(Assets.Platt_Right_1)));

        // WALL
        Wall_Mid_Right_1 = register(Wall_Mid_Right_1_ID, new Bloco(Assets.manager.get(Assets.Wall_Mid_Right_1)));
        Wall_Mid_Left_1 = register(Wall_Mid_Left_1_ID, new Bloco(Assets.manager.get(Assets.Wall_Mid_Left_1)));

        // OBJECTIVES
        Codigo_1 = register(Codigo_1_ID, new BlocoCodigo(Assets.manager.get(Assets.Codigo_1)));
    }
}
