import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class DFS {
    
    private Grid grid;
    
    public DFS(Grid grid) {
        this.grid = grid;
    }
    
    public List<Node> findPath(Node start, Node end) {
        Stack<Node> stack = new Stack<>();
        stack.push(start);
        start.isVisited = true;
        
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            
            if (current == end) {
                return buildPath(end);
            }
            
            for (Node neighbour : getNeighbours(current)) {
                if (!neighbour.isVisited && !neighbour.isWall) {
                    neighbour.isVisited = true;
                    neighbour.parent = current;
                    stack.push(neighbour);
                }
            }
        }
        return new ArrayList<>(); // no path found
    }
    
    private List<Node> getNeighbours(Node node) {
        List<Node> neighbours = new ArrayList<>();
        int r = node.row;
        int c = node.col;
        
        if (r > 0) neighbours.add(grid.getNode(r-1, c)); // up
        if (r < grid.rows-1) neighbours.add(grid.getNode(r+1, c)); // down
        if (c > 0) neighbours.add(grid.getNode(r, c-1)); // left
        if (c < grid.cols-1) neighbours.add(grid.getNode(r, c+1)); // right
        
        return neighbours;
    }
    
    private List<Node> buildPath(Node end) {
        List<Node> path = new ArrayList<>();
        Node current = end;
        
        while (current != null) {
            path.add(0, current);
            current = current.parent;
        }
        return path;
    }
}