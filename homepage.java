package quiz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class homepage extends JFrame implements ActionListener {
    JFrame f;
    JLabel l1, l2, Background;
    MenuBar m;
    Menu m1, m2, m3, m4;
    MenuItem i1, i2, i3;
    JButton b1;
    Connection con;

    homepage() {
        f = new JFrame();
        Background = new JLabel("", new ImageIcon("u.jpg"), JLabel.CENTER);
        Background.setBounds(0, 0, 1300, 1000);
        f.add(Background);
        f.setSize(1300, 1000);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setFont(new Font("Arial", Font.BOLD, 30));
        f.setVisible(true);

        m = new MenuBar();
        m.setFont(new Font("Arial", Font.BOLD, 40));
        f.setMenuBar(m);

        m1 = new Menu("Home");
        m1.setFont(new Font("Arial", Font.BOLD, 20));
        m.add(m1);

        m2 = new Menu("Quizzes");
        m2.setFont(new Font("Arial", Font.BOLD, 20));
        m.add(m2);
        i1 = new MenuItem("General Knowledge");
        i1.setFont(new Font("Arial", Font.BOLD, 20));
        i1.addActionListener(this);
        m2.add(i1);

        i2 = new MenuItem("Aptitude");
        i2.setFont(new Font("Arial", Font.BOLD, 20));
        i2.addActionListener(this);
        m2.add(i2);

        m3 = new Menu("Leaderboard");
        m3.setFont(new Font("Arial", Font.BOLD, 20));
        m.add(m3);
        i3 = new MenuItem("Leaderboard");
        i3.setFont(new Font("Arial", Font.BOLD, 20));
        i3.addActionListener(this);
        m3.add(i3);

        m4 = new Menu("");
        m4.setFont(new Font("Arial", Font.BOLD, 20));
        m.add(m4);

        l1 = new JLabel("WELCOME TO ");
        l1.setBounds(50, 30, 700, 300);
        l1.setFont(new Font("Serif", Font.BOLD, 80));
        Background.add(l1);

        l2 = new JLabel("QUIZ!");
        l2.setBounds(350, 30, 900, 500);
        l2.setForeground(Color.RED);
        l2.setFont(new Font("Serif", Font.BOLD, 100));
        Background.add(l2);

        b1 = new JButton("Get Started");
        b1.setBounds(100, 500, 400, 60);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("Serif", Font.BOLD, 40));
        b1.addActionListener(this);
        Background.add(b1);

        try {
            String url = "jdbc:mysql://localhost:3306/quiz";
            String user = "root";
            String password = "Vivo123@";
            Connection conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new homepage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == i1) {
            new login();
        } else if (e.getSource() == i2) {
            new login();
        } else if (e.getSource() == i3) {
            
            new leaderboard(); 
        } else if (e.getSource() == b1) {
            new login();
        }
    }

    
   
}
