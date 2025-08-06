package all;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenerThread extends Thread {
    private final ServerSocket serverSocket;
    private boolean isEnd;

    @Override
    public void run() {
        try {
            while (!isEnd) {
                Socket socket = serverSocket.accept();
                System.out.println("Accepted Connection");
                handleConnection(socket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Constructor
    public ListenerThread(int port) throws Exception {
        this.serverSocket = new ServerSocket(port);
        this.isEnd = false;
    }


    // Methods
    private void handleConnection(Socket socket) {
        if (socket == null) return;
        try {
            new ClientConnectionThread(socket).start();
        } catch (Exception e) {
            try {socket.close();} catch (IOException ignore) {}
        }
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}
