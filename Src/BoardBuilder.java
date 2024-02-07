import javax.swing.*;
import java.awt.*;

public class BoardBuilder extends JFrame {
    private final TicTacToe ticTacToe;
    JLabel lbl;
    JPanel labelPanel;
    JPanel buttonPanel;
    JButton[][] buttons;
    JButton restartButton;
    int height;
    int length;

    public BoardBuilder(TicTacToe ticTacToe, int height, int length){
        this.ticTacToe = ticTacToe;
        this.height = height;
        this.length = length;
        buttonPanel = new JPanel(new GridLayout(this.height , this.length));

        initGUI();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(buttonPanel);
        add(labelPanel);
        setLocationRelativeTo(null);

        setSize(500, 300);
        //pack();
        setVisible(true);
    }

    private void initGUI() {
        setTitle("Tic Tac Toe");

        labelPanel = new JPanel();
        buttonPanel = new JPanel(new GridLayout(height, length)); // split the panel in 1 rows and 2 cols)

        lbl = new JLabel("It is player X turn");
        restartButton = new JButton("Start over?");

        buttonPanel.setPreferredSize(new Dimension(0, 200));
        initButtons();

        labelPanel.add(lbl);

        restartButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent ae) {
                ticTacToe.restartGame();
            }
        });

        labelPanel.add(restartButton);
    }

    private void initButtons() {
        buttons = new JButton[3][3];

        // Initialize buttons and add ActionListener
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent ae) {
                        ticTacToe.btnMouseClicked(ae);
                    }
                });
                buttonPanel.add(buttons[i][j]);
            }
        }
    }
}
