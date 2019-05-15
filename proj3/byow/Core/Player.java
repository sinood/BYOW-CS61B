package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Player extends Character {
    boolean hasTorch;

    public Player(TETile tile, int x, int y) {
        super(tile, x, y);
        hasTorch = false;
    }

    public void toggleLightSource(MapGenerator map) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (map.lightsMap.containsKey((i + X) * 100 + (j + Y))) {
                    map.lightsMap.get((i + X) * 100 + (j + Y)).toggleLight();
                }
            }
        }
    }
}
