package fifteenpuzzle.additional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Puzzle {

    public final static int UP = 0;
    public final static int DOWN = 1;
    public final static int LEFT = 2;
    public final static int RIGHT = 3;

    public static int SIZE;

    public int[][] board, goal, temp;

    public class IllegalMoveException extends Exception {

        private static final long serialVersionUID = 1L;

        public IllegalMoveException(String message) {
            super(message);
        }
    }

    public Puzzle(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/Users/dom1k/IdeaProjects/finalproject/src/" + fileName));
        String boardSize = br.readLine();
        SIZE = Integer.parseInt(boardSize);
        board = new int[SIZE][SIZE];
        int c1, c2, s;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                c1 = br.read();
                c2 = br.read();
                s = br.read(); // skip the space
                if (c1 == ' ')
                    c1 = '0';
                if (c2 == ' ')
                    c2 = '0';
                board[i][j] = 10 * (c1 - '0') + (c2 - '0');
            }
        }
        temp = board.clone();
        br.close();
        goal = new int[SIZE][SIZE];
        int count = 1;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                goal[i][j] = count;
                count++;
            }
        goal[SIZE - 1][SIZE - 1] = 0;
    }

    public class Pair {
        int i, j;

        Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    private Puzzle.Pair findCoord(int tile) {
        int i = 0, j = 0;
        for (i = 0; i < SIZE; i++)
            for (j = 0; j < SIZE; j++)
                if (board[i][j] == tile)
                    return new Puzzle.Pair(i, j);
        return null;
    }
    public void makeMove(int tile, int direction) throws IllegalMoveException {
        Puzzle.Pair p = findCoord(tile);
        if (p == null)
            throw new IllegalMoveException("tile " + tile + " not found");
        int i = p.i;
        int j = p.j;

        // the tile is in position [i][j]
        switch (direction) {
            case UP: {
                if (i > 0 && board[i - 1][j] == 0) {
                    board[i - 1][j] = tile;
                    board[i][j] = 0;
                    break;
                } else
                    throw new IllegalMoveException("" + tile + "cannot move UP");
            }
            case DOWN: {
                if (i < SIZE - 1 && board[i + 1][j] == 0) {
                    board[i + 1][j] = tile;
                    board[i][j] = 0;
                    break;
                } else
                    throw new IllegalMoveException("" + tile + "cannot move DOWN");
            }
            case RIGHT: {
                if (j < SIZE - 1 && board[i][j + 1] == 0) {
                    board[i][j + 1] = tile;
                    board[i][j] = 0;
                    break;
                } else
                    throw new IllegalMoveException("" + tile + "cannot move LEFT");
            }
            case LEFT: {
                if (j > 0 && board[i][j - 1] == 0) {
                    board[i][j - 1] = tile;
                    board[i][j] = 0;
                    break;
                } else
                    throw new IllegalMoveException("" + tile + "cannot move LEFT");
            }
            default:
                throw new IllegalMoveException("Unexpected direction: " + direction);
        }

    }

    private String num2str(int i) {
        if (i == 0)
            return "  ";
        else if (i < 10)
            return " " + Integer.toString(i);
        else
            return Integer.toString(i);
    }

    public String toString() {
        String ans = "";
        for (int i = 0; i < SIZE; i++) {
            ans += num2str(board[i][0]);
            for (int j = 1; j < SIZE; j++)
                ans += " " + num2str(board[i][j]);
            ans += "\n";
        }
        return ans;
    }

    public int notPlaced() {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != goal[i][j])
                    count++;
            }
        }
        return count;
    }

    public int heuretics() {
        int cost = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int tile = board[i][j];
                if (tile != 0) {
                    int goalRow = (tile - 1) / SIZE;
                    int goalCol = (tile - 1) % SIZE;
                    cost += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }
        return cost;
    }

    public int getSize() {
        return SIZE;
    }

    public void setSize(int size) {
        SIZE = size;
    }

    public boolean canMove(int direction) {
        Puzzle.Pair zero = findCoord(0);
        int a = zero.i;
        int b = zero.j;

        if (direction == UP)
            if (a + 1 <= SIZE - 1)
                return true;
        if (direction == DOWN)
            if (a - 1 >= 0)
                return true;
        if (direction == LEFT)
            if (b + 1 <= SIZE - 1)
                return true;
        if (direction == RIGHT)
            if (b - 1 >= 0)
                return true;
        return false;
    }

    public Puzzle(Puzzle copy) {
        this.setSize(copy.getSize());
        this.board = new int[SIZE][SIZE];
        this.goal = copy.goal;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                this.board[i][j] = copy.board[i][j];
    }
}
