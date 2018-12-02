import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Mat extends JonsLib {
	private ArrayList<double[][]> data;
	private int cols;
	Mat() {}
	
	Mat(int r, int c) {
		data = new ArrayList<>();
		ints(0, r).forEach(i->data.add(new double[c][4]));
		this.cols = c;
//		this.rows = r;
	}

	Mat(int cols) {
		data = new ArrayList<>();
		this.cols = cols;
	}
	
	Mat(double[] row) {
		this.data = new ArrayList<>(cols = row.length);
		pushSlottedRow(row);
	}
	
	//PRINT POLICY
	void policy(Mat R) {
		char[][] p = new char[rows()][cols()];
		for(int r = 0; r < rows(); r++)
			for(int c = 0; c < cols(); c++) {
				int s = maxSlot(r, c);
				p[r][c] = s == 0 ? '<' : (s == 1) ? '^' : (s == 2) ? 'v' : (s == 3) ? '>' : '+';
				if(R.row(r)[c][0] == Double.MIN_VALUE)
					p[r][c] = '#';
				else if(R.row(r)[c][0] == Double.MAX_VALUE)
					p[r][c] = 'G';
			}
		
		for(int r = 0; r < rows(); r++) {
			for(int c = 0; c < cols(); c++) 
				print(p[r][c] + " ");
			println();
		}
	}
	
	//Returns slot with highest value or 9 if all slots are 0.
	int maxSlot(int r, int c) {
		int best_slot = 0;
		double[] slots = row(r)[c];
		for(int i = 1; i < 4; i++)
			if(slots[i] > slots[best_slot])
				best_slot = i;
		return (slots[best_slot] == 0) ? 9 : best_slot;
	}
	
	double maxValue(int r, int c) {
		int maxSlot = maxSlot(r, c);
		return maxSlot < 4 ? row(r)[c][maxSlot] : 0;
	}
	
	void pushSlottedRow(double[] newRow) {
		double[][] d2 = ints(0, newRow.length).mapToObj(i->ints(0,4).mapToDouble(z->newRow[i]).toArray()).toArray(double[][]::new);
		try {
			data.add(d2);
		} catch(Exception e) {
			this.data = new ArrayList<>();
			pushSlottedRow(newRow);
		}
		cols = newRow.length;
//		rows = data.size();
		if(newRow.length != cols) error("pushRow() error: mismatched column size");
	}
	
	void setSlot(int r, int c, int s, double v) {
		row(r)[c][s] = v;
	}
	
	int rows() { return data.size(); }
	int cols() { return cols; }
	
	double[][] row(int rowNum) { return data.get(rowNum); }
	double[][] first() { if(data.isEmpty()) error("Matrix is empty."); return row(0); }
	double[][] last() { if(data.isEmpty()) error("Matrix is empty."); return row(rows()-1); }

	void removeRow(int rowNum) {
		data.remove(rowNum);
	}
	
	void printAll() {
		for(int r = 0; r < rows(); r++) {
			final double[][] rr = row(r);
			println(ints(0, rr.length).mapToObj(i->toStrMAT(rr[i])).collect(Collectors.joining(",")));
		}
	}
	String toStrMAT(double[] arr) { 
		return "[" + Arrays.stream(arr).mapToObj(i->formatDbl(i)).collect(Collectors.joining(",")) + "]"; 
	}
	String formatDbl(double d) {
		DecimalFormat df = new DecimalFormat("#0.#");
		return String.format("%3s", df.format((int)(d*10)));
	}
	
	
	boolean isEmpty() { return data.isEmpty(); }
	
//	double colSum(int colNum) {
//		if(colNum > cols-1) error("ColSum error: col out of range");
//		double sum = 0.0;
//		for(int i = 0; i < data.size(); i++)
//			sum += data.get(i)[colNum];
//		return sum;
//	}
//	double colMean(int colNum) {
//		if(colNum > cols-1) error("ColMean error: col out of range");
//		return colSum(colNum)/data.size();
//	}
//	
//	void error(String description) {
//		throw new IllegalArgumentException(description);
//	}
}