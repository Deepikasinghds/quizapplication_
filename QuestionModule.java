package quiz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class QuestionModule extends JFrame implements ActionListener {
    JFrame f;
    JLabel l1, l2, l3, l4, l5, l6, l7, l8;
    JTextField t1, t2, t3, t4, t5, t6;
    JComboBox<String> c1;
    JButton b1, b2;
    Connection conn;

    QuestionModule() {
        f = new JFrame("Question Module");
        f.setSize(1100, 900);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setFont(new Font("Arial Black", Font.BOLD, 30));
        f.setVisible(true);

        l1 = new JLabel("ADD QUESTION");
        l1.setFont(new Font("Serif", Font.BOLD, 40));
        l1.setBounds(400, 30, 700, 40);
        f.add(l1);

        l2 = new JLabel("CATEGORY");
        l2.setFont(new Font("Serif", Font.PLAIN, 30));
        l2.setBounds(100, 100, 300, 40);
        f.add(l2);

        l3 = new JLabel("Question:");
        l3.setFont(new Font("Serif", Font.PLAIN, 30));
        l3.setForeground(Color.BLACK);
        l3.setBounds(50, 150, 200, 30);
        f.add(l3);

        l4 = new JLabel("(A)");
        l4.setFont(new Font("Serif", Font.PLAIN, 30));
        l4.setBounds(50, 300, 350, 30);
        f.add(l4);

        l5 = new JLabel("(B)");
        l5.setFont(new Font("Serif", Font.PLAIN, 30));
        l5.setBounds(50, 350, 350, 30);
        f.add(l5);

        l6 = new JLabel("(C)");
        l6.setFont(new Font("Serif", Font.PLAIN, 30));
        l6.setBounds(50, 400, 350, 30);
        f.add(l6);

        l7 = new JLabel("(D)");
        l7.setFont(new Font("Serif", Font.PLAIN, 30));
        l7.setBounds(50, 450, 350, 30);
        f.add(l7);

        l8 = new JLabel("Correct Option:");
        l8.setFont(new Font("Serif", Font.PLAIN, 30));
        l8.setBounds(50, 600, 350, 30);
        f.add(l8);

        c1 = new JComboBox<>(new String[]{"General Awareness", "Reasoning"});
        c1.setFont(new Font("Serif", Font.PLAIN, 30));
        c1.setBounds(400, 100, 400, 30);
        f.add(c1);

        t1 = new JTextField();
        t1.setBounds(300, 150, 700, 60);
        t1.setFont(new Font("Serif", Font.PLAIN, 30));
        f.add(t1);

        t2 = new JTextField();
        t2.setBounds(400, 300, 200, 40);
        t2.setFont(new Font("Serif", Font.PLAIN, 30));
        f.add(t2);

        t3 = new JTextField();
        t3.setBounds(400, 350, 200, 40);
        t3.setFont(new Font("Serif", Font.PLAIN, 30));
        f.add(t3);

        t4 = new JTextField();
        t4.setBounds(400, 400, 200, 40);
        t4.setFont(new Font("Serif", Font.PLAIN, 30));
        f.add(t4);

        t5 = new JTextField();
        t5.setBounds(400, 450, 200, 40);
        t5.setFont(new Font("Serif", Font.PLAIN, 30));
        f.add(t5);

        t6 = new JTextField();
        t6.setBounds(400, 600, 200, 40);
        t6.setFont(new Font("Serif", Font.PLAIN, 30));
        f.add(t6);

        b1 = new JButton("Add Question");
        b1.setBounds(250, 700, 200, 50);
        b1.setForeground(Color.BLACK);
        b1.setFont(new Font("Serif", Font.PLAIN, 20));
        b1.addActionListener(this);
        f.add(b1);

        b2 = new JButton("Clear");
        b2.setBounds(500, 700, 200, 50);
        b2.setForeground(Color.BLACK);
        b2.setFont(new Font("Serif", Font.PLAIN, 20));
        b2.addActionListener(this);
        f.add(b2);

        
        try {
            String url = "jdbc:mysql://localhost:3306/quiz";
            String user = "root";
            String password = "Vivo123@";
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "Vivo123@");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            
            try {
                String query = "INSERT INTO questions (category, question, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, (String) c1.getSelectedItem());
                pst.setString(2, t1.getText());
                pst.setString(3, t2.getText());
                pst.setString(4, t3.getText());
                pst.setString(5, t4.getText());
                pst.setString(6, t5.getText());
                pst.setString(7, t6.getText());

                pst.executeUpdate();
                JOptionPane.showMessageDialog(f, "Question added successfully!");

                
                c1.setSelectedIndex(0);
                t1.setText("");
                t2.setText("");
                t3.setText("");
                t4.setText("");
                t5.setText("");
                t6.setText("");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == b2) {
            
            c1.setSelectedIndex(0);
            t1.setText("");
            t2.setText("");
            t3.setText("");
            t4.setText("");
            t5.setText("");
            t6.setText("");
            
            new homepage();
        }
    }

    public static void main(String[] args) {
        new QuestionModule().setVisible(true);
    }
}
