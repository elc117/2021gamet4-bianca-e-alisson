package com.paradigmas.game.bloco;

public class BlocoAir extends Bloco {

    public BlocoAir() {
        super(null);
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
