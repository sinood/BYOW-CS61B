package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;

public class MapGenerator implements Serializable {
    TETile[][] world;
    final int WIDTH;
    final int HEIGHT;
    final TETile DEFAULT;
    final TETile WALL;
    final TETile FLOOR;
    final TETile[] defaults;
    final TETile[] floorTiles;
    final TETile[] walls;
    final Map<Integer, LightSource> lightsMap;
    final Set<LightSource> lightsSet;
    final TETile AVATAR;
    Player player;
    PriorityQueue<Room> roomPQ = new PriorityQueue();
    Set<Point> floorPoints;
    Random r;

    public MapGenerator(int W, int H, Random r) {
        this.r = r;
        WIDTH = W;
        HEIGHT = H;
        world = new TETile[W][H];
        floorTiles = new TETile[6];
        floorTiles[0] = Tileset.FLOOR1;
        floorTiles[1] = Tileset.FLOOR3;
        floorTiles[2] = Tileset.FLOOR5;
        floorTiles[3] = Tileset.FLOOR6;
        floorTiles[4] = Tileset.FLOOR5;//not used
        floorTiles[5] = Tileset.FLOOR6;//not used
        defaults = new TETile[4];
        defaults[0] = Tileset.GRASS2;
        defaults[1] = Tileset.SAND2;
        defaults[2] = Tileset.TREE2;
        defaults[3] = Tileset.TUNDRA;
        walls = new TETile[3];
        walls[0] = Tileset.WALL2;
        walls[1] = Tileset.BRICK;
        walls[2] = Tileset.DARKWALL;

        DEFAULT = getRandomTilePresetFrom(defaults);
        WALL = getRandomTilePresetFrom(walls);
        FLOOR = Tileset.FLOOR6;
        AVATAR = Tileset.AVATAR2;
        lightsMap = new HashMap<>();
        lightsSet = new HashSet<>();
        floorPoints = new HashSet<>();

        //Filling world with default tiles
        for (int i = 0; i < W; i++) {
            for (int j = 0; j < H; j++) {
                world[i][j] = DEFAULT;
            }
        }

        int limit = r.nextInt(20) + 15;
        for (int i = 0; i < limit; i++) {
            int x = r.nextInt(WIDTH - 4) + 2;
            int y = r.nextInt(HEIGHT - 4) + 2;
            int width = r.nextInt(WIDTH / 3) - (WIDTH / 6);
            int height = r.nextInt(HEIGHT / 3) - (HEIGHT / 6);
            while (Math.abs(width) < WIDTH / 8) {
                width = r.nextInt(WIDTH / 3) - (WIDTH / 6);
            }
            while (Math.abs(height) < HEIGHT / 8) {
                height = r.nextInt(HEIGHT / 3) - (HEIGHT / 6);
            }
            drawRoom(x, y, width, height);
        }
        connectAllRooms();
        surroundFloors();
        addAllLightSources();

    }


    /**
     * Returns the world TETile[][] object.
     */
    public TETile[][] getWorldGrid() {
        return world;
    }

    /**
     * Returns the TETile object at the given X, Y position.
     */
    public TETile getTileAT(int x, int y) {
        return world[x][y];
    }

    public void swapTilesAt(int xA, int yA, int xB, int yB) {
        TETile temp = getTileAT(xA, yA);
        world[xA][yA] = getTileAT(xB, yB);
        world[xB][yB] = temp;
    }

    /**
     * Draw one tile at the given x, y, position.
     */
    public void drawTileAt(int x, int y, TETile tile) {
        if (!getTileAT(x, y).description().equals("floor") || tile == AVATAR) {
            world[x][y] = tile;
        }
        if (tile.description().equals("floor")) {
            floorPoints.add(new Point(x, y));
        }
    }

    /**
     * Draws a sequence of tiles from the given x, y position in the given direction.
     */
    public void drawNTilesVertical(int x, int y, int N, TETile tile) {
        if (tile.description().equals("floor")) {
            drawNFloorTilesVertical(x, y, N);
            return;
        }
        int maxY = HEIGHT;
        int minY = 0;
        int incrementBy = 1;
        // If N is negative, draw DOWNWARD (y pos. increments by -1), for
        // abs(N) tiles
        if (N < 0) {
            incrementBy *= -1;
            N *= -1;
        }
        while (N > 0 && y >= minY && y < maxY) {
            drawTileAt(x, y, tile);
            y += incrementBy;
            N -= 1;
        }
    }

