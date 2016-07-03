package com.mecharyry.dropcap;

import android.support.annotation.StringRes;

import com.mecharyry.drop_cap.R;

public enum FontType {

    CABIN_REGULAR("Cabin Regular", R.string.sans_serif),
    FUNKROCKER("Funkrocker", R.string.funkrocker),
    MAGNIFICENT("Magnificent", R.string.magnificent),
    NEUROPOLITICAL("Neuropolitical", R.string.neuropolitical);

    private final String fontName;
    @StringRes
    private final int assetUrl;

    FontType(String fontName, @StringRes int assetUrl) {
        this.fontName = fontName;
        this.assetUrl = assetUrl;
    }

    public String getFontName() {
        return fontName;
    }

    @StringRes
    public int getAssetUrl() {
        return assetUrl;
    }

}
