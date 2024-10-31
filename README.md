# README - Project 2 - Algorithm Design

The code structure for each problem is based on the laboratory framework for reading data and outputting results.

---

## Problem 1 - Counting

To optimize time, the problem is solved during data reading within the same function. We start by reading the edges for the first graph, and then, knowing that we are only interested in common edges, we process only those edges in the second graph that already exist in the first graph.

The solution uses a vector, `paths`, where each element `paths[i]` represents the number of paths from node 1 to node `i`. These values are updated as each edge is read if it already exists in the first graph. For example, in the list of edges:

- (1 -> 2) sets `paths[2] = 1`
- (1 -> 4) sets `paths[4] = 1`
- (4 -> 2) updates `paths[2] = 2`
- (2 -> 7) updates `paths[7] = 2`, which is the value of the node from which the edge originates.

For edges directed towards node N, the vertex from which the edge originates is added to a stack. This approach ensures that all nodes in the graph are processed (i.e., the `paths` vector is fully populated). The final result is obtained by summing the values of `paths[j]` for each `j` that is in the stack.

**Complexity**: O(n²) because in addition to the O(n) for reading edges in the second graph, we also have the `contains` call on the array list, which has a complexity of O(n).

---

## Problem 2 - Trains

To solve this problem, I used a `HashMap` to assign each city a number, simplifying work with adjacency lists. The next step is to extract the subgraph between the starting and destination nodes because we are only interested in nodes reachable from the start node. Similarly, nodes farther than the destination node are ignored. As we extract the subgraph, we also calculate the in-degree for each node, which aids in its topological sorting. 

Once we have the topological sort, the goal is to increment the distance of each node's neighbors within the adjacency list of the subgraph. For example, consider the adjacency list:

