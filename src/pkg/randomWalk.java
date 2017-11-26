package pkg;

import java.util.ArrayList;
import java.util.Random;

public class randomWalk {
	public static String randomWalk(final Graph graph) {
	    Random random = new Random();

	    int[][] matrix = new int[graph.matrixVertex.length][graph.matrixVertex.length];
	    for (int i = 0; i < graph.matrixVertex.length; i++) {
	      for (int j = 0; j < graph.matrixVertex.length; j++) {
	        matrix[i][j] = graph.adjacentMatrix[i][j];
	      }
	    }
	    int numToGo = (random.nextInt(100)) % graph.matrixVertex.length;
	    System.out.print(graph.matrixVertex[numToGo]);
	    boolean rowMark = true;
	    StringBuilder wordsReturnStr = new StringBuilder();
	    ArrayList<Integer> canChoose = new ArrayList<Integer>();
	    while (rowMark) {
	      rowMark = false;
	      for (int i = 0; i < graph.matrixVertex.length; i++) {
	        if (matrix[numToGo][i] > 0) {
	          rowMark = true;
	          break;
	        }
	      }
	      canChoose.clear();
	      for (int i = 0; i < graph.matrixVertex.length; i++) {
	        if (matrix[numToGo][i] != 0) {
	          canChoose.add(i);
	        }
	      }
	      if (canChoose.isEmpty()) {
	        break;
	      }
	      numToGo = (int) canChoose.get((random.nextInt(100)) % (canChoose.size()));
	      final int mark = numToGo;
	      if (matrix[mark][numToGo] == -1) {
	        wordsReturnStr.append("->");
	        wordsReturnStr.append(graph.matrixVertex[numToGo]);
	        break;
	      }
	      matrix[mark][numToGo] = -1;
	      wordsReturnStr.append("->");
	      wordsReturnStr.append(graph.matrixVertex[numToGo]);
	    }
	    return wordsReturnStr.toString();
	  }

}
