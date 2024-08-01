package quiz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class welcomepage extends JFrame implements ActionListener {
    private JTextArea t1;
    private JCheckBox c1;
    private JButton b1;
    private JLabel l1;
    private String username;

    public welcomepage(String username) {
        this.username = username;
        setTitle("Welcome to the Quiz");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        l1 = new JLabel("Welcome, " + username);
        l1.setFont(new Font("Serif", Font.BOLD, 24));
        l1.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(l1, BorderLayout.NORTH);

        t1 = new JTextArea();
        t1.setText("Rules of the Quiz:\n\n"
                + "1. Each quiz consists of multiple questions.\n"
                + "2. You have a limited time to answer each question.\n"
                + "3. No negative marking for wrong answers.\n"
                + "4. Make sure to answer all questions to the best of your knowledge.\n"
                + "5. Click 'Next' to proceed to the quiz.\n");
        t1.setFont(new Font("Serif", Font.PLAIN, 18));
        t1.setEditable(false);
        t1.setMargin(new Insets(40, 50, 20, 40));
        mainPanel.add(new JScrollPane(t1), BorderLayout.CENTER);

        c1 = new JCheckBox("I agree to the terms and conditions");
        c1.setFont(new Font("Serif", Font.PLAIN, 18));
        c1.addActionListener(this);
        JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkboxPanel.add(c1);
        mainPanel.add(checkboxPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        b1 = new JButton("Next");
        b1.setFont(new Font("Serif", Font.BOLD, 20));
        b1.addActionListener(this);
        b1.setEnabled(false);
        add(b1, BorderLayout.SOUTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == c1) {
            b1.setEnabled(c1.isSelected());
        } else if (e.getSource() == b1) {
            new quizstart(username);
            
            dispose();
        }
    }

    public static void main(String[] args) {
        new welcomepage("testuser").setVisible(true);
    }
}
