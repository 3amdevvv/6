import java.util.*;

public class UCS {

    static class Node implements Comparable<Node> {
        String vertex;
        int cost;

        Node(String vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }

        public int compareTo(Node other) {
            return this.cost - other.cost;
        }
    }

    public static List<String> ucs(Map<String, Map<String, Integer>> graph, String start, String goal) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));

        Map<String, Integer> costs = new HashMap<>();
        Map<String, String> parents = new HashMap<>();
        Set<String> visited = new HashSet<>();

        for (String node : graph.keySet()) {
            costs.put(node, Integer.MAX_VALUE);
        }
        costs.put(start, 0);
        parents.put(start, null);

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            String currentNode = current.vertex;

            if (currentNode.equals(goal)) {
                return constructPath(parents, goal);
            }

            if (visited.contains(currentNode)) continue;
            visited.add(currentNode);

            for (Map.Entry<String, Integer> neighbor : graph.get(currentNode).entrySet()) {
                String next = neighbor.getKey();
                int newCost = costs.get(currentNode) + neighbor.getValue();

                if (newCost < costs.getOrDefault(next, Integer.MAX_VALUE)) {
                    costs.put(next, newCost);
                    parents.put(next, currentNode);
                    pq.offer(new Node(next, newCost));
                }
            }
        }
        return null;
    }

    public static List<String> constructPath(Map<String, String> parents, String goal) {
        List<String> path = new ArrayList<>();
        for (String at = goal; at != null; at = parents.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Map<String, Integer>> graph = new HashMap<>();

        System.out.print("Enter the number of edges: ");
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.print("Enter edge (from to cost): ");
            String[] input = sc.nextLine().split(" ");
            String u = input[0];
            String v = input[1];
            int w = Integer.parseInt(input[2]);

            graph.putIfAbsent(u, new HashMap<>());
            graph.get(u).put(v, w);

            // Ensure all nodes are in the graph, even if no outgoing edges
            graph.putIfAbsent(v, new HashMap<>());
        }

        System.out.print("Enter start node: ");
        String start = sc.nextLine();
        System.out.print("Enter goal node: ");
        String goal = sc.nextLine();

        List<String> path = ucs(graph, start, goal);
        if (path != null) {
            System.out.println("Shortest Path: " + String.join(" -> ", path));
        } else {
            System.out.println("No path found");
        }

        sc.close();
    }
}
