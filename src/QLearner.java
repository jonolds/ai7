import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;


public class QLearner extends JonsLib {
	Random r;
	Mat Q, R= new Mat();
	static final double epsilon = .45, gamma = .97, ak = .1, pay = Double.MAX_VALUE, penalty = Double.MIN_VALUE;
	static int[] st, startState;
	int iters = 0;

	public static void main(String[] args) throws IOException {
		(new QLearner()).run();
	}
	
	void run() {
		st = startState;
		for(int a = 0; a < 200; a++) {
			for(int b = 0; b < 4999999; b++) 
				run2();
			println("\n\n\n\n\n");
			println(a);
			Q.policy(R);
		}
		println();
		Q.printAll();
	}

	int[] doAction(int a) {
		int[] st2 = copy(st);
		st2[0] = (a == 1) ? st2[0] -1 : (a == 2) ? st2[0] + 1 : st2[0];
		st2[1] = (a == 0) ? st2[1] -1 : (a == 3) ? st2[1] + 1 : st2[1];
		return (st2[0] < 0 || st2[1] < 0 || st2[0] >= R.rows() || st2[1] >= R.cols()) ? st : st2;
	}
	

	
	void run2() {
		int[] i = copy(st);
		int act = 5;
		
		act = (r.nextDouble() < epsilon) ? r.nextInt(4) : ((act = Q.maxSlot(i[0], i[1])) >3) ? r.nextInt(4) : act;
			
		int[] i2 = doAction(act);

		//Learn from move if i2 != i
		if(i[0] != i2[0] || i[1] != i2[1]) {
			
			double left = (1-ak)*Q.row(i[0])[i[1]][act];
			
			double riaj = R.row(i2[0])[i2[1]][0];
			double maxQjb = Q.maxValue(i2[0], i2[1]);
			double right = ak*(riaj + gamma*(maxQjb));
			
			double newIval = left + right;
			Q.setSlot(i[0], i[1], act, newIval);
		}
		st = (R.row(i2[0])[i2[1]][act] >0) ? copy(startState) : copy(i2);
	}
	

	
	boolean isConverged() { 
		return iters > 10000;
	}
		
	void loadMap(String filename) throws IOException {
		Files.lines(Paths.get(filename)).forEach(ln->{R.pushSlottedRow(lnToArr(ln.replace(",", "").toCharArray()));});
		if(R.isEmpty()) error("Empty data file");
		Q = new Mat(R.rows(), R.cols());
	}
	double[] lnToArr(char[] ln) {
		return ints(0, ln.length).mapToDouble(c-> (ln[c] == '#') ? penalty : (ln[c] == 'G') ? pay : 0.0).toArray();
	}
	
	QLearner() throws IOException {
		r = new Random();
		loadMap("in_map.txt");
		System.out.println("startState: " + toStr(startState = new int[] {R.rows() -1, 0}));
	}
}