import java.util.*;
import java.io.IOException;
import java.io.*;
//b2 change
class MatrixDG {
	//the changes of tests
//kaigegao is always handsome
//second change
	public String[] mVexs; // ���㼯��
	public int[][] mMatrix; // �ڽӾ���
	// ����ͼ

	public MatrixDG(String[] vex, String[] edges) {

		// ��ʼ��"������"��"����"
		int vlen = vex.length;
		int elen = edges.length - 1;

		// ��ʼ��"����"
		mVexs = new String[vlen];
		for (int i = 0; i < mVexs.length; i++)
			mVexs[i] = vex[i];

		// ��ʼ��"��"
		setmMatrix(new int[vlen][vlen]);
		for (int i = 0; i < elen; i++) {
			// ��ȡ�ߵ���ʼ����ͽ�������
			int p1 = getPosition(edges[i]);
			int p2 = getPosition(edges[i + 1]);

			getmMatrix()[p1][p2]++;
		}
	}

	// ����λ��
	public int getPosition(String node) {
		for (int i = 0; i < mVexs.length; i++)
			if (mVexs[i].equals(node))
				return i;
		return -1;
	}

	// ��ӡ�������ͼ
	public void print() {
		System.out.println("����ͼ�Ķ��㣺");
		for (int i = 0; i < mVexs.length; i++) {
			System.out.print(mVexs[i] + " ");
		}
		System.out.println("\n����ͼ���ڽӾ���");
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

	// ��ѯ�ŽӴ�
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
		System.out.print("�����ŽӴʺ����䣺\n" + strGeneSplited[0] + " ");
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
			System.out.println("��� \"" + word1 + " \"���յ� \"" + word2 + " \"��·����");
		else {
			if (word1pos != word2pos) {
				System.out.print("��� \"" + word1 + " \"���յ� \"" + word2 + " \"���·����" + word1);
				while (pathScanner != word2pos) {
					System.out.print("->" + vex[pathScanner]);
					pathScanner = Path[pathScanner][word2pos];
				}
				System.out.println("->" + word2);
				System.out.println("·������Ϊ��" + Distance[word1pos][word2pos]);
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
		String[] s = strNew.split(" ");// �ַ����ķָ�ɵõ�����ͼ�ĸ�����
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < s.length; i++)
			if (!list.contains(s[i]))
				list.add(s[i]);
		Object[] vex0 = list.toArray();// �õ����㼯
		String[] vex = new String[vex0.length];
		for (int i = 0; i < vex0.length; i++) {
			vex[i] = vex0[i].toString();
		}
		MatrixDG Graph;
		// ����ͼ�ڽӾ��󴴽������
		Graph = new MatrixDG(vex, s);
		Graph.print();

		 // ��ѯ�ŽӴ�
		 System.out.println("������word1��word2��");
		 String words0 = sc.nextLine();
		 String[] words = words0.split(" ");
		 String word1 = words[0];
		 String word2 = words[1];
		 Graph.queryBridgeWords(vex, word1, word2);
		
		 // �ŽӴʲ���
		 System.out.println("\n������Ҫ�����ŽӴʵ���䣺");
		 String strGene = sc.nextLine();
		 String[] strGeneSplited = strGene.split(" ");
		 Graph.generateNewText(vex, strGeneSplited);
		
		 // ���·��
		 System.out.println("\n������word1��word2��");
		 words0 = sc.nextLine();
		 words = words0.split(" ");
		 word1 = words[0];
		 word2 = words[1];
		 Graph.calcShortestPath(vex, word1, word2);

		// �������
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
