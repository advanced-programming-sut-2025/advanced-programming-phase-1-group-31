package io.github.some_example_name.model;

import common.JSON;
import common.Message;
import io.github.some_example_name.controller.ServerMessageController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class C2SConnectionThread extends Thread {

    private boolean isEnd;
    private final Socket socket;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    private final ScheduledExecutorService scheduler;

    public C2SConnectionThread(Socket socket) throws IOException {
        this.isEnd = false;
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void run() {
        scheduler.scheduleAtFixedRate(() -> {
            if (!isEnd) {
                sendMessage(new Message(new HashMap<>(), Message.Type.Heartbeat));
            }
        }, 5, 10, TimeUnit.SECONDS);

        try {
            while (!isEnd) {
                try {
                    String receivedStr = dataInputStream.readUTF();
                    Message message = JSON.fromJson(receivedStr);
                    Message generatedMessage = ServerMessageController.handleMessage(message);
                    if (generatedMessage != null) {
                        sendMessage(generatedMessage);
                    }
                } catch (Exception e) {
                    System.err.println("Error sending message: " + e.getMessage());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
        } finally {
            end();
        }
    }

    public void sendMessage(Message message) {
        String jsonMessage = JSON.toJson(message);
        try {
            dataOutputStream.writeUTF(jsonMessage);
            dataOutputStream.flush();
        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }

    public void end() {
        if (isEnd) return;
        isEnd = true;
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
        try {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException ignored) {
        }
    }
}
