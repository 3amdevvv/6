import java.util.*;

public class AStarSearch {

    static class Node implements Comparable<Node> {
        String name;
        int f; // f = g + h
        int g; // g = path cost from start
        List<String> path; // path from start to current node

        Node(String name, int f, int g, List<String> path) {
            this.name = name;
            this.f = f;
            this.g = g;
            this.path = path;
        }

        @Override
        public int compareTo(Node other) {
            return this.f - other.f;
        }
    }

    static class Edge {
        String to;
        int cost;

        Edge(String to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    public static Map<String, List<Edge>> graph = new HashMap<>();
    public static Map<String, Integer> heuristics = new HashMap<>();

    public static List<String> aStarSearch(String start, String goal) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        // Add the start node to open list
        openList.offer(new Node(start, heuristics.get(start), 0, new ArrayList<>(List.of(start))));

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            String currentName = current.name;

            // If the goal is found, return the path
            if (currentName.equals(goal)) {
                System.out.println("Total cost: " + current.g);
                return current.path;
            }

            if (visited.contains(currentName)) continue;
            visited.add(currentName);

            // Expand neighbors
            for (Edge neighbor : graph.getOrDefault(currentName, new ArrayList<>())) {
                if (!visited.contains(neighbor.to)) {
                    int newG = current.g + neighbor.cost;  // New path cost
                    int newF = newG + heuristics.getOrDefault(neighbor.to, 0); // New f = g + h
                    List<String> newPath = new ArrayList<>(current.path);
                    newPath.add(neighbor.to);

                    openList.offer(new Node(neighbor.to, newF, newG, newPath));
                }
            }
        }

        // No solution found
        return null;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Read the number of nodes
        System.out.print("Enter number of nodes: ");
        int n = Integer.parseInt(sc.nextLine());

        // Read heuristic values and neighbors for each node
        for (int i = 0; i < n; i++) {
            System.out.print("Node name: ");
            String node = sc.nextLine();

            System.out.print("Heuristic value for " + node + ": ");
            int h = Integer.parseInt(sc.nextLine());
            heuristics.put(node, h);

            System.out.print("Enter neighbors of " + node + " (format: B 2 C 3): ");
            String[] input = sc.nextLine().split(" ");
            List<Edge> edges = new ArrayList<>();
            for (int j = 0; j < input.length; j += 2) {
                String neighbor = input[j];
                int cost = Integer.parseInt(input[j + 1]);
                edges.add(new Edge(neighbor, cost));
            }

            graph.put(node, edges);
        }

        // Read the start and goal nodes
        System.out.print("Enter start node: ");
        String start = sc.nextLine();

        System.out.print("Enter goal node: ");
        String goal = sc.nextLine();

        // Perform A* search
        List<String> path = aStarSearch(start, goal);

        // Output result
        if (path != null) {
            System.out.println("Path found: " + String.join(" -> ", path));
        } else {
            System.out.println("No path found");
        }

        sc.close();
    }
}

Enter number of nodes: 3
Node name: A
Heuristic value for A: 5
Enter neighbors of A (format: B 2 C 3): B 2 C 3
Node name: B
Heuristic value for B: 2
Enter neighbors of B (format: B 2 C 3): A 2 C 1
Node name: C
Heuristic value for C: 0
Enter neighbors of C (format: B 2 C 3): A 3 B 1
Enter start node: A
Enter goal node: C


