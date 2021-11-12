package SSP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class TextClient {
    public static void main(String[] args) {
        try {
            // localhost är alias för IP-adress för den lokala datorn d.v.s. den datorn
            // du kör detta program (vilket i detta fall är samma dator som serverprogrammet körs på)
            Socket socket = new Socket("localhost", 4713);
            Scanner scanner = new Scanner (System.in);
            BufferedReader in=new BufferedReader
                    (new InputStreamReader(socket.getInputStream()));
            PrintWriter ut = new PrintWriter(socket.getOutputStream());
            //ut.println("Charlotta");

            while (true){
                String nextInput = scanner.next();
                if (Objects.equals(nextInput, "4")){
                    break;
                }
                ut.println((nextInput));
                ut.flush();
                System.out.println(in.readLine());

            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }


}
