import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {
    JLabel lbl;
    JPanel panel;
    JButton[][] buttons;
    private Boolean isXTurn = true;

    public TicTacToe() {
        lbl = new JLabel();
        panel = new JPanel(new GridLayout(3,3)); // split the panel in 1 rows and 2 cols
        buttons = new JButton[3][3];

        setTitle("Tic Tac Toe");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Initialize buttons and add ActionListener
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("Click Me!");
                buttons[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent ae) {
                        btnMouseClicked(ae);
                    }
                });
                panel.add(buttons[i][j]);
            }
        }

        setLayout(new BorderLayout());
        panel.add(lbl, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }

    private void btnMouseClicked(java.awt.event.MouseEvent ae) {
        JButton clickedButton = (JButton) ae.getSource();
        lbl.setText("Hello World");
        clickedButton.setText(isXTurn ? "X" : "O");
        // Remove the click listener from the button
        clickedButton.setEnabled(false);

        isXTurn = !isXTurn;
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToe();
            }
        });


//        JFrame board = new JFrame("My First JFrame");
//
//        // Close operation
//        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // Set frame properties
//        board.setSize(300, 200); // Set the size of the frame
//
//        // Make the frame visible
//        board.setVisible(true);
//    }
        //Model
        //View
        //Controller
    }
}
