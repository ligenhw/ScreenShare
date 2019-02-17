package org.gen.screensharesdk;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

class ScreenShareService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private DeviceListManager dm = new DeviceListManager();
    private RtspServer server = new RtspServer(dm);

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class LocalBinder extends Binder {
        ScreenShareService getService() {
            return ScreenShareService.this;
        }
    }

    public RtspServer getServer() {
        return server;
    }

    public DeviceListManager getDm() {
        return dm;
    }
}
