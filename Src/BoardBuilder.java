import javax.swing.*;
import java.awt.*;

public class BoardBuilder extends JFrame {
    private final TicTacToe ticTacToe;
    public JLabel lbl;
    private JPanel labelPanel;
    private JPanel buttonPanel;
    public JButton[][] buttons;
    JButton restartButton;

    public BoardBuilder(TicTacToe ticTacToe, int height, int length){
        this.ticTacToe = ticTacToe;
        buttonPanel = new JPanel(new GridLayout(height, length));

        initGUI(height, length);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(buttonPanel);
        add(labelPanel);
        setLocationRelativeTo(null);

        setSize(500, 300);
        //pack();
        setVisible(true);
    }

    private void initGUI(int height, int length) {
        setTitle("Tic Tac Toe");

        labelPanel = new JPanel();
        buttonPanel = new JPanel(new GridLayout(height, length)); // split the panel in 1 rows and 2 cols)

        lbl = new JLabel("It is player X turn");
        restartButton = new JButton("Start over?");

        buttonPanel.setPreferredSize(new Dimension(0, 200));
        initButtons(height, length);

        labelPanel.add(lbl);

        restartButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent ae) {
                ticTacToe.restartGame();
            }
        });

        labelPanel.add(restartButton);
    }

    private void initButtons(int height, int length) {
        buttons = new JButton[height][length];

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
