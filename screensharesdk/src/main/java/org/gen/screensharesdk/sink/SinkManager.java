package org.gen.screensharesdk.sink;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import org.gen.screensharesdk.ISinkService;
import org.gen.screensharesdk.service.AbsServiceManager;
import org.gen.screensharesdk.service.RemoteService;

public final class SinkManager extends AbsServiceManager {

    private static final String TAG = "SinkManager";

    private static SinkManager INSTANCE;

    public static SinkManager getInstance(Context context) {
        assertMainThread();

        if (context == null) {
            throw new NullPointerException("Context can not be null.");
        }

        if (INSTANCE == null) {
            INSTANCE = new SinkManager(context);
        }
        return INSTANCE;
    }

    @Override
    protected Intent getIntent() {
        return RemoteService.getSinkIntent(mContext);
    }

    protected SinkManager(Context context) {
        super(context);
    }

    @Override
    protected ISinkService getService() {
        return ISinkService.Stub.asInterface(bnBinder);
    }

    public void connect(String ip, int port) {
        assertMainThread();

        try {
            getService().connect(ip, port);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        assertMainThread();

        try {
            getService().disconnect();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
