package pkg;

class Graph {
  
  public String[] matrixVertex;
  public int[][] adjacentMatrix;
  
  /**
   * get the index of a node.
   * @param graph the object of Graph, node 
   * @return index
   */
  public static int getPosition(final Graph graph, final String node) {
	    for (int i = 0; i < graph.matrixVertex.length; i++) {
	      if (graph.matrixVertex[i].equals(node)) {
	        return i;
	      }
	    } 
	    return -1;
	  }
  /*
  public String[] getMatrixVertex() {
		return matrixVertex;
	}

	public void setMatrixVertex(String[] matrixVertex) {
		this.matrixVertex = matrixVertex;
	}
	
	public int[][] getAdjacentMatrix() {
		return adjacentMatrix;
	}

	public  void setAdjacentMatrix(int[][] adjacentMatrix) {
		this.adjacentMatrix = adjacentMatrix;
	}
*/
}

