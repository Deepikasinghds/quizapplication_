package quiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class leaderboard {
    private JFrame f;
    private JTable d;
    private DefaultTableModel model;
    private JLabel l1;

    leaderboard() {
        
        f = new JFrame("Leaderboard");
        f.setSize(900, 900);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(null);

       
        l1 = new JLabel("LEADERBOARD");
        l1.setBounds(350, 20, 300, 40);
        l1.setFont(new Font("Arial Black", Font.BOLD, 30));
        f.add(l1);

      
        model = new DefaultTableModel();
        d = new JTable(model);
        model.setColumnIdentifiers(new String[]{"S.No", "RANK", "USERNAME", "SCORE"});
        d.setFont(new Font("Arial", Font.PLAIN, 18));
        d.setRowHeight(30);

       
        JScrollPane scrollPane = new JScrollPane(d);
        scrollPane.setBounds(50, 100, 800, 750);
        f.add(scrollPane);

       
        fetchAndDisplayLeaderboard();

        f.setVisible(true);
    }

    private void fetchAndDisplayLeaderboard() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            
            String url = "jdbc:mysql://localhost:3306/quiz";
            String user = "root";
            String password = "Vivo123@";

          
            conn = DriverManager.getConnection(url, user, password);

          
            String query = "SELECT username, score FROM user_scores ORDER BY score DESC";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

          
            int rank = 1;
            while (rs.next()) {
                String username = rs.getString("username");
                int score = rs.getInt("score");
                model.addRow(new Object[]{rank, "Rank " + rank, username, score});
                rank++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
          
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new leaderboard();
    }
}
