import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PuzzleUI extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private JButton shuffleButton;
    private JLabel moveLabel;
    private PuzzleGame game;

    public PuzzleUI() {
        game = new PuzzleGame();
        setupUI();
        updateUI();
    }

    private void setupUI() {
        setTitle("9-Box Puzzle");
        setSize(350, 400);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(44, 62, 80)); // Dark classy bg
    
        Font font = new Font("SansSerif", Font.BOLD, 18);
    
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBounds(60 + j * 70, 50 + i * 70, 60, 60);
                buttons[i][j].setFont(font);
                buttons[i][j].setBackground(new Color(236, 240, 241)); // Light button
                buttons[i][j].setForeground(Color.BLACK);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(this);
                add(buttons[i][j]);
            }
    
        shuffleButton = new JButton("Shuffle");
        shuffleButton.setBounds(110, 270, 100, 40);
        shuffleButton.setFont(font);
        shuffleButton.setBackground(new Color(52, 152, 219)); // Classy blue
        shuffleButton.setForeground(Color.WHITE);
        shuffleButton.setFocusPainted(false);
        shuffleButton.addActionListener(this);
        add(shuffleButton);
    
        moveLabel = new JLabel("Moves: 0");
        moveLabel.setBounds(120, 10, 200, 30);
        moveLabel.setFont(font);
        moveLabel.setForeground(Color.WHITE);
        add(moveLabel);
    
        setVisible(true);
    }
    
    private void updateUI() {
        String[][] grid = game.getGrid();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                buttons[i][j].setText(grid[i][j]);

        moveLabel.setText("Moves: " + game.getMoveCount());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == shuffleButton) {
            game.shuffleTiles();
            updateUI();
            return;
        }

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (e.getSource() == buttons[i][j]) {
                    if (game.moveTile(i, j)) {
                        updateUI();
                        if (game.checkWin()) {
                            JOptionPane.showMessageDialog(this, "🎉 You Win in " + game.getMoveCount() + " moves!");
                        }
                    }
                }
    }
}
