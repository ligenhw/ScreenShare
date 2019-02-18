package org.gen.screensharesdk.sink;

import org.gen.screensharesdk.ScreenShareException;

import java.io.IOException;
import java.net.Socket;

public class RtspClient {

    private Socket socket;

    public void connect(String ip, int port) throws IOException, ScreenShareException {
        if (socket != null) {
            throw new ScreenShareException("sink already connected.", new Exception());
        }
        socket = new Socket(ip, port);

    }

    public void disconnect() throws IOException {
        socket.close();
        socket = null;
    }
}
