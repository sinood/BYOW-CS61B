package byow.Core;

import byow.TileEngine.TETile;

import java.io.Serializable;

public class LightSource implements Serializable {
    int X, Y;
    boolean on;

    public LightSource(int x, int y, boolean onOrOff) {
        X = x;
        Y = y;
        this.on = onOrOff;
    }

    public LightSource(int x, int y) {
        X = x;
        Y = y;
        on = false;
    }

    public void toggleLight() {
        on = !on;
    }

    public void turnLightOn(MapGenerator map) {
        for (int i = -3; i < 4; i++) {
            for (int j = -3; j < 4; j++) {
                // Get tile i, j away from light source's x, y coordinates
                //unless this point goes off the map
                int xToChange = Math.min(Math.max(X + i, 0), map.WIDTH - 1);
                int yToChange = Math.min(Math.max(Y + j, 0), map.HEIGHT - 1);
                TETile toChange = map.getTileAT(xToChange, yToChange);
                if (toChange.description().equals("floor")) {
                    int distFromLight = Math.max(Math.abs(i), Math.abs(j));
                    map.getWorldGrid()[xToChange][yToChange]
                            = map.floorTiles[distFromLight - 1];
                }
            }
        }
    }

    public void turnLightOff(MapGenerator map) {
        for (int i = -3; i < 4; i++) {
            for (int j = -3; j < 4; j++) {
                // Get tile i, j away from light source's x, y coordinates
                //unless this point goes off the map
                int xToChange = Math.min(Math.max(X + i, 0), map.WIDTH - 1);
                int yToChange = Math.min(Math.max(Y + j, 0), map.HEIGHT - 1);
                TETile toChange = map.getTileAT(xToChange, yToChange);
                if (toChange.description().equals("floor")) {
                    int distFromLight = Math.max(Math.abs(i), Math.abs(j));
                    map.getWorldGrid()[xToChange][yToChange]
                            = map.floorTiles[5];
                }
            }
        }
    }

    public void renderLight(MapGenerator map) {
        if (on) {
            turnLightOn(map);
        } else {
            turnLightOff(map);
        }
    }
}
