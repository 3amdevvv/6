import java.util.*;

public class GreedyBestFirstSearch {

    static class Node implements Comparable<Node> {
        String vertex;
        int heuristic;

        Node(String vertex, int heuristic) {
            this.vertex = vertex;
            this.heuristic = heuristic;
        }

        public int compareTo(Node other) {
            return this.heuristic - other.heuristic;
        }
    }

    public static List<String> greedyBestFirstSearch(Map<String, List<String>> graph, Map<String, Integer> heuristics, String start, String goal) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        openList.offer(new Node(start, heuristics.get(start)));

        Set<String> visited = new HashSet<>();
        Map<String, String> parents = new HashMap<>();

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            String currentNode = current.vertex;
            System.out.println("Visiting: " + currentNode);

            if (currentNode.equals(goal)) {
                return constructPath(parents, goal);
            }

            visited.add(currentNode);

            for (String neighbor : graph.getOrDefault(currentNode, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    parents.put(neighbor, currentNode);
                    openList.offer(new Node(neighbor, heuristics.get(neighbor)));
                }
            }
        }

        return null;
    }

    public static List<String> constructPath(Map<String, String> parents, String goal) {
        List<String> path = new ArrayList<>();
        String current = goal;

        while (current != null) {
            path.add(current);
            current = parents.get(current);
        }

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> heuristics = new HashMap<>();

        System.out.print("Enter number of nodes: ");
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.print("Node: ");
            String node = sc.nextLine();

            System.out.print("Enter neighbours of " + node + " (space-separated): ");
            List<String> neighbors = Arrays.asList(sc.nextLine().split(" "));
            graph.put(node, neighbors);

            System.out.print("Enter heuristic value of " + node + ": ");
            int h = Integer.parseInt(sc.nextLine());
            heuristics.put(node, h);
        }

        System.out.print("Enter start node: ");
        String start = sc.nextLine();

        System.out.print("Enter goal node: ");
        String goal = sc.nextLine();

        List<String> path = greedyBestFirstSearch(graph, heuristics, start, goal);

        if (path != null) {
            System.out.println("Path: " + String.join(" -> ", path));
        } else {
            System.out.println("No path found");
        }

        sc.close();
    }
}
