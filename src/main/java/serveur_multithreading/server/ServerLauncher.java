package serveur_multithreading.server;

import server.Server;

public class ServerLauncher {
    public final static int PORT = 1337;

    public static void main(String[] args) {
        server.Server server;
        try {
            server = new Server(PORT);
            System.out.println("Server is running...");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}