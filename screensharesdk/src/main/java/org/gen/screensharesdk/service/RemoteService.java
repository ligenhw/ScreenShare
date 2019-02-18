package org.gen.screensharesdk.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import org.gen.screensharesdk.sink.SinkService;
import org.gen.screensharesdk.source.ScreenShareService;

public final class RemoteService extends Service {

    private final static String ACTION_SOURCE = "action.SOURCE";
    private final static String ACTION_SINK = "action.SINK";

    public static Intent getSourceIntent(Context context) {
        Intent intent = new Intent(context, RemoteService.class);
        intent.setAction(ACTION_SOURCE);
        return intent;
    }

    public static Intent getSinkIntent(Context context) {
        Intent intent = new Intent(context, RemoteService.class);
        intent.setAction(ACTION_SINK);
        return intent;
    }

    @Override
    public IBinder onBind(Intent intent) {
        IBinder iBinder = null;
        if (ACTION_SOURCE.equals(intent.getAction())) {
            iBinder = new ScreenShareService();
        } else {
            iBinder = new SinkService();
        }
        return iBinder;
    }
}