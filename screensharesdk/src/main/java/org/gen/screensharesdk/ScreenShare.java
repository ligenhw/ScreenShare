package org.gen.screensharesdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.MainThread;
import android.util.Log;


/**
 * the api must invoke in main thread.
 */
@MainThread
public final class ScreenShare {

    private static final String TAG = "ScreenShare";

    private ScreenShare() {
    }

    private static final ScreenShare INSTANCE = new ScreenShare();

    public static ScreenShare getInstance() {
        return INSTANCE;
    }

    private IScreenShare service;

    public interface Callback {
        void onComplete();
    }

    public interface DeviceListListener {
        void onDeviceAdded(DeviceInfo device);
        void onDeviceRemoved(DeviceInfo device);
    }

    /**
     * start ScreenShareService.
     * @param context   use to start a service.
     * @param callback  after init complete call this callback.
     */
    public void init(Context context, final Callback callback) {
        assertMainThread();

        Log.d(TAG, "init");
        if (context == null) {
            throw new NullPointerException("Context can not be null.");
        }

        context = context.getApplicationContext();
        Intent intent = new Intent(context, ScreenShareService.class);
        context.bindService(intent, new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(TAG, "service connected");
                service = IScreenShare.Stub.asInterface(binder);
                callback.onComplete();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "service disconnected");
                service = null;
            }
        }, Context.BIND_AUTO_CREATE);
    }

    /**
     * start a server socket.
     *
     * @return port
     */
    public int start(DeviceListListener listener) throws RemoteException {
        assertMainThread();

        Log.d(TAG, "start");
        if (service != null) {
            return service.start();
        }

        return -1;
    }

    public void pause() throws RemoteException {
        if (service != null) {
            service.puase();
        }
    }

    public void resume() throws RemoteException {
        if (service != null) {
            service.resume();
        }
    }

    /**
     * close server socket.
     */
    public void stop() throws RemoteException {
        assertMainThread();

        Log.d(TAG, "stop");
        if (service != null) {
            service.stop();
        } else {
            Log.d(TAG, "service is null, do nothing.");
        }
    }

    /**
     * destory service.
     */
    public void destory() throws RemoteException {
        assertMainThread();

        Log.d(TAG, "destory");
        if (service != null) {
            service.destory();
            service = null;
        } else {
            Log.d(TAG, "service is null, do nothing.");
        }
    }

    private void assertMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("ScreenShare API must invoke in main thread.");
        }
    }

}
