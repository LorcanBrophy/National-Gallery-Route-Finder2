package com.nationalgalleryroutefinder.util;

import com.nationalgalleryroutefinder.graph.Graph;
import com.nationalgalleryroutefinder.model.Exhibit;
import com.nationalgalleryroutefinder.model.Room;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVLoader {

    //loads all three CSVs and returns a fully built graph
    public Graph<Room> loadGraph(String roomsFilePath, String exhibitsFilePath, String edgesFilePath) throws IOException {
        Graph<Room> graph = new Graph<>();

        loadRooms(graph, roomsFilePath);
        loadExhibits(graph, exhibitsFilePath);
        loadEdges(graph, edgesFilePath);

        return graph;
    }

    // reads rooms.csv and adds each room as a vertex in the graph
    // format: id,name,period,x,y
    // e.g: 151,"Fine Painters","Baroque",296,187
    // the same for each other csv file
    private void loadRooms(Graph<Room> graph, String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] fields = line.split(",", 5);
                if (fields.length < 5) {
                    System.err.println("Skipping room line: " + line);
                    continue;
                }

                try {
                    int id = Integer.parseInt(fields[0].trim());
                    String name = fields[1].replace("\"", "").trim();
                    String period = fields[2].replace("\"", "").trim();
                    int x = Integer.parseInt(fields[3].trim());
                    int y = Integer.parseInt(fields[4].trim());

                    graph.addVertex(id, new Room(id, name, period, x, y));
                } catch (NumberFormatException e) {
                    System.err.println("Skipping room line: " + line);
                }
            }
        }
    }

    private void loadExhibits(Graph<Room> graph, String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] fields = line.split(",", 3);
                if (fields.length < 3) {
                    System.err.println("Skipping exhibit line: " + line);
                    continue;
                }
                try {
                    int roomId = Integer.parseInt(fields[0].trim());
                    String title = fields[1].replace("\"", "").trim();
                    String artist = fields[2].replace("\"", "").trim();
                    var vertex = graph.getVertex(roomId);
                    if (vertex == null) {
                        System.err.println("room ID not found: " + roomId);
                        continue;
                    }
                    vertex.getData().addExhibit(new Exhibit(title, artist));

                } catch (NumberFormatException e) {
                    System.err.println("Skipping exhibit line: " + line);
                }
            }
        }
    }

    private void loadEdges(Graph<Room> graph, String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] fields = line.split(",", 3);
                if (fields.length < 3) {
                    System.err.println("Skipping edge line: " + line);
                    continue;
                }

                try {
                    int src = Integer.parseInt(fields[0].trim());
                    int dest = Integer.parseInt(fields[1].trim());
                    double weight = Double.parseDouble(fields[2].trim());

                    graph.addUndirectedEdge(src, dest, weight);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping edge line: " + line);
                }
            }
        }
    }
}