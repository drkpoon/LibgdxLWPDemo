package com.mygdx.game.libgdxlwpdemo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class LiveWallpaperSettings extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new FragmentSetting()).commit();
    }

    public static class FragmentSetting extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting);
        }
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return FragmentSetting.class.getName().equals(fragmentName);
    }
}
