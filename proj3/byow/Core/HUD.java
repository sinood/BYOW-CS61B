package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

public class HUD {
    MapGenerator map;
    String belowMouse;

    public HUD(MapGenerator m) {
        map = m;
        belowMouse = "Nothing";
    }

    public void updateBelowMouseText(int xOffSet, int yOffSet) {
        int x = (int) StdDraw.mouseX() - xOffSet;
        int y = (int) StdDraw.mouseY() - yOffSet;
        if (x < map.WIDTH && x >= 0
                && y < map.HEIGHT && y >= 0) {
            belowMouse = map.getTileAT(x, y).description();
        }
    }

    public String getBelowMouseText() {
        return belowMouse;
    }
}
