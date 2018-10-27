package Charpter6;
//import java.util.Arrays;
//import java.io.*;

public class HeapDemo {
	
	public static void main(String[] args) {
		
		//----------------------------------------------
		//------------------堆排序测试---------------------
		//----------------------------------------------
//		
//		int times = 50;
//		int num = 5000000;
//		
//		//有效堆大小
//		int heap_size = num;
//		//堆高度为 h
////		int h = HeapStr.HeapHeight(heap_size);
//		//堆最大长度
//		int Alength = HeapStr.HeapLength(heap_size);
//		//初始化堆
//		int[] A = new int[Alength];
//		
//		//读取文件
//		String s;
//		FileReader fr = null;
//		BufferedReader br = null;
//		
//		try {
//			String dir = new String("E:\\JavaWorkspace\\AlgorithmsIntroduce\\src\\Charpter6\\DataSet.dat");
//			fr = new FileReader(dir);
//			br = new BufferedReader(fr);
//		} catch(FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			int i = 0;
//			while((s=br.readLine()) != null) {
//				A[i] = Integer.valueOf(s);
//				i++;
//			}
//			br.close();
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("File Read Successfully");
////		
////		System.out.println("原始数组A：");
//////		System.out.println(Arrays.toString(A));
////		printA(A);
////		
////		
//////		int[] A = {3,2,14,9,17,4,7,12,6,5};
////		BuildMaxHeap(A, heap_size);
////		
////		System.out.println("最大堆A：");
//////		System.out.println(Arrays.toString(A));
////		printA(A);
//		
//		try {
//			Thread.sleep(2000);
//			System.out.println("Starting...");
//		} catch(InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		long startTime = System.currentTimeMillis();
//		for(int i=0; i<times; i++) {
//			HeapStr.HeapSort(A, heap_size);
//		}
//		long endTime = System.currentTimeMillis();
//		System.out.println("time: " + (endTime-startTime)/times + "ms");
//		
//		HeapStr.HeapSort(A, heap_size);
////		System.out.println("堆排序后A：");
//////		System.out.println(Arrays.toString(A));
////		printA(A);
//		
//		String dirRead = "E:\\JavaWorkspace\\AlgorithmsIntroduce\\src\\Charpter6\\DataSetHeapSort.dat";
//		FileWriter fw = null;
//		BufferedWriter bw = null;
//		
//		try {
//			fw = new FileWriter(dirRead);
//			bw = new BufferedWriter(fw);
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			for(int i=0; i<A.length; i++) {
//				bw.write(String.valueOf(A[i]));
//				bw.newLine();
//			}
//			bw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("File Write Successfully");
		
		//-------------------------------------------
		//-------------堆排序测试结束-------------------
		//-------------------------------------------
		
		
		//--------------------------------------------
		//-------------堆其他操作测试---------------------
		//--------------------------------------------
		
		int[] inArray = {15, 13, 9, 5, 12, 8, 7, 4, 0, 6, 2, 1};
		int heap_size = inArray.length;
		int Alen = MaxHeapStr.HeapLength(heap_size);
		int[] A = new int[Alen];
		for(int i=0; i<heap_size; i++) {
			A[i] = inArray[i];
		}
		
		MaxHeapStr.printA(A, heap_size);
		
//		int Amax = HeapStr.HeapExtractMax(A, heap_size);
//		HeapStr.printA(A, heap_size);
//		System.out.println(Amax);
		
		heap_size = MaxHeapStr.MaxHeapInsert(A, 10, heap_size);
		MaxHeapStr.printA(A,  heap_size);
		
//		int[] res = MaxHeapStr.HeapExtractMax(A, heap_size);
//		heap_size = res[1];
//		MaxHeapStr.printA(A, heap_size);
		
		heap_size = MaxHeapStr.HeapDelete(A, 2, heap_size);
		MaxHeapStr.printA(A, heap_size);
		
		MinHeapStr.BuildMinHeap(A, heap_size);
		MinHeapStr.printA(A, heap_size);
		
		heap_size = MinHeapStr.MinHeapInsert(A, 10, heap_size);
		MinHeapStr.printA(A, heap_size);
		
	}
	


}

class ArrayHeap {
	public int heap_size;
	public int length;
	public int[] e;
}

abstract class HeapStr {

