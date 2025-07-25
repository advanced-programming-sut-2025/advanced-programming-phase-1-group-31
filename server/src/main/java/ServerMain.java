import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: ServerMain <port>");
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

        label:
        while (true) {
            String line = scanner.nextLine();
            switch (line) {
                case "exit":
                    break label;
                case "list":
                    for (ClientConnectionThread cct : ServerApp.connections) {
                        System.out.println(cct.getUsername() + " " + cct.getTimeToConnect());
                    }
                    break;
                case "refresh":
                    for (ClientConnectionThread cct : ServerApp.connections) {
                        cct.refreshStatus();
                    }
                    break;
            }
        }
    }
}
