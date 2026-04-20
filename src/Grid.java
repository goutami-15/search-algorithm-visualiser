public class Grid {
    int rows, cols;
    Node[][] nodes;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.nodes = new Node[rows][cols];
        initGrid();
    }

    private void initGrid() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                nodes[r][c] = new Node(r, c);
            }
        }
    }

    public Node getNode(int row, int col) {
        return nodes[row][col];
    }

    public void setWall(int row, int col) {
        nodes[row][col].isWall = true;
    }

    public void reset() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                nodes[r][c].isVisited = false;
                nodes[r][c].parent = null;
            }
        }
    }
}