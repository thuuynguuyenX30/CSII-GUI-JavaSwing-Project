import javax.swing.*;
import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.FocusListener;
import java.awt.geom.AffineTransform;
import java.awt.event.FocusEvent;

public class HappyNumber extends JFrame {
    private JTextField inputField; // input field - get number from user
    private static JTextArea resultArea; // result area - show the result
    private check_isHappyNum checker; // check if the number is happy
    private int randomNumber; // random number for challenge mode
    private HappyNumberPanel happyNumberPanel; // panel to draw the sequence

    public HappyNumber() {
        JPanel buttonPanel = new JPanel(); // create a panel to contain buttons and other input components
        happyNumberPanel = new HappyNumberPanel(); // result panel that include the result area
        checker = new check_isHappyNum(happyNumberPanel); // initialize the checker with the result panel
        JFrame frame = new JFrame("Happy Number Checker"); // create the main application frame with the title "Happy
                                                           // Number Checker"
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(getWidth(), getHeight()); //
        frame.setLayout(new BorderLayout()); // set the layout manager for the JFrame is BorderLayout
        frame.setBackground(Color.PINK); // sets the background color of the JFrame to pink.

        // CREATE PANEL FOR BUTTON VISUALIZATION

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // set the layout manager for the
                                                                             // buttonPanel is BoxLayout.Y_AXIS
        buttonPanel.setBounds(100, 100, 300, 400); // set size for buttonPanel
        buttonPanel.setBackground(new Color(254, 197, 216)); // set color
        // Define the introduction text for Happy Number
        String intro = "             \r\n" +
                "       Happy number is a positive integer which \r\n" +
                "       eventually reaches 1 when replaced by the sum of the \r\n" +
                "       square of each digit\r\n" +
                "            Example 1: n = 19 \r\n" +
                "            Output: 19 is a happy number \r\n" +
                "            Explain: 1^2 + 9^2 = 82; 8^2  + 2^2 = 68\r\n" +
                "            6^2 + 8^2 = 100; 1^2 + 0^2 + 0^2 = 1 \r\n" +
                "             \r\n   ";
        // Create and configure the introduction text area
        JTextArea introductionLabel = new JTextArea(intro);
        introductionLabel.setEditable(false);
        introductionLabel.setLineWrap(true);
        introductionLabel.setWrapStyleWord(true);
        introductionLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        introductionLabel.setForeground(new Color(186, 28, 80));
        introductionLabel.setPreferredSize(new Dimension(450, 200));
        introductionLabel.setBackground(Color.WHITE);
        introductionLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // set the position of text is in the central of
                                                                     // text area
        // Create and configure the mode 1: user input number and check
        JLabel mode1Label = new JLabel("Happy Number Checker", SwingConstants.CENTER);
        mode1Label.setFont(new Font("Roboto", Font.BOLD, 17));
        mode1Label.setForeground(new Color(186, 28, 80));
        mode1Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create and configure the input field
        inputField = new JTextField();
        inputField.setMaximumSize(new Dimension(175, 80)); // set the size for input field
        inputField.setText("Enter your valid number");
        inputField.setForeground(Color.GRAY);

        // Implement a placeholder for the user identification input box
        inputField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String input = inputField.getText();
                if (input.length() > 0) {
                    inputField.setText(""); // when the user click to the input field, the initial text is displace by a
                                            // blank space
                    inputField.setForeground(Color.BLACK); // change the color of input number into BLACK
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (inputField.getText().isEmpty()) {
                    inputField.setForeground(Color.GRAY);
                    inputField.setText("Enter your valid number"); // The initial text displays in the text area
                }
            }
        });
        // Configure the input field
        inputField.setFont(new Font("Serif", Font.BOLD, 15));
        inputField.setBackground(Color.WHITE);
        inputField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        inputField.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Create and configure the check button
        JButton checkButton = new JButton("Check");
        checkButton.setBackground(new Color(0x1BC444));
        checkButton.setFocusPainted(false); // Remove focus border
        checkButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Define the behavior in response to the check button click
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int number = Integer.parseInt(inputField.getText());
                    // call happy number checking method
                    checker.printHappySequence(number);
                } catch (NumberFormatException ex) {
                    // throw a message if the input is invalid
                    JOptionPane.showMessageDialog(null, "Please enter a valid integer less than 6 digits.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    // note: the program will not crash if the user enters an interger mỏe than 6 digits but the sum sequence will be too long to display
                }
            }
        });
        // Mode 2: Generate random number and guess whether the happy number or not
        JLabel mode2Label = new JLabel("Challenge Yourself!"); // title of mode 2
        mode2Label.setFont(new Font("Roboto", Font.BOLD, 17));
        mode2Label.setForeground(new Color(186, 28, 80));
        mode2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Generate random number button
        JButton generateButton = new JButton("Generate Random Number");
        generateButton.setFocusPainted(false);
        generateButton.setBackground(new Color(0xFE6C2B));
        generateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Define the behavior in response to the generate button click
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomNumber = (int) (Math.random() * 1000); // generates a random integer between 0 and 999 and assigns
                                                             // it to the variable randomNumber.
                inputField.setText(String.valueOf(randomNumber));
            }
        });
        // Result area to show the visualization
        JLabel resultLabel = new JLabel("Is this a Happy Number?");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 17));
        resultLabel.setForeground(new Color(186, 28, 80));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Create and configure the yes/no button for checking the result using
        // JRadioButton to ensure that only one answer is chosen
        JRadioButton yesButton = new JRadioButton("Yes");
        JRadioButton noButton = new JRadioButton("No");
        ButtonGroup group = new ButtonGroup();
        group.add(yesButton);
        group.add(noButton);
        // set logic for yes button
        yesButton.setFocusPainted(false);
        yesButton.setBackground(new Color(0x1BC444));
        yesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        // set logic for no button
        noButton.setFocusPainted(false);
        noButton.setBackground(new Color(0xDB5230));
        noButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        // Add components & buttons to the left panel and adjust their positions
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 6)); // create a border line for panel
        buttonPanel.add(introductionLabel);
        buttonPanel.add(Box.createVerticalStrut(20)); // creates an invisible component has size of 20 pixels implied to
                                                      // create space between components
        buttonPanel.add(mode1Label);
        buttonPanel.add(Box.createVerticalStrut(35));
        buttonPanel.add(inputField);
        buttonPanel.add(Box.createVerticalStrut(25));
        buttonPanel.add(checkButton);
        buttonPanel.add(Box.createVerticalStrut(60));
        buttonPanel.add(mode2Label);
        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(generateButton);
        buttonPanel.add(Box.createVerticalStrut(70));
        buttonPanel.add(resultLabel);
        buttonPanel.add(Box.createVerticalStrut(40));
        buttonPanel.add(yesButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(noButton);
        buttonPanel.add(Box.createVerticalStrut(100));
        // Add title in the right panel
        JLabel rightLabel = new JLabel("Happy Number Checker", SwingConstants.CENTER);
        rightLabel.setFont(new Font("Roboto", Font.BOLD, 60));
        rightLabel.setForeground(new Color(186, 28, 80));
        rightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        happyNumberPanel.add(rightLabel);
        happyNumberPanel.setBackground(new Color(255, 236, 233));
        // add 2 bigs panel in frame
        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(happyNumberPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false); // fixed the size of frame
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // set the frame to the maximum size of the screen to maximize
                                                       // visibility
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HappyNumber();
            }

        });
    }

    public static JTextArea getResultArea() {
        return resultArea;
    }

}

