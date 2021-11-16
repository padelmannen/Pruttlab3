package SSP;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class Gameboard extends JPanel {


    private final JButton[] buttons = new JButton[3];
    private JButton lastPlayed; // remembers last chosen button/gesture
    private final JLabel upperMess;
    private final JLabel lowerMess;
    private final JLabel scorelabel;
    private int score;
    private final Color bgcolor;
    private final HashMap<String,JButton> map = new HashMap<>();


    // Constructor that builds the board, used for computers board
    Gameboard(String name) {
        setLayout(new GridLayout(5,1));

        // Upper JPanel holds players name and last gesture played
        JPanel upper = new JPanel();
        upper.setLayout(new GridLayout(2,1));
        upper.add(new JLabel(name, JLabel.CENTER));
        upperMess = new JLabel("RPS", JLabel.CENTER);
        upper.add(upperMess);
        add(upper);

        // Lower JPanel has messages about the game and score
        JPanel lower = new JPanel();
        lower.setLayout(new GridLayout(2,1));
        lowerMess = new JLabel("win/lose/draw", JLabel.CENTER);
        scorelabel = new JLabel("Score: 0", JLabel.CENTER);
        lower.add(lowerMess); lower.add(scorelabel);

        for (int i = 0; i<3; i++){
            Icon[] icons = {
                    new ImageIcon(Objects.requireNonNull(getClass().getResource("/SSP/rock.gif"))),
                    new ImageIcon(Objects.requireNonNull(getClass().getResource("/SSP/scissors.gif"))),
                    new ImageIcon(Objects.requireNonNull(getClass().getResource("/SSP/paper.gif")))};
            buttons[i] = new JButton(icons[i]);
            buttons[i].setOpaque(true);
            String[] texts = {"STEN", "SAX", "PASE"};
            buttons[i].setActionCommand(texts[i]);
            add(buttons[i]);
            // Store each button in a map with its text as key.
            // Enables us to retrieve the button from a textvalue.
            map.put(texts[i],buttons[i]);
        }

        add(lower); // added after buttons
        bgcolor = buttons[0].getBackground();
        lastPlayed = buttons[0]; // arbitrary value at start
    }


    // Contructor for players board, puts listener on buttons
    Gameboard(String name, ActionListener listener) {
        this(name); // call other constructor to build the board
        for (int i = 0; i<3; i++)
            buttons[i].addActionListener(listener);
    }


    // reset yellow color
    void resetColor() {
        lastPlayed.setBackground(bgcolor);
    }

    void setUpper(String r) {
        upperMess.setText(r);
    }

    void setLower(String r) {
        lowerMess.setText(r);
    }

    // remember last chosen JButton and mark it yellow
    void markPlayed(String r) {
        lastPlayed = map.get(r);
        lastPlayed.setBackground(Color.yellow);
    }

    // or use JButton as parameter
    void markPlayed(JButton b) {
        lastPlayed = b;
        lastPlayed.setBackground(Color.yellow);
    }

    // add one point and display new score
    void wins() {
        score++;
        scorelabel.setText("Score: " + score);
    }
}