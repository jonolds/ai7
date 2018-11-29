import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.jonslib.JonsLib;

public class Mat<E> extends JonsLib {

	BigDecimal[] bd = new BigDecimal[3];
	private ArrayList<double[]> data;
	private int cols;
	String type;
	
	Mat(int rows, int cols) {
		data = new ArrayList<>();
		ints(0, rows).forEach(i->data.add(new double[cols]));
		this.cols = cols;
	}
	
	Mat(int cols) {
		data = new ArrayList<>();
		this.cols = cols;
	}
	
	Mat(int cols, String type) {
		data = new ArrayList<>();
		this.cols = cols;
		this.type = type;
	}
	
	int rows() { return data.size(); }
	int cols() { return cols; }
	
	double[] row(int rowNum) {
		return data.get(rowNum);
	}
	
	void pushRow(double[] newRow) {
		if(newRow.length != cols) error("pushRow() error: mismatched column size");
		data.add(newRow);
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
	
	void error(String description) {
		throw new IllegalArgumentException(description);
	}

}
