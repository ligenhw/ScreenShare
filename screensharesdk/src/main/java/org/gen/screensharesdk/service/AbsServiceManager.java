package org.gen.screensharesdk.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import org.gen.screensharesdk.ScreenShareException;

public abstract class AbsServiceManager implements ServiceManager {

    private static final String TAG = "AbsServiceManager";

    protected IBinder bnBinder;
    protected ServiceConnection connection;
    protected Context mContext;

    protected abstract <T> T getService();
    protected abstract Intent getIntent();

    protected AbsServiceManager(Context context) {
        mContext = context;
    }

    /**
     * start RemoteService.
     * @param listener after init complete call this callback.
     */
    @Override
    public void init(final OnInitListener listener) {
        assertMainThread();

        createService(listener);
    }

    /**
     * unbind service, and stop service.
     */
    @Override
    public void destory() {
        assertMainThread();

        if (connection != null) {
            mContext.unbindService(connection);
        }
        //TODO: destory service.
    }

    private void createService(final ServiceManager.OnInitListener listener) {
        if (bnBinder == null) {
            Intent intent = getIntent();
            connection = new Connection(listener);
            mContext.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }

    protected void checkServiceConnection() throws ScreenShareException {
        createService(null);
        if (bnBinder == null) {
            throw new ScreenShareException(
                    "ScreenShareService is dead and is restarting...", new Exception());
        }
    }

    private class Connection implements ServiceConnection {

        private final ServiceManager.OnInitListener l;

        public Connection(OnInitListener listener) {
            l = listener;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.d(TAG, "service connected");
            bnBinder = binder;
            if (l != null) {
                l.onInit();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "service disconnected");
            bnBinder = null;
            connection = null;
        }
    }

    protected static void assertMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("ScreenShare API must invoke in main thread.");
        }
    }
}
