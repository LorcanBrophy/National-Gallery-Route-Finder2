package com.nationalgalleryroutefinder.benchmark;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.nationalgalleryroutefinder.graph.Graph;
import com.nationalgalleryroutefinder.graph.Vertices;
import com.nationalgalleryroutefinder.util.CSVLoader;
import com.nationalgalleryroutefinder.model.MyArrayList;
import com.nationalgalleryroutefinder.model.Room;
import com.nationalgalleryroutefinder.algos.BFS;
import com.nationalgalleryroutefinder.algos.Dijkstra;
import org.openjdk.jmh.annotations.*;

@Measurement(iterations = 1, time = 1)
@Warmup(iterations = 2, time = 1)
@Fork(value = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class benchmarking {

    private Graph<Room> graph;
    private int startID;
    private int endID;

    // empty avoided rooms and waypoints for benchmarking purposes
    private final MyArrayList<Room> avoid = new MyArrayList<>();
    private final MyArrayList<Integer> waypoints = new MyArrayList<>();

    @Setup(Level.Invocation)
    public void setup() throws IOException {
        CSVLoader loader = new CSVLoader();

        graph = loader.loadGraph(
                "src/main/resources/ie/setu/nationalgalleryroutefinder/rooms.csv",
                "src/main/resources/ie/setu/nationalgalleryroutefinder/exhibits.csv",
                "src/main/resources/ie/setu/nationalgalleryroutefinder/edges.csv"
        );

        // pick two different random vertices for start and end each invocation
        MyArrayList<Vertices<Room>> vertices = graph.getAllVertices();
        int startIndex = (int) (Math.random() * vertices.size());
        int endIndex;
        do endIndex = (int) (Math.random() * vertices.size());
        while (endIndex == startIndex);

        startID = vertices.get(startIndex).getData().getId();
        endID   = vertices.get(endIndex).getData().getId();
    }

    // benchmarks BFS traversal between two random rooms
    @Benchmark
    public void runBFS() {
        BFS.traverse(graph, startID, endID);
    }

    // benchmarks Dijkstra shortest path between two random rooms
    @Benchmark
    public void runDijkstra() {
        Dijkstra.traverse(graph, startID, endID, avoid);
    }

    // benchmarks Dijkstra with waypoints (empty waypoints list = same as shortest path)
    @Benchmark
    public void runDijkstraWaypoints() {
        RoutePlanner.traverseWaypoints(graph, startID, endID, avoid, waypoints);
    }
}