package pkg;//NOPMD

import java.awt.BorderLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Paths;

import java.util.*;

import javax.imageio.stream.FileImageInputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * main class.
 *@author suzichao gaokaige
 */
public class PairProgramming {
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

  /**
   * output the adjacent matrix of the graph.
   * @param graph the object of Graph
   */
  public static void print(final Graph graph) {
    System.out.println("The vertex of the directed graph:");
    for (int i = 0; i < graph.matrixVertex.length; i++) {
      System.out.print(graph.matrixVertex[i] + " ");
    }
    System.out.println("\\nThe adjacent matrix of the directed graph:");
    for (int i = 0; i < graph.matrixVertex.length; i++) {
      for (int j = 0; j < graph.matrixVertex.length; j++) {
        System.out.printf("%d ", graph.adjacentMatrix[i][j]);
      }
      System.out.println();
    }
  }

  /**
   * create directed graph.
   * @param graph the object of Graph
   * @param vex the nodes
   * @param edges the edges
   */
  public static void createDirectedGraph(Graph graph, final String[] vex, final String[] edges) {
    final int vexLen = vex.length;
    final int edgeLen = edges.length - 1;
    final String[] tmp = vex;
    graph.matrixVertex = tmp;
    graph.adjacentMatrix = new int[vexLen][vexLen];
    for (int i = 0; i < edgeLen; i++) {
      final int position1 = getPosition(graph, edges[i]);
      final int position2 = getPosition(graph, edges[i + 1]);
      graph.adjacentMatrix[position1][position2]++;
    }
  }

