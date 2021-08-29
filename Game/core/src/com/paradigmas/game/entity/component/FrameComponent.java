package com.paradigmas.game.entity.component;

import com.paradigmas.game.ParadigmasGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class FrameComponent {
    public static float progress = 0;

    public static JFrame loadScreen() {

        JProgressBar progressBar = new JProgressBar();
        progressBar.setValue((int) progress);

        JPanel panel = new JPanel();
        panel.add(progressBar);

        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(ParadigmasGame.SCREEN_WIDTH, ParadigmasGame.SCREEN_HEIGHT);

        return frame;
    }
}
