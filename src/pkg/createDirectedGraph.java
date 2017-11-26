package pkg;

public class createDirectedGraph {
	  
		 public static void createDirectedGraph(Graph graph, final String[] vex, final String[] edges) {
			    final int vexLen = vex.length;
			    final int edgeLen = edges.length - 1;
			    final String[] tmp = vex;
			    graph.matrixVertex = tmp;
			    graph.adjacentMatrix = new int[vexLen][vexLen];
			    for (int i = 0; i < edgeLen; i++) {
			      final int position1 = Graph.getPosition(graph, edges[i]);
			      final int position2 = Graph.getPosition(graph, edges[i + 1]);
			      graph.adjacentMatrix[position1][position2]++;
			    }
			  }

	}
