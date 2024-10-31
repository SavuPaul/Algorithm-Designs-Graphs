import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Trenuri {
	static class Task {
		public static final String INPUT_FILE = "trenuri.in";
		public static final String OUTPUT_FILE = "trenuri.out";

		int M;
		Map<String, Integer> cities = new HashMap<>();
		ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
		ArrayList<ArrayList<Integer>> adj2 = new ArrayList<>();
		int[] gIn;
		int[] path;
		String firstCity, secondCity;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));

				// these are added so that indexing starts from 1
				adj.add(new ArrayList<>());

				// first city
				String city1 = sc.next();
				firstCity = city1;
				// add it to the map and assign it a code (1)
				cities.put(city1, 1);
				adj.add(new ArrayList<>());

				// second city
				String city2 = sc.next();
				secondCity = city2;
				// add it to the map and assign it a code (2)
				cities.put(city2, 2);
				adj.add(new ArrayList<>());

				// read number of edges
				M = sc.nextInt();

				// this will assign a value to each city when a new
				// one is found
				int idxForNodes = 3;

				for (int i = 0; i < M; i++) {
					city1 = sc.next();
					city2 = sc.next();

					// add the new cities to the map and assign them a value
					if (!cities.containsKey(city1)) {
						cities.put(city1, idxForNodes);
						adj.add(new ArrayList<>());
						idxForNodes++;
					}
					if (!cities.containsKey(city2)) {
						cities.put(city2, idxForNodes);
						adj.add(new ArrayList<>());
						idxForNodes++;
					}

					// establish the connections in the adj lists
					adj.get(cities.get(city1)).add(cities.get(city2));
				}

				gIn = new int[adj.size() + 1];
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
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

		private void getSubgraph() {
			ArrayList<Integer> queue = new ArrayList<>();

			// initialize array lists in adj2
			for (int i = 0; i <= adj.size(); i++) {
				adj2.add(new ArrayList<>());
			}

			boolean[] visited = new boolean[adj.size() + 1];
			Arrays.fill(visited, false);

			queue.add(cities.get(firstCity));

			while (!queue.isEmpty()) {
				int top = queue.get(0);
				visited[top] = true;

				if (top != cities.get(secondCity)) {
					for (Integer elem : adj.get(top)) {
						gIn[elem]++;
						if (!visited[elem] && !queue.contains(elem)) {
							queue.add(elem);
						}
						if (!adj2.get(top).contains(elem)) {
							adj2.get(top).add(elem);
						}
					}
				}

				queue.remove(0);

			}
		}

		private ArrayList<Integer> topSort() {
			ArrayList<Integer> topSort = new ArrayList<>();
			ArrayList<Integer> queue = new ArrayList<>();

			queue.add(cities.get(firstCity));

			boolean[] visited = new boolean[adj2.size() + 1];
			Arrays.fill(visited, false);

			while (!queue.isEmpty()) {
				int top = queue.get(0);

				if (gIn[top] == 0) {
					visited[top] = true;
					topSort.add(top);
				}

				for (Integer elem : adj2.get(top)) {
					gIn[elem]--;
					if (visited[elem] || gIn[elem] == 0) {
						queue.add(elem);
					}
				}

				queue.remove(0);
			}

			return topSort;
		}

		private long getResult() {
			// gets the subgraph of all nodes and also computes the internal grades
			// for each of the nodes in the subgraph
			getSubgraph();

			// perform bfs topological sort on the graph
			ArrayList<Integer> topSort = topSort();

			path = new int[adj2.size() + 1];
			Arrays.fill(path, 0);

			for (Integer elem : topSort) {
				for (Integer crt : adj2.get(elem)) {
					path[crt] = path[elem] + 1;
				}
			}

			return path[cities.get(secondCity)] + 1;
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
