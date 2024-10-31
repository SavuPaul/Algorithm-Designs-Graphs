import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Numarare {
	static class Task {
		public static final String INPUT_FILE = "numarare.in";
		public static final String OUTPUT_FILE = "numarare.out";

		int N, M;
		ArrayList<ArrayList<Integer>> adj1 = new ArrayList<>();
		public static final long LIM = 1000000007;

		public void solve() {
			writeOutput(readAndCompute());
		}

		private long readAndCompute() {
			// this will contain the final result
			long maxPaths = 0;

			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));

				// read number of nodes
				N = sc.nextInt();

				// read number of edges
				M = sc.nextInt();

				// initialize each element in the array lists with a new array list
				for (int i = 1; i <= N + 1; i++) {
					adj1.add(new ArrayList<>());
				}

				int u, v;
				// read edges for the first graph
				for (int i = 1; i <= M; i++) {
					u = sc.nextInt();
					v = sc.nextInt();
					adj1.get(u).add(v);
				}

				// this array will count how many routes there are
				// through each node (path[i] = paths for node i)
				long[] paths = new long[N + 1];

				// initialize starting values
				for (int i = 2; i <= N; i++) {
					paths[i] = 0;
				}
				paths[1] = 1;

				// stack for the nodes where v is N
				Stack<Integer> stack = new Stack<>();

				// read edges for the second graph, but exclude the
				// ones which do not exist in the first graph
				for (int i = 1; i <= M; i++) {
					u = sc.nextInt();
					v = sc.nextInt();
					if (v == N && adj1.get(u).contains(v)) {
						stack.push(u);
					} else if (adj1.get(u).contains(v)) {
						// edge starts from u, towards v
						if (paths[v] == 0) {
							paths[v] = paths[u];
						} else {
							paths[v] += paths[u];
							paths[v] %= LIM;
						}
					}
				}

				while (!stack.isEmpty()) {
					maxPaths += paths[stack.pop()];
					maxPaths %= LIM;
				}

				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			return maxPaths;
		}

		private void writeOutput(long count) {
			try {
				PrintWriter pw = new PrintWriter(OUTPUT_FILE);
				pw.printf("%d\n", count);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
