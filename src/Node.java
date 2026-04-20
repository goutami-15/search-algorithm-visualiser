public class Node {
    int row, col;
    boolean isWall;
    boolean isVisited;
    Node parent;
    
    // A* specific variables
    double g; // cost from start to this node
    double h; // estimated cost from this node to end
    double f; // g + h = total estimated cost
    
    public Node(int row, int col) {
        this.row = row;
        this.col = col;
        this.isWall = false;
        this.isVisited = false;
        this.parent = null;
        this.g = 0;
        this.h = 0;
        this.f = 0;
    }
}