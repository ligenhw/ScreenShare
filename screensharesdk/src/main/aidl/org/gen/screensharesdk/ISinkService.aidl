// ISinkService.aidl
package org.gen.screensharesdk;

// Declare any non-default types here with import statements

interface ISinkService {
    void connect(String ip, int port);
    void disconnect();
}
