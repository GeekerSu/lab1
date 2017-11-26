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

public class showDirectedGraph {
	
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
	    final File out = new File("C:\\temp\\graphOut." + type);
	    graphViz.writeGraphToFile(graphViz.getGraph(graphViz.getDotSource(), type), out);
	    final byte[] image = image2byte("C:\\temp\\graphOut.png");
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
}
