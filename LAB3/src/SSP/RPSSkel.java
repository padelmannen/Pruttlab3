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
    Map<String, Map<String, Integer>> resultMap = new HashMap<>();
    String[] choice = {"STEN", "SAX", "PASE"};
    String[] result = {"draw draw", "win lose", "lose win"};

    RPSSkel () {
        try {
            socket = new Socket("localhost", 4713);
            in=new BufferedReader
                    (new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            createResultMap();

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            closebutton = new JButton("Close");
            closebutton.addActionListener(e -> {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            });

            myboard = new Gameboard("Myself", this); // Must be changed
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

    private void createResultMap() {

        Map <String, Integer> stoneMap = new HashMap<>();
        stoneMap.put("STEN", 0);
        stoneMap.put("SAX", 1);
        stoneMap.put("PASE", 2);

        Map <String, Integer> scissorMap = new HashMap<>();
        scissorMap.put("STEN", 2);
        scissorMap.put("SAX", 0);
        scissorMap.put("PASE", 1);

        Map <String, Integer> paperMap = new HashMap<>();
        paperMap.put("STEN", 1);
        paperMap.put("SAX", 2);
        paperMap.put("PASE", 0);

        resultMap.put("STEN", stoneMap);
        resultMap.put("SAX", scissorMap);
        resultMap.put("PASE", paperMap);

    }

    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        //myboard.markPlayed(btn);
        counter ++;
        if (counter == 1){
            myboard.setUpper("ONE");
            myboard.resetColor();
            computersboard.resetColor();
        }
        else if (counter == 2){
            myboard.setUpper("TWO");
        }
        else if (counter == 3){
            myboard.markPlayed(btn);
            counter = 0;
            makePlay(btn);
        }
    }
    private void makePlay(JButton btn){
        myboard.markPlayed(btn);
        String myHand = getHandChoice(btn);
        myboard.setUpper(myHand);
        out.println(myHand);
        out.flush();
        try {
            String pcHand = in.readLine();
            computersboard.setUpper(pcHand);
            computersboard.markPlayed(pcHand);
            winDecider(myHand, pcHand);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void winDecider(String myHand, String pcHand) {

        int winner = resultMap.get(myHand).get(pcHand);
        String[] res = result[winner].split(" ");
        myboard.setLower(res[0]);
        computersboard.setLower(res[1]);
        if (winner == 1) {
            myboard.wins();
        }
        else if (winner == 2) {
            computersboard.wins();
        }
    }

    private String getHandChoice (JButton btn){
        int y = btn.getY()/125;
        String curChoice;
        curChoice = choice[y];
        return curChoice;
    }

    public static void main (String[] u) {
        new RPSSkel();
    }
}
