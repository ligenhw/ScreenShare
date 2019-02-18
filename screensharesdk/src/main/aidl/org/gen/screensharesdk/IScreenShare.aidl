// IScreenShare.aidl
package org.gen.screensharesdk;

// Declare any non-default types here with import statements
import org.gen.screensharesdk.IScreenShareListener;

interface IScreenShare {
    int start(IScreenShareListener listener);
    void puase();
    void resume();
    void stop();

    void destory();
}
