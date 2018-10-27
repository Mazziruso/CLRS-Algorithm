package Charpter4;
import java.util.Random;
//import java.util.Formatter;

public class StrassenMatrixMult {
	
	public static void main(String[] args) {
		
		Random r = new Random(100L);
		int n = 128;
		int times = 50;
		
		int[][] aElements = new int[n][n];
		int[][] bElements = new int[n][n];
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				aElements[i][j] = r.nextInt(10);
			}
		}
		
		r.setSeed(15L);
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				bElements[i][j] = r.nextInt(10);
			}
		}
		
		Matrix A = new Matrix(aElements);
		Matrix B = new Matrix(bElements);
		Matrix C;
		
		
		
		System.out.println("--------------------");
		System.out.println("Starting...");
		System.out.println("--------------------");
		long startTime = System.currentTimeMillis();
		for(int i=0; i<times; i++) {
			 C = trandition(A, B);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("average run time: " + (endTime-startTime)/times);
		C = trandition(A, B);
		C.print();
		
//		System.out.println("--------------------");
//		A.print();
//		System.out.println("--------------------");
//		B.print();
		
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		          
		System.out.println("--------------------");
		System.out.println("Starting...");
		System.out.println("--------------------");
		startTime = System.currentTimeMillis();
		for(int i=0; i<times; i++) {
			C = Strassen(A, B);
		}
		endTime = System.currentTimeMillis();
		System.out.println("average run time: " + (endTime-startTime)/times);
//		C = Strassen(A, B);
//		C.print();
		
		
	}
	
	public static Matrix trandition(Matrix A, Matrix B) {
		Matrix C = new Matrix(A.getRows(), A.getCols());
		int[][] elements = C.getElements();
		int[][] aElements = A.getElements();
		int[][] bElements = B.getElements();
		
		for(int i=0; i<A.getRows(); i++) {
			for(int j=0; j<A.getCols(); j++) {
				for(int k=0; k<A.getRows(); k++) {
					elements[i][j] += aElements[i][k] * bElements[k][j];
				}
			}
		}
		
		return C;
	}
	
	public static Matrix Strassen(Matrix A, Matrix B) {
		Matrix C = new Matrix(A.getRows(), A.getCols());
		
		//base-case
		if(A.getRows() == 1) {
			C.getElements()[0][0] = A.getElements()[A.getRowStartIndex()][A.getColStartIndex()]
					* B.getElements()[B.getRowStartIndex()][B.getColStartIndex()];
			return C;
		}
		
		//partition
		Matrix[] Apartition = partition(A);
		Matrix a11 = Apartition[0];
		Matrix a12 = Apartition[1];
		Matrix a21 = Apartition[2];
		Matrix a22 = Apartition[3];
		Matrix[] Bpartition = partition(B);
		Matrix b11 = Bpartition[0];
		Matrix b12 = Bpartition[1];
		Matrix b21 = Bpartition[2];
		Matrix b22 = Bpartition[3];
		
		//S-Matrix
		Matrix S1 = Matrix.caculation(b12, b22, "-");
		Matrix S2 = Matrix.caculation(a11, a12, "+");
		Matrix S3 = Matrix.caculation(a21, a22, "+");//
		Matrix S4 = Matrix.caculation(b21, b11, "-");
		Matrix S5 = Matrix.caculation(a11, a22, "+");//
		Matrix S6 = Matrix.caculation(b11, b22, "+");
		Matrix S7 = Matrix.caculation(a12, a22, "-");
		Matrix S8 = Matrix.caculation(b21, b22, "+");
		Matrix S9 = Matrix.caculation(a11, a21, "-");
		Matrix S10 = Matrix.caculation(b11, b12, "+");
		
		//P-Matrix
		//Recursive
		Matrix P1 = Strassen(a11, S1);
		Matrix P2 = Strassen(S2, b22);
		Matrix P3 = Strassen(S3, b11);
		Matrix P4 = Strassen(a22, S4);
		Matrix P5 = Strassen(S5, S6);
		Matrix P6 = Strassen(S7, S8);
		Matrix P7 = Strassen(S9, S10);
		
		//C-Submatrix
		Matrix C11 = Matrix.caculation(Matrix.caculation(Matrix.caculation(P4, P5, "+"), P6, "+"), P2, "-");
		Matrix C12 = Matrix.caculation(P1, P2, "+");
		Matrix C21 = Matrix.caculation(P3, P4, "+");
		Matrix C22 = Matrix.caculation(Matrix.caculation(Matrix.caculation(P1, P5, "+"), P3, "-"), P7, "-");
		
		//merge to C-Matrix
		
		C = merge(C11, C12, C21, C22);
		
		return C;
	}
	
	public static Matrix[] partition(Matrix A) {
		Matrix[] matrix = new Matrix[4];
		
		int rowStart = A.getRowStartIndex();
		int rowEnd = A.getRowEndIndex();
		int rowMid = (rowStart+rowEnd)/2;
		int colStart = A.getColStartIndex();
		int colEnd = A.getColEndIndex();
		int colMid = (colStart+colEnd)/2;
		int[][] elements = A.getElements();
		
		matrix[0] = new Matrix(elements, rowStart, rowMid, colStart, colMid);
		matrix[1] = new Matrix(elements, rowStart, rowMid, colMid+1, colEnd);
		matrix[2] = new Matrix(elements, rowMid+1, rowEnd, colStart, colMid);
		matrix[3] = new Matrix(elements, rowMid+1, rowEnd, colMid+1, colEnd);
		
		
		return matrix;
	}
	
	public static Matrix merge(Matrix C11, Matrix C12, Matrix C21, Matrix C22) {
		Matrix matrix = new Matrix(C11.getRows()*2, C11.getCols()*2);
		int[][] resultElements = matrix.getElements();
		int length = C11.getRows();
		
		for(int i=0; i<length; i++) {
			for(int j=0; j<length; j++) {
				resultElements[i][j] = C11.getElements()[i][j];
				resultElements[i][j+length] = C12.getElements()[i][j];
				resultElements[i+length][j] = C21.getElements()[i][j];
				resultElements[i+length][j+length] = C22.getElements()[i][j];
			}
		}
		
		return matrix;
		
	}

}

