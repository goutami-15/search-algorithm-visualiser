public class Node {
    int row, col;
    boolean isWall;
    boolean isVisited;
    Node parent;

    public Node(int row, int col) {
        this.row = row;
        this.col = col;
        this.isWall = false;
        this.isVisited = false;
        this.parent = null;
    }
}