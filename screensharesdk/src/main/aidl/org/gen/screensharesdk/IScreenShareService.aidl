// IScreenShareService.aidl
package org.gen.screensharesdk;

// Declare any non-default types here with import statements
import org.gen.screensharesdk.IScreenShareListener;

interface IScreenShareService {
    int start(IScreenShareListener listener);
    void puase();
    void resume();
    void stop();
}
