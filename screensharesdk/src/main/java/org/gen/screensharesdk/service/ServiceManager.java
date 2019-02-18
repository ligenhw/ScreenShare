package org.gen.screensharesdk.service;

public interface ServiceManager {

    interface OnInitListener {
        /**
         * Called to signal the completion of the ScreenShare service initialization.
         */
        void onInit();
    }

    void init(OnInitListener l);
    void destory();
}
