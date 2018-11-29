import java.util.ArrayList;

public class Mat extends JonsLib {

	private ArrayList<double[]> data;
	private int cols;
	
	Mat(int rows, int cols) {
		data = new ArrayList<>();
		ints(0, rows).forEach(i->data.add(new double[cols]));
		this.cols = cols;
	}
	
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
		data.add(newRow);
	}
	
	
	int rows() { return data.size(); }
	int cols() { return cols; }
	
	double[] row(int rowNum) {
		return data.get(rowNum);
	}
	

	
	void removeRow(int rowNum) {
		data.remove(rowNum);
	}
	
	
	void printAll() {
		for(int i = 0; i < rows(); i++)
			println(row(i));
	}
	
	
	
	double colSum(int colNum) {
		if(colNum > cols-1) error("ColSum error: col out of range");
		double sum = 0.0;
		for(int i = 0; i < data.size(); i++)
			sum += data.get(i)[colNum];
		return sum;
	}
	double colMean(int colNum) {
		if(colNum > cols-1) error("ColMean error: col out of range");
		return colSum(colNum)/data.size();
	}
	
//	void error(String description) {
//		throw new IllegalArgumentException(description);
//	}
	
}
