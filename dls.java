import java.util.*;

public class DLS {

    static void dfsRecursive(Map<String, List<String>> graph, String start, int depth, int limit, Set<String> visited) {
        visited.add(start);
        System.out.println(start);

        if (depth >= limit)
            return;

        List<String> neighbors = graph.get(start);
        if (neighbors != null) {
            for (String child : neighbors) {
                if (!visited.contains(child)) {
                    dfsRecursive(graph, child, depth + 1, limit, visited);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, List<String>> graph = new HashMap<>();

        System.out.print("Enter the number of nodes: ");
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.print("Node: ");
            String node = scanner.nextLine();
            System.out.print("Enter neighbours of " + node + " (space-separated): ");
            String[] neighbors = scanner.nextLine().split(" ");
            graph.put(node, Arrays.asList(neighbors));
        }

        System.out.print("Enter the limit: ");
        int limit = Integer.parseInt(scanner.nextLine());

        System.out.print("Start node: ");
        String start = scanner.nextLine();

        Set<String> visited = new HashSet<>();
        System.out.println("Depth Limited Search Traversal:");
        dfsRecursive(graph, start, 0, limit, visited);

        scanner.close();
    }
}