// SET LOGIC TO VISUALIZE THE SEQUENCE OF NUMBERS
class HappyNumberPanel extends JPanel {
    private List<Integer> sequence = new ArrayList<>(); // List to store the sequence of numbers

    public void setSequence(List<Integer> sequence, int cycleStartIndex) {
        this.sequence = sequence;
        this.cycleStartIndex = cycleStartIndex;

        repaint();
    }

    private int cycleStartIndex = -1; // index of the start of the cycle in sequence, if one exists
    private int nodeIndexToDraw = 0; // index of the node to draw up to during the animation
    private Timer timer; // Timer to control the animation
    public boolean drawCycleArrow = false;
    public boolean highlightCycleNode = false;
    private boolean drawToNull = false;

    public HappyNumberPanel() {
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Logic to determine the state of the animation
                if (nodeIndexToDraw < sequence.size()) {
                    nodeIndexToDraw++;
                    repaint();
                } else if (sequence.get(sequence.size() - 1) == 1 && !drawToNull) {
                    // when last node = 1, set flag to null
                    drawToNull = true;
                    repaint();
                } else if (drawToNull) { // if the null pointer arrow has been drawn, reset false
                    drawToNull = false;
                    timer.stop();
                } else if (!drawCycleArrow && cycleStartIndex != -1) {
                    drawCycleArrow = true; // if the cycle arrow has been drawn but haven't highlighted the cycle node
                                           // yet, then set the flag true to highlight
                    repaint();
                } else if (drawCycleArrow && !highlightCycleNode) {
                    highlightCycleNode = true; // set the flag to highlight the cycle node
                    repaint();
                } else {
                    timer.stop();
                }
            }
        });
    }

    class MovingTextPanel extends JPanel {
        private static final int DELAY_MS = 20; // Delay in milliseconds
        private static int xPosition = 0; // Initial x-coordinate for the text
        private String movingText = "Happy Number Checker"; // Text to move

        public MovingTextPanel() {
            Timer timer = new Timer(DELAY_MS, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    xPosition += 2; // Move the text by 2 pixels
                    repaint(); // Redraw the panel
                }
            });
            timer.start();
        }

        // Method to paint the sequence of numbers and graphics
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK); // Set text color (adjust as needed)
            g.drawString(movingText, xPosition, getHeight() / 2);
        }
    }

    public void startDrawingSequence() {
        nodeIndexToDraw = 0;
        drawCycleArrow = false;
        highlightCycleNode = false;
        drawToNull = false;
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sequence.isEmpty()) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int radius = 20;
        int padding = 10;
        int x = padding;
        int y = getHeight() / 2;

        for (int i = 0; i < nodeIndexToDraw; i++) {
            int number = sequence.get(i);
            boolean isCycleStartNode = (i == cycleStartIndex);
            boolean isHappyOneNode = (number == 1);

            // Draw the node
            if (isHappyOneNode && i == sequence.size() - 1) {
                g2d.setColor(Color.GREEN); // Happy one Node
            } else if (isCycleStartNode && highlightCycleNode) {
                g2d.setColor(Color.RED); // Cycle start node
            } else {
                g2d.setColor(Color.PINK); // Regular node
            }
            g2d.fillOval(x, y - radius, 2 * radius, 2 * radius);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x, y - radius, 2 * radius, 2 * radius);

            String numberStr = String.valueOf(number);
            FontMetrics fm = g2d.getFontMetrics();
            int numberX = x + radius - fm.stringWidth(numberStr) / 2;
            int numberY = y + fm.getHeight() / 2 - fm.getDescent();
            g2d.drawString(numberStr, numberX, numberY);

            // draw an arrow to the next node if this is not the last node
            if (i < sequence.size() - 1) {
                // calculate the starting point of the arrow(right edge of the current node)
                int startX = x + radius * 2;
                int startY = y;
                // calculate the ending point of the arrow (left edge of the next node)
                int endX = x + 3 * radius + padding - (radius);
                int endY = y;
                drawArrow(g2d, startX, startY, endX, endY); // draw the arrow between the current node and the next node
            }
            x += 2 * radius + padding; // update the x position for the next node
        }

        // if the last node is 1, draw an arrow pointing to null
        if (drawToNull && sequence.get(sequence.size() - 1) == 1) {
            int startX = x - radius + padding;
            int nullX = startX + padding + 20;
            drawArrowToPointingToNull(g2d, startX, y, nullX, y);
            drawNullPointer(g2d, nullX, y);
        }

        // draw a cycle arrow and a cycle has been detected
        if (drawCycleArrow && cycleStartIndex != -1 && cycleStartIndex < sequence.size() - 1) {
            // calculate the x position of the last unique node before the cycle starts
            int lastUniqueX = padding + (sequence.size() - 1) * (2 * radius + padding);
            // calculate the x position of the node where the cycle starts
            int cycleStartX = padding + cycleStartIndex * (2 * radius + padding);
            // draw a curved arrow from the last unique node to the cycle start node
            drawCurvedArrow(g2d, lastUniqueX, y, cycleStartX, y, radius);
        }

        // highlight the cycle start node and a cycle has been detected
        if (highlightCycleNode && cycleStartIndex != -1) {
            int cycleStartX = padding + cycleStartIndex * (2 * radius + padding);
            highlightNode((Graphics2D) g, cycleStartX, y, radius);
        } else {
        }
    }

    // Method to highlight a specific node in the sequence
    private void highlightNode(Graphics2D g2d, int x, int y, int radius) {
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(x, y - radius, 2 * radius, 2 * radius);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x, y - radius, 2 * radius, 2 * radius);

        // number highlight
        String numberStr = String.valueOf(sequence.get(cycleStartIndex));
        FontMetrics fm = g2d.getFontMetrics();
        int numberX = x + radius - fm.stringWidth(numberStr) / 2;
        int numberY = y + fm.getHeight() / 2 - fm.getDescent();
        g2d.drawString(numberStr, numberX, numberY);
    }

    private void drawArrowToPointingToNull(Graphics2D g2, int x1, int y1, int x2, int y2) {
        drawArrow(g2, x1, y1, x2, y2);
    }

    private void drawNullPointer(Graphics2D g2, int x, int y) {
        g2.drawString(" NULL", x, y + 5);
    }

    private void drawCurvedArrow(Graphics2D g2, int x1, int y1, int x2, int y2, int radius) {
        // calculate the angle btw the start and end points
        double angle = Math.atan2(y2 - y1, x2 - x1);

        // starting point of the curve on the circumference of the start node
        int startX = (int) (x1 + 12 + Math.cos(angle - Math.PI / 2) * radius);
        int startY = (int) (y1 - Math.sin(angle - Math.PI / 2) * radius);

        // determine the ending point of the curve on the circumference of the end node
        int endX = (int) (x2 + Math.cos(angle + Math.PI - 1) * radius);
        int endY = (int) (y2 + Math.sin(angle + Math.PI - 1) * radius);

        // calculate control points for the quadratic curve to create a smooth arc
        int ctrlX = (startX - endX + 6) / 2;
        int ctrlY = startY - Math.abs(startX - endX) / 3;
        g2.setColor(Color.BLUE); // curve
        g2.setStroke(new BasicStroke(3));
        QuadCurve2D q = new QuadCurve2D.Float();
        q.setCurve(startX, startY, ctrlX, ctrlY, endX, endY);
        g2.draw(q);
        // draw the arrow head at the end of the curve
        drawArrowhead(g2, ctrlX, ctrlY, endX, endY);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
    }

    private void drawArrowhead(Graphics2D g2, int x1, int y1, int x2, int y2) {
        // calculate the angle of the line
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowLength = 10;
        double arrowAngle = Math.PI / 6; // 30 degrees for the arrowhead

        // calculate the points of the arrowhead
        double x3 = x2 - arrowLength * Math.cos(angle - arrowAngle);
        double y3 = y2 - arrowLength * Math.sin(angle - arrowAngle);
        double x4 = x2 - arrowLength * Math.cos(angle + arrowAngle);
        double y4 = y2 - arrowLength * Math.sin(angle + arrowAngle);

        int[] xPoints = { x2, (int) x3, (int) x4 };
        int[] yPoints = { y2, (int) y3, (int) y4 };
        g2.fillPolygon(xPoints, yPoints, 3);
    }

    // draw a straight arrow between two points
    private void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
        Graphics2D g2 = (Graphics2D) g.create();

        // calculate the distance and angle between the start and end points
        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        // create affine transformation for the rotation and translation
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        // apply the transformation
        g2.transform(at);
        // draw the line for the arrow shaft and arrowhead
        g2.drawLine(0, 0, len, 0);
        g2.fillPolygon(new int[] { len, len - 5, len - 5, len },
                new int[] { 0, -5, 5, 0 }, 4);
    }
}

