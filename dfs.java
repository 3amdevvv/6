import java.util.*;

// Node class
class Node {
    String name;
    List<Node> neighbors;

    Node(String name) {
        this.name = name;
        this.neighbors = new ArrayList<>();
    }

    void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }
}

// DFS Algorithm
class SearchAlgorithms {

    public static boolean depthFirstSearch(Node start, String goal, Set<Node> visited) {
        System.out.println("Visiting: " + start.name);
        if (start.name.equals(goal)) {
            System.out.println("Goal found: " + start.name);
            return true;
        }

        visited.add(start);

        for (Node neighbor : start.neighbors) {
            if (!visited.contains(neighbor)) {
                if (depthFirstSearch(neighbor, goal, visited)) {
                    return true;
                }
            }
        }

        return false;
    }
}

// Main class to run the program
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Node> graph = new HashMap<>();

        System.out.print("Enter number of nodes: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        // Create nodes
        System.out.println("Enter node names:");
        for (int i = 0; i < n; i++) {
            String nodeName = scanner.nextLine();
            graph.put(nodeName, new Node(nodeName));
        }

        System.out.print("Enter number of edges: ");
        int e = scanner.nextInt();
        scanner.nextLine();

        // Create edges
        System.out.println("Enter edges in format <from> <to>:");
        for (int i = 0; i < e; i++) {
            String[] edge = scanner.nextLine().split(" ");
            Node from = graph.get(edge[0]);
            Node to = graph.get(edge[1]);
            if (from != null && to != null) {
                from.addNeighbor(to);
            }
        }

        System.out.print("Enter start node: ");
        String startNodeName = scanner.nextLine();
        Node startNode = graph.get(startNodeName);

        System.out.print("Enter goal node: ");
        String goalNodeName = scanner.nextLine();

        if (startNode == null || !graph.containsKey(goalNodeName)) {
            System.out.println("Invalid start or goal node.");
            return;
        }

        System.out.println("\n=== Performing DFS ===");
        boolean found = SearchAlgorithms.depthFirstSearch(startNode, goalNodeName, new HashSet<>());

        if (!found) {
            System.out.println("Goal not found.");
        }
    }
}
