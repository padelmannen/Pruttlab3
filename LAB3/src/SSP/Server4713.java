package SSP;

import java.io.IOException;
import java.net.ServerSocket;

public class Server4713 {
    public static void main( String[] args) {
        try {
            ServerSocket sock = new ServerSocket(4713,100);
            while (true)
                new ClientHandler(sock.accept()).start();
        }
        catch(IOException e)
        {System.err.println(e);
        }
    }
}
