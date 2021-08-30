package com.paradigmas.game.bloco;

import com.badlogic.gdx.graphics.Texture;

public class BlocoCodigo extends Bloco {
    private boolean solid = true;
    public int id;

    public BlocoCodigo(Texture texture) {
        super(texture);
    }

    @Override
    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }
}
