package quiz;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class quizstart extends JFrame implements ActionListener {
    private JFrame f;
    private JPanel p1;
    private JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9;
    private JButton[] b1;
    private JButton b2, b3, b4, b5;
    private Connection conn;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private String selectedOption = null;
    private Timer t;
    private int timeRemaining = 60;
    private String username;

    public quizstart(String username) {
         this.username = username;
        f = new JFrame("Quiz Page");
        f.setSize(1800, 950);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setFont(new Font("Arial Blue", Font.BOLD, 30));

        JLabel b = new JLabel("", new ImageIcon("r.jpg"), JLabel.CENTER);
        b.setBounds(300, 10, 466, 218);
        f.add(b);

        p1 = new JPanel();
        p1.setBounds(1150, 50, 500, 700);
        p1.setBackground(Color.LIGHT_GRAY);
        p1.setLayout(new GridLayout(6, 5, 0, 0)); 
        f.add(p1);

        b1 = new JButton[30];
        for (int i = 1; i <= 30; i++) {
            b1[i - 1] = new JButton(String.valueOf(i));
            b1[i - 1].setFont(new Font("Arial", Font.PLAIN, 20));
            b1[i - 1].setPreferredSize(new Dimension(40, 40));
            b1[i - 1].setBackground(Color.WHITE);
            b1[i - 1].setOpaque(true);
            b1[i - 1].addActionListener(this);
            p1.add(b1[i - 1]);
        }

        l1 = new JLabel("USER ID: " +username);
        l1.setBounds(40, 20, 240, 30);
        l1.setFont(new Font("Serif", Font.PLAIN, 30));
        f.add(l1);
      

         l3 = new JLabel("");  
        l3.setBounds(50, 60, 200, 30);
        l3.setFont(new Font("Serif", Font.PLAIN, 30));
        f.add(l3);


        l2 = new JLabel("Time :");
        l2.setBounds(1400, 5, 200, 30);
        l2.setForeground(Color.red);
        l2.setFont(new Font("Serif", Font.BOLD, 30));
        f.add(l2);

        l4 = new JLabel("d ");
        l4.setBounds(1500, 5, 200, 30);
        l4.setForeground(Color.red);
        l4.setFont(new Font("Serif", Font.BOLD, 30));
        f.add(l4);

        l5 = new JLabel("");
        l5.setBounds(10, 250, 1250, 30);
        l5.setFont(new Font("Serif", Font.PLAIN, 30));
        f.add(l5);

        l6 = createOptionLabel("A");
        l6.setBounds(40, 350, 350, 30);
        l6.setForeground(Color.BLACK);
        l6.setBackground(Color.WHITE);
        f.add(l6);

        l7 = createOptionLabel("B");
        l7.setBounds(40, 450, 350, 30);
        l7.setForeground(Color.BLACK);
        l7.setBackground(Color.WHITE);
        f.add(l7);

        l8 = createOptionLabel("C");
        l8.setBounds(40, 550, 350, 30);
        l8.setForeground(Color.BLACK);
        l8.setBackground(Color.WHITE);
        f.add(l8);

        l9 = createOptionLabel("D");
        l9.setBounds(40, 650, 350, 30);
        l9.setBackground(Color.WHITE);
        l9.setForeground(Color.BLACK);
        f.add(l9);

        b2 = new JButton("Submit");
        b2.setBounds(1300, 800, 200, 40);
        b2.setFont(new Font("Serif", Font.PLAIN, 30));
        b2.addActionListener(this);
        f.add(b2);

        b3 = new JButton("Previous");
        b3.setBounds(100, 800, 200, 40);
        b3.setFont(new Font("Serif", Font.PLAIN, 30));
        b3.addActionListener(this);
        f.add(b3);

        b4 = new JButton("Clear Response");
        b4.setBounds(350, 800, 250, 40);
        b4.setFont(new Font("Serif", Font.PLAIN, 30));
        b4.addActionListener(this);
        f.add(b4);

        b5 = new JButton("Save & Next");
        b5.setBounds(650, 800, 250, 40);
        b5.setFont(new Font("Serif", Font.PLAIN, 30));
        b5.addActionListener(this);
        f.add(b5);

        f.setVisible(true);

        try {
            String url = "jdbc:mysql://localhost:3306/quiz";
            String user = "root";
            String password = "Vivo123@";
            conn = DriverManager.getConnection(url, user, password);
            loadQuestions();
            displayQuestion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    private JLabel createOptionLabel(String option) {
        JLabel label = new JLabel("", JLabel.LEFT);
        label.setFont(new Font("Serif", Font.PLAIN, 30));
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);
        label.setForeground(Color.BLUE);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        label.addMouseListener(new OptionLabelListener(option));
        return label;
    }

    private void loadQuestions() throws SQLException {
        questions = new ArrayList<>();
        String query = "SELECT * FROM questions";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt("id");
            String category = rs.getString("category");
            String question = rs.getString("question");
            String optionA = rs.getString("option_a");
            String optionB = rs.getString("option_b");
            String optionC = rs.getString("option_c");
            String optionD = rs.getString("option_d");
            String correctOption = rs.getString("correct_option");
            questions.add(new Question(id, category, question, optionA, optionB, optionC, optionD, correctOption));
        }
    }

    private void displayQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            thankyouf();
            return;
        }

        Question q = questions.get(currentQuestionIndex);
        l5.setText("Q" + (currentQuestionIndex + 1) + ": " + q.getQuestion());
        l6.setText("A. " + q.getOptionA());
        l7.setText("B. " + q.getOptionB());
        l8.setText("C. " + q.getOptionC());
        l9.setText("D. " + q.getOptionD());

        selectedOption = null;
        resetOptionLabels();

        if (currentQuestionIndex == questions.size() - 1) {
            b5.setVisible(false);
            b2.setVisible(true);
        }

        if (t != null) {
            t.cancel();
        }
        timeRemaining = 60;
        startTimer();
    }

    private void resetOptionLabels() {
        l6.setBackground(Color.LIGHT_GRAY);
        l7.setBackground(Color.LIGHT_GRAY);
        l8.setBackground(Color.LIGHT_GRAY);
        l9.setBackground(Color.LIGHT_GRAY);
    }

    private void startTimer() {
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeRemaining--;
                SwingUtilities.invokeLater(() -> l4.setText(" " + timeRemaining + "s"));
                if (timeRemaining <= 0) {
                    t.cancel();
                    checkAnswer();
                    currentQuestionIndex++;
                    displayQuestion();
                }
            }
        }, 1000, 1000);
    }


    private void thankyouf() {
        f.dispose();
        new thankyouf(score, l1.getText()); 
    }

    
    
   private void checkAnswer() {
    if (selectedOption != null) {
        Question q = questions.get(currentQuestionIndex);
        String correctOption = q.getCorrectOption();
        
    
        if (correctOption.length() == 1 && "abcd".contains(correctOption)) {
         
            String selectedOptionLetter = getOptionLetter(selectedOption);
            if (selectedOptionLetter != null && selectedOptionLetter.equals(correctOption)) {
                score += 2; 
            }
        } else {
            
            if (selectedOption.equals(correctOption)) {
                score += 2; 
            }
        }
    }
}

   

