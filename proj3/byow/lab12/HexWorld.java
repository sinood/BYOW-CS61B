package byow.lab12;

import org.junit.Test;

import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private final int WIDTH;
    private final int HEIGHT;
    private TETile[][] world;

    public HexWorld(int w, int h) {
        WIDTH = w;
        HEIGHT = h;
        world = new TETile[HEIGHT][WIDTH];
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                world[y][x] = Tileset.NOTHING;
            }
        }
    }


    public void addHexagon(TETile tile, int size, int rootX, int rootY) {
        int H = size * 2;
        int W = size + 2 * (size - 1);
        int xShift = getXShift(rootX, size);
        //TETile[][] hex = new TETile[H][W];//delete later
        int currW = size;
        for (int y = 0; y < H; y++) {
            int rangeStart = (W - currW) / 2;
            for (int x = 0; x < currW; x++) {
                world[x+xShift + rangeStart][y + rootY] = tile;
            }
            if (y < size-1) {
                currW += 2;
            } else if (y >= size) {
                currW -= 2;
            }
        }
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world);
    }

    private int getXShift(int x, int size) {
        return x - size + 1;
    }

    public static void main(String[] args){
        HexWorld hw = new HexWorld(20, 20);
        hw.addHexagon(Tileset.FLOWER,4, 3,0);
        /*for (int y = 0; y < hbox.length; y++) {
            for (int x = 0; x < hbox[0].length; x++) {
                System.out.print(hbox[y][x]);//change later
            }
            System.out.println();
        }//delete for loop later*/
    }
}
