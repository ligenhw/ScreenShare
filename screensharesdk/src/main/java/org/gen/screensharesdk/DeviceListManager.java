package org.gen.screensharesdk;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

class DeviceListManager implements RtspServer.Callback {

    private Set<DeviceInfo> deviceInfoList = new HashSet<>();
    private WeakReference<ScreenShare.DeviceListListener> wl;

    private static final int MSG_DEVICE_ADD = 0;
    private static final int MSG_DEVICE_REMOVE = 1;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DEVICE_ADD:
                    if (wl != null && wl.get() != null) {
                        DeviceInfo device = (DeviceInfo) msg.obj;
                        wl.get().onDeviceAdded(device);
                    }
                    break;
                case MSG_DEVICE_REMOVE:
                    if (wl != null) {
                        DeviceInfo device = (DeviceInfo) msg.obj;
                        wl.get().onDeviceRemoved(device);
                    }
                    break;
            }
        }
    };

    public void setListener(WeakReference<ScreenShare.DeviceListListener> listener) {
        wl = listener;
    }

    @Override
    public void onConnect(DeviceInfo device) {
        deviceInfoList.add(device);
        Message msg = handler.obtainMessage(MSG_DEVICE_ADD, device);
        msg.sendToTarget();
    }

    @Override
    public void onRemoved(DeviceInfo device) {
        deviceInfoList.remove(device);
        Message msg = handler.obtainMessage(MSG_DEVICE_REMOVE, device);
        msg.sendToTarget();
    }
}