  /**
   * query the bridge-words.
   * @param graph the object of Graph
   * @param word1 String word1
   * @param word2 String word2
   * @return wordsReurnStr the bridge-word
   */
  public static String queryBridgeWords(final Graph graph, final String word1, final String word2) {
    boolean bridgeWord = false;
    int[] word3 = new int[graph.matrixVertex.length];
    final int word1pos = getPosition(graph, word1);
    final int word2pos = getPosition(graph, word2);
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

  /**
   * get the index of the bridge-word of two words.
   * @param graph the object of Graph
   * @param word1 String word1
   * @param word2 String word2
   * @return the index of the bridge-word
   */
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
  
  /**
   * generate new text by input text and the bridge-word.
   * @param graph the object of Graph
   * @param strGeneSplited input text
   * @return new text
   */
  public static String generateNewText(final Graph graph, final String[] strGeneSplited) {
    int mark = -1;
    StringBuilder wordsReturnStr = new StringBuilder();
    System.out.print("The sentence inserted bridge words:\n" + strGeneSplited[0] + " ");
    for (int i = 0; i < strGeneSplited.length - 1; i++) {
      mark = insertBridgeWordsSearch(graph, strGeneSplited[i], strGeneSplited[i + 1]);
      if (mark != -1) {
        wordsReturnStr.append(graph.matrixVertex[mark]);
        wordsReturnStr.append(' ');
      }
      wordsReturnStr.append(strGeneSplited[i + 1]);
      wordsReturnStr.append(' ');
    }
    return wordsReturnStr.toString();
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
   * calculate the shortest path of two words.
   * @param graph the object of Graph
   * @param word1 String word1
   * @param word2 String word2
   * @return the shortest path 
   */
  public static String calcShortestPath(final Graph graph, final String word1, final String word2) {
    int[][] path = new int[graph.matrixVertex.length][graph.matrixVertex.length];
    int[][] distance = new int[graph.matrixVertex.length][graph.matrixVertex.length];
    floyd(graph, graph.matrixVertex.length, path, distance);
    final int word1pos = getPosition(graph, word1);
    final int word2pos = getPosition(graph, word2);
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
  
  /**
   * show the directed graph.
   * @param graph the object of Graph
   */
  public static void showDirectedGraph(final Graph graph) {
    GraphViz graphViz = new GraphViz();
    graphViz.addln(graphViz.start_graph());
    for (int i = 0; i < graph.matrixVertex.length; i++) {
      for (int j = 0; j < graph.matrixVertex.length; j++) {
        if (graph.adjacentMatrix[i][j] != 0) {
          graphViz.addln(graph.matrixVertex[i] + "->" + graph.matrixVertex[j] + "[label=\""
              + graph.adjacentMatrix[i][j] + "\"]" + ";");
        }
      }
    }
    graphViz.addln(graphViz.end_graph());
    graphViz.getDotSource();
    final String type = "png";
    final File out = new File("D:\\graphOut." + type);
    graphViz.writeGraphToFile(graphViz.getGraph(graphViz.getDotSource(), type), out);
    final byte[] image = image2byte("D:\\graphOut.png");
    ScaleIcon icon = new ScaleIcon(new ImageIcon(image));
    JLabel label = new JLabel(icon);
    JFrame frame = new JFrame();
    
    final int vertical = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
    final int horizontal = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
    JScrollPane jsp = new JScrollPane(label, vertical, horizontal);
    
    frame.getContentPane().add(jsp, BorderLayout.CENTER);
    frame.setSize(icon.getIconWidth(),icon.getIconHeight());
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setVisible(true);
  }
  
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
      matrix[getPosition(graph, shortest[i])][getPosition(graph, shortest[i + 1])] = -1;
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
    final File out1 = new File("D:\\shortestPathOut." + type1);
    shortestG.writeGraphToFile(shortestG.getGraph(shortestG.getDotSource(), type1), out1);
    final byte[] image = image2byte("D:\\shortestPathOut.png");
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

  /**
   * random walk.
   * @param graph the object of Graph
   * @return the path of random walk
   */
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

  /**
   * main function.
   * @param args null
   */
  public static void main(final String[] args) {
    System.out.println("Please input the file name: ");
    Scanner input = new Scanner(System.in);
    String filename = input.next();
    Scanner fileRead = null;
    try {
      fileRead = new Scanner(Paths.get(filename));
    } catch (IOException e) {
      System.out.println("file not found");
      return;
    }
    StringBuilder str = new StringBuilder();
    while (fileRead.hasNextLine()) {
      str.append(fileRead.nextLine());
    }
    System.out.println(str);
    String strNew = str.toString().replaceAll("[^a-zA-Z]", " ").toLowerCase(Locale.US);
    Scanner strNewTrans = new Scanner(strNew);
    List<String> listEdges = new ArrayList<String>();
    List<String> listVexs = new ArrayList<String>();
    while (strNewTrans.hasNext()) {
      String listTemp = strNewTrans.next();
      listEdges.add(listTemp);
      if (!listVexs.contains(listTemp)) {
        listVexs.add(listTemp);
      }
    }
    strNewTrans.close();
    Object[] listEdges0 = listEdges.toArray();
    String[] edges = new String[listEdges0.length];
    for (int i = 0; i < listEdges0.length; i++) {
      edges[i] = listEdges0[i].toString();
    }
    Object[] listVexs0 = listVexs.toArray();
    String[] vex = new String[listVexs0.length];
    for (int i = 0; i < listVexs0.length; i++) {
      vex[i] = listVexs0[i].toString();
    }

    Graph graph = new Graph();
    String choice = "-1";
    while (!"0".equals(choice)) {
      System.out.println("There are 7 functions:");
      System.out.println(">>1.Generate a directed graph.");
      System.out.println(">>2.Show the picture of the graph.");
      System.out.println(">>3.Query the bridge words");
      System.out.println(">>4.Generate new sentence.");
      System.out.println(">>5.Get the shortest path");
      System.out.println(">>6.Random walk");
      System.out.println(">>0.Stop");
      System.out.println("Please choose a function:");
      String word1;
      String word2;
      choice = input.next();

      switch (choice) {
        case "1":
          createDirectedGraph(graph, vex, edges);
          System.out.println("The directed graph had been generated successfully!");
          break;
        case "2":
          System.out.println("The picture of the graph:");
          showDirectedGraph(graph);

          break;
        case "3":
          System.out.println("Please input the two words:");
          word1 = input.next();
          word2 = input.next();
          System.out.println(queryBridgeWords(graph, word1, word2));
          break;
        case "4":
          System.out.println("\nPlease input the sentence:");
          input.nextLine();
          final String strGene = input.nextLine();
          final String[] strGeneSplited = strGene.split(" ");
          System.out.println(generateNewText(graph, strGeneSplited));
          break;
        case "5":
          System.out.println("\nQuery the shortest path of the two words:");
          input.nextLine();
          final String words = input.nextLine();
          final String[] wordsA = words.split(" ");
          if (wordsA.length == 2) {
            word1 = wordsA[0];
            word2 = wordsA[1];
            String[] shortest = calcShortestPath(graph, word1, word2).split("->");
            System.out.print(shortest[0]);
            for (int i = 1; i < shortest.length; i++) {
              System.out.print("->" + shortest[i]);
            }
            showDirectedGraph(graph, shortest);
            System.out.println();
          } else if (wordsA.length == 1) {
            word1 = wordsA[0];
            for (int i = 0; i < vex.length; i++) {
              if (!word1.equals(vex[i])) {
                System.out.println(calcShortestPath(graph, word1, vex[i]));
                System.out.println();
              }
            }
          } else {
            System.out.println("There is an error in the input!!!");
          }
          break;
        case "6":
          System.out.println("\nWalk Random:");
          System.out.println(randomWalk(graph));
          System.out.println();
          break;
        default:
          System.out.println();
          break;
      }
    }
    System.out.println("STOPPED!!!");
    input.close();
  }
}
