import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Main extends JPanel {

    static final int ROWS = 20;
    static final int COLS = 20;
    static final int CELL = 30;

    Grid grid;
    Node startNode;
    Node endNode;
    List<Node> path;
    String mode = "wall"; // wall, start, end

    public Main() {
        grid = new Grid(ROWS, COLS);
        setPreferredSize(new Dimension(COLS * CELL + 200, ROWS * CELL));
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (mode.equals("wall")) handleClick(e.getX(), e.getY());
            }
        });
    }

    void handleClick(int x, int y) {
        int col = x / CELL;
        int row = y / CELL;
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) return;

        Node node = grid.getNode(row, col);

        if (mode.equals("start")) {
            startNode = node;
        } else if (mode.equals("end")) {
            endNode = node;
        } else if (mode.equals("wall")) {
            grid.setWall(row, col);
        }
        repaint();
    }

    void runBFS() {
        if (startNode == null || endNode == null) return;
        grid.reset();
        path = new BFS(grid).findPath(startNode, endNode);
        repaint();
    }

    void runDFS() {
        if (startNode == null || endNode == null) return;
        grid.reset();
        path = new DFS(grid).findPath(startNode, endNode);
        repaint();
    }

    void runAStar() {
        if (startNode == null || endNode == null) return;
        grid.reset();
        path = new AStar(grid).findPath(startNode, endNode);
        repaint();
    }

    void clearAll() {
        grid = new Grid(ROWS, COLS);
        startNode = null;
        endNode = null;
        path = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Node node = grid.getNode(r, c);
                int x = c * CELL;
                int y = r * CELL;

                // Fill colour
                if (node == startNode) {
                    g.setColor(new Color(39, 174, 96)); // green
                } else if (node == endNode) {
                    g.setColor(new Color(231, 76, 60)); // red
                } else if (node.isWall) {
                    g.setColor(new Color(44, 62, 80)); // dark
                } else if (path != null && path.contains(node)) {
                    g.setColor(new Color(52, 152, 219)); // blue path
                } else if (node.isVisited) {
                    g.setColor(new Color(241, 196, 15)); // yellow explored
                } else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(x, y, CELL - 1, CELL - 1);

                // Grid border
                g.setColor(new Color(200, 200, 200));
                g.drawRect(x, y, CELL - 1, CELL - 1);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Search Algorithm Visualiser");
        Main panel = new Main();

        // Buttons
        JButton bfsBtn = new JButton("Run BFS");
        JButton dfsBtn = new JButton("Run DFS");
        JButton astarBtn = new JButton("Run A*");
        JButton clearBtn = new JButton("Clear");
        JButton startBtn = new JButton("Set Start");
        JButton endBtn = new JButton("Set End");
        JButton wallBtn = new JButton("Draw Walls");

        // Legend
        JLabel legend = new JLabel(
            "<html>" +
            "<b>Legend:</b><br>" +
            "<font color='#27AE60'>■</font> Start<br>" +
            "<font color='#E74C3C'>■</font> End<br>" +
            "<font color='#2C3E50'>■</font> Wall<br>" +
            "<font color='#F1C40F'>■</font> Explored<br>" +
            "<font color='#3498DB'>■</font> Path<br>" +
            "</html>"
        );

        // Button actions
        bfsBtn.addActionListener(e -> panel.runBFS());
        dfsBtn.addActionListener(e -> panel.runDFS());
        astarBtn.addActionListener(e -> panel.runAStar());
        clearBtn.addActionListener(e -> panel.clearAll());
        startBtn.addActionListener(e -> panel.mode = "start");
        endBtn.addActionListener(e -> panel.mode = "end");
        wallBtn.addActionListener(e -> panel.mode = "wall");

        // Side panel
        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        controls.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        controls.add(new JLabel("Mode:"));
        controls.add(Box.createVerticalStrut(5));
        controls.add(startBtn);
        controls.add(Box.createVerticalStrut(5));
        controls.add(endBtn);
        controls.add(Box.createVerticalStrut(5));
        controls.add(wallBtn);
        controls.add(Box.createVerticalStrut(20));
        controls.add(new JLabel("Algorithms:"));
        controls.add(Box.createVerticalStrut(5));
        controls.add(bfsBtn);
        controls.add(Box.createVerticalStrut(5));
        controls.add(dfsBtn);
        controls.add(Box.createVerticalStrut(5));
        controls.add(astarBtn);
        controls.add(Box.createVerticalStrut(20));
        controls.add(clearBtn);
        controls.add(Box.createVerticalStrut(20));
        controls.add(legend);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.EAST);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}