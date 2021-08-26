package com.paradigmas.game.dictionary;

import com.badlogic.gdx.utils.IntMap;
import com.paradigmas.game.bloco.Bloco;
import com.paradigmas.game.bloco.BlocoAir;
import com.paradigmas.game.resource.Assets;

public class Blocos
{
    public static final IntMap<Bloco> REGISTRY = new IntMap<>();
    public static final int AIR_ID = 0;
    public static final int DIRT_ID = 1;
    public static final int COBBLESTONE_ID = 2;
    public static final int OBSIDIAN_ID = 3;

    public static final Bloco AIR;
    public static final Bloco DIRT;
    public static final Bloco COBBLESTONE;
    public static final Bloco OBSIDIAN;

    public static Bloco getBlocoById(int id)
    {
        return REGISTRY.get(id);
    }

    public static int getIdByBloco(Bloco bloco)
    {
        return REGISTRY.findKey(bloco, true, AIR_ID);
    }

    private static Bloco register(int id, Bloco bloco)
    {
        REGISTRY.put(id, bloco);

        return bloco;
    }

    static
    {
        AIR = register(AIR_ID, new BlocoAir());
        DIRT = register(DIRT_ID, new Bloco(Assets.manager.get(Assets.dirt)));
        COBBLESTONE = register(COBBLESTONE_ID, new Bloco(Assets.manager.get(Assets.cobblestone)));
        OBSIDIAN = register(OBSIDIAN_ID, new Bloco(Assets.manager.get(Assets.obsidian)));
    }
}
