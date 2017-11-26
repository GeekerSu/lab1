package pkg;

public class queryBridgeWords {
	
	  public static String queryBridgeWords(final Graph graph, final String word1, final String word2) {
		    boolean bridgeWord = false;
		    int[] word3 = new int[graph.matrixVertex.length];
		    final int word1pos = Graph.getPosition(graph, word1);
		    final int word2pos = Graph.getPosition(graph, word2);
		    if (word1pos == -1) {
		      if (word2pos == -1) {
		        //System.out.println("No \"" + word1 + " \"and \"" + word2 + " \" in graph!");
		        return "No \"" + word1 + "\" and \"" + word2 + "\" in graph!";
		      } else {
		        //System.out.println("No \"" + word1 + " \" in graph!");
		        return "No \"" + word1 + "\" in graph!";
		      }
		    } else if (word2pos == -1) {
		      //System.out.println("No \"" + word2 + " \" in graph!");
		      return "No \"" + word2 + "\" in graph!";
		    } else {
		      for (int i = 0; i < graph.matrixVertex.length; i++) {
		        if ((graph.adjacentMatrix[word1pos][i]) * (graph.adjacentMatrix[i][word2pos]) != 0) {
		          word3[i] = 1;
		          bridgeWord = true;
		        }
		      }
		    }
		    StringBuilder wordsReturnStr = new StringBuilder();
		    if (bridgeWord) {
		      //System.out.print("The bridge word from \"" + word1 + " \"to \"" + word2 + " \" :");
		      for (int i = 0; i < graph.matrixVertex.length; i++) {
		        if (word3[i] == 1) {
		          wordsReturnStr.append(graph.matrixVertex[i]);
		          wordsReturnStr.append(' ');
		        }
		      }
		    } else {
		      //System.out.println("No bridge word from \"" + word1 + " \"to \"" + word2 + " \" !");
		    	return	"No bridge word from \"" + word1 + "\" to \"" + word2 + "\" !";
		    }
		    return "The bridge word from \"" + word1 + "\" to \"" + word2 + "\" :" + wordsReturnStr.toString();
		  }
}
