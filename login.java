package quiz;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class login extends JFrame implements ActionListener {
    JFrame f;
    JLabel l1, l2, l3, l4, Background;
    JComboBox<String> c1;
    JButton b1, b2, b3;
    JTextField t1;
    JPasswordField pf;
    Connection con;

    login() {
        f = new JFrame("login");
        Background = new JLabel("", new ImageIcon("c.jpg"), JLabel.CENTER);
        Background.setBounds(0, 0, 750, 700);
        f.add(Background);

        f.setSize(750, 700);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(null);

        f.setFont(new Font("Arial Black", Font.BOLD, 30));
        f.setVisible(true);

        l1 = new JLabel("LOGIN ACCOUNT");
        l1.setBounds(200, 50, 450, 100);
        l1.setForeground(Color.BLACK);
        l1.setFont(new Font("Serif", Font.BOLD, 50));
        Background.add(l1);

        l2 = new JLabel("Category");
        l2.setBounds(100, 140, 200, 80);
        l2.setForeground(Color.BLACK);
        l2.setFont(new Font("Arial Black", Font.BOLD, 20));
        Background.add(l2);

        l3 = new JLabel("User Name");
        l3.setBounds(100, 200, 200, 80);
        l3.setForeground(Color.BLACK);
        l3.setFont(new Font("Arial Black", Font.BOLD, 20));
        Background.add(l3);

        l4 = new JLabel("PASSWORD");
        l4.setBounds(100, 250, 200, 80);
        l4.setForeground(Color.BLACK);
        l4.setFont(new Font("Arial Black", Font.BOLD, 20));
        Background.add(l4);

        String[] categories = {"Select", "Teacher", "Student"};
        c1 = new JComboBox<>(categories);
        c1.setBounds(300, 170, 200, 30);
        c1.setFont(new Font("Arial Black", Font.BOLD, 20));
        Background.add(c1);

        t1 = new JTextField();
        t1.setBounds(300, 230, 200, 30);
        t1.setFont(new Font("Arial Black", Font.BOLD, 20));
        Background.add(t1);

        pf = new JPasswordField();
        pf.setBounds(300, 270, 200, 30);
        pf.setFont(new Font("Arial Black", Font.BOLD, 20));
        Background.add(pf);

        b1 = new JButton("Sign in");
        b1.setBounds(100, 400, 150, 40);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("Arial Black", Font.BOLD, 20));
        b1.addActionListener(this);
        Background.add(b1);

        b2 = new JButton("Register");
        b2.setBounds(350, 400, 150, 40);
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        b2.setFont(new Font("Arial Black", Font.BOLD, 20));
        b2.addActionListener(this);
        Background.add(b2);

        b3 = new JButton("Cancel");
        b3.setBounds(230, 450, 150, 40);
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.WHITE);
        b3.setFont(new Font("Arial Black", Font.BOLD, 20));
        b3.addActionListener(this);
        Background.add(b3);

        
        try {
            String url = "jdbc:mysql://localhost:3306/quiz";
            String user = "root";
            String password = "Vivo123@";
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new login();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) { 
            signIn();
        } else if (e.getSource() == b2) { 
            
            new register();
            f.dispose();
        } else if (e.getSource() == b3) { 
            f.dispose();
        }
    }
private void signIn() {
    String username = t1.getText();
    String password = new String(pf.getPassword());

    if (username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(f, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String category = (String) c1.getSelectedItem();
            if (category.equals("Teacher")) {
                new QuestionModule(); 
                f.dispose();
            } else if (category.equals("Student")) {
                
                welcomepage welcomePage = new welcomepage(username); 
                welcomePage.setVisible(true);
                f.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(f, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}