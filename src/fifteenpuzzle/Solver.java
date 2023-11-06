package fifteenpuzzle;

import fifteenpuzzle.additional.Node;
import fifteenpuzzle.additional.Puzzle;
import fifteenpuzzle.additional.HashSet;

import java.util.*;
import java.io.*;
import java.util.Comparator;
import java.lang.invoke.MethodHandles;


public class Solver {

	Stack<Node> solution;
	Node goal = null;

	public Solver(Puzzle board) {
		Comparator<Node> specificCompare = Comparator.comparingInt(o -> o.f);
		PriorityQueue<Node> openQ = new PriorityQueue<>(specificCompare);
		HashSet closedS = new HashSet();
		Node start = new Node(board, null);
		openQ.add(start);
		Node current;
		if (start.isGoal())
			goal = openQ.poll();
		if (board.getSize() == 3) {
			while (!openQ.isEmpty()) {
				if (goal != null)
					break;
				current = openQ.poll();
				for (Node child : current.childNodes()) {
					child.parent = current;
					if (child.isGoal()) {
						System.out.println("solved");
						goal = child;
						break;
					}
					if (checkOpenQ(openQ, closedS, child)) {
						openQ.add(child);
					}
				}
				closedS.add(current);
			}
		}
		else {

		}
	}

	public boolean checkOpenQ (PriorityQueue<Node> openQ, HashSet closedS, Node node) {
		Iterator<Node> iter = openQ.iterator();
		boolean check;
		Node temp;
		int size = node.state.getSize();
		while (iter.hasNext()) {
			check = false;
			temp = iter.next();
			for (int i = 0; i < size; i++)
				for (int j = 0; j < size; j++)
					if (node.state.board[i][j] != temp.state.board[i][j])
						check = true;
			if (!check) {
				temp.f = node.f;
				return false;
			}
		}
		if (closedS.checkClosedS(node) == 1)
			return false;
		return true;
	}

	public String getSolution() {
		solution = new Stack<>();
		solution.push(goal);
		while (goal.parent != null) {
			goal = goal.parent;
			solution.push(goal);
		}
		String moves = "";
		Node temp1 = solution.pop();
		Node temp2 = solution.pop();
		int i1 = 0, j1 = 0, i2 = 0, j2 = 0;
		int size = temp1.state.getSize();
		while (!solution.isEmpty()) {
			for (int i = 0; i < size; i++)
				for (int j = 0; j < size; j++) {
					if (temp1.state.board[i][j] == 0) {
						i1 = i;
						j1 = j;
					}
					if (temp2.state.board[i][j] == 0) {
						i2 = i;
						j2 = j;
					}
				}
			moves += temp1.state.board[i2][j2];
			if (i1 > i2)
				moves += " D\n";
			if (i1 < i2)
				moves += " U\n";
			if (j1 < j2)
				moves += " L\n";
			if (j1 > j2)
				moves += " R\n";
			temp1 = temp2;
			temp2 = solution.pop();
		}
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				if (temp1.state.board[i][j] == 0) {
					i1 = i;
					j1 = j;
				}
				if (temp2.state.board[i][j] == 0) {
					i2 = i;
					j2 = j;
				}
			}
		moves += temp1.state.board[i2][j2];
		if (i1 > i2)
			moves += " D\n";
		if (i1 < i2)
			moves += " U\n";
		if (j1 < j2)
			moves += " L\n";
		if (j1 > j2)
			moves += " R\n";
		return moves;
	}

	public static void main(String[] args) throws IOException {
		System.out.println("number of arguments: " + args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}

		if (args.length < 2) {
			System.out.println("File names are not specified");
			System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
			return;
		}


		// TODO
		Puzzle board = new Puzzle(args[0]);
		File output = new File(args[1]);
		FileWriter f = new FileWriter(output);
		if (board.notPlaced() == 0) {
			f.write("\n");
			f.close();
		}
		else {
			Solver sol = new Solver(board);
			String solution = sol.getSolution();
			f.write(solution);
			f.close();
		}
	}
}
