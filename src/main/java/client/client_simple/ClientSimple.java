package client.client_simple;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class ClientSimple {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("127.0.0.1", 1337);
            OutputStreamWriter os = new OutputStreamWriter(
                    clientSocket.getOutputStream()
            );
            BufferedWriter writer = new BufferedWriter(os);
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                System.out.println("Envoi de : " + line);
                writer.append(line + "\n");
                writer.flush();
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
