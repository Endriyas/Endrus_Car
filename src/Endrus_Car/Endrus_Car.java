/*
 * @This code is written by: Endriyas in Mar 19, 2017
 * I assure that my code is 100% mistake free!!
 */
package Endrus_Car;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.SecureRandom;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class Endrus_Car extends JFrame {

    private final JMenuBar menubar;
    private final JMenu helpMenu, aboutMenu;
    private final JPanel buttenPanel, infoPanel, lifePanel;
    private final JButton startButton, newButton;
    public final JLabel scoreLabel, bomb1Label, bomb2Label;
    public static int score = 0, bomb1 = 5, bomb2 = 3;

    public Endrus_Car() {

        String help = "\n                                    ♣♣Endrus_Car♣♣\n\n"
                + "  • UP : to move white rectangle up ward.\n"
                + "  • DOWN : to move white rectangle down ward.\n"
                + "  • LEFT : to move white rectangle left ward.\n"
                + "  • RIGHT : to move white rectangle right ward.\n"
                + "  • SPACE BAR Key: to jump white rectangle.\n\n"
                + "  ♠ RED RECTANGLE: attacker, on which you have 5 chances.\n"
                + "  ♠ BLUE RECTANGLE: attacker, on which you have 3 chances.\n"
                + "  ♠ YELLOW OVAL: food, on which you get 7 points.\n\n"
                + "                                          HAVE FUN;\n";

        menubar = new JMenuBar();
        helpMenu = new JMenu("Help");
        aboutMenu = new JMenu(" About");
        JMenuItem aboutMenuItem = new JMenuItem("", new ImageIcon(getClass().getResource("me.jpg")));

        helpMenu.setFont(new Font("Courier New", Font.BOLD, 13));
        aboutMenu.setFont(new Font("Courier New", Font.BOLD, 13));

        buttenPanel = new JPanel();
        infoPanel = new JPanel(new GridLayout(1, 2));
        lifePanel = new JPanel(new GridLayout(1, 2));
        buttenPanel.setFocusable(false);
        infoPanel.setFocusable(false);
        lifePanel.setFocusable(false);
        buttenPanel.setBorder(BorderFactory.createTitledBorder(""));
        infoPanel.setBorder(BorderFactory.createTitledBorder(""));
        lifePanel.setBorder(BorderFactory.createTitledBorder(""));

        startButton = new JButton("  Pause  ");
        newButton = new JButton("New Game");
        startButton.setFont(new Font("Courier New", Font.BOLD, 15));
        newButton.setFont(new Font("Courier New", Font.BOLD, 15));
        startButton.setFocusable(false);
        newButton.setFocusable(false);
        startButton.setEnabled(false);

        scoreLabel = new JLabel("Score 0");
        bomb1Label = new JLabel("5");
        bomb2Label = new JLabel("3");
        scoreLabel.setFont(new Font("Courier New", Font.BOLD, 20));
        bomb1Label.setFont(new Font("Courier New", Font.BOLD, 20));
        bomb1Label.setForeground(Color.red);
        bomb2Label.setFont(new Font("Courier New", Font.BOLD, 20));
        bomb2Label.setForeground(Color.BLUE);

        setLayout(new BorderLayout());
        Area area = new Area();
        area.setLayout(new BorderLayout());
        area.setFocusable(true);

        aboutMenu.add(aboutMenuItem);

        menubar.add(helpMenu);
        menubar.add(aboutMenu);

        buttenPanel.add(startButton);
        buttenPanel.add(newButton);

        lifePanel.add(bomb1Label);
        lifePanel.add(bomb2Label);

        infoPanel.add(scoreLabel);
        infoPanel.add(lifePanel);

        setJMenuBar(menubar);
        add(buttenPanel, BorderLayout.NORTH);
        add(area, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

        newButton.addActionListener(e -> {
            startButton.setEnabled(true);
            area.newGame();
        });
        startButton.addActionListener(e -> {
            if (startButton.getText().equals("  Pause  ")) {
                area.t.stop();
                startButton.setText("  Start  ");
            } else if (startButton.getText().equals("  Start  ")) {
                area.t.start();
                startButton.setText("  Pause  ");
            }
        });

        helpMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                area.t.stop();
                startButton.setText("  Start  ");
                JDialog d = new JDialog();
                d.setTitle("Endru's_Car");
                d.setModal(true);
                d.setBounds(100, 100, 440, 320);
                d.setResizable(false);
                d.setLocationRelativeTo(Endrus_Car.this);
                JPanel p = new JPanel();
                JTextArea h = new JTextArea(help);
                h.setFont(new Font("Century Gothic", Font.BOLD, 13));
                h.setEditable(false);
                p.add(h);
                d.add(p);
                d.setVisible(true);
            }
        });
        aboutMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                area.t.stop();
                startButton.setText("  Start  ");
            }
        });
    }

    class Area extends JPanel {

        boolean start = true;
        Timer t;
        int x = 50, y = 400, b1x = position(), b1y = -400, b2x = position(), b2y = -500, fx = position(), fy = -2000;
        int b1inc = 6, b2inc = 5;
        int inc = 2;

        public Area() {
            setBackground(new Color(51, 51, 51));
            t = new Timer(20, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    start();
                    repaint();
                }
            });

            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (startButton.getText().equals("  Pause  ")) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_UP:
                                y -= 20;
                                break;
                            case KeyEvent.VK_DOWN:
                                y += 20;
                                break;
                            case KeyEvent.VK_SPACE:
                                y -= 150;
                                break;
                            case KeyEvent.VK_RIGHT:
                                x += 150;
                                break;
                            case KeyEvent.VK_LEFT:
                                x -= 150;
                                break;
                        }
                        repaint();
                    }
                }
            });