    /**
     * Draws a sequence of tiles from the given x, y position in the given direction.
     */
    public void drawNTilesHorizontal(int x, int y, int N, TETile tile) {
        if (tile.description().equals("floor")) {
            drawNFloorTilesHorizontal(x, y, N);
            return;
        }
        int maxX = WIDTH;
        int minX = 0;
        int incrementBy = 1;
        // If N is negative, draw to the LEFT (x pos. increments by -1), for
        // abs(N) tiles
        if (N < 0) {
            incrementBy *= -1;
            N *= -1;
        }
        while (N > 0 && x >= minX && x < maxX) {
            drawTileAt(x, y, tile);
            x += incrementBy;
            N -= 1;
        }
    }

    /**
     * Same as drawNTilesVertical, only can't draw adjacent to the edge of
     * the world grid.
     */
    private void drawNFloorTilesVertical(int x, int y, int N) {
        int maxY = HEIGHT - 1; //buffer for FLOOR tiles
        int minY = 1; //buffer for FLOOR tiles
        int incrementBy = 1;
        if (N < 0) {
            incrementBy *= -1;
            N *= -1;
        }
        while (N > 0 && y >= minY && y < maxY) {
            drawTileAt(x, y, FLOOR);
            y += incrementBy;
            N -= 1;
        }
    }

    /**
     * Same as drawNTilesHorizontal, only can't draw adjacent to the edge of
     * the world grid.
     */
    private void drawNFloorTilesHorizontal(int x, int y, int N) {
        int maxX = WIDTH - 1; //buffer for FLOOR tiles
        int minX = 1; //buffer for FLOOR tiles
        int incrementBy = 1;
        if (N < 0) {
            incrementBy *= -1;
            N *= -1;
        }
        while (N > 0 && x >= minX && x < maxX) {
            drawTileAt(x, y, FLOOR);
            x += incrementBy;
            N -= 1;
        }
    }

    /**
     * Draw an W by H area of tiles from the lower left x and y coordinates.
     */
    public void drawArea(int x, int y, int W, int H, TETile tile) {
        int incrementBy = 1;
        int maxX = WIDTH;
        int minX = 0;
        if (W < 0) {
            incrementBy = -1;
            W *= -1;
        }
        if (tile.description().equals("floor")) {
            maxX = WIDTH - 1;
            minX = 1;
        }
        while (W > 0 && x >= minX && x < maxX) {
            drawNTilesVertical(x, y, H, tile);
            x += incrementBy;
            W -= 1;
        }

    }


    public void drawRoom(int x, int y, int W, int H) {
        if (x >= WIDTH || y >= HEIGHT) {
            throw new IllegalArgumentException("x: " + x
                    + ", y: " + y + " are out of bounds for WIDTH: " + WIDTH
                    + ", HEIGHT: " + HEIGHT);
        }
        int wallW = W + 2; //walls will surround inner floor area on either side
        int wallH = H + 2;
        int wallX = x - 1;
        int wallY = y - 1;
        if (W < 0) {
            wallW = W - 2;
            wallX = x + 1;
        }
        if (H < 0) {
            wallH = H - 2;
            wallY = y + 1;
        }
        if (isValidRoomArea(wallX, wallY, wallW, wallH)) {
            //draw WALL area (surrounds FLOOR area by 1 tile on all sides)
            //if (wqu.connected(4040, getIndexTileSet(x, y))) {
            drawArea(wallX, wallY, wallW, wallH, WALL);
            //draw the FLOOR area
            drawArea(x, y, W, H, FLOOR);
            Room toAdd = new Room(x, y, W, H);
            roomPQ.add(toAdd);
        }
    }

    /**
     * Returns false if prospective area contains any non-default tiles.
     */
    public boolean isValidRoomArea(int x, int y, int W, int H) {
        int incrementXBy = 1;
        int incrementYBy = 1;
        if (W < 0) {
            incrementXBy = -1;
            W = -1 * W;
        }
        if (H < 0) {
            incrementYBy = -1;
            H = -1 * H;
        }
        int xIndex = x;
        int xCounter = W;
        while (xCounter > 0 && xIndex >= 0 && xIndex < WIDTH) {
            int yCounter = H;
            int yIndex = y;
            while (yCounter > 0 && yIndex >= 0 && yIndex < HEIGHT) {
                if (getTileAT(xIndex, yIndex) != DEFAULT) {
                    return false;
                }
                yIndex += incrementYBy;
                yCounter -= 1;
            }
            xIndex += incrementXBy;
            xCounter -= 1;
        }
        return true;
    }

