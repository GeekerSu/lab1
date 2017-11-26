package pkg;

import java.awt.BorderLayout;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.stream.FileImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class calcShortestPath {
	 /**
	   * get the binary file of the picture.
	   * @param path the path of the picture
	   * @return data binary code of the picture
	   */
	  public static byte[] image2byte(final String path) {
	    byte[] data = null;
	    FileImageInputStream input;
	    try {
	      input = new FileImageInputStream(new File(path));
	      ByteArrayOutputStream output = new ByteArrayOutputStream();
	      byte[] buf = new byte[1024];
	      int numBytesRead;
	      numBytesRead = input.read(buf);
	      while (numBytesRead != -1) {
	        output.write(buf, 0, numBytesRead);
	        numBytesRead = input.read(buf);
	      }
	      data = output.toByteArray();
	      output.close();
	      input.close();
	    } catch (FileNotFoundException ex1) {
	      ex1.printStackTrace();
	    } catch (IOException ex1) {
	      ex1.printStackTrace();
	    }
	    return data;
	  }
	
	 /**
	   * Floyd algorithm.
	   * @param graph the object of Graph
	   * @param vexLen the number of vertexes
	   * @param path the shortest path
	   * @param distance the shortest distance
	   */
	  public static void floyd(final Graph graph, final int vexLen, int[][] path, int[][] distance) {
		    for (int i = 0; i < vexLen; i++) {
		      for (int j = 0; j < vexLen; j++) {
		        if (graph.adjacentMatrix[i][j] == 0) {
		          path[i][j] = -1;
		        } else {
		          path[i][j] = j;
		        }
		        distance[i][j] = graph.adjacentMatrix[i][j];
		      }
		    }
		    for (int k = 0; k < vexLen; k++) {
		      for (int i = 0; i < vexLen; i++) {
		        for (int j = 0; j < vexLen; j++) {
		          if (i == j || j == k || i == k) {
		            continue;
		          }
		          if (distance[i][k] == 0 || distance[k][j] == 0) {
		            continue;
		          }
		          if (distance[i][k] + distance[k][j] < distance[i][j] || distance[i][j] == 0) {
		            distance[i][j] = distance[i][k] + distance[k][j];
		            path[i][j] = path[i][k];
		          }
		        }
		      }
		    }
		  }


	  /**
	   * show the directed graph.
	   * @param graph the object of Graph
	   * @param shortest the shortest path
	   */
	  /**
	   * show the directed graph.
	   * @param graph the object of Graph
	   * @param shortest the shortest path
	   */
	  public static void showDirectedGraph(final Graph graph, final String[] shortest) {
	    GraphViz shortestG = new GraphViz();
	    shortestG.addln(shortestG.start_graph());

	    int[][] matrix = new int[graph.matrixVertex.length][graph.matrixVertex.length];
	    for (int i = 0; i < graph.matrixVertex.length; i++) {
	      for (int j = 0; j < graph.matrixVertex.length; j++) {
	        matrix[i][j] = graph.adjacentMatrix[i][j];
	      }
	    }

	    for (int i = 0; i < shortest.length - 1; i++) {
	      matrix[Graph.getPosition(graph, shortest[i])][Graph.getPosition(graph, shortest[i + 1])] = -1;
	    }

	    for (int i = 0; i < graph.matrixVertex.length; i++) {
	      for (int j = 0; j < graph.matrixVertex.length; j++) {
	        if (matrix[i][j] > 0) {
	          shortestG.addln(
	              graph.matrixVertex[i] + "->" + graph.matrixVertex[j] + "[label=\"" 
	              + graph.adjacentMatrix[i][j] + "\"]" + ";");
	        } else if (matrix[i][j] < 0) {
	          shortestG.addln(graph.matrixVertex[i] + "->" + graph.matrixVertex[j] 
	              + "[color=\"red\",label=\""
	              + graph.adjacentMatrix[i][j] + "\"];");
	        }
	      }
	    }
	    shortestG.addln(shortestG.end_graph());
	    shortestG.getDotSource();
	    final String type1 = "png";
	    final File out1 = new File("C:\\temp\\shortestPathOut." + type1);
	    shortestG.writeGraphToFile(shortestG.getGraph(shortestG.getDotSource(), type1), out1);
	    final byte[] image = image2byte("C:\\temp\\shortestPathOut.png");
	    ScaleIcon icon = new ScaleIcon(new ImageIcon(image));
	    JLabel label = new JLabel(icon);
	    label.repaint();
	    label.updateUI();
	    label.setVisible(true);
	    JFrame frame = new JFrame();
	    final int vertical = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
	    final int horizontal = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;

	    JScrollPane jsp = new JScrollPane(label, vertical, horizontal);
	    frame.getContentPane().add(jsp, BorderLayout.CENTER);
	    frame.setSize(icon.getIconWidth(),icon.getIconHeight());
	    frame.setLocationRelativeTo(null);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setVisible(true);
	    System.out.println("\nshortestPathOut.pnghad been output successfully");
	  }
	  
	  public static String calcShortestPath(final Graph graph, final String word1, final String word2) {
		    int[][] path = new int[graph.matrixVertex.length][graph.matrixVertex.length];
		    int[][] distance = new int[graph.matrixVertex.length][graph.matrixVertex.length];
		floyd(graph, graph.matrixVertex.length, path, distance);
		    final int word1pos =Graph.getPosition(graph, word1);
		    final int word2pos = Graph.getPosition(graph, word2);
		    if (word1pos == -1 || word2pos == -1) {
		      System.out.println("There are not words in the graph");
		      return "";
		    }
		    int pathScanner = path[word1pos][word2pos];
		    StringBuilder wordsReturnStr = new StringBuilder();
		    if (pathScanner == -1) {
		      System.out.println("There is no path" + "start word\"" + word1 + " \"to end word\"" + word2);
		    } else {
		      if (word1pos != word2pos) {
		        System.out.print("The shortest distance" + "start word \"" + word1 
		            + " \"end word \"" + word2 + " \"is ");
		        wordsReturnStr.append(word1);
		        while (pathScanner != word2pos) {
		          wordsReturnStr.append("->");
		          wordsReturnStr.append(graph.matrixVertex[pathScanner]);
		          pathScanner = path[pathScanner][word2pos];
		        }
		        wordsReturnStr.append("->");
		        wordsReturnStr.append(word2);
		        System.out.println(distance[word1pos][word2pos]);
		      }
		    }
		    return wordsReturnStr.toString();
		  }
}
