package quiz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class register extends JFrame implements ActionListener {
    JFrame f;
    JLabel l1, l2, l3, l4,Background;
    JTextField t1, t3;
    JPasswordField t2;
    JButton b1, b2, b3;
    Connection conn;

    register() {
        
        f = new JFrame("Register Page");
         
        
        f.setSize(900, 700);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

        l1 = new JLabel("USER REGISTRATION");
        l1.setFont(new Font("Serif", Font.BOLD, 40));
        l1.setBounds(200, 30, 500, 70);
        f.add(l1);

        l2 = new JLabel("Username:");
        l2.setFont(new Font("Serif", Font.PLAIN, 30));
        l2.setBounds(110, 150, 250, 30);
        f.add(l2);

        t1 = new JTextField();
        t1.setBounds(300, 150, 300, 30);
        t1.setFont(new Font("Serif", Font.PLAIN, 30));
        f.add(t1);

        l3 = new JLabel("Password:");
        l3.setFont(new Font("Serif", Font.PLAIN, 30));
        l3.setBounds(110, 250, 250, 30);
        f.add(l3);

        t2 = new JPasswordField();
        t2.setBounds(300, 250, 300, 30);
        t2.setFont(new Font("Serif", Font.PLAIN, 30));
        f.add(t2);

        l4 = new JLabel("Email:");
        l4.setFont(new Font("Serif", Font.PLAIN, 30));
        l4.setBounds(110, 350, 250, 30);
        f.add(l4);

        t3 = new JTextField();
        t3.setBounds(300, 350, 300, 30);
        t3.setFont(new Font("Serif", Font.PLAIN, 30));
        f.add(t3);

        b1 = new JButton("Register");
        b1.setBounds(250, 500, 200, 30);
        b1.setFont(new Font("Serif", Font.PLAIN, 30));
        b1.addActionListener(this);
        f.add(b1);

        b2 = new JButton("SIGNIN");
        b2.setBounds(500, 500, 200, 30);
        b2.setFont(new Font("Serif", Font.PLAIN, 30));
        b2.addActionListener(this);
        f.add(b2);

        b3 = new JButton("Clear");
        b3.setBounds(340, 580, 200, 30);
        b3.setFont(new Font("Serif", Font.PLAIN, 30));
        b3.addActionListener(this);
        f.add(b3);

        f.setVisible(true);

        
        try {
            String url = "jdbc:mysql://localhost:3306/quiz";
            String user = "root";
            String password = "Vivo123@";
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) { 
            registerUser();
        } else if (e.getSource() == b2) { 
            new login();
        } else if (e.getSource() == b3){
            clearFields();
        }
    }

    private void registerUser() {
        String username = t1.getText();
        String password = new String(t2.getPassword());
        String email = t3.getText();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(f, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(f, "User registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { 
                JOptionPane.showMessageDialog(f, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                e.printStackTrace();
            }
        }
    }

    private void clearFields() {
        t1.setText("");
        t2.setText("");
        t3.setText("");
    }

    public static void main(String[] a) {
        new register();
    }
}
