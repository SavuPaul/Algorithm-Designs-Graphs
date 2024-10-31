import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Drumuri {
	static class Task {
		public static final String INPUT_FILE = "drumuri.in";
		public static final String OUTPUT_FILE = "drumuri.out";

		public static class Pair implements Comparable<Pair> {
			int dest;
			long cost;
			int used;

			public Pair(int dest, long cost) {
				this.dest = dest;
				this.cost = cost;
				this.used = 0;
			}

			public int compareTo(Pair rhs) {
				return Long.compare(cost, rhs.cost);
			}
		}

		int N, M;
		ArrayList<Pair>[] adj;
		int x, y, z;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));

				// read number of nodes
				N = sc.nextInt();

				adj = new ArrayList[N + 1];

				// read number of edges
				M = sc.nextInt();

				// initialize the array of array lists
				for (int i = 1; i <= N; i++) {
					adj[i] = new ArrayList<>();
				}

				int u, v, c;
				// read edges for graph
				for (int i = 1; i <= M; i++) {
					u = sc.nextInt();
					v = sc.nextInt();
					c = sc.nextInt();
					Pair pair = new Pair(v, c);
					adj[u].add(pair);
				}

				// read the 3 nodes
				x = sc.nextInt();
				y = sc.nextInt();
				z = sc.nextInt();

				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(BigInteger count) {
			try {
				PrintWriter pw = new PrintWriter(OUTPUT_FILE);
				pw.printf("%d\n", count);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private long dijkstra(int source) {
			final long inf = Long.MAX_VALUE;
			// distance array
			List<Long> d = new ArrayList<>();
			List<Integer> p = new ArrayList<>();

			// set distances to infinity
			for (int i = 1; i <= N + 1; i++) {
				d.add(inf);
				p.add(0);
			}

			// create priority queue
			PriorityQueue<Pair> pq = new PriorityQueue<>();

			d.set(source, (long)0);
			pq.add(new Pair(source, 0));

			while (!pq.isEmpty()) {
				// get the head of the queue
				long cost = pq.peek().cost;
				int node = pq.poll().dest;

				// in case a node is inserted in the priority queue with more distances (because
				// the distance up to it improves with time), we want only the version with the
				// minimum distance to be processed. Finally, we discard the entrances in the
				// queue which do not have the optimum distance
				if (cost > d.get(node)) {
					continue;
				}

				// iterate through all neighbors
				for (Pair e : adj[node]) {
					int neigh = e.dest;
					long w = e.cost;

					if (d.get(node) + w < d.get(neigh)) {

						// update distance
						d.set(neigh, (d.get(node) + w));
						p.set(neigh, node);
						pq.add(new Pair(neigh, d.get(neigh)));
					}
				}
			}

			// increase counter for used for each edge from z to source
			int i = z;
			while (!Objects.equals(p.get(i), p.get(source))) {
				for (Pair e : adj[p.get(i)]) {
					if (e.dest == i) {
						e.used++;

						// if counter reaches 2, reduce the distance since the
						// edge cost must be counted only once
						if (e.used == 2) {
							d.set(z, d.get(z) - e.cost);
						}
						break;
					}
				}
				i = p.get(i);
			}

			return d.get(z);
		}

		private BigInteger getResult() {
			long d_x_z = dijkstra(x);
			long d_y_z = dijkstra(y);

			// get the BigInteger values for the long variables
			BigInteger dist_x_z = BigInteger.valueOf(d_x_z);
			BigInteger dist_y_z = BigInteger.valueOf(d_y_z);

			// final result
			return dist_x_z.add(dist_y_z);
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
