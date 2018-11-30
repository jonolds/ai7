import java.util.ArrayList;

public class Mat extends JonsLib {

	private ArrayList<double[]> data;
	private int cols;
	
//	Mat(int rows, int cols) {
//		data = new ArrayList<>();
//		ints(0, rows).forEach(i->data.add(new double[cols]));
//		this.cols = cols;
//	}
	
	Mat() {}
	
	Mat(int cols) {
		data = new ArrayList<>();
		this.cols = cols;
	}
	
	Mat(double[] row) {
		this.data = new ArrayList<>(cols = row.length);
		pushRow(row);
		
	}
	
	void pushRow(double[] newRow) {
		try {
			data.add(newRow);
		} catch(Exception e) {
			this.data = new ArrayList<>(cols = newRow.length);
			pushRow(newRow);
		}
		if(newRow.length != cols) error("pushRow() error: mismatched column size");
//		data.add(newRow);
	}
	
	void setCol(int col, double d) { ints(0, rows()).forEach(i->row(i)[col] = d); }
	void setSlotInRow(int slot, int row, double d) {ints(0, cols()).forEach(c->row(row)[c*4 + slot] = d);}
	void set(int r, int c, int s, double d) { row(r)[4*c + s] = d; }
	
	
	int rows() { return data.size(); }
	int cols() { return cols/4; }
	
	double[] row(int rowNum) { return data.get(rowNum); }
	double[] first() { if(data.isEmpty()) error("Matrix is empty."); return row(0); }
	double[] last() { if(data.isEmpty()) error("Matrix is empty."); return row(rows()-1); }
	

	
	void removeRow(int rowNum) {
		data.remove(rowNum);
	}
	
	
	void printAll() {
		for(int i = 0; i < rows(); i++)
			println(row(i));
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