class Matrix {
	private int[][] elements;
	private int rowStartIndex;
	private int rowEndIndex;
	private int colStartIndex;
	private int colEndIndex;
	
	public Matrix(int[][] elements) {
		this(elements, 0, elements.length-1, 0, elements[0].length-1);
	}
	
	public Matrix(int[][] elements, int rowStartIndex, int rowEndIndex, int colStartIndex, int colEndIndex) {
		this.elements = elements;
		this.rowStartIndex = rowStartIndex;
		this.rowEndIndex = rowEndIndex;
		this.colStartIndex = colStartIndex;
		this.colEndIndex = colEndIndex;
	}
	
	public Matrix(int row, int col) {
		this(new int[row][col]);
	}
	
	public static Matrix addMatrix(Matrix A, Matrix B) {
		Matrix C = new Matrix(A.getRows(), A.getCols());
		int[][] resultElements = C.getElements();
		int[][] aElements = A.getElements();
		int[][] bElements = B.getElements();
		
		for(int i=0; i<aElements.length; i++) {
			for(int j=0; j<bElements.length; i++) {
				resultElements[i][j] = aElements[i][j] + bElements[i][j];
			}
		}
		
		return C;
	}
	
	public static Matrix caculation(Matrix A, Matrix B, String operator) {
		Matrix matrix = new Matrix(A.getRows(), A.getCols());
		int[][] elements = matrix.getElements();
		int[][] aElements = A.getElements();
		int[][] bElements = B.getElements();
		
		int rowLength = A.getRows();
		int colLength = A.getCols();
		
		for(int i=0; i<rowLength; i++) {
			for(int j=0; j<colLength; j++) {
				if(operator == "+") 
						elements[i][j] = aElements[A.getRowStartIndex()+i][A.getColStartIndex()+j] 
								+ bElements[B.getRowStartIndex()+i][B.getColStartIndex()+j];
				else if(operator == "-")
						elements[i][j] = aElements[A.getRowStartIndex()+i][A.getColStartIndex()+j] 
								- bElements[B.getRowStartIndex()+i][B.getColStartIndex()+j];
				else
					System.out.println("Error, Operator");
			}
		}
		
		return matrix;
	}
	
	public void print() {
		
		int[][] e = this.getElements();
		for(int i=this.getRowStartIndex(); i<=this.getRowEndIndex(); i++) {
			for(int j=this.getColStartIndex(); j<=this.getColEndIndex(); j++) {
				System.out.print(String.format("%7d",(e[i][j])));
			}
			System.out.println();
		}
		
	}
	
	public int getRows() {
		return this.rowEndIndex-this.rowStartIndex+1;
	}
	
	public int getCols() {
		return this.colEndIndex-this.colStartIndex+1;
	}
	
	public int[][] getElements() {
		return this.elements;
	}
	
	public void setElements(int[][] elements) {
		this.elements = elements;
	}
	
	public int getRowStartIndex() {
		return this.rowStartIndex;
	}
	
	public void setRowStartInedx(int i) {
		this.rowStartIndex = i;
	}
	
	public int getRowEndIndex() {
		return this.rowEndIndex;
	}
	
	public void setRowEndInedx(int i) {
		this.rowEndIndex = i;
	}
	
	public int getColStartIndex() {
		return this.colStartIndex;
	}
	
	public void setColStartInedx(int i) {
		this.colStartIndex = i;
	}
	
	public int getColEndIndex() {
		return this.colEndIndex;
	}
	
	public void setColEndInedx(int i) {
		this.colEndIndex = i;
	}
	
}