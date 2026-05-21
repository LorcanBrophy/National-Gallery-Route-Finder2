package ie.setu.nationalgalleryroutefinder.algos;
import ie.setu.nationalgalleryroutefinder.Graphs.Edge;
import ie.setu.nationalgalleryroutefinder.Graphs.Graph;
import ie.setu.nationalgalleryroutefinder.Graphs.Vertices;
import ie.setu.nationalgalleryroutefinder.model.MyArrayList;
import ie.setu.nationalgalleryroutefinder.model.MyHashtable;

public class BFS {
    public static <T> MyArrayList<T> traverse(Graph<T> graph, int startID, int endID) {
        // final correct path to be returned
        MyArrayList<T> correctPath = new MyArrayList<>();

        Vertices<T> start = graph.getVertex(startID);
        Vertices<T> end = graph.getVertex(endID);
        if (start == null || end == null) return correctPath;
        // tracks visited vertices using identityHashCode as the key
        MyHashtable<Integer, Vertices<T>> visited = new MyHashtable<>();
        visited.put(System.identityHashCode(start), start);
        // add to back (add), remove from front (remove(0))
        MyArrayList<Vertices<T>> queue = new MyArrayList<>();
        queue.add(start);
        // stores parent of each vertex to reconstruct the path later
        // key = child's identity, value = parent vertex
        MyHashtable<Integer, Vertices<T>> parent = new MyHashtable<>();
        parent.put(System.identityHashCode(start), null);
        boolean found = false;
        while (!queue.isEmpty()) {

            // remove from front to maintain FIFO order
            Vertices<T> current = queue.remove(0);
            if (current == end) {
                found = true;
                break;
            }

            // iterate through neighbours of current vertex
            for (Edge<T> edge : current.getEdges()) {
                Vertices<T> neighbour = edge.getDestination();
                int neighbourID = System.identityHashCode(neighbour);
                // only visit unvisited neighbours
                if (!visited.containsKey(neighbourID)) {
                    visited.put(neighbourID, neighbour);
                    queue.add(neighbour);
                    // record that current is the parent of this neighbour
                    parent.put(neighbourID, current);
                }
            }
        }
        if (!found) return correctPath;
        // reconstruct path by walking back through parent map from end to start
        MyArrayList<Vertices<T>> reversePath = new MyArrayList<>();
        Vertices<T> current = end;
        while (current != null) {
            reversePath.add(current);
            current = parent.get(System.identityHashCode(current));
        }
        // reversePath, reverse it into correctPath
        for (int i = reversePath.size() - 1; i >= 0; i--) {
            correctPath.add(reversePath.get(i).getData());
        }
        return correctPath;
    }
}