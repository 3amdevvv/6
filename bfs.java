import java.util.*;

public class BFS {

    public static void bfs(Map<String, List<String>> graph, String root) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(root);
        queue.offer(root);

        while (!queue.isEmpty()) {
            String vertex = queue.poll();
            System.out.print(vertex + " ");

            for (String neighbour : graph.getOrDefault(vertex, new ArrayList<>())) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.offer(neighbour);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, List<String>> graph = new HashMap<>();

        System.out.print("Enter the number of vertices: ");
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.print("Enter node: ");
            String node = sc.nextLine();
            System.out.print("Enter neighbours of " + node + " (space-separated): ");
            String[] neighbours = sc.nextLine().split(" ");
            graph.put(node, Arrays.asList(neighbours));
        }

        System.out.print("Enter root node: ");
        String root = sc.nextLine();

        System.out.println("\nBFS Traversal:");
        bfs(graph, root);

        sc.close();
    }
}
