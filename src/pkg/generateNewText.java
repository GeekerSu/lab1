package pkg;

import java.util.ArrayList;
import java.util.Random;

public class generateNewText {
	
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
	  
	public static int insertBridgeWordsSearch(final Graph graph, final String word1, final String word2) {
	    ArrayList<Integer> wordMarked = new ArrayList<Integer>();
	    Random random1 = new Random();
	    boolean judgement = false;
	    final int word1pos = getPosition(graph, word1);
	    final int word2pos = getPosition(graph, word2);
	    int num;
	    if (word1pos == -1 || word2pos == -1) {
	      return -1;
	    } else {
	      for (int i = 0; i < graph.matrixVertex.length; i++) {
	        if ((graph.adjacentMatrix[word1pos][i]) * (graph.adjacentMatrix[i][word2pos]) != 0) {
	          wordMarked.add(i);
	          judgement = true;
	        }
	      }
	      if (judgement) {
	        num = (random1.nextInt(100)) % (wordMarked.size());
	      } else {
	        return -1;
	      }
	      return (int) wordMarked.get(num);
	    }
	  }
	
	public static String generateNewText(final Graph graph, final String[] strGeneSplited) {
	    int mark = -1;
	    StringBuilder wordsReturnStr = new StringBuilder();
	    System.out.print("The sentence inserted bridge words:\n" + strGeneSplited[0] + " ");
	    for (int i = 0; i < strGeneSplited.length - 1; i++) {
	      mark = generateNewText.insertBridgeWordsSearch(graph, strGeneSplited[i], strGeneSplited[i + 1]);
	      if (mark != -1) {
	        wordsReturnStr.append(graph.matrixVertex[mark]);
	        wordsReturnStr.append(' ');
	      }
	      wordsReturnStr.append(strGeneSplited[i + 1]);
	      wordsReturnStr.append(' ');
	    }
	    return wordsReturnStr.toString();
	  }
}
