public class Dijkstra {
  private static int V; //NUMBER OF VERTICES
  public static void main (String[] args) {
/*    int graph[][] = new int[][]{{0, 4, 27, 0, 26, 0, 8, 8, 0},
                               {4, 0, 1, 0, 17, 0, 0, 11, 9},
                               {0, 18, 0, 7, 0, 4, 0, 2, 22},
                               {0, 20, 7, 0, 9, 16, 0, 13, 0},
                               {0, 10, 0, 9, 0, 12, 0, 6, 7},
                               {0, 9, 4, 0, 10, 0, 2, 0, 0},
                               {0, 8, 0, 14, 0, 2, 0, 1, 6},
                               {8, 11, 0, 0, 0, 10, 1, 0, 7},
                               {0, 0, 12, 0, 0, 0, 5, 6, 0}
                              };
                              */
    int graph[][] =       {{0, 4, 0, 0, 0, 0, 0, 8, 0},
                           {4, 0, 8, 0, 0, 0, 0, 11, 0},
                           {0, 8, 0, 7, 0, 4, 0, 0, 2},
                           {0, 0, 7, 0, 9, 14, 0, 0, 0},
                           {0, 0, 0, 9, 0, 10, 0, 0, 0},
                           {0, 0, 4, 0, 10, 0, 2, 0, 0},
                           {0, 0, 0, 14, 0, 2, 0, 1, 6},
                           {8, 11, 0, 0, 0, 0, 1, 0, 7},
                           {0, 0, 2, 0, 0, 0, 6, 7, 0}};
    V = graph.length;
    dijkstra (graph, 0, 4);
  }

  public static void dijkstra (int[][] graph, int start, int dest) {
    boolean[] visited = new boolean[V];
    int[] dist = new int[V]; //stores best distance from start to all other nodes.
    int[] prev = new int[V]; //For figuring out the path from start to dest
    for (int i = 0; i < dist.length; i++) {
      visited[i] = false;
      dist[i] = Integer.MAX_VALUE; //"infinity" value because they are unknown
    }
    dist[start] = 0; //distance from start to itself is zero

    for (int fromV = 0; fromV < V - 1; fromV++) {
      int current = minimumDistance(visited, dist); //find the next vertex to work on. starts with start vertex because we set it to zero.
      visited[current] = true; //mark the current node as visited so we don't keep doing it

      for (int toV = 0; toV < V; toV++) {
        /* 1. check if the toV (vertex) is not visited
           2. if there is an edge from current to toV (0 in the graph indicates no edge)
           3. dist[current] is obviously not MAX_VALUE for the one to do, but we want to make this check so that 4) does not overflow
           4. if the distance to the current vertex plus the weight to the next vertex is better than the existing value to the next vertex
                then update that distance.
        */
        if (visited[toV] == false && graph[current][toV] != 0 && dist[current] != Integer.MAX_VALUE
                                  && dist[current] + graph[current][toV] < dist[toV]) {
            dist[toV] = dist[current] + graph[current][toV];
            prev[toV] = current; //this helps us "remember" the pathway to that vertex
          }
      }
    }
    printSolution (dist, dest);
    printAllSolutions (dist, V);
    printPath (prev, 4);
  }

  private static int minimumDistance (boolean[] visited, int[] dist) {
    int minimum = Integer.MAX_VALUE;
    int u = 0;
    for(int i = 0; i < V; i++) {
      if (visited[i] == false && dist[i] < minimum) {
        minimum = dist[i];
        u = i;
      }
    }
    return u;
  }

  private static void printAllSolutions (int dist[], int n) {
   System.out.println ("Vertex   Distance from Source");
   for (int i = 0; i < V; i++)
      System.out.println (i + "    " + dist[i]);
  }

  private static void printSolution (int dist[], int n) {
    System.out.println ("Vertex   Distance from Source");
    System.out.println (n +  "    " + dist[n]);
  }

  private static void printPath (int prev[], int n) {
    System.out.println("Path from source vertex to " + n);
    int src = 0; //stupid compiler
    StringBuilder path = new StringBuilder();
    for( int i : prev)
      if (prev[i] == 0){
        src = i;
        break;
      }
    int i = n;
    while (i != src) { //when i == src, then we're done finding our way back
      path.append(i + "-");
      i = prev[i];
    }
    path.append(src);
    System.out.println (path.reverse().toString());

  }
}
