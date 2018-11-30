import java.text.DecimalFormat;
import java.util.ArrayList;

public class Mati extends JonsLib {
	private ArrayList<int[][]> data;
	private int cols;
	Mati() {}
	
	Mati(int rows, int cols) {
		data = new ArrayList<>();
		ints(0, rows).forEach(i->data.add(new int[cols][4]));
		this.cols = cols;
	}

	Mati(int cols) {
		data = new ArrayList<>();
		this.cols = cols;
	}
	
	Mati(int[] row) {
		this.data = new ArrayList<>(cols = row.length);
		pushRow(row);
		
	}
	
	void pushRow(int[] newRow) {
		try {
			int[][] d2 = ints(0, newRow.length).mapToObj(i->ints(0,4).map(z->newRow[i]).toArray()).toArray(int[][]::new);
			data.add(d2);
		} catch(Exception e) {
			this.data = new ArrayList<>();
			pushRow(newRow);
		}
		cols = newRow.length;
		if(newRow.length != cols) error("pushRow() error: mismatched column size");
//		data.add(newRow);
	}
	
//	void setCol(int col, int d) { ints(0, rows()).forEach(i->row(i)[col] = d); }
//	void setSlotInRow(int slot, int row, int d) {ints(0, cols()).forEach(c->row(row)[c*4 + slot] = d);}
//	void set(int r, int c, int s, int d) { row(r)[4*c + s] = d; }
	
	int rows() { return data.size(); }
	int cols() { return cols; }
	
	int[][] row(int rowNum) { return data.get(rowNum); }
	int[][] first() { if(data.isEmpty()) error("Matirix is empty."); return row(0); }
	int[][] last() { if(data.isEmpty()) error("Matirix is empty."); return row(rows()-1); }

	void removeRow(int rowNum) {
		data.remove(rowNum);
	}
	
	void printAll() {
		DecimalFormat df = new DecimalFormat("###.#");
		for(int r = 0; r < rows(); r++) {
			println(row(r));
		}
	}
	
	boolean isEmpty() { return data.isEmpty(); }
	
//	int colSum(int colNum) {
//		if(colNum > cols-1) error("ColSum error: col out of range");
//		int sum = 0.0;
//		for(int i = 0; i < data.size(); i++)
//			sum += data.get(i)[colNum];
//		return sum;
//	}
//	int colMean(int colNum) {
//		if(colNum > cols-1) error("ColMean error: col out of range");
//		return colSum(colNum)/data.size();
//	}
//	
//	void error(String description) {
//		throw new IllegalArgumentException(description);
//	}
}