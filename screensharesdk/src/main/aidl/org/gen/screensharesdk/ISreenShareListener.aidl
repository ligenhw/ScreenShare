// ISreenShareListener.aidl
package org.gen.screensharesdk;

// Declare any non-default types here with import statements
import org.gen.screensharesdk.DeviceInfo;

interface ISreenShareListener {
    void onDeviceAdded(DeviceInfo device);
    void onDeviceRemoved(DeviceInfo device);
}
