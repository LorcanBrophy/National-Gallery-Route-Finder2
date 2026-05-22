package com.nationalgalleryroutefinder.util;

import com.nationalgalleryroutefinder.graph.Graph;
import com.nationalgalleryroutefinder.model.Exhibit;
import com.nationalgalleryroutefinder.model.Room;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVLoader {

    // loads all three CSVs and returns a fully built graph
    public Graph<Room> loadGraph(String roomsFilePath, String exhibitsFilePath, String edgesFilePath) throws IOException {
        Graph<Room> graph = new Graph<>();

        loadRooms(graph, roomsFilePath);
        loadExhibits(graph, exhibitsFilePath);
        loadEdges(graph, edgesFilePath);

        return graph;
    }

    // reads rooms.csv and adds each room as a vertex in the graph
    // format: id,name,period,x,y
    // e.g.: 151,"Fine Painters","Baroque",296,187
    // the same for each other csv file
    private void loadRooms(Graph<Room> graph, String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // skip the first line
            String currentLine = br.readLine();

            while ((currentLine = br.readLine()) != null) {

                // ignore empty lines
                if (currentLine.isEmpty()) continue;

                // split each line into individual fields
                int lineLimit = 5;
                String[] fields = currentLine.split(",", lineLimit);

                if (fields.length > lineLimit) {
                    System.err.println("Skipping room line: " + currentLine);
                    continue;
                }

                try {
                    int id = Integer.parseInt(fields[0].trim());

                    String name = fields[1].replace("\"", "").trim();
                    String period = fields[2].replace("\"", "").trim();

                    int x = Integer.parseInt(fields[3].trim());
                    int y = Integer.parseInt(fields[4].trim());

                    graph.addVertex(id, new Room(id, name, period, x, y));
                }
                catch (NumberFormatException e) {
                    System.err.println("Skipping room line: " + currentLine);
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    private void loadExhibits(Graph<Room> graph, String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String currentLine = br.readLine();

            while ((currentLine = br.readLine()) != null) {
                if (currentLine.isEmpty()) continue;

                int lineLimit = 3;
                String[] fields = currentLine.split(",", lineLimit);

                if (fields.length > lineLimit) {
                    System.err.println("Skipping exhibit line: " + currentLine);
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
                }
                catch (NumberFormatException e) {
                    System.err.println("Skipping exhibit line: " + currentLine);
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    private void loadEdges(Graph<Room> graph, String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String currentLine = br.readLine();

            while ((currentLine = br.readLine()) != null) {
                if (currentLine.isEmpty()) continue;

                int lineLimit = 3;
                String[] fields = currentLine.split(",", lineLimit);

                if (fields.length > lineLimit) {
                    System.err.println("Skipping edge line: " + currentLine);
                    continue;
                }

                try {
                    int src = Integer.parseInt(fields[0].trim());
                    int dest = Integer.parseInt(fields[1].trim());
                    double weight = Double.parseDouble(fields[2].trim());

                    graph.addUndirectedEdge(src, dest, weight);
                }
                catch (NumberFormatException e) {
                    System.err.println("Skipping edge line: " + currentLine);
                    System.err.println(e.getMessage());
                }
            }
        }
    }
}