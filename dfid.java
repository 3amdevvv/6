import java.util.*;

public class DFID {

    public static boolean dls(Map<String, List<String>> graph, String node, String target,
                              int depth, int limit, Set<String> visited, List<String> path) {
        path.add(node);
        System.out.println("Visiting: " + node);

        if (node.equals(target)) {
            System.out.println("Found the target!");
            System.out.println("Path to target: " + String.join(" -> ", path));
            return true;
        }

        if (depth == limit) {
            path.remove(path.size() - 1);
            return graph.getOrDefault(node, new ArrayList<>()).isEmpty();
        }

        boolean bottomReached = true;

        for (String child : graph.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(child)) {
                visited.add(child);
                boolean result = dls(graph, child, target, depth + 1, limit, visited, path);
                if (result) {
                    return true;
                }
                bottomReached = false;  // not a leaf if at least one child is not at bottom
                visited.remove(child);  // backtrack
            }
        }

        path.remove(path.size() - 1);
        return bottomReached;
    }

    public static boolean dfid(Map<String, List<String>> graph, String start, String target) {
        int depth = 0;
        while (true) {
            System.out.println("\nDepth Limit: " + depth);
            Set<String> visited = new HashSet<>();
            List<String> path = new ArrayList<>();
            visited.add(start);
            boolean found = dls(graph, start, target, 0, depth, visited, path);
            if (found) return true;

            boolean allLeaves = true;
            for (String node : graph.keySet()) {
                if (!graph.getOrDefault(node, new ArrayList<>()).isEmpty()) {
                    allLeaves = false;
                    break;
                }
            }

            if (allLeaves) return false;
            depth++;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, List<String>> graph = new HashMap<>();

        System.out.print("Enter number of nodes: ");
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.print("Node: ");
            String node = sc.nextLine();
            System.out.print("Enter neighbours of " + node + " (space-separated): ");
            String[] neighbors = sc.nextLine().split(" ");
            graph.put(node, Arrays.asList(neighbors));
        }

        System.out.print("Start node: ");
        String start = sc.nextLine();

        System.out.print("Target node: ");
        String target = sc.nextLine();

        boolean found = dfid(graph, start, target);

        if (found) {
            System.out.println("\nTarget found!");
        } else {
            System.out.println("\nTarget not found.");
        }

        sc.close();
    }
}
