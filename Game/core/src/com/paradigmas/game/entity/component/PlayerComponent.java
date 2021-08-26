package com.paradigmas.game.entity.component;

import com.artemis.Component;

public class PlayerComponent extends Component {
    public boolean canWalk = true;
    public float normalWalkSpeed = 100;
    public float buffedWalkSpeed = 200;

    public int coffe = 20;

    public boolean have_coffe() {
        return coffe > 0;
    }
}
