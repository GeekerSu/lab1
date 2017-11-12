package pkg;

import static org.junit.Assert.*;

import org.junit.Test;

public class PairProgrammingTestWhite {

	@Test
	public void testcalcShortestPath1() {
		Graph GraphTest = new Graph();
		String VexOrigin ="to explore strange new worlds seek out life and civilizations get";
		String[] VexTest= VexOrigin.split(" ");
		String EdgesOrigin ="to explore strange new worlds to seek out new life and new civilizations to get out";
		String[] EdgesTest= EdgesOrigin.split(" ");
		PairProgramming.createDirectedGraph(GraphTest, VexTest, EdgesTest);
		String testresult = PairProgramming.calcShortestPath(GraphTest, "a", "strange");
		assertEquals("",testresult);
		
	}
	@Test
	public void testcalcShortestPath2() {
		Graph GraphTest = new Graph();
		String VexOrigin ="to explore strange new worlds seek out life and civilizations get";
		String[] VexTest= VexOrigin.split(" ");
		String EdgesOrigin ="to explore strange new worlds to seek out new life and new civilizations to get out";
		String[] EdgesTest= EdgesOrigin.split(" ");
		PairProgramming.createDirectedGraph(GraphTest, VexTest, EdgesTest);
		String testresult = PairProgramming.calcShortestPath(GraphTest, "to", "a");
		assertEquals("",testresult);
		
	}
	@Test
	public void testcalcShortestPath3() {
		Graph GraphTest = new Graph();
		String VexOrigin ="to explore strange new worlds";
		String[] VexTest= VexOrigin.split(" ");
		String EdgesOrigin ="to explore strange new worlds";
		String[] EdgesTest= EdgesOrigin.split(" ");
		PairProgramming.createDirectedGraph(GraphTest, VexTest, EdgesTest);
		String testresult = PairProgramming.calcShortestPath(GraphTest, "new", "to");
		assertEquals("",testresult);
		
	}

	@Test
	public void testcalcShortestPath5() {
		Graph GraphTest = new Graph();
		String VexOrigin ="to explore strange new worlds seek out life and civilizations get";
		String[] VexTest= VexOrigin.split(" ");
		String EdgesOrigin ="to explore strange new worlds to seek out new life and new civilizations to get out";
		String[] EdgesTest= EdgesOrigin.split(" ");
		PairProgramming.createDirectedGraph(GraphTest, VexTest, EdgesTest);
		String testresult = PairProgramming.calcShortestPath(GraphTest, "new", "worlds");
		assertEquals("new->worlds",testresult);
		
	}
	@Test
	public void testcalcShortestPath6() {
		Graph GraphTest = new Graph();
		String VexOrigin ="to explore strange new worlds seek out life and civilizations get";
		String[] VexTest= VexOrigin.split(" ");
		String EdgesOrigin ="to explore strange new worlds to seek out new life and new civilizations to get out";
		String[] EdgesTest= EdgesOrigin.split(" ");
		PairProgramming.createDirectedGraph(GraphTest, VexTest, EdgesTest);
		String testresult = PairProgramming.calcShortestPath(GraphTest, "new", "seek");
		assertEquals("new->worlds->to->seek",testresult);
		
	}
}
