package byow.Core;

import byow.TileEngine.TERenderer;

import java.util.Random;

public class TestMapGenerator {
    public static void testRandomUnconnectedRooms() {

        int width = 80;
        int height = 30;
        TERenderer tr = new TERenderer();
        tr.initialize(width, height);
        long l = Long.parseLong("5197883569031643");
        Random r = new Random(l);

        MapGenerator m = new MapGenerator(width, height, r);
        tr.renderFrame(m.getWorldGrid());
    }

    public static void testOverlappingRooms() {

        int width = 80;
        int height = 30;
        TERenderer tr = new TERenderer();
        tr.initialize(width, height);
        long l = Long.parseLong("5197883569031643");
        Random r = new Random(l);

        MapGenerator m = new MapGenerator(width, height, r);

        m.drawRoom(10, 10, 10, 10);
        m.drawRoom(18, 18, -10, -10);
        tr.renderFrame(m.getWorldGrid());
    }

    public static void main(String[] args) {
        testRandomUnconnectedRooms();
        //testOverlappingRooms();
    }
}
