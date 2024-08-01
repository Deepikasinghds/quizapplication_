package quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class thankyouf extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    public thankyouf(int score, String username) {
        setTitle("Thank You");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(3, 1));

        JLabel usernameLabel = new JLabel("Username: " + username, SwingConstants.CENTER);
        usernameLabel.setFont(new Font("Serif", Font.BOLD, 24));
        contentPanel.add(usernameLabel);

        JLabel scoreLabel = new JLabel("Your Score: " + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Serif", Font.BOLD, 24));
        contentPanel.add(scoreLabel);

        add(contentPanel, BorderLayout.CENTER);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Serif", Font.PLAIN, 24));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveScore(username, score);
                dispose();
                new homepage(); 
            }
        });
        add(exitButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void saveScore(String username, int score) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz","root","Vivo123@")) {
            String query = "INSERT INTO user_scores (username, score) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
