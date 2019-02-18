package org.gen.screensharesdk.source;

import android.util.Log;
import org.gen.screensharesdk.IScreenShareListener;
import org.gen.screensharesdk.IScreenShareService;
import org.gen.screensharesdk.ScreenShareException;

import java.io.IOException;

public class ScreenShareService extends IScreenShareService.Stub {

    private static final String TAG = "ScreenShareService";

    private final DeviceListManager dm = new DeviceListManager();
    private final RtspServer server = new RtspServer(dm);

    @Override
    public synchronized int start(IScreenShareListener listener) {
        try {
            dm.setListener(listener);
            return server.start();
        } catch (IOException e) {
            Log.w(TAG, "server start failed " + e.getMessage());
        } catch (ScreenShareException e) {
            Log.w(TAG, "server is already running.");
        }

        return -1;
    }

    @Override
    public synchronized void puase() {
    }

    @Override
    public synchronized void resume() {
    }

    @Override
    public synchronized void stop() {
        try {
            server.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
