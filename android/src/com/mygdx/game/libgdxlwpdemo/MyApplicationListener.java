package com.mygdx.game.libgdxlwpdemo;

import android.content.Context;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

class MyApplicationListener implements ApplicationListener{
    private Context mContext;
    private float hue;
    private int direction = 1;

    MyApplicationListener(Context context) {
        mContext = context;
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        hue += 0.01f * direction;
        if (hue > 1 || hue < 0) {
            direction *= -1;
        }
        Gdx.gl.glClearColor(hue, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
