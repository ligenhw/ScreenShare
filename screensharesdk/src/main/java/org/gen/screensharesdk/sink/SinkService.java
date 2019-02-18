package org.gen.screensharesdk.sink;

import org.gen.screensharesdk.ISinkService;
import org.gen.screensharesdk.ScreenShareException;

import java.io.IOException;

public class SinkService extends ISinkService.Stub {

    private final RtspClient client = new RtspClient();

    @Override
    public synchronized void connect(String ip, int port) {
        try {
            client.connect(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ScreenShareException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void disconnect() {
        try {
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
