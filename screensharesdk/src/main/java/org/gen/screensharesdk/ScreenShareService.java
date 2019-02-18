package org.gen.screensharesdk;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.io.IOException;

public class ScreenShareService extends Service {

    private final IBinder mBinder = new ScreenShareBnBinder();
    private DeviceListManager dm = new DeviceListManager();
    private RtspServer server = new RtspServer(dm);

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class ScreenShareBnBinder extends IScreenShare.Stub {

        @Override
        public int start(IScreenShareListener listener) throws RemoteException {
            try {
                dm.setListener(listener);
                return server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return -1;
        }

        @Override
        public void puase() throws RemoteException {

        }

        @Override
        public void resume() throws RemoteException {

        }

        @Override
        public void stop() throws RemoteException {
            try {
                server.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void destory() throws RemoteException {
            ScreenShareService.this.stopSelf();
        }
    }

    public RtspServer getServer() {
        return server;
    }

    public DeviceListManager getDm() {
        return dm;
    }
}