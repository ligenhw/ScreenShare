// IScreenShareListener.aidl
package org.gen.screensharesdk;

// Declare any non-default types here with import statements
import org.gen.screensharesdk.DeviceInfo;

interface IScreenShareListener {
    void onDeviceAdded(in DeviceInfo device);
    void onDeviceRemoved(in DeviceInfo device);
}
