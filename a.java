import java.util.*;

public class AStarSearch {

    static class Node implements Comparable<Node> {
        String name;
        int f; // g + h
        int g; // path cost from start
        List<String> path;

        Node(String name, int f, int g, List<String> path) {
            this.name = name;
            this.f = f;
            this.g = g;
            this.path = path;
        }

        public int compareTo(Node other) {
            return this.f - other.f;
        }
    }

    public static Map<String, List<Edge>> graph = new HashMap<>();
    public static Map<String, Integer> heuristics = new HashMap<>();

    static class Edge {
        String to;
        int cost;

        Edge(String to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    public static List<String> aStarSearch(String start, String goal) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        openList.offer(new Node(start, heuristics.get(start), 0, new ArrayList<>(List.of(start))));

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            String currName = current.name;

            if (currName.equals(goal)) {
                System.out.println("Total cost: " + current.g);
                return current.path;
            }

            if (visited.contains(currName)) continue;
            visited.add(currName);

            for (Edge neighbor : graph.getOrDefault(currName, new ArrayList<>())) {
                if (!visited.contains(neighbor.to)) {
                    int newG = current.g + neighbor.cost;
                    int newF = newG + heuristics.getOrDefault(neighbor.to, 0);
                    List<String> newPath = new ArrayList<>(current.path);
                    newPath.add(neighbor.to);
                    openList.offer(new Node(neighbor.to, newF, newG, newPath));
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of nodes: ");
        int n = Integer.parseInt(sc.nextLine());

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

        System.out.print("Enter start node: ");
        String start = sc.nextLine();

        System.out.print("Enter goal node: ");
        String goal = sc.nextLine();

        List<String> path = aStarSearch(start, goal);

        if (path != null) {
            System.out.println("Path found: " + String.join(" -> ", path));
        } else {
            System.out.println("No path found");
        }

        sc.close();
    }
}