// NODE CLASS
class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }

}

// CLASS TO CHECK IF THE NUMBER IS HAPPY
class check_isHappyNum {
    private Node head;
    private HappyNumberPanel happyNumberPanel;

    public check_isHappyNum(HappyNumberPanel happyNumberPanel) {
        this.happyNumberPanel = happyNumberPanel;
    }

    // calculate the next number in the sequence by summing the squares of the
    // digits of the current number
    public int getNext(int n) {
        int totalSum = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            totalSum += d * d;
        }
        return totalSum;
    }

    // Determine if the number is happy using the fast and slow pointer technique to
    // detect cycle
    public boolean isHappy(int n) {
        int slow = n;
        int fast = getNext(n);

        while (fast != 1 && slow != fast) {
            slow = getNext(slow);
            fast = getNext(getNext(fast));
        }

        return fast == 1;
    }

    // Print the sequence of numbers to decide whether a number is happy or not
    public void printHappySequence(int n) {
        head = new Node(n);
        Node current = head;
        String result = String.valueOf(current.data);
        List<Integer> sequenceList = new ArrayList<>(); // Initialize the list to store the sequence
        sequenceList.add(current.data); // Add the number to the sequence list
        int cycleStartIndex = -1; // Initialize the cycle start index

        while (true) {

            int nextValue = getNext(current.data);
            if (nextValue == 1) {
                sequenceList.add(nextValue);
                result += "-->" + nextValue + " Yay number is happy!";
                break;
            } else if (contains(head, nextValue)) {
                cycleStartIndex = sequenceList.indexOf(nextValue);
                // result += "-->" + nextValue + "(cycle detected - Number is Unhappy :< )";
                // when cycle detected, set flag
                happyNumberPanel.drawCycleArrow = true;
                happyNumberPanel.highlightCycleNode = true;
                break;
            }
            result += "-->" + nextValue;
            current.next = new Node(nextValue);
            current = current.next;
            sequenceList.add(nextValue);
        }
        happyNumberPanel.setSequence(sequenceList, cycleStartIndex);
        if (cycleStartIndex != -1) {
            happyNumberPanel.drawCycleArrow = true;
            happyNumberPanel.highlightCycleNode = true;
        }
        happyNumberPanel.setSequence(sequenceList, cycleStartIndex);
        happyNumberPanel.startDrawingSequence();
    }

    // check if the number is already in the linked list
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
}
