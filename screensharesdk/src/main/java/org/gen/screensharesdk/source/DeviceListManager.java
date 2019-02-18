package org.gen.screensharesdk.source;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import org.gen.screensharesdk.DeviceInfo;
import org.gen.screensharesdk.IScreenShareListener;

import java.util.HashSet;
import java.util.Set;

public class DeviceListManager implements RtspServer.Callback {

    private Set<DeviceInfo> deviceInfoList = new HashSet<>();
    private IScreenShareListener wl;

    private static final int MSG_DEVICE_ADD = 0;
    private static final int MSG_DEVICE_REMOVE = 1;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DEVICE_ADD:
                    if (wl != null) {
                        DeviceInfo device = (DeviceInfo) msg.obj;
                        try {
                            wl.onDeviceAdded(device);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case MSG_DEVICE_REMOVE:
                    if (wl != null) {
                        DeviceInfo device = (DeviceInfo) msg.obj;
                        try {
                            wl.onDeviceRemoved(device);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                    default:
                        super.handleMessage(msg);
            }
        }
    };

    public void setListener(IScreenShareListener listener) {
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
