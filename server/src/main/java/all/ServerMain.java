package all;

import common.*;
import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: all.ServerMain <port>");
            return;
        }

        try {
            int port = Integer.parseInt(args[0]);
            ServerApp.setListenerThread(new ListenerThread(port));
            ServerApp.startListening();
        } catch (Exception e) {
            System.err.println("Error starting Server: " + e.getMessage());
        }
        Scanner scanner = new Scanner(System.in);

        ServerApp.players = SaveLoadPlayers.loadPlayers();
        label:
        while (true) {
            String line = scanner.nextLine();
            switch (line) {
                case "exit":
                    break label;
                case "list":
                    for (ClientConnectionThread cct : ServerApp.connections) {
                        System.out.println(cct.getPlayer().getUsername() + " " + cct.getTimeToConnect());
                    }
                    break;
                case "refresh":
                    for (ClientConnectionThread cct : ServerApp.connections) {
                        cct.refreshStatus();
                    }
                    break;
            }
        }
        scanner.close();
        System.out.println("End of server");
        ServerApp.end();
        System.exit(0);
    }
}
