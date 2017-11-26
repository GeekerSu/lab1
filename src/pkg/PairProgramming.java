package pkg;//NOPMD

import java.io.IOException;

import java.nio.file.Paths;

import java.util.*;

/**
 * main class.
 *@author suzichao gaokaige
 */
public class PairProgramming {
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
        	createDirectedGraph.createDirectedGraph(graph, vex, edges);
          System.out.println("The directed graph had been generated successfully!");
          break;
        case "2":
          System.out.println("The picture of the graph:");
          showDirectedGraph.showDirectedGraph(graph);

          break;
        case "3":
          System.out.println("Please input the two words:");
          word1 = input.next();
          word2 = input.next();
          System.out.println(queryBridgeWords.queryBridgeWords(graph, word1, word2));
          break;
        case "4":
          System.out.println("\nPlease input the sentence:");
          input.nextLine();
          final String strGene = input.nextLine();
          final String[] strGeneSplited = strGene.split(" ");
          System.out.println(generateNewText.generateNewText(graph, strGeneSplited));
          break;
        case "5":
          System.out.println("\nQuery the shortest path of the two words:");
          input.nextLine();
          final String words = input.nextLine();
          final String[] wordsA = words.split(" ");
          if (wordsA.length == 2) {
            word1 = wordsA[0];
            word2 = wordsA[1];
            String[] shortest = calcShortestPath.calcShortestPath(graph, word1, word2).split("->");
            System.out.print(shortest[0]);
            for (int i = 1; i < shortest.length; i++) {
              System.out.print("->" + shortest[i]);
            }
            calcShortestPath.showDirectedGraph(graph, shortest);
            System.out.println();
          } else if (wordsA.length == 1) {
            word1 = wordsA[0];
            for (int i = 0; i < vex.length; i++) {
              if (!word1.equals(vex[i])) {
                System.out.println(calcShortestPath.calcShortestPath(graph, word1, vex[i]));
                System.out.println();
              }
            }
          } else {
            System.out.println("There is an error in the input!!!");
          }
          break;
        case "6":
          System.out.println("\nWalk Random:");
          System.out.println(randomWalk.randomWalk(graph));
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
