package com.nationalgalleryroutefinder.model;

public class Room {
    private final int id;
    private final String name;
    private final String period;
    private final int x;
    private final int y;
    private final MyHashtable<String, Exhibit> exhibits;

    public Room(int id, String name, String period, int x, int y) {
        this.id = id;
        this.name = name;
        this.period = period;
        this.x = x;
        this.y = y;
        this.exhibits = new MyHashtable<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPeriod() {
        return period;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public MyHashtable<String, Exhibit> getExhibits() {
        return exhibits;
    }

    public void addExhibit(Exhibit exhibit) {
        exhibits.put(exhibit.getTitle(), exhibit);
    }

    public Exhibit getExhibit(String title) {
        return exhibits.get(title);
    }
}
