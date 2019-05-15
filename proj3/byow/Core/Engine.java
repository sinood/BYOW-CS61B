package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    MapGenerator map;
    boolean stillPlaying = true;
    boolean menuOpen = true;
    HUD hud;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        TETile[][] grid = new TETile[1][1];
        grid[0][0] = Tileset.NOTHING;
        while (stillPlaying) {
            String s = "";
            if (StdDraw.hasNextKeyTyped()) {
                s += StdDraw.nextKeyTyped();
                if (s.toLowerCase().equals("n")) {
                    ter.displaySeedPrompt();
                    StdDraw.show();
                    while (!s.substring(s.length() - 1).toLowerCase().equals("s")) {
                        if (StdDraw.hasNextKeyTyped()) {
                            s += StdDraw.nextKeyTyped();
                        }
                    }
                }
                System.out.println(s);
                grid = interactWithInputString(s);

                if (grid.length > 1) {
                    menuOpen = false;
                    display(grid);
                } else {
                    // World map is NOT open, display menu
                    menuOpen = true;
                    ter.displayMenuText();
                    StdDraw.show();
                }
            } else {
                // World map is open, but no key presses (still want to update
                // HUD)
                if (grid.length > 1) {
                    ter.displayHUDText(hud);
                    StdDraw.show();
                }
            }
        }

    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        TETile[][] result = new TETile[1][1];
        while (input.length() > 0) {
            if (input.substring(0, 1).toLowerCase().equals("n")) {
                input = input.substring(1);
                int seedLength = 0;
                while (input.charAt(seedLength) != 's') {
                    seedLength += 1;
                }
                result = processSeed(input.substring(0, seedLength));
                input = input.substring(seedLength + 1);
            } else if (input.length() >= 2
                    && input.substring(0, 2).toLowerCase().equals(":q")) {
                GameSave.save(map);
                System.out.println("Saving and Quitting..."); //delete later
                startGame();
                input = input.substring(2);
            } else {
                result = processByChar(input);
                input = input.substring(1);
            }
        }
        return result;
    }

    private TETile[][] processSeed(String input) {
        long l = 0;
        try {
            l = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println(e);
            return new TETile[1][1];
        }
        Random r = new Random(l);
        map = new MapGenerator(WIDTH, HEIGHT, r);
        map.addPlayer();
        return map.getWorldGrid();
    }

    private TETile[][] processByChar(String input) {
        TETile[][] result = new TETile[1][1];
        char c = input.toLowerCase().charAt(0);
        if (c == ':' && !menuOpen) {
            while (input.length() < 2) {
                if (StdDraw.hasNextKeyTyped()) {
                    input += StdDraw.nextKeyTyped();
                }
            }
            return interactWithInputString(input);
        } else if (c == 'l' && menuOpen) {
            map = GameSave.load();
            return map.getWorldGrid();
        } else if (c == 'q' && menuOpen) {
            System.out.println("exiting...");
            System.exit(0);
        } else if (map != null && !menuOpen) {
            result = processMove(c);
        }
        return result;
    }

    private TETile[][] processMove(char c) {
        Player p = map.player;
        if (c == 'w') {
            p.moveTo(map, p.X, p.Y + 1);
        } else if (c == 'a') {
            p.moveTo(map, p.X - 1, p.Y);
        } else if (c == 's') {
            p.moveTo(map, p.X, p.Y - 1);
        } else if (c == 'd') {
            p.moveTo(map, p.X + 1, p.Y);
        } else if (c == 'u') {
            p.toggleLightSource(map);
        }
        return map.getWorldGrid();
    }


    public void startGame() {
        ter.initialize(WIDTH + 2, HEIGHT + 4, 1, 3);
    }

    public void display(TETile[][] grid) {
        map.renderLights();
        if (hud == null) {
            hud = new HUD(map);
        }
        ter.renderFrame(grid, hud);
    }

    public static void main(String[] args) {
        //Engine e = new Engine();
        //TERenderer t = new TERenderer();
        //t.initialize(WIDTH, HEIGHT);
        //t.renderFrame(e.interactWithInputString("n5198675084700690313s"));
        String s = "n398462398462398sWWDSBJHB";
        s = s.substring(1);
        String rawSeed = "";
        int seedLength = 0;
        while (s.charAt(seedLength) != 's') {
            seedLength += 1;
        }
        rawSeed = s.substring(0, seedLength);
        s = s.substring(seedLength + 1);
        long l = Long.parseLong(rawSeed);
        System.out.println(l);
        String t = "e";
        System.out.println(t.substring(1) + "" + t.substring(1).equals(""));
    }
}
