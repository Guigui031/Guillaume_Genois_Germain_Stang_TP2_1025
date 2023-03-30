package client.client_simple;
import java.io.*;
import java.net.Socket;
public class ClientSimple {
        public static void main(String[] args) throws IOException {

                // need host and port, we want to connect to the ServerSocket at port 7777
                Socket socket = new Socket("127.0.0.1", 1337);
                System.out.println("Connected!");

                // get the output stream from the socket.
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream dataOutputStream = new ObjectOutputStream(outputStream);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                System.out.println("Sending string to the ServerSocket");

                // write the message we want to send
                dataOutputStream.writeObject("CHARGER Automne");
                dataOutputStream.flush(); // send the message
                System.out.println(in.readLine());
                System.out.println(dataOutputStream);
                dataOutputStream.close(); // close the output stream when we're done.



        }
}
