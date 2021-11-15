package SSP;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;

class RPSSkel extends JFrame {
    Gameboard myboard, computersboard;
    int counter; // To count ONE ... TWO  and on THREE you play
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    JButton closebutton;

    RPSSkel () {
        try {
            socket = new Socket("localhost", 4713);
            in=new BufferedReader
                    (new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            closebutton = new JButton("Close");
            myboard = new Gameboard("Myself"); // Must be changed
            computersboard = new Gameboard("Computer");
            JPanel boards = new JPanel();
            boards.setLayout(new GridLayout(1, 2));
            boards.add(myboard);
            boards.add(computersboard);
            add(boards, BorderLayout.CENTER);
            add(closebutton, BorderLayout.SOUTH);
            setSize(350, 650);
            setVisible(true);
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void main (String[] u) {
        new RPSSkel();
    }
}
