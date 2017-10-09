import java.util.*;
import java.io.IOException;
import java.io.*;
//b2 change
class MatrixDG {
	//the changes of tests
//kaigegao is always handsome
//second change
	public String[] mVexs; // 顶点集合
	public int[][] mMatrix; // 邻接矩阵
	// 创建图

	public MatrixDG(String[] vex, String[] edges) {

		// 初始化"顶点数"和"边数"
		int vlen = vex.length;
		int elen = edges.length - 1;

		// 初始化"顶点"
		mVexs = new String[vlen];
		for (int i = 0; i < mVexs.length; i++)
			mVexs[i] = vex[i];

		// 初始化"边"
		setmMatrix(new int[vlen][vlen]);
		for (int i = 0; i < elen; i++) {
			// 读取边的起始顶点和结束顶点
			int p1 = getPosition(edges[i]);
			int p2 = getPosition(edges[i + 1]);

			getmMatrix()[p1][p2]++;
		}
	}

	// 返回位置
	public int getPosition(String node) {
		for (int i = 0; i < mVexs.length; i++)
			if (mVexs[i].equals(node))
				return i;
		return -1;
	}

	// 打印矩阵队列图
	public void print() {
		System.out.println("有向图的顶点：");
		for (int i = 0; i < mVexs.length; i++) {
			System.out.print(mVexs[i] + " ");
		}
		System.out.println("\n有向图的邻接矩阵：");
		for (int i = 0; i < mVexs.length; i++) {
			for (int j = 0; j < mVexs.length; j++)
				System.out.printf("%d ", getmMatrix()[i][j]);
			System.out.println();
		}
	}

	public int[][] getmMatrix() {
		return mMatrix;
	}

	public void setmMatrix(int[][] mMatrix) {
		this.mMatrix = mMatrix;
	}

	// 查询桥接词
	public void queryBridgeWords(String[] vex, String word1, String word2) {
		boolean bridgeword = false;
		int[] word3 = new int[vex.length];
		int word1pos = getPosition(word1);
		int word2pos = getPosition(word2);
		if (word1pos == -1) {
			if (word2pos == -1)
				System.out.println("No \"" + word1 + " \"and \"" + word2 + " \" in graph!");
			else
				System.out.println("No \"" + word1 + " \" in graph!");
		} else if (word2pos == -1)
			System.out.println("No \"" + word2 + " \" in graph!");
		else {
			for (int i = 0; i < vex.length; i++) {
				if ((mMatrix[word1pos][i]) * (mMatrix[i][word2pos]) != 0) {
					word3[i] = 1;
					bridgeword = true;
				}
			}
		}
		if (bridgeword) {
			System.out.print("The bridge word from \"" + word1 + " \"to \"" + word2 + " \" :");
			for (int i = 0; i < vex.length; i++)
				if (word3[i] == 1)
					System.out.print(vex[i] + " ");
		} else
			System.out.println("No bridge word from \"" + word1 + " \"to \"" + word2 + " \" !");
	}

	public int insertBridgeWordsSearch(String[] vex, String word1, String word2) {
		ArrayList wordmarked = new ArrayList();
		Random random1 = new Random();
		boolean judgement = false;
		int word1pos = getPosition(word1);
		int word2pos = getPosition(word2);
		int num;
		if (word1pos == -1 || word2pos == -1)
			return -1;
		else {
			for (int i = 0; i < vex.length; i++) {
				if ((mMatrix[word1pos][i]) * (mMatrix[i][word2pos]) != 0) {
					wordmarked.add(i);
					judgement = true;
				}
			}
			if (!judgement)
				return -1;
			else
				num = (random1.nextInt(100)) % (wordmarked.size());
			return (int) wordmarked.get(num);
		}

	}

	public void generateNewText(String[] vex, String[] strGeneSplited) {
		int mark = -1;
		System.out.print("插入桥接词后的语句：\n" + strGeneSplited[0] + " ");
		for (int i = 0; i < strGeneSplited.length - 1; i++) {
			mark = insertBridgeWordsSearch(vex, strGeneSplited[i], strGeneSplited[i + 1]);
			if (mark != -1) {
				System.out.print(vex[mark] + " ");
			}
			System.out.print(strGeneSplited[i + 1] + " ");
		}
	}

