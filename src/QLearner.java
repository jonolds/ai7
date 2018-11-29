import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import com.jonslib.JonsLib;

public class QLearner extends JonsLib {
	Random r;
	Mat Q, R, M;
	static final double epsilon = .05, gamma = .97, ak = .1;
	int act = 0;
	int[] st;
	
	void readMap(String filename) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(filename));
		if(sc.hasNextLine()) {
			char[] line = sc.nextLine().replace(",", "").toCharArray();
			M = new Mat(line.length);
			M.pushRow(translateLine(line));
		}
		while(sc.hasNextLine())
			M.pushRow(translateLine(sc.nextLine().replace(",", "").toCharArray()));
		sc.close();
	}
	double[] translateLine(char[] ln) {
		double[] row = new double[ln.length];
		ints(0, ln.length).forEach(i-> row[i] = (ln[i] == '#') ? -1.0 : (ln[i] == 'G') ? 100.0 : 0.0 );
		return row;
	}
	
	QLearner() throws FileNotFoundException {
		readMap("in_map.txt");
		r = new Random();
		st= new int[] {M.rows() -1, 0};
		M.printAll();
	}
	void run() {
		if(r.nextDouble() < epsilon)
			act = r.nextInt(4);
		else {
			act = 0;
			ints(0, 4).forEach(i->act = getMoveVal(st, i) > getMoveVal(st, act) ? i : act);
			act = getMoveVal(st, act) == 0.0 ? r.nextInt(4) : act;
		}
		st = makeMove(act);
	}
	int[] makeMove(int move) { 
		return new int[] {99,99}; 
	}
	double getMoveVal(int[] state, int move) { 
		return move; 
	}
	boolean isConverged() { 
		return false; 
	}
	public static void main(String[] args) throws FileNotFoundException {
		(new QLearner()).run();
	}
}