    /**
     * Connects two rooms together using a sequence of FLOOR tiles.
     */
    public void connectRooms(Room roomA, Room roomB) {
        Point pA = roomA.getRandomFloorPoint(r, this);
        Point pB = roomB.getRandomFloorPoint(r, this);

        int xA = Math.max(Math.min(pA.x, WIDTH - 2), 1);
        int yA = Math.max(Math.min(pA.y, HEIGHT - 2), 1);
        int xB = Math.max(Math.min(pB.x, WIDTH - 2), 1);
        int yB = Math.max(Math.min(pB.y, HEIGHT - 2), 1);
        int distH = xA - xB;
        int distV = yA - yB;

        drawNTilesHorizontal(xA, yA, -1 * distH / 2, FLOOR);
        xA -= distH / 2;
        drawNTilesVertical(xA, yA, -1 * (distV), FLOOR);
        yA -= distV;
        drawNTilesHorizontal(xA, yA, -1 * (distH - distH / 2), FLOOR);
    }

    /**
     * For every room in the roomPQ, connects it to the next room in the PQ.
     */
    public void connectAllRooms() {
        PriorityQueue<Room> temp = new PriorityQueue<>();
        int limit = roomPQ.size() - 1;
        Room curr = roomPQ.poll();
        Room next = roomPQ.peek();
        for (int i = 0; i < limit; i++) {
            connectRooms(curr, next);
            temp.add(curr);
            curr = roomPQ.poll();
            next = roomPQ.peek();
        }
        roomPQ = temp;
    }

    /**
     * For every FLOOR tile, sets all adjacent tiles to WALL tiles if
     * not already a WALL tile.
     */
    public void surroundFloorPoint(int x, int y) {
        Point[] adj = new Point[8];
        adj[0] = new Point(x - 1, y);
        adj[1] = new Point(x + 1, y);
        adj[2] = new Point(x, y + 1);
        adj[3] = new Point(x, y - 1);
        adj[4] = new Point(x - 1, y - 1);
        adj[5] = new Point(x + 1, y + 1);
        adj[6] = new Point(x - 1, y + 1);
        adj[7] = new Point(x + 1, y - 1);
        for (int i = 0; i < adj.length; i++) {
            drawTileAt(adj[i].x, adj[i].y, WALL);
        }
    }

    public void surroundFloors() {
        for (Point p : floorPoints) {
            int x = p.x;
            int y = p.y;
            surroundFloorPoint(x, y);
        }
    }

    public Point getRandomPoint() {
        return new Point(r.nextInt(WIDTH), r.nextInt(HEIGHT));
    }

    public Point getFloorPoint() {
        Point p = getRandomPoint();
        while (!getTileAT(p.x, p.y).description().equals("floor")) {
            p = getRandomPoint();
        }
        return p;
    }

    public void addLightSource(Room room, boolean onOrOff) {
        Point p = room.getRandomFloorPoint(r, this);
        while (!isValidLightLocation(p)) {
            p = room.getRandomFloorPoint(r, this);
        }
        LightSource ls = new LightSource(p.x, p.y, onOrOff);
        lightsMap.put(p.x * 100 + p.y, ls);
        lightsSet.add(ls);
        world[p.x][p.y] = Tileset.LIGHT;
    }

    public boolean isValidLightLocation(Point p) {
        TETile[] adjTiles = getSurroundingTiles(p.x, p.y);
        int wallCount = 0;
        for (int i = 0; i < adjTiles.length; i++) {
            if (adjTiles[i] == WALL) {
                wallCount += 1;
            }
            if (wallCount > 5) {
                return false;
            }
        }
        return wallCount == 3 || wallCount == 5;
    }

    public void addAllLightSources() {
        for (Room room : roomPQ) {
            addLightSource(room, false);
        }
    }

    public void renderLights() {
        for (LightSource light : lightsSet) {
            light.renderLight(this);
        }
    }

    public void addPlayer() {
        Point randomPoint = getFloorPoint();
        player = new Player(AVATAR, randomPoint.x, randomPoint.y);
        drawTileAt(player.X, player.Y, player.tile);
    }

    public TETile[] getSurroundingTiles(int x, int y) {
        TETile[] adj = new TETile[8];
        adj[0] = getTileAT(x - 1, y);
        adj[1] = getTileAT(x + 1, y);
        adj[2] = getTileAT(x, y + 1);
        adj[3] = getTileAT(x, y - 1);
        adj[4] = getTileAT(x - 1, y - 1);
        adj[5] = getTileAT(x + 1, y + 1);
        adj[6] = getTileAT(x - 1, y + 1);
        adj[7] = getTileAT(x + 1, y - 1);
        return adj;
    }

    public TETile getRandomTilePresetFrom(TETile[] tiles) {
        int index = r.nextInt(tiles.length);
        return tiles[index];
    }
}
