// IScreenShare.aidl
package org.gen.screensharesdk;

// Declare any non-default types here with import statements

interface IScreenShare {
    int start(ISreenShareListener listener);
    void puase();
    void resume();
    void stop();

    void destory();
}
