package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 * <p>
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 * <p>
 * Ex:
 * world[x][y] = Tileset.FLOOR;
 * <p>
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile AVATAR = new TETile('@', Color.white, Color.black, "you");
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall");
    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");

    //Added
    //AVATAR
    public static final TETile AVATAR2 = new TETile('@', Color.white, Color.black, "you",
            "/byow/TileEngine/AVATAR.png");

    //defaults
    public static final TETile GRASS2 = new TETile('"', new Color(113, 138, 75),
            new Color(189, 236, 112), "grass",
            "/byow/TileEngine/GRASS.png");
    public static final TETile SAND2 = new TETile('▒', Color.yellow, Color.black,
            "sand", "/byow/TileEngine/SAND.png");
    public static final TETile TREE2 = new TETile('♠', Color.green, Color.black,
            "trees", "/byow/TileEngine/TREES.png");
    public static final TETile TUNDRA = new TETile('▒', Color.lightGray, Color.white,
            "snow", "/byow/TileEngine/TUNDRA.png");

    //walls
    public static final TETile WALL2 = new TETile('^', new Color(65, 58, 45),
            new Color(159, 150, 142), "stone wall",
            "/byow/TileEngine/WALL.png");
    public static final TETile BRICK = new TETile('^', new Color(65, 58, 45),
            new Color(99, 40, 42), "brick wall",
            "/byow/TileEngine/BRICK.png");
    public static final TETile DARKWALL = new TETile('^', new Color(65, 58, 45),
            new Color(20, 20, 22), "dark wall",
            "/byow/TileEngine/DARKWALL.png");

    //Floor tiles of varying light level
    public static final TETile FLOOR1 = new TETile('=', new Color(118, 54, 47),
            new Color(252, 219, 121), "floor",
            "/byow/TileEngine/FLOOR1.png");
    public static final TETile FLOOR2 = new TETile('=', new Color(118, 54, 47),
            new Color(232, 199, 101), "floor",
            "/byow/TileEngine/FLOOR2.png");
    public static final TETile FLOOR3 = new TETile('=', new Color(118, 54, 47),
            new Color(192, 159, 61), "floor",
            "/byow/TileEngine/FLOOR3.png");
    public static final TETile FLOOR4 = new TETile('=', new Color(118, 54, 47),
            new Color(172, 139, 41), "floor",
            "/byow/TileEngine/FLOOR4.png");
    public static final TETile FLOOR5 = new TETile('=', new Color(118, 54, 47),
            new Color(152, 119, 21), "floor",
            "/byow/TileEngine/FLOOR5.png");
    public static final TETile FLOOR6 = new TETile('=', new Color(118, 54, 47),
            new Color(132, 99, 1), "floor",
            "/byow/TileEngine/FLOOR6.png");


    // Torch tile for LightSource
    public static final TETile LIGHT = new TETile('O', new Color(0, 0, 0),
            new Color(255, 255, 200), "torch",
            "byow/TileEngine/torch.png");
}

