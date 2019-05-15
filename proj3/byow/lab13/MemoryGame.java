package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
            "You got this!", "You're a star!", "Go Bears!",
            "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        String result = "";
        for (int i = 0; i < n; i++) {
            result += CHARACTERS[rand.nextInt(n)];
        }
        return result;
    }

    public void drawFrame(String s, String typeOrWatch) {
        StdDraw.clear();
        StdDraw.setFont(new Font("Helvetica", Font.BOLD, 30));
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.show();
        if (!gameOver) {
            StdDraw.line(0,height - 3.0, width, height - 3.0);
            //left text
            StdDraw.setFont(new Font("Helvetica", Font.PLAIN, 18));
            StdDraw.textLeft(1.0, height - 2, "Round: " + round);
            //middle text
            StdDraw.text(width / 2, height - 2, typeOrWatch);
            //right text
            StdDraw.textRight(width - 1.0, height - 2,
                    ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            StdDraw.show();
        }
    }

    public void flashSequence(String letters) {
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i + 1), "Watch!");
            StdDraw.pause(1000);
            StdDraw.clear();
            drawFrame("", "Watch!");
            StdDraw.show();
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        String inputString = "";
        drawFrame(inputString, "Type!");
        while (n > 0) {
            if (StdDraw.hasNextKeyTyped()) {
                inputString += StdDraw.nextKeyTyped();
                drawFrame(inputString, "Type!");
                n -= 1;
            }
        }
        StdDraw.pause(1000);
        return inputString;
    }

    public void startGame() {
        gameOver = false;
        round = 1;
        String randString = generateRandomString(round);
        flashSequence(randString);
        String inputString = solicitNCharsInput(round);
        while (!gameOver) {
            if (inputString.equals(randString)) {
                round += 1;
                drawFrame("Correct!", "");
                StdDraw.pause(1000);
                randString = generateRandomString(round);
                flashSequence(randString);
                inputString = solicitNCharsInput(round);
            } else {
                gameOver = true;
            }
        }
        drawFrame("Game Over! You made it to round: " + round, "");
    }

}
