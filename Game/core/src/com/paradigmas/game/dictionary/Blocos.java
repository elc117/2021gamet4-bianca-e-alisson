package com.paradigmas.game.dictionary;

import com.badlogic.gdx.utils.IntMap;
import com.paradigmas.game.bloco.Bloco;
import com.paradigmas.game.resource.Assets;

public class Blocos
{
    public static final IntMap<Bloco> REGISTRY = new IntMap<Bloco>();

    public static final int AIR_ID = 0;

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
        AIR = register(AIR_ID, new Bloco(null));
        DIRT = register(1, new Bloco(Assets.manager.get(Assets.dirt)));
        COBBLESTONE = register(2, new Bloco(Assets.manager.get(Assets.cobblestone)));
        OBSIDIAN = register(3, new Bloco(Assets.manager.get(Assets.obsidian)));
    }
}
