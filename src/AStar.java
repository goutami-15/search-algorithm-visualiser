import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class AStar {
    
    private Grid grid;
    
    public AStar(Grid grid) {
        this.grid = grid;
    }
    
    public List<Node> findPath(Node start, Node end) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(
            Comparator.comparingDouble(n -> n.f)
        );
        
        start.g = 0;
        start.h = heuristic(start, end);
        start.f = start.g + start.h;
        
        openSet.add(start);
        start.isVisited = true;
        
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            
            if (current == end) {
                return buildPath(end);
            }
            
            for (Node neighbour : getNeighbours(current)) {
                if (neighbour.isWall) continue;
                
                double tentativeG = current.g + 1;
                
                if (!neighbour.isVisited || tentativeG < neighbour.g) {
                    neighbour.g = tentativeG;
                    neighbour.h = heuristic(neighbour, end);
                    neighbour.f = neighbour.g + neighbour.h;
                    neighbour.parent = current;
                    neighbour.isVisited = true;
                    openSet.add(neighbour);
                }
            }
        }
        return new ArrayList<>(); // no path found
    }
    
    private double heuristic(Node a, Node b) {
        // Manhattan distance — perfect for grid movement
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col);
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