package org.gen.screensharesdk.source;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import org.gen.screensharesdk.DeviceInfo;
import org.gen.screensharesdk.IScreenShareListener;
import org.gen.screensharesdk.source.SourceManager.ScreenShareStatusListener;

public class ScreenShareListener extends IScreenShareListener.Stub {

    private final static int MSG_DEVICE_ADD = 0;
    private final static int MSG_DEVICE_REMOVE = 1;

    private ScreenShareStatusListener l;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DEVICE_ADD:
                    DeviceInfo add = (DeviceInfo) msg.obj;
                    l.onDeviceAdded(add);
                    break;
                case MSG_DEVICE_REMOVE:
                    DeviceInfo remove = (DeviceInfo) msg.obj;
                    l.onDeviceRemoved(remove);
                    break;
            }
        }
    };

    public ScreenShareListener(ScreenShareStatusListener listener) {
        l = listener;
    }

    @Override
    public void onDeviceAdded(DeviceInfo device) {
        handler.obtainMessage(MSG_DEVICE_ADD, device).sendToTarget();
    }

    @Override
    public void onDeviceRemoved(DeviceInfo device) {
        handler.obtainMessage(MSG_DEVICE_REMOVE, device).sendToTarget();
    }
}
