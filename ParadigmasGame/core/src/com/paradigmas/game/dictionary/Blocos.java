package com.paradigmas.game.dictionary;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.IntMap;
import com.paradigmas.game.bloco.Bloco;

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
        DIRT = register(1, new Bloco(new Texture("Mapa_Texturas/Dirt_texture.png")));
        COBBLESTONE = register(2, new Bloco(new Texture("Mapa_Texturas/Pedra_texture.png")));
        OBSIDIAN = register(3, new Bloco(new Texture("Mapa_Texturas/Obsidian_Texture.png")));
    }
}
