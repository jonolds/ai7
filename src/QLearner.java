import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;


public class QLearner extends JonsLib {
	Random r = new Random();
	Mat Q, R, M = new Mat();
	static final double epsilon = .05, gamma = .97, ak = .1, pay = 100;
	static int[] startState;

	
	void loadMap(String filename) throws IOException {
		Files.lines(Paths.get(filename)).forEach(ln->{M.pushRow(lnToArr(ln.replace(",", "").toCharArray()));});
		if(M.isEmpty()) error("Empty data file");
		ints(0, M.cols()).forEach(c->{if(M.row(0)[4*c + 1] < pay) M.set(0, c, 1, -1);});
		ints(0, M.cols()).forEach(c->{if(M.last()[4*c + 2] < pay)M.set(M.rows()-1, c, 2, -1);});
		ints(0, M.rows()).forEach(r->{if(M.row(r)[0] < pay) M.set(r, 0, 0, -1);});
		ints(0, M.rows()).forEach(r->{if(M.row(r)[M.cols()*4-1] < pay) M.set(r, M.cols()-1, 3, -1);});
	}
	double[] lnToArr(char[] ln) {
		double[] row = new double[ln.length*4];
		ints(0, ln.length).forEach(c-> ints(0, 4).forEach(i->row[slot(c, i)] = (ln[c] == '#') ? -1 : (ln[c] == 'G') ? pay : 0));
		return row;
	}
	int slot(int col, int slot) {
		return 4*col + slot;
	}
	
	double Qia(int[] i, int act) {
		return 0.0;
	}
	
	
	QLearner() throws IOException {
		loadMap("in_map.txt");
		startState = new int[] {M.rows() -1, 0};
		M.printAll();
	}
	void run(int[] state) {
		int[] i = state;
		int act;
		
		if(r.nextDouble() < epsilon)
			act = r.nextInt(4);
		else {
			act = 0;
//			act = ints(0, 4).map(z->getMoveVal(i, z) > getMoveVal(i, act) ? z : act);
			act = getMoveVal(i, act) == 0.0 ? r.nextInt(4) : act;
		}
		i = makeMove(act);
	}

	public static void main(String[] args) throws IOException {
		QLearner ql = new QLearner();
		ql.run(startState);
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
}