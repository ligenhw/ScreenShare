package org.gen.screensharesdk.source;

import android.util.Log;
import org.gen.screensharesdk.DeviceInfo;
import org.gen.screensharesdk.ScreenShareException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RtspServer {

    private static final String TAG = "RtspServer";
    private ServerSocket serverSocket;
    private ExecutorService exec;

    public interface Callback {
        void onConnect(DeviceInfo device);
        void onRemoved(DeviceInfo device);
    }

    private final Callback callback;

    public RtspServer(Callback callback) {
        this.callback = callback;
        exec = Executors.newCachedThreadPool();
    }

    private Runnable listeningRunnable = new Runnable() {
        @Override
        public void run() {
            for(;;) {
                final Socket socket;
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    Log.d(TAG, "server accept break : " + e.getMessage());
                    break;
                }
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        handleRequest(socket);
                    }
                });
            }
        }
    };

    public int start() throws IOException, ScreenShareException {
        if (serverSocket != null) {
            throw new ScreenShareException("RtspServer already start.", new Exception());
        }

        serverSocket = new ServerSocket();
        serverSocket.bind(null);
        exec.execute(listeningRunnable);
        int port = serverSocket.getLocalPort();
        String ip = serverSocket.getInetAddress().getHostAddress();
        Log.d(TAG, "host : "+ ip + " listening at port : " + port);
        return port;
    }

    public void stop() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
            exec.shutdown();
        }
    }

    private void handleRequest(Socket socket) {
        InetAddress address = socket.getInetAddress();
        Log.d(TAG, "incoming request " + address);

        DeviceInfo.Builder builder = new DeviceInfo.Builder();
        DeviceInfo info = builder.ip(address.getHostAddress()).build();
        callback.onConnect(info);
    }


}
