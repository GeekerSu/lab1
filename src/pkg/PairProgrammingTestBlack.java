package pkg;

import static org.junit.Assert.*;

import org.junit.Test;

public class PairProgrammingTestBlack {

	@Test
	public void testQueryBridgeWords1() {
		Graph GraphTest = new Graph();
		String VexOrigin ="to explore strange new worlds seek out life and civilizations get";
		String[] VexTest= VexOrigin.split(" ");
		String EdgesOrigin ="to explore strange new worlds to seek out new life and new civilizations to get out";
		String[] EdgesTest= EdgesOrigin.split(" ");
		PairProgramming.createDirectedGraph(GraphTest, VexTest, EdgesTest);
		String testresult = PairProgramming.queryBridgeWords(GraphTest, "to", "strange");
		assertEquals("The bridge word from \"to\" to \"strange\" :explore ",testresult);
		
	}
	@Test
	public void testQueryridgeWords2() {
		Graph GraphTest = new Graph();
		String VexOrigin ="to explore strange new worlds seek out life and civilizations get";
		String[] VexTest= VexOrigin.split(" ");
		String EdgesOrigin ="to explore strange new worlds to seek out new life and new civilizations to get out";
		String[] EdgesTest= EdgesOrigin.split(" ");
		PairProgramming.createDirectedGraph(GraphTest, VexTest, EdgesTest);
		String testresult = PairProgramming.queryBridgeWords(GraphTest, "to", "new");
		assertEquals("No bridge word from \"to\" to \"new\" !",testresult);
	}
		@Test
	public void testQueryBridgeWords3() {
			Graph GraphTest = new Graph();
			String VexOrigin ="to explore strange new worlds seek out life and civilizations get";
			String[] VexTest= VexOrigin.split(" ");
			String EdgesOrigin ="to explore strange new worlds to seek out new life and new civilizations to get out";
			String[] EdgesTest= EdgesOrigin.split(" ");
			PairProgramming.createDirectedGraph(GraphTest, VexTest, EdgesTest);
			String testresult = PairProgramming.queryBridgeWords(GraphTest, "a", "to");
			assertEquals("No \"a\" in graph!",testresult);
	}
		@Test
		public void testQueryridgeWords4() {
			Graph GraphTest = new Graph();
			String VexOrigin ="to explore strange new worlds seek out life and civilizations get";
			String[] VexTest= VexOrigin.split(" ");
			String EdgesOrigin ="to explore strange new worlds to seek out new life and new civilizations to get out";
			String[] EdgesTest= EdgesOrigin.split(" ");
			PairProgramming.createDirectedGraph(GraphTest, VexTest, EdgesTest);
			String testresult = PairProgramming.queryBridgeWords(GraphTest, "a", "b");
			assertEquals("No \"a\" and \"b\" in graph!",testresult);
		}
}
