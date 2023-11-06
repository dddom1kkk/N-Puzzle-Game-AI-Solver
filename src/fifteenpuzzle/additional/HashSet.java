package fifteenpuzzle.additional;

import java.io.*;
import java.util.*;

public class HashSet{

    public Map<Long, LinkedList<Node>> set;

    public HashSet() {
        set = new HashMap<>();
    }

    public int checkClosedS(Node node) {
        int size = node.state.getSize();
        LinkedList<Node> state = set.get(hashCode(node));
        if (state == null)
            return 0;
        Iterator<Node> iter = state.iterator();
        Node temp;
        boolean check;
        while (iter.hasNext()) {
            temp = iter.next();
            check = false;
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    if (temp.state.board[i][j] != node.state.board[i][j])
                        check = true;
            if (!check) {
                temp.f = node.f;
                return 1;
            }
        }
        return 0;
    }

    public void add(Node node) {
        if (checkClosedS(node) == 0) {
            long code = hashCode(node);
            LinkedList<Node> state = set.get(code);
            if (state == null) {
                state = new LinkedList<>();
                set.put(code, state);
            }
            state.add(node);
        }
    }

    public long hashCode(Node node) {
        int a = 0, b = 0;
        int size = node.state.getSize();
        String key = "";
        for(int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (node.state.board[i][j] == 0) {
                    a = i;
                    b = j;
                    if (j > 0 && j < size - 1) {
                        key += node.state.board[i][j + 1] + node.state.board[i][j - 1];
                        if (i < size - 1)
                            key += node.state.board[i + 1][j] + node.state.board[i + 1][j - 1] + node.state.board[i + 1][j + 1];
                        if (i > 0)
                            key += node.state.board[i - 1][j] + node.state.board[i - 1][j - 1] + node.state.board[i - 1][j + 1];
                    } else if (j > 0 && j == size - 1) {
                        key += node.state.board[i][j - 1];
                        if (i < size - 1)
                            key += node.state.board[i + 1][j] + node.state.board[i + 1][j - 1];
                        if (i > 0)
                            key += node.state.board[i - 1][j] + node.state.board[i - 1][j - 1];
                    } else if (j == 0 && j < size - 1) {
                        key += node.state.board[i][j + 1];
                        if (i < size - 1)
                            key += node.state.board[i + 1][j] + node.state.board[i + 1][j + 1];
                        if (i > 0)
                            key += node.state.board[i - 1][j] + node.state.board[i - 1][j + 1];
                    }
                    break;
                }
        key += node.state.board[0][0] + node.state.board[size - 1][0] + node.state.board[size - 1][size - 1] + node.state.board[0][size - 1] + a + b + node.f;
        return Long.parseLong(key);
    }

}
