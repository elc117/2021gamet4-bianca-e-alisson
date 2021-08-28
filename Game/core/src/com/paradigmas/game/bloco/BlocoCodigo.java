package com.paradigmas.game.bloco;

import com.badlogic.gdx.graphics.Texture;

public class BlocoCodigo extends Bloco{

    public static boolean cond = false;
    boolean solid = true;
    Texture textur;

    public BlocoCodigo(Texture texture) {
        super(texture);
        textur = texture;
    }

    @Override
    public boolean isSolid() {
        if(cond) {
            solid = false;
        }

        return solid;
    }
}
