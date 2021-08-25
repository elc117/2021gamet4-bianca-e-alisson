package com.paradigmas.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

public class FPSLogger extends com.badlogic.gdx.graphics.FPSLogger {

    long startTime;

    boolean debugInConsole;
    boolean debugInTitle;
    String title;

    public FPSLogger(String title, boolean debugInTitle, boolean debugInConsole) {
        startTime = TimeUtils.nanoTime();

        this.title = title;
        this.debugInTitle = debugInTitle;
        this.debugInConsole = debugInConsole;
    }

    @Override
    public void log() {
        if (TimeUtils.nanoTime() - startTime > 1000000000) /* 1,000,000,000ns == one second */ {
            final int fps = Gdx.graphics.getFramesPerSecond();

            if (debugInTitle) {
                Gdx.graphics.setTitle(String.format("%s - fps: %d", this.title, fps));
            }

            if (debugInConsole) {
                Gdx.app.log("FPSLogger", "fps: " + fps);
            }

            startTime = TimeUtils.nanoTime();
        }
    }
}
