package com.paradigmas.game.bloco;

import com.badlogic.gdx.graphics.Texture;

public class Bloco {

    public static final int TILE_SIZE = 24;

    public Texture texture;

    public Bloco(Texture texture)
    {
        this.texture = texture;
    }

    public boolean isSolid() {
        return true;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
