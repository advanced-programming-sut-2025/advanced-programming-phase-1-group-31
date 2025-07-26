package io.github.some_example_name.model;

import common.JSON;
import common.Message;
import io.github.some_example_name.controller.ServerMessageController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.*;

public class C2SConnectionThread extends Thread {

    private boolean isEnd;
    private final Socket socket;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    private final ScheduledExecutorService scheduler;
    protected final BlockingQueue<Message> receivedMessagesQueue;

    public C2SConnectionThread(Socket socket) throws IOException {
        this.isEnd = false;
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.receivedMessagesQueue = new LinkedBlockingQueue<>();

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
                    if (message.getType() == Message.Type.Menu) {
                        receivedMessagesQueue.put(message);
                        continue;
                    }
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


    public Message sendAndWaitForResponse(Message message) {
        sendMessage(message);
        try {
            return receivedMessagesQueue.poll(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    public synchronized void sendMessage(Message message) {
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
