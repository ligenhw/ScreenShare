package org.gen.screensharesdk;

import android.util.Log;

public class Wfd {

    private static final String TAG = "Wfd";

    static {
        System.loadLibrary("wfd-lib");
    }

    public native String listen();

    public static void init() {
        Wfd wfd = new Wfd();
        Log.d(TAG, "init sdk native code : " + wfd.listen());
    }
}
