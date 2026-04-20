import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

public class BFS {
    
    private Grid grid;
    
    public BFS(Grid grid) {
        this.grid = grid;
    }
    
    public List<Node> findPath(Node start, Node end) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(start);
        start.isVisited = true;
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            
            if (current == end) {
                return buildPath(end);
            }
            
            for (Node neighbour : getNeighbours(current)) {
                if (!neighbour.isVisited && !neighbour.isWall) {
                    neighbour.isVisited = true;
                    neighbour.parent = current;
                    queue.add(neighbour);
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
