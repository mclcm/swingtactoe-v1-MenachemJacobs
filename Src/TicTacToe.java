import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {
    JLabel lbl;
    JPanel labelPanel;
    JPanel buttonPanel;
    JButton[][] buttons;
    private Boolean isXTurn = true;

    public TicTacToe() {
        initGUI();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(labelPanel);
        add(buttonPanel);

        setSize(500, 300);
        //pack();
        setVisible(true);
    }

    private void initGUI() {
        setTitle("Tic Tac Toe");

        lbl = new JLabel();
        labelPanel = new JPanel();
        labelPanel.add(lbl);

        buttonPanel = new JPanel(new GridLayout(3, 3)); // split the panel in 1 rows and 2 cols)
        initButtons();
    }

    private void initButtons() {
        buttons = new JButton[3][3];

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
                buttonPanel.add(buttons[i][j]);
            }
        }
    }

    private void btnMouseClicked(java.awt.event.MouseEvent ae) {
        JButton clickedButton = (JButton) ae.getSource();
        lbl.setText("Hello World");

        if(clickedButton.isEnabled()) {
            clickedButton.setText(isXTurn ? "X" : "O");
            isXTurn = !isXTurn;
        }

        clickedButton.setEnabled(false);


    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToe();
            }
        });
        //Model
        //View
        //Controller
    }
}