	public static int Parent(int i) {
		return (i-1)/2;
	}
	
	public static int Left(int i) {
		return i*2+1; //满足伪代码中的指针关系
	}
	
	public static int Right(int i) {
		return i*2+2; //满足伪代码中的指针关系
	}
	
	public static int HeapHeight(int heap_size) {
		return (int) (Math.log(heap_size) * 1.442695041);
	}
	
	//
	public static int HeapLength(int heap_size) {
		int h = HeapHeight(heap_size);
		return (int)Math.pow(2, h+1) - 1;
	}
	
	//打印堆元素关键字
	public static void printA(int[] A, int heap_size) {
		for(int i=0; i<heap_size; i++) {
			System.out.print(A[i] + " ");
			if(i%20 == 0 & i!=0) {
				System.out.println();
			}
		}
		System.out.println();
	}
	
}

class MaxHeapStr extends HeapStr{
	
	
	//-------------------------
	//---------堆操作函数---------
	//-------------------------
	
	
	//维护最大堆性质,i代表伪代码中的指针
	public static void MaxHeapify(int[] A, int i, int heap_size) {
		int l = Left(i);
		int r = Right(i);
		int largest = i;
		
		if(r<heap_size) {
			largest = (A[r]>A[i]) ? r : i;
		}
		if(l<heap_size) {
			largest = (A[l]>A[largest]) ? l : largest;
		}
		
		if(largest != i) {
			int temp = A[i];
			A[i] = A[largest];
			A[largest] = temp;
			MaxHeapify(A, largest, heap_size);
		}
	}
	
	//建堆
	public static void BuildMaxHeap(int[] A, int heap_size) {
		for(int i=(heap_size/2)-1; i>=0; i--) {
			MaxHeapify(A, i, heap_size);
		}
	}
	
	//堆排序
	public static void HeapSort(int[] A, int heap_size) {
		BuildMaxHeap(A, heap_size);
		
		int temp;
		for(int i=heap_size-1; i>0; i--) {
			temp = A[i];
			A[i] = A[0];
			A[0] = temp;
			heap_size--;
			MaxHeapify(A, 0, heap_size);
		}
	}
	
	//返回堆中最大数
	public static int HeapMaximum(int[] A) {
		return A[0];
	}
	
	//抽出堆中最大数，返回{最大值，有效堆大小}
	public static int[] HeapExtractMax(int[] A, int heap_size) {
		if(heap_size<1) {
			System.err.println("Heap Underflow");
		}
		int max = A[0];
		heap_size--;//满足计算机数组指针的关系，伪代码中的指针自减一
		A[0] = A[heap_size];
		MaxHeapify(A, 0, heap_size);//heap_size已经自减一，所以不用重复操作
		
		int[] returnRes = {max, heap_size};
		return returnRes;
	}
	
	//实现关键字增加的操作，这一操作要将元素A[i]的关键字更新为key，所以输入key要比A[i]大
	public static void HeapIncreasekey(int[] A, int i, int key) {
		if(key<A[i]) {
			System.err.println("new key is smaller than current key");
		}
		
		//类似于插入排序操作，不断与父结点进行比较并交换
		A[i] = key;
		int p = Parent(i);
		while(i>0 & A[p]<key) {
			//交换子结点与父结点
			A[i] = A[p];
			//更新子结点下标
			i = p;
			//更新父结点下标
			p = Parent(p);
		}
		A[i] = key;
	}
	
	//实现在堆中插入新关键字，返回更新后的有效堆大小
	public static int MaxHeapInsert(int[] A, int key, int heap_size) {
		A[heap_size] = -2147483648;
		HeapIncreasekey(A, heap_size, key);
		
		return ++heap_size;
		
	}
	
