package fifteenpuzzle.additional;

import java.util.*;

public class Node {

    public Puzzle state;
    public Node parent;
    public ArrayList<Node> child;
    public int g, f, h;

    public Node(Puzzle puzzle, Node parent) {
        this.state = puzzle;
        for (int i = 0; i < puzzle.getSize(); i++)
            for (int j = 0; j < puzzle.getSize(); j++)
                this.state.board[i][j] = puzzle.board[i][j];
        this.parent = parent;
        if (parent != null)
            g = parent.g + 1;
        else
            g = 0;
        h = state.heuretics();
        f = g + h;
    }

    public ArrayList<Node> childNodes() {
        child = new ArrayList<>();
        int count = 0;
        int a = -1;
        int b = -1;
        Puzzle temp = new Puzzle(this.state);
        int size = state.getSize();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (state.board[i][j] == 0) {
                    a = i;
                    b = j;
                }
        int tile;
        if (this.state.canMove(0)) {
            child.add(new Node(temp, this));
            tile = child.get(count).state.board[a + 1][b];
            try {
                child.get(count).state.makeMove(tile, 0);
            } catch (Puzzle.IllegalMoveException e) {
                throw new RuntimeException(e);
            }
            count++;
        }
        temp = new Puzzle(this.state);

        if (this.state.canMove(1)) {
            child.add(new Node(temp, this));
            tile = child.get(count).state.board[a - 1][b];
            try {
                child.get(count).state.makeMove(tile, 1);
            } catch (Puzzle.IllegalMoveException e) {
                throw new RuntimeException(e);
            }
            count++;
        }
        temp = new Puzzle(this.state);
        if (this.state.canMove(2)) {
            child.add(new Node(temp, this));
            tile = child.get(count).state.board[a][b + 1];
            try {
                child.get(count).state.makeMove(tile, 2);
            } catch (Puzzle.IllegalMoveException e) {
                throw new RuntimeException(e);
            }
            count++;
        }
        temp = new Puzzle(this.state);
        if (this.state.canMove(3)) {
            child.add(new Node(temp, this));
            tile = child.get(count).state.board[a][b - 1];
            try {
                child.get(count).state.makeMove(tile, 3);
            } catch (Puzzle.IllegalMoveException e) {
                throw new RuntimeException(e);
            }
        }
        return child;
    }

    public void equal(Node from) {
        this.state = from.state;
        this.parent = from.parent;
        this.g = from.g;
        this.f = from.f;
    }

    public boolean isGoal() {
        int size = state.getSize();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (state.board[i][j] != state.goal[i][j])
                    return false;
        return true;
    }

}
