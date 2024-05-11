import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HappyNumber extends JFrame {
    private JTextField inputField;
    private static JTextArea resultArea;
    private check_isHappyNum checker;
    private int randomNumber;

    public HappyNumber() {
        checker = new check_isHappyNum();

        JFrame frame = new JFrame("Happy Number Checker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.GREEN);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.PINK);

        String intro = "  Happy number: A positive integer is a number which \r\n" +
                "  eventually reaches 1 when replaced by the sum of the \r\n" +
                "  square of each digit\r\n" +
                "  Example 1: n = 19 \r\n" +
                "  Output: 19 is a happy number \r\n" +
                "  Explain: 1^2 + 9^2 = 82; 8^2  + 2^2 = 68\r\n" +
                "  6^2 + 8^2 = 100; 1^2 + 0^2 + 0^2 = 1";

        JTextArea introductionLabel = new JTextArea(intro);
        introductionLabel.setEditable(false);
        introductionLabel.setLineWrap(true);
        introductionLabel.setWrapStyleWord(true);
        introductionLabel.setFont(new Font("Serif", Font.BOLD, 16));
        introductionLabel.setForeground(Color.PINK);
        introductionLabel.setPreferredSize(new Dimension(450, 150));
        introductionLabel.setBackground(Color.WHITE);

        JLabel mode1Label = new JLabel("Happy Number Checker");
        mode1Label.setFont(new Font("Arial", Font.BOLD, 17));

        inputField = new JTextField(10);
        inputField.setMaximumSize(new Dimension(450, 100));
        inputField.setText("Enter your valid number here");

        JButton checkButton = new JButton("Check");
        checkButton.setBackground(Color.GREEN);
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int number = Integer.parseInt(inputField.getText());
                    checker.printHappySequence(number);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid integer.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JLabel mode2Label = new JLabel("Challenge Yourself!");
        mode2Label.setFont(new Font("Arial", Font.BOLD, 17));

        JButton generateButton = new JButton("Generate Random Number");
        generateButton.setBackground(Color.ORANGE);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomNumber = (int) (Math.random() * 1000);
                inputField.setText(String.valueOf(randomNumber));
            }
        });

        JLabel resultLabel = new JLabel("Is this a Happy Number?");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 17));

        JButton yesButton = new JButton("Yes");
        yesButton.setBackground(Color.GREEN);
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isHappy = checker.isHappy(randomNumber);
                if (isHappy) {
                    JOptionPane.showMessageDialog(null, "AWESOME!", "Result", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Oh no, you lose", "Result", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton noButton = new JButton("No");
        noButton.setBackground(Color.RED);
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isHappy = checker.isHappy(randomNumber);
                if (!isHappy) {
                    JOptionPane.showMessageDialog(null, "AWESOME!", "Result", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Oh no, you lose", "Result", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton explainButton = new JButton("Explain");

        JButton clearButton = new JButton("Clear Canvas");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultArea.setText("");
            }
        });

        leftPanel.add(introductionLabel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(mode1Label);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(inputField);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(checkButton);
        leftPanel.add(Box.createVerticalStrut(100));
        leftPanel.add(mode2Label);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(generateButton);
        leftPanel.add(Box.createVerticalStrut(100));
        leftPanel.add(resultLabel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(yesButton);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(noButton);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(explainButton);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(clearButton);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.lightGray);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setFont(new Font("Serif", Font.BOLD, 16));
        resultArea.setForeground(Color.BLACK);
        resultArea.setPreferredSize(new Dimension(600, 300));
        resultArea.setBackground(Color.WHITE);

        rightPanel.add(resultArea);

        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        HappyNumber happyNumber = new HappyNumber();
    }

    public static JTextArea getResultArea() {
        return resultArea;
    }
}

class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class check_isHappyNum {
    private Node head;

    public int getNext(int n) {
        int totalSum = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            totalSum += d * d;
        }
        return totalSum;
    }

    public boolean isHappy(int n) {
        head = new Node(n);
        Node current = head;

        while (current.data != 1 && current.next != null) {
            int nextValue = getNext(current.data);
            current.next = new Node(nextValue);
            current = current.next;
        }
        return current.data == 1;
    }

    public void printHappySequence(int n) {
        head = new Node(n);
        Node current = head;
        String result = String.valueOf(current.data);

        while (true) {
            int nextValue = getNext(current.data);
            if (nextValue == 1 || contains(head, nextValue)) {
                if (nextValue == 1) {
                    result += "-->" + nextValue + " Yay number is happy!";
                } else {
                    result += "-->" + nextValue + "(cycle detected - Number is Unhappy :< )";
                }
                break;
            }
            result += "-->" + nextValue;
            current.next = new Node(nextValue);
            current = current.next;
        }

        HappyNumber.getResultArea().append(result + "\n");
    }

    private boolean contains(Node head, int value) {
        Node current = head;
        while (current != null) {
            if (current.data == value) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean detect_cycle() {
        if (head == null) {
            return false;
        }
        Node slow = head;
        Node fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }
}
