package SSP;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;

class RPSSkel extends JFrame implements ActionListener {
    Gameboard myboard, computersboard;
    int counter; // To count ONE ... TWO  and on THREE you play
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    JButton closebutton;
    String[] choice = {"STEN", "SAX", "PÅSE"};
    String[] result = {"draw draw", "win lose", "lose win"};

    RPSSkel () {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        closebutton = new JButton("Close");

        myboard = new Gameboard("Myself", this); // Must be changed

        computersboard = new Gameboard("Computer");
        JPanel boards = new JPanel();
        boards.setLayout(new GridLayout(1,2));
        boards.add(myboard);
        boards.add(computersboard);
        add(boards, BorderLayout.CENTER);
        add(closebutton, BorderLayout.SOUTH);
        setSize(350, 650);
        setVisible(true);
    }

    public static void main (String[] u) {
        new RPSSkel();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        int y = btn.getY()/125;
        String curChoice;
        int resultIndex = 0;
        counter++;

        if(counter == 1){
            myboard.setUpper("ETT");
            myboard.resetColor();
            myboard.setLower("win/lose/draw");
            computersboard.setLower("win/lose/draw");
        }
        if(counter == 2){
            myboard.setUpper("TVÅ");
        }
        if(counter == 3){
            curChoice = choice[y];
            myboard.setUpper(curChoice);
            String[] res = result[resultIndex].split(" ");
            myboard.setLower(res[0]);
            computersboard.setLower(res[1]);

            myboard.markPlayed(btn);
            counter = 0;
        }


    }
}
