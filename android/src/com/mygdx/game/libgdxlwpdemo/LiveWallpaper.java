package com.mygdx.game.libgdxlwpdemo;

import android.content.Context;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;
import com.badlogic.gdx.backends.android.AndroidWallpaperListener;

public class LiveWallpaper extends AndroidLiveWallpaperService {
    ApplicationListener mApplicationListener;

    @Override
    public void onDestroy() {
        super.onDestroy();
        mApplicationListener.dispose();
    }

    @Override
    public void onCreateApplication() {
        super.onCreateApplication();
        AndroidApplicationConfiguration androidApplicationConfiguration = new AndroidApplicationConfiguration();
        mApplicationListener = new MyLiveWallpaperListener(this);
        initialize(mApplicationListener, androidApplicationConfiguration);
    }

    private class MyLiveWallpaperListener extends MyApplicationListener implements AndroidWallpaperListener {
        MyLiveWallpaperListener(Context context) {
            super(context);
        }

        @Override
        public void offsetChange(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

        }

        @Override
        public void previewStateChange(boolean isPreview) {

        }

        @Override
        public void iconDropped(int x, int y) {

        }
    }
}
