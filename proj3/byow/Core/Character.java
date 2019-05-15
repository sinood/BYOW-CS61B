package byow.Core;

import byow.TileEngine.TETile;

import java.io.Serializable;

public class Character implements Serializable {
    TETile tile;
    int X, Y;

    public Character(TETile t, int x, int y) {
        tile = t;
        X = x;
        Y = y;
    }

    public void moveTo(MapGenerator m, int x, int y) {
        if (m.getTileAT(x, y).description().equals("floor")) {
            m.swapTilesAt(X, Y, x, y);
            X = x;
            Y = y;
        }
    }
}