//            newGame();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (x > 300) {
                x = 200;
                repaint();
            } else if (x < 0) {
                x = 50;
                repaint();
            } else if (y > 400) {
                y -= 20;
                repaint();
            } else if (y < 30) {
                y += 20;
                repaint();
            } else {
                drawBasic(g);
            }
        }

        public void newGame() {
            x = 50;
            y = 400;
            b1x = position();
            b1y = -400;
            b2x = position();
            b2y = -700;
            fx = position();
            fy = -1500;
            bomb1 = 5;
            bomb2 = 3;
            score = 0;
            b1inc = 6;
            b2inc = 5;
            inc = 2;
            bomb1Label.setText(bomb1 + "");
            bomb2Label.setText(bomb2 + "");
            scoreLabel.setText("Score " + score);
            if (start) {
                start = false;
                t.start();
            }
            repaint();
        }

        public void drawBasic(Graphics g) {
            g.setColor(Color.WHITE);
            g.drawLine(147, 0, 147, 500);
            g.drawLine(150, 0, 150, 500);
            g.fillRect(x, y, 50, 50);
            for (int i = -50; i < 500; i += 60) {
                g.fillRect(75, i + 50, 3, 20);
                g.fillRect(225, i + 60, 3, 20);
            }
            drawBomb1(g);
            drawBomb2(g);
            drawFood(g);
        }

        public void drawBomb1(Graphics g) {
            g.setColor(Color.red);
            g.fillRect(b1x, b1y, 40, 40);
        }

        public void drawBomb2(Graphics g) {
            g.setColor(Color.BLUE);
            g.fillRect(b2x, b2y, 40, 40);
        }

        public void drawFood(Graphics g) {
            g.setColor(Color.YELLOW);
            g.fillOval(fx, fy, 40, 40);
        }

        public void start() {
            if (b1y > 500) {
                b1x = position();
                b1y = -50;
                score++;
                increasingValue();
            } else if (b2y > 500) {
                b2x = position();
                b2y = 0;
                score++;
                increasingValue();
            } else if (fy > 500) {
                fx = position();
                fy = -1000;
            } else {
                change();
            }
            scoreLabel.setText("Score " + score);
        }

        public void increasingValue() {
            if ((score >= 10) && (score / 10 == inc)) {
                b1inc += 1;
                b2inc += 1;
                inc+=2;
                System.out.println((score % 10) + "" + b1inc + "" + b2inc);
            }
        }

        public void change() {
            b1y += b1inc;
            b2y += b2inc;
            fy += 6;
            checkLose();
        }

        public void checkLose() {
            if ((b1x == x) && (b1y + 40 < y + 50 && b1y + 40 > y)) {
                b1x = position();
                b1y = -50;
                if (bomb1 >= 0) {
                    bomb1--;
                }
                checkLoseWhole();
                repaint();
            }
            if ((b2x == x) && (b2y + 40 < y + 50 && b2y + 40 > y)) {
                b2x = position();
                b2y = -50;
                if (bomb2 >= 0) {
                    bomb2--;
                }
                checkLoseWhole();
                repaint();
            }
            if ((fx == x) && (fy + 40 < y + 50 && fy + 40 > y)) {
                fx = position();
                fy = -1000;
                score += 7;
                repaint();
            }
        }

        public void checkLoseWhole() {
            if (bomb1 == -1 || bomb2 == -1) {
                t.stop();
                start = true;
                dialogBox("You Lose, Better Luck Next Time!\n\nScore: " + score);
            } else {
                bomb1Label.setText(bomb1 + "");
                bomb2Label.setText(bomb2 + "");
            }
        }

        public int position() {
            if (new SecureRandom().nextInt(2) == 0) {
                return 50;
            } else {
                return 200;
            }
        }

        public JTextArea messageOnJOptionPane(String message) {
            JTextArea Label = new JTextArea(message);
            Label.setEditable(false);
            Label.setFont(new Font("Century Gothic", Font.BOLD, 14));
            return Label;
        }

        void dialogBox(String message) {
            String exit = "<html><strong> Exit </strong></html>";
            String again = "<html><strong> Play Again </strong></html>";
            String[] winButtons = {exit, again};
            int accept = JOptionPane.showOptionDialog(Endrus_Car.this, messageOnJOptionPane(message), "Endru's_Car", 0, JOptionPane.INFORMATION_MESSAGE, null, winButtons, winButtons[1]);
            switch (accept) {
                case 0:
                    if (askConfirmation() == 0) {
                        System.exit(0);
                    } else {
                        dialogBox(message);
                    }
                    break;
                default:
                    newGame();
                    break;
            }
        }

        int askConfirmation() {
            return JOptionPane.showConfirmDialog(Endrus_Car.this, messageOnJOptionPane("Are You Sure?"), "Endru's_Car", JOptionPane.YES_NO_OPTION);
        }
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Endrus_Car.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Endrus_Car frame = new Endrus_Car();
        frame.setVisible(true);
        frame.setTitle("Endru's_Car");
        frame.setResizable(false);
        frame.setSize(300, 630);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}
