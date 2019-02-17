package org.gen.screensharesdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;

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

    private ScreenShareService service;

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
                service = ((ScreenShareService.LocalBinder) binder).getService();
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
    public int start(DeviceListListener listener) {
        assertMainThread();

        Log.d(TAG, "start");
        try {
            if (service != null) {
                WeakReference<DeviceListListener> l = new WeakReference<>(listener);
                service.getDm().setListener(l);
                return service.getServer().start();
            } else {
                Log.d(TAG, "service is null, do nothing.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void pause() {

    }

    public void resume() {

    }

    /**
     * close server socket.
     */
    public void stop() {
        assertMainThread();

        Log.d(TAG, "stop");
        try {
            if (service != null) {
                service.getServer().stop();
            } else {
                Log.d(TAG, "service is null, do nothing.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * destory service.
     */
    public void destory() {
        assertMainThread();

        Log.d(TAG, "destory");
        if (service != null) {
            service.stopSelf();
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
