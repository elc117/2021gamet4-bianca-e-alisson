package com.paradigmas.game.entity.component;

import javax.swing.JFrame;

public class FrameComponent extends JFrame {

    public FrameComponent () {
        //this.add();
    }

    public static JFrame inicialScreen () {
        return new JFrame();
    }
}