	//删除堆中的一个结点i
	public static int HeapDelete(int[] A, int i, int heap_size) {
		A[i] = A[heap_size-1];
		heap_size--;
		MaxHeapify(A, i, heap_size);
		
		return heap_size;
	}
	
}

class MinHeapStr extends HeapStr {
	
	
	//-------------------------
	//---------堆操作函数---------
	//-------------------------
	
	
	//维护最小堆性质,i代表伪代码中的指针
	public static void MinHeapify(int[] A, int i, int heap_size) {
		int l = Left(i);
		int r = Right(i);
		int smallest = i;
		
		if(r<heap_size) {
			smallest = (A[r]<A[i]) ? r : i;
		}
		if(l<heap_size) {
			smallest = (A[l]<A[smallest]) ? l : smallest;
		}
		
		if(smallest != i) {
			int temp = A[i];
			A[i] = A[smallest];
			A[smallest] = temp;
			MinHeapify(A, smallest, heap_size);
		}
	}
	
	//建堆
	public static void BuildMinHeap(int[] A, int heap_size) {
		for(int i=(heap_size-1)/2; i>=0; i--) {
			MinHeapify(A, i, heap_size);
		}
	}
	
	//堆排序，从大到小
	public static void HeapSort(int[] A, int heap_size) {
		BuildMinHeap(A, heap_size);
		
		int temp;
		for(int i=heap_size-1; i>0; i--) {
			temp = A[i];
			A[i] = A[0];
			A[0] = temp;
			heap_size--;
			MinHeapify(A, 0, heap_size);
		}
	}
	
	//返回堆中最小数
	public static int HeapMinimum(int[] A) {
		return A[0];
	}
	
	//抽出堆中最小数，返回{最小值， 堆有效大小}
	public static int[] HeapExtractMin(int[] A, int heap_size) {
		if(heap_size<1) {
			System.err.println("Heap Underflow");
		}
		int min = A[0];
		heap_size--;//满足计算机数组指针的关系，伪代码中的指针自减一
		A[0] = A[heap_size];
		MinHeapify(A, 0, heap_size);//heap_size已经自减一，所以不用重复操作
		
		int[] returnRes = {min, heap_size}; 
		return returnRes;
	}
	
	//实现关键字减少的操作，这一操作要将元素A[i]的关键字更新为key，所以输入key要比A[i]小
	public static void HeapDecreasekey(int[] A, int i, int key) {
		if(key>A[i]) {
			System.err.println("new key is smaller than current key");
		}
		
		//类似于插入排序操作，不断与父结点进行比较并交换
		A[i] = key;
		int p = Parent(i);
		while(i>0 & A[p]>key) {
			//交换子结点与父结点
			A[i] = A[p];
			//更新子结点下标
			i = p;
			//更新父结点下标
			p = Parent(p);
		}
		A[i] = key;
	}
	
	//实现在堆中插入新关键字，返回更新后的有效堆大小
	public static int MinHeapInsert(int[] A, int key, int heap_size) {
		A[heap_size] = 2147483647;
		HeapDecreasekey(A, heap_size, key);
		
		return ++heap_size;
		
	}
	
	//删除堆中的一个结点i
	public static int HeapDelete(int[] A, int i, int heap_size) {
		A[i] = A[heap_size-1];
		heap_size--;
		MinHeapify(A, i, heap_size);
		
		return heap_size;
	}
	
}

//class MaxHeapNode {
//	int element;
//	MaxHeapNode Parent;
//	MaxHeapNode Left;
//	MaxHeapNode Right;
//}
//
//class Heap {
//	
//	MaxHeapNode root;
//	
//	public Heap(int[] A) {
//		
//		int k = A.length;
//		
//		this.root = createNode();
//		
//	}
//	
//	public static MaxHeapNode createNode(int index, int[] A) {
//		if(A.length <= 0) {
//			return null;
//		}
//		
//		
//	}
//}