	public void Floyd(int vexlen, int[][] Path, int[][] Distance) {
		for (int i = 0; i < vexlen; i++)
			for (int j = 0; j < vexlen; j++) {
				if (mMatrix[i][j] != 0)
					Path[i][j] = j;
				else
					Path[i][j] = -1;
				Distance[i][j] = mMatrix[i][j];
			}
		for (int k = 0; k < vexlen; k++) {
			for (int i = 0; i < vexlen; i++)
				for (int j = 0; j < vexlen; j++) {
					if (i == j || j == k || i == k)
						continue;
					if (Distance[i][k] != 0 && Distance[k][j] != 0)
						if (Distance[i][k] + Distance[k][j] < Distance[i][j] || Distance[i][j] == 0) {
							Distance[i][j] = Distance[i][k] + Distance[k][j];
							Path[i][j] = Path[i][k];
						}
				}
		}
	}

	public void calcShortestPath(String[] vex, String word1, String word2) {
		int[][] Path = new int[vex.length][vex.length];
		int[][] Distance = new int[vex.length][vex.length];
		Floyd(vex.length, Path, Distance);
		int word1pos = getPosition(word1);
		int word2pos = getPosition(word2);
		int pathScanner = Path[word1pos][word2pos];
		if (pathScanner == -1)
			System.out.println("起点 \"" + word1 + " \"到终点 \"" + word2 + " \"无路径。");
		else {
			if (word1pos != word2pos) {
				System.out.print("起点 \"" + word1 + " \"到终点 \"" + word2 + " \"最短路径：" + word1);
				while (pathScanner != word2pos) {
					System.out.print("->" + vex[pathScanner]);
					pathScanner = Path[pathScanner][word2pos];
				}
				System.out.println("->" + word2);
				System.out.println("路径长度为：" + Distance[word1pos][word2pos]);
			}
		}
	}

}


public class pair_programming {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		/*String str = sc.nextLine();
		System.out.println(str);// test*/
		String str=null;
		try{
			File myfile=new File("C:\\Users\\54507\\Desktop\\oo.txt");
			FileReader fr=new FileReader(myfile);
			BufferedReader reader=new BufferedReader(fr);
			String line=null;
			while((line=reader.readLine())!=null)
			{
				str=line;
				System.out.println(line);
			}
			reader.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		String strNew = str.replaceAll("[^a-zA-Z]", " ").toLowerCase();
		String[] s = strNew.split(" ");// 字符串的分割，可得到有向图的各条边
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < s.length; i++)
			if (!list.contains(s[i]))
				list.add(s[i]);
		Object[] vex0 = list.toArray();// 得到顶点集
		String[] vex = new String[vex0.length];
		for (int i = 0; i < vex0.length; i++) {
			vex[i] = vex0[i].toString();
		}
		MatrixDG Graph;
		// 有向图邻接矩阵创建与输出
		Graph = new MatrixDG(vex, s);
		Graph.print();

		 // 查询桥接词
		 System.out.println("请输入word1，word2：");
		 String words0 = sc.nextLine();
		 String[] words = words0.split(" ");
		 String word1 = words[0];
		 String word2 = words[1];
		 Graph.queryBridgeWords(vex, word1, word2);
		
		 // 桥接词插入
		 System.out.println("\n请输入要插入桥接词的语句：");
		 String strGene = sc.nextLine();
		 String[] strGeneSplited = strGene.split(" ");
		 Graph.generateNewText(vex, strGeneSplited);
		
		 // 最短路径
		 System.out.println("\n请输入word1，word2：");
		 words0 = sc.nextLine();
		 words = words0.split(" ");
		 word1 = words[0];
		 word2 = words[1];
		 Graph.calcShortestPath(vex, word1, word2);

		// 随机游走
		Random random = new Random();
		int[][] Matrix = new int[vex.length][vex.length];
		for (int i = 0; i < vex.length; i++) {
			for (int j = 0; j < vex.length; j++) {
				Matrix[i][j] = Graph.mMatrix[i][j];
			}
		}
		int numToGo = (random.nextInt(100)) % vex.length;
		System.out.println();
		System.out.print(vex[numToGo]);
		int productmatrix = 1;
		while ( productmatrix != 0) {
			int mark = numToGo;
			ArrayList canChoose = new ArrayList();
			for (int i = 0; i < vex.length; i++) {
				if (Matrix[numToGo][i] != 0) {
					canChoose.add(i);
				}
			}
//			if (canChoose.size() == 0)
//				break;
			numToGo = (int) canChoose.get((random.nextInt(100)) % (canChoose.size()));
			Matrix[mark][numToGo] = 0;
			System.out.print("->" + vex[numToGo]);
			productmatrix = 0;
			for (int i = 0; i < vex.length; i++)
				productmatrix += Matrix[numToGo][i];
		}
		System.out.println();
	}
}