private void saveScoreToDatabase() {
    String userId = l1.getText(); 
    try {
        String query = "INSERT INTO user_scores (user_id, score) VALUES (?, ?) ON DUPLICATE KEY UPDATE score = VALUES(score)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, userId);
        pstmt.setInt(2, score);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b2) {
            checkAnswer();
            thankyouf();
        } else if (e.getSource() == b3) {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                displayQuestion();
            }
        } else if (e.getSource() == b4) {
            resetOptionLabels();
            selectedOption = null;
        } else if (e.getSource() == b5) {
            checkAnswer();
            currentQuestionIndex++;
            displayQuestion();
        } else {
            for (int i = 0; i < b1.length; i++) {
                if (e.getSource() == b1[i]) {
                    if (i < questions.size()) {
                        currentQuestionIndex = i;
                        displayQuestion();
                    }
                    break;
                }
            }
        }
    }

    private String getOptionLetter(String selectedOption) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private class OptionLabelListener extends MouseAdapter {
        private String option;

        public OptionLabelListener(String option) {
            this.option = option;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            selectedOption = option;
            resetOptionLabels();

            
            JLabel clickedLabel = (JLabel) e.getSource();
            clickedLabel.setBackground(Color.YELLOW);
        }
    }

    private static class Question {
        private int id;
        private String category;
        private String question;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String correctOption;

        public Question(int id, String category, String question, String optionA, String optionB, String optionC, String optionD, String correctOption) {
            this.id = id;
            this.category = category;
            this.question = question;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.correctOption = correctOption;
        }

        public String getQuestion() {
            return question;
        }

        public String getOptionA() {
            return optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public String getOptionD() {
            return optionD;
        }

        public String getCorrectOption() {
            return correctOption;
        }
    }

    public static void main(String[] a) {
        new quizstart("");
    }
}
