package org.gen.screensharesdk.source;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import org.gen.screensharesdk.DeviceInfo;
import org.gen.screensharesdk.IScreenShareService;
import org.gen.screensharesdk.ScreenShareException;
import org.gen.screensharesdk.service.AbsServiceManager;
import org.gen.screensharesdk.service.RemoteService;

/**
 * Provides APIs for Screen share.
 */
public final class SourceManager extends AbsServiceManager {

    private static final String TAG = "SourceManager";

    private static SourceManager INSTANCE;

    @Override
    protected Intent getIntent() {
        return RemoteService.getSourceIntent(mContext);
    }

    private SourceManager(Context context) {
        super(context);
    }

    public static SourceManager getInstance(Context context) {
        assertMainThread();

        if (context == null) {
            throw new NullPointerException("Context can not be null.");
        }

        if (INSTANCE == null) {
            INSTANCE = new SourceManager(context);
        }
        return INSTANCE;
    }

    @Override
    protected IScreenShareService getService() {
        return IScreenShareService.Stub.asInterface(bnBinder);
    }

    public interface ScreenShareStatusListener {
        void onDeviceAdded(DeviceInfo device);

        void onDeviceRemoved(DeviceInfo device);
    }

    /**
     * start a server socket.
     *
     * @return port
     */
    public int start(ScreenShareStatusListener listener) throws ScreenShareException {
        assertMainThread();
        checkServiceConnection();

        try {
            int port = getService().start(new ScreenShareListener(listener));
            if (port <= 0) {
                throw new ScreenShareException("start() failed.", new Exception());
            }

            return port;
        } catch (RemoteException e) {
            throw new ScreenShareException("start()", e);
        }
    }

    public void pause() throws ScreenShareException {
        assertMainThread();
        checkServiceConnection();

        try {
            getService().puase();
        } catch (RemoteException e) {
            throw new ScreenShareException("pause()", e);
        }
    }

    public void resume() throws ScreenShareException {
        assertMainThread();
        checkServiceConnection();

        try {
            getService().resume();
        } catch (RemoteException e) {
            throw new ScreenShareException("resume()", e);
        }
    }

    /**
     * close server socket.
     */
    public void stop() throws ScreenShareException {
        assertMainThread();

        if (getService() != null) {
            try {
                getService().stop();
            } catch (RemoteException e) {
                throw new ScreenShareException("stop()", e);
            }
        }
    }
}
