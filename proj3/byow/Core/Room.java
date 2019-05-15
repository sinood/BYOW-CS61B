package byow.Core;

import java.util.Random;
import java.io.Serializable;

public class Room implements Comparable<Room>, Serializable {
    int width;
    int height;
    int X;
    int Y;

    public Room(int x, int y, int W, int H) {
        width = W;
        height = H;
        X = x;
        Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getHEIGHT() {
        return height;
    }

    public int getWIDTH() {
        return width;
    }

    public Point getRandomFloorPoint(Random r, MapGenerator map) {
        int x;
        int y;
        if (width < 0) {
            x = -1 * (r.nextInt(Math.abs(width))) + X;
        } else {
            x = (r.nextInt(Math.abs(width))) + X;
        }
        if (height < 0) {
            y = -1 * (r.nextInt(Math.abs(height))) + Y;
        } else {
            y = (r.nextInt(Math.abs(height))) + Y;
        }
        x = Math.max(Math.min(x, map.WIDTH - 2), 1);
        y = Math.max(Math.min(y, map.HEIGHT - 2), 1);
        return new Point(x, y);
    }

    public int compareTo(Room other) {
        int sumXY = X * 100 + Y;
        int otherSumXY = other.getX() * 100 + other.getY();
        if (sumXY < otherSumXY) {
            return -1;
        } else if (sumXY > otherSumXY) {
            return 1;
        } else {
            return 0;
        }
    }
}

