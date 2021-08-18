package com.paradigmas.game.bloco;

import com.badlogic.gdx.graphics.Texture;

public class Bloco {

    public static final int TILE_SIZE = 16;

    public final Texture texture;

    public Bloco(Texture texture)
    {
        this.texture = texture;
    }
}
